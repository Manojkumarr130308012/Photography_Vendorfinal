package com.astr.hc.celebrations.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.astr.hc.celebrations.R;


import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

public class ForgotPageActivity extends AppCompatActivity {

    EditText editTextnPass,editTextcPass;
    CircularProgressButton submitButton;
    String idd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_page);

        editTextnPass = findViewById(R.id.editTextnPass);
        editTextcPass = findViewById(R.id.editTextcPass);
        submitButton = findViewById(R.id.submitButton);

        Intent intent = getIntent();
        idd = intent.getStringExtra("idd");

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editTextnPass.getText().toString().isEmpty()) {
                    editTextnPass.requestFocus();
                    editTextnPass.setError("New Password Required");
                } else  if (editTextcPass.getText().toString().isEmpty()) {
                    editTextcPass.requestFocus();
                    editTextcPass.setError("Confirm Password Required");
                } else if (editTextnPass.getText().toString().equals(editTextcPass.getText().toString())) {
                    submitData();
                } else {
                    editTextcPass.requestFocus();
                    editTextcPass.setError("Password does not match!");
                }
            }
        });
    }

    private void submitData() {

        final ProgressDialog progressDialog = ProgressDialog.show(ForgotPageActivity.this,
                "Please wait",
                "Loading...");

        RequestQueue requestQueue= Volley.newRequestQueue(ForgotPageActivity.this);
        String url = "Constant.API_FORGOT_CHANGE_PASSWORD";
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //let's parse json data
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject obj = jsonObject.getJSONObject("reg");
                    String msg = obj.getString("message");

                    if (msg.equals("Success")) {
                        Toast.makeText(ForgotPageActivity.this, "Password Changed Success!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ForgotPageActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();

                    } else {
                        Toast.makeText(ForgotPageActivity.this, ""+msg, Toast.LENGTH_SHORT).show();
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
//                Toast.makeText(ForgotPageActivity.this, "Post Data : Response Failed", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params=new HashMap<String, String>();
                params.put("id", ""+idd);
                params.put("password", ""+editTextcPass.getText().toString());
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