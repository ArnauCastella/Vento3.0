package com.example.vento30;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.android.material.slider.Slider;

import java.util.Objects;

public class ReviewActivity extends AppCompatActivity {

    private Button mPostReview;
    private Button mDiscardReview;

    private EditText mReviewComment;

    private Slider slider;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.review_activity);

        mReviewComment = (EditText) findViewById(R.id.review_text_et);

        Intent intent = getIntent();
        int idEvent = intent.getIntExtra("eventId", 0);

        slider = findViewById(R.id.slider);

        mPostReview = (Button) findViewById(R.id.post_review_button);
        mPostReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //finish();
                RequestQueue queue = Volley.newRequestQueue(ReviewActivity.this);
                String text = mReviewComment.getText().toString();
                int rating = (int) slider.getValue();

                API.postReview(new PostReviewCallback() {
                    @Override
                    public void postReviewOK() {
                        finish();
                    }

                    @Override
                    public void postReviewKO() {
                        Toast.makeText(getApplicationContext(),"Could not post review!",Toast.LENGTH_SHORT).show();
                    }
                }, queue, idEvent, text, rating);
            }
        });

        mDiscardReview = (Button) findViewById(R.id.discard_review_button);
        mDiscardReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
