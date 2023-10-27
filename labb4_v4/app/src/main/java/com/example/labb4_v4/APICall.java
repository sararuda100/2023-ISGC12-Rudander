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
    private ArrayList list;
    private ArrayList<Product> products = new ArrayList();
    public void fetchData(RequestQueue requestQueue, ApiCallback callback, String url){
        //skapar en lista som vi kan lagra hämtad data i
        //ArrayList<Product> products = new ArrayList();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
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

                        products.add(new Product(id, amount, name, comp, description, cat, price));
                        list = products;
                        Log.d("API_1", "hej");
                    }
                    Log.d("API_2", "hej");
                    callback.onSuccess(products);

                } catch(Exception e){
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onFailure(error);
            }
        });
        requestQueue.add(jsonObjectRequest);
    }
}
