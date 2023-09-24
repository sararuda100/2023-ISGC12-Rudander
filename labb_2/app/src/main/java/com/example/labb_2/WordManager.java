package com.example.labb_2;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class WordManager {
        private List<String> wordList;

        public WordManager() {
            //skapar en tom lista när en instans av wordManager skapas
            wordList = new ArrayList<>();
            //lägger till några ord i listan
            wordList.add("apple");
            wordList.add("banana");
            wordList.add("yxa");
            wordList.add("sax");
            wordList.add("pirate");

        }

        /* Metod som slumpar ett ord från listan och returnerar det */
        public String getRandomWord() {
            // Slumpar ett ord från listan och returnera det
            Random random = new Random();
            //genererar en slumpmässig index-position i listan
            int randomIndex = random.nextInt(wordList.size());
            //returnerar det slumpade ordet
            return wordList.get(randomIndex);
        }
    }
