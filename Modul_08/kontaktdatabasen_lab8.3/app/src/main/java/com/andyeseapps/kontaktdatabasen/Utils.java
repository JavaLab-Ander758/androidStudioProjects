package com.andyeseapps.kontaktdatabasen;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class Utils {
    private static final String ERROR_LOG_NAME = "error_log";

    /**
     * Displays a Toast on given Activity
     *
     * @param activity Activity to display Toast on
     * @param output   String to use in Toast
     */
    public static void displayToast(Activity activity, String output) {
        Toast.makeText(activity, output, Toast.LENGTH_SHORT).show();
    }

    /**
     * Sets up toolbar and back-button for given AppCompatActivity
     * @param appCompatActivity Passed from Activity as 'this'
     */
    public static void setToolbar(AppCompatActivity appCompatActivity) {
        try {
            appCompatActivity.setSupportActionBar(appCompatActivity.findViewById(R.id.my_toolbar));
            Objects.requireNonNull(appCompatActivity.getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
            appCompatActivity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        } catch (Exception ex) {
            Log.d(ERROR_LOG_NAME, ex.toString());
        }
    }
}