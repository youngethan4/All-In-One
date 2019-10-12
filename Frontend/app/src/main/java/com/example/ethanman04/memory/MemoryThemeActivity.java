package com.example.ethanman04.memory;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import com.example.ethanman04.allone.PreferenceKeys;
import com.example.ethanman04.allone.R;

public class MemoryThemeActivity extends AppCompatActivity {

    private ImageView view;
    private Button cancelButton;
    private Button saveButton;
    private Spinner spinner;
    private int saveTheme;
    private int color;
    private int boarder;
    private int style;
    private SetSound setSound;
    private boolean isSetUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme);

        setSound = SetSound.getInstance();    //gets current instance of SetSound
        setSpinner();
        setButtons();
    }

    /**
     * Sets the dropdown spinner with the correct theme names. There is tapping noises as well once
     * the spinner is setup.
     */
    private void setSpinner(){
        spinner = findViewById(R.id.memory_theme_spinner);
        view = findViewById(R.id.memory_theme_image_view);
        isSetUp = false;
        String[] themes = {"Blue","Brown","Dark Blue","Green","Grey","Lime","Orange","Pink","Purple",
                "Purple Spotted","Rainbow","Red Pink","Teal","White Green"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, themes);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //This is so that there is no tapping noise on setup
                if (isSetUp) {
                    setSound.startButtonNoise(MemoryThemeActivity.this);
                }
                else {
                    isSetUp = true;
                }
                changeImage(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    /**
     * Uses a switch and the position of the spinner item selected. This sets the current theme in
     * MemoryThemeActivity as well as saves each part of the theme to preference keys.
     * @param pos
     */
    private void changeImage(int pos){
        switch(pos){
            case 0:
                view.setImageDrawable(getResources().getDrawable(R.drawable.card_blue));
                cancelButton.setBackground(getResources().getDrawable(R.drawable.memory_boarder_blue));
                saveButton.setBackground(getResources().getDrawable(R.drawable.memory_boarder_blue));
                spinner.setBackground(getResources().getDrawable(R.drawable.memory_boarder_blue));
                saveTheme = R.drawable.card_blue;
                color = R.color.blue;
                boarder = R.drawable.memory_boarder_blue;
                style = R.style.BlueTheme;
                break;
            case 1:
                view.setImageDrawable(getResources().getDrawable(R.drawable.card_brown));
                cancelButton.setBackground(getResources().getDrawable(R.drawable.memory_boarder_brown));
                saveButton.setBackground(getResources().getDrawable(R.drawable.memory_boarder_brown));
                spinner.setBackground(getResources().getDrawable(R.drawable.memory_boarder_brown));
                saveTheme = R.drawable.card_brown;
                color = R.color.brown;
                boarder = R.drawable.memory_boarder_brown;
                style = R.style.BrownTheme;
                break;
            case 2:
                view.setImageDrawable(getResources().getDrawable(R.drawable.card_dark_blue));
                cancelButton.setBackground(getResources().getDrawable(R.drawable.memory_boarder_darkblue));
                saveButton.setBackground(getResources().getDrawable(R.drawable.memory_boarder_darkblue));
                spinner.setBackground(getResources().getDrawable(R.drawable.memory_boarder_darkblue));
                saveTheme = R.drawable.card_dark_blue;
                color = R.color.dark_blue;
                boarder = R.drawable.memory_boarder_darkblue;
                style = R.style.DarkBlueTheme;
                break;
            case 3:
                view.setImageDrawable(getResources().getDrawable(R.drawable.card_green));
                cancelButton.setBackground(getResources().getDrawable(R.drawable.memory_boarder_green));
                saveButton.setBackground(getResources().getDrawable(R.drawable.memory_boarder_green));
                spinner.setBackground(getResources().getDrawable(R.drawable.memory_boarder_green));
                saveTheme = R.drawable.card_green;
                color = R.color.green;
                boarder = R.drawable.memory_boarder_green;
                style = R.style.GreenTheme;
                break;
            case 4:
                view.setImageDrawable(getResources().getDrawable(R.drawable.card_grey));
                cancelButton.setBackground(getResources().getDrawable(R.drawable.memory_boarder_grey));
                saveButton.setBackground(getResources().getDrawable(R.drawable.memory_boarder_grey));
                spinner.setBackground(getResources().getDrawable(R.drawable.memory_boarder_grey));
                saveTheme = R.drawable.card_grey;
                color = R.color.grey;
                boarder = R.drawable.memory_boarder_grey;
                style = R.style.GreyTheme;
                break;
            case 5:
                view.setImageDrawable(getResources().getDrawable(R.drawable.card_lime));
                cancelButton.setBackground(getResources().getDrawable(R.drawable.memory_boarder_lime));
                saveButton.setBackground(getResources().getDrawable(R.drawable.memory_boarder_lime));
                spinner.setBackground(getResources().getDrawable(R.drawable.memory_boarder_lime));
                saveTheme = R.drawable.card_lime;
                color = R.color.lime;
                boarder = R.drawable.memory_boarder_lime;
                style = R.style.LimeTheme;
                break;
            case 6:
                view.setImageDrawable(getResources().getDrawable(R.drawable.card_orange));
                cancelButton.setBackground(getResources().getDrawable(R.drawable.memory_boarder_orange));
                saveButton.setBackground(getResources().getDrawable(R.drawable.memory_boarder_orange));
                spinner.setBackground(getResources().getDrawable(R.drawable.memory_boarder_orange));
                saveTheme = R.drawable.card_orange;
                color = R.color.orange;
                boarder = R.drawable.memory_boarder_orange;
                style = R.style.OrangeTheme;
                break;
            case 7:
                view.setImageDrawable(getResources().getDrawable(R.drawable.card_pink));
                cancelButton.setBackground(getResources().getDrawable(R.drawable.memory_boarder_pink));
                saveButton.setBackground(getResources().getDrawable(R.drawable.memory_boarder_pink));
                spinner.setBackground(getResources().getDrawable(R.drawable.memory_boarder_pink));
                saveTheme = R.drawable.card_pink;
                color = R.color.pink;
                boarder = R.drawable.memory_boarder_pink;
                style = R.style.PinkTheme;
                break;
            case 8:
                view.setImageDrawable(getResources().getDrawable(R.drawable.card_purple));
                cancelButton.setBackground(getResources().getDrawable(R.drawable.memory_boarder_purple));
                saveButton.setBackground(getResources().getDrawable(R.drawable.memory_boarder_purple));
                spinner.setBackground(getResources().getDrawable(R.drawable.memory_boarder_purple));
                saveTheme = R.drawable.card_purple;
                color = R.color.purple;
                boarder = R.drawable.memory_boarder_purple;
                style = R.style.PurpleTheme;
                break;
            case 9:
                view.setImageDrawable(getResources().getDrawable(R.drawable.card_purple_spotted));
                cancelButton.setBackground(getResources().getDrawable(R.drawable.memory_boarder_purplespotted));
                saveButton.setBackground(getResources().getDrawable(R.drawable.memory_boarder_purplespotted));
                spinner.setBackground(getResources().getDrawable(R.drawable.memory_boarder_purplespotted));
                saveTheme = R.drawable.card_purple_spotted;
                color = R.color.purple_spotted;
                boarder = R.drawable.memory_boarder_purplespotted;
                style = R.style.PurpleSpottedTheme;
                break;
            case 10:
                view.setImageDrawable(getResources().getDrawable(R.drawable.card_rainbow));
                cancelButton.setBackground(getResources().getDrawable(R.drawable.memory_boarder_rainbow));
                saveButton.setBackground(getResources().getDrawable(R.drawable.memory_boarder_rainbow));
                spinner.setBackground(getResources().getDrawable(R.drawable.memory_boarder_rainbow));
                saveTheme = R.drawable.card_rainbow;
                color = R.color.rainbow;
                boarder = R.drawable.memory_boarder_rainbow;
                style = R.style.RainbowTheme;
                break;
            case 11:
                view.setImageDrawable(getResources().getDrawable(R.drawable.card_red_pink));
                cancelButton.setBackground(getResources().getDrawable(R.drawable.memory_boarder_redpink));
                saveButton.setBackground(getResources().getDrawable(R.drawable.memory_boarder_redpink));
                spinner.setBackground(getResources().getDrawable(R.drawable.memory_boarder_redpink));
                saveTheme = R.drawable.card_red_pink;
                color = R.color.red_pink;
                boarder = R.drawable.memory_boarder_redpink;
                style = R.style.RedPinkTheme;
                break;
            case 12:
                view.setImageDrawable(getResources().getDrawable(R.drawable.card_teal));
                cancelButton.setBackground(getResources().getDrawable(R.drawable.memory_boarder_teal));
                saveButton.setBackground(getResources().getDrawable(R.drawable.memory_boarder_teal));
                spinner.setBackground(getResources().getDrawable(R.drawable.memory_boarder_teal));
                saveTheme = R.drawable.card_teal;
                color = R.color.teal;
                boarder = R.drawable.memory_boarder_teal;
                style = R.style.TealTheme;
                break;
            case 13:
                view.setImageDrawable(getResources().getDrawable(R.drawable.card_white_green));
                cancelButton.setBackground(getResources().getDrawable(R.drawable.memory_boarder_whitegreen));
                saveButton.setBackground(getResources().getDrawable(R.drawable.memory_boarder_whitegreen));
                spinner.setBackground(getResources().getDrawable(R.drawable.memory_boarder_whitegreen));
                saveTheme = R.drawable.card_white_green;
                color = R.color.white_green;
                boarder = R.drawable.memory_boarder_whitegreen;
                style = R.style.WhiteGreenTheme;
                break;
        }
    }

    /**
     * On click listeners for the save and cancel buttons. The preference keys for the theme will
     * not change unless the user taps on save.
     */
    private void setButtons(){
        cancelButton = findViewById(R.id.memory_theme_cancel);
        saveButton = findViewById(R.id.memory_theme_save);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSound.startButtonNoise(MemoryThemeActivity.this);
                finish();
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
        finish();
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
