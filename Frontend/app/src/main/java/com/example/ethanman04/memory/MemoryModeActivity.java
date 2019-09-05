package com.example.ethanman04.memory;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.ethanman04.allone.PreferenceKeys;
import com.example.ethanman04.allone.R;

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


    }

    private void getHighScore(){

    }

    /**
     * Uses shared preferences to save the high score of each game subset
     */
    private void setHighScore() {

        TextView hsTime30 = findViewById(R.id.memory_high_score_30);
        TextView hsTime20 = findViewById(R.id.memory_high_score_20);


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
