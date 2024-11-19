package org.meicode.saola;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText emailEditText, passwordEditText;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        Button loginButton = findViewById(R.id.loginButton);
        Button signUpButton = findViewById(R.id.signUpButton);
        View welcomeText = findViewById(R.id.welcomeText);

        // Load fade-in animation
        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        Animation slideInTop = AnimationUtils.loadAnimation(this, R.anim.slide_in);

        // Apply animation to the views
        emailEditText.startAnimation(fadeIn);
        passwordEditText.startAnimation(fadeIn);
        loginButton.startAnimation(fadeIn);
        welcomeText.setVisibility(View.VISIBLE);
        welcomeText.startAnimation(slideInTop);
        signUpButton.startAnimation(fadeIn);

        // Navigate to RegisterActivity for new user registration
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        // Handle login when the login button is clicked
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInUser();
            }
        });
    }

    // Method to handle user sign-in with Firebase Authentication
    private void signInUser() {
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        // Check if the email or password is empty
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(LoginActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if the email domain is correct (must end with @vnrvjiet.in)
        if (!email.endsWith("@vnrvjiet.in")) {
            Toast.makeText(LoginActivity.this, "Email must be a @vnrvjiet.in domain", Toast.LENGTH_SHORT).show();
            return;
        }

        // Sign in the user with email and password
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            // Reload user data to get the updated email verification status
                            user.reload().addOnCompleteListener(reloadTask -> {
                                if (reloadTask.isSuccessful()) {
                                    Log.d("LoginActivity", "Reload successful");
                                    Log.d("LoginActivity", "Email Verified Status: " + user.isEmailVerified());

                                    // Check if the email is verified
                                    if (user.isEmailVerified()) {
                                        // Email is verified, proceed to MainActivity
                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        finish(); // Close LoginActivity to prevent going back
                                    } else {
                                        Toast.makeText(LoginActivity.this, "Please verify your email first.", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    // Reload failed, show an error message
                                    Log.d("LoginActivity", "Reload failed: " + reloadTask.getException().getMessage());
                                    Toast.makeText(LoginActivity.this, "Error reloading user data. Please try again.", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    } else {
                        // Login failed, show the error message
                        Toast.makeText(LoginActivity.this, "Login failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
