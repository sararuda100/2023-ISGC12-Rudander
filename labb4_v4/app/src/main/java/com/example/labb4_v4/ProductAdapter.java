package com.example.labb4_v4;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ProductAdapter extends ArrayAdapter<Product> {
    private List<Product> productList;
    private Context context;

    public ProductAdapter(Context context, List<Product> products) {
        super(context, 0, products);
        this.context = context;
        this.productList = products;
    }
    //metoden används för att skapa och returnera en vy för varje objekt i listan.
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            // Om vyn inte redan är skapad, inflatas den från item_layout.xml
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.item_layout, parent, false);
        }

        // Hitta och initialisera olika vyer inom item_layout.xml
        TextView productName = convertView.findViewById(R.id.nameTextView);
        TextView productDescription = convertView.findViewById(R.id.descriptionTextView);
        TextView productPrice = convertView.findViewById(R.id.priceTextView);
        Button addToCartBtn = convertView.findViewById(R.id.addToCartBtn);

        //Hämta det nuvarande Product-objektet på den angivna positionen i listan.
        Product currentProduct = productList.get(position);

        //Sätter data från det aktuella produkt-objektet till texten i TextViews
        productName.setText(currentProduct.getName());
        productDescription.setText(currentProduct.getDescription());
        productPrice.setText("$" + String.valueOf(currentProduct.getPrice()));

        //lyssnare för knappen "shopping cart"
        addToCartBtn.setOnClickListener(v -> {
            //Lägger till det aktuella Product-objektet i kundvagnen.
            ShoppingCart.getInstance(context).addItem(currentProduct);
        });

        //Definiera en onClickListener för hela vyn (objektet).
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //skapar variabel för produkten som klickades
                // När ett objekt klickas, skapas en Intent för att visa dens detaljer i en ny aktivitet.
                //Läggr till relevant information om produkten i Intent.
                Product currentProduct = productList.get(position);

                Intent intent = new Intent(context, ProductDetailsActivity.class);
                intent.putExtra("name", currentProduct.getName());
                intent.putExtra("company", currentProduct.getComp());
                intent.putExtra("price", currentProduct.getPrice());
                intent.putExtra("description", currentProduct.getDescription());

                //Starta den nya aktiviteten för att visa produktinformationen
                context.startActivity(intent);
            }
        });

        //Returnera den ifyllda vyn för detta objekt.
        return convertView;
    }
}
