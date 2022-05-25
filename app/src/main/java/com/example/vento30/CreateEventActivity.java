package com.example.vento30;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.time.format.DateTimeFormatter;

public class CreateEventActivity extends AppCompatActivity {

    private Button mDiscardEventButton;
    private Button mCreateEventButton;

    private EditText mTitle;
    private EditText mImage;
    private EditText mLocation;
    private EditText mDescription;
    private EditText mCategory;
    private DatePicker mStartDate;
    private DatePicker mEndDate;
    private EditText mParticipators;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_event_activity);

        // Category choosing.
        // Get the spinner from the xml.
        Spinner dropdown = findViewById(R.id.category_spinner);
        // Create a list of items for the spinner.
        String[] items = new String[]{"Category 1", "Category 2", "Category 3"};
        // Create an adapter to describe how the items are displayed (dropdown mode).
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        // Set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);

        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                Toast.makeText(CreateEventActivity.this, dropdown.getSelectedItem().toString(), Toast.LENGTH_LONG).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });

        mDiscardEventButton = (Button) findViewById(R.id.discard_event_button);
        mCreateEventButton = (Button) findViewById(R.id.create_event_button);

        mTitle = (EditText) findViewById(R.id.create_title_et);
        mImage = (EditText) findViewById(R.id.create_image_et);
        mLocation = (EditText) findViewById(R.id.create_location_et);
        mDescription = (EditText) findViewById(R.id.create_description_et);
        mStartDate = (DatePicker) findViewById(R.id.start_date_datePicker);
        mEndDate = (DatePicker) findViewById(R.id.end_date_datePicker);
        mParticipators = (EditText) findViewById(R.id.create_participators_et);

        mDiscardEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mCreateEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // String name, String image, String location, String description, String eventStart_date, String eventEnd_date, Integer n_participators, String type
                // finish();
                RequestQueue queue = Volley.newRequestQueue(CreateEventActivity.this);

                // Getting dates
                int dayStart = mStartDate.getDayOfMonth();
                int monthStart = mStartDate.getMonth();
                int yearStart = mStartDate.getYear();

                int dayEnd = mEndDate.getDayOfMonth();
                int monthEnd = mEndDate.getMonth();
                int yearEnd = mEndDate.getYear();

                // Getting other attributes
                String title = mTitle.getText().toString();
                String image = mImage.getText().toString();
                String location = mLocation.getText().toString();
                String description = mDescription.getText().toString();
                String category = dropdown.getSelectedItem().toString();
                String startDate = yearStart+"/"+monthStart+"/"+dayStart;
                String endDate = yearEnd+"/"+monthEnd+"/"+dayEnd;
                int participators = Integer.parseInt(mParticipators.getText().toString());
                API.createEvent(new CreateEventCallback() {
                    @Override
                    public void createEventOK() {
                        finish();
                    }

                    @Override
                    public void createEventKO() {
                        Toast.makeText(getApplicationContext(),"Could not create event!",Toast.LENGTH_SHORT).show();
                    }
                }, queue, title, image, location, description, startDate, endDate, participators, category);

            }
        });


    }
}
