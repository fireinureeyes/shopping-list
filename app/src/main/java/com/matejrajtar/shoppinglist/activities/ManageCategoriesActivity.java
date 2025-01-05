package com.matejrajtar.shoppinglist.activities;

import com.matejrajtar.shoppinglist.R;
import com.matejrajtar.shoppinglist.base.BaseActivity;
import com.matejrajtar.shoppinglist.model.Category;
import com.matejrajtar.shoppinglist.tasks.category.AddCategory;
import com.matejrajtar.shoppinglist.tasks.category.AddCategory.OnCategoryAdded;
import com.matejrajtar.shoppinglist.tasks.category.DeleteCategory;
import com.matejrajtar.shoppinglist.tasks.category.DeleteCategory.OnCategoryDeleted;
import com.matejrajtar.shoppinglist.tasks.category.LoadCategories;
import com.matejrajtar.shoppinglist.tasks.category.LoadCategories.OnCategoriesLoaded;
import com.matejrajtar.shoppinglist.tasks.category.RenameCategory;
import com.matejrajtar.shoppinglist.tasks.category.RenameCategory.OnCategoryRenamed;
import com.matejrajtar.shoppinglist.views.Dialogs;
import com.matejrajtar.shoppinglist.views.ManageCategoriesView;
import com.matejrajtar.shoppinglist.views.ManageCategoriesView.ManageCategoriesViewObserver;

import java.util.Arrays;
import java.util.List;

public class ManageCategoriesActivity extends BaseActivity<ManageCategoriesView> implements ManageCategoriesViewObserver, OnCategoriesLoaded, OnCategoryAdded, OnCategoryDeleted, OnCategoryRenamed {
    @Override
    protected void initialize() {
        reloadCategories();
    }

    private void reloadCategories() {
        LoadCategories task = new LoadCategories(this, this);
        task.execute();
    }

    @Override
    public void onCategoriesLoaded(List<Category> categories) {
        view.updateList(categories);
    }

    @Override
    public void onCategorySelected(Category category) {
        List<String> options = Arrays.asList(
                getString(R.string.button_rename),
                getString(R.string.button_remove)
        );

        Dialogs dialogs = new Dialogs(this);
        dialogs.options(category.name(), options, option ->
        {
            if (option == 0) {
                requestCategoryName(category);
            } else if (option == 1) {
                confirmRemoveCategory(category);
            }
        });
    }

    private void requestCategoryName(Category category) {
        Dialogs dialogs = new Dialogs(this);
        dialogs.input(this, getString(R.string.label_product_edit_category), category.name(), input -> renameCategory(category, input));
    }

    private void renameCategory(Category category, String newName) {
        RenameCategory task = new RenameCategory(this, category, newName, this);
        task.execute();
    }

    @Override
    public void onCategoryRenamed(Boolean result) {
        if (result) {
            reloadCategories();
        } else {
            toast(R.string.error_category_already_exists);
        }
    }

    private void confirmRemoveCategory(Category category) {
        Dialogs dialogs = new Dialogs(this);
        dialogs.confirmation(category.name(), getString(R.string.dialog_remove_category), () -> removeCategory(category));
    }

    private void removeCategory(Category category) {
        DeleteCategory task = new DeleteCategory(this, category, this);
        task.execute();
    }

    @Override
    public void onCategoryDeleted(Boolean result) {
        if (result) {
            reloadCategories();
        } else {
            toast(R.string.error_category_in_use);
        }
    }

    @Override
    public void onAddCategory() {
        Dialogs dialogs = new Dialogs(this);
        dialogs.input(this, getString(R.string.label_product_new_category), "", this::addCategory);
    }

    private void addCategory(String name) {
        AddCategory task = new AddCategory(this, new Category(name), this);
        task.execute();
    }

    @Override
    public void onCategoryAdded(Boolean result) {
        if (result) {
            reloadCategories();
        } else {
            toast(R.string.error_category_already_exists);
        }
    }

    @Override
    public void onBack() {
        finish();
    }

    @Override
    protected ManageCategoriesView view() {
        return new ManageCategoriesView(this, this);
    }
}