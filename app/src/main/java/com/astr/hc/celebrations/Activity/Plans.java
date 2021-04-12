package com.astr.hc.celebrations.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
import com.astr.hc.celebrations.Adapter.SingleCheckAdapter;
import com.astr.hc.celebrations.Api.Api;
import com.astr.hc.celebrations.Config.DBHelper;
import com.astr.hc.celebrations.Model.PersonItem;
import com.astr.hc.celebrations.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Plans extends AppCompatActivity implements AdapterView.OnItemClickListener, PaymentResultListener {

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
    SharedPreferences sh;
    int defalt;
    String image,videos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plans);
        choose=findViewById(R.id.choose);
        dbHelper=new DBHelper(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Chhose Your Plan");
        sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        defalt=sh.getInt("Name", 0);

        Intent intent = getIntent();
        vendorid= intent.getStringExtra("vendorid");

        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mPreferences.edit();
        mobb  = mPreferences.getString("v_mob", "");
        maill  = mPreferences.getString("v_mail", "");



//        if (defalt == 1){
//            startPayment1();
//        }

        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startPayment();

            }
        });
        getplans();
        mSingleCheckList.clear();


    }
    public void getplans() {



        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JSONObject object = new JSONObject();

        mSingleCheckList.clear();
        String url = Api.planurl;

        // Enter the correct url for your api service site

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
//                            message = (String) response.get("status");
                            Log.e("xdddddddddddd", "" + response);

                            JSONArray jObject1 = response.getJSONArray("plan");

                            for (int i = 0; i < jObject1.length(); i++) {
                                JSONObject jsonObject = jObject1.getJSONObject(i);
                                mSingleCheckList.add(new PersonItem(""+jsonObject.getString("plan_name"),""+jsonObject.getString("plan_id"),""+jsonObject.getString("plan_photos"),""+jsonObject.getString("plan_videos"),""+jsonObject.getString("plan_amount")));
                            }

                            mAdapter = new SingleCheckAdapter(Plans.this, mSingleCheckList);
                            mRecyclerView = (RecyclerView) findViewById(R.id.recylcerview);
                            mRecyclerView.setLayoutManager(new LinearLayoutManager(Plans.this));
                            mRecyclerView.setAdapter(mAdapter);
                            mAdapter.setOnItemClickListener(Plans.this);
//                    mTxtExpense.setText(objResDetails.getTodayPayments()+" Rs");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("xddddd", "" + error);

            }
        });
        requestQueue.add(jsonObjectRequest);


    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        Toast.makeText(getApplicationContext(), position + " - " + mSingleCheckList.get(position).getPersonName(), Toast.LENGTH_SHORT).show();
        planid=""+mSingleCheckList.get(position).getPersonid();
//        Toast.makeText(this, ""+planid, Toast.LENGTH_SHORT).show();
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
//                        Toast.makeText(getApplicationContext(),"Nothing Happened",Toast.LENGTH_LONG).show();
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
                        dbHelper.insertData(vendorid,planid,image,videos);

                        Intent i=new Intent(Plans.this,Bottommenu.class);
                        startActivity(i);
                    }
                    else
                        {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(Plans.this, ""+errorMsg, Toast.LENGTH_SHORT).show();
                        }
                }
                catch (JSONException e)
                {
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
                params.put("payid", "Free");


                return params;
            }

        };

        // Adding request to request queue
        requestQueue.add(strReq);
        Intent i=new Intent(Plans.this,Bottommenu.class);
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
            object.put("amount", ""+amt);

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


    private void startPayment1() {
        final Activity activity = this;
        final Checkout checkout = new Checkout();

        JSONObject object = new JSONObject();
        int amt = 299 * 100;
        try {
            object.put("key", "rzp_live_9iwQ2J6jQ2eJhI");
            object.put("name", "Happy Celebrations");
            object.put("description", "Plan Description");
            //You can omit the image option to fetch the image from dashboard
            object.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            object.put("currency", "INR");
            object.put("amount", ""+amt);

            JSONObject preFill = new JSONObject();
            preFill.put("email", ""+maill);
            preFill.put("contact", ""+mobb);

            object.put("prefill", preFill);

            checkout.open(activity,object);

            planid="1";
            image="150";
            videos="3";

        } catch (JSONException e) {
            Toast.makeText(activity, "Exception :"+e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

//    public void startPayment() {
//        /**
//         * You need to pass current activity in order to let Razorpay create CheckoutActivity
//         */
//        final Activity activity = this;
//        final Checkout co = new Checkout();
//        try {
//            JSONObject options = new JSONObject();
//            options.put("key", ""+razor_keyy);
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
//            options.put("amount", total);
//            JSONObject preFill = new JSONObject();
//            preFill.put("email", "kamal.bunkar07@gmail.com");
//            preFill.put("contact", "9144040888");
//            options.put("prefill", preFill);
//            co.open(activity, options);
//        } catch (Exception e) {
//            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT).show();
//            e.printStackTrace();
//        }
//    }


    @Override
    public void onPaymentSuccess(String s) {

        final ProgressDialog progressDialogg = ProgressDialog.show(Plans.this,
                "Please wait",
                "Loading...");

        RequestQueue requestQueue= Volley.newRequestQueue(Plans.this);
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

                        Toast toast = Toast.makeText(Plans.this, "Your Booking Successfully...", Toast.LENGTH_LONG);

                        toast.show();
                        dbHelper.insertData(vendorid,planid,image,videos);
                        Intent i=new Intent(Plans.this,Bottommenu.class);
                        startActivity(i);
                    } else {

                        Toast.makeText(Plans.this, "Network Failed!", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(Plans.this, "Post Data : Response Failed", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params=new HashMap<String, String>();
                params.put("pid", planid);
                params.put("vid", vendorid);
                params.put("payid", s);
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




    @Override
    public void onBackPressed() {

            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Plans.this);
            builder.setTitle(R.string.app_name);
            builder.setIcon(R.mipmap.ic_launcher);
            builder.setMessage("Do you want to exit?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            moveTaskToBack(true);
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            android.app.AlertDialog alert = builder.create();
            alert.show();

    }
}
