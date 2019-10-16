package com.example.ethanman04.memory;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.ethanman04.Login.ProfileActivity;
import com.example.ethanman04.allone.MainActivity;
import com.example.ethanman04.allone.PreferenceKeys;
import com.example.ethanman04.allone.R;

public class MemoryActivity extends AppCompatActivity {

    private SetSound setSound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory);

        Toolbar myToolbar = findViewById(R.id.memory_toolbar);
        setSupportActionBar(myToolbar);
        Drawable drawable = myToolbar.getOverflowIcon();
        if(drawable != null) {
            drawable = DrawableCompat.wrap(drawable);
            DrawableCompat.setTint(drawable.mutate(), getResources().getColor(R.color.white));
            myToolbar.setOverflowIcon(drawable);
        }
        setSound = SetSound.getInstance();
        setSound.startMusic(MemoryActivity.this);

        setClicks();
    }

    /**
     * Sets the theme based on what is in the shared preference key
     * @return the theme
     */
    @Override
    public Resources.Theme getTheme() {
        Resources.Theme theme = super.getTheme();
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        theme.applyStyle(sp.getInt(PreferenceKeys.MEMORY_THEME_STYE, R.style.BlueTheme), true);
        return theme;
    }

    /**
     * Sets both button's onclick methods to open up the grid activity with the correct number of
     * cards as an extra.
     */
    private void setClicks(){
        Button singleplayer = findViewById(R.id.memory_button_singleplayer);
        Button multiplayer = findViewById(R.id.memory_button_multiplayer);
        TextView tv = findViewById(R.id.memory_welcome);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        singleplayer.setBackground(getResources().getDrawable(sp.getInt(PreferenceKeys.MEMORY_THEME_BOARDER, R.drawable.memory_boarder_blue)));
        multiplayer.setBackground(getResources().getDrawable(sp.getInt(PreferenceKeys.MEMORY_THEME_BOARDER, R.drawable.memory_boarder_blue)));
        tv.setTextColor(getResources().getColor(sp.getInt(PreferenceKeys.MEMORY_THEME_COLOR, R.color.blue)));

        singleplayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSound.startButtonNoise(MemoryActivity.this);
                Intent intent = new Intent(MemoryActivity.this, MemoryModeActivity.class);
                intent.putExtra("multiplayer", false);
                startActivity(intent);

            }
        });

        multiplayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSound.startButtonNoise(MemoryActivity.this);
                Intent intent = new Intent(MemoryActivity.this, MemoryModeActivity.class);
                intent.putExtra("multiplayer", true);
                startActivity(intent);
            }
        });
    }

    /**
     * Sets the Android back button to go to the home screen
     */
    @Override
    public void onBackPressed(){
        setSound.startButtonNoise(MemoryActivity.this);
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }

    /**
     * Creates the options menu and looks in the shared preferences to see if music or sound is muted.
     * @param menu
     * @return true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_memory_options, menu);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        boolean muteMusic = sp.getBoolean(PreferenceKeys.MEMORY_MUSIC_CHECKED, false);
        boolean muteSound = sp.getBoolean(PreferenceKeys.MEMORY_SOUND_CHECKED, false);
        if(muteMusic) {
            MenuItem item = menu.findItem(R.id.memory_options_music);
            item.setChecked(true);
        }
        if(muteSound){
            MenuItem item = menu.findItem(R.id.memory_options_sound);
            item.setChecked(true);
        }
        return true;
    }

    /**
     * Sets what happens when a user selects a menu item. For mute music and sound, updates the shared
     * preferences.
     * The options menu alloys for profile editing, theme editing, music muting, sound muting, and
     * logging out.
     * @param item Item that will be selected by the user.
     * @return Item that was selected.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        boolean muteMusic = sp.getBoolean(PreferenceKeys.MEMORY_MUSIC_CHECKED, false);
        boolean muteSound = sp.getBoolean(PreferenceKeys.MEMORY_SOUND_CHECKED, false);
        SharedPreferences.Editor editor = sp.edit();

        switch(item.getItemId()) {
            case R.id.memory_options_profile:
                setSound.startButtonNoise(MemoryActivity.this);
                Intent intent = new Intent(MemoryActivity.this, ProfileActivity.class);
                startActivity(intent);
                break;
            case R.id.memory_options_theme:
                setSound.startButtonNoise(MemoryActivity.this);
                Intent i = new Intent(MemoryActivity.this, MemoryThemeActivity.class);
                startActivity(i);
                break;
            case R.id.memory_options_music:
                if (muteMusic) {
                    item.setChecked(false);
                    editor.putBoolean(PreferenceKeys.MEMORY_MUSIC_CHECKED, false);
                    editor.apply();
                    setSound.startMusic(MemoryActivity.this);
                    setSound.startButtonNoise(MemoryActivity.this);
                }
                else {
                    setSound.stopMusic();
                    item.setChecked(true);
                    editor.putBoolean(PreferenceKeys.MEMORY_MUSIC_CHECKED, true);
                    setSound.startButtonNoise(MemoryActivity.this);
                }
                break;
            case R.id.memory_options_sound:
                if (muteSound) {
                    item.setChecked(false);
                    editor.putBoolean(PreferenceKeys.MEMORY_SOUND_CHECKED, false);
                    setSound.startButtonNoise(MemoryActivity.this);
                }
                else {
                    item.setChecked(true);
                    editor.putBoolean(PreferenceKeys.MEMORY_SOUND_CHECKED, true);
                }
                break;
            case R.id.memory_options_logout:
                setSound.startButtonNoise(MemoryActivity.this);
                editor.putInt(PreferenceKeys.LOGGED_IN_USER_ID, 0);
                Intent main = new Intent(MemoryActivity.this, MainActivity.class);
                startActivity(main);
        }
        editor.apply();
        return super.onOptionsItemSelected(item);
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