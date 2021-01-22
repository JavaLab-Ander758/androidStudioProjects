package com.andyeseapps.kontaktdatabasen.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.andyeseapps.kontaktdatabasen.R;
import com.andyeseapps.kontaktdatabasen.Utils;
import com.andyeseapps.kontaktdatabasen.entities.Category;

public class NewCategoryActivity extends AppCompatActivity {
    public static final String EXTRA_REPLY = "newCategory";
    private EditText etDescription;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_category);

        setUpSaveButton();

        Utils.setToolbar(this);
    }

    /**
     * Sets up the save Button
     */
    private void setUpSaveButton() {
        final Button button = findViewById(R.id.button_save_category);
        button.setOnClickListener(view -> {
                Intent replyIntent = new Intent();
                if (checkIfFieldsAreFilled()) {
                    replyIntent.putExtra(EXTRA_REPLY, getCategoryFromInput());
                    setResult(RESULT_OK, replyIntent);
                    Log.d("Testing", "OK");
                } else {
                    setResult(RESULT_CANCELED, replyIntent);
                    Log.d("Testing", "NOT OK");
                }
                finish();
        });
    }

    /**
     * Checks if input-fields are filled
     * @return boolean
     */
    private boolean checkIfFieldsAreFilled() {
        etDescription = findViewById(R.id.et_category_description);
        return !etDescription.getText().toString().matches("");
    }

    /**
     * Generates and returns Category object form input-fields
     * @return Category object
     */
    private Category getCategoryFromInput() {
        Log.d("Testing", "getCategoryFromInput()");
        return new Category(etDescription.getText().toString());
    }
}
