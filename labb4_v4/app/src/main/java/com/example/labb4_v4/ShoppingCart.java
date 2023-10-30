package com.example.labb4_v4;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    //deklararerar referens till en enda instans av shopping cart (part of singleton pattern)
    private static ShoppingCart instance;
    //lagrar en lista med produktobjekt som representerar items i shoppingcarten
    private List<Product> cartItems;
    //används för att kommunicera mellan olika komponenter o resources i appen
    private Context context;

    public ShoppingCart(Context context){
        cartItems = new ArrayList<>();
        this.context = context;
    }

    //singleton pattern som skapar en instans om den inte redan finns, detta för att säkerställa att endast en instans skapas
    public static ShoppingCart getInstance(Context context) {
        if (instance==null) {
            instance = new ShoppingCart(context);
        }
        return instance;
    }

    public void addItem(Product product) {
        //lägger till en en produkt i carten
        cartItems.add(product);
        // Callar metod som sparar shoppingcarten till textfil efter att man addat items
        FileManager.saveShoppingCart(context, cartItems);
    }

    //metod som returnerar listan av produkterna i shoppings carten
    public List<Product> getCartItems(){
        return cartItems;
    }

}
