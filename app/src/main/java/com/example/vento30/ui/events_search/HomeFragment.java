package  com.example.vento30.ui.events_search;

import android.content.Intent;
import android.content.SharedPreferences;
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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import  com.example.vento30.AttendEventActivity;
import  com.example.vento30.CreateEventActivity;
import  com.example.vento30.Event;
import  com.example.vento30.LogInActivity;
import  com.example.vento30.R;
import  com.example.vento30.SignUpActivity;
import  com.example.vento30.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;

    private Button mTestButton;

    // Recycler view.
    private RecyclerView mEventSearchRecyclerView;
    private EventSearchAdapter mAdapter;
    private static List<Event> mEvents; // Events array.

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        container.removeAllViews(); // In order to clean previous views.


        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Preparing Events Recycler View
        mEventSearchRecyclerView = (RecyclerView)root.findViewById(R.id.events_search_recycler_view);
        mEventSearchRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mEvents = new ArrayList<Event>();
        createEvents();
        updateUI();


        FloatingActionButton fab = root.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CreateEventActivity.class);
                startActivity(intent);
            }
        });

        /*
        final TextView textView = binding.textEventsSearch;
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        */

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
        mAdapter = new EventSearchAdapter(mEvents);
        mEventSearchRecyclerView.setAdapter(mAdapter);
    }

    /**
     * Creates a list of events with auxiliary data.
     */
    private void createEvents() {
        // Retrieving the value using its keys the file name
        // must be same in both saving and retrieving the data
        for (int i = 0; i < 15; i++) {
            Event event = new Event("Event "+i, "Description", "Category", "Today", "Tomorrow", "Spain", "No image");
            mEvents.add(event);
        }
    }

    /**
     * Event Adapter for Recycler View.
     */
    private class EventSearchAdapter extends RecyclerView.Adapter<EventHolder> {

        private List<Event> mEvents;

        // Checks the deleted task position.
        //private int mRecentlyDeletedTaskPosition;

        public EventSearchAdapter(List<Event> events) {
            mEvents = events;
        }

        @Override
        public EventHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            return new EventHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder (EventHolder holder, int position) {
            Event event = mEvents.get(position);
            holder.bind(event);
        }

        @Override
        public int getItemCount() {
            return mEvents.size();
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

        private Event mEvent;

        private TextView mTitleTextView;
        private TextView mDateTextView;

        public EventHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_event, parent, false));
            itemView.setOnClickListener(this);

            mTitleTextView = (TextView) itemView.findViewById(R.id.event_title);
            mDateTextView = (TextView) itemView.findViewById(R.id.event_start_date);
        }

        public void bind(Event event) {
            if (event != null) {
                mEvent = event;
                mTitleTextView.setText(mEvent.getTitle());
                mDateTextView.setText(mEvent.getStartDate());
            }
        }

        @Override
        public void onClick(View v) {
            //Toast.makeText(getContext(), "Clicked!", Toast.LENGTH_LONG).show();

            // Intent to new activity.
            Intent intent = new Intent(getActivity(), AttendEventActivity.class);
            intent.putExtra("title", mEvent.getTitle());
            intent.putExtra("description", mEvent.getDescription());
            intent.putExtra("start",mEvent.getStartDate()); // Put anything what you want
            intent.putExtra("end",mEvent.getEndDate()); // Put anything what you want
            intent.putExtra("location",mEvent.getLocation()); // Put anything what you want

            startActivity(intent);
        }
    }
}