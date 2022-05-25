package com.example.vento30;

import org.json.JSONException;
import org.json.JSONObject;

public class MessageAPI {
    private int id;
    private String content;
    private int user_id_send;
    private int user_id_recived;
    private String timeStamp;

    public MessageAPI(JSONObject object) throws JSONException {
        this.id = object.getInt("id");
        this.content = object.getString("content");
        this.user_id_send = object.getInt("user_id_send");
        this.user_id_recived = object.getInt("user_id_recived");
        this.timeStamp = object.getString("timeStamp");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getUser_id_send() {
        return user_id_send;
    }

    public void setUser_id_send(int user_id_send) {
        this.user_id_send = user_id_send;
    }

    public int getUser_id_recived() {
        return user_id_recived;
    }

    public void setUser_id_recived(int user_id_recived) {
        this.user_id_recived = user_id_recived;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}
