package com.shashank.platform.loginui.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.shashank.platform.loginui.R;
import com.tiper.MaterialSpinner;

public class Editprofile extends AppCompatActivity {
    TextInputEditText firstname,lastname,mobile,watsapp,email,address;
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
        watsapp.setText(""+watsapp);
        address.setText(""+vendor_address);
        email.setText(""+vendor_email);
//        email.setText(""+vendor_email);

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });



    }
}