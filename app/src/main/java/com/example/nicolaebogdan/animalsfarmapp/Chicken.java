package com.example.nicolaebogdan.animalsfarmapp;

import java.io.Serializable;

public class Chicken extends DataProvider implements Serializable {

    String aChickenName;


    public Chicken(String chickenName) {
        super(  Action.IDLE,
                R.drawable.chicken,
                "Chicken",
                PriceManager.getINSTANCE().getPriceChicken(),
                chickenName,
                100,
                false,
                0,
                System.currentTimeMillis(),
                false
                );
        aChickenName = chickenName;
    }
}
