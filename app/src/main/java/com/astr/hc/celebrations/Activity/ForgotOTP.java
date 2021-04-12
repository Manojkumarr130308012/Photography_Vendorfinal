package com.astr.hc.celebrations.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.astr.hc.celebrations.R;
import com.chaos.view.PinView;

public class ForgotOTP extends AppCompatActivity {
    Button btnSubmit;
    TextView displayTxt,textU;
    String maill,otpp,idd;
    PinView pinView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_o_t_p2);
        btnSubmit = findViewById(R.id.btnSubmit);
        displayTxt = findViewById(R.id.displayTxt);
        textU = findViewById(R.id.textView_noti);
        pinView = findViewById(R.id.pinView);

        Intent intent = getIntent();
        maill = intent.getStringExtra("maill");
        otpp = intent.getStringExtra("otpp");
        idd = intent.getStringExtra("idd");

        displayTxt.setText("OTP sent on your registered email address at "+maill);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (otpp.equals(pinView.getText().toString())) {
                    Intent intent = new Intent(ForgotOTP.this, ForgotPage.class);
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