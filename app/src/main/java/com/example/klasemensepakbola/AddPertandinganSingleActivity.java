package com.example.klasemensepakbola;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.klasemensepakbola.helper.SharedPreferenceHelper;
import com.example.klasemensepakbola.model.Klub;
import com.example.klasemensepakbola.model.Pertandingan;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AddPertandinganSingleActivity extends AppCompatActivity {

    Spinner spinnerClubSatu, spinnerClubDua;
    EditText scoreClubSatu, scoreClubDua;
    Button btnSave;

    SharedPreferenceHelper sharedPreferenceHelper;
    List<Klub> klubList = new ArrayList<>();
    List<Pertandingan> pertandinganList = new ArrayList<>();

    String club1, club2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pertandingan_single);

        sharedPreferenceHelper = new SharedPreferenceHelper(this);

        spinnerClubSatu = findViewById(R.id.spinnerClubSatu);
        spinnerClubDua = findViewById(R.id.spinnerClubDua);
        scoreClubSatu = findViewById(R.id.scoreClubSatu);
        scoreClubDua = findViewById(R.id.scoreClubDua);
        btnSave = findViewById(R.id.btnSave);

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

        SpinAdapter spinAdapter = new SpinAdapter(this, android.R.layout.simple_spinner_item, klubList);

        spinnerClubSatu.setAdapter(spinAdapter);
        spinnerClubDua.setAdapter(spinAdapter);

        spinnerClubSatu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                club1 = klubList.get(position).getNamaKlub();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerClubDua.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                club2 = klubList.get(position).getNamaKlub();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnSave.setOnClickListener(v -> {
            if (club1.equals(club2)) {
                Toast.makeText(this, "Club tidak boleh sama", Toast.LENGTH_SHORT).show();
            } else if (!pernahTanding()) {
                Toast.makeText(this, "Kedua klub sudah pernah bertanding", Toast.LENGTH_SHORT).show();
            } else if (scoreClubSatu.getText().toString().isEmpty()) {
                Toast.makeText(this, "Score klub " + club1 + " tidak boleh kosong", Toast.LENGTH_SHORT).show();
            } else if (scoreClubDua.getText().toString().isEmpty()) {
                Toast.makeText(this, "Score klub " + club2 + " tidak boleh kosong", Toast.LENGTH_SHORT).show();
            } else {
                int point1, point2;

                if (Integer.parseInt(scoreClubSatu.getText().toString()) == Integer.parseInt(scoreClubDua.getText().toString())) {
                    point1 = 1;
                    point2 = 1;
                } else {
                    if (Integer.parseInt(scoreClubSatu.getText().toString()) > Integer.parseInt(scoreClubDua.getText().toString())) {
                        point1 = 3;
                        point2 = 0;
                    } else {
                        point1 = 0;
                        point2 = 3;
                    }
                }


                for (int i = 0; i < klubList.size(); i++) {
                    if (club1.equals(klubList.get(i).getNamaKlub())) {
                        klubList.get(i).setJumlahMain(klubList.get(i).getJumlahMain() + 1);
                        if (point1 == 1) {
                            klubList.get(i).setJumlahSeri(klubList.get(i).getJumlahSeri() + 1);
                        } else if (point1 == 3) {
                            klubList.get(i).setJumlahMenang(klubList.get(i).getJumlahMenang() + 1);
                        } else {
                            klubList.get(i).setJumlahKalah(klubList.get(i).getJumlahKalah() + 1);
                        }
                        klubList.get(i).setJumlahGoalMenang(klubList.get(i).getJumlahGoalMenang() + Integer.parseInt(scoreClubSatu.getText().toString()));
                        klubList.get(i).setJumlahGoalKalah(klubList.get(i).getJumlahGoalKalah() + Integer.parseInt(scoreClubDua.getText().toString()));
                        klubList.get(i).setJumlahPoin(klubList.get(i).getJumlahPoin() + point1);
                    }

                    if (club2.equals(klubList.get(i).getNamaKlub())) {
                        klubList.get(i).setJumlahMain(klubList.get(i).getJumlahMain() + 1);
                        if (point2 == 1) {
                            klubList.get(i).setJumlahSeri(klubList.get(i).getJumlahSeri() + 1);
                        } else if (point2 == 3) {
                            klubList.get(i).setJumlahMenang(klubList.get(i).getJumlahMenang() + 1);
                        } else {
                            klubList.get(i).setJumlahKalah(klubList.get(i).getJumlahKalah() + 1);
                        }
                        klubList.get(i).setJumlahGoalMenang(klubList.get(i).getJumlahGoalMenang() + Integer.parseInt(scoreClubDua.getText().toString()));
                        klubList.get(i).setJumlahGoalKalah(klubList.get(i).getJumlahGoalKalah() + Integer.parseInt(scoreClubSatu.getText().toString()));
                        klubList.get(i).setJumlahPoin(klubList.get(i).getJumlahPoin() + point2);
                    }
                }

                pertandinganList.add(new Pertandingan(club1, club2));
                sharedPreferenceHelper.addPertandingan(pertandinganList);
                sharedPreferenceHelper.addClub(klubList);
                Toast.makeText(this, "Berhasil Menambahkan Pertandingan", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, MainActivity.class));
                finish();
            }
        });
    }

    private boolean pernahTanding() {
        boolean valid = true;
        for (Pertandingan pertandingan : pertandinganList) {
            if (pertandingan.getClub1().equals(club1) && pertandingan.getClub2().equals(club2)) {
                valid = false;
                break;
            }
        }

        return valid;
    }

    public class SpinAdapter extends ArrayAdapter<Klub> {

        // Your sent context
        private Context context;
        // Your custom values for the spinner (User)
        private List<Klub> values;

        public SpinAdapter(Context context, int textViewResourceId,
                           List<Klub> values) {
            super(context, textViewResourceId, values);
            this.context = context;
            this.values = values;
        }

        @Override
        public int getCount() {
            return values.size();
        }

        @Override
        public Klub getItem(int position) {
            return values.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }


        // And the "magic" goes here
        // This is for the "passive" state of the spinner
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // I created a dynamic TextView here, but you can reference your own  custom layout for each spinner item
            TextView label = (TextView) super.getView(position, convertView, parent);
            label.setTextColor(Color.BLACK);
            // Then you can get the current item using the values array (Users array) and the current position
            // You can NOW reference each method you has created in your bean object (User class)
            label.setText(values.get(position).getNamaKlub());

            // And finally return your dynamic (or custom) view for each spinner item
            return label;
        }

        // And here is when the "chooser" is popped up
        // Normally is the same view, but you can customize it if you want
        @Override
        public View getDropDownView(int position, View convertView,
                                    ViewGroup parent) {
            TextView label = (TextView) super.getDropDownView(position, convertView, parent);
            label.setTextColor(Color.BLACK);
            label.setText(values.get(position).getNamaKlub());

            return label;
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}