package com.matejrajtar.shoppinglist.views;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.matejrajtar.shoppinglist.R;
import com.matejrajtar.shoppinglist.adapters.CategoryAdapter;
import com.matejrajtar.shoppinglist.base.BaseView;
import com.matejrajtar.shoppinglist.model.Category;
import com.matejrajtar.shoppinglist.views.ManageCategoriesView.ManageCategoriesViewObserver;
import com.matejrajtar.shoppinglist.views.ManageCategoriesView.ViewContainer;
import com.mauriciotogneri.androidutils.uibinder.annotations.BindView;
import com.mauriciotogneri.androidutils.uibinder.annotations.OnClick;

import java.util.List;

public class ManageCategoriesView extends BaseView<ManageCategoriesViewObserver, ViewContainer> {
    private final CategoryAdapter adapter;

    public ManageCategoriesView(Context context, ManageCategoriesViewObserver observer) {
        super(R.layout.screen_manage_categories, observer, new ViewContainer());

        this.adapter = new CategoryAdapter(context);
    }

    @Override
    protected void initialize() {
        toolbarTitle(R.string.toolbar_title_manage_categories);
        enableBack(v -> observer.onBack());

        ui.list.setAdapter(adapter);
        ui.list.setOnItemClickListener((adapterView, view, position, id) ->
        {
            Category category = (Category) adapterView.getItemAtPosition(position);
            observer.onCategorySelected(category);
        });
    }

    public void updateList(List<Category> categories) {

        if (categories.isEmpty()) {
            ui.labelEmpty.setVisibility(View.VISIBLE);
            ui.list.setVisibility(View.GONE);
        } else {
            ui.labelEmpty.setVisibility(View.GONE);
            ui.list.setVisibility(View.VISIBLE);
            adapter.set(categories);
        }
    }

    @OnClick(R.id.category_add)
    public void onAddCategory() {
        observer.onAddCategory();
    }

    public interface ManageCategoriesViewObserver {
        void onCategorySelected(Category category);

        void onAddCategory();

        void onBack();
    }

    public static class ViewContainer {
        @BindView(R.id.category_list)
        public ListView list;

        @BindView(R.id.label_empty)
        public View labelEmpty;
    }
}