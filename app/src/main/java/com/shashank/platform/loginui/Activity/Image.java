package com.shashank.platform.loginui.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.shashank.platform.loginui.Api.Api;
import com.shashank.platform.loginui.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Image extends AppCompatActivity {
    ImageView selectedImage;
    ImageView delbtnPlay;
    String imgid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        selectedImage = (ImageView) findViewById(R.id.selectedImage); // init a ImageView
        delbtnPlay = (ImageView) findViewById(R.id.delbtnPlay); // init a ImageView
        Intent intent = getIntent(); // get Intent which we set from Previous Activity
        imgid=intent.getStringExtra("imageid");
        Picasso.with(this).load(intent.getStringExtra("image")).into(selectedImage);
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
                                deleteimage();
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

//        selectedImage.setImageResource(intent.getIntExtra("image", 0));
    }



    public void deleteimage(){

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

                        Toast toast = Toast.makeText(Image.this, "Your Image Deleted Successfully...", Toast.LENGTH_LONG);

                        toast.show();

                        Intent i=new Intent(Image.this,Bottommenu.class);
                        startActivity(i);
                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("error_msg");

                        Toast.makeText(Image.this, ""+errorMsg, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
//                    toast("Json error: " + e.getMessage());
                    Toast.makeText(Image.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();

                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
//                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(Image.this, "Unknown Error occurred", Toast.LENGTH_SHORT).show();
//                progressDialog.hide();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<>();
                params.put("imgid", imgid);
                return params;
            }

        };

        // Adding request to request queue
        requestQueue.add(strReq);
    }
}