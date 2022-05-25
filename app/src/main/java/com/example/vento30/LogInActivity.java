package com.example.vento30;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class LogInActivity extends AppCompatActivity {

    private Button mDontHaveAccountButton;
    private Button mLogInButton;

    private EditText mEmail;
    private EditText mPass;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in_activity);
        // Objects.requireNonNull(getSupportActionBar()).hide();

        mEmail = (EditText)findViewById(R.id.log_in_email_et);
        mPass = (EditText)findViewById(R.id.log_in_password_et);

        mLogInButton = (Button) findViewById(R.id.log_in_button);
        mLogInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestQueue queue = Volley.newRequestQueue(LogInActivity.this);
                String email = mEmail.getText().toString();
                String pass = mPass.getText().toString();
                API.logInUser(new LogInCallback() {
                    @Override
                    public void logInOk() {
                        // Get user.
                        API.getUserByEmail(new GetUserCallback() {
                            @Override
                            public void getUserOK() {
                                startActivity(new Intent(LogInActivity.this, MainActivity.class));
                                finish();
                            }

                            @Override
                            public void getUserKO() {
                                Toast.makeText(getApplicationContext(),"Could log in but not get user data!",Toast.LENGTH_SHORT).show();

                            }
                        }, queue, email);
                    }
                    @Override
                    public void logInKO() {
                        Toast.makeText(getApplicationContext(),"Could not log in!",Toast.LENGTH_SHORT).show();
                    }
                },queue, email, pass);
            }
        });

        mDontHaveAccountButton = (Button) findViewById(R.id.dont_have_account_button);
        mDontHaveAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LogInActivity.this, SignUpActivity.class));
                finish();
            }
        });
    }
}
