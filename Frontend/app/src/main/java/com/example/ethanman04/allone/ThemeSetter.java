package com.example.ethanman04.allone;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.EditText;

public class ThemeSetter {
    private SharedPreferences sp;


    public ThemeSetter(Context context){
        sp = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void setTheme(Button button){
        button.setBackgroundResource(sp.getInt(PreferenceKeys.MEMORY_THEME_BOARDER, R.drawable.memory_boarder_blue));
    }



    public void setTint(EditText editText) {
        int[][] states = new int[][]{
                new int[]{android.R.attr.state_enabled}, // enabled
                new int[]{-android.R.attr.state_enabled}, // disabled
                new int[]{-android.R.attr.state_checked}, // unchecked
                new int[]{android.R.attr.state_pressed}  // pressed
        };

        int[] colors = new int[]{
                sp.getInt(PreferenceKeys.MEMORY_THEME_COLOR, R.color.blue),
                sp.getInt(PreferenceKeys.MEMORY_THEME_COLOR, R.color.blue),
                sp.getInt(PreferenceKeys.MEMORY_THEME_COLOR, R.color.blue),
                sp.getInt(PreferenceKeys.MEMORY_THEME_COLOR, R.color.blue)
        };
        editText.setBackgroundTintList(new ColorStateList(states, colors));
    }
}
