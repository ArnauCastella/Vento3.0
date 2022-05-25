package com.example.vento30;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

public class MyFutureEvent extends AppCompatActivity {
    private TextView mEventTitle;
    private TextView mEventDescription;
    private TextView mEventStartDate;
    private TextView mEventEndDate;
    private TextView mEventLocation;

    private Button mStopAttendButton;
    private Button mGoBackButton;

    private ImageView mImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_future_event_activity);

        Intent intent = getIntent();

        mImage = (ImageView) findViewById(R.id.attend_event_imageview);

        mEventTitle = (TextView) findViewById(R.id.event_title_tv);
        mEventDescription = (TextView) findViewById(R.id.event_description_tv);
        mEventStartDate = (TextView) findViewById(R.id.event_startdate_tv);
        mEventEndDate = (TextView) findViewById(R.id.event_enddate_tv);
        mEventLocation = (TextView) findViewById(R.id.event_location_tv);

        int id = intent.getIntExtra("id", 0);
        String title = intent.getStringExtra("title");
        String description = intent.getStringExtra("description");
        String startDate = "Start: " + intent.getStringExtra("start");
        String endDate = "End: " + intent.getStringExtra("end");
        String location = intent.getStringExtra("location");
        String image = intent.getStringExtra("image");
        mEventTitle.setText(title);
        mEventDescription.setText(description);
        mEventStartDate.setText(startDate);
        mEventEndDate.setText(endDate);
        mEventLocation.setText(location);

        String extensionJPEG = image.substring(image.length() - 5, image.length());
        String extensionJPG = image.substring(image.length() - 4, image.length());
        String extensionPNG = image.substring(image.length() - 4, image.length());

        boolean jpg = extensionJPG.matches(".jpg");
        boolean png = extensionPNG.matches(".png");
        boolean jpeg = extensionJPEG.matches(".jpeg");

        if (jpg || png || jpeg) {
            Picasso.get().load(image).into(mImage);
        } else {
            mImage.setImageResource(R.drawable.festival_image);
        }

        mStopAttendButton = (Button) findViewById(R.id.stop_attend_button);
        mGoBackButton = (Button) findViewById(R.id.go_back_button);


        mStopAttendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestQueue queue = Volley.newRequestQueue(MyFutureEvent.this);
                API.removeAttendance(new RemoveAttendanceCallback() {
                    @Override
                    public void removeAttendanceOK() {
                        startActivity(new Intent(MyFutureEvent.this, MainActivity.class));
                    }

                    @Override
                    public void removeAttendanceKO() {
                        Toast.makeText(getApplicationContext(),"Could not remove attendance from event!",Toast.LENGTH_SHORT).show();
                    }
                }, queue, id);
            }
        });

        mGoBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MyFutureEvent.this, MainActivity.class));
            }
        });


    }

}
