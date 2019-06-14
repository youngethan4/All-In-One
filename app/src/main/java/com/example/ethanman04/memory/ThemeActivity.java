package com.example.ethanman04.memory;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.ethanman04.allone.R;

public class ThemeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme);
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
