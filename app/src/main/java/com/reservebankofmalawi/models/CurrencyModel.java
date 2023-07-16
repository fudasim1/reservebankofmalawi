package com.reservebankofmalawi.models;


public class CurrencyModel {

    private int currencyPosition;
    private String currencyName;

    public CurrencyModel(int currencyPosition, String currencyName) {
        this.currencyPosition = currencyPosition;
        this.currencyName = currencyName;
    }

    public int getCurrencyPosition() {
        return currencyPosition;
    }

    public String getCurrencyName() {
        return currencyName;
    }
}
