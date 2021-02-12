package com.shashank.platform.loginui.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.shashank.platform.loginui.Adapter.SingleCheckAdapter;
import com.shashank.platform.loginui.Api.Api;
import com.shashank.platform.loginui.Model.PersonItem;
import com.shashank.platform.loginui.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Plans extends AppCompatActivity implements AdapterView.OnItemClickListener{

    RecyclerView mRecyclerView;

    private List<PersonItem> mSingleCheckList = new ArrayList<>();
    private SingleCheckAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plans);

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
                                mSingleCheckList.add(new PersonItem(""+jsonObject.getString("plan_name"),""+jsonObject.getString("plan_photos"),""+jsonObject.getString("plan_videos"),""+jsonObject.getString("plan_amount")));
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
        Toast.makeText(getApplicationContext(), position + " - " + mSingleCheckList.get(position).getPersonName(), Toast.LENGTH_SHORT).show();
    }
}