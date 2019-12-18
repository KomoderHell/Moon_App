package com.company.moon;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class DistributerAddProduct3 extends AppCompatActivity {
    Button buttonNextDis;
    EditText noOfPiece,Price;
    ConstraintLayout toolbarDis;
    String Piece,price;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.distributer_add_3);
        buttonNextDis = findViewById(R.id.buttonNextDistributerAdd3);

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
        noOfPiece=findViewById(R.id.editTextNoOfPieces);
        Price=findViewById(R.id.textViewPrice);

    }
    private void next() {
            Piece = noOfPiece.getText().toString().trim();
        price = Price.getText().toString().trim();
        if (Piece.isEmpty()){
            noOfPiece.setError("This field can not be empty");
        }


        else{
            if(price.isEmpty()){
                startActivity(new Intent(DistributerAddProduct3.this,DistributerAddProduct4.class));
            }
            else {
                startActivity(new Intent(DistributerAddProduct3.this,DistributerAddProduct4.class));
            }
        }
    }
}
