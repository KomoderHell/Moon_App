package com.company.moon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    private SQLiteDatabase userInfoDatabase;

    android.os.Handler Handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Creating the database if not exists already
        userInfoDatabase = this.openOrCreateDatabase("UserInfoDatabase", MODE_PRIVATE, null);
        // user_id --> Id of the user
        // type --> admin, manufacturer, retailer
        userInfoDatabase.execSQL("CREATE TABLE IF NOT EXISTS UserInfo(user_id varchar2(20), type varchar2(1))");

        Handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Cursor cursor = userInfoDatabase.rawQuery("SELECT * FROM UserInfo", null);
                Intent intent = null;
                if (cursor.getCount() > 0) {
                    // Go to HomeScreenActivity (Remember me was clicked before)
                    cursor.moveToFirst();
                    intent = new Intent(MainActivity.this, HomeScreenActivity.class);
                    intent.putExtra("Type", cursor.getString(cursor.getColumnIndex("type")));
                } else {
                    intent = new Intent(MainActivity.this, SignInActivity.class);
                }
                startActivity(intent);
                finish();
            }
        }, 2500);
    }
}
