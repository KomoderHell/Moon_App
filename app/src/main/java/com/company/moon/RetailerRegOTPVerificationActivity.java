package com.company.moon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class RetailerRegOTPVerificationActivity extends AppCompatActivity {

    ConstraintLayout toolbar;
    TextView textViewResend;
    EditText editTextOTP;
    Button buttonNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retailer_reg_otpverification);
        toolbar = findViewById(R.id.toolbar_retailer_registration);
        TextView textView = toolbar.findViewById(R.id.toolbar_head);
        textView.setText("Register as retailer");
        ImageView imageBack = findViewById(R.id.back_arrow);
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        textViewResend = findViewById(R.id.textViewResend);
        editTextOTP = findViewById(R.id.editTextOTP);
        buttonNext = findViewById(R.id.buttonNext);

        textViewResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RetailerRegOTPVerificationActivity.this, "OTP resent", Toast.LENGTH_SHORT).show();
            }
        });

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next();
            }
        });
    }

    private void next() {
        String enteredOTP = editTextOTP.getText().toString().trim();
        if(enteredOTP.isEmpty()){
            editTextOTP.setError("wrong OTP");
        }
        else{
            startActivity(new Intent(RetailerRegOTPVerificationActivity.this,RetailerRegProfilePicActivity.class));
        }
    }


}
