package com.ayoapp.cryptocurrency;

public class CurrencyRVModel {
    private String name;
    private String symbol;
    private double price;
    private double percentchange;

    public CurrencyRVModel(String name, String symbol, double price, double percentchange) {
        this.name = name;
        this.symbol = symbol;
        this.percentchange = percentchange;
        this.price = price;
    }

    public String getName() {
        return name;
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
