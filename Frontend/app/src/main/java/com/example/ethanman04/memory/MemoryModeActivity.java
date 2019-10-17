package com.example.ethanman04.memory;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.ethanman04.allone.Endpoints;
import com.example.ethanman04.allone.PreferenceKeys;
import com.example.ethanman04.allone.R;
import com.example.ethanman04.allone.VolleyRequests;
import com.example.ethanman04.multiplayer.MultiplayerActivity;

import org.json.JSONObject;

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
        if (!isMultiplayer) setHighScore();
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
                    launchMultiplayer("time20", 20);
                }
                else {
                    launchSingleplayer("time20", 20);
                }
            }
        });
        time30.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSound.startButtonNoise(MemoryModeActivity.this);
                if (isMultiplayer){
                    launchMultiplayer("time30", 30);
                }
                else {
                    launchSingleplayer("time30", 30);
                }
            }
        });
        moves20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSound.startButtonNoise(MemoryModeActivity.this);
                if (isMultiplayer){
                    launchMultiplayer("moves20", 20);
                }
                else {
                    launchSingleplayer("moves20", 20);
                }
            }
        });
        moves30.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSound.startButtonNoise(MemoryModeActivity.this);
                if (isMultiplayer){
                    launchMultiplayer("moves30", 30);
                }
                else {
                    launchSingleplayer("moves30", 30);
                }
            }
        });
    }

    private void launchMultiplayer(String type, int numCards){
        Intent intent = new Intent(MemoryModeActivity.this, MultiplayerActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("cards", numCards);
        startActivity(intent);
    }

    private void launchSingleplayer(String type, int numCards){
        Intent intent = new Intent(MemoryModeActivity.this, MemoryGridActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("cards", numCards);
        startActivity(intent);
    }

    /**
     * Uses shared preferences to retrieve the high score of each game subset
     */
    private void setHighScore() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        TextView hsTime20 = findViewById(R.id.memory_mode_high_score_time_20);
        TextView hsTime30 = findViewById(R.id.memory_mode_high_score_time_30);
        TextView hsMoves20 = findViewById(R.id.memory_mode_high_score_moves_20);
        TextView hsMoves30 = findViewById(R.id.memory_mode_high_score_moves_30);

        long T20 = sp.getLong(PreferenceKeys.MEMORY_HIGH_SCORE_TIME_20, 0);
        long T30 = sp.getLong(PreferenceKeys.MEMORY_HIGH_SCORE_TIME_30,  0);
        int M20 = sp.getInt(PreferenceKeys.MEMORY_HIGH_SCORE_MOVES_20, 0);
        int M30 = sp.getInt(PreferenceKeys.MEMORY_HIGH_SCORE_MOVES_30, 0);
        String ST20 = "High score : " + millisToString(T20);
        String ST30 = "High score : " + millisToString(T30);
        String SM20 = String.format(Locale.US, "High score : %d", M20);
        String SM30 = String.format(Locale.US, "High score : %d", M30);

        if(T20 != 0){
            hsTime20.setText(ST20);
            hsTime20.setVisibility(View.VISIBLE);
        }
        if(T30 != 0){
            hsTime30.setText(ST30);
            hsTime30.setVisibility(View.VISIBLE);
        }
        if(M20 != 0){
            hsMoves20.setText(SM20);
            hsMoves20.setVisibility(View.VISIBLE);
        }
        if(M30 != 0){
            hsMoves30.setText(SM30);
            hsMoves30.setVisibility(View.VISIBLE);
        }
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
        return "" + minutes + ":" + String.format(Locale.US, "%02d", seconds);
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
