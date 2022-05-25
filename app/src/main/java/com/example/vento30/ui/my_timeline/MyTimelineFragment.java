package com.example.vento30.ui.my_timeline;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.vento30.API;
import com.example.vento30.Event;
import com.example.vento30.EventAPI;
import com.example.vento30.FriendRequestCallback;
import com.example.vento30.GetEventsCallback;
import com.example.vento30.PastEventActivity;
import com.example.vento30.R;
import com.example.vento30.databinding.FragmentMyTimelineBinding;

public class MyTimelineFragment extends Fragment {

    private MyTimelineViewModel myTimelineViewModel;
    private FragmentMyTimelineBinding binding;

    // Timeline Recycler View
    private RecyclerView mTimelineRecyclerView;
    private myTimelineAdapter mAdapter;
    // private static List<Event> mEvents; // Events array.
    private static List<EventAPI> mEventsAPI; // Events array.

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        container.removeAllViews(); // In order to clean previous views.


        myTimelineViewModel =
                new ViewModelProvider(this).get(MyTimelineViewModel.class);

        binding = FragmentMyTimelineBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Preparing Events Recycler View
        mTimelineRecyclerView = (RecyclerView)root.findViewById(R.id.timeline_recycler_view);
        mTimelineRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mEventsAPI = new ArrayList<EventAPI>();
        createEvents();
        updateUI();


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    /**
     * Updates the UI of the recycler view.
     */
    private void updateUI () {
        mAdapter = new myTimelineAdapter(mEventsAPI);
        mTimelineRecyclerView.setAdapter(mAdapter);
    }

    /**
     * Creates a list of events with auxiliary data.
     */
    private void createEvents() {
        // API.DataManager.getMyEventsAPI().clear();
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        API.attendedInThePastEvents(new GetEventsCallback() {
            @Override
            public void getAllEventsOK() {
                mEventsAPI.addAll(API.DataManager.getMyEventsAPI());
                updateUI();
            }

            @Override
            public void getAllEventsKO() {
                Toast.makeText(getActivity(),"Could not get my events!",Toast.LENGTH_SHORT).show();
            }
        }, queue);
    }



    /**
     * Event Adapter for Recycler View.
     */
    private class myTimelineAdapter extends RecyclerView.Adapter<EventHolder> {

        private List<EventAPI> mEventsAPI;

        // Checks the deleted task position.
        //private int mRecentlyDeletedTaskPosition;

        public myTimelineAdapter(List<EventAPI> events) {
            mEventsAPI = events;
        }

        @Override
        public EventHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            return new EventHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder (EventHolder holder, int position) {
            EventAPI event = mEventsAPI.get(position);
            holder.bind(event);
        }

        @Override
        public int getItemCount() {
            return mEventsAPI.size();
        }

        /*
        public void deleteItem(int position) {
            Task mRecentlyDeletedTask = mTasks.get(position);
            mRecentlyDeletedTaskPosition = position;
            mTasks.remove(position);
            notifyItemRemoved(position);
            updateSharedPreferences();
        }
         */
    }

    /**
     * Event Holder for Recycler View
     */
    private class EventHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private EventAPI mEventAPI;

        private TextView mTitleTextView;
        private TextView mDateTextView;

        private Button mDetailsButton;

        public EventHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.timeline_list_item_event, parent, false));
            itemView.setOnClickListener(this);

            mTitleTextView = (TextView) itemView.findViewById(R.id.timeline_event_title);
            mDateTextView = (TextView) itemView.findViewById(R.id.timeline_event_start_date);

            mDetailsButton = (Button) itemView.findViewById(R.id.details_button);
            mDetailsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), PastEventActivity.class);
                    intent.putExtra("title", mEventAPI.getName());
                    intent.putExtra("description", mEventAPI.getDescription());
                    intent.putExtra("start",mEventAPI.getEventStart_date()); // Put anything what you want
                    intent.putExtra("end",mEventAPI.getEventEnd_date()); // Put anything what you want
                    intent.putExtra("location",mEventAPI.getLocation()); // Put anything what you want

                    startActivity(intent);
                }
            });
        }

        public void bind(EventAPI event) {
            if (event != null) {
                mEventAPI = event;
                mTitleTextView.setText(mEventAPI.getName());
                String formattedDate = mEventAPI.getEventStart_date().substring(0, 10);
                mDateTextView.setText(formattedDate);
            }
        }

        @Override
        public void onClick(View v) {
        }
    }
}
