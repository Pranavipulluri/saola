package org.meicode.saola;



import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;

public class BiometricActivity extends AppCompatActivity {

    // Declare UI elements
    private ImageButton backButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biometric); // Make sure this matches your XML file name

        // Initialize UI elements
        backButton = findViewById(R.id.backButton);

        // Set an onClick listener for the back button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back to MainActivity when the back button is pressed
                Intent intent = new Intent(BiometricActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); // Optional: Close the current activity
            }
        });

        // If you have other data to display dynamically, initialize them here
        // Example: TextView fingerprintID = findViewById(R.id.fingerprintID);
        //          fingerprintID.setText("Fingerprint ID: #A12345");
    }
}
