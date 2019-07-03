package com.example.ethanman04.memory;

import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.preference.PreferenceManager;
import android.util.SparseIntArray;

import com.example.ethanman04.allone.PreferenceKeys;
import com.example.ethanman04.allone.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

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
             MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.tap_long);
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
            MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.possible_card_tap);
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
            MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.maybe_match);
            mediaPlayer.start();
        }
    }

    /**
     * Plays a sound when the two cards selsected are not a match based on if the shared preference
     * to mute noise is not true.
     * @param context
     */
    void startNonMatchNoise(Context context){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        if (!sp.getBoolean(PreferenceKeys.MEMORY_SOUND_CHECKED, false)) {
            MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.card_fail);
            mediaPlayer.start();
        }
    }

    /**
     * Plays a winning soundtrack based on if the shared preference
     * to mute noise is not true.
     * @param context
     */
    void startWinningNoise(Context context){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        if (!sp.getBoolean(PreferenceKeys.MEMORY_SOUND_CHECKED, false)) {
            MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.win);
            mediaPlayer.start();
            MediaPlayer mediaPlayer2 = MediaPlayer.create(context, R.raw.cheer);
            mediaPlayer2.start();
        }
    }

    /**
     * Starts the background music on a loop based on if the shared preference to mute music is not true.
     * @param context
     */
    void startMusic(Context context){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);

        if (!sp.getBoolean(PreferenceKeys.MEMORY_MUSIC_CHECKED, false)) {
            shuffelMusic(context);
        }
    }

    void shuffelMusic(Context context){
        final Context c = context;

        SparseIntArray songs = new SparseIntArray();
        songs.append(0, R.raw.bensound_memories);
        songs.append(1, R.raw.bensound_straight);
        songs.append(2, R.raw.bensound_anewbeginning);
        songs.append(3, R.raw.bensound_creativeminds);
        songs.append(4, R.raw.bensound_cute);
        songs.append(5, R.raw.bensound_goinghigher);
        songs.append(6, R.raw.bensound_jazzyfrenchy);
        songs.append(7, R.raw.bensound_summer);
        songs.append(8, R.raw.bensound_ukulele);
        Random random = new Random();

        music = MediaPlayer.create(context, songs.get(random.nextInt(songs.size())));
        music.start();
        music.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                shuffelMusic(c);
            }
        });

    }

    /**
     * Stops the music.
     */
    void stopMusic() {
        if (music != null) {
            music.stop();
        }
    }

    /**
     * Pauses the music.
     */
    void pauseMusic(){
        if (music != null) {
            music.pause();
        }
    }

    /**
     * Resumes the music.
     */
    void resumeMusic(Context context){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);

        if (!sp.getBoolean(PreferenceKeys.MEMORY_MUSIC_CHECKED, false) || music != null) {
            music.start();
        }

    }
}
