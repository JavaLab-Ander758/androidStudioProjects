package com.andyeseapps.trengingsprogram.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.andyeseapps.trengingsprogram.R;
import com.andyeseapps.trengingsprogram.Utils;
import com.andyeseapps.trengingsprogram.data.ProgramData;
import com.andyeseapps.trengingsprogram.entity.Program;
import com.andyeseapps.trengingsprogram.viewmodel.ProgramViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private boolean downloadingProgramState = false;
    private ProgramViewModel programViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setSupportActionBar((Toolbar)findViewById(R.id.my_toolbar));
    }

    @Override
    public void onResume() {
        super.onResume();

        programViewModel = new ViewModelProvider(this).get(ProgramViewModel.class);
        this.subscribe();
        downloadPrograms();
    }

    /**
     * Subscribe for download
     */
    private void subscribe() {
        final Observer<ProgramData> programDataObserver = new Observer<ProgramData>() {
            @Override
            public void onChanged(ProgramData programData) {
                if (downloadingProgramState) {
                    downloadingProgramState = false;
                    listPrograms();
                }
            }
        };
        programViewModel.getProgramData().observe(this, programDataObserver);
    }

    /**
     * Downloads all programs to programViewModel
     *
     */
    public void downloadPrograms() {
        Utils.displayToast(this, Utils.getDownloadingString(getApplication()));
        if (programViewModel != null) {
            programViewModel.requestDownload(getApplicationContext());
            downloadingProgramState = true;
        } else
            downloadingProgramState = false;
    }

    /**
     * List programs in a ListView and add onItemClick listeners to each item
     */
    public void listPrograms() {
        final List<Program> programs = programViewModel.getProgramData().getValue().getPrograms();
        final ArrayList<String> programNames = new ArrayList<>();
        if (programs != null) {
            for (int i = 0; i < programs.size(); i++) {
                programNames.add(programs.get(i).getName());
                Log.d("Testing", programs.get(i).getName());
            }

        }

        ListView listView = (ListView) findViewById(R.id.listId);
        createListViewString(programNames, listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent programIntent = new Intent(MainActivity.this, ExerciseActivity.class);
                programIntent.putExtra("programId", programs.get(i).getId());
                programIntent.putExtra("programName", programs.get(i).getName());
                programIntent.putExtra("programDescription", programs.get(i).getDescription());
                MainActivity.this.startActivity(programIntent);
            }
        });
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
                downloadPrograms();
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