package com.company.moon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class RetailerRegistrationActivity extends AppCompatActivity {
    ConstraintLayout toolbar;
    EditText editTextShopName, editTextGSTNumber;
    Spinner spinnerNatureOfBuss;
    CheckBox checkBoxTnC;
    TextView textViewTnC;
    Button buttonNext;

    ApiRequest apiRequest;

    SQLiteDatabase userInfoDatabase;

    String shopName, numberGST, bussNature, user_id;
    Boolean boolTnC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retailer_registration);
        toolbar = findViewById(R.id.toolbar_distributer_registration);
        TextView textView = toolbar.findViewById(R.id.toolbar_head);
        textView.setText("Register as retailer");
        ImageView imageBack = findViewById(R.id.back_arrow);
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        editTextShopName = findViewById(R.id.editTextStdPrice);
        editTextGSTNumber = findViewById(R.id.editTextQuantity);
        spinnerNatureOfBuss = findViewById(R.id.editTextMRP);
        textViewTnC = findViewById(R.id.textViewTnC);
        checkBoxTnC = findViewById(R.id.checkBoxTnC2);
        buttonNext = findViewById(R.id.buttonNextDistributerAdd2);

        userInfoDatabase = this.openOrCreateDatabase("UserInfoDatabase", MODE_PRIVATE, null);

        apiRequest = ApiRequest.getInstance();

        if (getIntent().getStringExtra("user_id") != null) {
            user_id = getIntent().getStringExtra("user_id");
        } else {
            // Fetch from SQLiteDatabase
            Cursor cursor = userInfoDatabase.rawQuery("SELECT * FROM UserInfoTable", null);
            cursor.moveToFirst();
            user_id = cursor.getString(cursor.getColumnIndex("user_id"));
        }

        // Adapter for nature of business spinner
        ArrayList<String> natureBuss = new ArrayList<>();
        natureBuss.add("Nature of Business");
        natureBuss.add("Electric");
        natureBuss.add("Hardware");
        ArrayAdapter<String> natureAdapter = new ArrayAdapter<>(RetailerRegistrationActivity.this, android.R.layout.simple_spinner_dropdown_item, natureBuss);
        spinnerNatureOfBuss.setAdapter(natureAdapter);

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
        bussNature = spinnerNatureOfBuss.getSelectedItem().toString().trim();
        boolTnC = checkBoxTnC.isChecked();

        if (shopName.isEmpty()) {
            editTextShopName.setError("This field can not be empty");
        } else if (bussNature.equals("Nature of Business")) {
            Toast.makeText(RetailerRegistrationActivity.this, "Select nature of business", Toast.LENGTH_SHORT).show();
        } else if (numberGST.equals("")) {
            editTextGSTNumber.setError("This field cannot ber empty");
        } else if (!boolTnC) {
            Toast.makeText(this, "Accept the Terms and Conditions", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(RetailerRegistrationActivity.this, RetailerRegProfilePicActivity.class);
            intent.putExtra("user_id", user_id);
            intent.putExtra("Shop Name", shopName);
            intent.putExtra("GST Number", numberGST);
            intent.putExtra("Nature", spinnerNatureOfBuss.getSelectedItem().toString());
            startActivity(intent);
        }
    }
}
