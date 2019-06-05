package com.example.ethanman04.memory;

import android.content.Context;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.ethanman04.allone.R;

import java.util.ArrayList;

public class MemoryGridActivity extends AppCompatActivity {

    static GridView gv;
    static MyAdapter adapter;
    static int taps;
    ImageButton cardFlip1;
    ImageButton cardFlip2;
    TextView strCard1;
    int points;
    private ArrayList<Character> cards;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory_grid);
        setCards();
    }

    private void setCards() {
        gv = findViewById(R.id.memory_grid_view);
        cards = new ArrayList<>();
        SetCards setCards = new SetCards(getIntent().getExtras().getInt("size"));
        cards = setCards.getCards();
        System.out.println(cards.size());
        adapter = new MyAdapter(cards, this);
        gv.setAdapter(adapter);
    }


    public class MyAdapter extends BaseAdapter {
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
            return R.layout.layout_card;
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
                convertView = layoutInflater.inflate(R.layout.layout_card, null);

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

    public class ViewHolder {
        ImageButton ib;
        TextView tv;

        public ViewHolder(ImageButton b, TextView v) {
            ib = b;
            tv = v;

            ib.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (taps < 2) {
                        ib.setVisibility(View.INVISIBLE);
                        setTaps(ib, tv);
                    }
                }
            });
        }
    }

    private void setTaps(ImageButton card, TextView chr) {
        if (taps == 0) {
            cardFlip1 = card;
            strCard1 = chr;
            ++taps;
        } else {
            ++taps;
            cardFlip2 = card;
            if (strCard1.getText().toString().trim().equals(chr.getText().toString().trim())) {
                points++;
                strCard1.setVisibility(View.INVISIBLE);
                chr.setVisibility(View.INVISIBLE);
                //TODO:Winning screen
                taps = 0;
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

    private void flipBack() {
        cardFlip1.setVisibility(View.VISIBLE);
        cardFlip2.setVisibility(View.VISIBLE);
        taps = 0;
    }
}