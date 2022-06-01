package com.example.vento30;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.vento30.databinding.FragmentHomeBinding;
import com.example.vento30.ui.events_search.HomeFragment;
import com.example.vento30.ui.events_search.HomeViewModel;
import com.google.android.material.chip.Chip;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class SeeReviewsActivity extends AppCompatActivity {
    private TextView eventName;
    private Button goBackButton;
    private Button writeReviewButton;
    private RecyclerView reviewsRecyclerView;
    private ReviewListAdapter mAdapter;
    private static List<UserAPI> mReviews;

    private int mReceivedID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.review_list_activity);

        // Preparing reviews recycler view.
        reviewsRecyclerView = (RecyclerView) findViewById(R.id.recycler_reviews);
        reviewsRecyclerView.setLayoutManager(new LinearLayoutManager(SeeReviewsActivity.this));

        Intent i = getIntent();
        mReceivedID = i.getIntExtra("eventID", 0);
        String mReceivedName = i.getStringExtra("name");

        mReviews = new ArrayList<>();

        getReviews();

        eventName = (TextView) findViewById(R.id.event_review_name);
        eventName.setText(mReceivedName);

        goBackButton = (Button) findViewById(R.id.go_back_button_reviews);
        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        writeReviewButton = (Button) findViewById(R.id.write_review_button);
        writeReviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SeeReviewsActivity.this, ReviewActivity.class);
                i.putExtra("eventId", mReceivedID);
                startActivity(i);
            }
        });
    }

    private void updateUI () {
        mAdapter = new SeeReviewsActivity.ReviewListAdapter(mReviews);
        reviewsRecyclerView.setAdapter(mAdapter);
    }

    private void getReviews() {
        mReviews.clear();
        API.DataManager.getMyReviews().clear();
        RequestQueue queue = Volley.newRequestQueue(SeeReviewsActivity.this);
        API.getReviews(new GetReviewsCallback() {
            @Override
            public void getReviewsOK() {
                mReviews.addAll(API.DataManager.getMyReviews());
                updateUI();
            }

            @Override
            public void getReviewsKO() {
                Toast.makeText(getApplicationContext(),"Could not get reviews!",Toast.LENGTH_SHORT).show();
            }
        }, queue, mReceivedID);
    }

    private class ReviewListAdapter extends RecyclerView.Adapter<SeeReviewsActivity.ReviewHolder> {

        private List<UserAPI> mReviews; // Reviews array.


        public ReviewListAdapter(List<UserAPI> reviews) {
            mReviews = reviews;
        }

        @Override
        public SeeReviewsActivity.ReviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(SeeReviewsActivity.this);
            return new SeeReviewsActivity.ReviewHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder (SeeReviewsActivity.ReviewHolder holder, int position) {
            UserAPI review = mReviews.get(position);
            holder.bind(review);
        }

        @Override
        public int getItemCount() {
            return mReviews.size();
        }
    }

    private class ReviewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private UserAPI mUserAPI;

        private TextView mUsername;
        private TextView mPunctuation;
        private TextView mText;

        public ReviewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_review_item, parent, false));
            itemView.setOnClickListener(this);

            mUsername = (TextView) itemView.findViewById(R.id.review_username);
            mPunctuation = (TextView) itemView.findViewById(R.id.review_score);
            mText = (TextView) itemView.findViewById(R.id.review_text);
        }

        public void bind(UserAPI review) {
            if (review != null) {
                mUserAPI = review;
                mUsername.setText(mUserAPI.getName());
                mPunctuation.setText(String.valueOf(mUserAPI.getPuntuation()));
                mText.setText(mUserAPI.getComentary());
            }
        }

        @Override
        public void onClick(View v) {
        }
    }
}