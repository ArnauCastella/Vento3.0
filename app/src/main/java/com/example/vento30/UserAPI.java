package com.example.vento30;

import org.json.JSONException;
import org.json.JSONObject;

public class UserAPI {
    private int id;
    private String name;
    private String last_name;
    private String email;
    private String password;
    private String image;
    private int puntuation;
    private String comentary;

    public UserAPI(JSONObject object) throws JSONException {
        this.id = object.getInt("id");
        this.name = object.getString("name");
        this.last_name = object.getString("last_name");
        this.email = object.getString("email");
        // this.password = object.getString("password");
        this.image = object.getString("image");
    }

    public UserAPI(JSONObject object, int review) throws JSONException {
        this.id = object.getInt("id");
        this.name = object.getString("name");
        this.last_name = object.getString("last_name");
        this.email = object.getString("email");
        // this.password = object.getString("password");
        this.puntuation = object.getInt("puntuation");
        this.comentary = object.getString("comentary");
    }

    /**
     * Friend Request
     * @param object
     * @throws JSONException
     */
    public UserAPI(JSONObject object, boolean friendRequest) throws JSONException {
        this.id = object.getInt("id");
        this.name = object.getString("name");
        this.last_name = object.getString("last_name");
        this.email = object.getString("email");
        this.image = object.getString("image");
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

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getPuntuation() {
        return puntuation;
    }

    public String getComentary() {
        return comentary;
    }

    @Override
    public String toString() {
        return "UserAPI{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", image='" + image + '\'' +
                ", puntuation=" + puntuation +
                ", comentary='" + comentary + '\'' +
                '}';
    }
}
