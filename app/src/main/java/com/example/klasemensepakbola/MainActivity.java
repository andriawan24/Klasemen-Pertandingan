package com.example.klasemensepakbola;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.Toast;

import com.example.klasemensepakbola.adapter.KlasemenAdapter;
import com.example.klasemensepakbola.helper.SharedPreferenceHelper;
import com.example.klasemensepakbola.model.Klub;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    SharedPreferenceHelper sharedPreferenceHelper;
    List<Klub> klubList = new ArrayList<>();
    Button btnAddData, btnAddPertandingan;
    RecyclerView rvKlasemen;
    KlasemenAdapter klasemenAdapter;

    TableRow emptyTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferenceHelper = new SharedPreferenceHelper(this);

        if (!sharedPreferenceHelper.getClub().isEmpty()) {
            Gson gson = new Gson();
            String klubListString = sharedPreferenceHelper.getClub();
            Type type = new TypeToken<List<Klub>>(){}.getType();
            klubList = gson.fromJson(klubListString, type);

            Log.e("Main", "onCreate: " + klubList);
        }

        emptyTable = findViewById(R.id.emptyTable);

        klasemenAdapter = new KlasemenAdapter(this, klubList);
        rvKlasemen = findViewById(R.id.rvKlub);

        rvKlasemen.setLayoutManager(new LinearLayoutManager(this));
        rvKlasemen.setAdapter(klasemenAdapter);

        if (klubList.size() == 0) {
            emptyTable.setVisibility(View.VISIBLE);
        } else {
            emptyTable.setVisibility(View.GONE);
        }

        btnAddData = findViewById(R.id.btnAddData);
        btnAddData.setOnClickListener(v -> {
            startActivity(new Intent(this, InputDataActivity.class));
            finish();
        });

        btnAddPertandingan = findViewById(R.id.btnAddPertandingan);
        btnAddPertandingan.setOnClickListener(v -> {
            if (klubList.size() == 0) {
                Toast.makeText(this, "Klub belum tersedia, silahkan tambah klub", Toast.LENGTH_SHORT).show();
            } else {
                String[] choices = {"Single", "Multiple"};

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Pilih cara menambahkan");
                builder.setItems(choices, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            startActivity(new Intent(MainActivity.this, AddPertandinganSingleActivity.class));
                            finish();
                        } else if (which == 1){
                            startActivity(new Intent(MainActivity.this, AddPertandinganMultiple.class));
                            finish();
                        }
                    }
                });
                builder.show();
            }
        });
    }
}