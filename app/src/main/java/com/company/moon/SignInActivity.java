package com.company.moon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignInActivity extends AppCompatActivity {

    ConstraintLayout toolbar;
    EditText editTextMobileEmail , editTextPassword;
    CheckBox checkBoxRemember;
    Button buttonLogin , buttonRegister;

    String phoneEmail,password;
    Boolean boolRemember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        toolbar = findViewById(R.id.toolbar_signin);
        TextView textView = toolbar.findViewById(R.id.toolbar_head);
        textView.setText("Sign in");

        editTextMobileEmail = findViewById(R.id.editTextPhoneEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        checkBoxRemember = findViewById(R.id.checkBox);
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonRegister = findViewById(R.id.buttonRegister);


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
                startActivity(new Intent(SignInActivity.this,SignUpActivity.class));
            }
        });
    }

    public void loginUser(){
        phoneEmail = editTextMobileEmail.getText().toString().trim();
        password = editTextPassword.getText().toString().trim();
        boolRemember = checkBoxRemember.isChecked();

        if (phoneEmail.isEmpty()){
            editTextMobileEmail.setError("This field cannot be empty");
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(phoneEmail).matches()&&!Patterns.PHONE.matcher(phoneEmail).matches()){
            editTextMobileEmail.setError("Enter a valid phone number or email");
        }
        else if (password.isEmpty()){
            editTextPassword.setError("This field cannot be empty");
        }
        else if (password.length()<8){
            editTextPassword.setError("Enter a valid Password");
        }
        else{
            Toast.makeText(this, "Login\n" + boolRemember , Toast.LENGTH_SHORT).show();
        }
    }

}
