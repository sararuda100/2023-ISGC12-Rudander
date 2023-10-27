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

    //private TextView productName;
    private List<Product> productList;

    private Context context;

    public ProductAdapter(Context context, List<Product> products){
        super(context, 0, products);
        this.context = context;
        this.productList = products;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.item_layout, parent, false);
        }

        TextView productName = convertView.findViewById(R.id.nameTextView);
        TextView productDescription = convertView.findViewById(R.id.descriptionTextView);
        TextView productPrice = convertView.findViewById(R.id.priceTextView);
        Button addToCartBtn = convertView.findViewById(R.id.addToCartBtn);

        Product currentProduct = productList.get(position);
        productName.setText(currentProduct.getName());
        productDescription.setText(currentProduct.getDescription());
        productPrice.setText("$" + String.valueOf(currentProduct.getPrice()));

        // Use a lambda expression to capture the currentProduct
        addToCartBtn.setOnClickListener(v -> {
            ShoppingCart.getInstance(context).addItem(currentProduct);
            //för att spara varan till fil
            Log.d("Cart", ShoppingCart.getInstance(context).getCartItems().toString());
        });

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Product currentProduct = productList.get(position);

                Intent intent = new Intent(context, ProductDetailsActivity.class);
                intent.putExtra("name", currentProduct.getName());
                intent.putExtra("company", currentProduct.getComp());
                intent.putExtra("price", currentProduct.getPrice());
                intent.putExtra("description", currentProduct.getDescription());
                context.startActivity(intent);
            }
        });
        //return super.getView(position, convertView, parent);
        return convertView;
    }
}