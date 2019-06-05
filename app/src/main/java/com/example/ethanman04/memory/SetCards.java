package com.example.ethanman04.memory;

import java.util.ArrayList;
import java.util.Collections;

public class SetCards {
    private int numCards;

    public SetCards(int n) {
        numCards = n;
    }

    public SetCards() {
        numCards = 0;
    }

    public ArrayList getCards() {
        ArrayList<Character> arrList = new ArrayList<>();
        for (int i = 0; i < numCards/2; i++) {
            arrList.add((char) (65+i));
            arrList.add((char) (65+i));
        }
        Collections.shuffle(arrList);
        return arrList;
    }
}