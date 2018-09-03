package com.example.nicolaebogdan.animalsfarmapp;

import android.widget.ProgressBar;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Handler;

public class DataProvider implements Serializable {
    private int aImage;
    private String aPetName;
    private int aPrice;
    private String aeachAnimalName;
    private int aHappiness;

    private boolean isAction;

     private Action eAction;

     private long lActionStart;

     private long happinessActionStart;

     private boolean isDead;


    public DataProvider(Action enumAction,int image,String petName,int price,String eachAnimalName,int happiness,boolean action,long actionStart,long happStart,boolean dead){
        aImage = image;
        aPetName = petName;
        aPrice = price;
        aeachAnimalName = eachAnimalName;
        aHappiness = happiness;
        isAction = action;
        eAction = enumAction;
        lActionStart = actionStart;
        happinessActionStart = happStart;
        isDead = dead;
    }

    public boolean getisDead() {
        return isDead;
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }

    public int getaImage() {
        return aImage;
    }

    public String getaPetName() {
        return aPetName;
    }


    public String getEachAnimalName() {
        return aeachAnimalName;
    }

    public int getaHappiness() {
        return aHappiness;
    }

    public void setaHappiness(int aHappiness) {
        this.aHappiness = aHappiness;
    }

    public boolean getisAction(){
        return isAction;
    }

    public void setAction(boolean action) {
        isAction = action;
    }

    public Action geteAction() {
        return eAction;
    }

    public void seteAction(Action eAction) {
        this.eAction = eAction;
    }

    public long getlActionStart() {
        return lActionStart;
    }

    public void setlActionStart(long lActionStart) {
        this.lActionStart = lActionStart;
    }

    public long getHappinessActionStart() {
        return happinessActionStart;
    }

    public void setHappinessActionStart(long happinessActionStart) {
        this.happinessActionStart = happinessActionStart;
    }
}
