package com.example.labb4_v4;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.List;
import androidx.appcompat.app.AppCompatActivity;

public class ShoppingCartActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Sätter layouten för aktiviteten till activity_shopping_cart.xml
        setContentView(R.layout.activity_shopping_cart);

        //Laddar kundvagnens produkter från filen vi skapat i FileManager klassen
        List<Product> cartItems = FileManager.loadShoppingCart(this);

        Log.d("cartItems", cartItems.toString());
        //Skapar en ArrayAdapter för att visa kundvagnens produkter i en ListView.
        ArrayAdapter<Product> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, cartItems
        );

        //Hittar ListView med id cartListView i layouten.
        ListView cartListView = findViewById(R.id.cartListView);

        // Kopplar ArrayAdaptern till ListView för att visa produkterna.
        cartListView.setAdapter(adapter);
    }
}
