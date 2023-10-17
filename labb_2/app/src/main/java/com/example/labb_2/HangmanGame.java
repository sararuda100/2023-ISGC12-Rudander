package com.example.labb_2;

import android.util.Log;

public class HangmanGame {
    private String secretWord, currentStatus;
    private int maxAttempts, wrongGuesses;

    public HangmanGame(String word, int maxAttempts) {
        this.secretWord = word.toLowerCase();
        this.maxAttempts = maxAttempts;
        this.currentStatus = generateInitialStatus();
        this.wrongGuesses = 0;
    }

    /* Funktion för att generera den initiala statusen, dvs längden av det hemliga ordet */
    private String generateInitialStatus() {
        StringBuilder initialStatus = new StringBuilder();
        for (int i = 0; i < secretWord.length(); i++) {
            initialStatus.append('X');
        }
        return initialStatus.toString();
    }

    /* Funktion för att hantera gissning och match med ordet */
    public boolean guessLetter(char letter) {
        boolean isCorrectGuess = false;

        //Kontrollerar om bokstaven finns i ordet
        for (int i = 0; i < secretWord.length(); i++) {
            if (secretWord.charAt(i) == letter) {
                // Uppdatera statusen med den rätta bokstaven
                currentStatus = currentStatus.substring(0, i) + letter + currentStatus.substring(i + 1);
                isCorrectGuess = true;
            }
        }

        // Om gissningen var felaktig, ökar antalet felaktiga gissningar
        if (!isCorrectGuess) {
            wrongGuesses++;
        }

        return isCorrectGuess;
    }

    /*funk för att hämta antal kvarstående gissningar*/
    public int getWrongGuesses(){
        return maxAttempts - wrongGuesses;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public boolean isGameWon() {
        return !currentStatus.contains("X");
    }

    public boolean isGameLost() {
        if(wrongGuesses >= maxAttempts){
            return true;
        }
        return false;
    }
}
