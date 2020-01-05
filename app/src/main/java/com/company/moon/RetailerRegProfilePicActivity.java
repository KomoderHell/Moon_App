package com.company.moon;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.loader.content.CursorLoader;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.net.URI;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetailerRegProfilePicActivity extends AppCompatActivity {

    public static final int PICK_IMAGE_REQUEST = 1;
    private Uri imageUri;

    ConstraintLayout toolbar;
    ImageView imageViewProfilePic;
    Button buttonFinish;

    private ApiRequest apiRequest;

    private SQLiteDatabase userInfoDatabase;

    private String user_id, name, nature, gstin;
    private MultipartBody.Part logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retailer_reg_profile_pic);

        toolbar = findViewById(R.id.toolbar_distributer_registration);
        TextView textView = toolbar.findViewById(R.id.toolbar_head);
        textView.setText("Register as retailer");
        ImageView imageBack = findViewById(R.id.back_arrow);
        imageViewProfilePic = findViewById(R.id.profile_pic);
        buttonFinish = findViewById(R.id.buttonFinishDistributer);

        userInfoDatabase = this.openOrCreateDatabase("UserInfoDatabase", MODE_PRIVATE, null);

        apiRequest = ApiRequest.getInstance();

        user_id = getIntent().getStringExtra("user_id");
        name = getIntent().getStringExtra("Shop Name");
        gstin = getIntent().getStringExtra("GST Number");
        nature = getIntent().getStringExtra("Nature");
        logo = null;

        if (nature.equals("Electric")) {
            nature = "0";
        } else if (nature.equals("Hardware")) {
            nature = "1";
        }

        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        imageViewProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });
        buttonFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadRetailerDetails();
            }
        });
    }

    private void chooseImage() {
        if (ContextCompat.checkSelfPermission(RetailerRegProfilePicActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            // Permission is not given (Ask for permission
            ActivityCompat.requestPermissions(RetailerRegProfilePicActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    3);
        } else {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_PICK);
            startActivityForResult(intent, PICK_IMAGE_REQUEST);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();

            String imagePath = getRealPathFromUri(imageUri);
            File file = new File(imagePath);
            RequestBody body = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            logo = MultipartBody.Part.createFormData("logo", file.getName(), body);
            Log.d("retro:", "onActivityResult: " + file.getName());

            Picasso.get().load(imageUri).into(imageViewProfilePic);
        }

        if (requestCode == 3 && resultCode == RESULT_OK) {
            Toast.makeText(this, "Permission Granted!!!", Toast.LENGTH_SHORT).show();
        }
    }

    private String getRealPathFromUri(Uri uri) {
        String[] projection = new String[]{MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(getApplicationContext(), uri, projection, null, null, null);
        Cursor cursor = loader.loadInBackground();
        cursor.moveToFirst();
        String result = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();
        Log.d("image Path:", "getRealPathFromUri: " + result);
        return result;
    }

    private void uploadRetailerDetails() {
        if (logo == null) {
            Toast.makeText(this, "Choose a photo to upload!!!", Toast.LENGTH_SHORT).show();
        } else {
            ProgressDialog dialog = new ProgressDialog(RetailerRegProfilePicActivity.this);
            dialog.setMessage("Saving...");
            dialog.setCancelable(false);
            dialog.show();

            RequestBody user_id_reqBody = RequestBody.create(MediaType.parse("text/plain"), user_id);
            RequestBody name_req_body = RequestBody.create(MediaType.parse("text/plain"), name);
            RequestBody nature_req_body = RequestBody.create(MediaType.parse("text/plain"), nature);
            RequestBody gstin_req_body = RequestBody.create(MediaType.parse("text/plain"), gstin);
            apiRequest.retailerDetailsUpload(logo, user_id_reqBody, name_req_body, nature_req_body, gstin_req_body, new Callback<Integer>() {
                @Override
                public void onResponse(Call<Integer> call, Response<Integer> response) {
                    if (!response.isSuccessful()) {
                        //Log.d("retro:", "onResponse: " + "response failed " + response.code());
                        Toast.makeText(RetailerRegProfilePicActivity.this, "Error uploading image", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        return;
                    }

                    dialog.dismiss();

                    // update the fill_status in the SQLite Database to be 1
                    Cursor cursor = userInfoDatabase.rawQuery("SELECT * FROM UserInfoTable", null);
                    if (cursor.getCount() > 0) {
                        cursor.moveToFirst();
                        String userId = cursor.getString(cursor.getColumnIndex("user_id"));
                        String type = cursor.getString(cursor.getColumnIndex("type"));

                        userInfoDatabase.execSQL("DELETE FROM UserInfoTable");

                        String sql = "INSERT INTO UserInfoTable(user_id, type, fill_status) VALUES(?, ?, ?)";
                        SQLiteStatement statement = userInfoDatabase.compileStatement(sql);
                        statement.bindString(1, userId);
                        statement.bindString(2, type);
                        statement.bindString(3, "1");
                        statement.execute();
                    }

                    Intent intent = new Intent(RetailerRegProfilePicActivity.this, HomeScreenActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }

                @Override
                public void onFailure(Call<Integer> call, Throwable t) {
                    //Log.d("retro:", "onFailure: " + t.getLocalizedMessage());
                    Toast.makeText(RetailerRegProfilePicActivity.this, "Error uploading image", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            });
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 3:
                // Storage Permission code
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission Granted!!!", Toast.LENGTH_SHORT).show();
                    chooseImage();
                } else {
                    // Do nothing
                }
                break;
        }
    }
}
