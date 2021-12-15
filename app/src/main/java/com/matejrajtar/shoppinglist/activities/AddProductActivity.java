package com.matejrajtar.shoppinglist.activities;

import android.content.Intent;

import com.matejrajtar.shoppinglist.base.BaseActivity;
import com.matejrajtar.shoppinglist.model.Category;
import com.matejrajtar.shoppinglist.tasks.category.LoadCategories;
import com.matejrajtar.shoppinglist.tasks.category.LoadCategories.OnCategoriesLoaded;
import com.matejrajtar.shoppinglist.views.AddProductView;
import com.matejrajtar.shoppinglist.views.AddProductView.AddProductViewObserver;

import java.util.List;

public class AddProductActivity extends BaseActivity<AddProductView> implements AddProductViewObserver, OnCategoriesLoaded {
    @Override
    public void onCategoriesLoaded(List<Category> categories) {
        view.updateLists(getSupportFragmentManager(), categories);
    }

    @Override
    public void onBack() {
        finish();
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