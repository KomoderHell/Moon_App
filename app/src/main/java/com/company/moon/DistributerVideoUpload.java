package com.company.moon;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class DistributerVideoUpload extends AppCompatActivity {
    Button buttonNextDisVid;

    ConstraintLayout toolbarDis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.distributer_company_video);
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
        buttonNextDisVid = findViewById(R.id.buttonNextDistributerVid);
        buttonNextDisVid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DistributerVideoUpload.this, "Registered", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(DistributerVideoUpload.this, HomeScreenActivity.class));
            }
        });

    }

}

