package com.andyeseapps.new_triviaquiz.settings;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.andyeseapps.new_triviaquiz.R;
import com.andyeseapps.new_triviaquiz.Utils;

public class SettingsActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {
    private SharedPreferences sharedPreferences;

    int numberOfQuestions = 10;
    boolean darkModeState = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportFragmentManager().beginTransaction().replace(android.R.id.content, new SettingsFragment()).commit();


        sharedPreferences = this.getSharedPreferences(Utils.getSharedPreferenceFile(), Activity.MODE_PRIVATE);
    }



    @Override
    protected void onStop() {
        this.writeToSharedPreferences();
        super.onStop();
    }

    private void writeToSharedPreferences() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("DARK_MODE", darkModeState);
        editor.putInt("NUMBER_OF_QUESTIONS", numberOfQuestions);
        editor.apply();
    }


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        darkModeState = sharedPreferences.getBoolean("DARK_MODE", false);
        numberOfQuestions = sharedPreferences.getInt("NUMBER_OF_QUESTIONS", 10);
        false
    }



}
