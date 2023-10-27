package com.example.labb4_v4;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    private static ShoppingCart instance;
    private List<Product> cartItems;
    private Context context;

    public ShoppingCart(Context context){
        cartItems = new ArrayList<>();
        this.context = context;
    }

    public static ShoppingCart getInstance(Context context) {
        if (instance==null) {
            instance = new ShoppingCart(context); //singleton, för att säkerställa att endast en instans skapas
        }
        return instance;
    }

    public void addItem(Product product) {
        cartItems.add(product);
        // Save the shopping cart after adding an item
        FileManager.saveShoppingCart(context, cartItems);
    }

    public List<Product> getCartItems(){
        return cartItems;
    }

}
