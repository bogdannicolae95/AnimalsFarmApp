package com.example.nicolaebogdan.animalsfarmapp.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nicolaebogdan.animalsfarmapp.Constants;
import com.example.nicolaebogdan.animalsfarmapp.DataProvider;
import com.example.nicolaebogdan.animalsfarmapp.DialogFragment;
import com.example.nicolaebogdan.animalsfarmapp.PriceManager;
import com.example.nicolaebogdan.animalsfarmapp.R;
import com.example.nicolaebogdan.animalsfarmapp.RecyclerAdaptor;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";


    TextView avatarName ;
    ImageView avatarImage;
    Bitmap imageSaved;

    TextView initialCoin;

    SharedPreferences myPrefs;


    RecyclerView recyclerView;
    static RecyclerAdaptor adapter;
    RecyclerView.LayoutManager layoutManager;

    TextView coinTextView;
    SharedPreferences prefCoin;
    public static int Coin;

    List<String> animalsOfFamName;


     static Map<String,List<DataProvider>> petsMap;

     SharedPreferences sharedPreferences ;

     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

            // oncreate Main activity
         sharedPreferences = getSharedPreferences(Constants.PRICE,MainActivity.MODE_PRIVATE);
         if(sharedPreferences.contains(Constants.CCHIKENPRICE)){
             PriceManager.getINSTANCE().setPriceChicken(sharedPreferences.getInt(Constants.CCHIKENPRICE,Constants.FIRSTCHICKENPRICE));
         }
         if(sharedPreferences.contains(Constants.COWPRICE)){
             PriceManager.getINSTANCE().setPriceCow(sharedPreferences.getInt(Constants.COWPRICE,Constants.FIRSTCAWPRICE));
         }

         if(sharedPreferences.contains(Constants.PIGPRICE)){
             PriceManager.getINSTANCE().setPricePig(sharedPreferences.getInt(Constants.PIGPRICE,Constants.FIRSTPIGPRICE));
         }
         if(sharedPreferences.contains(Constants.HORSEPRICE)){
             PriceManager.getINSTANCE().setPriceHorse(sharedPreferences.getInt(Constants.HORSEPRICE,Constants.FIRSTHORSEPRICE));
         }
         if(sharedPreferences.contains(Constants.CATPRICE)){
             PriceManager.getINSTANCE().setPriceCat(sharedPreferences.getInt(Constants.CATPRICE,Constants.FIRSTCATPRICE));
         }
         if(sharedPreferences.contains(Constants.DOGPRICE)){
            PriceManager.getINSTANCE().setPriceDog(sharedPreferences.getInt(Constants.DOGPRICE,Constants.FIRSTDOGPRICE));
         }

         initialCoin = findViewById(R.id.coin_text_view);
        prefCoin = getSharedPreferences(Constants.PREFNAMECOIN,MODE_PRIVATE);
        Coin = prefCoin.getInt(Constants.PREFCOIN,20);
         Log.d(TAG, "COIN: " + Coin);

        //myPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        myPrefs = getSharedPreferences(Constants.PREFMAP,MODE_PRIVATE);

        petsMap = new HashMap<>();

        if(myPrefs.contains(Constants.PETSMAPTOSTRING)) {

            petsMap = loadMap(Constants.PETSMAPTOSTRING,getApplicationContext());
            Log.d(TAG, "onResume: create");
        }
        Log.d(TAG, "mtprefs: " + myPrefs.contains(Constants.PETSMAPTOSTRING));

        animalsOfFamName = new ArrayList<>();
        animalsOfFamName.addAll(petsMap.keySet());





        recyclerView = findViewById(R.id.recycler_view);
        adapter = new RecyclerAdaptor(this, petsMap, animalsOfFamName,initialCoin);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);



        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        //find the textView with Id AVATAR_TEXT_VIEW;
        avatarName = findViewById(R.id.avatar_text_view);
        //set text in avatarName text view from sharedPreferences;
        avatarName.setText(prefs.getString("userName", "Empty String"));

        avatarImage = findViewById(R.id.avatar_image_view);
        //decode base64-image from sharedPreferences to Bitmap image;
        imageSaved = decodeBase64(prefs.getString("Photo","Empty Image"));
        //set the imageSaved;
        avatarImage.setImageBitmap(imageSaved);

        coinTextView = findViewById(R.id.coin_text_view);
        coinTextView.setText(String.valueOf(Coin));

        FloatingActionButton fab =  findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment dialog = new DialogFragment();
                dialog.show(getFragmentManager(),"DialogFragment");
            }
        });



    }

    // method for base64 to bitmap
    public static Bitmap decodeBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }

    public void addNewAnimal(String animalType, String animalName) {
        adapter.addAnimal(animalType, animalName);
    }

    public void sellAnAnimal(String animalType){
         adapter.sellAnimal(animalType);
    }

    public static Map<String,List<DataProvider>> loadMap(String key,Context context){
        Map<String,List<DataProvider>> outputMap = new HashMap<String, List<DataProvider>>();
        SharedPreferences pSharedPref = context.getSharedPreferences(Constants.PREFMAP, Context.MODE_PRIVATE);
        try{
            //get from shared prefs
            String storedHashMapString = pSharedPref.getString(key, (new JSONObject()).toString());
            java.lang.reflect.Type type = new TypeToken<HashMap<String, List<DataProvider>>>(){}.getType();
            Gson gson = new Gson();
            return  gson.fromJson(storedHashMapString, type);
        }catch(Exception e){
            e.printStackTrace();
        }
        return outputMap;
    }

    @Override
    protected void onPause() {
        super.onPause();
        prefCoin.edit().putInt(Constants.PREFCOIN, Coin).apply();
    }

    @Override
    protected void onResume() {
        super.onResume();
        prefCoin = getSharedPreferences(Constants.PREFNAMECOIN,MODE_PRIVATE);
        Coin = prefCoin.getInt(Constants.PREFCOIN,20);
        coinTextView.setText(String.valueOf(Coin));

        myPrefs = getSharedPreferences(Constants.PREFMAP,MODE_PRIVATE);

        petsMap = new HashMap<>();

        if(myPrefs.contains(Constants.PETSMAPTOSTRING)) {

            petsMap = loadMap(Constants.PETSMAPTOSTRING,getApplicationContext());
            Log.d(TAG, "onResume: resume");

        }

        animalsOfFamName = new ArrayList<>();
        animalsOfFamName.addAll(petsMap.keySet());


        adapter = new RecyclerAdaptor(this, petsMap, animalsOfFamName,initialCoin);
        if(recyclerView != null){
            adapter = new RecyclerAdaptor(this, petsMap, animalsOfFamName,initialCoin);
            recyclerView.setHasFixedSize(true);
            layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
        }
    }
}
