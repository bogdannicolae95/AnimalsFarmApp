package com.example.nicolaebogdan.animalsfarmapp;

import android.content.Context;
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

import java.util.ArrayList;

public class RecyclerDialogAdaptor extends RecyclerView.Adapter<RecyclerDialogAdaptor.RecyclerViewHolder> {

    private static final String TAG = RecyclerDialogAdaptor.class.getName();


    private ArrayList<DataProvider> arrayList;
    private LayoutInflater mInflater;
    Context context;
    DialogFragment fragment;
    private OnItemSelected onItemSelected;

    public interface OnItemSelected {
        void onItemClicked();
    }

    public RecyclerDialogAdaptor(Context context, ArrayList<DataProvider> arrayListDialog, DialogFragment fragmentDialog, OnItemSelected onItemSelected) {
        this.context = context;
        this.arrayList = arrayListDialog;
        this.fragment = fragmentDialog;
        this.onItemSelected = onItemSelected;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = mInflater.inflate(R.layout.dialog_list_item, parent, false);

        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view);

        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, final int position) {

        DataProvider dataProvider = arrayList.get(position);
        holder.imageView.setImageResource(dataProvider.getaImage());



        switch(dataProvider.getaPetName()){
            case "Chicken":
                holder.petPrice.setText(String.valueOf(PriceManager.getINSTANCE().getPriceChicken()));
                break;
            case "Cow":
                holder.petPrice.setText(String.valueOf(PriceManager.getINSTANCE().getPriceCow()));
                break;
            case "Dog":
                holder.petPrice.setText(String.valueOf(PriceManager.getINSTANCE().getPriceDog()));
                break;
            case "Pig":
                holder.petPrice.setText(String.valueOf(PriceManager.getINSTANCE().getPricePig()));
                break;
            case "Cat":
                holder.petPrice.setText(String.valueOf(PriceManager.getINSTANCE().getPriceCat()));
                break;
            case "Horse":
                holder.petPrice.setText(String.valueOf(PriceManager.getINSTANCE().getPriceHorse()));
                break;

        }
        holder.itemView.setTag(dataProvider);




        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(context,"Onclick Called on position: " + position,Toast.LENGTH_LONG).show();
                DataProvider dp = (DataProvider) view.getTag();


                ((MainActivity) context).addNewAnimal(dp.getaPetName(), holder.petName.getText().toString());

                onItemSelected.onItemClicked();

                Log.d(TAG, "onClick: " + dp.getaPetName());

            }
        });

    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        EditText petName;
        TextView petPrice;


        public RecyclerViewHolder(View view) {

            super(view);
            imageView = view.findViewById(R.id.dialog_image_view);
            petName = view.findViewById(R.id.dialog_pet_edit_text_view);
            petPrice = view.findViewById(R.id.dialog_price_text_view);


        }

    }

    /*//To save Hash Map
    public static void saveList(Context context,String key,List<DataProvider> inputList){
        SharedPreferences myListPref = context.getSharedPreferences(Constants.PREFDIALOGLIST, Context.MODE_PRIVATE);
        if (myListPref != null){
            Gson gson = new Gson();
            String ListString = gson.toJson(inputList);
            Log.d(TAG, "saveMap: " + ListString);
            //save in shared prefs
            myListPref.edit().putString(key, ListString).apply();
        }
    }

    public void saveArrayList(ArrayList<DataProvider> list, String key){
        SharedPreferences prefs =context.getSharedPreferences(Constants.PREFDIALOGLIST, MainActivity.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(key, json);
        editor.apply();     // This line is IMPORTANT !!!
    }*/

}
