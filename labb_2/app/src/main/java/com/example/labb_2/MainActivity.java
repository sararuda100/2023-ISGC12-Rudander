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
    private Button newGameButton, guessButton;
    private TextView statusTextView, guessedTextView, guessesTextView;
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

        /* Skapar nytt hangMan-objekt, slumpar ett ord, uppdaterar GUI med slumpat ord*/
        wordManager = new WordManager();
        String randomWord = wordManager.getRandomWord();
        hangmanGame = new HangmanGame(randomWord, 7);
        statusTextView.setText(hangmanGame.getCurrentStatus());

        /* knapplyssnare för att starta nytt spel */
        newGameButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 /*Återställer försök & felgissnings-fälten*/
                 guessesTextView.setText("Försök kvar: ");
                 guessedTextView.setText("Fel gissningar: ");

                 wordManager = new WordManager();
                 String randomWord = wordManager.getRandomWord();
                 hangmanGame = new HangmanGame(randomWord, 7);
                 statusTextView.setText(hangmanGame.getCurrentStatus());

             }
         });
        /* Knapplyssnare för gissa-knappen */
        guessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String guessedLetter = guessEditText.getText().toString();

                //Kontroll om gissningen är av giltigt värde
                if (guessedLetter.length() == 1 && guessedLetter.matches("[a-zA-Z]")) {

                    char letter = guessedLetter.toLowerCase().charAt(0);

                    boolean isCorrectGuess = hangmanGame.guessLetter(letter);

                    // hanterar resultat av gissningen
                    if (isCorrectGuess) {
                        //uppdaterar status-textView med aktuell status
                        statusTextView.setText(hangmanGame.getCurrentStatus());

                        if(hangmanGame.isGameWon()) {
                            Toast.makeText(getApplicationContext(), "You won!", Toast.LENGTH_SHORT).show();

                            /*slumpar ett nytt ord när spelaren vunnit*/
                            wordManager = new WordManager();
                            String randomWord = wordManager.getRandomWord();
                            hangmanGame = new HangmanGame(randomWord, 7);
                            statusTextView.setText(hangmanGame.getCurrentStatus());

                            /* Återställer försök & felgissnings-fälten */
                            guessesTextView.setText("Försök kvar: ");
                            guessedTextView.setText("Fel gissningar: ");

                        }
                    } else {
                        /* uppdaterar antal försök och fel-gissningar */
                        guessesTextView.setText("Försök kvar: " + hangmanGame.getWrongGuesses());
                        guessedTextView.append(" " + letter);

                        if(hangmanGame.isGameLost()){
                            Toast.makeText(getApplicationContext(), "YOU LOST", Toast.LENGTH_SHORT).show();

                            /*slumpar ett nytt ord när spelaren förlorat*/
                            wordManager = new WordManager();
                            String randomWord = wordManager.getRandomWord();
                            hangmanGame = new HangmanGame(randomWord, 7);
                            statusTextView.setText(hangmanGame.getCurrentStatus());

                            /* Återställer försök & felgissnings-fält */
                            guessesTextView.setText("Försök kvar: ");
                            guessedTextView.setText("Fel gissningar: ");

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