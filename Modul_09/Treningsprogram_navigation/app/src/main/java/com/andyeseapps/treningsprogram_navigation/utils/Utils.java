package com.andyeseapps.treningsprogram_navigation.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.navigation.NavOptions;

import com.andyeseapps.treningsprogram_navigation.R;

import java.util.ArrayList;
import java.util.Objects;

public class Utils {
    public static final String DEBUG_LOGGER_TAG = "Testing";
    private static final String SHARED_PREF_FILENAME = "_SHARED_PREFERENCE_FILE";
    private static final String KEY_LOGGED_IN_STATE = "KEY_LOGIN_STATE", KEY_USERNAME = "KEY_USERNAME", KEY_PASSWORD = "KEY_PASSWORD";

    /**
     * Returns current version nome of app
     */
    public static String getVersionName(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return ErrorCode.VERSION_NAME.toString();
    }

    /**
     * Displays a Toast on given Activity
     */
    public static void displayToast(Activity activity, String output) {
        Toast.makeText(activity, output, Toast.LENGTH_SHORT).show();
    }

    /**
     * Returns SharedPreference for current package
     */
    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(Objects.requireNonNull(context.getClass().getPackage()).getName() + SHARED_PREF_FILENAME, Context.MODE_PRIVATE);
    }

    /**
     * Returns true if session is logged in from from SharedPreference-file
     */
    public static boolean isLoggedIn(Context context) {
        return getSharedPreferences(context).getBoolean(KEY_LOGGED_IN_STATE, false);
    }

    /**
     * Sets login-state in SharedPreference-file
     */
    public static void setLoggedIn(boolean loggedIn, Context context) {
        getSharedPreferences(context).edit().putBoolean(KEY_LOGGED_IN_STATE, loggedIn).apply();
        displayToast((Activity) context, (loggedIn) ? context.getString(R.string.login_message) : context.getString(R.string.logout_message));
    }

    /**
     * Changes login-detail in SharedPreference-file
     */
    public static boolean setLoginDetails(Context context, String username, String password) {
        if (username.equals("") || password.equals("")) {
            displayToast((Activity) context, context.getString(R.string.empty_input_fields));
            return false;
        }
        getSharedPreferences(context).edit().putString(KEY_USERNAME, username).apply();
        getSharedPreferences(context).edit().putString(KEY_PASSWORD, password).apply();
        displayToast((Activity) context, context.getString(R.string.register_user_success_message));
        return true;
    }

    /**
     * Returns true if login-details matches with stored details in SharedPreference-file
     */
    public static boolean isLoginDetailsCorrect(Context context, String usernameFromInput, String passwordFromInput) {
        if (usernameFromInput.equals("") || passwordFromInput.equals("")) {
            displayToast((Activity) context, context.getString(R.string.login_wrong_credentials));
            return false;
        }
        return (getSharedPreferences(context).getString(KEY_USERNAME, "").equals(usernameFromInput)
                && getSharedPreferences(context).getString(KEY_PASSWORD, "").equals(passwordFromInput));
    }

    /**
     * Sets visibility for a given button
     */
    public static void setButtonVisibility(Button button, boolean visible) {
        button.setClickable(visible);
        button.setAlpha(visible ? 1 : .5f);
    }

    /**
     * Retuns all String elements in ArrayList<String> as formatted String
     */
    public static String stringArrayListToString(ArrayList<String> stringArrayList) {
        StringBuilder output = new StringBuilder();
        for (String item : stringArrayList)
            output.append(" ~[").append(item).append("]");
        return output.toString();
    }

    /**
     * Sets adapter on ArrayList<String> to ListView with simple_list_item_1
     */
    public static void setAdapterToListView(Activity activity, ArrayList<String> arrayList, ListView listView) {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(activity, R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(arrayAdapter);
    }

    public static NavOptions getAnimation() {
        NavOptions.Builder navOptionsBuilder = new NavOptions.Builder();
        navOptionsBuilder.setEnterAnim(R.anim.slide_in_right);
        navOptionsBuilder.setExitAnim(R.anim.slide_out_left);
        navOptionsBuilder.setPopEnterAnim(R.anim.slide_in_left);
        navOptionsBuilder.setPopExitAnim(R.anim.slide_out_right);
        return navOptionsBuilder.build();
    }

    /**
     * Used for error-codes
     */
    private enum ErrorCode {
        VERSION_NAME(0, "Version name not found");

        private final int code;
        private final String description;

        ErrorCode(int code, String description) {
            this.code = code;
            this.description = description;
        }

        @Override
        public String toString() {
            return "ERROR[" + code + "] " + description;
        }
    }
}