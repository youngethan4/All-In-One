package com.example.ethanman04.allone;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VolleyRequests {

    private RequestQueue requestQueue;
    private Context ctx;

    private static VolleyRequests instance;
    public static synchronized VolleyRequests getInstance(Context context){
        if (instance == null){
            instance = new VolleyRequests(context);
        }
        return  instance;
    }

    private VolleyRequests(Context context){
        ctx = context;
        requestQueue = getRequestQueue();
    }

    /**
     * Gets the request queue for the instance. If requestQueue is null, one will be created.
     * @return
     */
    private RequestQueue getRequestQueue(){
        if (requestQueue == null){
            requestQueue = Volley.newRequestQueue(ctx.getApplicationContext());
        }
        return requestQueue;
    }

    /**
     * Takes a request in that can be a json or string and adds it to the queue.
     * @param req
     * @param <T>
     */
    public <T> void addToRequestQueue(Request<T> req){
        getRequestQueue().add(req);
    }
}
