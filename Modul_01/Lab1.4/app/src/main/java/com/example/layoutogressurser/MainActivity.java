package com.example.layoutogressurser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Set TextView text from "app/res/values/heltall.xlm" resource...
        Resources res = getResources();
        int birthyear = res.getInteger(R.integer.birthyear);
        TextView textView = findViewById(R.id.etBirthyear);
        textView.setText(Integer.toString(birthyear));
    }
}