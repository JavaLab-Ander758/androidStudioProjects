package com.andyeseapps;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import com.andyeseapps.model.UserQuestions;

public class StatisticsActivity extends AppCompatActivity {
    private UserQuestions userQuestions;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        userQuestions = (UserQuestions) getIntent().getSerializableExtra("UserQuestions");
        setUpScreen();
    }

    public void exitStatistics(View view) {
        finish();
    }



    private void setUpScreen() {
        TextView numberOfQuestionsTextView=  findViewById(R.id.statistics_numberOfAnswersTextView);
        numberOfQuestionsTextView.setText(String.format("There were %s questions", userQuestions.getQuestions().size())); // +1 here?
        TextView correctAnswersTextView = findViewById(R.id.statistics_correctAnswersTextView);
        correctAnswersTextView.setText(String.format("You answered correct on %s questions", userQuestions.getNumOfCorrectAnswers()));
        TextView incorrectAnswersTextView = findViewById(R.id.statistics_incorrectAnswersTextView);
        incorrectAnswersTextView.setText(String.format("You answered wrong on %s questions", userQuestions.getNumOfIncorrectAnswers()));




        Utils.setDarkMode(new View[] {numberOfQuestionsTextView, correctAnswersTextView, incorrectAnswersTextView, numberOfQuestionsTextView.getRootView()}, sharedPreferences);
    }
}