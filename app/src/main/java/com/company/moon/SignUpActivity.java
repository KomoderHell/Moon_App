package com.company.moon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    ConstraintLayout toolbar;
    EditText editTextPhone, editTextEmail, editTextCreatePassword, editTextConfirmPassword;
    Button buttonLogin, buttonRegister;
    RadioGroup radioGroup;

    String phone, email, createPassword, confirmPassword, type;

    private SQLiteDatabase userInfoDatabase;

    ApiRequest apiRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        
        // Finding the views
        toolbar = findViewById(R.id.toolbar_signup);
        TextView textView = toolbar.findViewById(R.id.toolbar_head);
        textView.setText("Sign Up");
        ImageView imageViewBack = findViewById(R.id.back_arrow);
        imageViewBack.setVisibility(View.GONE);
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextCreatePassword = findViewById(R.id.editTextCreatePassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        buttonLogin = findViewById(R.id.buttonNextDistributerAdd2);
        buttonRegister = findViewById(R.id.buttonRegister);
        radioGroup = findViewById(R.id.rg_sign_up);

        userInfoDatabase = this.openOrCreateDatabase("UserInfoDatabase", MODE_PRIVATE, null);

        apiRequest = ApiRequest.getInstance();

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, SignInActivity.class));
                finish();
            }
        });

    }

    private void registerUser() {
        email = editTextEmail.getText().toString().trim();
        phone = editTextPhone.getText().toString().trim();
        createPassword = editTextCreatePassword.getText().toString().trim();
        confirmPassword = editTextConfirmPassword.getText().toString().trim();
        int checkedId = radioGroup.getCheckedRadioButtonId();
        switch (checkedId) {
            case R.id.rb_distributor_sign_up:
                type = "2";
                break;
            case R.id.rb_retailer_sign_up:
                type = "3";
                break;
            default:
                type = "";
                break;
        }

        if (phone.isEmpty()) {
            editTextPhone.setError("This field cannot be empty");
        } else if (email.isEmpty()) {
            editTextEmail.setError("This field cannot be empty");
        } else if (createPassword.isEmpty()) {
            editTextCreatePassword.setError("This field cannot be empty");
        } else if (confirmPassword.isEmpty()) {
            editTextConfirmPassword.setError("This field cannot be empty");
        } else if (!Patterns.PHONE.matcher(phone).matches()) {
            editTextPhone.setError("Enter a valid mobile number");
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Enter a valid email");
        } else if (createPassword.length() < 8) {
            editTextCreatePassword.setError("Enter a valid password");
        } else if (!confirmPassword.equals(createPassword)) {
            editTextConfirmPassword.setError("Does not matches the created password");
        } else if (type.equals("")) {
            Toast.makeText(SignUpActivity.this, "Select sign up as distributor or retailer", Toast.LENGTH_SHORT).show();
        }else {
            // Show a progress dialog while we sign up the user
            ProgressDialog dialog = new ProgressDialog(SignUpActivity.this);
            dialog.setMessage("Signing Up...");
            dialog.setCancelable(false);
            dialog.show();

            apiRequest.signUpUser(email, createPassword, confirmPassword, phone, type, new Callback<List<LogInInfo>>() {
                @Override
                public void onResponse(Call<List<LogInInfo>> call, Response<List<LogInInfo>> response) {
                    if (!response.isSuccessful()) {
                        Log.d("retro:", "onResponse: " + "response failed");
                        dialog.dismiss();
                        return;
                    }
                    List<LogInInfo> users = response.body();
                    Log.d("retro:", "onResponse: " + response.body());

                    dialog.dismiss();
                    
                    userInfoDatabase.execSQL("DELETE FROM UserInfoTable");
                    // Insert in SQLite Database
                    String sql = "INSERT INTO UserInfoTable(user_id, type, fill_status) VALUES(?, ?, ?)";
                    SQLiteStatement statement = userInfoDatabase.compileStatement(sql);
                    statement.bindString(1, users.get(0).getId());
                    statement.bindString(2, users.get(0).getType());
                    statement.bindString(3, users.get(0).getFill_status());
                    statement.execute();

                    Intent intent;

                    if (type.equals("2")) {
                        // Manufacturer
                    } else if (type.equals("3")) {
                        // Retailer
                        if (users.get(0).getFill_status().equals("0")) {
                            // Go to Update profile section
                            intent = new Intent(SignUpActivity.this, RetailerRegistrationActivity.class);
                            intent.putExtra("user_id", users.get(0).getId());
                            startActivity(intent);
                        } else {
                            // Go to HomeScreenActivity
                            intent = new Intent(SignUpActivity.this, HomeScreenActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<LogInInfo>> call, Throwable t) {
                    Log.d("retro:", "onFailure: " + t.getLocalizedMessage());
                    apiRequest.signUpUser2(email, createPassword, confirmPassword, phone, type, new Callback<Integer>() {
                        @Override
                        public void onResponse(Call<Integer> call, Response<Integer> response) {
                            if (!response.isSuccessful()) {
                                dialog.dismiss();
                                Log.d("retro:", "onResponse: " + "response failed internal" + response.code());
                                return;
                            }
                            dialog.dismiss();
                            int responseType = response.body();
                            switch (responseType) {
                                case 2:
                                    // Already handled
                                    dialog.dismiss();
                                    Toast.makeText(SignUpActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                                    break;
                                case 3 :
                                    // Already handled
                                    dialog.dismiss();
                                    Toast.makeText(SignUpActivity.this, "Incorrect Email format", Toast.LENGTH_SHORT).show();
                                    break;
                                case 4:
                                    dialog.dismiss();
                                    Toast.makeText(SignUpActivity.this, "Email already exists", Toast.LENGTH_SHORT).show();
                                    break;
                                case 5:
                                    // Already handled
                                    dialog.dismiss();
                                    Toast.makeText(SignUpActivity.this, "Incorrect phone number format", Toast.LENGTH_SHORT).show();
                                    break;
                                case 6:
                                    dialog.dismiss();
                                    Toast.makeText(SignUpActivity.this, "Phone number already exists", Toast.LENGTH_SHORT).show();
                                    break;
                            }
                        }

                        @Override
                        public void onFailure(Call<Integer> call, Throwable t) {
                            dialog.dismiss();
                            Log.d("retro:", "onFailure: " + t.getLocalizedMessage());
                        }
                    });
                }
            });
        }
    }
}
