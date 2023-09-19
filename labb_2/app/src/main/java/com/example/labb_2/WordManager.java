package com.example.labb_2;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class WordManager {
    /*
    * kan erhålla en lista av olika ord
    * och välja ett slumpmässigt ord när
    * en ny spelomgång startar*/
        private List<String> wordList;

        public WordManager() {
            // Här initialiseras ordlistan (t.ex. från en resursfil eller en API).
            wordList = new ArrayList<>();
            wordList.add("apple");
            wordList.add("banana");
            wordList.add("yxa");
            wordList.add("sax");
            wordList.add("pirate");

        }

        public String getRandomWord() {
            // Slumpa ett ord från listan och returnera det
            Random random = new Random();
            int randomIndex = random.nextInt(wordList.size());
            return wordList.get(randomIndex);
        }
    }
