package com.example.ethanman04.memory;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.preference.PreferenceManager;
import com.example.ethanman04.allone.PreferenceKeys;
import com.example.ethanman04.allone.R;

class SetSound {
    private MediaPlayer music;

     void startButtonNoise(Context context){
         SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
         if (!sp.getBoolean(PreferenceKeys.MEMORY_SOUND_CHECKED, false)) {
             MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.button);
             mediaPlayer.start();
         }
    }

    void startCardNoise(Context context){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        if (!sp.getBoolean(PreferenceKeys.MEMORY_SOUND_CHECKED, false)) {
            MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.card_button);
            mediaPlayer.start();
        }
    }

    void startFailNoise(Context context){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        if (!sp.getBoolean(PreferenceKeys.MEMORY_SOUND_CHECKED, false)) {
            MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.card_fail);
            mediaPlayer.start();
        }
    }

    void startMusic(Context context){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        if (!sp.getBoolean(PreferenceKeys.MEMORY_MUSIC_CHECKED, false)) {
            music = MediaPlayer.create(context, R.raw.bensound_memories);
            music.start();
            music.setLooping(true);
        }
    }

    void stopMusic() {
         music.stop();
    }
}
