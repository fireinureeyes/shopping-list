package com.matejrajtar.shoppinglist.model;

import android.text.TextUtils;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Product implements Serializable {
    @PrimaryKey
    public Integer id;

    @ColumnInfo
    public String category;

    @ColumnInfo
    public String name;

    @ColumnInfo
    public String image;

    @ColumnInfo
    public String inCart;

    @ColumnInfo
    public Boolean selected;

    public Product(String category, String name, String image, String inCart, Boolean selected) {
        this.name = name;
        this.category = category;
        this.image = image;
        this.inCart = inCart;
        this.selected = selected;
    }

    public Integer id() {
        return id;
    }

    public String category() {
        return category;
    }

    public String name() {
        return name;
    }

    public String image() {
        return image;
    }

    public String isInCart() {
        return inCart;
    }

    public boolean isSelected() {
        return selected;
    }

    public void toggleSelection() {
        selected = !selected;
    }

    public boolean isValid() {
        return (!TextUtils.isEmpty(category) && !TextUtils.isEmpty(name));
    }

    public CartElement cartElement() {
        return new CartElement(
                id,
                category,
                name,
                image
        );
    }
}