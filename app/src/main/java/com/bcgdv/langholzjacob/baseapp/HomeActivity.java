package com.bcgdv.langholzjacob.baseapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import model.Entry;
import util.FirebaseHelper;

import static com.bcgdv.langholzjacob.baseapp.R.id.listView;

public class HomeActivity extends AppCompatActivity {

    private LoginFragment m_loginFragment;
    private Button m_dataButton;
    private ListView m_dataList;

    private FirebaseAuth m_auth;
    private FirebaseHelper m_fbHelper;

    private ArrayList<String> listContent;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        m_loginFragment = new LoginFragment();
        listContent = new ArrayList<String>();

        authInit();
        initViews();
    }

    private void initViews() {
        m_dataButton = (Button) findViewById(R.id.dataButton);
        m_dataList = (ListView) findViewById(listView);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listContent);

        m_dataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Entry entry = new Entry(m_auth.getCurrentUser().getUid(), new Date());
                adapter.add("Button clicked: " + entry.getDate().toString());
                m_fbHelper.write(entry, FirebaseHelper.ENTRY);
            }
        });

        m_dataList.setAdapter(adapter);
    }

    private void authInit() {
        m_auth = FirebaseAuth.getInstance();
        if (m_auth.getCurrentUser() == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(android.R.id.content, m_loginFragment).commit();
        } else {
            TextView user_email = (TextView) findViewById(R.id.user_email);
            TextView firebase_uid = (TextView) findViewById(R.id.firebase_uid);

            user_email.setText(m_auth.getCurrentUser().getEmail());
            firebase_uid.setText(m_auth.getCurrentUser().getUid());
        }
        m_fbHelper = new FirebaseHelper();
    }
}
