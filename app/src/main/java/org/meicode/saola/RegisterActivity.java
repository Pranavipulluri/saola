package org.meicode.saola;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private EditText emailEditText, passwordEditText, confirmPasswordEditText;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        registerButton = findViewById(R.id.registerButton);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                String confirmPassword = confirmPasswordEditText.getText().toString().trim();

                if (validateEmail(email) && validatePassword(password, confirmPassword)) {
                    // Proceed to OTP verification
                    Intent intent = new Intent(RegisterActivity.this, OtpVerificationActivity.class);
                    intent.putExtra("email", email);
                    startActivity(intent);
                }
            }
        });
    }

    private boolean validateEmail(String email) {
        if (TextUtils.isEmpty(email)) {
            emailEditText.setError("Email is required");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.setError("Enter a valid email address");
            return false;
        } else if (!email.endsWith("@vnrvjiet.in")) {
            emailEditText.setError("Only @vnrvjiet.in emails are allowed");
            return false;
        }
        return true;
    }

    private boolean validatePassword(String password, String confirmPassword) {
        if (TextUtils.isEmpty(password)) {
            passwordEditText.setError("Password is required");
            return false;
        } else if (password.length() < 6) {
            passwordEditText.setError("Password should be at least 6 characters");
            return false;
        } else if (!password.equals(confirmPassword)) {
            confirmPasswordEditText.setError("Passwords do not match");
            return false;
        }
        return true;
    }
}
