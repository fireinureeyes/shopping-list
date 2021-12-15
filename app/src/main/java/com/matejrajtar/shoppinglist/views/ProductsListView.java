package com.matejrajtar.shoppinglist.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.matejrajtar.shoppinglist.R;
import com.matejrajtar.shoppinglist.adapters.ProductAvailableAdapter;
import com.matejrajtar.shoppinglist.base.BaseView;
import com.matejrajtar.shoppinglist.model.Product;
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
    }

    public static class ViewContainer {
        @BindView(R.id.product_list)
        public ListView list;
    }
}