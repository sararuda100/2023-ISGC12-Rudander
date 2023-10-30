package com.example.labb4_v4;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class APICall {
    private ArrayList<Product> products = new ArrayList();

    // Metod för att hämta data från en API.
    public void fetchData(RequestQueue requestQueue, ApiCallback callback, String url){
        // Skapar en JsonObjectRequest för att göra en GET-förfrågan till den angivna URL:en.
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    //försöker hämta data för varje produkt man hittar
                    JSONArray jsonArray = response.getJSONArray("products");
                    for(int i = 0; i < jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        int id = jsonObject.getInt("id");
                        String name = jsonObject.getString("name");
                        String description = jsonObject.getString("description");
                        String cat = jsonObject.getString("category");
                        String comp = jsonObject.getString("company");
                        double price = Double.parseDouble(jsonObject.getString("price"));
                        int amount = jsonObject.getInt("available");

                        // Skapar en ny Product och lägger till den i listan av produkter.
                        products.add(new Product(id, amount, name, comp, description, cat, price));
                    }
                    //Anropar callback-metoden onSuccess med den hämtade listan av produkter
                    //meddelas att datan hämtats "framgångsrikt"
                    callback.onSuccess(products);

                } catch(Exception e){
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //om ett fel uppstår under API-anropet, anropas callback-metoden onFailure med felet.
                callback.onFailure(error);
            }
        });

        //lägger till förfrågan i RequestQueue för att utföra den.
        requestQueue.add(jsonObjectRequest);
    }
}
