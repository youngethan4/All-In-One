package com.example.ethanman04.allone;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.example.ethanman04.Login.LoginActivity;
import com.example.ethanman04.memory.MemoryActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);

        if (sp.getInt(PreferenceKeys.LOGGED_IN_USER_ID, 0) > 0){
            Intent intent = new Intent(MainActivity.this, MemoryActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }


    }

}
