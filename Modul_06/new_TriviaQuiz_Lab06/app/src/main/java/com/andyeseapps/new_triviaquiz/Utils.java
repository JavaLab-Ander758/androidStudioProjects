package com.andyeseapps.new_triviaquiz;







import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.andyeseapps.new_triviaquiz.model.UserQuestions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Utils {
    private static final String SHARED_PREF_FILE = "com.andyeseapps.triviaquiz.sharedpreferences";
    private static final String SAVED_OBJECT_FILE_NAME = "userQuestionsFile";


    /**
     * Writes a UserQuestion object to file
     * @param userQuestions Object to write
     * @param context Context from Activity
     */
    static void writeUserQuestionsToFile(UserQuestions userQuestions, Context context) {
        try {
            FileOutputStream fileOutputStream = context.openFileOutput(SAVED_OBJECT_FILE_NAME, Context.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(userQuestions);
            objectOutputStream.close();
            fileOutputStream.close();
            Log.d("Testing", String.format("Object successfully written to file '%s' in writeUserQuestionsToFile", SAVED_OBJECT_FILE_NAME));
        } catch (Exception ex) {
            displayToast((Activity) context, "Could not write to file!");
            Log.d("Testing", ex.toString());
        }
    }

    /**
     * Reads and returns UserQuestion object from file or Null for caught exception
     * @param context Context from Activity
     * @return UserQuestion object
     */
    static UserQuestions getUserQuestionsFromFile(Context context) {
        UserQuestions userQuestions = null;
        try {
            FileInputStream fileInputStream = context.openFileInput(SAVED_OBJECT_FILE_NAME);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            userQuestions = (UserQuestions) objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
            Log.d("Testing", String.format("Object successfully read from file '%s' in writeUserQuestionsToFile", SAVED_OBJECT_FILE_NAME));
        } catch (Exception ex) {
            Log.d("Testing", ex.toString());
        }
        return userQuestions;
    }

    /**
     * Deletes file containing UserQuestions
     */
    static void deleteUserQuestionsFromFile() {
        File file = new File(SAVED_OBJECT_FILE_NAME);
        file.delete();
    }

    /**
     * Checks if file containing UserQuestions exist
     * @return boolean stating success
     */
    static boolean checkIfUserQuestionsFileExists() {
        File file = new File(SAVED_OBJECT_FILE_NAME);
        return file.exists();
    }

    /**
     * Displays a Toast on given Activity activity
     * @param activity Activity to display Toast on
     * @param output String to use in Toast
     */
    static void displayToast(Activity activity, String output) {
        Toast.makeText(activity,output, Toast.LENGTH_SHORT).show();
    }

    public static String getSharedPreferenceFile() {
        return SHARED_PREF_FILE;
    }

    /**
     * Returns an url to get questions from API at opendtb.com/api_config.php
     * @param numOfQuestions Number of questions to get
     * @param category Category for the questions
     * @param difficulty Difficulty for the questions
     * @param type Type of questions
     * @return Generated url as a String
     */
    static String getTriviaUrlApi(String numOfQuestions, String category, String difficulty, String type) {
        String urlAPI = "https://opentdb.com/api.php?";

        // Append the string
        urlAPI += String.format("amount=%s&", numOfQuestions);
        if (!category.equals("Any"))
            urlAPI += String.format("category=%s&", category);
        if (!difficulty.equals("Any"))
            urlAPI += String.format("difficulty=%s&", difficulty);
        if (!type.equals("Any"))
            urlAPI += String.format("type=%s&", type);

        if (urlAPI.substring(urlAPI.length() - 1).equals("&"))
            urlAPI = urlAPI.substring(0, urlAPI.length() - 1);

        return urlAPI;
    }
}
