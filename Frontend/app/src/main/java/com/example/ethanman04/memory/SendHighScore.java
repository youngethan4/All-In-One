package com.example.ethanman04.memory;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.ethanman04.allone.Endpoints;
import com.example.ethanman04.allone.PreferenceKeys;
import com.example.ethanman04.allone.VolleyRequests;

import org.json.JSONObject;

import java.util.HashMap;

public class SendHighScore implements Runnable {

    private Context context;

    public SendHighScore(Context context){
        this.context = context;
    }

    /**
     * Sends the users current high scores to the server to be put on the backend database.
     */
    @Override
    public void run() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("time20", sp.getLong(PreferenceKeys.MEMORY_HIGH_SCORE_TIME_20, 0));
        hashMap.put("time30", sp.getLong(PreferenceKeys.MEMORY_HIGH_SCORE_TIME_30, 0));
        hashMap.put("moves20", sp.getInt(PreferenceKeys.MEMORY_HIGH_SCORE_MOVES_20, 0));
        hashMap.put("moves30", sp.getInt(PreferenceKeys.MEMORY_HIGH_SCORE_MOVES_30, 0));
        hashMap.put("id", sp.getInt(PreferenceKeys.LOGGED_IN_USER_ID, 0));

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, Endpoints.getInstance().getUpdateHighScoreEndpoint(),
                new JSONObject(hashMap), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        VolleyRequests.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }
}
