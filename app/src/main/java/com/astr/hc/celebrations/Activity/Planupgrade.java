package com.astr.hc.celebrations.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
import com.astr.hc.celebrations.Adapter.SingleCheckAdapter;
import com.astr.hc.celebrations.Api.Api;
import com.astr.hc.celebrations.Config.DBHelper;
import com.astr.hc.celebrations.Model.PersonItem;
import com.astr.hc.celebrations.Model.YoutubeVideo;
import com.astr.hc.celebrations.R;

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

public class Planupgrade extends AppCompatActivity implements AdapterView.OnItemClickListener, PaymentResultListener {
    RecyclerView mRecyclerView;
    ProgressDialog progressDialog;
    private List<PersonItem> mSingleCheckList = new ArrayList<>();
    private SingleCheckAdapter mAdapter;
    String planid;
    String planamount;
    Button choose;
    DBHelper dbHelper;
    String vendorid,maill,mobb;
    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;
    String id,na,pa,image,videos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planupgrade);

        choose=findViewById(R.id.choose);
        dbHelper=new DBHelper(this);
        Cursor res = dbHelper.getAllData();

        while (res.moveToNext()) {
            id = res.getString(0);
            na = res.getString(1);
            pa = res.getString(2);
        }
        Intent intent = getIntent();
        vendorid= intent.getStringExtra("vendorid");

//        Toast.makeText(this, ""+vendorid, Toast.LENGTH_SHORT).show();

        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mPreferences.edit();
        mobb  = mPreferences.getString("v_mob", "");
        maill  = mPreferences.getString("v_mail", "");

        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startPayment();

            }
        });
        new getplans().execute(Api.planupgradeurl+""+pa);
        mSingleCheckList.clear();
    }


//    private void getplanup() {
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




    class getplans extends AsyncTask<String, Integer, String> {
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
                JSONArray jsonArray =  jsonObject.getJSONArray("plan");
                for(int i =0;i<jsonArray.length(); i++){
                    JSONObject productObject = jsonArray.getJSONObject(i);

//                    mCustomerList.add(addobj);
                    mSingleCheckList.add(new PersonItem(""+productObject.getString("plan_name"),""+productObject.getString("plan_id"),""+productObject.getString("plan_photos"),""+productObject.getString("plan_videos"),""+productObject.getString("plan_amount")));

                }

//                Log.e("dddddddddddddddddd",""+location_name.toString());
                if (getApplicationContext()!=null) {
                    //set layout manager and adapter for "GridView"

//                    SetCustomerListAdapter(mCustomerList);

                    mAdapter = new SingleCheckAdapter(Planupgrade.this, mSingleCheckList);
                    mRecyclerView = (RecyclerView) findViewById(R.id.recylcerview);
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(Planupgrade.this));
                    mRecyclerView.setAdapter(mAdapter);
                    mAdapter.setOnItemClickListener(Planupgrade.this);
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

//    public void getplans() {
//
//
//
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        JSONObject object = new JSONObject();
//
//        mSingleCheckList.clear();
//        String url = Api.planurl;
//
//        // Enter the correct url for your api service site
//
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//
//                        try {
////                            message = (String) response.get("status");
//                            Log.e("xdddddddddddd", "" + response);
//
//                            JSONArray jObject1 = response.getJSONArray("plan");
//
//                            for (int i = 0; i < jObject1.length(); i++) {
//                                JSONObject jsonObject = jObject1.getJSONObject(i);
//                                mSingleCheckList.add(new PersonItem(""+jsonObject.getString("plan_name"),""+jsonObject.getString("plan_id"),""+jsonObject.getString("plan_photos"),""+jsonObject.getString("plan_videos"),""+jsonObject.getString("plan_amount")));
//                            }
//
//                            mAdapter = new SingleCheckAdapter(Planupgrade.this, mSingleCheckList);
//                            mRecyclerView = (RecyclerView) findViewById(R.id.recylcerview);
//                            mRecyclerView.setLayoutManager(new LinearLayoutManager(Planupgrade.this));
//                            mRecyclerView.setAdapter(mAdapter);
//                            mAdapter.setOnItemClickListener(Planupgrade.this);
////                    mTxtExpense.setText(objResDetails.getTodayPayments()+" Rs");
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.e("xddddd", "" + error);
//
//            }
//        });
//        requestQueue.add(jsonObjectRequest);
//
//
//    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        Toast.makeText(getApplicationContext(), position + " - " + mSingleCheckList.get(position).getPersonName(), Toast.LENGTH_SHORT).show();
        planid=""+mSingleCheckList.get(position).getPersonid();
        planamount=""+mSingleCheckList.get(position).getAmount();
        image=""+mSingleCheckList.get(position).getPhotos();
        videos=""+mSingleCheckList.get(position).getVideos();
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Plan Choose")
                .setMessage("Are you sure!")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        if (planid.equals("1")) {

                            freePlan(planid);

                        } else {
                            startPayment();
                        }
//                        finish();
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

    private void freePlan(String planid) {

        String url=Api.planpayamounturl;
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
                    if (!error)
                    {
                        dbHelper.insertData(na,planid,image,videos);

                        Intent i=new Intent(Planupgrade.this,Bottommenu.class);
                        startActivity(i);
                    }
                    else
                    {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(Planupgrade.this, ""+errorMsg, Toast.LENGTH_SHORT).show();
                    }
                }
                catch (JSONException e)
                {
                    // JSON error
                    e.printStackTrace();
//                    toast("Json error: " + e.getMessage());
                    Toast.makeText(Planupgrade.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();

                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
//                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(Planupgrade.this, "Unknown Error occurred", Toast.LENGTH_SHORT).show();
//                progressDialog.hide();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<>();
                params.put("pid", planid);
                params.put("vid", na);
                params.put("payid", "Free");


                return params;
            }

        };

        // Adding request to request queue
        requestQueue.add(strReq);
        Intent i=new Intent(Planupgrade.this,Bottommenu.class);
        startActivity(i);
        Toast.makeText(this, "Free Plan Choosed Successfully! ", Toast.LENGTH_SHORT).show();
    }
/*
    private void setUpPayment() {



        final Activity activity = this;
        final Checkout checkout = new Checkout();

        JSONObject object = new JSONObject();
        String amtOnline = ""+planamount;
        int amt = Integer.parseInt(amtOnline) * 100;
        try {
            object.put("name", "Razorpay Corp");
            object.put("description", "Demoing Charges");
            //You can omit the image option to fetch the image from dashboard
            object.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            object.put("currency", "INR");
            object.put("amount", ""+amt);

            JSONObject preFill = new JSONObject();
//            preFill.put("email", ""+eemail);
//            preFill.put("contact", ""+emob);

            object.put("prefill", preFill);

            checkout.open(activity,object);

        } catch (JSONException e) {
//            Toast.makeText(activity, "Exception :"+e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    public void onPaymentSuccess(String razorPayId) {

//        new ActivityReservation2.sendData().execute();
        // Tag used to cancel the request
        String tag_string_req = "req_signup";
        progressDialog.setMessage("Loading...");
        progressDialog.show();

     */
/*    String url=Api.planpayamounturl;
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

                        Toast toast = Toast.makeText(Plans.this, "Your Booking Successfully...", Toast.LENGTH_LONG);

                        toast.show();
                        dbHelper.insertData(vendorid,planid);
                        Intent i=new Intent(Plans.this,Bottommenu.class);
                        startActivity(i);
                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("error_msg");

                        Toast.makeText(Plans.this, ""+errorMsg, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
//                    toast("Json error: " + e.getMessage());
                    Toast.makeText(Plans.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();

                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
//                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(Plans.this, "Unknown Error occurred", Toast.LENGTH_SHORT).show();
                progressDialog.hide();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<>();
                params.put("pid", planid);
                params.put("vid", vendorid);
                params.put("payid", razorPayId);


                return params;
            }

        };

        // Adding request to request queue
        requestQueue.add(strReq);*//*


    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(this, "Payment Failled", Toast.LENGTH_SHORT).show();
    }
*/



//    public void startPayment() {
//        /**
//         * You need to pass current activity in order to let Razorpay create CheckoutActivity
//         */
//        final Activity activity = this;
//        final Checkout co = new Checkout();
//        try {
//            JSONObject options = new JSONObject();
//            options.put("name", "BlueApp Software");
//            options.put("description", "App Payment");
//            //You can omit the image option to fetch the image from dashboard
//            options.put("image", "https://rzp-mobile.s3.amazonaws.com/images/rzp.png");
//            options.put("currency", "INR");
//            String payment = planamount;
//            // amount is in paise so please multiple it by 100
//            //Payment failed Invalid amount (should be passed in integer paise. Minimum value is 100 paise, i.e. ₹ 1)
//            double total = Double.parseDouble(payment);
//            total = total * 100;
//            options.put("amount", "100");
//            JSONObject preFill = new JSONObject();
//            preFill.put("email", ""+maill);
//            preFill.put("contact", ""+mobb);
//            options.put("prefill", preFill);
//            co.open(activity, options);
//        } catch (Exception e) {
////            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT).show();
//            e.printStackTrace();
//        }
//    }

    private void startPayment() {
        final Activity activity = this;
        final Checkout checkout = new Checkout();

        JSONObject object = new JSONObject();
        int amt = Integer.parseInt(planamount) * 100;
        try {
            object.put("key", "rzp_live_9iwQ2J6jQ2eJhI");
            object.put("name", "Happy Celebrations");
            object.put("description", "Plan Description");
            //You can omit the image option to fetch the image from dashboard
            object.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            object.put("currency", "INR");
            object.put("amount", "100");

            JSONObject preFill = new JSONObject();
            preFill.put("email", ""+maill);
            preFill.put("contact", ""+mobb);

            object.put("prefill", preFill);

            checkout.open(activity,object);

        } catch (JSONException e) {
            Toast.makeText(activity, "Exception :"+e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }


    @Override
    public void onPaymentSuccess(String s) {


//        Toast.makeText(this, "oiyhoihiuohiu", Toast.LENGTH_SHORT).show();
        
        final ProgressDialog progressDialogg = ProgressDialog.show(Planupgrade.this,
                "Please wait",
                "Loading...");

        RequestQueue requestQueue= Volley.newRequestQueue(Planupgrade.this);
        String url = Api.planpayamounturl;
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //let's parse json data
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject obj = jsonObject.getJSONObject("reg");
                    String msg = obj.getString("msg");

                    // Check for error node in json
                    if (msg.equals("Success")) {

                        Toast toast = Toast.makeText(Planupgrade.this, "Your Plan Upgrade Successfully...", Toast.LENGTH_LONG);
                        toast.show();
                        dbHelper.deleteRow();
                        dbHelper.insertData(vendorid,planid,image,videos);
                        Intent i=new Intent(Planupgrade.this,Bottommenu.class);
                        startActivity(i);
                    } else {

                        Toast.makeText(Planupgrade.this, "Network Failed!", Toast.LENGTH_SHORT).show();
                    }

                    progressDialogg.dismiss();
                }
                catch (Exception e){
                    e.printStackTrace();
                    progressDialogg.dismiss();
//                    Toast.makeText(ProfileActivity.this, "POST DATA : unable to Parse Json", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialogg.dismiss();
                Toast.makeText(Planupgrade.this, "Post Data : Response Failed", Toast.LENGTH_SHORT).show();
                Log.e("sssss0",""+error);
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params=new HashMap<String, String>();
                params.put("pid", planid);
                params.put("vid", na);
                params.put("payid", s);


                Log.e("pid",""+planid);
                Log.e("vid",""+na);
                Log.e("payid",""+s);

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


    @Override
    public void onPaymentError(int i, String s) {
//        Log.e(TAG,  "error code "+String.valueOf(i)+" -- Payment failed "+s.toString()  );
        try {
            Toast.makeText(this, "Payment error please try again", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e("OnPaymentError", "Exception in onPaymentError", e);
        }
    }

}