package com.astr.hc.celebrations.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.pierfrancescosoffritti.youtubeplayer.player.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.youtubeplayer.player.YouTubePlayerView;
import com.astr.hc.celebrations.Api.Api;
import com.astr.hc.celebrations.Config.DBHelper;
import com.astr.hc.celebrations.R;

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
                AlertDialog alertDialog = new AlertDialog.Builder(FullScreen.this)
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
                                Toast.makeText(FullScreen.this,"Nothing Happened",Toast.LENGTH_LONG).show();
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

        final ProgressDialog progressDialog = ProgressDialog.show(FullScreen.this,
                "Please wait",
                "Loading...");

        RequestQueue requestQueue= Volley.newRequestQueue(FullScreen.this);
        String url = Api.delete_video;
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //let's parse json data
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject obj = jsonObject.getJSONObject("reg");
                    String msg = obj.getString("msg");

                    if (msg.equals("Success")) {

                        Toast.makeText(FullScreen.this, ""+msg, Toast.LENGTH_SHORT).show();
                        Intent in=new Intent(getApplicationContext(), Bottommenu.class);
                        startActivity(in);

                    } else {
                        Toast.makeText(FullScreen.this, ""+msg, Toast.LENGTH_SHORT).show();
                    }

                    progressDialog.dismiss();
                }
                catch (Exception e){
                    e.printStackTrace();
                    progressDialog.dismiss();
//                    Toast.makeText(ProfileActivity.this, "POST DATA : unable to Parse Json", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(FullScreen.this, "Post Data : Response Failed", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params=new HashMap<String, String>();
                params.put("vidid", ""+vid);

                return params;
            }

            @Override
            public Map<String,String> getHeaders() throws AuthFailureError {
                Map<String,String> params=new HashMap<String, String>();
//                params.put("Content-Type","application/json;charset=utf-8");
                params.put("Content-Type","application/x-www-form-urlencoded;charset=utf-8");
//                params.put("Authorization","Bearer "+sToken);
//                params.put("X-Requested-With", "XMLHttpRequest");
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }
}