package com.example.labb4_v4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements ApiCallback{
    //kolla upp vad detta kallas, bra att ha globalt iom att det ska användas på flera ställen.
    private APICall apiCall;
    RequestQueue requestQueue;
    private final String APIKey = "h2s269nsMn012NASi2537bsA9dBSa2";
    private int pageNbr = 1;
    private String url = "https://informatik-webbkurser.hotell.kau.se/WebAPI/v1/products?limit=10&page=" + pageNbr + "&apikey=" + APIKey;
    private ProductAdapter productAdapter;
    private ListView listView;
    private Button showCartBtn, loadMoreBtn;
    private List<Product> productList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.list);
        showCartBtn = findViewById(R.id.showCartBtn);
        loadMoreBtn = findViewById(R.id.loadMoreBtn);

        //initiera produktlistan
        productList = new ArrayList<>();

        //vi sätter upp nätverk?
        Cache cache = new DiskBasedCache(getCacheDir(), 1024*1024);
        Network network = new BasicNetwork(new HurlStack());

        //vi börjar dra in volleyn lite
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();

        apiCall = new APICall();
        apiCall.fetchData(requestQueue, this, url);

        // Initialize the ShoppingCart with the application context
        ShoppingCart shoppingCart = ShoppingCart.getInstance(this);

        //initierar produktadaptern och skickar med context samt listan
        productAdapter = new ProductAdapter(this, productList);

        // Set the adapter on the ListView, binder listan till adaptern? annars syns inget
        listView.setAdapter(productAdapter);

        showCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onShowCartClicked();
            }
        });

        loadMoreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Increment the page number
                pageNbr++;
                // Construct the new URL with the updated page number
                String newUrl = "https://informatik-webbkurser.hotell.kau.se/WebAPI/v1/products?limit=10&page=" + pageNbr + "&apikey=" + APIKey;
                // Fetch data from the new URL
                apiCall.fetchData(requestQueue, new ApiCallback() {
                    @Override
                    public void onSuccess(List<Product> productList) {
                        ProductAdapter adapter = (ProductAdapter) listView.getAdapter();

                        // Clear the existing data and add the new products
                        adapter.clear();
                        adapter.addAll(productList);
                        adapter.notifyDataSetChanged();
                    }



                    @Override
                    public void onFailure(Exception e) {
                        // Handle failure
                        Log.e("MainActivity", "Network request failed: " + e.getMessage());
                    }
                }, newUrl);
            }
        });


    }

    public void onShowCartClicked() {
        Intent intent = new Intent(this, ShoppingCartActivity.class);
        startActivity(intent);
    }

    @Override
    public void onSuccess(List<Product> productList) {
        ProductAdapter adapter = (ProductAdapter) listView.getAdapter();
        adapter.clear();
        adapter.addAll(productList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onFailure(Exception e) {
        // Handle the failure, and log any exception that occurred
        Log.e("MainActivity", "Network request failed: " + e.getMessage());

    }


}