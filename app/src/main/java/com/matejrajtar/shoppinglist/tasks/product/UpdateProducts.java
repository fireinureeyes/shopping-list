package com.matejrajtar.shoppinglist.tasks.product;

import android.content.Context;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.matejrajtar.shoppinglist.R;
import com.matejrajtar.shoppinglist.database.ProductDao;
import com.matejrajtar.shoppinglist.model.Product;

public class UpdateProducts {
    private final Context context;
    private DatabaseReference dbRef;

    public UpdateProducts(Context context) {
        this.context = context;

        dbRef = FirebaseDatabase.getInstance(context.getString(R.string.database_url)).getReference("items");
    }

    public void moveToCart(Product product) {
        new Thread(() ->
        {
            ProductDao dao = ProductDao.instance(context);
            dao.moveToCart(product.id(), "1", false);

            dbRef.child(product.id().toString()).setValue("1");
        }).start();
    }

    public void removeFromCart(Product product) {
        new Thread(() ->
        {
            ProductDao dao = ProductDao.instance(context);

            dao.moveToCart(product.id(), "0", false);

            dbRef.child(product.id().toString()).setValue("0");
        }).start();
    }
}