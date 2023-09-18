package com.example.labb_2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    /*public ImageView hangmanImageView;*/
    private EditText guessEditText;
    private Button newGameButton;
    private Button guessButton;
    private TextView statusTextView;

    private TextView guessedTextView;

    private TextView guessesTextView;
    private WordManager wordManager;
    private HangmanGame hangmanGame;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Hitta gränssnittselement med hjälp av deras ID*/
        /*hangmanImageView = findViewById(R.id.hangmanImageView);*/
        guessEditText = findViewById(R.id.guessEditText);
        newGameButton = findViewById(R.id.newGameButton);
        guessButton = findViewById(R.id.guessButton);
        statusTextView = findViewById(R.id.statusTextView);
        guessedTextView = findViewById(R.id.guessedTextView);
        guessesTextView = findViewById(R.id.guessesTextView);

        newGameButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 //logic for new game
                 /*initialize wordManager*/
                 wordManager = new WordManager();

                 String randomWord = wordManager.getRandomWord();
                 Log.d("Ord", "Slumpat ord: " + randomWord);
                 //skickar med slumpade ordet som parameter till HangmanGame
                 hangmanGame = new HangmanGame(randomWord, 7);

                 //loggar currentStatus direkt efter att det har tilldelats
                 Log.d("CurrentStatus", "Nuvarande status: " + hangmanGame.getCurrentStatus());
                 //detta vill vi nu istället uppdatera GUI med...
                 statusTextView.setText(hangmanGame.getCurrentStatus());

             }
         });

        // Lägger till händelselyssnare och hantera interaktion här
        guessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Hämta användarens gissning från EditText
                String guessedLetter = guessEditText.getText().toString();

                // Kontrollera om gissningen är en giltig bokstav
                if (guessedLetter.length() == 1 && guessedLetter.matches("[a-zA-Z]")) {
                    Log.d("Gissning", "Giltig gissning: " + guessedLetter);
                    //hantera gissningen, kanske visa upp bokstaven som gissats
                    //hur många försök som återstår

                    //omvandlar och lagrar gissningen i en char
                    char letter = guessedLetter.toLowerCase().charAt(0);

                    //nu kan vi calla function med letter som parameter och lagra i variabel
                    boolean isCorrectGuess = hangmanGame.guessLetter(letter);

                    // Handle the result of the guess (update UI, check win/lose, etc.)
                    if (isCorrectGuess) {
                        // Handle correct guess
                        //uppdatera grafiska gränssnittet med ett sträck, el ImageHangManView
                        //dvs uppdatera gränssnitt och visa currentStatus igen?
                        Log.d("Gissning", guessedLetter + " är rätt!");
                        statusTextView.setText(hangmanGame.getCurrentStatus());
                        if(hangmanGame.isGameWon()){
                            Log.d("WIN", "You won!");
                            //uppdatera GUI om vinst!
                            Toast.makeText(getApplicationContext(), "You won!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // Handle incorrect guess
                        Log.d("Gissning", "inkorrekt gissning");
                        //UPPDATERA GUI om antal fel-gissningar
                        guessesTextView.setText("Försök kvar: " + hangmanGame.getWrongGuesses());
                        guessedTextView.append(" " + letter);
                        if(hangmanGame.isGameLost()){
                            Log.d("LOOSE", "You lost");
                            //uppdatera GUI om förlust!
                            Toast.makeText(getApplicationContext(), "You've been hung", Toast.LENGTH_SHORT).show();
                        }

                    }

                } else {
                    //Användaren har inte matat in en giltig bokstav, visa ett meddelande eller be om en ny gissning
                    //loggar felaktig gissning
                    Log.d("Gissning", "Ogiltig gissning: " + guessedLetter);
                    //visar ett toast-meddelande
                    Toast.makeText(getApplicationContext(), "Ogiltig gissning, försök igen!", Toast.LENGTH_SHORT).show();
                }
                // Clear the EditText after processing the guess
                guessEditText.setText("");
            }
        });
    }
}