package com.example.vento30;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class SignUpActivity extends AppCompatActivity {


    private Button mAlreadyHaveAccountButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_activity);
        Objects.requireNonNull(getSupportActionBar()).hide();

        mAlreadyHaveAccountButton = (Button) findViewById(R.id.already_have_account_button);
        mAlreadyHaveAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpActivity.this, LogInActivity.class));
                finish();
            }
        });

    }
}
