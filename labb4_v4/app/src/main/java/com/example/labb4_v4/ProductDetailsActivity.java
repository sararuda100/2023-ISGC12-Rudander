package com.example.labb4_v4;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ProductDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        // Get product details from the intent
        Intent intent = getIntent();
        if (intent != null) {
            String productName = intent.getStringExtra("name");
            String company = intent.getStringExtra("company");
            double price = intent.getDoubleExtra("price", 0.0);
            String description = intent.getStringExtra("description");

            // Display the product details
            TextView nameTextView = findViewById(R.id.nameTextView);
            TextView companyTextView = findViewById(R.id.companyTextView);
            TextView priceTextView = findViewById(R.id.priceTextView);
            TextView descriptionTextView = findViewById(R.id.descriptionTextView);

            nameTextView.setText(productName);
            companyTextView.setText("Made by: " + company);
            priceTextView.setText("Price: $" + price);
            descriptionTextView.setText(description);
        }
    }
}

