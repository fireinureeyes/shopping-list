package com.matejrajtar.shoppinglist.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.content.res.AppCompatResources;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.matejrajtar.shoppinglist.R;
import com.matejrajtar.shoppinglist.activities.CartActivity;
import com.matejrajtar.shoppinglist.adapters.ProductInCartAdapter.ViewHolder;
import com.matejrajtar.shoppinglist.base.BaseListAdapter;
import com.matejrajtar.shoppinglist.base.BaseViewHolder;
import com.matejrajtar.shoppinglist.database.AppDatabase;
import com.matejrajtar.shoppinglist.database.ProductDao;
import com.matejrajtar.shoppinglist.model.Product;
import com.mauriciotogneri.androidutils.uibinder.annotations.BindView;

public class ProductInCartAdapter extends BaseListAdapter<Product, ViewHolder> {
    private final RequestManager imageLoader;
    private final ProductDao productDao;
    private final Context context;

    private DatabaseReference dbRef;

    public ProductInCartAdapter(Context context) {
        super(context, R.layout.item_product_in_cart);

        this.imageLoader = Glide.with(context);

        this.context = context;

        AppDatabase database = AppDatabase.instance(context);

        productDao = database.productDao();

        dbRef = FirebaseDatabase.getInstance(context.getString(R.string.database_url)).getReference("items");
    }

    @Override
    protected void fillView(ViewHolder viewHolder, Product product, int position) {
        if (product.isSelected()) {
            viewHolder.row.setBackgroundColor(color(R.color.item_selected));
            viewHolder.name.setPaintFlags(viewHolder.name.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            viewHolder.row.setBackground(null);
            viewHolder.name.setPaintFlags(viewHolder.name.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }

        viewHolder.name.setText(product.name());

        if (product.isInCart().equals("1")) {
            viewHolder.edit.setText("", TextView.BufferType.EDITABLE);
        } else {
            viewHolder.edit.setText(product.isInCart(), TextView.BufferType.EDITABLE);
        }

        Drawable image = AppCompatResources.getDrawable(this.getContext(), Integer.parseInt(product.image()));

        imageLoader.load(image).into(viewHolder.image);

        //handle saving custom notes in cart
        viewHolder.edit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    if (context.getClass().equals(CartActivity.class)) {
                        ((CartActivity) context).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
                    }
                } else {
                    if (context.getClass().equals(CartActivity.class)) {
                        ((CartActivity) context).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
                    }

                    productDao.moveToCart(product.id(), String.valueOf(viewHolder.edit.getText()), false);
                    dbRef.child(product.id().toString()).setValue(String.valueOf(viewHolder.edit.getText()));

                    InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    viewHolder.edit.clearFocus();
                }
            }
        });

        //remove item from cart on press
        viewHolder.row.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    if (context.getClass().equals(CartActivity.class)) {
                        ((CartActivity) context).onProductSelected(product);
                    }
                }
            }
        });

        //handle pressing enter on soft keyboard to close
        viewHolder.edit.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_ENTER) {
                        viewHolder.edit.clearFocus();
                        return true;
                    }
                }
                return false;
            }
        });
    }

    //prioritize categories for sorting
    private Integer getPrio(String category) {
        switch (category) {
            case "Fruits & Veggies":
                return 1;
            case "Bakery":
                return 2;
            case "Sweet Pantry":
                return 3;
            case "Drinks":
                return 4;
            case "Seasoning":
                return 5;
            case "Dairy & Cooled":
                return 6;
            case "Savory Pantry":
                return 7;
            case "Household":
                return 8;
            default:
                return 0;
        }
    }

    public void sortList() {
        sort((p1, p2) ->
        {
            Integer one = getPrio(p1.category());
            Integer two = getPrio(p2.category());

            if (!p1.category().equals(p2.category())) {
                return one.compareTo(two);
            } else {
                //sort alphabetically by item name
                return p1.name().compareTo(p2.name());
            }
        });
    }

    @Override
    protected ViewHolder viewHolder(View view) {
        return new ViewHolder(view);
    }


    public static class ViewHolder extends BaseViewHolder {
        @BindView(R.id.product_row)
        public View row;

        @BindView(R.id.product_image)
        public ImageView image;

        @BindView(R.id.product_name)
        public TextView name;

        @BindView(R.id.editText)
        public TextView edit;

        protected ViewHolder(View view) {
            super(view);
        }
    }
}