package com.example.vento30.ui.search_users;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.vento30.API;
import com.example.vento30.EventAPI;
import com.example.vento30.FriendRequestCallback;
import com.example.vento30.GetEventsCallback;
import com.example.vento30.GetFriendsCallback;
import com.example.vento30.SearchUsersNameCallback;
import com.example.vento30.UserAPI;
import com.example.vento30.databinding.FragmentHomeBinding;
import com.example.vento30.databinding.FragmentSearchUsersBinding;
import com.example.vento30.ui.my_events.MyEventsFragment;
import com.example.vento30.ui.my_timeline.MyTimelineFragment;
import com.example.vento30.ui.my_timeline.MyTimelineViewModel;

import com.example.vento30.R;

import java.util.ArrayList;
import java.util.List;

public class SearchUsersFragment extends Fragment {
    private SearchUsersViewModel searchUsersViewModel;
    private FragmentSearchUsersBinding binding;

    private EditText mSearchEditText;
    private Button mSearchButton;

    // Users recycler view.
    // Recycler view.
    private RecyclerView mMyUsersRecyclerView;
    private myUsersAdaper mAdapter;
    private List<UserAPI> mUsersAPI; // Events array.

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        container.removeAllViews(); // In order to clean previous views.


        searchUsersViewModel = new ViewModelProvider(this).get(SearchUsersViewModel.class);

        binding = FragmentSearchUsersBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Preparing Users Recycler View
        mMyUsersRecyclerView = (RecyclerView)root.findViewById(R.id.search_users_recycler_view);
        mMyUsersRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mUsersAPI = new ArrayList<UserAPI>();

        mSearchEditText = (EditText) root.findViewById(R.id.search_bar_users_et);
        mSearchButton = (Button) root.findViewById(R.id.search_user_button);
        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUsers(mSearchEditText.getText().toString()); // Passing the name to find.
                // textView.setText(mSearchEditText.getText().toString());
            }
        });

        return root;
    }

    /**
     * Auxiliary function.
     */

    /**
     * Gets all the users that match a name.
     * Gets all the users that are already friends.
     * If any user who is a friend matches ID, gets removed from potential friend requests.
     */
    private void createUsers(String name) {
        mUsersAPI.clear();
        API.DataManager.getMySearchedUsersAPI().clear();
        API.DataManager.getMyFriendsUsersAPI().clear();
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        API.getFriends(new GetFriendsCallback() {
            @Override
            public void getFriendsOK() {
                API.getUserByName(new SearchUsersNameCallback() {
                    @Override
                    public void searchUsersNameOK() {
                        // Filter received users with my friends.
                        filterFriends();
                    }

                    @Override
                    public void searchUsersNameKO() {
                        mUsersAPI.clear();
                        updateUI();
                    }
                }, queue, name);
            }

            @Override
            public void getFriendsKO() {
                updateUI();
            }
        }, queue);
    }

    private void filterFriends() {
        boolean ok = true;

        for (int i = 0; i < API.DataManager.getMySearchedUsersAPI().size(); i++) {
            for (int j = 0; j < API.DataManager.getMyFriendsUsersAPI().size(); j++) {
                if (API.DataManager.getMySearchedUsersAPI().get(i).getId() ==
                        API.DataManager.getMyFriendsUsersAPI().get(j).getId()
                || API.DataManager.getMySearchedUsersAPI().get(i).getId() == API.DataManager.myUser.getId()) {
                    ok = false;
                }
            }

            if (ok) {
                // If user is not already a friend, add to list.
                mUsersAPI.add(API.DataManager.getMySearchedUsersAPI().get(i));
            } else {
                // Reset variable for next user.
                ok = true;
            }
        }
        // mUsersAPI.addAll(API.DataManager.getMySearchedUsersAPI());
        updateUI();
    }

    private void updateUI () {
        mAdapter = new myUsersAdaper(mUsersAPI);
        mMyUsersRecyclerView.setAdapter(mAdapter);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        mUsersAPI.clear();
        API.DataManager.getMySearchedUsersAPI().clear();
    }

    /**
     * Event Adapter for Recycler View.
     */
    private class myUsersAdaper extends RecyclerView.Adapter<MyUserHolder> {

        private List<UserAPI> mUsersAPI;

        // Checks the deleted task position.
        //private int mRecentlyDeletedTaskPosition;

        public myUsersAdaper(List<UserAPI> users) {
            mUsersAPI = users;
        }

        @Override
        public MyUserHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            return new MyUserHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder (MyUserHolder holder, int position) {
            UserAPI user = mUsersAPI.get(position);
            holder.bind(user);
        }

        @Override
        public int getItemCount() {
            return mUsersAPI.size();
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
    private class MyUserHolder extends RecyclerView.ViewHolder {

        private UserAPI mUserAPI;

        private TextView mUsernameTextView;

        private Button mRequestButton;


        public MyUserHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_search_user, parent, false));

            mUsernameTextView = (TextView) itemView.findViewById(R.id.search_user_username_tv);
            mRequestButton = (Button) itemView.findViewById(R.id.request_friend_button);
            mRequestButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RequestQueue queue = Volley.newRequestQueue(getActivity());
                    // Request user.
                    API.friendRequest(new FriendRequestCallback() {
                        @Override
                        public void sendFriendRequestOK() {
                            mRequestButton.setVisibility(View.GONE);
                        }

                        @Override
                        public void sendFriendRequestKO() {
                            Toast.makeText(getActivity(),"Could send friend request!",Toast.LENGTH_SHORT).show();
                        }
                    }, queue, mUserAPI.getId());
                }
            });
        }

        public void bind(UserAPI user) {
            if (user != null) {
                mUserAPI = user;
                String fullName = mUserAPI.getName() + " " + mUserAPI.getLast_name();
                mUsernameTextView.setText(fullName);
                //mTitleTextView.setText(mEventAPI.getName());
                // mDateTextView.setText(mEventAPI.getEventStart_date());
            }
        }
    }
}