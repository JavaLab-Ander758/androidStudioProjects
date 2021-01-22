package com.andyeseapps.treningsprogram_navigation.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.andyeseapps.treningsprogram_navigation.R;
import com.andyeseapps.treningsprogram_navigation.entity.Exercise;
import com.squareup.picasso.Picasso;

public class ExerciseDetailFragment extends Fragment {

    public ExerciseDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_exercise_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        updateIO(ExerciseDetailFragmentArgs.fromBundle(getArguments()).getExerciseObject());
    }

    /**
     * Displays the exercise, sets given image to ImageView using Picasso
     * and changes background color for root View depending on exercise
     */
    private void updateIO(Exercise exercise) {
        TextView tvExerciseName = getActivity().findViewById(R.id.show_exercise_tv_name);
        tvExerciseName.setText(exercise.getName());

        TextView tvExerciseDescription = getActivity().findViewById(R.id.show_exercise_tv_description);
        tvExerciseDescription.setText(Html.fromHtml(exercise.getDescription().replace("\n", "")));

        ImageView imExerciseIcon = getActivity().findViewById(R.id.show_exercise_tv_icon);
        Picasso.get().load("https://tusk.systems/trainingapp/icons/" + exercise.getIcon()).into(imExerciseIcon);

        LinearLayout lnRootView = getView().findViewById(R.id.show_exercise_root);
        lnRootView.setBackgroundColor(Color.parseColor(exercise.getInfobox_color()));
    }
}