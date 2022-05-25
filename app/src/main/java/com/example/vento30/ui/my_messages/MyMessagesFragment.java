package  com.example.vento30.ui.my_messages;

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

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.vento30.API;
import com.example.vento30.ChatActivity;
import com.example.vento30.FriendRequestCallback;
import com.example.vento30.GetFriendsCallback;
import com.example.vento30.GetOpenChatsCallback;
import com.example.vento30.ManageFriendRequestCallback;
import com.example.vento30.MyFriendRequestsCallback;
import com.example.vento30.R;
import com.example.vento30.UserAPI;
import  com.example.vento30.databinding.FragmentMyMessagesBinding;
import com.example.vento30.ui.search_users.SearchUsersFragment;

import java.util.ArrayList;
import java.util.List;

public class MyMessagesFragment extends Fragment {

    private MyMessagesViewModel myMessagesViewModel;
    private FragmentMyMessagesBinding binding;

    private RecyclerView mMyFriendRequestsRecyclerView;
    private RecyclerView mMyChatsRecyclerView;
    private myUsersAdaper mAdapter;
    private myChatsAdapter mChatAdapter;
    private List<UserAPI> mUsersAPI; // Events array.
    private List<UserAPI> mUsersFriendsAPI; // Events array.

    private TextView mFriendRequestTextView;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        container.removeAllViews(); // In order to clean previous views.


        myMessagesViewModel =
                new ViewModelProvider(this).get(MyMessagesViewModel.class);

        binding = FragmentMyMessagesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Preparing Users Friend Request Recycler View
        mMyFriendRequestsRecyclerView = (RecyclerView)root.findViewById(R.id.my_friend_requests_recycler_view);
        mMyFriendRequestsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Preparing Chats Recycler View
        mMyChatsRecyclerView = (RecyclerView)root.findViewById(R.id.chats_recycler_view);
        mMyChatsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mFriendRequestTextView = (TextView) root.findViewById(R.id.friend_request_tv);

        mUsersAPI = new ArrayList<UserAPI>();
        mUsersFriendsAPI = new ArrayList<>();

        createFriendRequests();
        createChats();

        return root;
    }

    private void updateUIChats () {
        mChatAdapter = new myChatsAdapter(mUsersFriendsAPI);
        mMyChatsRecyclerView.setAdapter(mChatAdapter);
    }

    private void updateUIRequests () {
        mAdapter = new myUsersAdaper(mUsersAPI);
        mMyFriendRequestsRecyclerView.setAdapter(mAdapter);
    }

    private void createChats() {
        mUsersFriendsAPI.clear();
        API.DataManager.getMyFriendsUsersAPI().clear();
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        API.getFriends(new GetFriendsCallback() {
            @Override
            public void getFriendsOK() {
                mUsersFriendsAPI.addAll(API.DataManager.getMyFriendsUsersAPI());
                updateUIChats();
            }

            @Override
            public void getFriendsKO() {
                updateUIChats();
            }
        }, queue);
    }

    private void createFriendRequests() {
        mUsersAPI.clear();
        API.DataManager.getMyFriendRequestsAPI().clear();
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        API.getFriendRequests(new MyFriendRequestsCallback() {
            @Override
            public void getMyFriendRequestsOK() {
                mUsersAPI.addAll(API.DataManager.getMyFriendRequestsAPI());
                if (mUsersAPI.size() > 0) {
                    String requestString = "You have " + Integer.toString(mUsersAPI.size()) + " friend request(s)";
                    mFriendRequestTextView.setText(requestString);
                } else {
                    mFriendRequestTextView.setVisibility(View.GONE);
                }

                updateUIRequests();
            }

            @Override
            public void getMyFriendRequestsKO() {
                mFriendRequestTextView.setVisibility(View.GONE);
                updateUIRequests();
            }
        }, queue);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    /**
     * Event Adapter for Recycler View Friend Requests.
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
     * Event Holder for Recycler View Friend Requests
     */
    private class MyUserHolder extends RecyclerView.ViewHolder {

        private UserAPI mUserAPI;

        private TextView mUsernameTextView;

        private Button mAcceptRequestButton;

        private Button mDenyRequestButton;


        public MyUserHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_friend_requests, parent, false));

            mUsernameTextView = (TextView) itemView.findViewById(R.id.search_user_username_tv);

            mAcceptRequestButton = (Button) itemView.findViewById(R.id.accept_friend_request_button);
            mAcceptRequestButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RequestQueue queue = Volley.newRequestQueue(getActivity());
                    // Request user.
                    API.acceptFriendRequest(new ManageFriendRequestCallback() {
                        @Override
                        public void acceptDenyFriendRequestOK() {
                            createFriendRequests();
                            createChats();
                        }

                        @Override
                        public void acceptDenyFriendRequestKO() {
                            Toast.makeText(getActivity(),"Could not accept request!",Toast.LENGTH_SHORT).show();

                        }
                    }, queue, mUserAPI.getId());
                }
            });

            mDenyRequestButton = (Button) itemView.findViewById(R.id.deny_friend_request_button);
            mDenyRequestButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RequestQueue queue = Volley.newRequestQueue(getActivity());
                    // Request user.
                    API.denyFriendRequest(new ManageFriendRequestCallback() {
                        @Override
                        public void acceptDenyFriendRequestOK() {
                            createFriendRequests();
                        }

                        @Override
                        public void acceptDenyFriendRequestKO() {
                            Toast.makeText(getActivity(),"Could not deny request!",Toast.LENGTH_SHORT).show();
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

    /**
     * Event Adapter for Recycler View Chats.
     */
    private class myChatsAdapter extends RecyclerView.Adapter<myChatHolder> {

        private List<UserAPI> mUsersAPI;

        // Checks the deleted task position.
        //private int mRecentlyDeletedTaskPosition;

        public myChatsAdapter(List<UserAPI> users) {
            mUsersAPI = users;
        }

        @Override
        public myChatHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            return new myChatHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder (myChatHolder holder, int position) {
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
     * Event Holder for Recycler View Chats.
     */
    private class myChatHolder extends RecyclerView.ViewHolder {

        private UserAPI mUserAPI;

        private TextView mUsernameTextView;

        private Button mChatButton;


        public myChatHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_chat, parent, false));

            mUsernameTextView = (TextView) itemView.findViewById(R.id.search_user_username_tv);

            mChatButton = (Button) itemView.findViewById(R.id.chat_button);
            mChatButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RequestQueue queue = Volley.newRequestQueue(getActivity());
                    // Request user.
                    API.getUserById(new GetOpenChatsCallback() {
                        @Override
                        public void getOpenChatsOK() {
                            Intent i = new Intent(getActivity(), ChatActivity.class);
                            i.putExtra("name", API.DataManager.getMyChattingUser().getName());
                            i.putExtra("id", API.DataManager.getMyChattingUser().getId());
                            startActivity(i);
                            getActivity().finish();
                        }

                        @Override
                        public void getOpenChatsKO() {
                            Toast.makeText(getActivity(),"Could not open chat!",Toast.LENGTH_SHORT).show();
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