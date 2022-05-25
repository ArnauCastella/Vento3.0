package com.example.vento30;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PastEventActivity extends AppCompatActivity {

    private TextView mEventTitle;
    private TextView mEventDescription;
    private TextView mEventStartDate;
    private TextView mEventEndDate;
    private TextView mEventLocation;

    private Button mRatingButton;
    private Button mGoBackButton;

    private ImageView mImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.past_event_activity);

        Intent intent = getIntent();

        mImage = (ImageView) findViewById(R.id.attend_event_imageview);

        mImage.setImageResource(R.drawable.festival_image);


        mEventTitle = (TextView) findViewById(R.id.event_title_tv);
        mEventDescription = (TextView) findViewById(R.id.event_description_tv);
        mEventStartDate = (TextView) findViewById(R.id.event_startdate_tv);
        mEventEndDate = (TextView) findViewById(R.id.event_enddate_tv);
        mEventLocation = (TextView) findViewById(R.id.event_location_tv);

        int id = intent.getIntExtra("id", 0);
        String title = intent.getStringExtra("title") + " (Past)";
        String description = intent.getStringExtra("description");
        String startDate = "Start: " + intent.getStringExtra("start");
        String endDate = "End: " + intent.getStringExtra("end");
        String location = intent.getStringExtra("location");
        mEventTitle.setText(title);
        mEventDescription.setText(description);
        mEventStartDate.setText(startDate);
        mEventEndDate.setText(endDate);
        mEventLocation.setText(location);

        mRatingButton = (Button) findViewById(R.id.rating_button);
        mGoBackButton = (Button) findViewById(R.id.go_back_button);

        mRatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PastEventActivity.this, ReviewActivity.class);
                i.putExtra("eventId", id);
                startActivity(i);
            }
        });

        mGoBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

}
