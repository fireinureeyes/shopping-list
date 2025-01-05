package com.matejrajtar.shoppinglist.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
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
        reloadProducts("");
    }

    private void handleExtraItemsEditText() {
        editText = (EditText) findViewById(R.id.extra_items);
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    Toast.makeText(getApplicationContext(), "Extra item saved", Toast.LENGTH_SHORT).show();
                    dbRef.child("0").setValue(editText.getText().toString());
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        });
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int keyCode, KeyEvent event) {
                editText.clearFocus();
                return true;
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
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),"Added " + clickedItem.name + " to cart", Snackbar.LENGTH_LONG);
                    snackbar.setAction("Undo", v -> {
                        UpdateProducts task = new UpdateProducts(CartActivity.this);
                        task.removeFromCart(clickedItem);
                        reloadProducts(String.valueOf(dao.inCart().size()));

                        if (view.getContext() instanceof Activity){
                            Activity host = (Activity) view.getContext();
                            TextView sumText = (TextView) host.findViewById(R.id.sum);
                            CharSequence text = sumText.getText();
                            String numberOnly= text.toString().replaceAll("[^0-9]", "");
                            Integer p = Integer.parseInt(numberOnly);
                            if (p>0) p--;
                            if (String.valueOf(p).equals("1")) {sumText.setText(p+" item");} else {
                                sumText.setText(p+" items");}
                        }
                    });
                    snackbar.show();

                    Vibrator vibrator = null;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        vibrator = (Vibrator) view.getContext().getSystemService(Context.VIBRATOR_SERVICE);
                    }
                    // Check if the device has a vibrator
                    if (vibrator.hasVibrator()) {
                        // Vibrate for 100 milliseconds
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            vibrator.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE));
                        } else {
                            //deprecated in API 26
                            vibrator.vibrate(100);
                        }
                    }

                    dao.moveToCart(clickedItem.id(), "1", false);
                    dbRef.child(String.valueOf(clickedItem.id())).setValue("1");
                    reloadProducts(String.valueOf(dao.inCart().size()));
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
        ProductDao dao = ProductDao.instance(this);
        task.removeFromCart(product);
        reloadProducts(String.valueOf(dao.inCart().size()));

        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),"Removed " + product.name + " from cart", Snackbar.LENGTH_LONG);
        snackbar.setAction("Undo", v -> {
            task.moveToCart(product);
            reloadProducts(String.valueOf(dao.inCart().size()));
        });
        snackbar.show();

        Vibrator vibrator = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            vibrator = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
        }
        // Check if the device has a vibrator
        if (vibrator.hasVibrator()) {
            // Vibrate for 100 milliseconds
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                //deprecated in API 26
                vibrator.vibrate(100);
            }
        }
    }

    @Override
    public void onShare(List<Product> products) {

        LoadProductsInCart task = new LoadProductsInCart(this, this, view);
        task.updateFromFirebase(editText);
        ProductDao dao = ProductDao.instance(this);
        reloadProducts(String.valueOf(dao.inCart().size()));
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

    public void reloadProducts(String s) {
        view.updateList();
        LoadProductsInCart task = new LoadProductsInCart(this, this, view);
        task.execute();

        TextView sumText = (TextView) findViewById(R.id.sum);
        if (s.equals("1")) {sumText.setText(s+" item");} else {
        sumText.setText(s+" items");}

        //Toast.makeText(this, "Reloaded cart", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMigrationDone() {
        NormalPreferences preferences = new NormalPreferences(this);
        preferences.migrationDone();

        handleAutocompleteTextView();
        ProductDao dao = ProductDao.instance(this);
        reloadProducts(String.valueOf(dao.inCart().size()));
    }

    @Override
    protected void onResume() {
        super.onResume();

        NormalPreferences preferences = new NormalPreferences(this);
        ProductDao dao = ProductDao.instance(this);

        if (preferences.isMigrationDone()) {
            LoadProductsInCart task = new LoadProductsInCart(this, this, view);
            task.updateFromFirebase(editText);
            reloadProducts(String.valueOf(dao.inCart().size()));
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