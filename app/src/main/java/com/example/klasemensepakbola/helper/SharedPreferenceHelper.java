package com.example.klasemensepakbola.helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.klasemensepakbola.model.Klub;
import com.example.klasemensepakbola.model.Pertandingan;
import com.google.gson.Gson;

import java.util.List;

public class SharedPreferenceHelper {

    Context context;
    SharedPreferences sharedPreferences;
    Gson gson = new Gson();

    public SharedPreferenceHelper(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
    }

    public void addClub(List<Klub> klubList) {
        sharedPreferences.edit().putString(Constants.KLUB_KEY, gson.toJson(klubList)).apply();
    }

    public String getClub() {
        return sharedPreferences.getString(Constants.KLUB_KEY, "");
    }

    public void addPertandingan(List<Pertandingan> pertandinganList) {
        sharedPreferences.edit().putString(Constants.PERTANDINGAN_KEY, gson.toJson(pertandinganList)).apply();
    }

    public String getPertandingan() {
        return sharedPreferences.getString(Constants.PERTANDINGAN_KEY, "");
    }

}
