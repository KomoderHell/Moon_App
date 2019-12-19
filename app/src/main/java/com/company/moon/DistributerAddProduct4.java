package com.company.moon;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import static com.company.moon.DistributerAddProduct2.PICK_IMAGE_REQUEST;

public class DistributerAddProduct4 extends AppCompatActivity {
    Button buttonNextDis;
    public static final int PICK_IMAGE_REQUEST = 1;
    ConstraintLayout toolbarDis;
    ImageView imageViewProfilePic1,imageViewProfilePic2,imageViewProfilePic3,imageViewProfilePic4;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.distributer_add_4);
        buttonNextDis = findViewById(R.id.buttonFinishDistributer);
        buttonNextDis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next();
            }
        });
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
        imageViewProfilePic1 = findViewById(R.id.UploadBttn1);
        imageViewProfilePic1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();

            }
        });
        imageViewProfilePic2 = findViewById(R.id.UploadBtn2);
        imageViewProfilePic2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();

            }
        });
        imageViewProfilePic3 = findViewById(R.id.UploadBtn3);
        imageViewProfilePic3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();

            }
        });
        imageViewProfilePic4 = findViewById(R.id.UploadBtn4);
        imageViewProfilePic4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();

            }
        });

    }
    private void next() {
        startActivity(new Intent(DistributerAddProduct4.this, HomeScreenActivity.class));
    }
    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }
}

