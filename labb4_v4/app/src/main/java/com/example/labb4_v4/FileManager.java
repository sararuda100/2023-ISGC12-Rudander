package com.example.labb4_v4;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
    //metod för att spara kundvagnen till en fil
    public static void saveShoppingCart(Context context, List<Product> shoppingCart) {
        //skapar en fil i appens för att lagra kundvagnen
        File file = new File(context.getFilesDir(), "shopping_cart.txt");

        try {
            FileWriter writer = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);

            // Loopa igenom produkterna i kundvagnen och skriv produktens namn till filen, en produkt sen ny rad
            for (Product product : shoppingCart) {
                bufferedWriter.write(product.getName() + "\n");
            }

            bufferedWriter.close();
            writer.close();

            //verifiering om filen skapats
            if (file.exists()) {
                Log.d("File_check", "filen finns");
            } else {
                Log.d("File_check", "finns inte");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Metod för att ladda kundvagnen från en fil.
    public static List<Product> loadShoppingCart(Context context) {
        List<Product> shoppingCart = new ArrayList<>();
        //anger sökvägen till filen där kundvagnen är sparad.
        File file = new File(context.getFilesDir(), "shopping_cart.txt");

        try {
            FileReader reader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line;
            int lineCount = 0;

            while ((line = bufferedReader.readLine()) != null) {
                // Kontrollerar om raden är tom.
                if (!line.isEmpty()) {
                    //skapar en temporär produkt med namnet från filen och lägg till den i kundvagnen.
                    Product product = new Product(0, 0, line, "", "", "", 0.0); //Fyller i lämpliga standardvärden.
                    shoppingCart.add(product);
                }
                lineCount++;
            }

            bufferedReader.close();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return shoppingCart;
    }
}
