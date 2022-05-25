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

public class SignUpActivity extends AppCompatActivity {


    private Button mAlreadyHaveAccountButton;
    private Button mSignUp;


    private EditText mEmail;
    private EditText mName;
    private EditText mSurname;
    private EditText mPassword;
    private EditText mConfirmPassword;
    private EditText mImage;

    private boolean signUpFlag;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_activity);

        signUpFlag = false;

        // Edit texts for Sign Up.
        mEmail   = (EditText)findViewById(R.id.sign_up_email_et);
        mName   = (EditText)findViewById(R.id.sign_up_first_name_et);
        mSurname   = (EditText)findViewById(R.id.sign_up_surname_et);
        mPassword   = (EditText)findViewById(R.id.sign_up_password_et);
        mConfirmPassword   = (EditText)findViewById(R.id.sign_up_confirm_password_et);
        mImage   = (EditText)findViewById(R.id.sign_up_image_et);

        mAlreadyHaveAccountButton = (Button) findViewById(R.id.already_have_account_button);
        mAlreadyHaveAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpActivity.this, LogInActivity.class));
                finish();
            }
        });

        mSignUp = (Button) findViewById(R.id.sign_up_button);
        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestQueue queue = Volley.newRequestQueue(SignUpActivity.this);
                String email = mEmail.getText().toString();
                String name = mName.getText().toString();
                String surname = mSurname.getText().toString();
                String pass = mPassword.getText().toString();
                String confirmPass = mConfirmPassword.getText().toString();
                String image = mImage.getText().toString();
                if (pass.equals(confirmPass)) {
                    API.registerUserToApi(new SignUpCallback() {
                        @Override
                        public void signUpOK() {
                            startActivity(new Intent(SignUpActivity.this, LogInActivity.class));
                            finish();
                        }
                        @Override
                        public void signUpKO() {
                            Toast.makeText(getApplicationContext(),"Could not sign up!",Toast.LENGTH_SHORT).show();
                        }
                    }, queue, name, surname, email, pass, image);
                } else {
                    Toast.makeText(getApplicationContext(),"Passwords do not match!",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
