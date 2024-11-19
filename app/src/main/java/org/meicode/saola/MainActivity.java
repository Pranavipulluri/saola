package org.meicode.saola;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the Logout button
        Button logoutButton = findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform logout actions
                logout();

                // Navigate to the Login page
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish(); // Close MainActivity
            }
        });

        // Initialize Button 1
        Button button1 = findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Button 1 clicked", Toast.LENGTH_SHORT).show();

                // Navigate back to MainActivity when the back button is clicked
                Intent intent = new Intent(MainActivity.this, UserGuideActivity.class);
                startActivity(intent);
                // Finish the current activity if you don't want it in the back stack
                finish();
            }
        });

        // Initialize Button 2
        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show a message when Button 2 is clicked
                Toast.makeText(MainActivity.this, "Button 2 clicked", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, BiometricActivity.class);
                startActivity(intent);
                // Finish the current activity if you don't want it in the back stack
                finish();
            }
        });
    }

    // Logout method
    private void logout() {
        // Clear user session or token here if you have one
        // For example, SharedPreferences could be used:
        // SharedPreferences preferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        // SharedPreferences.Editor editor = preferences.edit();
        // editor.clear();
        // editor.apply();

        // Show a message indicating logout
        Toast.makeText(MainActivity.this, "You have been logged out", Toast.LENGTH_SHORT).show();
    }
}
