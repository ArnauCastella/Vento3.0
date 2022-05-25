package  com.example.vento30.ui.my_events;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.vento30.API;
import com.example.vento30.EventAPI;
import com.example.vento30.GetEventsCallback;
import com.example.vento30.MyEventActivity;
import com.example.vento30.MyFutureEvent;
import com.example.vento30.PastEventActivity;
import  com.example.vento30.R;
import com.example.vento30.ReviewActivity;
import  com.example.vento30.databinding.FragmentMyEventsBinding;
import com.google.android.material.chip.Chip;

public class MyEventsFragment extends Fragment {

    private MyEventsViewModel myEventsViewModel;
    private FragmentMyEventsBinding binding;

    // Recycler view.
    private RecyclerView mMyEventsRecyclerView;
    private myEventAdapter mAdapter;
    private static List<EventAPI> mEventsAPI; // Events array.

    // Chips
    private Chip chipPast;
    private Chip chipFuture;
    private Chip chipMyEvents;

    private boolean currentFilter[] = new boolean[3];

    private TextView filterTitle;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        container.removeAllViews(); // In order to clean previous views.


        myEventsViewModel =
                new ViewModelProvider(this).get(MyEventsViewModel.class);

        binding = FragmentMyEventsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        filterTitle = (TextView) root.findViewById(R.id.filter_title_tv);

        // Preparing Events Recycler View
        mMyEventsRecyclerView = (RecyclerView)root.findViewById(R.id.my_events_search_recycler_view);
        mMyEventsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mEventsAPI = new ArrayList<EventAPI>();
        createEvents();

        chipPast = (Chip) root.findViewById(R.id.chipPastAttended);
        chipPast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateFilterFlag("Past");
                mEventsAPI.clear();
                API.DataManager.getMyEventsAPI().clear();
                getAttendedInThePastEvents();
                updateUI();
            }
        });

        chipFuture = (Chip) root.findViewById(R.id.chipFutureAttended);
        chipFuture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateFilterFlag("Future");
                mEventsAPI.clear();
                API.DataManager.getMyEventsAPI().clear();
                getAttendingInTheFutureEvents();
                updateUI();
            }
        });

        chipMyEvents = (Chip) root.findViewById(R.id.chipByMe);
        chipMyEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEventsAPI.clear();
                API.DataManager.getMyEventsAPI().clear();
                createEvents();
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        mEventsAPI.clear();
        API.DataManager.getMyEventsAPI().clear();
    }

    /**
     * Updates the UI of the recycler view.
     */
    private void updateUI () {
        mAdapter = new myEventAdapter(mEventsAPI);
        mMyEventsRecyclerView.setAdapter(mAdapter);
    }

    private void getAttendingInTheFutureEvents () {
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        API.attendingInTheFutureEvents(new GetEventsCallback() {
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

    private void getAttendedInThePastEvents () {
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
     * Updates the flag for better control of the application.
     * @param flag indicating current filter applied.
     */
    private void updateFilterFlag(String flag) {
        switch (flag) {
            case "My":
                currentFilter[0] = true;
                currentFilter[1] = false;
                currentFilter[2] = false;
                String text = "My Events";
                filterTitle.setText(text);
                break;
            case "Future":
                currentFilter[0] = false;
                currentFilter[1] = true;
                currentFilter[2] = false;
                String text2 = "Attending in the Future";
                filterTitle.setText(text2);
                break;
            case "Past":
                currentFilter[0] = false;
                currentFilter[1] = false;
                currentFilter[2] = true;
                String text3 = "Attended in the Past";
                filterTitle.setText(text3);
                break;
        }
    }
    /**
     * Creates a list of the events related to the logged in user.
     */
    private void createEvents() {
        updateFilterFlag("My");
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        API.getMyEvents(new GetEventsCallback() {
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
    private class myEventAdapter extends RecyclerView.Adapter<EventHolder> {

        private List<EventAPI> mEventsAPI;

        // Checks the deleted task position.
        //private int mRecentlyDeletedTaskPosition;

        public myEventAdapter(List<EventAPI> events) {
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
            mEventsAPI.clear();
            API.DataManager.myEventsAPI.clear();


            if (currentFilter[0]) {
                goToMyEventActivity(mEventAPI);
            } else if (currentFilter[1]) {
                goToFutureEventActivity(mEventAPI);
            } else if (currentFilter[2]) {
                goToPastEventActivity(mEventAPI);
            }

        }
    }

    private void goToMyEventActivity(EventAPI mEventAPI) {
        Intent intent = new Intent(getActivity(), MyEventActivity.class);
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

    private void goToFutureEventActivity(EventAPI mEventAPI) {
        Intent intent = new Intent(getActivity(), MyFutureEvent.class);
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

    private void goToPastEventActivity(EventAPI mEventAPI) {
        Intent intent = new Intent(getActivity(), PastEventActivity.class);
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