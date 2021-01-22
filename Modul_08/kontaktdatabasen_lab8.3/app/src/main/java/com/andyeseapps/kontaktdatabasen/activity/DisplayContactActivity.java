package com.andyeseapps.kontaktdatabasen.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.andyeseapps.kontaktdatabasen.R;
import com.andyeseapps.kontaktdatabasen.Utils;
import com.andyeseapps.kontaktdatabasen.entities.Category;
import com.andyeseapps.kontaktdatabasen.entities.Contact;

public class DisplayContactActivity extends AppCompatActivity {
    private Contact contact;
    public static final String EXTRA_REPLY = "ContactIntent";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_contact);

        contact = (Contact) getIntent().getSerializableExtra(EXTRA_REPLY);
        Utils.setToolbar(this);
        setUpTextViews();
    }


    private void setUpTextViews() {
        TextView tvFirstName = findViewById(R.id.tv_contact_firstname);
        tvFirstName.setText(contact.getFirstName());

        TextView tvLastName = findViewById(R.id.tv_contact_lastname);
        tvLastName.setText(contact.getLastName());

        TextView tvEmail = findViewById(R.id.tv_contact_email);
        tvEmail.setText(contact.getEmail());

        TextView tvPhoneNumber = findViewById(R.id.tv_contact_phonenumber);
        tvPhoneNumber.setText(contact.getPhoneNumber());
        tvPhoneNumber.setClickable(true);
        tvPhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + contact.getPhoneNumber()));
                callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if (ActivityCompat.checkSelfPermission(DisplayContactActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivity(callIntent);
            }
        });

        TextView tvBirthYear = findViewById(R.id.tv_contact_birthyear);
        tvBirthYear.setText(String.valueOf(contact.getBirthYear()));
    }

}
