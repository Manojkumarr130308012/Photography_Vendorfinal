package com.shashank.platform.loginui.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.shashank.platform.loginui.Api.Api;
import com.shashank.platform.loginui.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    String message;
    ImageView imageView;
    TextView textView;
    int count = 0;
    Button signin,signout;
    String checkusername,checkpassword;
    String suburl;
    EditText username1,password1;
    ProgressBar pbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.imageView);
        textView = findViewById(R.id.textView);
        signin = findViewById(R.id.signin);
        signout = findViewById(R.id.signout);
        username1 = findViewById(R.id.editText);
        password1 = findViewById(R.id.editText2);
        pbar = (ProgressBar) findViewById(R.id.log_pbar);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkusername = username1.getText().toString();
                checkpassword = password1.getText().toString();
                //validate form
                if(validateLogin(checkusername, checkpassword)){
                    //do loginhj

                    Log.e("ffffffffffffffff",""+checkusername);
                    Log.e("ffffffffffffffff",""+checkpassword);

                    postData(checkusername,checkpassword);
                    pbar.setVisibility(View.VISIBLE);
                }

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
    public void postData(String usernamestr, String passstr) {

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JSONObject object = new JSONObject();
        try {
//            SimpleDateFormat currentDate = new SimpleDateFormat("dd/MM/yyyy");
//            Date todayDate = new Date();
//            String thisDate = currentDate.format(todayDate);
            Log.e("xddddd",""+ usernamestr);Log.e("xddddd",""+ passstr);Log.e("xddddd",""+ usernamestr);Log.e("xddddd",""+ passstr);
            object.put("mobile",""+checkusername);
            object.put("password",""+checkpassword);



        } catch (JSONException e) {
            e.printStackTrace();
        }


        String url =Api.Loginurl;

        // Enter the correct url for your api service site

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,url,object,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            message = (String) response.get("status");
                            Log.e("xdddddddddddd",""+ response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if(message.equals("success")){
                            try {
                                JSONObject jObject = response.getJSONObject("UserDetails");
//                                userid=(String) jObject.get("UserID");
//                                DisplayName=(String) jObject.get("DisplayName");
//                                Log.e("xddddd",""+userid);
                                JSONArray jObject1 = jObject.getJSONArray("OrganizationDetails");
                                for(int i=0;i<jObject1.length();i++){
                                    JSONObject jsonObject = jObject1.getJSONObject(i);
//                                    orgid.add(i,""+jsonObject.optString("ID"));
//                                    orgname.add(i,""+jsonObject.optString("OrganizationName"));
                                }



                                //            if (singleParsed.equals("1")) {
////                        pbar.setVisibility(View.INVISIBLE);
//                dbHelper.insertData(Storeuser, Storeid);
//                Intent i=new Intent(Login.this,Sidemenu.class);
//                startActivity(i);
//
//            } else {
//                Toast.makeText(Login.this, "" + message, Toast.LENGTH_SHORT).show();
//            }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }else{
                            Toast.makeText(MainActivity.this, "Invalid Username And Password", Toast.LENGTH_SHORT).show();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("xddddd",""+error);

            }
        });
        requestQueue.add(jsonObjectRequest);
    }
}
