package com.astr.hc.celebrations.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.astr.hc.celebrations.Api.Api;
import com.astr.hc.celebrations.Config.DBHelper;
import com.astr.hc.celebrations.R;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements PaymentResultListener {
    String message;
    ImageView imageView;
    TextView textView;
    int count = 0;
    Button signin,signout;
    String checkusername,checkpassword;
    String suburl;
    EditText username1,password1;
    ProgressBar pbar;
     SharedPreferences mPreferences;
     SharedPreferences.Editor mEditor;
    SharedPreferences sh;
    DBHelper dbHelper;
    int defalt=0;
    String planid="1";
    String image="150";
    String videos="3";
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
//        requestPermission();
        imageView = findViewById(R.id.imageView);
        textView = findViewById(R.id.textView);
        signin = findViewById(R.id.signin);
        signout = findViewById(R.id.signout);
        username1 = findViewById(R.id.editText);
        password1 = findViewById(R.id.editText2);
        pbar = (ProgressBar) findViewById(R.id.log_pbar);
        dbHelper=new DBHelper(this);
//        sh = getSharedPreferences("MyPrefs", MODE_PRIVATE);
//        defalt=sh.getInt("Name", 0);
        Log.e("defalt",""+defalt);
        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mPreferences.edit();

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              requestPermission();

            }
        });


        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this,Register.class);
                startActivity(i);
            }
        });

    }

    public boolean validateLogin(String username, String password){
        if(username == null || username.trim().length() == 0){
            Toast.makeText(this, "Username is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(password == null || password.trim().length() == 0){
            Toast.makeText(this, "Password is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
//    public class fetchData extends AsyncTask<Void, Void, Void> {
//        String data = "";
//        String dataParsed = "";
//        String singleParsed = "";
//
////        @Override
////        protected void onPreExecute() {
////            pbar.setVisibility(View.VISIBLE);
////        }
//
//        @Override
//        protected Void doInBackground(Void... voids) {
//
//            if (checkusername == null || checkpassword == null) {
//
//                Toast.makeText(MainActivity.this, " Fill the Fields", Toast.LENGTH_SHORT).show();
//
//            } else {
//
//                try {
//
//                    suburl="?username="+checkusername+"&password="+checkpassword;
//                    URL url = new URL(Api.Loginurl+suburl);
//
//                    Log.e("dddddddddddddd", "" + url);
//
//                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
//                    InputStream inputStream = httpURLConnection.getInputStream();
//                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
//                    String line = "";
//                    while (line != null) {
//                        line = bufferedReader.readLine();
//                        data = data + line;
//                    }
//
//                    Log.e("dddddddddddddd", "" + data);
//                    JSONObject jsonobj = new JSONObject(data);
//                    JSONObject jObject = jsonobj.getJSONObject("user");
//                    message = (String) jsonobj.get("msg");
//                    singleParsed = (String) jsonobj.get("status");
//                    Storeuser = (String) jObject.get("username");
//                    Storeid = (String) jObject.get("_id");
//                    Storemob = (String) jObject.get("phone");
//
//                    Log.e("ddddddddd", "" + singleParsed);
//                    Log.e("ddddddddd", "" + Storeuser);
//                    Log.e("ddddddddd", "" + message);
//                    dataParsed = dataParsed + singleParsed + "\n";
//
//
//
//                } catch (MalformedURLException e) {
//                    e.printStackTrace();
////                    WriteFile(e);
//                } catch (IOException e) {
//                    e.printStackTrace();
////                    WriteFile(e);
//                } catch (JSONException e) {
//                    e.printStackTrace();
////                    WriteFile(e);
//                }
//
//            }
//
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            super.onPostExecute(aVoid);
//            if (singleParsed.equals("1")) {
////                        pbar.setVisibility(View.INVISIBLE);
//                dbHelper.insertData(Storeuser, Storeid);
//                Intent i=new Intent(Login.this,Sidemenu.class);
//                startActivity(i);
//
//            } else {
//                Toast.makeText(Login.this, "" + message, Toast.LENGTH_SHORT).show();
//            }
//
//        }
//    }



    private void getLogin(String usernamestr, String passstr) {

        final ProgressDialog progressDialog = ProgressDialog.show(MainActivity.this,
                "Please wait",
                "Loading...");

        RequestQueue requestQueue= Volley.newRequestQueue(MainActivity.this);
        String url = Api.Loginurl;
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //let's parse json data
                Log.e("response",""+response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject obj = jsonObject.getJSONObject("reg");
                    String msg = obj.getString("msg");

                    if (msg.equals("Success")) {

                         name = obj.getString("vendor_id");
                        String mob = obj.getString("vendor_fname");
                        String sts = obj.getString("vendor_mobile");
                        String plan = obj.getString("vendor_plan");
                        defalt= Integer.parseInt(obj.getString("vendor_basic"));
                        mEditor.putString("v_mob", obj.getString("vendor_mobile"));
                        mEditor.putString("v_mail", obj.getString("vendor_email"));
                        mEditor.commit();


                        if (plan.equals("0")) {
                            if (defalt==1){
                                startPayment1();
                            }else{
                                Toast.makeText(MainActivity.this, ""+msg, Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(MainActivity.this, Plans.class);
                                intent.putExtra("vendorid", ""+name);
                                startActivity(intent);
                                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_from_right);
                            }






                        }else{




                                String vendor_pimage = obj.getString("vendor_pimage");
                                String vendor_pvideo = obj.getString("vendor_pvideo");
                                dbHelper.insertData(name,plan,vendor_pimage,vendor_pvideo);
                                Toast.makeText(MainActivity.this, ""+msg, Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(MainActivity.this, Bottommenu.class);
                                startActivity(intent);
                                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_from_right);




                        }

                    } else {
                        Toast.makeText(MainActivity.this, ""+msg, Toast.LENGTH_SHORT).show();
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
                Toast.makeText(MainActivity.this, "Post Data : Response Failed", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params=new HashMap<String, String>();
                params.put("mobile", ""+usernamestr);
                params.put("password", ""+passstr);
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
    private void requestPermission(){
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
//                selectImagesFromGallery();
                checkusername = username1.getText().toString();
                checkpassword = password1.getText().toString();
                //validate form
                if(validateLogin(checkusername, checkpassword)){
                    //do loginhj

                    Log.e("ffffffffffffffff",""+checkusername);
                    Log.e("ffffffffffffffff",""+checkpassword);

                    getLogin(checkusername,checkpassword);
//                    pbar.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(MainActivity.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }
        };
        TedPermission.with(this)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .check();
    }

    @Override
    public void onBackPressed() {
        //  super.onBackPressed();
        moveTaskToBack(true);

    }
    private void startPayment1() {
        final Activity activity = this;
        final Checkout checkout = new Checkout();

        JSONObject object = new JSONObject();
        int amt = 1 * 100;
        try {
            object.put("key", "rzp_live_9iwQ2J6jQ2eJhI");
            object.put("name", "Happy Celebrations");
            object.put("description", "Plan Description");
            //You can omit the image option to fetch the image from dashboard
            object.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            object.put("currency", "INR");
            object.put("amount", ""+amt);

            JSONObject preFill = new JSONObject();
            preFill.put("email", "");
            preFill.put("contact", "");

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

        final ProgressDialog progressDialogg = ProgressDialog.show(MainActivity.this,
                "Please wait",
                "Loading...");

        RequestQueue requestQueue= Volley.newRequestQueue(MainActivity.this);
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

                        Toast toast = Toast.makeText(MainActivity.this, "Your Booking Successfully...", Toast.LENGTH_LONG);

                        toast.show();
                        dbHelper.insertData(name,planid,image,videos);
                        Intent i=new Intent(MainActivity.this,Bottommenu.class);
                        startActivity(i);
                    } else {

                        Toast.makeText(MainActivity.this, "Network Failed!", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(MainActivity.this, "Post Data : Response Failed", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params=new HashMap<String, String>();
                params.put("pid", planid);
                params.put("vid", name);
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


}
