package com.example.labb4_v4;

import java.io.Serializable;

public class Product implements Serializable {

    //privata instansvariabler, attribut för en produkt
    private String name, comp, description, cat;
    private int id, amount;
    private double price;
    //konstruktor för att skapa en ny produkt
    public Product(int id, int amount, String name, String comp, String description, String cat, double price) {
        this.id = id;
        this.amount = amount;
        this.name = name;
        this.comp = comp;
        this.description = description;
        this.cat = cat;
        this.price = price;
    }

    //get-metoder för att få tillgång till egenskaperna (variablerna)
    public int getId() {
        return id;
    }

    public int getAmount() {
        return amount;
    }

    public String getName() {
        return name;
    }

    public String getComp() {
        return comp;
    }

    public String getDescription() {
        return description;
    }

    public String getCat() {
        return cat;
    }

    public double getPrice() {
        return price;
    }

    //Överskriver toString-metoden för att returnera produktnamnet.Användbart vid när man ska displaya
    @Override
    public String toString(){return name;}
}


