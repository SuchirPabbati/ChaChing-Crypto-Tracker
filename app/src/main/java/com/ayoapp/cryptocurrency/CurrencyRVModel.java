package com.ayoapp.cryptocurrency;

public class CurrencyRVModel {
    private String name;
    private String symbol;
    private double price;
    private double percentchange;
    private String imageurl;
    private int id;

    public CurrencyRVModel(String name, String symbol, double price, double percentchange,String imageurl,int id) {
        this.name = name;
        this.symbol = symbol;
        this.percentchange = percentchange;
        this.price = price;
        this.imageurl = imageurl;
        this.id = id;

    }

    public String getName() {
        return name;
    }
    public String getImageurl() {
        return imageurl;
    }
    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPercentchange() {
        return percentchange;
    }

    public void setPercentchange(double percentchange) {
        this.percentchange = percentchange;
    }


}
