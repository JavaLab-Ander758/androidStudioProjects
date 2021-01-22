package com.andyeseapps.settings;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import com.andyeseapps.R;


public class SettingsFragment extends PreferenceFragmentCompat {

    public SettingsFragment() {
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootkey) {
        setPreferencesFromResource(R.xml.preferences, rootkey);
    }

}
