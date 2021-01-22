package com.andyeseapps.trengingsprogram.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.andyeseapps.trengingsprogram.R;
import com.andyeseapps.trengingsprogram.Utils;
import com.squareup.picasso.Picasso;

public class ShowExerciseActivity extends AppCompatActivity {
    String name, description, icon, infobox_color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_exercise);

        setSupportActionBar((Toolbar) findViewById(R.id.my_toolbar));

        Intent exerciseIntent = getIntent();
        name = exerciseIntent.getStringExtra("exerciseName");
        description = exerciseIntent.getStringExtra("exerciseDescription");
        icon = exerciseIntent.getStringExtra("exerciseIcon");
        infobox_color = exerciseIntent.getStringExtra("exerciseInfobox_color");
        setUpExerciseDisplay();
    }

    /**
     * Displays the exercise, sets given image to ImageView using Picasso
     * and changes background color for root View depending on exercise
     */
    private void setUpExerciseDisplay() {
        TextView tvExerciseName = findViewById(R.id.show_exercise_tv_name);
        tvExerciseName.setText(name);

        TextView tvExerciseDescription = findViewById(R.id.show_exercise_tv_description);
        tvExerciseDescription.setText(Html.fromHtml(description.replace("\n", "")));

        ImageView imExerciseIcon = findViewById(R.id.show_exercise_tv_icon);
        Picasso.get().load("https://tusk.systems/trainingapp/icons/" + icon).into(imExerciseIcon);

        LinearLayout lnRootView = findViewById(R.id.show_exercise_root);
        lnRootView.setBackgroundColor(Color.parseColor(infobox_color));
    }

    /**
     * Inflates the toolbar-menu
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * Called when user taps element in Toolbar
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
                Utils.displayToast(this, Utils.getSettingsString(getApplication()));
                break;
            case R.id.action_refresh:
                Utils.displayToast(this, Utils.getNothingToDownloadString(getApplication()));
                break;
            case R.id.action_about:
                Utils.displayToast(this, Utils.getAboutAppString(getApplication()));
                break;
            case R.id.action_quit:
                Utils.displayToast(this, Utils.getQuitString(getApplication()));
                doQuit();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Quits the application
     */
    public void doQuit() {
        finishAffinity();
    }
}
