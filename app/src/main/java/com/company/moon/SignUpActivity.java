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

    String phone, email, createPassword, confirmPassword;

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
        buttonLogin = findViewById(R.id.buttonNext);
        buttonRegister = findViewById(R.id.buttonRegister);

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
            }
        });

    }

    private void registerUser() {
        email = editTextEmail.getText().toString().trim();
        phone = editTextPhone.getText().toString().trim();
        createPassword = editTextCreatePassword.getText().toString().trim();
        confirmPassword = editTextConfirmPassword.getText().toString().trim();

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
        } else if (!confirmPassword.matches(createPassword)) {
            editTextConfirmPassword.setError("Does not matches the created password");
        } else {
            Toast.makeText(this, "Registered", Toast.LENGTH_SHORT).show();
        }
    }
}
