package com.example.labb_2;

import android.util.Log;

public class HangmanGame {
    /*This is where the game is being managed
    * här håller vi koll på det slumpade ordet,
    * den aktuella statusen av ordet (både med korrekta
    * och fel gissningar
    *
    * antalet felaktiga gissningar
    * samt om spelet är vunnet eller förlorat
    *
    * håller koll på nuvarande spelets tillstånd
    * (det hemliga order, rätt och fel gissningar)*/
    private String secretWord;
    private String currentStatus;
    private int maxAttempts;
    private int wrongGuesses;

    public HangmanGame(String word, int maxAttempts) {
        this.secretWord = word.toLowerCase(); // Slumpat ord (omvandlat till gemener)
        this.maxAttempts = maxAttempts;
        this.currentStatus = generateInitialStatus();
        this.wrongGuesses = 0;
    }

    // Funktion för att generera den initiala statusen baserat på längden av det hemliga ordet
    private String generateInitialStatus() {
        StringBuilder initialStatus = new StringBuilder();
        for (int i = 0; i < secretWord.length(); i++) {
            initialStatus.append('O'); // Understrykningstecken för varje bokstav i ordet
        }
        return initialStatus.toString();
    }

    // Funktion för att gissa en bokstav
    public boolean guessLetter(char letter) {
        //nedan rad behövs kanske ej, då jag redan gjorde det till lowerCase i main???
        letter = Character.toLowerCase(letter); // Konvertera gissningen till gemener
        boolean isCorrectGuess = false;

        // Kontrollera om gissningen finns i det hemliga ordet
        for (int i = 0; i < secretWord.length(); i++) {
            if (secretWord.charAt(i) == letter) {
                // Uppdatera den aktuella statusen med den rätta gissningen
                currentStatus = currentStatus.substring(0, i) + letter + currentStatus.substring(i + 1);
                isCorrectGuess = true;
            }
        }

        // Om gissningen var felaktig, öka antalet felaktiga gissningar
        if (!isCorrectGuess) {
            wrongGuesses++;
            Log.d("AmountGuess", "" + wrongGuesses);
        }

        return isCorrectGuess;
    }

    //funk för att hämta fel antal gissningar
    public int getWrongGuesses(){
        return maxAttempts - wrongGuesses;
    }

    // Funktion för att hämta den aktuella statusen av ordet (med rätt och fel gissningar)
    public String getCurrentStatus() {
        return currentStatus;
    }

    // Funktion för att kontrollera om spelet är vunnet
    public boolean isGameWon() {
        //Log.d("Value of boolean", "" + !currentStatus.contains("_"));
        Log.d("current", "" + currentStatus);
        return !currentStatus.contains("O");
    }

    // Funktion för att kontrollera om spelet är förlorat
    public boolean isGameLost() {
        if(wrongGuesses >= maxAttempts){
            return true;
        }
        return false;
    }
}
