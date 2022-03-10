package com.example.memeshare;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.service.chooser.ChooserTarget;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    String currentImageUrl =null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button share = findViewById(R.id.shareButton);
        Button next = findViewById(R.id.nextButton);
        ImageView image = findViewById(R.id.image);
        ProgressBar progressBar = findViewById(R.id.progressBar);

         loadData(image,progressBar);



        // For Share button
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_TEXT, "Hey checkout this cool meme i've found on Reddit:\n" + currentImageUrl);
                startActivity(Intent.createChooser(i,"Share this meme using..."));
            }
        });

        // For Next button
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               loadData(image,progressBar);
            }
        });
    }


    private void loadData(ImageView image,ProgressBar progressBar){

        progressBar.setVisibility(View.VISIBLE);
        // Instantiate the RequestQueue.
       String url = "https://meme-api.herokuapp.com/gimme";

        // Request a string response from the provided URL.
               JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
//                        String url = null;
                        try {
                            currentImageUrl = response.getString("url");
                        } catch (JSONException e) {
                            e.printStackTrace();

                        }
                        Glide.with(MainActivity.this).load(currentImageUrl).listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                progressBar.setVisibility(View.GONE);
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                progressBar.setVisibility(View.GONE);
                                return false;
                            }
                        }).into(image);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.d("errorr","Error aa gayo ji!");
                    }
                });

// Add the request to the RequestQueue.
        MySingelton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }
}