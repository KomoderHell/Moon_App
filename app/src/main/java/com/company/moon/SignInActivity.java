package com.company.moon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SignInActivity extends AppCompatActivity {

    ConstraintLayout toolbar;
    EditText editTextMobileEmail, editTextPassword;
    CheckBox checkBoxRemember;
    Button buttonLogin, buttonRegister;

    String phoneEmail, password;
    Boolean boolRemember;

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

                    Intent intent = new Intent(SignInActivity.this, HomeScreenActivity.class);

                    List<LogInInfo> user = response.body();
                    // Only 1 object is present
                    String type = user.get(0).getType();
                    intent.putExtra("Type", type);
                    startActivity(intent);
                    finish();
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
