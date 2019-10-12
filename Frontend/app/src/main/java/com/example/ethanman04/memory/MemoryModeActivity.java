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
        if (!isMultiplayer) getHighScore();
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
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        int id = sp.getInt(PreferenceKeys.LOGGED_IN_USER_ID, 0);
        String url = Endpoints.getInstance().getHighScoreEndpoint() + id;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        setHighScore(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("request.error", error.toString());
            }
        });
        VolleyRequests.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }

    /**
     * Uses shared preferences to save the high score of each game subset
     */
    private void setHighScore(JSONObject jsonObject) {
        TextView hsTime20 = findViewById(R.id.memory_mode_high_score_time_20);
        TextView hsTime30 = findViewById(R.id.memory_mode_high_score_time_30);
        TextView hsMoves20 = findViewById(R.id.memory_mode_high_score_moves_20);
        TextView hsMoves30 = findViewById(R.id.memory_mode_high_score_moves_30);
        String hsTime20String = "";
        String hsTime30String = "";
        String hsMoves20String = "";
        String hsMoves30String = "";
        try {
            hsTime20String ="" + jsonObject.getString("time20");
            hsTime30String ="" + jsonObject.getString("time30");
            hsMoves20String ="" + jsonObject.getString("moves20");
            hsMoves30String ="" + jsonObject.getString("moves30");
        }
        catch(Exception e){
            e.printStackTrace();
        }
        hsTime20.setText(hsTime20String);
        hsTime30.setText(hsTime30String);
        hsMoves20.setText(hsMoves20String);
        hsMoves30.setText(hsMoves30String);
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
