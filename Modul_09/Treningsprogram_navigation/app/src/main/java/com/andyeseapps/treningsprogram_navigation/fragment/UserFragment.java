package com.andyeseapps.treningsprogram_navigation.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.andyeseapps.treningsprogram_navigation.R;
import com.andyeseapps.treningsprogram_navigation.utils.Utils;

public class UserFragment extends Fragment {
    private Button loginButton, logoutButton;


    public UserFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        final EditText usernameEditText = view.findViewById(R.id.login_usernameET);
        final EditText passwordEditText = view.findViewById(R.id.login_passwordET);
        loginButton = view.findViewById(R.id.login_loginButton);
        logoutButton = view.findViewById(R.id.fragmentUserLogoutButton);

        // Log in and hide keyboard
        loginButton.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            if (Utils.isLoginDetailsCorrect(getActivity(), username, password)) {
                Utils.setLoggedIn(true, getActivity());
                Utils.displayToast(getActivity(), getString(R.string.login_message));
                ((InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getView().getWindowToken(), 0);
                Navigation.findNavController(getView()).navigate(R.id.programFragment, null, Utils.getAnimation());
            } else
                Utils.displayToast(getActivity(), getString(R.string.login_wrong_credentials));
            updateButtons();
        });
        updateButtons();

        // Log out
        logoutButton.setOnClickListener(v -> {
            Utils.setLoggedIn(false, getActivity());
            updateButtons();
        });
        updateButtons();

        // Register user
        Button signUpButton = view.findViewById(R.id.login_signUpButton);
        signUpButton.setOnClickListener(v -> Navigation.findNavController(getView()).navigate(R.id.signUpFragment, null, Utils.getAnimation()));
    }

    private void updateButtons() {
        Utils.setButtonVisibility(loginButton, !Utils.isLoggedIn(getActivity()));
        Utils.setButtonVisibility(logoutButton, Utils.isLoggedIn(getActivity()));
    }
}