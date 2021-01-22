package com.andyeseapps.new_triviaquiz;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.andyeseapps.new_triviaquiz.model.UserQuestions;

public class StatisticsActivity extends AppCompatActivity {
    private UserQuestions userQuestions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        userQuestions = (UserQuestions) getIntent().getSerializableExtra("UserQuestions");
        setUpScreen();
    }



    private void setUpScreen() {

        TextView numberOfQuestionsTextView=  findViewById(R.id.statistics_numberOfAnswersTextView);
        numberOfQuestionsTextView.setText(String.format("There were %s questions", userQuestions.getQuestions().size())); // +1 here?
        TextView correctAnswersTextView = findViewById(R.id.statistics_correctAnswersTextView);
        correctAnswersTextView.setText(String.format("You answered correct on %s questions", userQuestions.getNumOfCorrectAnswers()));
        TextView incorrectAnswersTextView = findViewById(R.id.statistics_incorrectAnswersTextView);
        incorrectAnswersTextView.setText(String.format("You answered wrong on %s questions", userQuestions.getNumOfIncorrectAnswers()));
    }
}