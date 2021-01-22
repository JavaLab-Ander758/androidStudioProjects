package com.andyeseapps.kontaktdatabasen.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;

import com.andyeseapps.kontaktdatabasen.R;
import com.andyeseapps.kontaktdatabasen.entities.Category;

import java.util.List;

public class CategorySpinnerAdapter extends ArrayAdapter<Category> {

    private int layoutResource;

    public CategorySpinnerAdapter(Context context, int layoutResource, List<Category> items) {
        super(context, layoutResource, items);
        this.layoutResource = layoutResource;
    }

    //NB!! Denne gjør at elementene i lista tegnes som ikoner.
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCategoryView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCategoryView(position, convertView, parent);
    }

    public View getCategoryView(int position, View convertView, ViewGroup parent) {
        LinearLayout categoryView;
        if (convertView == null) {
            categoryView = new LinearLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater vi = (LayoutInflater)getContext().getSystemService(inflater);
            vi.inflate(this.layoutResource, categoryView, true);
        } else {
            //Gjenbruker vha. convertView:
            categoryView = (LinearLayout) convertView;
        }

        Category categoryItem = getItem(position);
        String title = categoryItem.getDescription();
        TextView titleView = categoryView.findViewById(R.id.tvTitle);
        titleView.setText(title);

        ImageView iconView = categoryView.findViewById(R.id.ivCategoryIcon);
        int imageId = R.drawable.ic_people_black_24dp;
        Drawable drawable = ResourcesCompat.getDrawable(this.getContext().getResources(), imageId, null);
        iconView.setImageDrawable(drawable);

        return categoryView;
    }


}
