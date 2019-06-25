package com.example.ethanman04.memory;

import android.content.Intent;
import android.content.SharedPreferences;
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

public class MemoryThemeActivity extends AppCompatActivity {

    private HashMap<String, Integer> hashMap;
    private ImageView view;
    private Button cancelButton;
    private Button saveButton;
    private Spinner spinner;
    private int saveTheme;
    private int color;
    private int boarder;
    private int style;
    private SetSound setSound;
    private boolean selectedOnce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme);

        setSound = SetSound.getInstance();
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
        spinner = findViewById(R.id.memory_theme_spinner);
        view = findViewById(R.id.memory_theme_image_view);
        selectedOnce = false;
        String[] themes = {"Blue","Brown","Dark Blue","Green","Grey","Lime","Orange","Pink","Purple",
                "Purple Spotted","Rainbow","Red Pink","Teal","White Green"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, themes);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (selectedOnce) {
                    setSound.startButtonNoise(MemoryThemeActivity.this);
                }
                else {
                    selectedOnce = true;
                }

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
                cancelButton.setBackground(getResources().getDrawable(R.drawable.memory_boarder_blue));
                saveButton.setBackground(getResources().getDrawable(R.drawable.memory_boarder_blue));
                spinner.setBackground(getResources().getDrawable(R.drawable.memory_boarder_blue));
                saveTheme = hashMap.get("blue");
                color = R.color.blue;
                boarder = R.drawable.memory_boarder_blue;
                style = R.style.BlueTheme;
                break;
            case 1:
                view.setImageDrawable(getResources().getDrawable(hashMap.get("brown")));
                cancelButton.setBackground(getResources().getDrawable(R.drawable.memory_boarder_brown));
                saveButton.setBackground(getResources().getDrawable(R.drawable.memory_boarder_brown));
                spinner.setBackground(getResources().getDrawable(R.drawable.memory_boarder_brown));
                saveTheme = hashMap.get("brown");
                color = R.color.brown;
                boarder = R.drawable.memory_boarder_brown;
                style = R.style.BrownTheme;
                break;
            case 2:
                view.setImageDrawable(getResources().getDrawable(hashMap.get("dark_blue")));
                cancelButton.setBackground(getResources().getDrawable(R.drawable.memory_boarder_darkblue));
                saveButton.setBackground(getResources().getDrawable(R.drawable.memory_boarder_darkblue));
                spinner.setBackground(getResources().getDrawable(R.drawable.memory_boarder_darkblue));
                saveTheme = hashMap.get("dark_blue");
                color = R.color.dark_blue;
                boarder = R.drawable.dark_blue;
                style = R.style.DarkBlueTheme;
                break;
            case 3:
                view.setImageDrawable(getResources().getDrawable(hashMap.get("green")));
                cancelButton.setBackground(getResources().getDrawable(R.drawable.memory_boarder_green));
                saveButton.setBackground(getResources().getDrawable(R.drawable.memory_boarder_green));
                spinner.setBackground(getResources().getDrawable(R.drawable.memory_boarder_green));
                saveTheme = hashMap.get("green");
                color = R.color.green;
                boarder = R.drawable.memory_boarder_green;
                style = R.style.GreenTheme;
                break;
            case 4:
                view.setImageDrawable(getResources().getDrawable(hashMap.get("grey")));
                cancelButton.setBackground(getResources().getDrawable(R.drawable.memory_boarder_grey));
                saveButton.setBackground(getResources().getDrawable(R.drawable.memory_boarder_grey));
                spinner.setBackground(getResources().getDrawable(R.drawable.memory_boarder_grey));
                saveTheme = hashMap.get("grey");
                color = R.color.grey;
                boarder = R.drawable.memory_boarder_grey;
                style = R.style.GreyTheme;
                break;
            case 5:
                view.setImageDrawable(getResources().getDrawable(hashMap.get("lime")));
                cancelButton.setBackground(getResources().getDrawable(R.drawable.memory_boarder_lime));
                saveButton.setBackground(getResources().getDrawable(R.drawable.memory_boarder_lime));
                spinner.setBackground(getResources().getDrawable(R.drawable.memory_boarder_lime));
                saveTheme = hashMap.get("lime");
                color = R.color.lime;
                boarder = R.drawable.memory_boarder_lime;
                style = R.style.LimeTheme;
                break;
            case 6:
                view.setImageDrawable(getResources().getDrawable(hashMap.get("orange")));
                cancelButton.setBackground(getResources().getDrawable(R.drawable.memory_boarder_orange));
                saveButton.setBackground(getResources().getDrawable(R.drawable.memory_boarder_orange));
                spinner.setBackground(getResources().getDrawable(R.drawable.memory_boarder_orange));
                saveTheme = hashMap.get("orange");
                color = R.color.orange;
                boarder = R.drawable.memory_boarder_orange;
                style = R.style.OrangeTheme;
                break;
            case 7:
                view.setImageDrawable(getResources().getDrawable(hashMap.get("pink")));
                cancelButton.setBackground(getResources().getDrawable(R.drawable.memory_boarder_pink));
                saveButton.setBackground(getResources().getDrawable(R.drawable.memory_boarder_pink));
                spinner.setBackground(getResources().getDrawable(R.drawable.memory_boarder_pink));
                saveTheme = hashMap.get("pink");
                color = R.color.pink;
                boarder = R.drawable.memory_boarder_pink;
                style = R.style.PinkTheme;
                break;
            case 8:
                view.setImageDrawable(getResources().getDrawable(hashMap.get("purple")));
                cancelButton.setBackground(getResources().getDrawable(R.drawable.memory_boarder_purple));
                saveButton.setBackground(getResources().getDrawable(R.drawable.memory_boarder_purple));
                spinner.setBackground(getResources().getDrawable(R.drawable.memory_boarder_purple));
                saveTheme = hashMap.get("purple");
                color = R.color.purple;
                boarder = R.drawable.memory_boarder_purple;
                style = R.style.PurpleTheme;
                break;
            case 9:
                view.setImageDrawable(getResources().getDrawable(hashMap.get("purple_spotted")));
                cancelButton.setBackground(getResources().getDrawable(R.drawable.memory_boarder_purplespotted));
                saveButton.setBackground(getResources().getDrawable(R.drawable.memory_boarder_purplespotted));
                spinner.setBackground(getResources().getDrawable(R.drawable.memory_boarder_purplespotted));
                saveTheme = hashMap.get("purple_spotted");
                color = R.color.purple_spotted;
                boarder = R.drawable.memory_boarder_purplespotted;
                style = R.style.PurpleSpottedTheme;
                break;
            case 10:
                view.setImageDrawable(getResources().getDrawable(hashMap.get("rainbow")));
                cancelButton.setBackground(getResources().getDrawable(R.drawable.memory_boarder_rainbow));
                saveButton.setBackground(getResources().getDrawable(R.drawable.memory_boarder_rainbow));
                spinner.setBackground(getResources().getDrawable(R.drawable.memory_boarder_rainbow));
                saveTheme = hashMap.get("rainbow");
                color = R.color.rainbow;
                boarder = R.drawable.memory_boarder_rainbow;
                style = R.style.RainbowTheme;
                break;
            case 11:
                view.setImageDrawable(getResources().getDrawable(hashMap.get("red_pink")));
                cancelButton.setBackground(getResources().getDrawable(R.drawable.memory_boarder_redpink));
                saveButton.setBackground(getResources().getDrawable(R.drawable.memory_boarder_redpink));
                spinner.setBackground(getResources().getDrawable(R.drawable.memory_boarder_redpink));
                saveTheme = hashMap.get("red_pink");
                color = R.color.red_pink;
                boarder = R.drawable.memory_boarder_redpink;
                style = R.style.RedPinkTheme;
                break;
            case 12:
                view.setImageDrawable(getResources().getDrawable(hashMap.get("teal")));
                cancelButton.setBackground(getResources().getDrawable(R.drawable.memory_boarder_teal));
                saveButton.setBackground(getResources().getDrawable(R.drawable.memory_boarder_teal));
                spinner.setBackground(getResources().getDrawable(R.drawable.memory_boarder_teal));
                saveTheme = hashMap.get("teal");
                color = R.color.teal;
                boarder = R.drawable.memory_boarder_teal;
                style = R.style.TealTheme;
                break;
            case 13:
                view.setImageDrawable(getResources().getDrawable(hashMap.get("white_green")));
                cancelButton.setBackground(getResources().getDrawable(R.drawable.memory_boarder_whitegreen));
                saveButton.setBackground(getResources().getDrawable(R.drawable.memory_boarder_whitegreen));
                spinner.setBackground(getResources().getDrawable(R.drawable.memory_boarder_whitegreen));
                saveTheme = hashMap.get("white_green");
                color = R.color.white_green;
                boarder = R.drawable.memory_boarder_whitegreen;
                style = R.style.WhiteGreenTheme;
                break;
        }
    }

    private void setButtons(){
        cancelButton = findViewById(R.id.memory_theme_cancel);
        saveButton = findViewById(R.id.memory_theme_save);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSound.startButtonNoise(MemoryThemeActivity.this);
                Intent intent = new Intent(MemoryThemeActivity.this, MemoryActivity.class);
                startActivity(intent);
            }
        });

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        final SharedPreferences.Editor editor = sp.edit();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSound.startButtonNoise(MemoryThemeActivity.this);
                editor.putInt(PreferenceKeys.MEMORY_THEME, saveTheme);
                editor.putInt(PreferenceKeys.MEMORY_THEME_COLOR, color);
                editor.putInt(PreferenceKeys.MEMORY_THEME_BOARDER, boarder);
                editor.putInt(PreferenceKeys.MEMORY_THEME_STYE, style);
                editor.apply();
                Intent intent = new Intent(MemoryThemeActivity.this, MemoryActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Sets the Android back button to go to AppActivity from this
     */
    @Override
    public void onBackPressed(){
        setSound.startButtonNoise(MemoryThemeActivity.this);
        Intent intent = new Intent(MemoryThemeActivity.this, MemoryActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        setSound.pauseMusic();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        setSound.resumeMusic();
    }
}
