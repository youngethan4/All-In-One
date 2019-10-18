package com.example.ethanman04.memory;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.preference.PreferenceManager;
import android.util.SparseIntArray;
import com.example.ethanman04.allone.PreferenceKeys;
import com.example.ethanman04.allone.R;
import java.util.Random;

public class SetSound {

    //Makes an instance of SetSound that can always be accessed.
    private static SetSound instance;
    public static synchronized SetSound getInstance(){
        if (instance == null){
            instance = new SetSound();
        }
        return  instance;
    }

    private MediaPlayer music;

    /**
     * Plays a tapping sound based on if the shared preference to mute noise is not true.
     * @param context
     */
     public void startButtonNoise(Context context){
         SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
         if (!sp.getBoolean(PreferenceKeys.MEMORY_SOUND_CHECKED, false)) {
             final MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.tap_long);
             mediaPlayer.start();
             mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                 @Override
                 public void onCompletion(MediaPlayer mp) {
                     mediaPlayer.release();
                 }
             });
         }
    }

    /**
     * Plays a card sound based on if the shared preference to mute noise is not true.
     * @param context
     */
    public void startCardNoise(Context context){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        if (!sp.getBoolean(PreferenceKeys.MEMORY_SOUND_CHECKED, false)) {
            final MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.possible_card_tap);
            mediaPlayer.start();
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mediaPlayer.release();
                }
            });
        }
    }

    /**
     * Plays a card match sound based on if the shared preference to mute noise is not true.
     * @param context
     */
    public void startMatchNoise(Context context){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        if (!sp.getBoolean(PreferenceKeys.MEMORY_SOUND_CHECKED, false)) {
            final MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.maybe_match);
            mediaPlayer.start();
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mediaPlayer.release();
                }
            });
        }
    }

    /**
     * Plays a sound when the two cards selsected are not a match based on if the shared preference
     * to mute noise is not true.
     * @param context
     */
    public void startNonMatchNoise(Context context){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        if (!sp.getBoolean(PreferenceKeys.MEMORY_SOUND_CHECKED, false)) {
            final MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.card_fail);
            mediaPlayer.start();
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mediaPlayer.release();
                }
            });
        }
    }

    /**
     * Plays a winning soundtrack based on if the shared preference
     * to mute noise is not true.
     * @param context
     */
     void startWinningNoise(Context context){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        if (!sp.getBoolean(PreferenceKeys.MEMORY_MUSIC_CHECKED, false)) {
            music.pause();
            final MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.win);
            mediaPlayer.start();
            final MediaPlayer mediaPlayer2 = MediaPlayer.create(context, R.raw.cheer);
            mediaPlayer2.start();
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mediaPlayer.release();
                }
            });
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mediaPlayer2.release();
                }
            });
        }
    }

    /**
     * Starts the background music on a loop based on if the shared preference to mute music is not true.
     * @param context
     */
    void startMusic(Context context){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);

        if (!sp.getBoolean(PreferenceKeys.MEMORY_MUSIC_CHECKED, false)) {
            if (music == null) {
                shuffelMusic(context);
            }
            else {
                resumeMusic(context);
            }
        }
    }

    /**
     * Adds all the songs to a sparseintarray and then selects one to play randomly.
     * Once the song is completed, the method recursively calls itself.
     * @param context
     */
    private void shuffelMusic(Context context){
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
                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(c);

                if (!sp.getBoolean(PreferenceKeys.MEMORY_MUSIC_CHECKED, false)) {
                    shuffelMusic(c);
                }
            }
        });
    }

    /**
     * Stops the music.
     */
    void stopMusic() {
        if (music != null) {
            music.stop();
            music.release();
            music = null;
        }
    }

    /**
     * Pauses the music.
     */
    public void pauseMusic(){
        if (music != null) {
            music.pause();
        }
    }

    /**
     * Resumes the music.
     */
    public void resumeMusic(Context context){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);

        if (!sp.getBoolean(PreferenceKeys.MEMORY_MUSIC_CHECKED, false) && music != null) {
            music.start();
        }

    }
}
