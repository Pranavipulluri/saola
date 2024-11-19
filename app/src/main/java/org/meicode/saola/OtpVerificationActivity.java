package org.meicode.saola;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.FirebaseException;

import java.util.concurrent.TimeUnit;

public class OtpVerificationActivity extends AppCompatActivity {

    private EditText phoneEditText, otpEditText;
    private FirebaseAuth mAuth;
    private String verificationId;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Initialize UI elements
        phoneEditText = findViewById(R.id.phoneEditText);
        otpEditText = findViewById(R.id.otpEditText);
        Button sendOtpButton = findViewById(R.id.sendOtpButton);
        Button verifyOtpButton = findViewById(R.id.verifyOtpButton);

        // Initialize the OTP callback
        callbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                // Automatically verify and sign in the user
                signInWithPhoneAuthCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Toast.makeText(OtpVerificationActivity.this, "Verification failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(verificationId, forceResendingToken);
                // Store the verification ID for later use
                OtpVerificationActivity.this.verificationId = verificationId;
                Toast.makeText(OtpVerificationActivity.this, "OTP sent to your phone.", Toast.LENGTH_SHORT).show();
            }
        };

        // Send OTP when the button is clicked
        sendOtpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = phoneEditText.getText().toString();
                if (TextUtils.isEmpty(phone)) {
                    Toast.makeText(OtpVerificationActivity.this, "Please enter your phone number", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Send OTP to the phone number
                sendOtp(phone);
            }
        });

        // Verify OTP when the button is clicked
        verifyOtpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = otpEditText.getText().toString();
                if (TextUtils.isEmpty(code)) {
                    Toast.makeText(OtpVerificationActivity.this, "Please enter the OTP", Toast.LENGTH_SHORT).show();
                    return;
                }

                verifyOtp(code);
            }
        });
    }

    // Send OTP to the user's phone number
    private void sendOtp(String phoneNumber) {
        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(mAuth)
                .setPhoneNumber(phoneNumber)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(callbacks)
                .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    // Verify the OTP entered by the user
    private void verifyOtp(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithPhoneAuthCredential(credential);
    }

    // Sign the user in with the credential obtained from OTP
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // OTP verification successful, redirect to main activity
                        Intent intent = new Intent(OtpVerificationActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(OtpVerificationActivity.this, "OTP verification failed.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
