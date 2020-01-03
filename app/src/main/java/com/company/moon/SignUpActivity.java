package com.company.moon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {

    ConstraintLayout toolbar;
    EditText editTextPhone, editTextEmail, editTextCreatePassword, editTextConfirmPassword;
    Button buttonLogin, buttonRegister;
    RadioGroup radioGroup;

    String phone, email, createPassword, confirmPassword, type;

    ApiRequest apiRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
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

            apiRequest.signUpUser(email, createPassword, confirmPassword, phone, type, new Callback<Integer>() {
                @Override
                public void onResponse(Call<Integer> call, Response<Integer> response) {
                    if (!response.isSuccessful()) {
                        //  Log.d("retro:", "onResponse: " + "response failed!!");
                        dialog.dismiss();
                        Toast.makeText(SignUpActivity.this, "Error while signing up", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    int responseType = response.body();
                    //Log.d("retro:", "onResponse: response OK " + Integer.toString(responseType));
                    dialog.dismiss();

                    switch (responseType) {
                        case 1:
                            // Success
                            Toast.makeText(SignUpActivity.this, "Registered", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SignUpActivity.this, HomeScreenActivity.class));
                            finish();
                            break;
                        case 2:
                            // Already handled before
                            Toast.makeText(SignUpActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                            break;
                        case 3:
                            // Incorrect email format (handled before)
                            Toast.makeText(SignUpActivity.this, "Incorrect email format", Toast.LENGTH_SHORT).show();
                            break;
                        case 4:
                            // Email already exists
                            Toast.makeText(SignUpActivity.this, "Email already exists", Toast.LENGTH_SHORT).show();
                            break;
                        case 5:
                            // Incorrect number format (handled before)
                            Toast.makeText(SignUpActivity.this, "Incorrect number format", Toast.LENGTH_SHORT).show();
                            break;
                        case 6:
                            // Phone number exists
                            Toast.makeText(SignUpActivity.this, "Phone number already exists", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }

                @Override
                public void onFailure(Call<Integer> call, Throwable t) {
                    dialog.dismiss();
                    Toast.makeText(SignUpActivity.this, "Error while signing up", Toast.LENGTH_SHORT).show();
                    //Log.d("retro:", "onFailure: " + t.getLocalizedMessage());
                }
            });
        }
    }
}
