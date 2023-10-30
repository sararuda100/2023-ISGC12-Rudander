package com.example.labb4_v4;

import org.json.JSONObject;

import java.util.List;

//gränssnitt som används för resultatet av ett API-anrop, om det har gått eller inte
public interface ApiCallback {

    //om API-anropet lyckades returneras en lista av produkter
    public void onSuccess(List<Product> productList);
    //metod som anropas om fel uppstår vid API-anropet
    public void onFailure(Exception e);
}