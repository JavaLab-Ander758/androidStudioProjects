package com.andyeseapps.trengingsprogram.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.andyeseapps.trengingsprogram.R;
import com.andyeseapps.trengingsprogram.Utils;
import com.andyeseapps.trengingsprogram.data.ExerciseData;
import com.andyeseapps.trengingsprogram.entity.Exercise;
import com.andyeseapps.trengingsprogram.viewmodel.ExerciseViewModel;

import java.util.ArrayList;
import java.util.List;

public class ExerciseActivity extends AppCompatActivity {
    private boolean downloadingExerciseState = false;
    private ExerciseViewModel exerciseViewModel;
    private int programId;
    private String programName, programDescription;
    private boolean iterated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        setSupportActionBar((Toolbar) findViewById(R.id.my_toolbar));

        Intent programIntent = getIntent();
        programId = programIntent.getIntExtra("programId", -1);
        programName = programIntent.getStringExtra("programName");
        programDescription = programIntent.getStringExtra("programDescription");

        exerciseViewModel = new ViewModelProvider(this).get(ExerciseViewModel.class);
        this.subscribe();

        setUpScreen();
    }



    @Override
    public void onResume() {
        super.onResume();
        downloadExercises();

    }

    private void setUpScreen() {
        TextView tvProgramName = findViewById(R.id.exercise_program_name);
        TextView tvProgramDescription = findViewById(R.id.exercise_program_description);
        tvProgramName.setText(programName);
        tvProgramDescription.setText(programDescription);
    }

    /**
     * Subscribe for download
     */
    private void subscribe() {
        final Observer<ExerciseData> exerciseDataObserver = new Observer<ExerciseData>() {
            @Override
            public void onChanged(ExerciseData exerciseData) {
                if (downloadingExerciseState) {
                    downloadingExerciseState = false;
                    listExercises();
                }
            }
        };
        exerciseViewModel.getExerciseData().observe(this, exerciseDataObserver);
    }

    /**
     * Downloads all exercises to exerciseViewModel
     */
    public void downloadExercises() {
        Utils.displayToast(this, Utils.getDownloadingString(getApplication()));
        if (exerciseViewModel != null) {
            exerciseViewModel.requestDownload(getApplicationContext(), programId);
            downloadingExerciseState = true;
        } else
            downloadingExerciseState = false;
    }

    /**
     * List exercises in a ListView and add onItemClick listeners to each item
     */
    public void listExercises() {
        final List<Exercise> exercises = exerciseViewModel.getExerciseData().getValue().getExercises();
        final ArrayList<String> exerciseNames = new ArrayList<>();
        if (exercises != null) {
            for (int i = 0; i < exercises.size(); i++)
                exerciseNames.add(exercises.get(i).getName());
        }

        ListView listView = (ListView) findViewById(R.id.listId);
        createListViewString(exerciseNames, listView);

        iterated = false;
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                for (int j = 0; j < adapterView.getChildCount(); j++)
                adapterView.getChildAt(j).setBackgroundColor(Color.parseColor(exercises.get(j).getInfobox_color()));

                if (getIterated()) {
                    Intent exerciseIntent = new Intent(ExerciseActivity.this, ShowExerciseActivity.class);
                    exerciseIntent.putExtra("exerciseName", exercises.get(i).getName());
                    exerciseIntent.putExtra("exerciseDescription", exercises.get(i).getDescription());
                    exerciseIntent.putExtra("exerciseIcon", exercises.get(i).getIcon());
                    exerciseIntent.putExtra("exerciseInfobox_color", exercises.get(i).getInfobox_color());
                    ExerciseActivity.this.startActivity(exerciseIntent);
                }
                setIterated(true);
            }
        });
    }


    private boolean getIterated() {
        return iterated;
    }

    public void setIterated(boolean iterated) {
        this.iterated = iterated;
    }

    /**
     * Creates a ListView and puts all elements from ArrayList arrayListElements in it
     *
     * @param arrayListElements ArrayList with elements for ListView
     */
    public void createListViewString(ArrayList<String> arrayListElements, ListView listView) {
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.simple_list_item_1, arrayListElements);

        listView.setAdapter(arrayAdapter);
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
                this.subscribe();
                downloadExercises();
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