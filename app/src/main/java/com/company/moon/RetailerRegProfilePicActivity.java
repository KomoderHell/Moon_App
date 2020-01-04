package com.company.moon;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.loader.content.CursorLoader;

import android.content.Intent;
import android.database.Cursor;
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
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data!=null && data.getData()!= null){
            imageUri = data.getData();

            String imagePath = getRealPathFromUri(imageUri);
            File file = new File(imagePath);
            RequestBody body = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            logo = MultipartBody.Part.createFormData("logo", file.getName(), body);
            Log.d("retro:", "onActivityResult: " + file.getName());

            Picasso.get().load(imageUri).into(imageViewProfilePic);
        }
    }

    private String getRealPathFromUri(Uri uri) {
        String[] projection = new String[] {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(getApplicationContext(), uri, projection, null, null, null);
        Cursor cursor = loader.loadInBackground();
        cursor.moveToFirst();
        String result = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();
        Log.d("image Path:", "getRealPathFromUri: " + result);
        return result;
    }

    private void uploadRetailerDetails() {
        RequestBody user_id_reqBody = RequestBody.create(MediaType.parse("text/palin"), user_id);
        RequestBody name_req_body = RequestBody.create(MediaType.parse("text/palin"), name);
        RequestBody nature_req_body = RequestBody.create(MediaType.parse("text/palin"), nature);
        RequestBody gstin_req_body = RequestBody.create(MediaType.parse("text/palin"), gstin);
        apiRequest.retailerDetailsUpload(logo, user_id_reqBody, name_req_body, nature_req_body, gstin_req_body, new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (!response.isSuccessful()) {
                    Log.d("retro:", "onResponse: " + "response failed " + response.code());
                    return;
                }
                Log.d("retro:", "onResponse: " + response.body());
                Toast.makeText(RetailerRegProfilePicActivity.this, "Registered", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(RetailerRegProfilePicActivity.this,HomeScreenActivity.class));
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("retro:", "onFailure: " + t.getLocalizedMessage());
            }
        });
    }
}
