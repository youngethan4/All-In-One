package com.example.ethanman04.memory;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.ethanman04.allone.PreferenceKeys;
import com.example.ethanman04.allone.R;

import java.util.ArrayList;
import java.util.Locale;

public class MemoryGridActivity extends AppCompatActivity {

    private GridView gv;
    private MyAdapter adapter;
    private static int taps;
    private ImageButton cardFlip1;
    private ImageButton cardFlip2;
    private TextView strCard1;
    private TextView strCard2;
    private int numCards;
    private int points;
    private long millisecondTime;
    private long startTime;
    private boolean won;
    private ArrayList<Character> cards;
    private boolean is30;
    SharedPreferences sp;
    private SetSound setSound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory_grid);

        setSound = SetSound.getInstance();
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        setCards();
        startTimer();
    }

    /**
     * This is a setup method. It initializes points to 0, sets the gridview, sets the adapter,
     * and gets the cards based on the size that is brought through the intent.
     */
    private void setCards() {
        points = 0;
        numCards = getIntent().getExtras().getInt("size");

        //Used later when getting the high score
        if (numCards == 30) is30 = true;
        else is30 = false;

        gv = findViewById(R.id.memory_grid_view);
        cards = new ArrayList<>();
        SetCards setCards = new SetCards(numCards);
        cards = setCards.getCards();
        adapter = new MyAdapter(cards, this);
        gv.setAdapter(adapter);
    }

    /**
     * The adaper for the grid view being used.
     */
    private class MyAdapter extends BaseAdapter {
        ArrayList<Character> cd;
        Context con;

        MyAdapter(ArrayList data, Context context) {
            cd = data;
            con = context;
        }

        @Override
        public int getCount()
        {
            return cd.size();
        }

        @Override
        public int getItemViewType(int position)
        {
            return R.layout.layout_memory_card;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                final LayoutInflater layoutInflater = LayoutInflater.from(con);
                convertView = layoutInflater.inflate(R.layout.layout_memory_card, null);

                final ImageButton ib = convertView.findViewById(R.id.memory_card);
                final TextView tv = convertView.findViewById(R.id.memory_card_text);

                final ViewHolder viewHolder = new ViewHolder(ib, tv);
                convertView.setTag(viewHolder);

            }

            final ViewHolder viewHolder = (ViewHolder)convertView.getTag();
            viewHolder.tv.setText(cd.get(position).toString());

            return convertView;
        }
    }

    /**
     * The view holder for each card.
     */
    private class ViewHolder {
        ImageButton ib;
        TextView tv;

        private ViewHolder(ImageButton b, TextView v) {
            ib = b;
            tv = v;

            //Sets the card image
            int drawable = sp.getInt(PreferenceKeys.MEMORY_THEME, R.drawable.card_blue);
            ib.setImageDrawable(getResources().getDrawable(drawable));
            ib.setMaxWidth(230);

            //Only continues to the setTaps method if the user has tapped on 2 cards or less.
            //These two that the user tapped on must flip back over or disappear before the user can
            //select another two cards.
            ib.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setSound.startCardNoise(MemoryGridActivity.this);
                    if (taps < 2) {
                        ib.setVisibility(View.INVISIBLE);
                        setTaps(ib, tv);
                    }
                }
            });
        }
    }

    /**
     * If taps = 0, it will set the current card_teal ands its char value to global vars.
     * If taps > 0, then the game will see if the two chars match. If they do, it starts a delayed
     * thread that will make the chars invisible. The game will also see if this was the winning match.
     * If the two chars do not match, then another delayed thread will start to flip the cards back over.
     * @param card
     * @param chr
     */
    private void setTaps(ImageButton card, TextView chr) {
        if (taps == 0) {
            cardFlip1 = card;
            strCard1 = chr;
            ++taps;
        } else {
            ++taps;
            cardFlip2 = card;
            strCard2 = chr;
            if (strCard1.getText().toString().trim().equals(chr.getText().toString().trim())) {
                points += taps;
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        flipover();
                        if (points >= numCards) {
                            setSound.pauseMusic();
                            won = true;
                            showWinningScreen();
                            Handler h = new Handler();
                            h.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    setSound.startWinningNoise(MemoryGridActivity.this);
                                }
                            }, 50);
                        }
                    }
                }, 500);   //.5 seconds
            } else {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        flipBack();
                    }
                }, 500);   //.5 seconds
            }
        }
    }

    /**
     * Sets both cards to invisible and resets taps to 0.
     */
    private void flipover(){
        setSound.startMatchNoise(MemoryGridActivity.this);
        strCard1.setVisibility(View.INVISIBLE);
        strCard2.setVisibility(View.INVISIBLE);
        taps = 0;
    }

    /**
     * Sets both cards to invisible and resets the taps to 0.
     */
    private void flipBack() {
        setSound.startNonMatchNoise(MemoryGridActivity.this);
        cardFlip1.setVisibility(View.VISIBLE);
        cardFlip2.setVisibility(View.VISIBLE);
        taps=0;
    }

    @Override
    public void onBackPressed(){
        setSound.startButtonNoise(MemoryGridActivity.this);
        showAlertBackPress();
    }

    /**
     * Shows an alert dialog making sure that the user would like to go back.
     */
    private void showAlertBackPress(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("Exit");
        builder.setMessage("Are you sure you want to exit?");

        builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                setSound.startButtonNoise(MemoryGridActivity.this);
                finish();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                setSound.startButtonNoise(MemoryGridActivity.this);
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(getResources().getColor(sp.getInt(PreferenceKeys.MEMORY_THEME_COLOR, R.color.blue)));
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(getResources().getColor(sp.getInt(PreferenceKeys.MEMORY_THEME_COLOR, R.color.blue)));
    }

    /**
     * Shows an alert dialog box to see if the user would like to play again or return to the app list.
     * This method will also start playing winning music.
     */
    private void showWinningScreen(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);

        //Using shared preferences for the high score. Need two based on if it is 30 or 20 cards.
        long highScore;
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        if (is30) {
            highScore = sp.getLong(PreferenceKeys.MEMORY_HIGH_SCORE_30, 0);
        }
        else {
            highScore = sp.getLong(PreferenceKeys.MEMORY_HIGH_SCORE_20, 0);
        }

        millisecondTime = SystemClock.uptimeMillis() - startTime;
        if (millisecondTime < highScore || highScore == 0.0) {
            builder.setTitle("New High Score!!!");
            SharedPreferences.Editor editor = sp.edit();
            if (is30) {
                editor.putLong(PreferenceKeys.MEMORY_HIGH_SCORE_30, millisecondTime);
            }
            else {
                editor.putLong(PreferenceKeys.MEMORY_HIGH_SCORE_20, millisecondTime);
            }
            editor.apply();
        }
        else {
            String textTimer = millisToString(millisecondTime);
            String score = "Your Time: " + textTimer;
            builder.setTitle(score);
        }
        if (is30) {
            highScore = sp.getLong(PreferenceKeys.MEMORY_HIGH_SCORE_30, 0);
        }
        else {
            highScore = sp.getLong(PreferenceKeys.MEMORY_HIGH_SCORE_20, 0);
        }
        String highScoreTime = millisToString(highScore);
        String message = "Your high score is: " + highScoreTime + "\nWould you like to play again?";
        builder.setMessage(message);

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                setSound.startButtonNoise(MemoryGridActivity.this);
                finish();
                startActivity(getIntent());
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                setSound.startButtonNoise(MemoryGridActivity.this);
                Intent intent = new Intent(MemoryGridActivity.this, MemoryActivity.class);
                startActivity(intent);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(getResources().getColor(sp.getInt(PreferenceKeys.MEMORY_THEME_COLOR, R.color.blue)));
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(getResources().getColor(sp.getInt(PreferenceKeys.MEMORY_THEME_COLOR, R.color.blue)));

    }

    /**
     * Starts the timer using the system clock to record how long the game takes.
     */
    private void startTimer(){
        won = false;
        startTime = SystemClock.uptimeMillis();
        final TextView textViewTimer = findViewById(R.id.memory_timer);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        textViewTimer.setTextColor(getResources().getColor(sp.getInt(PreferenceKeys.MEMORY_THEME_COLOR, R.color.blue)));
        textViewTimer.setBackground(getResources().getDrawable(sp.getInt(PreferenceKeys.MEMORY_THEME_BOARDER, R.drawable.memory_boarder_blue)));

        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                millisecondTime = SystemClock.uptimeMillis() - startTime;
                String setTextTimer = millisToString(millisecondTime);
                textViewTimer.setText(setTextTimer);
                if (!won) handler.post(this);
            }
        });
    }

    /**
     * Helper method to convert millis to a string value
     * @param millis
     * @return users time in string format
     */
    private String millisToString(long millis) {
        int seconds = (int) (millis / 1000);
        int minutes = seconds / 60;
        seconds = seconds % 60;
        return "" + minutes + ":" + String.format(Locale.ENGLISH,"%02d", seconds);
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