package com.example.ethanman04.memory;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.ethanman04.allone.AppActivity;
import com.example.ethanman04.allone.R;

public class MemoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory);

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

}