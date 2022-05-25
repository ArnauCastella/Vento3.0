package com.example.vento30;

import android.content.Intent;
import android.provider.ContactsContract;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class API {
    private int mStatusCode = 0;
    /**
     * Registration of a user.
     * @param queue request queue.
     * @param name of the user.
     * @param last_name of the user.
     * @param email of the user.
     * @param password of the user.
     * @param image of the user.
     */
    public static void registerUserToApi(SignUpCallback callback, RequestQueue queue, String name, String last_name, String email, String password, String image) {
        // Creating JSON Object
        JSONObject object = new JSONObject();
        try {
            //input your API parameters
            object.put("name", name);
            object.put("last_name", last_name);
            object.put("email", email);
            object.put("password", password);
            object.put("image",image);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // url to post our data
        String url = "http://puigmal.salle.url.edu/api/v2/users";

        // creating a new variable for our request queue
        // RequestQueue queue = Volley.newRequestQueue(MainActivity.this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, object,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        callback.signUpOK();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.signUpKO();
            }
        });
        queue.add(jsonObjectRequest);
    }

    public static void getUserByEmail(GetUserCallback callback, RequestQueue queue, String emailToFind) {
        String email = "?s="+emailToFind;

        String url = ("http://puigmal.salle.url.edu/api/v2/users/search" + email);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //Adding each element of JSON array into ArrayList
                        try {
                            DataManager.myUser = new UserAPI(response.getJSONObject(0));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        callback.getUserOK();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.getUserKO();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                // below line we are creating a map for
                // storing our values in key and value pair.
                Map<String, String> params = new HashMap<String, String>();

                // on below line we are passing our key
                // and value pair to our parameters.
                params.put("Authorization", DataManager.token);

                // at last we are
                // returning our params.
                return params;
            }
        };

        // Add the request to the RequestQueue.
        queue.add(request);
    }

    public static void getUserById(GetOpenChatsCallback callback, RequestQueue queue, int id) {
        String url = ("http://puigmal.salle.url.edu/api/v2/users/"+Integer.toString(id));

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //Adding each element of JSON array into ArrayList
                        try {
                            DataManager.myChattingUser = new UserAPI(response.getJSONObject(0));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        callback.getOpenChatsOK();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.getOpenChatsKO();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                // below line we are creating a map for
                // storing our values in key and value pair.
                Map<String, String> params = new HashMap<String, String>();

                // on below line we are passing our key
                // and value pair to our parameters.
                params.put("Authorization", DataManager.token);

                // at last we are
                // returning our params.
                return params;
            }
        };

        // Add the request to the RequestQueue.
        queue.add(request);
    }

    public static void postReview(PostReviewCallback callback, RequestQueue queue, int idEvent, String text, int rating) {
        String url = ("http://puigmal.salle.url.edu/api/v2/events/"+Integer.toString(idEvent))+"/assistances";
        System.out.println(url);
        // Creating JSON Object
        JSONObject object = new JSONObject();
        try {
            //input your API parameters
            object.put("puntuation", rating);
            object.put("comentary", text);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        /*
        StringRequest request = new StringRequest(Request.Method.PUT, url, object, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                callback.postReviewOK();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.postReviewKO();
            }
        }) {
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                if (response != null) {
                    mStatusCode = response.statusCode;
                }
                return super.parseNetworkResponse(response);
            }
        };

        queue.add(request);

         */
    }

    public static void getFriends(GetFriendsCallback callback, RequestQueue queue) {
        String url = ("http://puigmal.salle.url.edu/api/v2/friends");

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //Adding each element of JSON array into ArrayList
                        for (int i=0;i<response.length();i++){
                            //Adding each element of JSON array into ArrayList
                            try {
                                DataManager.getMyFriendsUsersAPI().add(new UserAPI(response.getJSONObject(i)));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        callback.getFriendsOK();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.getFriendsKO();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                // below line we are creating a map for
                // storing our values in key and value pair.
                Map<String, String> params = new HashMap<String, String>();

                // on below line we are passing our key
                // and value pair to our parameters.
                params.put("Authorization", DataManager.token);

                // at last we are
                // returning our params.
                return params;
            }
        };

        // Add the request to the RequestQueue.
        queue.add(request);
    }

    public static void getMyChats(GetOpenChatsCallback callback, RequestQueue queue) {
        DataManager.getMyOpenChatsUsersAPI().clear();
        String url = ("http://puigmal.salle.url.edu/api/v2/message/users");

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //Adding each element of JSON array into ArrayList
                        for (int i=0;i<response.length();i++){
                            //Adding each element of JSON array into ArrayList
                            try {
                                DataManager.getMyOpenChatsUsersAPI().add(new UserAPI(response.getJSONObject(i)));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        callback.getOpenChatsOK();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.getOpenChatsKO();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                // below line we are creating a map for
                // storing our values in key and value pair.
                Map<String, String> params = new HashMap<String, String>();

                // on below line we are passing our key
                // and value pair to our parameters.
                params.put("Authorization", DataManager.token);

                // at last we are
                // returning our params.
                return params;
            }
        };

        // Add the request to the RequestQueue.
        queue.add(request);
    }

    public static void getFriendRequests(MyFriendRequestsCallback callback, RequestQueue queue) {
        System.out.println(DataManager.token);
        String url = ("http://puigmal.salle.url.edu/api/v2/friends/requests");

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //Adding each element of JSON array into ArrayList
                        for (int i=0;i<response.length();i++){
                            //Adding each element of JSON array into ArrayList
                            try {
                                DataManager.getMyFriendRequestsAPI().add(new UserAPI(response.getJSONObject(i), true));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        callback.getMyFriendRequestsOK();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.getMyFriendRequestsKO();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                // below line we are creating a map for
                // storing our values in key and value pair.
                Map<String, String> params = new HashMap<String, String>();

                // on below line we are passing our key
                // and value pair to our parameters.
                params.put("Authorization", DataManager.token);

                // at last we are
                // returning our params.
                return params;
            }
        };

        // Add the request to the RequestQueue.
        queue.add(request);
    }

    public static void friendRequest(FriendRequestCallback callback, RequestQueue queue, int id) {
        String url = ("http://puigmal.salle.url.edu/api/v2/friends/" + Integer.toString(id));

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        callback.sendFriendRequestOK();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.sendFriendRequestKO();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                // below line we are creating a map for
                // storing our values in key and value pair.
                Map<String, String> params = new HashMap<String, String>();

                // on below line we are passing our key
                // and value pair to our parameters.
                params.put("Authorization", DataManager.token);

                // at last we are
                // returning our params.
                return params;
            }
        };

        queue.add(jsonObjectRequest);
    }

    public static void acceptFriendRequest(ManageFriendRequestCallback callback, RequestQueue queue, int id) {
        String url = ("http://puigmal.salle.url.edu/api/v2/friends/" + Integer.toString(id));

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        callback.acceptDenyFriendRequestOK();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.acceptDenyFriendRequestKO();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                // below line we are creating a map for
                // storing our values in key and value pair.
                Map<String, String> params = new HashMap<String, String>();

                // on below line we are passing our key
                // and value pair to our parameters.
                params.put("Authorization", DataManager.token);

                // at last we are
                // returning our params.
                return params;
            }
        };

        queue.add(jsonObjectRequest);
    }

    public static void denyFriendRequest(ManageFriendRequestCallback callback, RequestQueue queue, int id) {
        String url = ("http://puigmal.salle.url.edu/api/v2/friends/" + Integer.toString(id));

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.DELETE, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        callback.acceptDenyFriendRequestOK();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.acceptDenyFriendRequestKO();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                // below line we are creating a map for
                // storing our values in key and value pair.
                Map<String, String> params = new HashMap<String, String>();

                // on below line we are passing our key
                // and value pair to our parameters.
                params.put("Authorization", DataManager.token);

                // at last we are
                // returning our params.
                return params;
            }
        };

        queue.add(jsonObjectRequest);
    }

    public static void getUserByName(SearchUsersNameCallback callback, RequestQueue queue, String nameToFind) {
        String name = "?s="+nameToFind;

        String url = ("http://puigmal.salle.url.edu/api/v2/users/search" + name);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //Adding each element of JSON array into ArrayList
                        for (int i=0;i<response.length();i++){
                            //Adding each element of JSON array into ArrayList
                            try {
                                DataManager.getMySearchedUsersAPI().add(new UserAPI(response.getJSONObject(i)));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        callback.searchUsersNameOK();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.searchUsersNameKO();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                // below line we are creating a map for
                // storing our values in key and value pair.
                Map<String, String> params = new HashMap<String, String>();

                // on below line we are passing our key
                // and value pair to our parameters.
                params.put("Authorization", DataManager.token);

                // at last we are
                // returning our params.
                return params;
            }
        };

        // Add the request to the RequestQueue.
        queue.add(request);
    }

    /**
     * Logging in a user.
     * @param callback function for control.
     * @param queue for the API request.
     * @param email of the user.
     * @param password of the user.
     */
    public static void logInUser(LogInCallback callback, RequestQueue queue, String email, String password) {
        // Creating JSON Object
        JSONObject object = new JSONObject();
        try {
            //input your API parameters
            object.put("email", email);
            object.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // url to post our data
        String url = "http://puigmal.salle.url.edu/api/v2/users/login";

        // creating a new variable for our request queue
        // RequestQueue queue = Volley.newRequestQueue(LogInActivity.this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, object,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            DataManager.token = "Bearer " + response.getString("accessToken");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        callback.logInOk();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.logInKO();
            }
        });
        queue.add(jsonObjectRequest);
    }

    public static void attendingInTheFutureEvents(GetEventsCallback callback, RequestQueue queue) {
        String url = "http://puigmal.salle.url.edu/api/v2/users/"+String.valueOf(DataManager.getMyUser().getId())+"/assistances/future";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i=0;i<response.length();i++){
                            //Adding each element of JSON array into ArrayList
                            try {
                                DataManager.getMyEventsAPI().add(new EventAPI(response.getJSONObject(i)));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        callback.getAllEventsOK();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.getAllEventsKO();
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                // below line we are creating a map for
                // storing our values in key and value pair.
                Map<String, String> params = new HashMap<String, String>();

                // on below line we are passing our key
                // and value pair to our parameters.
                params.put("Authorization", DataManager.token);

                // at last we are
                // returning our params.
                return params;
            }
        };

        // Add the request to the RequestQueue.
        queue.add(request);
    }

    public static void removeAttendance(RemoveAttendanceCallback callback, RequestQueue queue, int id) {
        // url to post our data
        String url = "http://puigmal.salle.url.edu/api/v2/events/"+Integer.toString(id)+"/assistances";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.DELETE, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        callback.removeAttendanceOK();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.removeAttendanceKO();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                // below line we are creating a map for
                // storing our values in key and value pair.
                Map<String, String> params = new HashMap<String, String>();

                // on below line we are passing our key
                // and value pair to our parameters.
                params.put("Authorization", DataManager.token);

                // at last we are
                // returning our params.
                return params;
            }
        };

        queue.add(jsonObjectRequest);
    }

    public static void attendedInThePastEvents(GetEventsCallback callback, RequestQueue queue) {
        String url = "http://puigmal.salle.url.edu/api/v2/users/"+String.valueOf(DataManager.getMyUser().getId())+"/assistances/finished";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i=0;i<response.length();i++){
                            //Adding each element of JSON array into ArrayList
                            try {
                                DataManager.getMyEventsAPI().add(new EventAPI(response.getJSONObject(i)));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        callback.getAllEventsOK();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.getAllEventsKO();
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                // below line we are creating a map for
                // storing our values in key and value pair.
                Map<String, String> params = new HashMap<String, String>();

                // on below line we are passing our key
                // and value pair to our parameters.
                params.put("Authorization", DataManager.token);

                // at last we are
                // returning our params.
                return params;
            }
        };

        // Add the request to the RequestQueue.
        queue.add(request);
    }

    public static void attendEvent(AttendEventCallback callback, RequestQueue queue, int id) {
        // url to post our data
        String url = "http://puigmal.salle.url.edu/api/v2/events/"+String.valueOf(id)+"/assistances";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        callback.attendEventOK();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.attendEventKO();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                // below line we are creating a map for
                // storing our values in key and value pair.
                Map<String, String> params = new HashMap<String, String>();

                // on below line we are passing our key
                // and value pair to our parameters.
                params.put("Authorization", DataManager.token);

                // at last we are
                // returning our params.
                return params;
            }
        };

        queue.add(jsonObjectRequest);
    }

    public static void getAllEvents(GetEventsCallback callback, RequestQueue queue) {
        String url = "http://puigmal.salle.url.edu/api/v2/events";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i=0;i<response.length();i++){
                            //Adding each element of JSON array into ArrayList
                            try {
                                DataManager.mEventsAPI.add(new EventAPI(response.getJSONObject(i)));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        callback.getAllEventsOK();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.getAllEventsKO();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // below line we are creating a map for
                // storing our values in key and value pair.
                Map<String, String> params = new HashMap<String, String>();

                // on below line we are passing our key
                // and value pair to our parameters.
                params.put("Authentication", DataManager.token);

                // at last we are
                // returning our params.
                return params;
            }
        };

        // Add the request to the RequestQueue.
        queue.add(request);
    }

    public static void getMessages(GetMessagesCallback callback, RequestQueue queue, int id) {
        String url = "http://puigmal.salle.url.edu/api/v2/messages/"+Integer.toString(id);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i=0;i<response.length();i++){
                            //Adding each element of JSON array into ArrayList
                            try {
                                DataManager.getMyChatMessages().add(new MessageAPI(response.getJSONObject(i)));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        callback.getMessagesOK();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.getMessagesKO();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                // below line we are creating a map for
                // storing our values in key and value pair.
                Map<String, String> params = new HashMap<String, String>();

                // on below line we are passing our key
                // and value pair to our parameters.
                params.put("Authorization", DataManager.token);

                // at last we are
                // returning our params.
                return params;
            }
        };

        // Add the request to the RequestQueue.
        queue.add(request);
    }

    public static void getMyEvents(GetEventsCallback callback, RequestQueue queue) {
        String url = "http://puigmal.salle.url.edu/api/v2/users/"+DataManager.myUser.getId()+"/events";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i=0;i<response.length();i++){
                            //Adding each element of JSON array into ArrayList
                            try {
                                DataManager.myEventsAPI.add(new EventAPI(response.getJSONObject(i)));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        callback.getAllEventsOK();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.getAllEventsKO();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                // below line we are creating a map for
                // storing our values in key and value pair.
                Map<String, String> params = new HashMap<String, String>();

                // on below line we are passing our key
                // and value pair to our parameters.
                params.put("Authorization", DataManager.token);

                // at last we are
                // returning our params.
                return params;
            }
        };

        // Add the request to the RequestQueue.
        queue.add(request);
    }

    public static void orderEventsByBest(GetEventsCallback callback, RequestQueue queue) {
        String url = "http://puigmal.salle.url.edu/api/v2/events/best";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        DataManager.myEventsAPI.clear();
                        for (int i=0;i<response.length();i++){
                            //Adding each element of JSON array into ArrayList
                            try {
                                DataManager.myEventsAPI.add(new EventAPI(response.getJSONObject(i)));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        callback.getAllEventsOK();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.getAllEventsKO();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // below line we are creating a map for
                // storing our values in key and value pair.
                Map<String, String> params = new HashMap<String, String>();

                // on below line we are passing our key
                // and value pair to our parameters.
                params.put("Authentication", DataManager.token);

                // at last we are
                // returning our params.
                return params;
            }
        };

        // Add the request to the RequestQueue.
        queue.add(request);
    }

    public static void postMessage(PostMessageCallback callback, RequestQueue queue, int receiverID, String message) {
        // url to post our data
        String url = "http://puigmal.salle.url.edu/api/v2/messages";

        // Creating JSON Object
        JSONObject object = new JSONObject();

        try {
            //input your API parameters
            object.put("content", message);
            object.put("user_id_send", DataManager.getMyUser().getId());
            object.put("user_id_recived", receiverID);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, object,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        callback.postMessageOK();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.err.println(error);
                callback.postMessageKO();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                // below line we are creating a map for
                // storing our values in key and value pair.
                Map<String, String> params = new HashMap<String, String>();

                // on below line we are passing our key
                // and value pair to our parameters.
                params.put("Authorization", DataManager.token);

                // at last we are
                // returning our params.
                return params;
            }
        };

        queue.add(jsonObjectRequest);
    }

    public static void createEvent(CreateEventCallback callback, RequestQueue queue, String name, String image, String location, String description, String eventStart_date, String eventEnd_date, int n_participators, String type) {
        // Creating JSON Object
        JSONObject object = new JSONObject();
        try {
            //input your API parameters
            object.put("name", name);
            object.put("image", image);
            object.put("location", location);
            object.put("description", description);
            object.put("eventStart_date", eventStart_date);
            object.put("eventEnd_date", eventEnd_date);
            object.put("n_participators", n_participators);
            object.put("type", type);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // url to post our data
        String url = "http://puigmal.salle.url.edu/api/v2/events";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, object,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        callback.createEventOK();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.err.println(error);
                callback.createEventKO();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                // below line we are creating a map for
                // storing our values in key and value pair.
                Map<String, String> params = new HashMap<String, String>();

                // on below line we are passing our key
                // and value pair to our parameters.
                params.put("Authorization", DataManager.token);

                // at last we are
                // returning our params.
                return params;
            }
        };

        queue.add(jsonObjectRequest);
    }

    public static void deleteEvent(DeleteEventCallback callback, RequestQueue queue, int id) {
        // url to post our data
        String url = "http://puigmal.salle.url.edu/api/v2/events/"+Integer.toString(id);

        StringRequest request = new StringRequest(Request.Method.DELETE, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                callback.deleteEventOK();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.deleteEventKO();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                // below line we are creating a map for
                // storing our values in key and value pair.
                Map<String, String> params = new HashMap<String, String>();

                // on below line we are passing our key
                // and value pair to our parameters.
                params.put("Authorization", DataManager.token);

                // at last we are
                // returning our params.
                return params;
            }
        };

        queue.add(request);
    }


    public static class DataManager {
        // Authorization token.
        public static String token;

        // List of events.
        public static List<EventAPI> mEventsAPI = new ArrayList<>(); // Events array.

        // My user.
        public static UserAPI myUser;

        // User who I am chatting with.
        public static UserAPI myChattingUser;

        // List of MY events.
        public static List<EventAPI> myEventsAPI = new ArrayList<>(); // Events array.

        // List of MY users by name.
        public static List<UserAPI> mySearchedUsersAPI = new ArrayList<>(); // Events array.

        // List of MY friends.
        public static List<UserAPI> myFriendsUsersAPI = new ArrayList<>(); // Events array.

        // List of MY friend requests.
        public static List<UserAPI> myFriendRequestsAPI = new ArrayList<>(); // Events array.

        // List of MY open chats.
        public static List<UserAPI> myOpenChatsUsersAPI = new ArrayList<>(); // Events array.

        // List of messages with user
        public static List<MessageAPI> myChatMessages = new ArrayList<>(); // Events array.


        /**
         * getters and setters
         * @return
         */

        public static String getToken() {
            return token;
        }

        public static void setToken(String token) {
            DataManager.token = token;
        }

        public static List<EventAPI> getmEventsAPI() {
            return mEventsAPI;
        }

        public static void setmEventsAPI(List<EventAPI> mEventsAPI) {
            DataManager.mEventsAPI = mEventsAPI;
        }

        public static UserAPI getMyUser() {
            return myUser;
        }

        public static void setMyUser(UserAPI myUser) {
            DataManager.myUser = myUser;
        }

        public static List<EventAPI> getMyEventsAPI() {
            return myEventsAPI;
        }

        public static void setMyEventsAPI(List<EventAPI> myEventsAPI) {
            DataManager.myEventsAPI = myEventsAPI;
        }

        public static List<UserAPI> getMySearchedUsersAPI() {
            return mySearchedUsersAPI;
        }

        public static void setMySearchedUsersAPI(List<UserAPI> mySearchedUsersAPI) {
            DataManager.mySearchedUsersAPI = mySearchedUsersAPI;
        }

        public static List<UserAPI> getMyFriendsUsersAPI() {
            return myFriendsUsersAPI;
        }

        public static void setMyFriendsUsersAPI(List<UserAPI> myFriendsUsersAPI) {
            DataManager.myFriendsUsersAPI = myFriendsUsersAPI;
        }

        public static List<UserAPI> getMyFriendRequestsAPI() {
            return myFriendRequestsAPI;
        }

        public static void setMyFriendRequestsAPI(List<UserAPI> myFriendRequestsAPI) {
            DataManager.myFriendRequestsAPI = myFriendRequestsAPI;
        }

        public static List<UserAPI> getMyOpenChatsUsersAPI() {
            return myOpenChatsUsersAPI;
        }

        public static void setMyOpenChatsUsersAPI(List<UserAPI> myOpenChatsUsersAPI) {
            DataManager.myOpenChatsUsersAPI = myOpenChatsUsersAPI;
        }

        public static UserAPI getMyChattingUser() {
            return myChattingUser;
        }

        public static void setMyChattingUser(UserAPI myChattingUser) {
            DataManager.myChattingUser = myChattingUser;
        }

        public static List<MessageAPI> getMyChatMessages() {
            return myChatMessages;
        }

        public static void setMyChatMessages(List<MessageAPI> myChatMessages) {
            DataManager.myChatMessages = myChatMessages;
        }
    }
}
