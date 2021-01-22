package com.andyeseapps;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {
    boolean darkModeState = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportFragmentManager().beginTransaction().replace(android.R.id.content, new SettingsFragment()).commit();


        SharedPreferences sharedPreferences = this.getSharedPreferences("myPreferences", Activity.MODE_PRIVATE);

    }




    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
    }



    //
//    @Override
//    protected void onStop() {
//        this.writeToSharedPreferences();
//        super.onStop();
//    }
//
//    private void writeToSharedPreferences() {
////        SharedPreferences.Editor editor = sharedPreferences.edit();
////        editor.putBoolean("DARK_MODE", darkModeState);
////
////        editor.apply();
//    }
}
