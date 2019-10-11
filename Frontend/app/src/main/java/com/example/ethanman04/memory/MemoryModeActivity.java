package com.example.ethanman04.memory;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.ethanman04.allone.R;
import com.example.ethanman04.multiplayer.MultiplayerActivity;

import java.util.Locale;

public class MemoryModeActivity extends AppCompatActivity {

    private SetSound setSound;
    private boolean isMultiplayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory_mode);

        setSound = SetSound.getInstance();
        isMultiplayer = getIntent().getExtras().getBoolean("multiplayer");
        if (isMultiplayer) getHighScore();
        setButtons();

    }

    private void setButtons(){
        Button time20 = findViewById(R.id.memory_mode_button_time_20);
        Button time30 = findViewById(R.id.memory_mode_button_time_30);
        Button moves20 = findViewById(R.id.memory_mode_button_moves_20);
        Button moves30 = findViewById(R.id.memory_mode_button_moves_30);

        time20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSound.startButtonNoise(MemoryModeActivity.this);
                if (isMultiplayer){
                    Intent intent = new Intent(MemoryModeActivity.this, MultiplayerActivity.class);
                    intent.putExtra("type", "time20");
                    intent.putExtra("cards", 20);
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(MemoryModeActivity.this, MemoryGridActivity.class);
                    intent.putExtra("type", "time20");
                    intent.putExtra("cards", 20);
                    startActivity(intent);
                }
            }
        });
        time30.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSound.startButtonNoise(MemoryModeActivity.this);
                if (isMultiplayer){
                    Intent intent = new Intent(MemoryModeActivity.this, MultiplayerActivity.class);
                    intent.putExtra("type", "time30");
                    intent.putExtra("cards", 30);
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(MemoryModeActivity.this, MemoryGridActivity.class);
                    intent.putExtra("type", "time30");
                    intent.putExtra("cards", 30);
                    startActivity(intent);
                }
            }
        });
        moves20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSound.startButtonNoise(MemoryModeActivity.this);
                if (isMultiplayer){
                    Intent intent = new Intent(MemoryModeActivity.this, MultiplayerActivity.class);
                    intent.putExtra("type", "moves20");
                    intent.putExtra("cards", 20);
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(MemoryModeActivity.this, MemoryGridActivity.class);
                    intent.putExtra("type", "moves20");
                    intent.putExtra("cards", 20);
                    startActivity(intent);
                }
            }
        });
        moves30.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSound.startButtonNoise(MemoryModeActivity.this);
                if (isMultiplayer){
                    Intent intent = new Intent(MemoryModeActivity.this, MultiplayerActivity.class);
                    intent.putExtra("type", "moves30");
                    intent.putExtra("cards", 30);
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(MemoryModeActivity.this, MemoryGridActivity.class);
                    intent.putExtra("type", "moves30");
                    intent.putExtra("cards", 30);
                    startActivity(intent);
                }
            }
        });
    }

    private void getHighScore(){

    }

    /**
     * Uses shared preferences to save the high score of each game subset
     */
    private void setHighScore() {

       // TextView hsTime30 = findViewById(R.id.memory_high_score_30);
      //  TextView hsTime20 = findViewById(R.id.memory_high_score_20);


    }

    /**
     * Helper method to convert millis to a string value
     * @param millis
     * @return users time in string format
     */
    private String millisToString(long millis) {
        int seconds = (int) (millis / 1000);
        int minutes = seconds / 60;
        seconds = seconds % 60;
        String setTextTimer = "High Score: " + minutes + ":" + String.format(Locale.ENGLISH,"%02d", seconds);
        return setTextTimer;
    }

    /**
     * When the activity is paused, the music will as well.
     */
    @Override
    protected void onPause()
    {
        super.onPause();
        setSound.pauseMusic();
    }

    /**
     * Once the activity is resumed, the music will also be resumed.
     */
    @Override
    protected void onResume()
    {
        super.onResume();
        setSound.resumeMusic(this);
    }
}
