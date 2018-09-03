package com.example.nicolaebogdan.animalsfarmapp;

import java.io.Serializable;

public class Cow extends DataProvider implements Serializable {

    String aCowName;

    public Cow(String cowName){
        super(  Action.IDLE,
                R.drawable.cow,
                "Cow",
                PriceManager.getINSTANCE().getPriceCow(),
                cowName,
                100,
                false,
                0,
                System.currentTimeMillis(),
                false
        );
        aCowName = cowName;
    }
}
