package com.example.nicolaebogdan.animalsfarmapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nicolaebogdan.animalsfarmapp.activities.MainActivity;
import com.example.nicolaebogdan.animalsfarmapp.activities.PetDetailActivity;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class RecyclerAdaptor extends RecyclerView.Adapter<RecyclerAdaptor.RecyclerViewHolder> {
    private static final String TAG = RecyclerAdaptor.class.getName();
    private Map<String, List<DataProvider>> animals;
    private LayoutInflater mInflater;
    private Context context;
    private List<String> animalNames;
    private TextView coinTextView;
    private SharedPreferences prefs;

    public RecyclerAdaptor(Context context, Map<String, List<DataProvider>> animal, List<String> animalNames, TextView coinTextView) {
        this.context = context;
        this.animals = animal;
        this.animalNames = animalNames;
        this.coinTextView = coinTextView;
        prefs = context.getSharedPreferences(Constants.PRICE, MainActivity.MODE_PRIVATE);
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = mInflater.inflate(R.layout.item_layout, parent, false);

        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view);

        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, int position) {
        final String animalName = animalNames.get(position);
        if (animalName != null && holder.petName != null) {
            holder.petName.setText(animalName);
        }
        List<DataProvider> dataProvider = animals.get(animalName);
        if (dataProvider == null) {
            return;
        }

        switch (animalName) {
            case Constants.CHICKEN:
                holder.petOne.setImageResource(R.drawable.chicken);
                holder.petTwo.setImageResource(R.drawable.chicken);
                holder.petThree.setImageResource(R.drawable.chicken);
                break;
            case Constants.PIG:
                holder.petOne.setImageResource(R.drawable.pig);
                holder.petTwo.setImageResource(R.drawable.pig);
                holder.petThree.setImageResource(R.drawable.pig);
                break;
            case Constants.COW:
                holder.petOne.setImageResource(R.drawable.cow);
                holder.petTwo.setImageResource(R.drawable.cow);
                holder.petThree.setImageResource(R.drawable.cow);
                break;
            case Constants.HORSE:
                holder.petOne.setImageResource(R.drawable.horse);
                holder.petTwo.setImageResource(R.drawable.horse);
                holder.petThree.setImageResource(R.drawable.horse);
                break;
            case Constants.CAT:
                holder.petOne.setImageResource(R.drawable.cat);
                holder.petTwo.setImageResource(R.drawable.cat);
                holder.petThree.setImageResource(R.drawable.cat);
                break;
            case Constants.DOG:
                holder.petOne.setImageResource(R.drawable.dog);
                holder.petTwo.setImageResource(R.drawable.dog);
                holder.petThree.setImageResource(R.drawable.dog);
                break;
        }
        if (dataProvider.size() == 0) {
            holder.petOne.setVisibility(View.INVISIBLE);
            holder.petTwo.setVisibility(View.INVISIBLE);
            holder.petThree.setVisibility(View.INVISIBLE);
        } else if (dataProvider.size() == 1) {
            holder.petOne.setVisibility(View.VISIBLE);
            holder.petTwo.setVisibility(View.INVISIBLE);
            holder.petThree.setVisibility(View.INVISIBLE);
        } else if (dataProvider.size() == 2) {
            holder.petOne.setVisibility(View.VISIBLE);
            holder.petTwo.setVisibility(View.VISIBLE);
            holder.petThree.setVisibility(View.INVISIBLE);
        } else {
            holder.petOne.setVisibility(View.VISIBLE);
            holder.petTwo.setVisibility(View.VISIBLE);
            holder.petThree.setVisibility(View.VISIBLE);
        }


        switch (animalName){
            case Constants.CHICKEN :
                holder.petOne.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context,PetDetailActivity.class);
                        intent.putExtra(Constants.ANIMALOBJECT, animals.get(animalName).get(0));
                        intent.putExtra(Constants.LISTPOSITION, 0);
                        context.startActivity(intent);
                    }
                });
                holder.petTwo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context,PetDetailActivity.class);
                        intent.putExtra(Constants.ANIMALOBJECT,animals.get(animalName).get(1));
                        intent.putExtra(Constants.LISTPOSITION, 1);
                        context.startActivity(intent);
                    }
                });
                holder.petThree.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context,PetDetailActivity.class);
                        intent.putExtra(Constants.ANIMALOBJECT, (Serializable) animals.get(animalName).get(2));
                        intent.putExtra(Constants.LISTPOSITION, 2);
                        context.startActivity(intent);
                    }
                });

                break;
            case Constants.CAT :
                holder.petOne.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context,PetDetailActivity.class);
                        intent.putExtra(Constants.ANIMALOBJECT, animals.get(animalName).get(0));
                        intent.putExtra(Constants.LISTPOSITION, 0);
                        context.startActivity(intent);

                    }
                });

                holder.petTwo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context,PetDetailActivity.class);
                        intent.putExtra(Constants.ANIMALOBJECT, animals.get(animalName).get(1));
                        intent.putExtra(Constants.LISTPOSITION, 1);
                        context.startActivity(intent);

                    }
                });

                holder.petThree.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context,PetDetailActivity.class);
                        intent.putExtra(Constants.ANIMALOBJECT, animals.get(animalName).get(2));
                        intent.putExtra(Constants.LISTPOSITION, 2);
                        context.startActivity(intent);

                    }
                });
                break;

                case  Constants.COW:
                    holder.petOne.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(context,PetDetailActivity.class);
                            intent.putExtra(Constants.ANIMALOBJECT, animals.get(animalName).get(0));
                            intent.putExtra(Constants.LISTPOSITION, 0);
                            context.startActivity(intent);

                        }
                    });

                    holder.petTwo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(context,PetDetailActivity.class);
                            intent.putExtra(Constants.ANIMALOBJECT, animals.get(animalName).get(1));
                            intent.putExtra(Constants.LISTPOSITION, 1);
                            context.startActivity(intent);
                        }
                    });

                    holder.petThree.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(context,PetDetailActivity.class);
                            intent.putExtra(Constants.ANIMALOBJECT, animals.get(animalName).get(2));
                            intent.putExtra(Constants.LISTPOSITION, 2);
                            context.startActivity(intent);
                        }
                    });
                break;

                case Constants.DOG :
                    Log.d(TAG, "onBindViewHolder: ANIMAL NAME" + animalNames.get(position));

                    holder.petOne.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(context,PetDetailActivity.class);
                            intent.putExtra(Constants.ANIMALOBJECT, animals.get(animalName).get(0));
                            intent.putExtra(Constants.LISTPOSITION, 0);
                            context.startActivity(intent);

                        }
                    });

                    holder.petTwo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(context,PetDetailActivity.class);
                            intent.putExtra(Constants.ANIMALOBJECT, animals.get(animalName).get(1));
                            intent.putExtra(Constants.LISTPOSITION, 1);
                            context.startActivity(intent);
//
                        }
                    });

                    holder.petThree.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(context,PetDetailActivity.class);
                            intent.putExtra(Constants.ANIMALOBJECT, animals.get(animalName).get(2));
                            intent.putExtra(Constants.LISTPOSITION, 2);
                            context.startActivity(intent);
                        }
                    });
                break;

                case Constants.PIG:
                    holder.petOne.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(context,PetDetailActivity.class);
                            intent.putExtra(Constants.ANIMALOBJECT, animals.get(animalName).get(0));
                            intent.putExtra(Constants.LISTPOSITION, 0);
                            context.startActivity(intent);
                        }
                    });

                    holder.petTwo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(context,PetDetailActivity.class);
                            intent.putExtra(Constants.ANIMALOBJECT, animals.get(animalName).get(1));
                            intent.putExtra(Constants.LISTPOSITION, 1);
                            context.startActivity(intent);
                        }
                    });

                    holder.petThree.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(context,PetDetailActivity.class);
                            intent.putExtra(Constants.ANIMALOBJECT, animals.get(animalName).get(2));
                            intent.putExtra(Constants.LISTPOSITION, 2);
                            context.startActivity(intent);
                        }
                    });
                break;

                case Constants.HORSE:
                    holder.petOne.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(context,PetDetailActivity.class);
                            intent.putExtra(Constants.ANIMALOBJECT, animals.get(animalName).get(0));
                            intent.putExtra(Constants.LISTPOSITION, 0);
                            context.startActivity(intent);
                        }
                    });

                    holder.petTwo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(context,PetDetailActivity.class);
                            intent.putExtra(Constants.ANIMALOBJECT, animals.get(animalName).get(1));
                            intent.putExtra(Constants.LISTPOSITION, 1);
                            context.startActivity(intent);
                        }
                    });

                    holder.petThree.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(context,PetDetailActivity.class);
                            intent.putExtra(Constants.ANIMALOBJECT, animals.get(animalName).get(2));
                            intent.putExtra(Constants.LISTPOSITION, 2);
                            context.startActivity(intent);
                        }
                    });

                break;


        }


    }

    @Override
    public int getItemCount() {
        return animals.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {

        private ImageView petOne, petTwo, petThree;
        private TextView petName;

        public RecyclerViewHolder(View view) {

            super(view);
            petOne = view.findViewById(R.id.iv_pet_one);
            petTwo = view.findViewById(R.id.iv_pet_two);
            petThree = view.findViewById(R.id.iv_pet_three);
            petName = view.findViewById(R.id.pet_name);


        }

    }

    public void addAnimal(String animalType, String animalName) {
        if (animals.containsKey(animalType)) {
            List<DataProvider> animalsOfType = animals.get(animalType);
            switch (animalType) {
                case Constants.CHICKEN:

                    if(MainActivity.Coin >= PriceManager.getINSTANCE().getPriceChicken()){
                        if(animalsOfType.size() < 3) {
                            animalsOfType.add(new Chicken(animalName));
                            MainActivity.Coin = MainActivity.Coin - PriceManager.getINSTANCE().getPriceChicken();
                            PriceManager.getINSTANCE().incremntChickenPrice(5);
                            coinTextView.setText(String.valueOf(MainActivity.Coin));
                            prefs.edit().putInt(Constants.CCHIKENPRICE, PriceManager.getINSTANCE().getPriceChicken()).apply();
                        }else{
                            Toast.makeText(context,"You can't add more Chickens",Toast.LENGTH_LONG).show();
                            return;
                        }
                    } else {
                        Toast.makeText(context,"You haven't enough money!",Toast.LENGTH_LONG).show();
                        return;
                    }
                    break;
                case Constants.PIG:

                    if(MainActivity.Coin >= PriceManager.getINSTANCE().getPricePig()){
                        if(animalsOfType.size() < 3) {
                            animalsOfType.add(new Pig(animalName));
                            MainActivity.Coin = MainActivity.Coin - PriceManager.getINSTANCE().getPricePig();
                            PriceManager.getINSTANCE().incremntPigPrice(5);
                            coinTextView.setText(String.valueOf(MainActivity.Coin));
                            prefs.edit().putInt(Constants.PIGPRICE, PriceManager.getINSTANCE().getPricePig()).apply();
                        }else{
                            Toast.makeText(context,"You can't add more Pigs",Toast.LENGTH_LONG).show();
                            return;
                        }
                    }else{
                        Toast.makeText(context,"You haven't enough money!",Toast.LENGTH_LONG).show();
                        return;
                    }

                    break;
                case Constants.COW:
                    if(MainActivity.Coin >= PriceManager.getINSTANCE().getPriceCow()) {
                        if(animalsOfType.size() < 3) {
                            animalsOfType.add(new Cow(animalName));
                            MainActivity.Coin = MainActivity.Coin - PriceManager.getINSTANCE().getPriceCow();
                            PriceManager.getINSTANCE().incremntCowPrice(5);
                            coinTextView.setText(String.valueOf(MainActivity.Coin));
                            prefs.edit().putInt(Constants.COWPRICE, PriceManager.getINSTANCE().getPriceCow()).apply();
                        }else{
                            Toast.makeText(context,"You can't add more cows",Toast.LENGTH_LONG).show();
                            return;
                        }
                    }else{
                        Toast.makeText(context,"You haven't enough money!",Toast.LENGTH_LONG).show();
                        return;
                    }
                    break;
                case Constants.HORSE:
                    if(MainActivity.Coin >= PriceManager.getINSTANCE().getPriceHorse()) {
                        if(animalsOfType.size() < 3) {
                            animalsOfType.add(new Horse(animalName));
                            MainActivity.Coin = MainActivity.Coin - PriceManager.getINSTANCE().getPriceHorse();
                            PriceManager.getINSTANCE().incrementHorsePrice(5);
                            coinTextView.setText(String.valueOf(MainActivity.Coin));
                            prefs.edit().putInt(Constants.HORSEPRICE, PriceManager.getINSTANCE().getPriceHorse()).apply();
                        }else{
                            Toast.makeText(context,"You can't add more Horses",Toast.LENGTH_LONG).show();
                            return;
                        }
                    }else{
                        Toast.makeText(context,"You haven't enough money!",Toast.LENGTH_LONG).show();
                        return;
                    }
                    break;
                case Constants.CAT:
                    if(MainActivity.Coin >= PriceManager.getINSTANCE().getPriceCat()) {
                        if(animalsOfType.size() < 3) {
                            animalsOfType.add(new Cat(animalName));
                            MainActivity.Coin = MainActivity.Coin - PriceManager.getINSTANCE().getPriceCat();
                            PriceManager.getINSTANCE().incrementCatPrice(5);
                            coinTextView.setText(String.valueOf(MainActivity.Coin));
                            prefs.edit().putInt(Constants.CATPRICE, PriceManager.getINSTANCE().getPriceCat()).apply();
                        }else{
                            Toast.makeText(context,"You can't add more cats",Toast.LENGTH_LONG).show();
                            return;
                        }
                    }else{
                        Toast.makeText(context,"You haven't enough money!",Toast.LENGTH_LONG).show();
                        return;
                    }
                    break;
                case Constants.DOG:
                    if (MainActivity.Coin >= PriceManager.getINSTANCE().getPriceDog()) {
                        if(animalsOfType.size() < 3) {
                            animalsOfType.add(new Dog(animalName));
                            MainActivity.Coin = MainActivity.Coin - PriceManager.getINSTANCE().getPriceDog();
                            PriceManager.getINSTANCE().incrementDogPrice(5);
                            coinTextView.setText(String.valueOf(MainActivity.Coin));
                            prefs.edit().putInt(Constants.DOGPRICE, PriceManager.getINSTANCE().getPriceDog()).apply();
                        }else{
                            Toast.makeText(context,"You can't add more Dogs",Toast.LENGTH_LONG).show();
                            return;
                        }
                    }else{
                        Toast.makeText(context,"You haven't enough money!",Toast.LENGTH_LONG).show();
                        return;
                    }
                    break;
            }

            notifyItemChanged(animalNames.indexOf(animalType));
            saveMap(context,Constants.PETSMAPTOSTRING,animals);
        } else {
            List<DataProvider> newAnimalType = new ArrayList<>();
            switch (animalType) {
                case Constants.CHICKEN:
                    if(MainActivity.Coin >= PriceManager.getINSTANCE().getPriceChicken()){
                        if(newAnimalType.size() < 3) {
                            newAnimalType.add(new Chicken(animalName));
                            MainActivity.Coin = MainActivity.Coin - PriceManager.getINSTANCE().getPriceChicken();
                            PriceManager.getINSTANCE().incremntChickenPrice(5);
                            coinTextView.setText(String.valueOf(MainActivity.Coin));
                            prefs.edit().putInt(Constants.CCHIKENPRICE, PriceManager.getINSTANCE().getPriceChicken()).apply();
                        }else{
                            Toast.makeText(context,"You can't add more Chickens",Toast.LENGTH_LONG).show();
                            return;
                        }
                    }else{
                        Toast.makeText(context,"You don't have enough money",Toast.LENGTH_LONG).show();
                        return;
                    }
                    break;
                case Constants.PIG:
                    if(MainActivity.Coin >= PriceManager.getINSTANCE().getPricePig()) {
                        if(newAnimalType.size() < 3) {

                            newAnimalType.add(new Pig(animalName));
                            MainActivity.Coin = MainActivity.Coin - PriceManager.getINSTANCE().getPricePig();
                            PriceManager.getINSTANCE().incremntPigPrice(5);
                            coinTextView.setText(String.valueOf(MainActivity.Coin));
                            prefs.edit().putInt(Constants.PIGPRICE, PriceManager.getINSTANCE().getPricePig()).apply();
                        }else{
                            Toast.makeText(context,"You can't add more Pigs",Toast.LENGTH_LONG).show();
                            return;
                        }
                    }else{
                        Toast.makeText(context,"You don't have enough money",Toast.LENGTH_LONG).show();
                        return;
                    }
                    break;
                case Constants.COW:
                    if(MainActivity.Coin >= PriceManager.getINSTANCE().getPriceCow()){
                        if(newAnimalType.size() < 3) {
                            newAnimalType.add(new Cow(animalName));
                            MainActivity.Coin = MainActivity.Coin - PriceManager.getINSTANCE().getPriceCow();
                            PriceManager.getINSTANCE().incremntCowPrice(5);
                            coinTextView.setText(String.valueOf(MainActivity.Coin));
                            prefs.edit().putInt(Constants.COWPRICE, PriceManager.getINSTANCE().getPriceCow()).apply();
                        }else{
                            Toast.makeText(context,"You can't add more Cows",Toast.LENGTH_LONG).show();
                            return;
                        }
                    }else{
                        Toast.makeText(context,"You don't have enough money",Toast.LENGTH_LONG).show();
                        return;
                    }
                    break;
                case Constants.HORSE:
                    if(MainActivity.Coin >= PriceManager.getINSTANCE().getPriceHorse()) {
                        if(newAnimalType.size() < 3) {
                            newAnimalType.add(new Horse(animalName));
                            MainActivity.Coin = MainActivity.Coin - PriceManager.getINSTANCE().getPriceHorse();
                            PriceManager.getINSTANCE().incrementHorsePrice(5);
                            coinTextView.setText(String.valueOf(MainActivity.Coin));
                            prefs.edit().putInt(Constants.HORSEPRICE, PriceManager.getINSTANCE().getPriceHorse()).apply();
                        }else{
                            Toast.makeText(context,"You can't add more Horses",Toast.LENGTH_LONG).show();
                            return;
                        }
                    }else{
                        Toast.makeText(context,"You don't have enough money",Toast.LENGTH_LONG).show();
                        return;
                    }
                    break;
                case Constants.CAT:
                    if(MainActivity.Coin >= PriceManager.getINSTANCE().getPriceCat()) {
                        if(newAnimalType.size() < 3) {
                            newAnimalType.add(new Cat(animalName));
                            MainActivity.Coin = MainActivity.Coin - PriceManager.getINSTANCE().getPriceCat();
                            PriceManager.getINSTANCE().incrementCatPrice(5);
                            coinTextView.setText(String.valueOf(MainActivity.Coin));
                            prefs.edit().putInt(Constants.CATPRICE, PriceManager.getINSTANCE().getPriceCat()).apply();
                        }else{
                            Toast.makeText(context,"You can't add more Cats",Toast.LENGTH_LONG).show();
                            return;
                        }
                    }else{
                        Toast.makeText(context,"You don't have enough money",Toast.LENGTH_LONG).show();
                        return;
                    }
                    break;
                case Constants.DOG:
                    if(MainActivity.Coin >= PriceManager.getINSTANCE().getPriceDog()) {
                        if(newAnimalType.size() <3) {
                            newAnimalType.add(new Dog(animalName));
                            MainActivity.Coin = MainActivity.Coin - PriceManager.getINSTANCE().getPriceDog();
                            PriceManager.getINSTANCE().incrementDogPrice(5);
                            coinTextView.setText(String.valueOf(MainActivity.Coin));
                            prefs.edit().putInt(Constants.DOGPRICE, PriceManager.getINSTANCE().getPriceDog()).apply();
                        }else {
                            Toast.makeText(context,"You can't add more Dogs",Toast.LENGTH_LONG).show();
                            return;
                        }
                    }else{
                        Toast.makeText(context,"You don't have enough money",Toast.LENGTH_LONG).show();
                        return;
                    }
                    break;
            }
            animals.put(animalType, newAnimalType);
            animalNames.add(animalType);
            notifyDataSetChanged();

           saveMap(context,Constants.PETSMAPTOSTRING,animals);


        }
    }

    public void sellAnimal(String animalType){

    }


    //To save Hash Map
    public static void saveMap(Context context,String key, Map<String,List<DataProvider>> inputMap){
        SharedPreferences pSharedPref = context.getSharedPreferences(Constants.PREFMAP, Context.MODE_PRIVATE);
        if (pSharedPref != null){
            Gson gson = new Gson();
            String hashMapString = gson.toJson(inputMap);
            Log.d(TAG, "saveMap: " + hashMapString);
            //save in shared prefs
            pSharedPref.edit().putString(key, hashMapString).apply();
        }
    }

}
