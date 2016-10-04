package com.example.handesen.registerlogin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class UserAreaActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_area);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String username = intent.getStringExtra("username");
        String email = intent.getStringExtra("email");

        TextView tvWelcomeMsg = (TextView) findViewById(R.id.tvWelcomeMsg);
        EditText etUsername = (EditText) findViewById(R.id.etUsername);
        EditText etEmail = (EditText) findViewById(R.id.etEmail);

        // Display user details
        String message = "Légy üdvözölve :" + name;
        tvWelcomeMsg.setText(message);
        etUsername.setText(username );
        etEmail.setText(email);
    }
}