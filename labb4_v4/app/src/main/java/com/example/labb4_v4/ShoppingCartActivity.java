package com.example.labb4_v4;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ShoppingCartActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);

        ShoppingCart shoppingCart = ShoppingCart.getInstance(this);
        ArrayAdapter<Product> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1,
                shoppingCart.getCartItems());

                FileManager.loadShoppingCart(this);


        ListView cartListView = findViewById(R.id.cartListView);
        cartListView.setAdapter(adapter);

    }
}
