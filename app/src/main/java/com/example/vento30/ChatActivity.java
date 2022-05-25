package com.example.vento30;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.vento30.ui.events_search.HomeFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ChatActivity extends AppCompatActivity {

    // Name of the user sending messages to ME.
    private String mReceivedName;
    private int mReceivedID;

    private RecyclerView mMessagesRecyclerView;
    private MessageListAdapter mAdapter;
    private List<MessageAPI> mMessages; // Events array.

    private Button mSendMessageButton;
    private EditText mMessageToSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_activity_xml);

    // Preparing messages recycler view.
        mMessagesRecyclerView = (RecyclerView) findViewById(R.id.recycler_gchat);
        mMessagesRecyclerView.setLayoutManager(new LinearLayoutManager(ChatActivity.this));

        Intent i = getIntent();
        mReceivedName = i.getStringExtra("name");
        mReceivedID = i.getIntExtra("id", 0);

        mMessages = new ArrayList<>();

        getMessages();

        mMessageToSend = (EditText) findViewById(R.id.edit_gchat_message);

        mSendMessageButton = (Button) findViewById(R.id.button_gchat_send);
        mSendMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * content String Content of the message
                 * user_id_send Integer Identifier number associated with sender user
                 * user_id_recived Integer Identifier number associated with receiver user
                 */
                String message = mMessageToSend.getText().toString();
                RequestQueue queue = Volley.newRequestQueue(ChatActivity.this);
                API.postMessage(new PostMessageCallback() {
                    @Override
                    public void postMessageOK() {
                        getMessages();
                        mMessageToSend.getText().clear();
                    }

                    @Override
                    public void postMessageKO() {
                        Toast.makeText(getApplicationContext(),"Could not send messages!",Toast.LENGTH_SHORT).show();
                        mMessageToSend.getText().clear();

                    }
                }, queue, mReceivedID, message);
            }
        });

        // Timer to get messages.

        Timer t = new Timer();
        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                //System.out.println("TIMER");
                getMessages();
            };
        };
        t.scheduleAtFixedRate(tt,500,1000);
    }

    private void updateUI () {
        mAdapter = new MessageListAdapter(ChatActivity.this, mMessages);
        mMessagesRecyclerView.setAdapter(mAdapter);
    }

    private void getMessages() {
        mMessages.clear();
        API.DataManager.getMyChatMessages().clear();
        RequestQueue queue = Volley.newRequestQueue(ChatActivity.this);
        API.getMessages(new GetMessagesCallback() {
            @Override
            public void getMessagesOK() {
                mMessages.addAll(API.DataManager.getMyChatMessages());
                updateUI();
            }

            @Override
            public void getMessagesKO() {
                Toast.makeText(getApplicationContext(),"Could not get messages!",Toast.LENGTH_SHORT).show();
            }
        }, queue, mReceivedID);
    }

    private class MessageListAdapter extends RecyclerView.Adapter {
        private Context mContext;
        private List<MessageAPI> mMessageList;

        // variables to define if message received or sent.
        private static final int VIEW_TYPE_MESSAGE_SENT = 1;
        private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;

        public MessageListAdapter(Context context, List<MessageAPI> messageList) {
            mContext = context;
            mMessageList = messageList;
        }

        @Override
        public int getItemCount() {
            return mMessageList.size();
        }

        // Determines the appropriate ViewType according to the sender of the message.
        @Override
        public int getItemViewType(int position) {
            MessageAPI message = (MessageAPI) mMessageList.get(position);

            if (message.getUser_id_send() == API.DataManager.getMyUser().getId()) {
                // If the current user is the sender of the message
                return VIEW_TYPE_MESSAGE_SENT;
            } else {
                // If some other user sent the message
                return VIEW_TYPE_MESSAGE_RECEIVED;
            }
        }

        // Inflates the appropriate layout according to the ViewType.
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view;

            if (viewType == VIEW_TYPE_MESSAGE_SENT) {
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.me_message, parent, false);
                return new SentMessageHolder(view);
            } else if (viewType == VIEW_TYPE_MESSAGE_RECEIVED) {
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.friend_message, parent, false);
                return new ReceivedMessageHolder(view);
            }

            return null;
        }

        // Passes the message object to a ViewHolder so that the contents can be bound to UI.
        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            MessageAPI message = (MessageAPI) mMessageList.get(position);

            switch (holder.getItemViewType()) {
                case VIEW_TYPE_MESSAGE_SENT:
                    ((SentMessageHolder) holder).bind(message);
                    break;
                case VIEW_TYPE_MESSAGE_RECEIVED:
                    ((ReceivedMessageHolder) holder).bind(message);
            }
        }
    }

    private class ReceivedMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, nameText;

        ReceivedMessageHolder(View itemView) {
            super(itemView);
            messageText = (TextView) itemView.findViewById(R.id.text_gchat_message_other);
            nameText = (TextView) itemView.findViewById(R.id.text_gchat_user_other);
        }

        void bind(MessageAPI message) {
            messageText.setText(message.getContent());

            nameText.setText(mReceivedName);


        }
    }

    private class SentMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText;

        SentMessageHolder(View itemView) {
            super(itemView);

            messageText = (TextView) itemView.findViewById(R.id.text_gchat_message_me);
        }

        void bind(MessageAPI message) {
            messageText.setText(message.getContent());

        }
    }

}
