package com.astr.hc.celebrations.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.astr.hc.celebrations.Api.Api;
import com.astr.hc.celebrations.R;
import com.squareup.picasso.Picasso;

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
                AlertDialog alertDialog = new AlertDialog.Builder(Image.this)
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
                                Toast.makeText(Image.this,"Nothing Happened",Toast.LENGTH_LONG).show();
                            }
                        }).show();
            }
        });

//        selectedImage.setImageResource(intent.getIntExtra("image", 0));
    }



    public void deleteimage(){

        final ProgressDialog progressDialog = ProgressDialog.show(Image.this,
                "Please wait",
                "Loading...");

        RequestQueue requestQueue= Volley.newRequestQueue(Image.this);
        String url = Api.delete_image;
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //let's parse json data
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject obj = jsonObject.getJSONObject("reg");
                    String msg = obj.getString("msg");

                    if (msg.equals("Success")) {

                        Toast.makeText(Image.this, ""+msg, Toast.LENGTH_SHORT).show();
                        Intent in=new Intent(getApplicationContext(), Bottommenu.class);
                        startActivity(in);


                    } else {
                        Toast.makeText(Image.this, ""+msg, Toast.LENGTH_SHORT).show();
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
                Toast.makeText(Image.this, "Post Data : Response Failed", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params=new HashMap<String, String>();
                params.put("imgid", ""+imgid);

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