package com.example.vento30;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class CreateEventActivity extends AppCompatActivity {

    private Button mDiscardEventButton;
    private Button mCreateEventButton;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_event_activity);

        mDiscardEventButton = (Button) findViewById(R.id.discard_event_button);
        mCreateEventButton = (Button) findViewById(R.id.create_event_button);

        mDiscardEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mCreateEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // Category choosing.
        // Get the spinner from the xml.
        Spinner dropdown = findViewById(R.id.category_spinner);
        // Create a list of items for the spinner.
        String[] items = new String[]{"Category 1", "Category 2", "Category 3"};
        // Create an adapter to describe how the items are displayed (dropdown mode).
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        // Set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);

    }
}
