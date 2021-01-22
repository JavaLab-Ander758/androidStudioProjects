package com.andyeseapps.treningsprogram_navigation.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.andyeseapps.treningsprogram_navigation.R;
import com.andyeseapps.treningsprogram_navigation.data.ProgramData;
import com.andyeseapps.treningsprogram_navigation.entity.Program;
import com.andyeseapps.treningsprogram_navigation.utils.Utils;
import com.andyeseapps.treningsprogram_navigation.viewmodel.ProgramViewModel;

import java.util.ArrayList;
import java.util.List;

public class ProgramFragment extends Fragment {
    private boolean downloadingProgramState = false;
    private ProgramViewModel programViewModel;

    public ProgramFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_program, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        if (!Utils.isLoggedIn(getActivity()))
            NavHostFragment.findNavController(this).navigate(R.id.userFragment, null);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Utils.isLoggedIn(getActivity())) {
            programViewModel = new ViewModelProvider(getActivity()).get(ProgramViewModel.class);
            subscribe();
            downloadPrograms();
        }
    }

    /**
     * Subscribe for download
     */
    private void subscribe() {
        final Observer<ProgramData> programDataObserver = programData -> {
            if (downloadingProgramState) {
                downloadingProgramState = false;
                listPrograms();
            }
        };
        programViewModel.getProgramData().observe(ProgramFragment.this, programDataObserver);
    }

    /**
     * Downloads all programs to programViewModel
     */
    private void downloadPrograms() {
        Utils.displayToast(getActivity(), getString(R.string.downloading));
        if (programViewModel != null) {
            programViewModel.requestDownload(getActivity().getApplicationContext());
            downloadingProgramState = true;
        } else
            downloadingProgramState = false;
    }

    /**
     * List programs in a ListView and add onItemClick listeners to each item
     */
    private void listPrograms() {
        final List<Program> programs = programViewModel.getProgramData().getValue().getPrograms();
        final ArrayList<String> programNames = new ArrayList<>();
        if (programs != null)
            for (int i = 0; i < programs.size(); i++)
                programNames.add(programs.get(i).getName());

        Log.d(Utils.DEBUG_LOGGER_TAG, "[programNames]= " + Utils.stringArrayListToString(programNames));

        ListView listView = getView().findViewById(R.id.listId);
        Utils.setAdapterToListView(getActivity(), programNames, listView);
        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            Program program = new Program(programs.get(i).getId(), programs.get(i).getName(), programs.get(i).getDescription());
            ProgramFragmentDirections.ActionProgramFragmentToExerciseFragment argWithProgramObj = ProgramFragmentDirections.actionProgramFragmentToExerciseFragment(program);
            NavController navController = Navigation.findNavController(view);
            navController.navigate(argWithProgramObj);
        });
    }
}