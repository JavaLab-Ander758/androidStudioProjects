package com.andyeseapps.trengingsprogram;

import android.app.Activity;
import android.app.Application;
import android.widget.Toast;

public class Utils {

    /**
     * Displays a Toast on given Activity
     *
     * @param activity Activity to display Toast on
     * @param output   String to use in Toast
     */
    public static void displayToast(Activity activity, String output) {
        Toast.makeText(activity, output, Toast.LENGTH_SHORT).show();
    }

    // Metodene under brukte jeg fordi getString(R.id.string_id_i_strings.xml) ikke funket.
    // Vet ikke helt hvorfor det ikke funket da det funket i det forrige prosjektet mitt
    public static String getAboutAppString(Application application) {
        return application.getString(application.getResources().getIdentifier("toolbar_menu_about_app_text", "string", application.getPackageName()));
    }

    public static String getQuitString(Application application) {
        return application.getString(application.getResources().getIdentifier("toolbar_menu_quit_quitting", "string", application.getPackageName()));
    }

    public static String getSettingsString(Application application) {
        return application.getString(application.getResources().getIdentifier("toolbar_menu_settings", "string", application.getPackageName()));
    }

    public static String getNothingToDownloadString(Application application) {
        return application.getString(application.getResources().getIdentifier("no_downloading", "string", application.getPackageName()));
    }

    public static String getDownloadingString(Application application) {
        return application.getString(application.getResources().getIdentifier("downloading", "string", application.getPackageName()));
    }
}