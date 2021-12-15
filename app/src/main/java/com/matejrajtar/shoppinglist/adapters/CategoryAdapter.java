package com.matejrajtar.shoppinglist.adapters;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.matejrajtar.shoppinglist.R;
import com.matejrajtar.shoppinglist.adapters.CategoryAdapter.ViewHolder;
import com.matejrajtar.shoppinglist.base.BaseListAdapter;
import com.matejrajtar.shoppinglist.base.BaseViewHolder;
import com.matejrajtar.shoppinglist.model.Category;
import com.mauriciotogneri.androidutils.uibinder.annotations.BindView;

public class CategoryAdapter extends BaseListAdapter<Category, ViewHolder> {
    public CategoryAdapter(Context context) {
        super(context, R.layout.item_category);
    }

    @Override
    protected void fillView(ViewHolder viewHolder, Category category, int position) {
        viewHolder.name.setText(category.name());
    }

    @Override
    protected ViewHolder viewHolder(View view) {
        return new ViewHolder(view);
    }

    public static class ViewHolder extends BaseViewHolder {
        @BindView(R.id.category_name)
        public TextView name;

        protected ViewHolder(View view) {
            super(view);
        }
    }
}