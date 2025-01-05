package com.matejrajtar.shoppinglist.activities;

import android.content.Intent;

import androidx.appcompat.app.AlertDialog;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.matejrajtar.shoppinglist.R;
import com.matejrajtar.shoppinglist.base.BaseActivity;
import com.matejrajtar.shoppinglist.database.ProductDao;
import com.matejrajtar.shoppinglist.model.Category;
import com.matejrajtar.shoppinglist.model.Product;
import com.matejrajtar.shoppinglist.tasks.category.LoadCategories;
import com.matejrajtar.shoppinglist.tasks.category.LoadCategories.OnCategoriesLoaded;
import com.matejrajtar.shoppinglist.views.AddProductView;
import com.matejrajtar.shoppinglist.views.AddProductView.AddProductViewObserver;

import java.util.List;

public class AddProductActivity extends BaseActivity<AddProductView> implements AddProductViewObserver, OnCategoriesLoaded {

    private DatabaseReference dbRef;


    @Override
    public void onCategoriesLoaded(List<Category> categories) {
        view.updateLists(getSupportFragmentManager(), categories);
    }

    @Override
    public void onBack() {
        finish();
    }

    @Override
    public void onShare() {
        new AlertDialog.Builder(this)
                .setTitle("Empty shopping list")
                .setMessage("Are you sure you want to remove all items from the list?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, (dialog, whichButton) -> {
                    // User confirmed
                    dbRef = FirebaseDatabase.getInstance(this.getString(R.string.database_url)).getReference("items");
                    ProductDao dao = ProductDao.instance(this);
                    List<Product> productList = dao.inCart();
                    for (Product product : productList) {
                        dao.moveToCart(product.id(), "0", false);
                        dbRef.child(String.valueOf(product.id())).setValue("0");
                    }
                    LoadCategories task = new LoadCategories(this, this);
                    task.execute();
                })
                .setNegativeButton(android.R.string.no, null).show();
    }

    @Override
    public void onCreateProduct(String category) {
        Intent intent = CreateProductActivity.intent(this, category);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();

        LoadCategories task = new LoadCategories(this, this);
        task.execute();
    }

    @Override
    protected AddProductView view() {
        return new AddProductView(this);
    }
}