package com.example.klasemensepakbola;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

public class InputDataActivity extends BaseActivity {

    List<Klub> klubList = new ArrayList<>();
    SharedPreferenceHelper sharedPreferenceHelper;

    TextInputEditText etNamaKlub, etKotaKlub;
    Button btnSave;

    @Override
    protected void onCreateStuff() {
        setContentView(R.layout.activity_input_data);
    }

    @Override
    protected void initUi() {
        sharedPreferenceHelper = new SharedPreferenceHelper(this);

        getData();

        etKotaKlub = findViewById(R.id.etKotaKlub);
        etNamaKlub = findViewById(R.id.etClubName);
        btnSave = findViewById(R.id.btnSave);
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

    private void getData() {
        if (!sharedPreferenceHelper.getClub().isEmpty()) {
            Gson gson = new Gson();
            String klubListString = sharedPreferenceHelper.getClub();
            Type type = new TypeToken<List<Klub>>(){}.getType();
            klubList = gson.fromJson(klubListString, type);
        }
    }

    @Override
    protected void initListener() {
        btnSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnSave) {
            if (etNamaKlub.getText().toString().isEmpty()) {
                showToast(this, "Nama Klub tidak boleh kosong");
            } else if (etKotaKlub.getText().toString().isEmpty()) {
                showToast(this, "Kota Klub tidak boleh kosong");
            } else if (!containSameNameClub()) {
                showToast(this, "Nama Klub sudah ada");
            } else {
                showToast(this, "Berhasil menambahkan klub");
                klubList.add(new Klub(etNamaKlub.getText().toString(), etKotaKlub.getText().toString(), 0, 0, 0, 0, 0, 0, 0));
                sharedPreferenceHelper.addClub(klubList);
                startActivity(new Intent(this, MainActivity.class));
                finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}