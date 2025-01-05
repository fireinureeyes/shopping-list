package com.matejrajtar.shoppinglist.tasks.product;

import android.content.Context;
import android.os.AsyncTask;

import com.matejrajtar.shoppinglist.database.ProductDao;
import com.matejrajtar.shoppinglist.model.Product;

public class UpdateProduct extends AsyncTask<Void, Void, Boolean> {
    private final Product oldProduct;
    private final Product newProduct;
    private final ProductDao dao;
    private final OnProductUpdated callback;

    public UpdateProduct(Context context, Product oldProduct, Product newProduct, OnProductUpdated callback) {
        this.oldProduct = oldProduct;
        this.newProduct = newProduct;
        this.dao = ProductDao.instance(context);
        this.callback = callback;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        Boolean result = false;

        Product product = dao.byName(newProduct.name());

        if ((product == null) || (product.id().equals(oldProduct.id()))) {
            dao.update(oldProduct.id(), newProduct.name(), newProduct.category(), newProduct.image().toString());
            result = true;
        }

        return result;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        callback.onProductUpdated(result);
    }

    public interface OnProductUpdated {
        void onProductUpdated(Boolean result);
    }
}