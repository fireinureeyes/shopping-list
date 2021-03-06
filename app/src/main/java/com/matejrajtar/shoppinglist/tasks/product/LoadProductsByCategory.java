package com.matejrajtar.shoppinglist.tasks.product;

import android.content.Context;
import android.os.AsyncTask;

import com.matejrajtar.shoppinglist.database.ProductDao;
import com.matejrajtar.shoppinglist.model.Product;

import java.util.Collections;
import java.util.List;

public class LoadProductsByCategory extends AsyncTask<Void, Void, List<Product>> {
    private final String category;
    private final ProductDao dao;
    private final OnProductsLoaded callback;

    public LoadProductsByCategory(Context context, String category, OnProductsLoaded callback) {
        this.category = category;
        this.dao = ProductDao.instance(context);
        this.callback = callback;
    }

    @Override
    protected List<Product> doInBackground(Void... voids) {
        List<Product> products = dao.byCategory(category, false);
        Collections.sort(products, (p1, p2) -> p1.name().compareTo(p2.name()));

        return products;
    }

    @Override
    protected void onPostExecute(List<Product> products) {
        callback.onProductsLoaded(products);
    }

    public interface OnProductsLoaded {
        void onProductsLoaded(List<Product> products);
    }
}