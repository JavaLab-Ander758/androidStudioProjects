package com.andyeseapps.new_triviaquiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andyeseapps.new_triviaquiz.json.QuestionData;
import com.andyeseapps.new_triviaquiz.json.QuestionViewModel;
import com.andyeseapps.new_triviaquiz.model.Question;
import com.andyeseapps.new_triviaquiz.model.UserQuestions;
import com.andyeseapps.new_triviaquiz.settings.SettingsActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// TODO: Use setText instead of append for current settings textviews...
// TODO: Use String values instead of hardcoded in xml
// TODO: Implement dark mode with styles
public class MainActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    private boolean downloading = false;
    private QuestionViewModel questionViewModel;
    private UserQuestions userQuestions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        questionViewModel = new ViewModelProvider(this).get(QuestionViewModel.class);
        this.subscribe();
    }

    /**
     * Subscribe for download
     */
    private void subscribe() {
        final Observer<QuestionData> questionDataObserver = new Observer<QuestionData>() {
            @Override
            public void onChanged(QuestionData questionData) {
                if (downloading) {
                    downloading = false;
                    Log.d("Testing", "Finished downloading");
                    fetchQuestions();
                }
            }
        };
        questionViewModel.getQuestionData().observe(this, questionDataObserver);
    }

    /**
     * Downloads new questions from API
     * @param view View from button
     */
    public void downloadNewQuestions(View view) {
        if (Utils.checkIfUserQuestionsFileExists())
            userQuestions = Utils.getUserQuestionsFromFile(getApplicationContext());
        else {
            // File did not exist --> Download new questions
            String numOfQuestions = sharedPreferences.getString("NUMBER_OF_QUESTIONS", "10");
            String category = sharedPreferences.getString("CATEGORY_QUESTIONS", "Any");
            String difficulty = sharedPreferences.getString("DIFFICULTY_QUESTIONS", "Any");
            String type = sharedPreferences.getString("TYPE_QUESTIONS", "Any");
            String url = Utils.getTriviaUrlApi(numOfQuestions, category, difficulty, type);
            Log.d("Testing", url);

            if (questionViewModel != null) {
                questionViewModel.requestDownload(getApplicationContext(), url);
                Utils.displayToast(this, getString(R.string.main_toast_started_downloading));
                downloading = true;
            } else {
                downloading = false;
            }
        }
    }

    private void fetchQuestions() {
        // TODO: Implement this
        List <Question> questionList = questionViewModel.getQuestionData().getValue().getQuestions();
        userQuestions = new UserQuestions(questionList);
//        Log.d("testing", questionList.get(0).getCategory());
        TextView test = findViewById(R.id.testingTextView);
//        test.setText(questionList.get(0).getCategory());
        test.setText("sdøflgjsdflgjkdfgjksdføgkljsdfg");

        Log.d("Testing", questionList.get(0).toString());

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
        boolean darkModeState = sharedPreferences.getBoolean("DARK_MODE", false);
        if (darkModeState)
            linearLayout.setBackgroundColor(Color.GRAY);
        else
            linearLayout.setBackgroundColor(Color.WHITE);

     setUpCurrentSettings();
    }


    private void setUpCurrentSettings() {
        TextView numOfQuestionsTV = findViewById(R.id.mainNumberOfQuestions);
        numOfQuestionsTV.setText(getString(R.string.main_current_settings_numberOfQuestions, sharedPreferences.getString("NUMBER_OF_QUESTIONS", "10")));
//        numOfQuestionsTV.append(sharedPreferences.getString("NUMBER_OF_QUESTIONS", "10"));

        TextView categoryTV = findViewById(R.id.mainCategoryTextView);
        categoryTV.setText(getString(R.string.main_current_settings_category, sharedPreferences.getString("CATEGORY_QUESTIONS", "Any")));
        // NEED TO GET ITEM AND NOT VALUE IN STRING-ARRAY
//        categoryTV.append(sharedPreferences.getString("CATEGORY_QUESTIONS", "Any"));

        TextView difficultyTV = findViewById(R.id.mainDifficultyTextView);
        difficultyTV.setText(getString(R.string.main_current_settings_difficulty, sharedPreferences.getString("DIFFICULTY_QUESTIONS", "Any")));
//        difficultyTV.append(sharedPreferences.getString("DIFFICULTY_QUESTIONS", "Any"));

        TextView typeTV = findViewById(R.id.mainTypeOfQuestions);
        typeTV.setText(getString(R.string.main_current_settings_typeOfQuestions, sharedPreferences.getString("TYPE_QUESTIONS", "Any")));
//        typeTV.append(sharedPreferences.getString("TYPE_QUESTIONS", "Any"));
    }



    /**
     * Starts a new QuizActivity
     * @param view View from button
     */
    public void startQuizActivity(View view) {
        // TODO: Replace this if-statement with downloadNewQuestions() when fixed
        if (Utils.checkIfUserQuestionsFileExists())
            userQuestions = Utils.getUserQuestionsFromFile(getApplicationContext());
        else {
            generateDummyQuestions(new View(this));
        }

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
    public void generateDummyQuestions(View view) {
        Question dummyQuestion1 = new Question("Entertainment: Books", "multiple", "medium", "By what nickname is Jack Dawkins known in the Charles Dickens novel, Oliver Twist?", "The Artful Dodger", Arrays.asList("Fagin", "Bulls-eye", "Mr. Fang"));
        Question dummyQuestion2 = new Question("Entertainment: Books", "multiple", "medium", "What was the pen name of novelist, Mary Ann Evans?", "George Eliot", Arrays.asList("George Orwell", "George Bernard Shaw", "George Saunders"));
        Question dummyQuestion3 = new Question("Entertainment: Books", "multiple", "medium", "J.K. Rowling completed &quot;Harry Potter and the Deathly Hallows&quot; in which hotel in Edinburgh, Scotland?", "The Balmoral", Arrays.asList("The Dunstane Hotel", "Hotel Novotel", "Sheraton Grand Hotel &amp; Spa"));
        List<Question> questions = new ArrayList<>();
        questions.add(dummyQuestion1);
        questions.add(dummyQuestion2);
        questions.add(dummyQuestion3);
        userQuestions = new UserQuestions(questions);
        TextView textView = findViewById(R.id.testingTextView);
        textView.setText(userQuestions.getQuestions().get(0).getCategory());
    }
}
