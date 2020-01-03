package com.company.moon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInActivity extends AppCompatActivity {

    ConstraintLayout toolbar;
    EditText editTextMobileEmail, editTextPassword;
    CheckBox checkBoxRemember;
    Button buttonLogin, buttonRegister;

    String phoneEmail, password;
    Boolean boolRemember;

    private SQLiteDatabase userInfoDatabase;

    private ApiRequest apiRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        toolbar = findViewById(R.id.toolbar_distributer_registration);
        TextView textView = toolbar.findViewById(R.id.toolbar_head);
        textView.setText("Sign in");
        ImageView imageViewBack = findViewById(R.id.back_arrow);
        imageViewBack.setVisibility(View.GONE);

        editTextMobileEmail = findViewById(R.id.editTextStdPrice);
        editTextPassword = findViewById(R.id.editTextQuantity);
        checkBoxRemember = findViewById(R.id.checkBoxTnC2);
        buttonLogin = findViewById(R.id.buttonNextDistributerAdd2);
        buttonRegister = findViewById(R.id.buttonRegister);

        userInfoDatabase = this.openOrCreateDatabase("UserInfoDatabase", MODE_PRIVATE, null);

        apiRequest = ApiRequest.getInstance();

        checkBoxRemember.setChecked(true);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
                finish();
            }
        });
    }

    public void loginUser() {
        phoneEmail = editTextMobileEmail.getText().toString().trim();
        password = editTextPassword.getText().toString().trim();
        boolRemember = checkBoxRemember.isChecked();

        if (phoneEmail.isEmpty()) {
            editTextMobileEmail.setError("This field cannot be empty");
        } else if (!Patterns.EMAIL_ADDRESS.matcher(phoneEmail).matches() && !Patterns.PHONE.matcher(phoneEmail).matches()) {
            editTextMobileEmail.setError("Enter a valid phone number or email");
        } else if (password.isEmpty()) {
            editTextPassword.setError("This field cannot be empty");
        } else if (password.length() < 8) {
            editTextPassword.setError("Enter a valid Password");
        } else {
            // Show progress bar while we sign in the user
            ProgressDialog dialog = new ProgressDialog(SignInActivity.this);
            dialog.setMessage("Signing In...");
            dialog.setCancelable(false);
            dialog.show();

            apiRequest.logInUser(phoneEmail, password, new Callback<List<LogInInfo>>() {
                @Override
                public void onResponse(Call<List<LogInInfo>> call, Response<List<LogInInfo>> response) {
                    if (!response.isSuccessful()) {
                        Log.d("retro:", "onResponse: response failed");
                        Toast.makeText(SignInActivity.this, "Login failed!!!", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        return;
                    }
                    Log.d("retro:", "onResponse: " + "response OK");
                    dialog.dismiss();

                    Intent intent;

                    List<LogInInfo> user = response.body();

                    String type = user.get(0).getType();
                    // Type is used to identify who is the user
                    // 1 --> Admin
                    // 2 --> Manufacturer
                    // 3 --> Retailer
                    if (type.equals("1")) {
                        // Admin Login
                    } else if (type.equals("2")) {
                        // Manufacturer Login
                    } else if (type.equals("3")) {
                        // Retailer Login
                        if (user.get(0).getFill_status().equals("0")) {
                            // Go to update profile section
                            intent = new Intent(SignInActivity.this, RetailerRegistrationActivity.class);
                            intent.putExtra("user_id", response.body().get(0).getId());
                            startActivity(intent);
                            //finish();
                        } else {
                            // Go to Home Screen
                            intent = new Intent(SignInActivity.this, HomeScreenActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<LogInInfo>> call, Throwable t) {
                    Log.d("retro:", "onFailure: " + t.getLocalizedMessage());
                    Toast.makeText(SignInActivity.this, "Login failed!!!", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            });
        }
    }
}
