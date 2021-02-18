package com.shashank.platform.loginui.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.pierfrancescosoffritti.youtubeplayer.player.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.youtubeplayer.player.YouTubePlayerView;
import com.shashank.platform.loginui.Api.Api;
import com.shashank.platform.loginui.Config.DBHelper;
import com.shashank.platform.loginui.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FullScreen extends AppCompatActivity {
    YouTubePlayerView youTubePlayerView;
    ImageView delbtnPlay;
    String vid;
    DBHelper dbHelper;
    String id,na,pa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        String s = getIntent().getStringExtra("video_url");
         vid = getIntent().getStringExtra("video_id");
        youTubePlayerView=findViewById(R.id. youTubePlayerView);
        delbtnPlay=findViewById(R.id. delbtnPlay);


        dbHelper=new DBHelper(this);
        Cursor res = dbHelper.getAllData();

        while (res.moveToNext()) {
            id = res.getString(0);
            na = res.getString(1);
            pa = res.getString(2);
        }

        delbtnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog alertDialog = new AlertDialog.Builder(getApplicationContext())
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Delete Video")
                        .setMessage("Are you sure!")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i)
                            {
//                                setUpPayment();
//                        finish();
                                deletevideo();
                            }
                        })

                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i)
                            {
                                Toast.makeText(getApplicationContext(),"Nothing Happened",Toast.LENGTH_LONG).show();
                            }
                        }).show();
            }
        });

        youTubePlayerView.initialize(
                initializedYouTubePlayer -> initializedYouTubePlayer.addListener(
                        new AbstractYouTubePlayerListener() {
                            @Override
                            public void onReady() {
                                initializedYouTubePlayer.loadVideo(s, 0);
                            }
                        }), true);
    }


    public void deletevideo(){

        String url= Api.delete_video;
        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(getApplicationContext());

        StringRequest strReq = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
//                Log.d(TAG, "Register Response: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error) {

                        Toast toast = Toast.makeText(FullScreen.this, "Your Video Deleted Successfully...", Toast.LENGTH_LONG);

                        toast.show();

                        Intent i=new Intent(FullScreen.this,Bottommenu.class);
                        startActivity(i);
                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("error_msg");

                        Toast.makeText(FullScreen.this, ""+errorMsg, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
//                    toast("Json error: " + e.getMessage());
                    Toast.makeText(FullScreen.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();

                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
//                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(FullScreen.this, "Unknown Error occurred", Toast.LENGTH_SHORT).show();
//                progressDialog.hide();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<>();
                params.put("vidid", vid);
                return params;
            }

        };

        // Adding request to request queue
        requestQueue.add(strReq);
    }
}