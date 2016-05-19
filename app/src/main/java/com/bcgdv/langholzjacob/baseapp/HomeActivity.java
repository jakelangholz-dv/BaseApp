package com.bcgdv.langholzjacob.baseapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {

    private LoginFragment loginFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        loginFragment = new LoginFragment();

        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(android.R.id.content, loginFragment).commit();
        } else {
            TextView user_email = (TextView) findViewById(R.id.user_email);
            TextView firebase_uid = (TextView) findViewById(R.id.firebase_uid);

            user_email.setText(auth.getCurrentUser().getEmail());
            firebase_uid.setText(auth.getCurrentUser().getUid());
        }


    }
}
