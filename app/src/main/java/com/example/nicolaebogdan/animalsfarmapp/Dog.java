package com.example.nicolaebogdan.animalsfarmapp;

import java.io.Serializable;

public class Dog extends DataProvider implements Serializable{

    String aDogName;

    public Dog(String dogName){
        super(  Action.IDLE,
                R.drawable.dog,
                "Dog",
                PriceManager.getINSTANCE().getPriceDog(),
                dogName,
                100,
                false,
                0,
                System.currentTimeMillis(),
                false
        );
        aDogName = dogName;
    }
}
