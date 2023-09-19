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

        /* Hitta gränssnittselement med hjälp av deras ID */
        /* HangmanImageView = findViewById(R.id.hangmanImageView); */
        guessEditText = findViewById(R.id.guessEditText);
        newGameButton = findViewById(R.id.newGameButton);
        guessButton = findViewById(R.id.guessButton);
        statusTextView = findViewById(R.id.statusTextView);
        guessedTextView = findViewById(R.id.guessedTextView);
        guessesTextView = findViewById(R.id.guessesTextView);

        newGameButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 //nollställer försök & felgissnings-fälten
                 guessesTextView.setText("Försök kvar: 7");
                 guessedTextView.setText("Fel gissningar: ");

                 wordManager = new WordManager();

                 String randomWord = wordManager.getRandomWord();

                 Log.d("Ord", "Slumpat ord: " + randomWord);

                 hangmanGame = new HangmanGame(randomWord, 7);

                 //uppdaterar GUI med ordet
                 statusTextView.setText(hangmanGame.getCurrentStatus());

             }
         });

        guessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String guessedLetter = guessEditText.getText().toString();

                // Kontrollera om gissningen är en giltig bokstav
                if (guessedLetter.length() == 1 && guessedLetter.matches("[a-zA-Z]")) {
                    //lagrar gissningen i en char
                    char letter = guessedLetter.toLowerCase().charAt(0);

                    boolean isCorrectGuess = hangmanGame.guessLetter(letter);

                    // hanterar resultat av gissningen
                    if (isCorrectGuess) {

                        statusTextView.setText(hangmanGame.getCurrentStatus());

                        if(hangmanGame.isGameWon()) {
                            Toast.makeText(getApplicationContext(), "You won!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        //uppdatera grafiska gränssnittet med ett sträck, el ImageHangManView
                        guessesTextView.setText("Försök kvar: " + hangmanGame.getWrongGuesses());
                        guessedTextView.append(" " + letter);

                        if(hangmanGame.isGameLost()){
                            //uppdatera GUI om förlust!
                            Toast.makeText(getApplicationContext(), "YOU LOST!!!", Toast.LENGTH_SHORT).show();
                        }

                    }

                } else {
                    //om användaren matat in ett ogiltigt värde i gissnings-fältet
                    Toast.makeText(getApplicationContext(), "Ogiltig gissning, försök igen!", Toast.LENGTH_SHORT).show();
                }
                // Clear the EditText after processing the guess
                guessEditText.setText("");
            }
        });
    }
}