package com.bcgdv.langholzjacob.baseapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import model.User;
import util.FirebaseHelper;


public class LoginFragment extends Fragment {

    private FirebaseHelper m_fbHelper;
    private FirebaseAuth m_auth;

    private EditText m_usernameEditText;
    private EditText m_passwordEditText;

    private Button m_signInButton;
    private Button m_signUpButton;

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        m_auth = FirebaseAuth.getInstance();
        m_fbHelper.initDB();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (initViews()) {
            applyOnClickListeners();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private boolean initViews() {
        try {
            m_usernameEditText = (EditText) getView().findViewById(R.id.username);
            m_passwordEditText = (EditText) getView().findViewById(R.id.password);
            m_signInButton = (Button) getView().findViewById(R.id.sign_in);
            m_signUpButton = (Button) getView().findViewById(R.id.sign_up);
            return true;
        } catch (NullPointerException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void applyOnClickListeners() {
        m_signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                checkEmailAndPassword();
                m_auth.signInWithEmailAndPassword(m_usernameEditText.getText().toString(),
                        m_passwordEditText.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        exitFragment(task);
                    }
                });
            }
        });

        m_signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m_auth.createUserWithEmailAndPassword(m_usernameEditText.getText().toString(),
                        m_passwordEditText.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        exitFragment(task);
                    }
                });
            }
        });
    }

    private void checkEmailAndPassword() {
        String email = m_usernameEditText.getText().toString();
        String password = m_passwordEditText.getText().toString();
        if (!email.contains("@") || !email.contains(".")) {
            m_usernameEditText.setError(getString(R.string.invalid_email_address));
        } else if (password.length() < 6) {
            m_passwordEditText.setError(getString(R.string.invalid_password));
        }
    }

    private void exitFragment(@NonNull Task<AuthResult> task) {
        if (task.isSuccessful()) {
            m_fbHelper.write(new User(m_auth.getCurrentUser()), FirebaseHelper.USER);
            getActivity().getSupportFragmentManager().popBackStack();
        }
    }
}
