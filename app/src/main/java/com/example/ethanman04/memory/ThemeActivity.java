package com.example.ethanman04.memory;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.ethanman04.allone.R;

public class ThemeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme);

        setButtons();
    }

    private void setButtons(){
        Button cancelButton = findViewById(R.id.memory_theme_cancel);
        Button saveButton = findViewById(R.id.memory_theme_save);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ThemeActivity.this, MemoryActivity.class);
                startActivity(intent);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    /**
     * Sets the Android back button to go to AppActivity from this
     */
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(ThemeActivity.this, MemoryActivity.class);
        startActivity(intent);
    }
}
