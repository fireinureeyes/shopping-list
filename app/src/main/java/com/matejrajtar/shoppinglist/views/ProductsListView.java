package com.matejrajtar.shoppinglist.views;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.matejrajtar.shoppinglist.R;
import com.matejrajtar.shoppinglist.adapters.ProductAvailableAdapter;
import com.matejrajtar.shoppinglist.base.BaseView;
import com.matejrajtar.shoppinglist.model.Product;
import com.matejrajtar.shoppinglist.tasks.product.UpdateProducts;
import com.matejrajtar.shoppinglist.views.ProductsListView.ProductListViewObserver;
import com.matejrajtar.shoppinglist.views.ProductsListView.ViewContainer;
import com.mauriciotogneri.androidutils.uibinder.annotations.BindView;

import java.util.List;

public class ProductsListView extends BaseView<ProductListViewObserver, ViewContainer> {
    private final ProductAvailableAdapter adapter;

    public ProductsListView(Context context, ProductListViewObserver observer) {
        super(R.layout.view_category_products, observer, new ViewContainer());

        this.adapter = new ProductAvailableAdapter(context);
    }

    @Override
    protected void initialize() {
        ui.list.setAdapter(adapter);
        ui.list.setOnItemClickListener((adapterView, view, position, id) ->
        {
            Product product = (Product) adapterView.getItemAtPosition(position);
            observer.onProductSelected(product);

            Snackbar snackbar = Snackbar.make(view,"Added " + product.name + " to cart", Snackbar.LENGTH_LONG);
            snackbar.setAction("Undo", v -> {
                UpdateProducts task = new UpdateProducts(context().getApplicationContext());
                task.removeFromCart(product);
                adapter.insert(product,position);
                observer.onProductRedone(product);
                if (view.getContext() instanceof Activity){
                    Activity host = (Activity) view.getContext();
                    TextView sumText = (TextView) host.findViewById(R.id.sum);
                    CharSequence text = sumText.getText();
                    String numberOnly= text.toString().replaceAll("[^0-9]", "");
                    Integer i = Integer.parseInt(numberOnly);
                    if (i>0) i--;
                    if (String.valueOf(i).equals("1")) {sumText.setText(i+" item");} else {
                        sumText.setText(i+" items");}
                }
            });
            snackbar.show();
        });

        View footer = LayoutInflater.from(context()).inflate(R.layout.view_footer, null);
        ui.list.addFooterView(footer, null, false);
    }

    public void updateList(List<Product> products) {
        adapter.set(products);
    }

    public void removeProduct(Product product) {
        adapter.remove(product);
    }

    public interface ProductListViewObserver {
        void onProductSelected(Product product);
        void onProductRedone(Product product);
    }

    public static class ViewContainer {
        @BindView(R.id.product_list)
        public ListView list;
    }
}