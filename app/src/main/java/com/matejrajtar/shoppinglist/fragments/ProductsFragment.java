package com.matejrajtar.shoppinglist.fragments;

import static android.app.ProgressDialog.show;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;
import com.matejrajtar.shoppinglist.R;
import com.matejrajtar.shoppinglist.base.BaseFragment;
import com.matejrajtar.shoppinglist.database.ProductDao;
import com.matejrajtar.shoppinglist.model.Product;
import com.matejrajtar.shoppinglist.tasks.product.LoadProductsByCategory;
import com.matejrajtar.shoppinglist.tasks.product.LoadProductsByCategory.OnProductsLoaded;
import com.matejrajtar.shoppinglist.tasks.product.UpdateProducts;
import com.matejrajtar.shoppinglist.utils.Analytics;
import com.matejrajtar.shoppinglist.views.ProductsListView;
import com.matejrajtar.shoppinglist.views.ProductsListView.ProductListViewObserver;

import java.util.List;

public class ProductsFragment extends BaseFragment<ProductsListView> implements ProductListViewObserver, OnProductsLoaded {
    private static final String PARAM_CATEGORY = "category";

    public static ProductsFragment create(String category) {
        ProductsFragment fragment = new ProductsFragment();
        Bundle args = new Bundle();
        args.putString(PARAM_CATEGORY, category);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    protected void initialize() {
        reloadProducts();
    }

    @Override
    public void onProductsLoaded(List<Product> products) {
        view.updateList(products);

        TextView sumText = (TextView) getActivity().findViewById(R.id.sum);
        ProductDao dao = ProductDao.instance(getContext());
        Integer i = dao.inCart().size();

        if (String.valueOf(i).equals("1")) {sumText.setText(i+" item");} else {
            sumText.setText(i+" items");}
    }

    public String title() {
        return parameter(PARAM_CATEGORY, "");
    }

    @Override
    public void onProductSelected(Product product) {
        UpdateProducts task = new UpdateProducts(getContext());
        task.moveToCart(product);

        view.removeProduct(product);

        TextView sumText = (TextView) getActivity().findViewById(R.id.sum);
        ProductDao dao = ProductDao.instance(getContext());

        Integer i = dao.inCart().size();

        if (String.valueOf(i).equals("1")) {sumText.setText(i+" item");} else {
            sumText.setText(i+" items");}

        Analytics analytics = new Analytics(getContext());
        analytics.cartItemAdded(product);

        Vibrator vibrator = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            vibrator = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
        }
        // Check if the device has a vibrator
        if (vibrator.hasVibrator()) {
            // Vibrate for 100 milliseconds
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                //deprecated in API 26
                vibrator.vibrate(100);
            }
        }
    }

    @Override
    public void onProductRedone(Product product) {
        UpdateProducts task = new UpdateProducts(getContext());
        task.removeFromCart(product);
    }

    private void reloadProducts() {
        LoadProductsByCategory task = new LoadProductsByCategory(getContext(), parameter(PARAM_CATEGORY, ""), this);
        task.execute();
    }

    @Override
    protected ProductsListView view() {
        return new ProductsListView(getContext(), this);
    }
}