package com.example.ethanman04.memory;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.ethanman04.allone.AppActivity;
import com.example.ethanman04.allone.PreferenceKeys;
import com.example.ethanman04.allone.R;

import java.util.Locale;

public class MemoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory);

        setHighScore();

        Button b = findViewById(R.id.memory_button1);
        Button b2 = findViewById(R.id.memory_button2);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MemoryActivity.this, MemoryGridActivity.class);
                intent.putExtra("size", 20);
                startActivity(intent);

            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MemoryActivity.this, MemoryGridActivity.class);
                intent.putExtra("size", 30);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(MemoryActivity.this, AppActivity.class);
        startActivity(intent);
    }

    private void setHighScore() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        long highScore30 = sp.getLong(PreferenceKeys.MEMORY_HIGH_SCORE_30, 0);
        long highScore20 = sp.getLong(PreferenceKeys.MEMORY_HIGH_SCORE_20, 0);
        TextView hs30 = findViewById(R.id.memory_high_score_30);
        TextView hs20 = findViewById(R.id.memory_high_score_20);

        if (highScore20 == 0.0) {
            hs20.setText("High Score: none");
        }
        else {
            String str20 = millisToString(highScore20);
            hs20.setText(str20);
        }
        if (highScore30 == 0.0) {
            hs30.setText("High Score: none");
        }
        else {
            String str30 = millisToString(highScore30);
            hs30.setText(str30);
        }

    }

    private String millisToString(long millis) {
        int seconds = (int) (millis / 1000);
        int minutes = seconds / 60;
        seconds = seconds % 60;
        String setTextTimer = "High Score: " + minutes + ":" + String.format(Locale.ENGLISH,"%02d", seconds);
        return setTextTimer;
    }
}