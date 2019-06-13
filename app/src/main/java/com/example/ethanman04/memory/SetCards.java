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

    /**
     * Generates cards based on the number needed (30 or 20) then uses collections to shuffle them
     * @return list of cards
     */
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