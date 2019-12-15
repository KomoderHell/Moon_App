package com.company.moon;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class DistributerLocation extends AppCompatActivity {
    Button buttonNextDisLoc;
    ConstraintLayout toolbarDis;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.distributer_location);
        buttonNextDisLoc = findViewById(R.id.buttonNextDistributerLoc);
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
        buttonNextDisLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next();
            }
        });
    }
    private void next() {

        startActivity(new Intent(DistributerLocation.this,DistributerImageUpload.class));
    }
}
