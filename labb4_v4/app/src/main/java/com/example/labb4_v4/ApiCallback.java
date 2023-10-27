package com.example.labb4_v4;

import org.json.JSONObject;

import java.util.List;

public interface ApiCallback {

    public void onSuccess(List<Product> productList);
    public void onFailure(Exception e);
}
