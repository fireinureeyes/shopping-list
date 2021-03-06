package com.matejrajtar.shoppinglist.tasks.category;

import android.content.Context;
import android.os.AsyncTask;

import com.matejrajtar.shoppinglist.database.CategoryDao;
import com.matejrajtar.shoppinglist.database.ProductDao;
import com.matejrajtar.shoppinglist.model.Category;

public class RenameCategory extends AsyncTask<Void, Void, Boolean> {
    private final Category category;
    private final String newName;
    private final CategoryDao categoryDao;
    private final ProductDao productDao;
    private final OnCategoryRenamed callback;

    public RenameCategory(Context context, Category category, String newName, OnCategoryRenamed callback) {
        this.category = category;
        this.newName = newName;
        this.categoryDao = CategoryDao.instance(context);
        this.productDao = ProductDao.instance(context);
        this.callback = callback;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        Boolean result = false;

        if (!categoryDao.contains(newName)) {
            categoryDao.rename(category.name(), newName);
            productDao.updateCategory(category.name(), newName);
            result = true;
        }

        return result;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        callback.onCategoryRenamed(result);
    }

    public interface OnCategoryRenamed {
        void onCategoryRenamed(Boolean result);
    }
}