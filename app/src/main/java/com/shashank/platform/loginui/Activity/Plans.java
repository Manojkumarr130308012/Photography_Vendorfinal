package com.shashank.platform.loginui.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.shashank.platform.loginui.Adapter.SingleCheckAdapter;
import com.shashank.platform.loginui.Model.PersonItem;
import com.shashank.platform.loginui.R;

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


        mSingleCheckList.clear();
        for (int i = 0; i < 3; i++) {
            mSingleCheckList.add(new PersonItem("Plan"+""+i));
        }

        mAdapter = new SingleCheckAdapter(this, mSingleCheckList);
        mRecyclerView = (RecyclerView) findViewById(R.id.recylcerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getApplicationContext(), position + " - " + mSingleCheckList.get(position).getPersonName(), Toast.LENGTH_SHORT).show();
    }
}