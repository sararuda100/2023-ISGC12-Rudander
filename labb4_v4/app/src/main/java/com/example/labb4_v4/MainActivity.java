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

public class MainActivity extends AppCompatActivity implements ApiCallback {
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

        //hämtar referenser till ListView, knappar och andra vyer från layouten
        listView = findViewById(R.id.list);
        showCartBtn = findViewById(R.id.showCartBtn);
        loadMoreBtn = findViewById(R.id.loadMoreBtn);

        //initierar produktlistan
        productList = new ArrayList<>();

        //skapar en RequestQueue för att hantera nätverksförfrågningar med hjälp av Volley-biblioteket
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024);
        Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();

        //skapar ett APICall-objekt för att göra API-anrop
        apiCall = new APICall();
        apiCall.fetchData(requestQueue, this, url);

        //skapar en instans av ShoppingCart för att hantera kundvagnen och skickar med context
        ShoppingCart shoppingCart = ShoppingCart.getInstance(this);

        //skapar en ProductAdapter för att visa produkter i ListView.
        productAdapter = new ProductAdapter(this, productList);
        listView.setAdapter(productAdapter);

        //sätter lussnare på kundvagnsknappen och callar funktionen för att visa kundvagnen.
        showCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onShowCartClicked();
            }
        });

        //lyssnare på knappen för att hämta nästa sida av produkter
        loadMoreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ökar sidnumret och uppdatera URL:en för att hämta fler produkter.
                pageNbr++;
                String newUrl = "https://informatik-webbkurser.hotell.kau.se/WebAPI/v1/products?limit=10&page=" + pageNbr + "&apikey=" + APIKey;

                //gör ett nytt API-anrop med den uppdaterade URL:en.
                apiCall.fetchData(requestQueue, new ApiCallback() {
                    @Override
                    public void onSuccess(List<Product> productList) {
                        // Uppdaterar adaptern för att visa de nya produkterna.
                        ProductAdapter adapter = (ProductAdapter) listView.getAdapter();
                        adapter.clear();
                        adapter.addAll(productList);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Exception e) {
                        //hanterarfel under nätverksförfrågan och logga felmeddelanden.
                        Log.e("MainActivity", "Nätverksförfrågan misslyckades: " + e.getMessage());
                    }
                }, newUrl);
            }
        });
    }

    //anropas när klick på kundvagn-knappen görs
    public void onShowCartClicked() {
        Intent intent = new Intent(this, ShoppingCartActivity.class);
        startActivity(intent);
    }

    //implmementera onSuccess-metoden från ApiCallback-gränssnittet
    @Override
    public void onSuccess(List<Product> productList) {
        // Uppdaterar adaptern för att visa de nya produkterna.
        ProductAdapter adapter = (ProductAdapter) listView.getAdapter();
        adapter.clear();
        adapter.addAll(productList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onFailure(Exception e) {
        //hanterar fel under nätverksförfrågan och loggar felmeddelanden.
        Log.e("MainActivity", "Nätverksförfrågan misslyckades: " + e.getMessage());
        e.printStackTrace();
    }
}
