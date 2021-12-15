package com.matejrajtar.shoppinglist.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.content.res.AppCompatResources;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.matejrajtar.shoppinglist.R;
import com.matejrajtar.shoppinglist.adapters.ProductAvailableAdapter.ViewHolder;
import com.matejrajtar.shoppinglist.base.BaseListAdapter;
import com.matejrajtar.shoppinglist.base.BaseViewHolder;
import com.matejrajtar.shoppinglist.model.Product;
import com.mauriciotogneri.androidutils.uibinder.annotations.BindView;

public class ProductAvailableAdapter extends BaseListAdapter<Product, ViewHolder> { ;
    private final RequestManager imageLoader;

    public ProductAvailableAdapter(Context context) {
        super(context, R.layout.item_product_available);

        this.imageLoader = Glide.with(context);
    }

    @Override
    protected void fillView(ViewHolder viewHolder, Product product, int position) {
        viewHolder.name.setText(product.name());

        Drawable image = AppCompatResources.getDrawable(this.getContext(), Integer.parseInt(product.image()));

        imageLoader.load(image).into(viewHolder.image);
    }

    @Override
    protected ViewHolder viewHolder(View view) {
        return new ViewHolder(view);
    }

    public static class ViewHolder extends BaseViewHolder {
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.product_image)
        public ImageView image;

        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.product_name)
        public TextView name;

        protected ViewHolder(View view) {
            super(view);
        }
    }
}