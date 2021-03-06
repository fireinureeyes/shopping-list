package com.matejrajtar.shoppinglist.views;

import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputLayout;
import com.matejrajtar.shoppinglist.R;
import com.matejrajtar.shoppinglist.base.BaseView;
import com.matejrajtar.shoppinglist.model.Category;
import com.matejrajtar.shoppinglist.model.Product;
import com.matejrajtar.shoppinglist.views.CreateProductView.CreateProductViewObserver;
import com.matejrajtar.shoppinglist.views.CreateProductView.ViewContainer;
import com.mauriciotogneri.androidutils.uibinder.annotations.BindView;
import com.mauriciotogneri.androidutils.uibinder.annotations.OnClick;

import java.util.List;

public class CreateProductView extends BaseView<CreateProductViewObserver, ViewContainer> {
    private static final String DEFAULT_IMAGE = "https://i.imgur.com/ztA411S.png";

    private String selectedImage;

    public CreateProductView(CreateProductViewObserver observer) {
        super(R.layout.screen_create_product, observer, new ViewContainer());
    }

    public void initialize(boolean isNew) {
        enableBack(v -> observer.onBack());

        if (isNew) {
            toolbarTitle(R.string.toolbar_title_create_product);
            ui.buttonAction.setText(R.string.button_create);
            image(DEFAULT_IMAGE);
        } else {
            ui.inCart.setChecked(false);
            ui.inCart.setVisibility(View.GONE);

            toolbarTitle(R.string.toolbar_title_edit_product);
            ui.buttonAction.setText(R.string.button_update);
        }
    }

    public void load(List<Category> categories, String category, Product product) {
        ArrayAdapter<Category> adapter = new ArrayAdapter<>(context(), android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ui.category.setAdapter(adapter);

        if (product != null) {
            ui.category.setSelection(categories.indexOf(new Category(product.category())));
            ui.name.setText(product.name());
            image(product.image());
        } else if (!TextUtils.isEmpty(category)) {
            ui.category.setSelection(categories.indexOf(new Category(category)));
        }
    }

    @OnClick(R.id.product_image)
    public void onProductImage() {
        observer.onChangeImage();
    }

    @OnClick(R.id.product_image_change)
    public void onChangeImage() {
        observer.onChangeImage();
    }

    @OnClick(R.id.category_manage)
    public void onManageCategories() {
        observer.onManageCategories();
    }

    @OnClick(R.id.button_action)
    public void onAction() {
        //ui.inCart.isChecked()
        observer.onAction(category(), name(), selectedImage, "0");
    }

    private String category() {
        Object element = ui.category.getSelectedItem();

        return (element != null) ? element.toString() : "";
    }

    public String name() {
        return ui.name.getText().toString();
    }

    public void image(String image) {
        selectedImage = image;


        Glide.with(context())
                .load(image)
                .into(ui.image);
    }

    public void clearError() {
        ui.nameHeader.setError("");
    }

    public void missingName() {
        ui.nameHeader.setError(context().getString(R.string.error_invalid_name));
    }

    public interface CreateProductViewObserver {
        void onBack();

        void onManageCategories();

        void onChangeImage();

        void onAction(String category, String name, String image, String inCart);
    }

    public static class ViewContainer {
        @BindView(R.id.name_header)
        public TextInputLayout nameHeader;

        @BindView(R.id.name)
        public EditText name;

        @BindView(R.id.category)
        public Spinner category;

        @BindView(R.id.product_image)
        public ImageView image;

        @BindView(R.id.product_addToCard)
        public CheckBox inCart;

        @BindView(R.id.button_action)
        public Button buttonAction;
    }
}