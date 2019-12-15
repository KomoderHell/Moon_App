package com.company.moon;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class ActivityDistributer extends AppCompatActivity {
    ConstraintLayout toolbarDis;
    EditText editTextYrsDis,editTextGSTNumberDis,editTextBussNatureDis;
    CheckBox checkBoxTnCDis;
    TextView textViewTnCDis;
    Button buttonNextDis;

    String Years,numberGST,bussNature;
    Boolean boolTnC;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distributer_reg);
        toolbarDis = findViewById(R.id.toolbar_distributer_registration);
        TextView textView = toolbarDis.findViewById(R.id.toolbar_head);
        textView.setText("Register as Distributer");
        ImageView imageBack = findViewById(R.id.back_arrow);
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        editTextYrsDis = findViewById(R.id.editTextYrsDistributer);
        editTextGSTNumberDis = findViewById(R.id.editTextGstDistributer);
        editTextBussNatureDis = findViewById(R.id.editTextBussNatureDistributer);
        textViewTnCDis = findViewById(R.id.textViewTnC);
        checkBoxTnCDis = findViewById(R.id.checkBoxTnC2);
        buttonNextDis = findViewById(R.id.buttonNextDistributer);

        buttonNextDis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next();
            }
        });
    }
    private void next() {
        Years = editTextYrsDis.getText().toString().trim();
        numberGST = editTextGSTNumberDis.getText().toString().trim();
        bussNature = editTextBussNatureDis.getText().toString().trim();
        boolTnC = checkBoxTnCDis.isChecked();

        if (Years.isEmpty()){
            editTextYrsDis.setError("This field can not be empty");
        }
        else if (bussNature.isEmpty()){
            editTextBussNatureDis.setError("This field cannot be empty");
        }
        else if (!boolTnC){
            Toast.makeText(this, "Accept the Terms and Conditions", Toast.LENGTH_SHORT).show();
        }

        else {
            startActivity(new Intent(ActivityDistributer.this, DistributerLocation.class));
        }


    }
}
/*
ConstraintLayout toolbarDis;
toolbarDis = findViewById(R.id.toolbar_distributer_registration);
        TextView textView = toolbarDis.findViewById(R.id.toolbar_head);
        textView.setText("Register as Distributer");
        ImageView imageBack = findViewById(R.id.back_arrow);
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


 */