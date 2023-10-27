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
            setContentView(R.layout.activity_shopping_cart);

            // Hämtar shoppingcarten från den lokala filen och sparar i en variabel
            List<Product> cartItems = FileManager.loadShoppingCart(this);

            // Loggar cart items för att verifiera data
            for (Product product : cartItems) {
                Log.d("ShoppingCartActivity", "Loaded from file: " + product.getName());
            }

            //skapar en adapter för att visa cartitems i en listview
            ArrayAdapter<Product> adapter = new ArrayAdapter<>(
                    this, android.R.layout.simple_list_item_1, cartItems
            );

            //hämtar referens till till listviewn i layouten och sätter adaptern till den
            ListView cartListView = findViewById(R.id.cartListView);
            cartListView.setAdapter(adapter);
        }
    }


