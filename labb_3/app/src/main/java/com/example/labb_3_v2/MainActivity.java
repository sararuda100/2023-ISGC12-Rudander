package com.example.labb_3_v2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private EditText search;
    private TextView list;
    private Button btn;
    private String input;

    private FetchArtists fetchArtists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //initializing ett nytt objekt
        fetchArtists = new FetchArtists();

        //hämtar referenser till GUI elementen och lagrar i variabler
        list = findViewById(R.id.listContent);
        btn = findViewById(R.id.searchButton);
        search = findViewById(R.id.searchField);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //hämtar värdet i sökfältet
                input = search.getText().toString();

                //rensar sökfältet efter man klickat
                search.setText("");

                //callar funktionen som hanterar sökningen
                fetchArtists.fetchData(input);

                //visar listan
                list.setText(fetchArtists.getList());
            }
        });
    }
}