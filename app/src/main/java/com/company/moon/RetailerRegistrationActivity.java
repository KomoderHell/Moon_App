package com.company.moon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class RetailerRegistrationActivity extends AppCompatActivity {
    ConstraintLayout toolbar;
    EditText editTextShopName,editTextGSTNumber,editTextBussNature;
    CheckBox checkBoxTnC;
    TextView textViewTnC;
    Button buttonNext;

    String shopName,numberGST,bussNature;
    Boolean boolTnC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retailer_registration);
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

        editTextShopName = findViewById(R.id.editTextShopName);
        editTextGSTNumber = findViewById(R.id.editTextGSTNumber);
        editTextBussNature = findViewById(R.id.editTextBussNature);
        textViewTnC = findViewById(R.id.textViewTnC);
        checkBoxTnC = findViewById(R.id.checkBoxTnC);
        buttonNext = findViewById(R.id.buttonNext);

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next();
            }
        });
    }



    private void next() {
        shopName = editTextShopName.getText().toString().trim();
        numberGST = editTextGSTNumber.getText().toString().trim();
        bussNature = editTextBussNature.getText().toString().trim();
        boolTnC = checkBoxTnC.isChecked();

        if (shopName.isEmpty()){
            editTextShopName.setError("This field can not be empty");
        }
        else if (bussNature.isEmpty()){
            editTextBussNature.setError("This field cannot be empty");
        }
        else if (!boolTnC){
            Toast.makeText(this, "Accept the Terms and Conditions", Toast.LENGTH_SHORT).show();
        }
        else{
            if(numberGST.isEmpty()){
                startActivity(new Intent(RetailerRegistrationActivity.this,RetailerRegShopPicUploadActivity.class));
            }
            else {
                startActivity(new Intent(RetailerRegistrationActivity.this,RetailerRegOTPVerificationActivity.class));
            }
        }
    }
}
