package com.example.klasemensepakbola;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.example.klasemensepakbola.helper.SharedPreferenceHelper;
import com.example.klasemensepakbola.model.Klub;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class InputDataActivity extends AppCompatActivity {

    List<Klub> klubList = new ArrayList<>();
    SharedPreferenceHelper sharedPreferenceHelper;

    TextInputEditText etNamaKlub, etKotaKlub;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_data);

        sharedPreferenceHelper = new SharedPreferenceHelper(this);

        if (!sharedPreferenceHelper.getClub().isEmpty()) {
            Gson gson = new Gson();
            String klubListString = sharedPreferenceHelper.getClub();
            Type type = new TypeToken<List<Klub>>(){}.getType();
            klubList = gson.fromJson(klubListString, type);
        }

        etKotaKlub = findViewById(R.id.etKotaKlub);
        etNamaKlub = findViewById(R.id.etClubName);
        btnSave = findViewById(R.id.btnSave);

        btnSave.setOnClickListener(v -> {
            if (etNamaKlub.getText().toString().isEmpty()) {
                Toast.makeText(this, "Nama Klub tidak boleh kosong", Toast.LENGTH_SHORT).show();
            } else if (etKotaKlub.getText().toString().isEmpty()) {
                Toast.makeText(this, "Kota Klub tidak boleh kosong", Toast.LENGTH_SHORT).show();
            } else if (!containSameNameClub()) {
                Toast.makeText(this, "Nama Klub sudah ada", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Berhasil menambahkan klub", Toast.LENGTH_SHORT).show();
                klubList.add(new Klub(etNamaKlub.getText().toString(), etKotaKlub.getText().toString(), 0, 0, 0, 0, 0, 0, 0));
                sharedPreferenceHelper.addClub(klubList);
                startActivity(new Intent(this, MainActivity.class));
                finish();
            }
        });
    }

    private boolean containSameNameClub() {
        boolean valid = true;
        if (!klubList.isEmpty()) {
            for (Klub klub : klubList) {
                if (klub.getNamaKlub().equals(etNamaKlub.getText().toString())) {
                    valid = false;
                }
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