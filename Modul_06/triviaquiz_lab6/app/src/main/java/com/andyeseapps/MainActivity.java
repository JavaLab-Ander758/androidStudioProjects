package com.andyeseapps;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;

import com.andyeseapps.json.ResultData;
import com.andyeseapps.json.ResultViewModel;
import com.andyeseapps.model.Question;
import com.andyeseapps.model.UserQuestions;
import com.andyeseapps.model.Result;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// TODO: Implement dark mode with styles
public class MainActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    private boolean downloading = false;
    private ResultViewModel resultViewModel;
    private UserQuestions userQuestions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        resultViewModel = new ViewModelProvider(this).get(ResultViewModel.class);
        this.subscribe();
    }

    /**
     * Subscribe for download
     */
    private void subscribe() {
        final Observer<ResultData> questionDataObserver = new Observer<ResultData>() {
            @Override
            public void onChanged(ResultData questionData) {
                if (downloading) {
                    downloading = false;
                    Log.d("Testing", "Finished downloading");
                    fetchQuestions();
                }
            }
        };
        resultViewModel.getResultData().observe(this, questionDataObserver);
    }

    /**
     * Downloads new questions from API
     * @param view View from button
     */
    public void downloadNewQuestions(View view) {
            // File did not exist --> Download new questions
            String numOfQuestions = sharedPreferences.getString("NUMBER_OF_QUESTIONS", "10");
            String category = sharedPreferences.getString("CATEGORY_QUESTIONS", "Any");
            String difficulty = sharedPreferences.getString("DIFFICULTY_QUESTIONS", "Any");
            String type = sharedPreferences.getString("TYPE_QUESTIONS", "Any");
            String url = Utils.getTriviaUrlApi(numOfQuestions, category, difficulty, type);
            Log.d("Testing", url);

            if (resultViewModel != null) {
                resultViewModel.requestDownload(getApplicationContext(), url);
                Utils.displayToast(this, getString(R.string.main_toast_started_downloading));
                downloading = true;
            } else {
                downloading = false;
            }
    }

    private void fetchQuestions() {
        // TODO: Implement this
        List <Result> resultList = resultViewModel.getResultData().getValue().getResults();
        ArrayList<Question> questionList = new ArrayList<>();
        for (int i = 0; i < resultList.size(); i++) {
            questionList.addAll(resultList.get(i).getResults());
        }


        userQuestions = new UserQuestions(questionList);

        Log.d("Testing", resultList.get(0).toString());
    }

    /**
     * Resumes the main activity
     */
    @Override
    protected void onResume() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        setUpScreen();
        super.onResume();
    }

    /**
     * Sets the screen if resumed from Settings Activity
     */
    private void setUpScreen() {
        LinearLayout linearLayout = findViewById(R.id.main_LinearLayout);
//        boolean darkModeState = sharedPreferences.getBoolean("DARK_MODE", false);
        Utils.setDarkMode(new View[]{linearLayout}, sharedPreferences);




        setUpCurrentSettings();
    }

    /**
     * Sets up te settings TextViews
     */
    private void setUpCurrentSettings() {
        TextView numOfQuestionsTV = findViewById(R.id.mainNumberOfQuestions);
        numOfQuestionsTV.setText(getString(R.string.main_current_settings_numberOfQuestions, sharedPreferences.getString("NUMBER_OF_QUESTIONS", "10")));
        TextView categoryTV = findViewById(R.id.mainCategoryTextView);
        categoryTV.setText(getString(R.string.main_current_settings_category, sharedPreferences.getString("CATEGORY_QUESTIONS", "Any")));
        TextView difficultyTV = findViewById(R.id.mainDifficultyTextView);
        difficultyTV.setText(getString(R.string.main_current_settings_difficulty, sharedPreferences.getString("DIFFICULTY_QUESTIONS", "Any")));
        TextView typeTV = findViewById(R.id.mainTypeOfQuestions);
        typeTV.setText(getString(R.string.main_current_settings_typeOfQuestions, sharedPreferences.getString("TYPE_QUESTIONS", "Any"))); // TODO: No output here, fix this

        Utils.setDarkMode(new View[]{numOfQuestionsTV, categoryTV, difficultyTV, typeTV}, sharedPreferences);
    }


    /**
     * Starts a new QuizActivity
     * @param view View from button
     */
    public void startQuizActivity(View view) {
        if (Utils.checkIfUserQuestionsFileExists(getFilesDir()))
            userQuestions = Utils.getUserQuestionsFromFile(getApplicationContext());
        else
//            downloadNewQuestions(new View(this));
            generateDummyQuestions(); // TODO: Replace this if-statement with downloadNewQuestions() when fixed

        Intent startQuizIntent = new Intent(MainActivity.this, QuizActivity.class);
        if (userQuestions != null) {
            startQuizIntent.putExtra("UserQuestions", userQuestions);
            MainActivity.this.startActivity(startQuizIntent);
        } else
            Utils.displayToast(this, getString(R.string.main_toast_no_questions_error));
    }

    /**
     * Used for generating custom top bar
     * @param menu Menu object
     * @return Boolean stating success
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainmenu, menu);
        return true;
    }

    /**
     * Used for generating custom top bar
     * @param item Items in top bar to be clicked
     * @return Boolean stating success
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;

            case R.id.action_end:
                finish();
                break;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
        return true;
    }












    // TESTING BELOW
    public void generateDummyQuestions() {
        Question dummyQuestion1 = new Question("Entertainment: Books", "multiple", "medium", "By what nickname is Jack Dawkins known in the Charles Dickens novel, Oliver Twist?", "The Artful Dodger", Arrays.asList("Fagin", "Bulls-eye", "Mr. Fang"));
        Question dummyQuestion2 = new Question("Entertainment: Books", "multiple", "medium", "What was the pen name of novelist, Mary Ann Evans?", "George Eliot", Arrays.asList("George Orwell", "George Bernard Shaw", "George Saunders"));
        Question dummyQuestion3 = new Question("Entertainment: Books", "multiple", "medium", "J.K. Rowling completed &quot;Harry Potter and the Deathly Hallows&quot; in which hotel in Edinburgh, Scotland?", "The Balmoral", Arrays.asList("The Dunstane Hotel", "Hotel Novotel", "Sheraton Grand Hotel &amp; Spa"));
        List<Question> questions = new ArrayList<>();
        questions.add(dummyQuestion1);
        questions.add(dummyQuestion2);
        questions.add(dummyQuestion3);
        userQuestions = new UserQuestions(questions);
    }
}