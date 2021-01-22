package com.andyeseapps.new_triviaquiz.settings;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import com.andyeseapps.new_triviaquiz.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    public SettingsFragment() {
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootkey) {
        setPreferencesFromResource(R.xml.preferences, rootkey);
    }

}
