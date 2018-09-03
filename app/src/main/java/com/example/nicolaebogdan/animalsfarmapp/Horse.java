package com.example.nicolaebogdan.animalsfarmapp;

import java.io.Serializable;

public class Horse extends DataProvider implements Serializable {

    String aHorseName;

    public Horse(String horseName){
        super(Action.IDLE,
                R.drawable.horse,
                "Horse",
                PriceManager.getINSTANCE().getPriceHorse(),
                horseName,
                100,
                false,
                0,
                System.currentTimeMillis(),
                false
        );
        aHorseName = horseName;
    }
}
