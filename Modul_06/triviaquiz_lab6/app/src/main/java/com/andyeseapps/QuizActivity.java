package com.andyeseapps;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import com.andyeseapps.model.UserQuestions;

import java.util.Collections;
import java.util.List;

public class QuizActivity extends AppCompatActivity {
    private UserQuestions userQuestions;
    private RadioGroup radioGroup;
    String correctAnswer;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        userQuestions = (UserQuestions) getIntent().getSerializableExtra("UserQuestions");
        radioGroup = findViewById(R.id.quiz_questionRadioGroup);
        setUpScreen();
    }




    /**
     * Goes to next question or finishes quiz if RadioGroup with questions is pressed
     * @param view View from Button
     */
    public void goToNextQuestion(View view) {
        if (radioGroup.getCheckedRadioButtonId() != -1) {
            checkCorrectAnswer();
            if (checkForMoreQuestions()) { // Ensure this method works
                userQuestions.appendCurrentIndex();
                Intent nextQuestionIntent = new Intent(this, QuizActivity.class);
                nextQuestionIntent.putExtra("UserQuestions", userQuestions);
                this.startActivity(nextQuestionIntent);
            } else {
                Utils.deleteUserQuestionsFromFile(getFilesDir());
                Intent finishQuizIntent = new Intent(this, StatisticsActivity.class);
                finishQuizIntent.putExtra("UserQuestions", userQuestions);
                this.startActivity(finishQuizIntent);
            }
            finish();
        } else
            Utils.displayToast(this, "No answers checked!");
    }





    /**
     * Displays the questions and all answers in random order
     */
    private void setUpScreen() {
        TextView currentIndexTextView = findViewById(R.id.quiz_currentIndexTextView);
        currentIndexTextView.setText(getString(R.string.quiz_current_index_text, userQuestions.getCurrentIndex() + 1, userQuestions.getQuestions().size())); // +1 for 1-indexing for end-user

        if (userQuestions.getQuestions().size() == userQuestions.getCurrentIndex() - 1) {
            Button finishQuizButton = findViewById(R.id.quiz_nextQuestionButton);
            finishQuizButton.setText(getString(R.string.quiz_finish_quiz_button));
        }

        TextView questionTitleTV = findViewById(R.id.quiz_questionTitleTextView);
        questionTitleTV.setText(userQuestions.getQuestions().get(userQuestions.getCurrentIndex()).getQuestion());

        List<String> incorrectAnswers = userQuestions.getQuestions().get(userQuestions.getCurrentIndex()).getIncorrect_answers();
        Collections.shuffle(incorrectAnswers);

        for (int i = 0, randomIndex = getRandomInt(userQuestions.getQuestions().size(), 0); i < 3; i++) {
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(incorrectAnswers.get(i));
            radioGroup.addView(radioButton);
            if (i == randomIndex) {
                RadioButton correctRadioButton = new RadioButton(this);
                Utils.setDarkMode(new View[] {correctRadioButton}, sharedPreferences);
                correctAnswer = userQuestions.getQuestions().get(userQuestions.getCurrentIndex()).getCorrect_answer();
                correctRadioButton.setText(correctAnswer);
                radioGroup.addView(correctRadioButton);
            }
            Utils.setDarkMode(new View[] {radioButton}, sharedPreferences);
        }

        Utils.setDarkMode(new View[] {currentIndexTextView, questionTitleTV, currentIndexTextView.getRootView()}, sharedPreferences);
    }

    /**
     * Checks if user pressed correct answer and adjusts current score
     */
    private void checkCorrectAnswer() {
        View checkedRadioButtonView = radioGroup.findViewById(radioGroup.getCheckedRadioButtonId());
        RadioButton checkedRadioButton = (RadioButton) radioGroup.getChildAt(radioGroup.indexOfChild(checkedRadioButtonView));

        if (checkedRadioButton.getText().equals(correctAnswer))
            userQuestions.appendNumOfCorrectAnswers();
        else
            userQuestions.appendNumOfIncorrectAnswers();
    }

    /**
     * Checks if there are more questions to give user
     * @return boolean stating success
     */
    private boolean checkForMoreQuestions() {
        return (userQuestions.getCurrentIndex() < userQuestions.getQuestions().size() - 1);
    }

    /**
     * Saves and quits current quiz
     * @param view View from Button
     */
    public void saveAndQuitQuiz(View view) {
        Utils.writeUserQuestionsToFile(userQuestions, getApplicationContext());
        Utils.displayToast(this, getString(R.string.quiz_toast_save_and_quit));
        finish();
    }

    /**
     * Discards current save and quits current qui<
     * @param view View from Button
     */
    public void discardAndQuitQuiz(View view) {
        Utils.deleteUserQuestionsFromFile(getFilesDir());
        Utils.displayToast(this, getString(R.string.quiz_toast_discard_and_quit));
        finish();
    }


















    /**
     * Overrides back button
     */
    @Override
    public void onBackPressed() {
        Utils.displayToast(this, "Please save and exit...");
    }

    /**
     * Returns a random integer value in given range
     * @param max Maximum value
     * @param min Minimum value
     * @return Random number as int
     */
    private int getRandomInt(int max, int min) {
        return (int)(Math.random() * (max ) + min);
    }
}