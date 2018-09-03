package com.example.nicolaebogdan.animalsfarmapp;

import java.io.Serializable;

public class Pig extends DataProvider implements Serializable{

    String aPigName;

    public Pig(String pigName){
        super(Action.IDLE,
                R.drawable.pig,
                "Pig",
                PriceManager.getINSTANCE().getPricePig(),
                pigName,
                100,
                false,
                0,
                System.currentTimeMillis(),
                false
        );
        aPigName = pigName;
    }
}
