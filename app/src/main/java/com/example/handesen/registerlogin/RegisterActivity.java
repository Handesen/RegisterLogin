package com.example.handesen.registerlogin;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    boolean password=false;
    boolean name=false;
    boolean username=false;
    boolean email=false;


    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText etEmail = (EditText) findViewById(R.id.etEmail);
        final EditText etName = (EditText) findViewById(R.id.etName);
        final EditText etUsername = (EditText) findViewById(R.id.etUsername);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);
        final Button bRegister = (Button) findViewById(R.id.bRegister);


        etName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (etName.getText().toString().trim()
                            .length() < 6) {

                        etName.setError("A neved túl rövid!");
                        name=false;
                    } else {
                        etName.setError(null);
                        name=true;
                    }
                }
            }
        });
        etUsername.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (etUsername.getText().toString().trim()
                            .length() < 6) {

                        etUsername.setError("A Felhasználóneved túl rövid!");
                        username=false;
                    } else {
                        etUsername.setError(null);
                        username=true;
                    }
                }
            }
        });
        etPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (etPassword.getText().toString().trim()
                            .length() < 6) {

                        etPassword.setError("A jelszó túl rövid!");
                        password=false;
                    } else {
                        etPassword.setError(null);
                        password=true;
                    }
                }
            }
        });
        etEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (!isEmailValid(etEmail.getText().toString())) {

                        etEmail.setError("Az email cím helytelen!");
                        email=false;
                    } else {
                        etEmail.setError(null);
                        email=true;
                    }
                }
            }
        });

        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                etEmail.clearFocus();
                etName.clearFocus();
                etUsername.clearFocus();
                etPassword.clearFocus();
                if (name && username && password && email){
                    bRegister.setError(null);


                final String name = etName.getText().toString();
                final String username = etUsername.getText().toString();
                final String email = etEmail.getText().toString();
                final String password = etPassword.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                RegisterActivity.this.startActivity(intent);
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                builder.setMessage("Register Failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                RegisterRequest registerRequest = new RegisterRequest(name, username, email, password, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);
            }
                else{
                    bRegister.setError("Valamely adat hibás! javítsd!");
                }
            }
        });
    }
}