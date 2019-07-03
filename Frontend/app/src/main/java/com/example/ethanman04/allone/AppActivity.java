package com.example.ethanman04.allone;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.ethanman04.memory.MemoryActivity;

public class AppActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);
        appButtonClick();
    }

    private void appButtonClick() {
        Button memoryButton = findViewById(R.id.app_memory_button);

        memoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AppActivity.this, MemoryActivity.class);
                startActivity(intent);
            }
        });
    }

}
