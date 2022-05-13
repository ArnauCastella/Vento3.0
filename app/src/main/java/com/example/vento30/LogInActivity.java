package com.example.vento30;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class LogInActivity extends AppCompatActivity {

    private Button mDontHaveAccountButton;
    private Button mLogInButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in_activity);
        Objects.requireNonNull(getSupportActionBar()).hide();

        mLogInButton = (Button) findViewById(R.id.log_in_button);
        mLogInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LogInActivity.this, MainActivity.class));
                finish();
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
