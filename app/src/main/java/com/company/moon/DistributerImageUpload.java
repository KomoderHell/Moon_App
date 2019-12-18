package com.company.moon;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class DistributerImageUpload extends AppCompatActivity {
    Button buttonNextDisImg;
    public static final int PICK_IMAGE_REQUEST = 1;
    ConstraintLayout toolbarDis;
    ImageView pic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_upload_distributer);
        buttonNextDisImg = findViewById(R.id.buttonNextDistributerAdd1);
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
        buttonNextDisImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next();
            }
        });
        pic = findViewById(R.id.UploadBttn1);
        pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();

            }
        });
    }
    private void next() {

        startActivity(new Intent(DistributerImageUpload.this,DistributerVideoUpload.class));
    }
    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }
}
