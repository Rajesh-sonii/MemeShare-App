package com.example.memeshare;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

public class MySingelton {
    private static MySingelton instance;
    private RequestQueue requestQueue;
    private static Context ctx;

    private MySingelton(Context context) {
        ctx = context;
        requestQueue = getRequestQueue();

    }

    public static synchronized MySingelton getInstance(Context context) {
        if (instance == null) {
            instance = new MySingelton(context);
        }
        return instance;
    }

    private RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            requestQueue = Volley.newRequestQueue(ctx.getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

}
