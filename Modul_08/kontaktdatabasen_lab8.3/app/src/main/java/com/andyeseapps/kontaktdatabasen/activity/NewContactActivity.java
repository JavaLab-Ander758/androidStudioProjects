package com.andyeseapps.kontaktdatabasen.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.andyeseapps.kontaktdatabasen.R;
import com.andyeseapps.kontaktdatabasen.Utils;
import com.andyeseapps.kontaktdatabasen.adapter.CategorySpinnerAdapter;
import com.andyeseapps.kontaktdatabasen.entities.Category;
import com.andyeseapps.kontaktdatabasen.entities.Contact;
import com.andyeseapps.kontaktdatabasen.viewmodel.CategoryViewModel;

import java.util.ArrayList;

public class NewContactActivity extends AppCompatActivity {
    public static final String EXTRA_REPLY = "newContact";
    private int categoryId;
    private EditText[] etFields;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_contact);

        setUpSaveButton();
        setUpCategorySpinner();

        Utils.setToolbar(this);
    }

    /**
     * Checks if any input fields are empty
     *
     * @return boolean
     */
    private boolean checkIfFieldsAreFilled() {
        // Get all EditText-fields
        EditText etLastName, etFirstName, etMail, etPhoneNumber, etBirthYear;
        etLastName = findViewById(R.id.et_contact_lastname);
        etFirstName = findViewById(R.id.et_contact_firstname);
        etMail = findViewById(R.id.et_contact_email);
        etPhoneNumber = findViewById(R.id.et_contact_phonenumber);
        etBirthYear = findViewById(R.id.et_contact_birthyear);

        // Check if fields are empty
        etFields = new EditText[]{etLastName, etFirstName, etMail, etPhoneNumber, etBirthYear};
        for (EditText editText : etFields) {
            if (editText.getText().toString().matches(""))
                return false;
        }
        return true;
    }

    /**
     * Generates a Contact object from the EditText fields
     * Returns null if any field empty
     *
     * @return Contact
     */
    private Contact getContactFromInput() {
        return new Contact(etFields[0].getText().toString(), etFields[1].getText().toString(),
                etFields[2].getText().toString(), etFields[3].getText().toString(),
                Integer.parseInt(etFields[4].getText().toString()), categoryId);
    }

    /**
     * Sets up spinner with Categories
     */
    private void setUpCategorySpinner() {
        CategorySpinnerAdapter categorySpinnerAdapter;
        Spinner spinner = findViewById(R.id.spinner_existing_categories_new_contacs);
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
                categoryId = categoryArrayList.get(position).getCategoryID();
                Utils.displayToast(NewContactActivity.this, "" + categoryId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
    }

    /**
     * Sets up the save Button
     */
    private void setUpSaveButton() {
        final Button button = findViewById(R.id.button_save_contact);
        button.setOnClickListener(view -> {
            if (categoryId != 1) {
                Intent replyIntent = new Intent();
                if (checkIfFieldsAreFilled()) {
                    replyIntent.putExtra(EXTRA_REPLY, getContactFromInput());
                    setResult(RESULT_OK, replyIntent);
                } else
                    setResult(RESULT_CANCELED, replyIntent);
                finish();
            } else
                Utils.displayToast(NewContactActivity.this, getString(R.string.new_contact_illegal));
        });
    }
}