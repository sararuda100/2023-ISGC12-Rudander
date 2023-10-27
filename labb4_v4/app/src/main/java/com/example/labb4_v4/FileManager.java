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
    public static void saveShoppingCart(Context context, List<Product> shoppingCart) {
        File file = new File(context.getFilesDir(), "shopping_cart.txt");

        try {
            FileWriter writer = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);

            for (Product product : shoppingCart) {
                bufferedWriter.write(product.getName() + "\n");
            }

            bufferedWriter.close();
            writer.close();
            if (file.exists()) {
                Log.d("File_check", "file exists");
                // You can put your code here to read or write to the file
            } else {
                Log.d("File_check", "doesn't exist");
                // You can handle the error here
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Product> loadShoppingCart(Context context) {
        List<Product> shoppingCart = new ArrayList<>();
        File file = new File(context.getFilesDir(), "shopping_cart.txt");

        try {
            FileReader reader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line;
            int lineCount = 0;

            while ((line = bufferedReader.readLine()) != null) {
                // Check if the loaded line is not empty and the product name is not empty
                if (!line.isEmpty()) {
                    // Create a new Product instance with the name from the file
                    Product product = new Product(0, 0, line, "", "", "", 0.0); // Fill in appropriate default values
                    shoppingCart.add(product);
                    // Log the loaded line
                    Log.d("FileLoader", "Loaded line " + lineCount + ": " + line);
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

