package com.company.moon;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class RetailerRegShopPicUploadActivity extends AppCompatActivity {

    public static final int PICK_IMAGE_1_REQUEST = 1;
    public static final int PICK_IMAGE_2_REQUEST = 2;
    private int imagesAdded = 0;

    ConstraintLayout toolbar;
    ImageView imageViewAdd1, imageViewAdd2, tick1, tick2;
    TextView textViewAdd1, textViewAdd2;
    Button buttonFinish;

    Uri imageUri1, imageUri2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retailer_reg_shop_pic_upload);
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


        buttonFinish = findViewById(R.id.buttonFinish);
        imageViewAdd1 = findViewById(R.id.imageViewAddImg1);
        imageViewAdd2 = findViewById(R.id.imageViewAddImg2);
        textViewAdd1 = findViewById(R.id.textViewAddImg1);
        textViewAdd2 = findViewById(R.id.textViewAddImg2);
        tick1 = findViewById(R.id.imageView3);
        tick2 = findViewById(R.id.imageView4);

        imageViewAdd1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                importImage(PICK_IMAGE_1_REQUEST);
            }
        });
        textViewAdd1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                importImage(PICK_IMAGE_1_REQUEST);
            }
        });
        imageViewAdd2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                importImage(PICK_IMAGE_2_REQUEST);
            }
        });
        textViewAdd2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                importImage(PICK_IMAGE_2_REQUEST);
            }
        });
    }


    private void importImage(int request) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, request);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_1_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri1 = data.getData();

            textViewAdd1.setText("Image addded");
            tick1.setVisibility(View.VISIBLE);
            imagesAdded++;
            if (imageUri1!=null&&imageUri2!=null) {
                buttonFinish.setVisibility(View.VISIBLE);
            }
        }
        if (requestCode == PICK_IMAGE_2_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri2 = data.getData();

            textViewAdd2.setText("Image addded");
            tick2.setVisibility(View.VISIBLE);
            imagesAdded++;
            if (imageUri1!=null&&imageUri2!=null) {
                buttonFinish.setVisibility(View.VISIBLE);
            }
        }

    }
}
