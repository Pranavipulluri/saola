package org.meicode.saola;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;

public class UserGuideActivity extends AppCompatActivity {

    private ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_guide);

        // Initialize the back button
        backButton = findViewById(R.id.backButton);

        // Set OnClickListener for the back button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back to MainActivity when the back button is clicked
                Intent intent = new Intent(UserGuideActivity.this, MainActivity.class);
                startActivity(intent);
                // Finish the current activity if you don't want it in the back stack
                finish();
            }
        });
    }
}
