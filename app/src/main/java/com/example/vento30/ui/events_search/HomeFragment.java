package  com.example.vento30.ui.events_search;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.vento30.API;
import com.example.vento30.EventAPI;
import com.example.vento30.GetEventsCallback;
import com.google.android.material.chip.Chip;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import  com.example.vento30.AttendEventActivity;
import  com.example.vento30.CreateEventActivity;
import  com.example.vento30.Event;
import  com.example.vento30.LogInActivity;
import  com.example.vento30.R;
import  com.example.vento30.SignUpActivity;
import  com.example.vento30.databinding.FragmentHomeBinding;

import org.json.JSONArray;
import org.json.JSONException;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;

    private Button mTestButton;

    // Recycler view.
    private RecyclerView mEventSearchRecyclerView;
    private EventSearchAdapter mAdapter;
    private static List<EventAPI> mEventsAPI; // Events array.

    // Chip
    private Chip chipBest;

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

        mEventsAPI = new ArrayList<EventAPI>();
        createEvents();

        FloatingActionButton fab = root.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CreateEventActivity.class);
                startActivity(intent);
            }
        });

        chipBest = (Chip) root.findViewById(R.id.chip0);
        chipBest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getEventsOrderedByBest();
            }
        });

        return root;
    }

    private void getEventsOrderedByBest() {
        System.out.println(API.DataManager.token);
        API.DataManager.getmEventsAPI().clear();

        RequestQueue queue = Volley.newRequestQueue(getActivity());

        API.orderEventsByBest(new GetEventsCallback() {
            @Override
            public void getAllEventsOK() {
                mEventsAPI.addAll(API.DataManager.getmEventsAPI());
                updateUI();
            }

            @Override
            public void getAllEventsKO() {
                Toast.makeText(getActivity(),"Could get all the events!",Toast.LENGTH_SHORT).show();
            }
        }, queue);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        mEventsAPI.clear();
        API.DataManager.getmEventsAPI().clear();
    }

    /**
     * Updates the UI of the recycler view.
     */
    private void updateUI () {
        mAdapter = new EventSearchAdapter(mEventsAPI);
        mEventSearchRecyclerView.setAdapter(mAdapter);
    }

    /**
     * Creates a list of events with auxiliary data.
     */
    private void createEvents() {
        RequestQueue queue = Volley.newRequestQueue(getActivity());

        API.getAllEvents(new GetEventsCallback() {
            @Override
            public void getAllEventsOK() {
                mEventsAPI.addAll(API.DataManager.getmEventsAPI());
                updateUI();
            }

            @Override
            public void getAllEventsKO() {
                Toast.makeText(getActivity(),"Could not get all the events!",Toast.LENGTH_SHORT).show();

            }
        }, queue);
    }
    /**
     * Event Adapter for Recycler View.
     */
    private class EventSearchAdapter extends RecyclerView.Adapter<EventHolder> {

        private List<EventAPI> mEventsAPI; // Events array.

        // Checks the deleted task position.
        //private int mRecentlyDeletedTaskPosition;

        public EventSearchAdapter(List<EventAPI> events) {
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

        public EventHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_event, parent, false));
            itemView.setOnClickListener(this);

            mTitleTextView = (TextView) itemView.findViewById(R.id.event_title);
            mDateTextView = (TextView) itemView.findViewById(R.id.event_start_date);
        }

        public void bind(EventAPI event) {
            if (event != null) {
                mEventAPI = event;
                mTitleTextView.setText(mEventAPI.getName());
                mDateTextView.setText(mEventAPI.getEventStart_date());
            }
        }

        @Override
        public void onClick(View v) {
            //Toast.makeText(getContext(), "Clicked!", Toast.LENGTH_LONG).show();
            mEventsAPI.clear();
            API.DataManager.mEventsAPI.clear();

            // Intent to new activity.
            Intent intent = new Intent(getActivity(), AttendEventActivity.class);
            intent.putExtra("id", mEventAPI.getId());
            intent.putExtra("title", mEventAPI.getName());
            intent.putExtra("description", mEventAPI.getDescription());
            intent.putExtra("start",mEventAPI.getEventStart_date()); // Put anything what you want
            intent.putExtra("end",mEventAPI.getEventEnd_date()); // Put anything what you want
            intent.putExtra("location",mEventAPI.getLocation()); // Put anything what you want
            intent.putExtra("image", mEventAPI.getImage());
            startActivity(intent);
            getActivity().finish();
        }
    }
}