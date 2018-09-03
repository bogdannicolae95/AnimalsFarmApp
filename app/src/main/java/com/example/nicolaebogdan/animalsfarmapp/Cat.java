package com.example.nicolaebogdan.animalsfarmapp;

import java.io.Serializable;

public class Cat extends DataProvider implements Serializable {

    String aCatName;

    public Cat(String catName) {
        super(  Action.IDLE,
                R.drawable.cat,
                "Cat",
                PriceManager.getINSTANCE().getPriceCat(),
                catName,
                100,
                false,
                0,
                System.currentTimeMillis(),
                false
                );
        aCatName = catName;
    }
}
