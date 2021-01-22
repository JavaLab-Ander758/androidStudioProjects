package com.andyeseapps.kontaktdatabasen.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.andyeseapps.kontaktdatabasen.R;
import com.andyeseapps.kontaktdatabasen.RecyclerItemClickListener;
import com.andyeseapps.kontaktdatabasen.Utils;
import com.andyeseapps.kontaktdatabasen.adapter.CategoryListAdapter;
import com.andyeseapps.kontaktdatabasen.adapter.CategorySpinnerAdapter;
import com.andyeseapps.kontaktdatabasen.adapter.ContactListAdapter;
import com.andyeseapps.kontaktdatabasen.database.CategoryRoomDatabase;
import com.andyeseapps.kontaktdatabasen.entities.Category;
import com.andyeseapps.kontaktdatabasen.entities.Contact;
import com.andyeseapps.kontaktdatabasen.viewmodel.CategoryViewModel;
import com.andyeseapps.kontaktdatabasen.viewmodel.ContactViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static final int NEW_CONTACT_ACTIVITY_REQUEST_CODE = 1, DELETE_CONTACT_ACTIVITY_REQUEST_CODE = 2, NEW_CATEGORY_ACTIVITY_REQUEST_CODE = 3, DELETE_CATEGORY_ACTIVITY_REQUEST_CODE = 4;
    public static ContactViewModel mContactViewModel;
    private CategoryViewModel mCategoryViewModel;
    public static ContactListAdapter contactListAdapter;
    public static CategoryListAdapter categoryListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpFloatingActionButtons();
        setUpContactRecyclerView(-1);
        setUpCategoryAdapter();
        setUpCategorySpinner();

        setSupportActionBar(findViewById(R.id.my_toolbar));
    }

    /**
     * Injects to database from various activities by intents and specific defined codes
     *
     * @param requestCode Defined code to request
     * @param resultCode  Defined code to receive
     * @param data        Received intent to work with
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_CONTACT_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK)
            mContactViewModel.insert((Contact) data.getSerializableExtra(NewContactActivity.EXTRA_REPLY));
        else if (requestCode == DELETE_CONTACT_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK)
            mContactViewModel.deleteContactWithID(data.getIntExtra(DeleteContactActivity.EXTRA_REPLY, -1));
        else if (requestCode == NEW_CATEGORY_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK)
            mCategoryViewModel.insert((Category) data.getSerializableExtra(NewCategoryActivity.EXTRA_REPLY));
        else if (requestCode == DELETE_CATEGORY_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            int categoryID = data.getIntExtra(DeleteCategoryActivity.EXTRA_REPLY, -1);
            mCategoryViewModel.deleteCategoryWithID(categoryID);
            mContactViewModel.deleteContactByCategoryID(categoryID);
        }
        else
            Utils.displayToast(this, getString(R.string.empty_not_saved));
    }

    /**
     * Sets up categoryListAdapter
     */
    private void setUpCategoryAdapter() {
        categoryListAdapter = new CategoryListAdapter(this);
        mCategoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
        mCategoryViewModel.getAllCategories().observe(this, categories -> categoryListAdapter.setCategories(categories));
    }

    /**
     * Populates RecyclerView with Contact objects with given categoryID
     * Parameter -1 displays all contacts in all categories
     *
     * @param categoryId contacts' categoryId
     */
    private void setUpContactRecyclerView(int categoryId) {
        RecyclerView recyclerView = findViewById(R.id.main_recyclerview);
        contactListAdapter = new ContactListAdapter(this);
        recyclerView.setAdapter(contactListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mContactViewModel = new ViewModelProvider(this).get(ContactViewModel.class);
        if (categoryId == -1)
            mContactViewModel.getAllContacts().observe(this, contactListAdapter::setContacts);
        else
            mContactViewModel.getContactsFromCategory(categoryId).observe(this, contactListAdapter::setContacts);
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent replyIntent = new Intent(MainActivity.this, DisplayContactActivity.class);
                        replyIntent.putExtra(DisplayContactActivity.EXTRA_REPLY, contactListAdapter.getItem(position));
                        startActivity(replyIntent);
                    }
                    @Override
                    public void onLongItemClick(View view, int position) { }
                })
        );
    }

    /**
     * Sets up all FloatingActionButtons
     */
    private void setUpFloatingActionButtons() {
        FloatingActionButton fabNewContact = findViewById(R.id.fabAddContact);
        fabNewContact.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, NewContactActivity.class);
            startActivityForResult(intent, NEW_CONTACT_ACTIVITY_REQUEST_CODE);
        });

        FloatingActionButton fabDeleteContact = findViewById(R.id.fabDeleteContact);
        fabDeleteContact.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, DeleteContactActivity.class);
            startActivityForResult(intent, DELETE_CONTACT_ACTIVITY_REQUEST_CODE);
        });

        FloatingActionButton fabNewCategory = findViewById(R.id.fabAddCategory);
        fabNewCategory.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, NewCategoryActivity.class);
            startActivityForResult(intent, NEW_CATEGORY_ACTIVITY_REQUEST_CODE);
        });

        FloatingActionButton fabDeleteCategory = findViewById(R.id.fabDeleteCategory);
        fabDeleteCategory.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, DeleteCategoryActivity.class);
            startActivityForResult(intent, DELETE_CATEGORY_ACTIVITY_REQUEST_CODE);
        });

    }

    /**
     * Initializes MainActivity with spinner
     * Later on populate with descriptions from category_table
     */
    private void setUpCategorySpinner() {
        CategorySpinnerAdapter categorySpinnerAdapter;
        Spinner spinner = findViewById(R.id.spinner_existing_categories);
        ArrayList<Category> categoryArrayList = new ArrayList<>();
        CategoryViewModel categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);

        categorySpinnerAdapter = new CategorySpinnerAdapter(this, R.layout.category_item_spinner, categoryArrayList);
        spinner.setAdapter(categorySpinnerAdapter);
        categoryViewModel.getAllCategories().observe(this, categories -> {
            categoryArrayList.clear();
            categoryArrayList.addAll(categories);
            categorySpinnerAdapter.notifyDataSetChanged();
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Category category = categoryArrayList.get(position);

                if (category.getDescription().equals(CategoryRoomDatabase.INITIAL_CATEGORY_DESCRIPTION))
                    setUpContactRecyclerView(-1);
                else
                    setUpContactRecyclerView(category.getCategoryID());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
    }
}
