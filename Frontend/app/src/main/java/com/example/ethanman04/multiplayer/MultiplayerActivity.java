package com.example.ethanman04.multiplayer;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.example.ethanman04.allone.PreferenceKeys;
import com.example.ethanman04.allone.R;
import com.example.ethanman04.memory.SetSound;
import com.google.android.material.tabs.TabLayout;

public class MultiplayerActivity extends AppCompatActivity implements GameFragment.OnFragmentInteractionListener, ChatFragment.OnFragmentInteractionListener {

    SetSound setSound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiplayer);
        setSound = SetSound.getInstance();
        setupTabs();
    }

    private void setupTabs() {
        MultiPagerAdapter multiPagerAdapter;
        ViewPager viewPager = findViewById(R.id.multi_view_pager);
        multiPagerAdapter = new MultiPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(multiPagerAdapter);
       //TabLayout tabLayout = findViewById(R.id.multi_tab_layout);
        //tabLayout.setupWithViewPager(viewPager);


    }

    @Override
    public void onBackPressed(){
        setSound.startButtonNoise(MultiplayerActivity.this);
        showAlertBackPress();
    }

    /**
     * Shows an alert dialog making sure that the user would like to go back.
     */
    private void showAlertBackPress(){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("Exit");
        builder.setMessage("Are you sure you want to exit?");

        builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                setSound.startButtonNoise(MultiplayerActivity.this);
                finish();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                setSound.startButtonNoise(MultiplayerActivity.this);
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(getResources().getColor(sp.getInt(PreferenceKeys.MEMORY_THEME_COLOR, R.color.blue)));
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(getResources().getColor(sp.getInt(PreferenceKeys.MEMORY_THEME_COLOR, R.color.blue)));
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

    @Override
    public void onFragmentInteraction(Uri uri) {
    }
}
