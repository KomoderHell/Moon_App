package com.company.moon;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.squareup.picasso.Picasso;

public class DistributerAddProduct2 extends AppCompatActivity {
    Button buttonNextDis;
    EditText editTextStdPrice,editTextQty,editTextMrp;
    public static final int PICK_IMAGE_REQUEST = 1;
    private Uri imageUri;
    String StdPrice,Qty,Mrp;

    ConstraintLayout toolbarDis;
    ImageView imageViewProfilePic;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.distributer_add_2);
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
        buttonNextDis = findViewById(R.id.buttonNextDistributerAdd2);
        buttonNextDis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next();
            }
        });
        editTextStdPrice=findViewById(R.id.editTextStdPrice);
        editTextQty=findViewById(R.id.editTextQuantity);
        editTextMrp=findViewById(R.id.editTextMRP);
        imageViewProfilePic = findViewById(R.id.UploadBtn1);
        imageViewProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();

            }
        });

    }
    private void next() {
        StdPrice = editTextStdPrice.getText().toString().trim();
        Qty = editTextQty.getText().toString().trim();
        Mrp = editTextMrp.getText().toString().trim();


        if (StdPrice.isEmpty()){
            editTextStdPrice.setError("This field can not be empty");
        }
        else if (Qty.isEmpty()){
            editTextQty.setError("This field cannot be empty");
        }

        else{
            if(Mrp.isEmpty()){
                startActivity(new Intent(DistributerAddProduct2.this,DistributerAddProduct3.class));
            }
            else {
                startActivity(new Intent(DistributerAddProduct2.this,DistributerAddProduct3.class));
            }
        }
    }
    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }


}
