package com.company.moon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private SQLiteDatabase userInfoDatabase;
    private SQLiteDatabase productCompanyDatabase;

    android.os.Handler Handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Creating the database if not exists already
        userInfoDatabase = this.openOrCreateDatabase("UserInfoDatabase", MODE_PRIVATE, null);
        // user_id --> Id of the user
        // type --> admin, manufacturer, retailer
        // fill_status --> whether or not the user had completed registration
        productCompanyDatabase = this.openOrCreateDatabase("ProductCompanyDatabase", MODE_PRIVATE, null);

        userInfoDatabase.execSQL("CREATE TABLE IF NOT EXISTS UserInfoTable(user_id varchar2(20), type varchar2(1), fill_status varchar2(1))");
        productCompanyDatabase.execSQL("CREATE TABLE IF NOT EXISTS Companies(Name varchar2(50), ID varchar2(20))");
        productCompanyDatabase.execSQL("CREATE TABLE IF NOT EXISTS Products(Name varchar2(50), ID varchar2(20))");
        productCompanyDatabase.execSQL("CREATE TABLE IF NOT EXISTS CurrentProduct(ID varchar2(20))");

        Handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Cursor cursor = userInfoDatabase.rawQuery("SELECT * FROM UserInfoTable", null);
                Intent intent = null;
                if (cursor.getCount() > 0) {
                    // This code is executed only when remember me was selected before
                    cursor.moveToFirst();
                    String typeStr = cursor.getString(cursor.getColumnIndex("type"));
                    if (typeStr.equals("1")) {
                        // Handle admin section here
                    } else if (typeStr.equals("2")) {
                        // Handle manufacturer section here
                    } else if (typeStr.equals("3")) {
                        // Handle retailer section here
                        // If fill_status of retailer is 1 go to HomeScreen else go to RetailerRegistration
                        String fillStatusStr = cursor.getString(cursor.getColumnIndex("fill_status"));
                        Log.d("sqlite:", "run: " + fillStatusStr);
                        if (fillStatusStr.equals("1")) {
                            // Go to HomeScreen
                            intent = new Intent(MainActivity.this, HomeScreenActivity.class);
                        } else {
                            intent = new Intent(MainActivity.this, RetailerRegistrationActivity.class);
                        }
                    }
                } else {
                    intent = new Intent(MainActivity.this, SignInActivity.class);
                }
                startActivity(intent);
                finish();
            }
        }, 2500);
    }
}
