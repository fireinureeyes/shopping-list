package com.matejrajtar.shoppinglist.activities;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.matejrajtar.shoppinglist.R;
import com.matejrajtar.shoppinglist.base.BaseActivity;
import com.matejrajtar.shoppinglist.database.ProductDao;
import com.matejrajtar.shoppinglist.model.Product;
import com.matejrajtar.shoppinglist.tasks.migration.Migration;
import com.matejrajtar.shoppinglist.tasks.migration.Migration.OnMigrationDone;
import com.matejrajtar.shoppinglist.tasks.product.LoadProductsInCart;
import com.matejrajtar.shoppinglist.tasks.product.LoadProductsInCart.OnProductsLoaded;
import com.matejrajtar.shoppinglist.tasks.product.UpdateProducts;
import com.matejrajtar.shoppinglist.utils.Analytics;
import com.matejrajtar.shoppinglist.utils.NormalPreferences;
import com.matejrajtar.shoppinglist.views.CartView;
import com.matejrajtar.shoppinglist.views.CartView.CartViewObserver;

import java.util.List;

public class CartActivity extends BaseActivity<CartView> implements CartViewObserver, OnProductsLoaded, OnMigrationDone {

    private EditText editText;
    private DatabaseReference dbRef;

    @Override
    protected void initialize() {

        Analytics analytics = new Analytics(this);
        analytics.appLaunched();

        LoadProductsInCart task = new LoadProductsInCart(this, this, view);

        handleAutocompleteTextView();

        handleExtraItemsEditText();

        dbRef = FirebaseDatabase.getInstance(this.getString(R.string.database_url)).getReference("items");

        task.updateFromFirebase(editText);
        reloadProducts();
    }

    private void handleExtraItemsEditText() {
        editText = (EditText) findViewById(R.id.extra_items);
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    dbRef.child("0").setValue(editText.getText().toString());
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        });
    }

    private void handleAutocompleteTextView() {
        final ArrayAdapter<String> autoComplete = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        autoCompleteTextView.setThreshold(1);

        ProductDao dao = ProductDao.instance(this);
        List<Product> productList = dao.all();
        for (Product product : productList) {
            autoComplete.add(product.name());
        }
        autoCompleteTextView.setAdapter(autoComplete);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                autoCompleteTextView.setText("");
                autoCompleteTextView.clearFocus();
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(findViewById(R.id.autoCompleteTextView).getWindowToken(), 0);

                Product clickedItem = dao.byName(autoComplete.getItem(i).toString());
                if (clickedItem.inCart.equals("0")) {
                    dao.moveToCart(clickedItem.id(), "1", false);
                    dbRef.child(String.valueOf(clickedItem.id())).setValue("1");
                    reloadProducts();
                }
            }
        });
    }

    @Override
    public void onProductsLoaded(List<Product> products) {
        view.updateList(products);
    }

    @Override
    public void onProductSelected(Product product) {
        UpdateProducts task = new UpdateProducts(this);

        task.removeFromCart(product);
        reloadProducts();
    }

    @Override
    public void onShare(List<Product> products) {

        LoadProductsInCart task = new LoadProductsInCart(this, this, view);
        task.updateFromFirebase(editText);

        reloadProducts();
    }

    @Override
    public void onAddProduct() {
        Intent intent = new Intent(this, AddProductActivity.class);
        startActivity(intent);
    }

    @Override
    protected CartView view() {
        return new CartView(this, this);
    }

    private String shareContent(List<Product> products) {
        StringBuilder result = new StringBuilder();

        String lastCategory = "";

        for (Product product : products) {
            if (!product.isSelected()) {
                if (!TextUtils.equals(product.category(), lastCategory)) {
                    lastCategory = product.category();

                    if (result.length() != 0) {
                        result.append("\n");
                    }

                    result.append(lastCategory).append(":\n");
                }

                result.append("   - ").append(product.name()).append("\n");
            }
        }

        return result.toString();
    }

    public void reloadProducts() {
        view.updateList();
        LoadProductsInCart task = new LoadProductsInCart(this, this, view);
        task.execute();
    }

    @Override
    public void onMigrationDone() {
        NormalPreferences preferences = new NormalPreferences(this);
        preferences.migrationDone();

        handleAutocompleteTextView();

        reloadProducts();
    }

    @Override
    protected void onResume() {
        super.onResume();

        NormalPreferences preferences = new NormalPreferences(this);

        if (preferences.isMigrationDone()) {
            LoadProductsInCart task = new LoadProductsInCart(this, this, view);
            task.updateFromFirebase(editText);
            reloadProducts();
        } else {
            Migration migration = new Migration(this, this);
            migration.execute();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}