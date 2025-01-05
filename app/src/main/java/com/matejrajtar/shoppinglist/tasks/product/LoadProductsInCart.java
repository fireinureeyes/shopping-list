package com.matejrajtar.shoppinglist.tasks.product;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.matejrajtar.shoppinglist.R;
import com.matejrajtar.shoppinglist.database.ProductDao;
import com.matejrajtar.shoppinglist.model.Product;
import com.matejrajtar.shoppinglist.views.CartView;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class LoadProductsInCart extends AsyncTask<Void, Void, List<Product>> {
    private final ProductDao dao;
    private final OnProductsLoaded callback;
    @SuppressLint("StaticFieldLeak")
    private final Context context;

    private final CartView view;

    private ValueEventListener listener;
    private DatabaseReference dbRef;

    public LoadProductsInCart(Context context, OnProductsLoaded callback, CartView view) {
        this.dao = ProductDao.instance(context);
        this.callback = callback;
        this.context = context;
        this.view = view;

        dbRef = FirebaseDatabase.getInstance(context.getString(R.string.database_url)).getReference("items");
    }

    @Override
    protected List<Product> doInBackground(Void... voids) {
        return dao.inCart();
    }

    @Override
    protected void onPostExecute(List<Product> products) {
        if (listener != null) {
            dbRef.removeEventListener(listener);
        }
        view.updateList();
        callback.onProductsLoaded(products);
    }

    public interface OnProductsLoaded {
        void onProductsLoaded(List<Product> products);
    }

    //reloads cart from firebase database
    public void updateFromFirebase(EditText editText) {
        ProductDao dao = ProductDao.instance(context);
        List<Product> database = dao.all();

        HashMap<Long, String> hashMap = new HashMap<>();

        listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                        hashMap.put(Long.parseLong(Objects.requireNonNull(itemSnapshot.getKey())), itemSnapshot.getValue().toString());
                    }

                    ProductDao dao = ProductDao.instance(context);

                    for (Product product : database) {
                        dao.moveToCart(product.id(), hashMap.get(Long.valueOf(product.id())), false);
                    }
                    onPostExecute(dao.inCart());

                    if (editText != null) editText.setText(hashMap.get(Long.valueOf("0")));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        this.dbRef.addListenerForSingleValueEvent(listener);
    }

}