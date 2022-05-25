package com.example.vento30;

public class Event {
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
    private String title;
    private String description;
    private String category;
    private String startDate;
    private String endDate;
    private String location;
    private String image;

    public Event(String title, String description, String category, String startDate, String endDate, String location, String image) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.startDate = startDate;
        this.endDate = endDate;
        this.location = location;
        this.image = image;
    }

    public Event(String title, String startDate) {
        this.title = title;
        this.startDate = startDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
