package com.example.vento30;

import org.json.JSONException;
import org.json.JSONObject;

public class EventAPI {
    /*
     "id": 10,
             "name": "Drawing Meet Up of the month: January",
             "owner_id": 1,
             "date": "2022-01-01T12:00:00.000Z",
             "image": "https://i.imgur.com/Ou3LVpb.jpg",
             "location": "Barcelona",
             "description": "Come paint with a group of artists. Bring your own
    materials and canvas.",
            "eventStart_date": "2022-01-12T15:00:00.000Z",
            "eventEnd_date": "2022-01-12T17:30:00.000Z",
            "n_participators": 50,
            "slug": null,
            "type": "Arts"

     */
    private int id;
    private String name;
    private int owner_id;
    private String date;
    private String image;
    private String location;
    private String description;
    private String eventStart_date;
    private String eventEnd_date;
    private int n_participators;
    private String type;

    public EventAPI(int id, String name, int owner_id, String date, String image, String location, String description, String eventStart_date, String eventEnd_date, int n_participators, String type) {
        this.id = id;
        this.name = name;
        this.owner_id = owner_id;
        this.date = date;
        this.image = image;
        this.location = location;
        this.description = description;
        this.eventStart_date = eventStart_date;
        this.eventEnd_date = eventEnd_date;
        this.n_participators = n_participators;
        this.type = type;
    }

    public EventAPI(JSONObject newEvent) throws JSONException {
        this.id = newEvent.getInt("id");
        this.name = newEvent.getString("name");
        this.owner_id = newEvent.getInt("owner_id");
        this.date = newEvent.getString("date");
        this.image = newEvent.getString("image");
        this.location = newEvent.getString("location");
        this.description = newEvent.getString("description");
        this.eventStart_date = newEvent.getString("eventStart_date");
        this.eventEnd_date = newEvent.getString("eventEnd_date");
        this.n_participators = newEvent.getInt("n_participators");
        this.type = newEvent.getString("type");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(int owner_id) {
        this.owner_id = owner_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEventStart_date() {
        return eventStart_date;
    }

    public void setEventStart_date(String eventStart_date) {
        this.eventStart_date = eventStart_date;
    }

    public String getEventEnd_date() {
        return eventEnd_date;
    }

    public void setEventEnd_date(String eventEnd_date) {
        this.eventEnd_date = eventEnd_date;
    }

    public int getN_participators() {
        return n_participators;
    }

    public void setN_participators(int n_participators) {
        this.n_participators = n_participators;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
