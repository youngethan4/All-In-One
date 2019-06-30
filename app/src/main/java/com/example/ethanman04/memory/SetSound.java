package com.example.ethanman04.memory;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.preference.PreferenceManager;
import com.example.ethanman04.allone.PreferenceKeys;
import com.example.ethanman04.allone.R;

class SetSound {

    //Makes an instance of SetSound that can always be accessed.
    private static SetSound instance = new SetSound();
    public static SetSound getInstance(){
        return  instance;
    }

    private MediaPlayer music;

    /**
     * Plays a tapping sound based on if the shared preference to mute noise is not true.
     * @param context
     */
     void startButtonNoise(Context context){
         SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
         if (!sp.getBoolean(PreferenceKeys.MEMORY_SOUND_CHECKED, false)) {
             MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.button);
             mediaPlayer.start();
         }
    }

    /**
     * Plays a card sound based on if the shared preference to mute noise is not true.
     * @param context
     */
    void startCardNoise(Context context){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        if (!sp.getBoolean(PreferenceKeys.MEMORY_SOUND_CHECKED, false)) {
            MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.card_button);
            mediaPlayer.start();
        }
    }

    /**
     * Plays a card match sound based on if the shared preference to mute noise is not true.
     * @param context
     */
    void startMatchNoise(Context context){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        if (!sp.getBoolean(PreferenceKeys.MEMORY_SOUND_CHECKED, false)) {
            MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.card_fail);
            mediaPlayer.start();
        }
    }

    /**
     * Starts the background music on a loop based on if the shared preference to mute music is not true.
     * @param context
     */
    void startMusic(Context context){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        if (!sp.getBoolean(PreferenceKeys.MEMORY_MUSIC_CHECKED, false)) {
            music = MediaPlayer.create(context, R.raw.bensound_memories);
            music.start();
            music.setLooping(true);
        }
    }

    /**
     * Stops the music.
     */
    void stopMusic() {
         music.stop();
    }

    /**
     * Pauses the music.
     */
    void pauseMusic(){
         music.pause();
    }

    /**
     * Resumes the music.
     */
    void resumeMusic(){
         music.start();
    }
}
