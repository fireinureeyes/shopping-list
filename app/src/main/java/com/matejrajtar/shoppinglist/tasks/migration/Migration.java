package com.matejrajtar.shoppinglist.tasks.migration;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.matejrajtar.shoppinglist.R;
import com.matejrajtar.shoppinglist.database.AppDatabase;
import com.matejrajtar.shoppinglist.database.CategoryDao;
import com.matejrajtar.shoppinglist.database.ProductDao;
import com.matejrajtar.shoppinglist.old.CartItem;
import com.matejrajtar.shoppinglist.old.Category;
import com.matejrajtar.shoppinglist.old.Product;
import com.matejrajtar.shoppinglist.utils.ResourceUtils;
import com.orm.query.Select;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Migration extends AsyncTask<Void, Void, Void> {
    @SuppressLint("StaticFieldLeak")
    private final Context context;
    private final AppDatabase database;
    private final OnMigrationDone callback;
    private final ProgressDialog dialog;
    private final HashMap<Long, String> hashMap = new HashMap<>();
    private ValueEventListener listener;
    private DatabaseReference dbRef;

    public Migration(Context context, OnMigrationDone callback) {
        this.context = context;
        this.database = AppDatabase.instance(context);
        this.callback = callback;
        this.dialog = new ProgressDialog(context);
        this.dialog.setMessage(context.getString(R.string.dialog_update_database));
        this.dialog.setCancelable(false);
        this.dialog.setCanceledOnTouchOutside(false);
    }

    @Override
    protected void onPreExecute() {
        //shows loading in the beginning
        dialog.show();

        dbRef = FirebaseDatabase.getInstance(context.getString(R.string.database_url)).getReference("items");
    }

    @Override
    protected Void doInBackground(Void... voids) {
        List<Category> categories = Select.from(Category.class).list();
        listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                        hashMap.put(Long.parseLong(Objects.requireNonNull(itemSnapshot.getKey())), itemSnapshot.getValue().toString());
                    }

                    if (!categories.isEmpty()) {
                        CategoryDao categoryDao = database.categoryDao();
                        ProductDao productDao = database.productDao();
                        for (Category category : categories) {
                            try {
                                categoryDao.insert(new com.matejrajtar.shoppinglist.model.Category(category.getName()));
                            } catch (Exception e) {
                            }
                        }

                        Set<Long> productsInCart = new HashSet<>();
                        List<CartItem> cartItems = Select.from(CartItem.class).list();

                        for (CartItem cartItem : cartItems) {
                            try {

                                productDao.insert(product(
                                        cartItem.getCategory(),
                                        cartItem.getName(),
                                        cartItem.getImage(),
                                        "1"
                                ));

                                productsInCart.add(cartItem.productId());
                            } catch (Exception e) {
                                // error creating product
                            }
                        }

                        List<Product> products = Select.from(Product.class).list();

                        for (Product product : products) {
                            try {

                                if (!productsInCart.contains(product.getId())) {
                                    productDao.insert(product(
                                            product.getCategory(),
                                            product.getName(),
                                            product.getImage(),
                                            "0"
                                    ));
                                }
                            } catch (Exception e) {
                                // error creating product
                            }
                        }

                        CartItem.deleteAll(CartItem.class);
                        Product.deleteAll(Product.class);
                        Category.deleteAll(Category.class);
                    } else {
                        database.initialize(context, hashMap, callback);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        dbRef.addListenerForSingleValueEvent(listener);

        return null;
    }

    private com.matejrajtar.shoppinglist.model.Product product(String category, String name, byte[] image, String inCart) throws Exception {
        File imageFile = ResourceUtils.createFile(context, image);

        return new com.matejrajtar.shoppinglist.model.Product(
                category,
                name,
                imageFile.getAbsolutePath(),
                inCart,
                false
        );
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if (listener != null) {
            dbRef.removeEventListener(listener);
        }
        dialog.dismiss();
    }

    public interface OnMigrationDone {
        void onMigrationDone();
    }
}