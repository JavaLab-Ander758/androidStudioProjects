package com.andyeseapps.treningsprogram_navigation.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.andyeseapps.treningsprogram_navigation.R;
import com.andyeseapps.treningsprogram_navigation.utils.Utils;

public class SignUpFragment extends Fragment {

    public SignUpFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_signup, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        final EditText usernameEditText = view.findViewById(R.id.signUp_usernameET);
        final EditText passwordEditText = view.findViewById(R.id.signUp_passwordET);
        final EditText passwordConfirmEditText = view.findViewById(R.id.signUp_passwordConfirmET);

        // Create new user
        Button loginButton = view.findViewById(R.id.signUp_createAccountButton);
        loginButton.setOnClickListener(view1 -> {
            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            String passwordConfirm = passwordConfirmEditText.getText().toString();

            if (password.equals(passwordConfirm)) {
                if (Utils.setLoginDetails(getActivity(), username, password))
                    Utils.setLoggedIn(false, getActivity());
                Navigation.findNavController(view1).navigate(R.id.userFragment, null, Utils.getAnimation());
            } else
                Utils.displayToast(getActivity(), getString(R.string.signUp_password_mismatch));
        });
    }
}