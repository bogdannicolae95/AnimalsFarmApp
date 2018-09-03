package com.example.nicolaebogdan.animalsfarmapp;

public class PriceManager {

    private int PriceChicken = 10;
    private int PriceCow = 25;
    private int PriceHorse = 35;
    private int PricePig = 15;
    private int PriceCat = 15;
    private int PriceDog = 20;

    private static PriceManager INSTANCE;


    public static PriceManager getINSTANCE() {
        if(INSTANCE == null) {
            INSTANCE = new PriceManager();
        }
        return INSTANCE;
    }

    public void setPriceChicken(int priceChicken) {
        this.PriceChicken = priceChicken;
    }

    public void setPriceCow(int PriceCow) {
        this.PriceCow = PriceCow;
    }

    public void setPriceHorse(int priceHorse) {
        this.PriceHorse = priceHorse;
    }

    public void setPricePig(int pricePig) {
        this.PricePig = pricePig;
    }

    public void setPriceCat(int priceCat) {
        this.PriceCat = priceCat;
    }

    public void setPriceDog(int priceDog) {
        this.PriceDog = priceDog;
    }

    public int getPriceChicken() {
        return PriceChicken;
    }

    public int getPriceCow() {
        return PriceCow;
    }

    public int getPriceHorse() {
        return PriceHorse;
    }

    public int getPricePig() {
        return PricePig;
    }

    public int getPriceCat() {
        return PriceCat;
    }

    public int getPriceDog() {
        return PriceDog;
    }

    public void incremntChickenPrice(int diference){
        PriceChicken += diference;
    }

    public void incremntCowPrice(int diference){
        PriceCow += diference;
    }

    public void incremntPigPrice(int diference){
        PricePig += diference;
    }

    public void incrementHorsePrice(int diference){
        PriceHorse += diference;
    }

    public void incrementCatPrice(int diference){
        PriceCat += diference;
    }

    public void incrementDogPrice(int diference){
        PriceDog += diference;
    }
}
