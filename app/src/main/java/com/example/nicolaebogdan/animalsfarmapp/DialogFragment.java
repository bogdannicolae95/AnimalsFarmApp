package com.example.nicolaebogdan.animalsfarmapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DialogFragment extends android.app.DialogFragment implements RecyclerDialogAdaptor.OnItemSelected {

    private static final String TAG = "DialogFragment";

    Button closeBtn;

    SharedPreferences myListPref;

    RecyclerView recyclerView;
    RecyclerDialogAdaptor adapter;
    RecyclerView.LayoutManager layoutManager;

    static ArrayList<DataProvider> dialogPetsList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_dialog, container, false);


        dialogPetsList = new ArrayList<>();
        dialogPetsList.add(new Chicken(""));
        dialogPetsList.add(new Horse(""));
        dialogPetsList.add(new Cow(""));
        dialogPetsList.add(new Cat(""));
        dialogPetsList.add(new Dog(""));
        dialogPetsList.add(new Pig(""));


        recyclerView = view.findViewById(R.id.recycler_view_dialog);
        adapter = new RecyclerDialogAdaptor(getActivity(), dialogPetsList, this, DialogFragment.this);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);


        closeBtn = view.findViewById(R.id.close_btn);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: closing dialog");
                getDialog().dismiss();
            }
        });


        return view;
    }

    public RecyclerDialogAdaptor getAdapter() {
        return adapter;
    }

    @Override
    public void onItemClicked() {
        getDialog().dismiss();
    }


}
