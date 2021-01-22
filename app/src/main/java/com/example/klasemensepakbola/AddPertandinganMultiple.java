package com.example.klasemensepakbola;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.klasemensepakbola.adapter.AddPertandinganAdapter;
import com.example.klasemensepakbola.helper.SharedPreferenceHelper;
import com.example.klasemensepakbola.model.Klub;
import com.example.klasemensepakbola.model.Pertandingan;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.w3c.dom.Text;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AddPertandinganMultiple extends AppCompatActivity {

    TextView addPertandingan;
    Button btnSave;
    RecyclerView rvAddPertandingan;

    int jumlah = 1;

    SharedPreferenceHelper sharedPreferenceHelper;
    List<Klub> klubList = new ArrayList<>();
    List<Pertandingan> pertandinganList = new ArrayList<>();

    AddPertandinganAdapter addPertandinganAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pertandingan_multiple);
        sharedPreferenceHelper = new SharedPreferenceHelper(this);

        if (!sharedPreferenceHelper.getClub().isEmpty()) {
            Gson gson = new Gson();
            String klubListString = sharedPreferenceHelper.getClub();
            Type type = new TypeToken<List<Klub>>(){}.getType();
            klubList = gson.fromJson(klubListString, type);

            Log.e("Main", "onCreate: " + klubList);
        }

        if (!sharedPreferenceHelper.getPertandingan().isEmpty()) {
            Gson gson = new Gson();
            String pertandinganListString = sharedPreferenceHelper.getPertandingan();
            Type type = new TypeToken<List<Pertandingan>>(){}.getType();
            pertandinganList = gson.fromJson(pertandinganListString, type);

            Log.e("Pertandingan", "onCreate: " + pertandinganList);
        }

        addPertandinganAdapter = new AddPertandinganAdapter(this, jumlah);

        rvAddPertandingan = findViewById(R.id.rvAddPertandingan);
        addPertandingan = findViewById(R.id.addPertandingan);
        btnSave = findViewById(R.id.btnSave);

        rvAddPertandingan.setAdapter(addPertandinganAdapter);
        rvAddPertandingan.setLayoutManager(new LinearLayoutManager(this));

        addPertandingan.setOnClickListener(v -> {
            jumlah += 1;
            addPertandinganAdapter = new AddPertandinganAdapter(this, jumlah);
            rvAddPertandingan.setAdapter(addPertandinganAdapter);
        });

        btnSave.setOnClickListener(v -> {
            String[] club1 = addPertandinganAdapter.getClub1(), club2 = addPertandinganAdapter.getClub2();
            Integer[] score1 = addPertandinganAdapter.getScore1(), score2 = addPertandinganAdapter.getScore2();
            int[] point1 = new int[score1.length], point2 = new int[score1.length];

            for (int i = 0; i < club1.length; i++) {
                if (club1[i].equals(club2[i])) {
                    Toast.makeText(this, "Club tidak boleh sama", Toast.LENGTH_SHORT).show();
                    return;
                } else if (!pernahTanding(club1, club2, i)) {
                    Toast.makeText(this, "Kedua klub sudah pernah bertanding", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    if (score1[i].equals(score2[i])) {
                        point1[i] = 1;
                        point2[i] = 1;
                    } else {
                        if (score1[i] > score2[i]) {
                            point1[i] = 3;
                            point2[i] = 0;
                        } else {
                            point1[i] = 0;
                            point2[i] = 3;
                        }
                    }


                    for (int k = 0; k < klubList.size(); k++) {
                        if (club1[i].equals(klubList.get(k).getNamaKlub())) {
                            klubList.get(k).setJumlahMain(klubList.get(k).getJumlahMain() + 1);
                            if (point1[i] == 1) {
                                klubList.get(k).setJumlahSeri(klubList.get(k).getJumlahSeri() + 1);
                            } else if (point1[i] == 3) {
                                klubList.get(k).setJumlahMenang(klubList.get(k).getJumlahMenang() + 1);
                            } else {
                                klubList.get(k).setJumlahKalah(klubList.get(k).getJumlahKalah() + 1);
                            }
                            klubList.get(k).setJumlahGoalMenang(klubList.get(k).getJumlahGoalMenang() + score1[i]);
                            klubList.get(k).setJumlahGoalKalah(klubList.get(k).getJumlahGoalKalah() + score2[i]);
                            klubList.get(k).setJumlahPoin(klubList.get(k).getJumlahPoin() + point1[i]);
                        }

                        if (club2[i].equals(klubList.get(k).getNamaKlub())) {
                            klubList.get(k).setJumlahMain(klubList.get(k).getJumlahMain() + 1);
                            if (point2[i] == 1) {
                                klubList.get(k).setJumlahSeri(klubList.get(k).getJumlahSeri() + 1);
                            } else if (point2[i] == 3) {
                                klubList.get(k).setJumlahMenang(klubList.get(k).getJumlahMenang() + 1);
                            } else {
                                klubList.get(k).setJumlahKalah(klubList.get(k).getJumlahKalah() + 1);
                            }
                            klubList.get(k).setJumlahGoalMenang(klubList.get(k).getJumlahGoalMenang() + score2[i]);
                            klubList.get(k).setJumlahGoalKalah(klubList.get(k).getJumlahGoalKalah() + score1[i]);
                            klubList.get(k).setJumlahPoin(klubList.get(k).getJumlahPoin() + point2[i]);
                        }
                    }

                    pertandinganList.add(new Pertandingan(club1[i], club2[i]));
                }
            }

            sharedPreferenceHelper.addPertandingan(pertandinganList);
            sharedPreferenceHelper.addClub(klubList);

            Toast.makeText(this, "Berhasil Menambahkan Pertandingan", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });
    }

    private boolean pernahTanding(String[] club1, String[] club2, int position) {
        boolean valid = true;
        for (Pertandingan pertandingan : pertandinganList) {
            if (pertandingan.getClub1().equals(club1[position]) && pertandingan.getClub2().equals(club2[position])) {
                valid = false;
                break;
            }
        }

        return valid;
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}