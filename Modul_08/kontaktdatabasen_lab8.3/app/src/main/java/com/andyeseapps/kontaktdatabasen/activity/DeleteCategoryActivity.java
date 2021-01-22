package com.andyeseapps.kontaktdatabasen.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.andyeseapps.kontaktdatabasen.R;
import com.andyeseapps.kontaktdatabasen.RecyclerItemClickListener;
import com.andyeseapps.kontaktdatabasen.Utils;
import com.andyeseapps.kontaktdatabasen.adapter.CategoryListAdapter;
import com.andyeseapps.kontaktdatabasen.viewmodel.CategoryViewModel;

public class DeleteCategoryActivity extends AppCompatActivity {
    private AlertDialog alertDialogDeletion;
    public static final String EXTRA_REPLY = "categoryID";
    private int categoryID;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_category);

        setUpAlertDialogForDeletion();
        setUpRecyclerViewWithCategoriesAndListener();

        Utils.setToolbar(this);
    }

    /**
     * Sets up alert-dialog for deletion
     */
    private void setUpAlertDialogForDeletion() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.alert_dialog_delete_contact));
        builder.setCancelable(true);

        builder.setPositiveButton(R.string.yes, (dialog, which) -> {
            Intent replyIntent = new Intent();
            replyIntent.putExtra(EXTRA_REPLY, categoryID);
            setResult(RESULT_OK, replyIntent);
            dialog.cancel();
            finish();
        });
        builder.setNegativeButton(R.string.no, (dialog, which) -> {
            dialog.cancel();
            finish();
        });
        alertDialogDeletion = builder.create();
    }

    /**
     * Populates and displays RecyclerView with categories
     */
    private void setUpRecyclerViewWithCategoriesAndListener() {
        RecyclerView recyclerView = findViewById(R.id.delete_recyclerview_category);
        final CategoryListAdapter categoryListAdapter = new CategoryListAdapter(this);
        recyclerView.setAdapter(categoryListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        CategoryViewModel mCategoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
        mCategoryViewModel.getAllCategories().observe(this, categoryListAdapter::setCategories);

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        categoryID = categoryListAdapter.getItem(position).getCategoryID();
                        if (categoryID != 1)
                            alertDialogDeletion.show();
                        else
                            Utils.displayToast(DeleteCategoryActivity.this, getString(R.string.delete_category_illegal) + " " + categoryListAdapter.getItem(position).getDescription());
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                    }
                })
        );
    }
}
