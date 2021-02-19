package com.shashank.platform.loginui.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;
import com.shashank.platform.loginui.R;
import com.tiper.MaterialSpinner;

public class Editprofile extends AppCompatActivity {
    TextInputLayout firstname,lastname,mobile,watsapp,email,address;
    MaterialSpinner location,category;
    Button upload;
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




    }
}