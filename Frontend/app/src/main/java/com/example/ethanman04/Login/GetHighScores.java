package com.example.ethanman04.Login;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.ethanman04.allone.Endpoints;
import com.example.ethanman04.allone.PreferenceKeys;
import com.example.ethanman04.allone.VolleyRequests;

import org.json.JSONObject;

public class GetHighScores implements Runnable {

    Context context;

    public GetHighScores(Context context){
        this.context = context;
    }

    @Override
    public void run() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        int id = sp.getInt(PreferenceKeys.LOGGED_IN_USER_ID, 0);
        String url = Endpoints.getInstance().getHighScoreEndpoint() + id;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        float hsTime20 = 0;
                        float hsTime30 = 0;
                        int hsMoves20 = 0;
                        int hsMoves30 = 0;
                        try {
                            hsTime20 = (float) response.getDouble("time20");
                            hsTime30 = (float) response.getDouble("time30");
                            hsMoves20 = response.getInt("moves20");
                            hsMoves30 = response.getInt("moves30");
                        }
                        catch(Exception e){
                            e.printStackTrace();
                        }

                        SharedPreferences.Editor editor = sp.edit();
                        editor.putFloat(PreferenceKeys.MEMORY_HIGH_SCORE_TIME_20, hsTime20);
                        editor.putFloat(PreferenceKeys.MEMORY_HIGH_SCORE_TIME_30, hsTime30);
                        editor.putInt(PreferenceKeys.MEMORY_HIGH_SCORE_MOVES_20, hsMoves20);
                        editor.putInt(PreferenceKeys.MEMORY_HIGH_SCORE_MOVES_30, hsMoves30);
                        editor.apply();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("request.error", error.toString());
            }
        });
        VolleyRequests.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }
}
