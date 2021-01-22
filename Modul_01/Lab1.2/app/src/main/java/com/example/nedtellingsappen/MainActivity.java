package com.example.nedtellingsappen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    public static final String MY_DATE_FORMAT = "dd.MM.yyyy";
    Date dateNow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setCurrentDateMessage();
    }

    void setCurrentDateMessage() {
        TextView textView = findViewById(R.id.tv_CurrentDate);
        dateNow = new Date();
        textView.setText(String.format("Beregner tidsdifferanse fra %s til ...", new SimpleDateFormat("dd.MM.yyyy HH:mm").format(dateNow.getTime())));
    }

    /**
     * Outputs countdown message from an EditText field
     *      String format: dd.MM.YYYY
     * @param view View that is used
     */
    public void setCountDownMessageEditText(View view) {
        EditText etUserDateInput = findViewById(R.id.etUserDateInput);
        String dateGiven = etUserDateInput.getText().toString();

        outputCountdownToTextView(dateGiven);
    }

    /**
     * Outputs countdown message from a CalendarView
     *      String format: dd.MM.YYYY
     * @param view View that is used
     */
    public void setCountDownMessageCalendarView(View view) {
        CalendarView cvUserDateInput = findViewById(R.id.cvUserDateInput);

        cvUserDateInput.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String dateGiven = String.format("%s.%s.%s", dayOfMonth, month + 1, year);

                outputCountdownToTextView(dateGiven);
            }
        });
    }

    /**
     * Outputs text to a TextView
     *      Input format: dd.MM.YYYY
     *      Output format: 0d 00t 00m
     * @param dateGivenString Date given by the user to calculate time difference
     */
    void outputCountdownToTextView(String dateGivenString) {
        // Parse the string to a Calendar object
        Calendar calendarGiven = Calendar.getInstance();
        try {
            calendarGiven.setTime(new SimpleDateFormat(MY_DATE_FORMAT).parse(dateGivenString));
            Date dateGiven = calendarGiven.getTime();

            TextView textView = findViewById(R.id.tv_Countdown);
            if (dateGivenString != null) {
                long diff = dateGiven.getTime() - dateNow.getTime();
                long days    = Math.abs(diff / (24 * 60 * 60 * 1000));
                long hours   = Math.abs(diff / (60 * 60 * 1000) % 24);
                long minutes = Math.abs(diff / (60 * 1000) % 60);
                String countdown = String.format("%sd %st %sm", days, hours, minutes);

                // Output countdown and set color depending on positive/negative difference
                if (diff >= 0)
                    textView.setBackgroundColor(Color.GREEN);
                else
                    textView.setBackgroundColor(Color.RED);
                textView.setText(countdown);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}