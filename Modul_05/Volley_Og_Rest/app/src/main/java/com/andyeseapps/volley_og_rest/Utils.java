package com.andyeseapps.volley_og_rest;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

public class Utils {

    /**
     * Displays a Toast on given Activity activity
     * @param activity Activity to display Toast on
     * @param output String to use in Toast
     */
    static void displayToast(Activity activity, String output) {
        Toast.makeText(activity,output, Toast.LENGTH_SHORT).show();
    }

    public static void logToLogcat(String classname, Object object) {
        Log.d("   _" + classname + "_",  object.getClass().getEnclosingMethod().getName() + "()_" + "t@" + System.currentTimeMillis());
    }
}
