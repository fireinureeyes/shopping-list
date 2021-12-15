package com.matejrajtar.shoppinglist.views;

import android.content.Context;
import android.view.View;
import android.widget.ListView;

import com.matejrajtar.shoppinglist.R;
import com.matejrajtar.shoppinglist.adapters.ProductInCartAdapter;
import com.matejrajtar.shoppinglist.base.BaseView;
import com.matejrajtar.shoppinglist.model.Product;
import com.matejrajtar.shoppinglist.views.CartView.CartViewObserver;
import com.matejrajtar.shoppinglist.views.CartView.ViewContainer;
import com.mauriciotogneri.androidutils.uibinder.annotations.BindView;
import com.mauriciotogneri.androidutils.uibinder.annotations.OnClick;

import java.util.ArrayList;
import java.util.List;

public class CartView extends BaseView<CartViewObserver, ViewContainer> {
    private final ProductInCartAdapter adapter;

    public CartView(Context context, CartViewObserver observer) {
        super(R.layout.screen_cart, observer, new ViewContainer());

        this.adapter = new ProductInCartAdapter(context);
    }

    @Override
    protected void initialize() {
        toolbarTitle(R.string.toolbar_title_main);

        ui.list.setAdapter(adapter);
        ui.list.setOnItemClickListener((adapterView, view, position, id) ->
        {
            Product product = (Product) adapterView.getItemAtPosition(position);
            observer.onProductSelected(product);
        });
    }

    public void updateList(List<Product> products) {
        if (products.isEmpty()) {
            ui.labelEmpty.setVisibility(View.VISIBLE);
            ui.list.setVisibility(View.GONE);
        } else {
            ui.labelEmpty.setVisibility(View.GONE);
            ui.list.setVisibility(View.VISIBLE);
            adapter.set(products);
            adapter.sortList();
            enableToolbarAction(R.drawable.ic_share, v -> observer.onShare(products));
        }
    }

    public List<Product> selectedProducts() {
        List<Product> products = new ArrayList<>();

        for (int i = 0; i < adapter.getCount(); i++) {
            Product product = adapter.getItem(i);

            if ((product != null) && product.isSelected()) {
                products.add(product);
            }
        }

        return products;
    }

    public void updateList() {
        adapter.sortList();
        adapter.update();
    }

    @OnClick(R.id.product_add)
    public void onAddProduct() {
        observer.onAddProduct();
    }

    public interface CartViewObserver {
        void onProductSelected(Product product);

        void onShare(List<Product> products);

        void onAddProduct();
    }

    public static class ViewContainer {
        @BindView(R.id.product_list)
        public ListView list;

        @BindView(R.id.label_empty)
        public View labelEmpty;
    }
}