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

    /*deklaration av UI-element;*/
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

        /* Hittar gränssnittselement med hjälp av deras ID */
        guessEditText = findViewById(R.id.guessEditText);
        newGameButton = findViewById(R.id.newGameButton);
        guessButton = findViewById(R.id.guessButton);
        statusTextView = findViewById(R.id.statusTextView);
        guessedTextView = findViewById(R.id.guessedTextView);
        guessesTextView = findViewById(R.id.guessesTextView);

        /* knapp för att starta nytt spel */
        newGameButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 // Återställer försök & felgissnings-fälten
                 guessesTextView.setText("Försök kvar: 7");
                 guessedTextView.setText("Fel gissningar: ");

                 // Skapar nytt hangMan-objekt
                 wordManager = new WordManager();

                 String randomWord = wordManager.getRandomWord();

                 hangmanGame = new HangmanGame(randomWord, 7);

                 // Uppdaterar GUI med slumpade ordet
                 statusTextView.setText(hangmanGame.getCurrentStatus());

             }
         });
        /* Knapp för att göra en gissning */
        guessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String guessedLetter = guessEditText.getText().toString();

                // Kontrollerar om gissningen är av giltigt värde
                if (guessedLetter.length() == 1 && guessedLetter.matches("[a-zA-Z]")) {

                    char letter = guessedLetter.toLowerCase().charAt(0);

                    boolean isCorrectGuess = hangmanGame.guessLetter(letter);

                    // hanterar resultat av gissningen
                    if (isCorrectGuess) {
                        //uppdaterar status-textView med aktuell status
                        statusTextView.setText(hangmanGame.getCurrentStatus());

                        if(hangmanGame.isGameWon()) {
                            Toast.makeText(getApplicationContext(), "You won!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        //uppdaterar antal försök och fel-gissningar
                        guessesTextView.setText("Försök kvar: " + hangmanGame.getWrongGuesses());
                        guessedTextView.append(" " + letter);

                        if(hangmanGame.isGameLost()){
                            Toast.makeText(getApplicationContext(), "YOU LOST", Toast.LENGTH_SHORT).show();
                        }

                    }

                } else {
                    //om användaren matat in ett ogiltigt värde i gissnings-fältet
                    Toast.makeText(getApplicationContext(), "Ogiltig gissning, försök igen!", Toast.LENGTH_SHORT).show();
                }
                // rensar editText efter gissningen har bearbetats
                guessEditText.setText("");
            }
        });
    }
}