package com.example.handesen.registerlogin;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;



public class LoginActivity extends AppCompatActivity {

    boolean username=false;
    boolean password=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText etUsername = (EditText) findViewById(R.id.etUsername);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);
        final TextView tvRegisterLink = (TextView) findViewById(R.id.tvRegisterLink);
        final Button bLogin = (Button) findViewById(R.id.bSignIn);

        tvRegisterLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });

        etUsername.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (etUsername.getText().toString().trim()
                            .length() < 6) {

                        etUsername.setError("A jelszó túl rövid!");
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
        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                etUsername.clearFocus();
                etPassword.clearFocus();
                if (username && password){
                    bLogin.setError(null);
                final String username = etUsername.getText().toString();
                final String password = etPassword.getText().toString();

                // Response received from the server
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            Boolean success = jsonResponse.getBoolean("success");

                            if (success) {
                                String name = jsonResponse.getString("name");
                                String email = jsonResponse.getString("email");

                                Intent intent = new Intent(LoginActivity.this, UserAreaActivity.class);
                                intent.putExtra("name", name);
                                intent.putExtra("email", email);
                                intent.putExtra("username", username);

                                LoginActivity.this.startActivity(intent);
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                builder.setMessage("Login Failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                LoginRequest loginRequest = new LoginRequest(username, password, responseListener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);
            }else  {
                bLogin.setError("Tölts ki minden mezőt!");
            }}
        });
    }
}
