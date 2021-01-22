package com.andyeseapps.treningsprogram_navigation.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.andyeseapps.treningsprogram_navigation.R;
import com.andyeseapps.treningsprogram_navigation.data.ExerciseData;
import com.andyeseapps.treningsprogram_navigation.entity.Exercise;
import com.andyeseapps.treningsprogram_navigation.entity.Program;
import com.andyeseapps.treningsprogram_navigation.utils.Utils;
import com.andyeseapps.treningsprogram_navigation.viewmodel.ExerciseViewModel;

import java.util.ArrayList;
import java.util.List;

public class ExerciseFragment extends Fragment {
    private boolean downloadingExerciseState = false;
    private boolean iterated;
    private ExerciseViewModel exerciseViewModel;
    private Program program;
    private ListView listView;
    private Button buttonSetColor;

    public ExerciseFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        program = ExerciseFragmentArgs.fromBundle(getArguments()).getProgramObject();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_exercise, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        updateUI();
    }

    @Override
    public void onResume() {
        super.onResume();
        exerciseViewModel = new ViewModelProvider(getActivity()).get(ExerciseViewModel.class);
        subscribe();
        downloadExercises();
    }

    /**
     * Initializes TextViews with Program-info
     */
    private void updateUI() {
        TextView tvProgramName = getView().findViewById(R.id.exercise_program_name);
        TextView tvProgramDescription = getView().findViewById(R.id.exercise_program_description);
        tvProgramName.setText(program.getName());
        tvProgramDescription.setText(program.getDescription());
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
        exerciseViewModel.getExerciseData().observe(ExerciseFragment.this, exerciseDataObserver);
    }

    /**
     * Downloads all exercises to exerciseViewModel
     */
    private void downloadExercises() {
        Utils.displayToast(getActivity(), getString(R.string.downloading));
        if (exerciseViewModel != null) {
            exerciseViewModel.requestDownload(getActivity().getApplicationContext(), program.getId());
            downloadingExerciseState = true;
        } else
            downloadingExerciseState = false;
    }

    /**
     * List exercises in a ListView and add onItemClick listeners to each item
     */
    private void listExercises() {
        final List<Exercise> exercises = exerciseViewModel.getExerciseData().getValue().getExercises();
        final ArrayList<String> exerciseNames = new ArrayList<>();
        if (exercises != null)
            for (int i = 0; i < exercises.size(); i++)
                exerciseNames.add(exercises.get(i).getName());

        Log.d(Utils.DEBUG_LOGGER_TAG, "[exerciseNames]=" + Utils.stringArrayListToString(exerciseNames));

        listView = getView().findViewById(R.id.listId);
        Utils.setAdapterToListView(getActivity(), exerciseNames, listView);

        iterated = false;
        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            for (int j = 0; j < adapterView.getChildCount(); j++)
                adapterView.getChildAt(j).setBackgroundColor(Color.parseColor(exercises.get(j).getInfobox_color()));
            if (getIterated()) {
                ExerciseFragmentDirections.ActionExerciseFragmentToExerciseDetailFragment argWithExerciseObj = ExerciseFragmentDirections.actionExerciseFragmentToExerciseDetailFragment(exercises.get(i));
                NavController navController = Navigation.findNavController(view);
                navController.navigate(argWithExerciseObj);
            }
            setIterated(true);
            Utils.setButtonVisibility(buttonSetColor, false);
        });

        buttonSetColor = getView().findViewById(R.id.testButton);
        buttonSetColor.setOnClickListener(v -> {
            programmaticallyClickListView(listView);
            Utils.setButtonVisibility(buttonSetColor, false);
        });
    }

    /**
     * Programmatically click on first element in a ListView
     */
    private void programmaticallyClickListView(ListView listView) {
        listView.performItemClick(listView.getAdapter().getView(1, null, null), 1, listView.getAdapter().getItemId(1));
    }

    /**
     * Used for setting colour to listView-items
     */
    private boolean getIterated() {
        return iterated;
    }

    /**
     * Used for setting colour to listView-items
     */
    private void setIterated(boolean iterated) {
        this.iterated = iterated;
    }
}