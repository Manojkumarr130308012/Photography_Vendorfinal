package com.shashank.platform.loginui.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.chaos.view.PinView;
import com.shashank.platform.loginui.R;


import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

public class ForgotOTPActivity extends AppCompatActivity {

    TextView displayTxt,textU;
    String maill,otpp,idd;
    PinView pinView;
    CircularProgressButton submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_o_t_p);

        displayTxt = findViewById(R.id.displayTxt);
        textU = findViewById(R.id.textView_noti);
        pinView = findViewById(R.id.pinView);
        submitButton = findViewById(R.id.submitButton);

        Intent intent = getIntent();
        maill = intent.getStringExtra("maill");
        otpp = intent.getStringExtra("otpp");
        idd = intent.getStringExtra("idd");

        displayTxt.setText("OTP sent on your registered email address at "+maill);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (otpp.equals(pinView.getText().toString())) {
                    Intent intent = new Intent(ForgotOTPActivity.this, ForgotPageActivity.class);
                    intent.putExtra("idd",idd );
                    startActivity(intent);
                    finish();
                } else {
                    pinView.setLineColor(Color.RED);
                    textU.setText(" Incorrect OTP");
                    textU.setTextColor(Color.RED);
                }
            }
        });
    }
}