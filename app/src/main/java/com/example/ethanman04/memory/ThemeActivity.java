package com.example.ethanman04.memory;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.ethanman04.allone.PreferenceKeys;
import com.example.ethanman04.allone.R;

import java.util.HashMap;

public class ThemeActivity extends AppCompatActivity {

    private HashMap<String, Integer> hashMap;
    private ImageView view;
    private int saveTheme;
    private int color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme);

        generateHash();
        setSpinner();
        setButtons();
    }

    private void generateHash(){
        hashMap = new HashMap<>();
        hashMap.put("blue", R.drawable.blue);
        hashMap.put("brown", R.drawable.brown);
        hashMap.put("dark_blue", R.drawable.dark_blue);
        hashMap.put("green", R.drawable.green);
        hashMap.put("grey", R.drawable.grey);
        hashMap.put("lime", R.drawable.lime);
        hashMap.put("orange", R.drawable.orange);
        hashMap.put("pink", R.drawable.pink);
        hashMap.put("purple", R.drawable.purple);
        hashMap.put("purple_spotted", R.drawable.purple_spotted);
        hashMap.put("rainbow", R.drawable.rainbow);
        hashMap.put("red_pink", R.drawable.red_pink);
        hashMap.put("teal", R.drawable.teal);
        hashMap.put("white_green", R.drawable.white_green);
    }

    private void setSpinner(){
        Spinner spinner = findViewById(R.id.memory_theme_spinner);
        view = findViewById(R.id.memory_theme_image_view);
        String[] themes = {"blue","brown","dark_blue","green","grey","lime","orange","pink","purple",
                "purple_spotted","rainbow","red_pink","teal","white_green"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, themes);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                changeImage(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void changeImage(int pos){
                switch(pos){
            case 0:
                view.setImageDrawable(getResources().getDrawable( hashMap.get("blue")));
                saveTheme = hashMap.get("blue");
                color = R.color.blue;
                break;
            case 1:
                view.setImageDrawable(getResources().getDrawable(hashMap.get("brown")));
                saveTheme = hashMap.get("brown");
                color = R.color.brown;
                break;
            case 2:
                view.setImageDrawable(getResources().getDrawable(hashMap.get("dark_blue")));
                saveTheme = hashMap.get("dark_blue");
                color = R.color.dark_blue;
                break;
            case 3:
                view.setImageDrawable(getResources().getDrawable(hashMap.get("green")));
                saveTheme = hashMap.get("green");
                color = R.color.green;
                break;
            case 4:
                view.setImageDrawable(getResources().getDrawable(hashMap.get("grey")));
                saveTheme = hashMap.get("grey");
                color = R.color.grey;
                break;
            case 5:
                view.setImageDrawable(getResources().getDrawable(hashMap.get("lime")));
                saveTheme = hashMap.get("lime");
                color = R.color.lime;
                break;
            case 6:
                view.setImageDrawable(getResources().getDrawable(hashMap.get("orange")));
                saveTheme = hashMap.get("orange");
                color = R.color.orange;
                break;
            case 7:
                view.setImageDrawable(getResources().getDrawable(hashMap.get("pink")));
                saveTheme = hashMap.get("pink");
                color = R.color.pink;
                break;
            case 8:
                view.setImageDrawable(getResources().getDrawable(hashMap.get("purple")));
                saveTheme = hashMap.get("purple");
                color = R.color.purple;
                break;
            case 9:
                view.setImageDrawable(getResources().getDrawable(hashMap.get("purple_spotted")));
                saveTheme = hashMap.get("purple_spotted");
                color = R.color.purple_spotted;
                break;
            case 10:
                view.setImageDrawable(getResources().getDrawable(hashMap.get("rainbow")));
                saveTheme = hashMap.get("rainbow");
                color = R.color.rainbow;
                break;
            case 11:
                view.setImageDrawable(getResources().getDrawable(hashMap.get("red_pink")));
                saveTheme = hashMap.get("red_pink");
                color = R.color.red_pink;
                break;
            case 12:
                view.setImageDrawable(getResources().getDrawable(hashMap.get("teal")));
                saveTheme = hashMap.get("teal");
                color = R.color.teal;
                break;
            case 13:
                view.setImageDrawable(getResources().getDrawable(hashMap.get("white_green")));
                saveTheme = hashMap.get("white_green");
                color = R.color.white_green;
                break;
        }
    }

    private void setButtons(){
        Button cancelButton = findViewById(R.id.memory_theme_cancel);
        final Button saveButton = findViewById(R.id.memory_theme_save);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ThemeActivity.this, MemoryActivity.class);
                startActivity(intent);
            }
        });

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        final SharedPreferences.Editor editor = sp.edit();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putInt(PreferenceKeys.MEMORY_THEME, saveTheme);
                editor.putInt(PreferenceKeys.MEMORY_THEME_COLOR, color);
                editor.apply();
                Intent intent = new Intent(ThemeActivity.this, MemoryActivity.class);
                startActivity(intent);
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
