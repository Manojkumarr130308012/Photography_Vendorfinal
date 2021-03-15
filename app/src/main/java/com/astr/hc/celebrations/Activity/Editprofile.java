package com.astr.hc.celebrations.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.astr.hc.celebrations.Api.Api;
import com.astr.hc.celebrations.Config.DBHelper;
import com.astr.hc.celebrations.Model.YoutubeVideo;
import com.astr.hc.celebrations.R;
import com.tiper.MaterialSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Editprofile extends AppCompatActivity {
    TextInputEditText firstname,lastname,mobile,watsapp,email,address,amount;
    MaterialSpinner location,category;
    Button upload;
    String vendor_id;
    String vendor_fname;
    String vendor_lname ;
    String vendor_dob ;
    String vendor_proof ;
    String vendor_category;
    String vendor_cname ;
    String vendor_mobile ;
    String vendor_whatsapp;
    String vendor_email ;
    String vendor_address;
    String vendor_service;
    String vendor_location;
    String vendor_status ;
    String vendor_plan ;
    List<String> locationname = new ArrayList<>();
    List<String> locationid = new ArrayList<>();
    List<String> catgoryid = new ArrayList<>();
    List<String> catgoryname = new ArrayList<>();

    String vendor_idStr;
    String vendor_fnameStr;
    String vendor_lnameStr ;
    String vendor_dobStr ;
    String vendor_proofStr ;
    String vendor_categoryStr;
    String vendor_cnameStr ;
    String vendor_mobileStr ;
    String vendor_whatsappStr;
    String vendor_emailStr ;
    String vendor_addressStr;
    String vendor_serviceStr;
    String vendor_locationStr;
    String vendor_statusStr ;
    String vendor_planStr ;

String category_id;
String category_name;
    String location_id;
    String location_name;
    DBHelper dbHelper;
    String id,na,pa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);
        firstname=findViewById(R.id.firstname);
        lastname=findViewById(R.id.lastname);
        mobile=findViewById(R.id.mobile);
        watsapp=findViewById(R.id.watsapp);
        email=findViewById(R.id.email);
        address=findViewById(R.id.address);
        location=findViewById(R.id.location);
        category=findViewById(R.id.category);
        upload=findViewById(R.id.upload);
//        amount=findViewById(R.id.amount);

        dbHelper=new DBHelper(this);
        Cursor res = dbHelper.getAllData();

        while (res.moveToNext()) {
            id = res.getString(0);
            na = res.getString(1);
            pa = res.getString(2);
        }
        Intent intent = getIntent();
        vendor_id= intent.getStringExtra("vendor_id");
        vendor_fname= intent.getStringExtra("vendor_fname");
        vendor_lname= intent.getStringExtra("vendor_lname");
        vendor_dob= intent.getStringExtra("vendor_dob");
        vendor_category= intent.getStringExtra("vendor_category");
        vendor_mobile= intent.getStringExtra("vendor_mobile");
        vendor_whatsapp= intent.getStringExtra("vendor_whatsapp");
        vendor_email= intent.getStringExtra("vendor_email");
        vendor_address= intent.getStringExtra("vendor_address");
        vendor_location= intent.getStringExtra("vendor_location");

        firstname.setText(""+vendor_fname);
        lastname.setText(""+vendor_lname);
        mobile.setText(""+vendor_mobile);
        watsapp.setText(""+vendor_whatsapp);
        address.setText(""+vendor_address);
        email.setText(""+vendor_email);
//        email.setText(""+vendor_email);
//        amount.setText(""+);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vendor_idStr=firstname.getText().toString();
                vendor_lnameStr=lastname.getText().toString();
                vendor_addressStr=address.getText().toString();
                vendor_whatsappStr=watsapp.getText().toString();
                vendor_mobileStr=mobile.getText().toString();
                vendor_fnameStr=firstname.getText().toString();

                getpost();
            }
        });
        new getlocat().execute(Api.locationidurl+""+vendor_location);
        new getcate().execute(Api.categoryidurl+""+vendor_category);
//        getcategory();
//        getlocation();
//        getpost();
        location.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {

            @Override
            public void onNothingSelected(MaterialSpinner materialSpinner) {

            }

            @Override
            public void onItemSelected(MaterialSpinner materialSpinner, View view, int i, long l) {
                if (i > -1) {
                    vendor_locationStr = locationid.get(i).toString();
                } else {
                    vendor_locationStr = "";
                }
            }
        });

        category.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {

            @Override
            public void onNothingSelected(MaterialSpinner materialSpinner) {

            }

            @Override
            public void onItemSelected(MaterialSpinner materialSpinner, View view, int i, long l) {
                if (i > -1) {
                    vendor_categoryStr = catgoryid.get(i).toString();

                } else {
                    vendor_categoryStr = "";
                }
            }
        });
    }


    class getlocat extends AsyncTask<String, Integer, String> {
        ArrayList<YoutubeVideo> videoArrayList=new ArrayList<>();
        @Override
        protected String doInBackground(String... params) {
            return readURL(params[0]);
        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(String content) {
            try {
                JSONObject jsonObject = new JSONObject(content);
                JSONArray jsonArray =  jsonObject.getJSONArray("location");
                for(int i =0;i<jsonArray.length(); i++){
                    JSONObject productObject = jsonArray.getJSONObject(i);
                    locationname.add(productObject.getString("location_name"));
                    locationid.add(productObject.getString("location_id"));
//                    mCustomerList.add(addobj);

                }

//                Log.e("dddddddddddddddddd",""+location_name.toString());
                if (getApplicationContext()!=null) {
                    //set layout manager and adapter for "GridView"

//                    SetCustomerListAdapter(mCustomerList);

                    ArrayAdapter<String> _Adapter = new ArrayAdapter<String>(Editprofile.this, android.R.layout.simple_spinner_item, locationname);
                    _Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    location.setAdapter(_Adapter);
                    location.setSelection(0);
                }
            } catch (JSONException e) {
                e.printStackTrace();
//                custPrograssbar.closePrograssBar();
            }


        }
    }



    class getcate extends AsyncTask<String, Integer, String> {
        ArrayList<YoutubeVideo> videoArrayList=new ArrayList<>();
        @Override
        protected String doInBackground(String... params) {
            return readURL(params[0]);
        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(String content) {
            try {
                JSONObject jsonObject = new JSONObject(content);
                JSONArray jsonArray =  jsonObject.getJSONArray("category");
                for(int i =0;i<jsonArray.length(); i++){
                    JSONObject productObject = jsonArray.getJSONObject(i);

                    catgoryname.add(productObject.getString("category_name"));
                    catgoryid.add(productObject.getString("category_id"));
                }

                if (getApplicationContext()!=null) {
                    //set layout manager and adapter for "GridView"

//                    SetCustomerListAdapter(mCustomerList);

                    ArrayAdapter<String> _Adapter = new ArrayAdapter<String>(Editprofile.this, android.R.layout.simple_spinner_item, catgoryname);
                    _Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    category.setAdapter(_Adapter);
                    category.setSelection(0);
                }
            } catch (JSONException e) {
                e.printStackTrace();
//                custPrograssbar.closePrograssBar();
            }


        }
    }


    private static String readURL(String theUrl) {
        StringBuilder content = new StringBuilder();
        try {
            // create a url object
            URL url = new URL(theUrl);
            // create a urlconnection object
            URLConnection urlConnection = url.openConnection();
            // wrap the urlconnection in a bufferedreader
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;
            // read from the urlconnection via the bufferedreader
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line + "\n");
            }
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content.toString();
    }









//
//    private void getcategory() {
//
//        final ProgressDialog progressDialog = ProgressDialog.show(getApplicationContext(),
//                "Please wait",
//                "Loading...");
//
//        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
//        String url = Api.categoryidurl;
//        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                //let's parse json data
//                try {
//                    JSONObject jsonObject = new JSONObject(response);
//                    JSONObject obj = jsonObject.getJSONObject("category");
////                    String msg = obj.getString("msg");
//
//                        category_id = obj.getString("category_id");
//                        category_name = obj.getString("catgory_name");
//                    catgoryname.add(jsonObject.getString("category_name"));
//                    catgoryid.add(jsonObject.getString("category_id"));
//                    progressDialog.dismiss();
//                }
//                catch (Exception e){
//                    e.printStackTrace();
//                    progressDialog.dismiss();
////                    Toast.makeText(ProfileActivity.this, "POST DATA : unable to Parse Json", Toast.LENGTH_SHORT).show();
//                }
//
//                ArrayAdapter<String> _Adapter = new ArrayAdapter<String>(Editprofile.this, android.R.layout.simple_spinner_item, catgoryname);
//                _Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                category.setAdapter(_Adapter);
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                progressDialog.dismiss();
//                Toast.makeText(getApplicationContext(), "Post Data : Response Failed", Toast.LENGTH_SHORT).show();
//            }
//        }){
//            @Override
//            protected Map<String,String> getParams(){
//                Map<String,String> params=new HashMap<String, String>();
//                params.put("cid", ""+vendor_category);
//                return params;
//            }
//
//            @Override
//            public Map<String,String> getHeaders() throws AuthFailureError {
//                Map<String,String> params=new HashMap<String, String>();
////                params.put("Content-Type","application/json;charset=utf-8");
//                params.put("Content-Type","application/x-www-form-urlencoded;charset=utf-8");
////                params.put("Authorization","Bearer "+sToken);
////                params.put("X-Requested-With", "XMLHttpRequest");
//                return params;
//            }
//        };
//
//        requestQueue.add(stringRequest);
//
//    }
//
//    private void getlocation() {
//
//        final ProgressDialog progressDialog = ProgressDialog.show(getApplicationContext(),
//                "Please wait",
//                "Loading...");
//
//        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
//        String url = Api.locationidurl;
//        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                //let's parse json data
//                try {
//                    JSONObject jsonObject = new JSONObject(response);
//                    JSONObject obj = jsonObject.getJSONObject("location");
////                    String msg = obj.getString("msg");
//
//                    location_id = obj.getString("location_id");
//                    location_name = obj.getString("location_name");
//
//                    locationname.add(jsonObject.getString("location_name"));
//                    locationid.add(jsonObject.getString("location_id"));
//                    progressDialog.dismiss();
//                }
//                catch (Exception e){
//                    e.printStackTrace();
//                    progressDialog.dismiss();
////                    Toast.makeText(ProfileActivity.this, "POST DATA : unable to Parse Json", Toast.LENGTH_SHORT).show();
//                }
//                ArrayAdapter<String> _Adapter = new ArrayAdapter<String>(Editprofile.this, android.R.layout.simple_spinner_item, locationname);
//                _Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                location.setAdapter(_Adapter);
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                progressDialog.dismiss();
//                Toast.makeText(getApplicationContext(), "Post Data : Response Failed", Toast.LENGTH_SHORT).show();
//            }
//        }){
//            @Override
//            protected Map<String,String> getParams(){
//                Map<String,String> params=new HashMap<String, String>();
//                params.put("lid", ""+vendor_location);
//                return params;
//            }
//
//            @Override
//            public Map<String,String> getHeaders() throws AuthFailureError {
//                Map<String,String> params=new HashMap<String, String>();
////                params.put("Content-Type","application/json;charset=utf-8");
//                params.put("Content-Type","application/x-www-form-urlencoded;charset=utf-8");
////                params.put("Authorization","Bearer "+sToken);
////                params.put("X-Requested-With", "XMLHttpRequest");
//                return params;
//            }
//        };
//
//        requestQueue.add(stringRequest);
//
//    }


    private void getpost() {

        final ProgressDialog progressDialog = ProgressDialog.show(Editprofile.this,
                "Please wait",
                "Loading...");

        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        String url = Api.updateprofileurl;
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //let's parse json data
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    Toast.makeText(Editprofile.this, ""+response.toString(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    Intent i=new Intent(Editprofile.this, Bottommenu.class);
                    startActivity(i);

                }
                catch (Exception e){
                    e.printStackTrace();
                    progressDialog.dismiss();
////                    Toast.makeText(ProfileActivity.this, "POST DATA : unable to Parse Json", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Post Data : Response Failed", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params=new HashMap<String, String>();
                params.put("vendor_id", ""+na);
                params.put("vendor_fname", ""+vendor_fnameStr);
                params.put("vendor_lname", ""+vendor_lnameStr);
                params.put("vendor_address", ""+vendor_addressStr);
                params.put("vendor_whatsapp", ""+vendor_whatsappStr);
                params.put("vendor_mobile", ""+vendor_mobileStr);
                params.put("vendor_location", ""+vendor_locationStr);
                params.put("vendor_category", ""+vendor_categoryStr);
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