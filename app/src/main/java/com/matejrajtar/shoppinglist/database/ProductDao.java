package com.matejrajtar.shoppinglist.database;

import android.content.Context;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.matejrajtar.shoppinglist.model.Product;

import java.util.List;

@Dao
public interface ProductDao {
    @Query("SELECT * FROM Product")
    List<Product> all();

    @Query("SELECT * FROM Product WHERE inCart!='0'")
    List<Product> inCart();

    @Query("SELECT * FROM Product WHERE category=:category AND inCart=:inCart")
    List<Product> byCategory(String category, Boolean inCart);

    @Query("SELECT * FROM Product WHERE name=:name")
    Product byName(String name);

    @Query("SELECT EXISTS(SELECT * FROM Product WHERE name=:name)")
    boolean containsWithName(String name);

    @Query("SELECT EXISTS(SELECT * FROM Product WHERE category=:category)")
    boolean containsWithCategory(String category);

    @Query("UPDATE Product SET category=:newCategory WHERE category=:oldCategory")
    void updateCategory(String oldCategory, String newCategory);

    @Query("UPDATE Product SET name=:name, category=:category, image=:image WHERE id=:id")
    void update(Integer id, String name, String category, String image);

    @Query("UPDATE Product SET selected=:selected WHERE id=:id")
    void setSelection(Integer id, Boolean selected);

    @Query("UPDATE Product SET inCart=:inCart, selected=:selected WHERE id=:id")
    void moveToCart(Integer id, String inCart, Boolean selected);

    @Insert
    void insert(Product... products);

    @Delete
    void delete(Product product);

    static ProductDao instance(Context context) {
        return AppDatabase.instance(context).productDao();
    }
}