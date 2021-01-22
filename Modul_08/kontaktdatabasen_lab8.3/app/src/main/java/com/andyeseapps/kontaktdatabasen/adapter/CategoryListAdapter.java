package com.andyeseapps.kontaktdatabasen.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.andyeseapps.kontaktdatabasen.R;
import com.andyeseapps.kontaktdatabasen.entities.Category;

import java.io.Serializable;
import java.util.List;

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.CategoryViewHolder> {

    class CategoryViewHolder extends RecyclerView.ViewHolder {
        private final TextView categoryItemView;

        private CategoryViewHolder(View itemView) {
            super(itemView);
            categoryItemView = itemView.findViewById(R.id.textView);
        }
    }

    private final LayoutInflater mInflater;
    private List<Category> mCategories; // Cached copy of categories

    public CategoryListAdapter(Context context) { mInflater = LayoutInflater.from(context); }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new CategoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder holder, int position) {
        if (mCategories != null) {
            Category current = mCategories.get(position);
            holder.categoryItemView.setText(current.getDescription());
        } else {
            // Covers the case of data not being ready yet.
            holder.categoryItemView.setText("No Word");
        }
    }

    public void setCategories(List<Category> categories){
        mCategories = categories;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mCategories != null)
            return mCategories.size();
        else return 0;
    }

    public Category getItem(int position) {
        return mCategories.get(position);
    }

    public List<Category> getAllCategories() {
        return mCategories;
    }

}
