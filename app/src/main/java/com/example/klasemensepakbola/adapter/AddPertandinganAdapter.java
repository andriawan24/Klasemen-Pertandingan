package com.example.klasemensepakbola.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.klasemensepakbola.R;
import com.example.klasemensepakbola.helper.SharedPreferenceHelper;
import com.example.klasemensepakbola.model.Klub;
import com.example.klasemensepakbola.model.Pertandingan;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AddPertandinganAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private int total;
    private Context context;

    SpinAdapter spinAdapter;

    SharedPreferenceHelper sharedPreferenceHelper;
    List<Klub> klubList = new ArrayList<>();
    List<Pertandingan> pertandinganList = new ArrayList<>();

    String[] club1, club2;
    Integer[] score1, score2;

    public AddPertandinganAdapter(Context context, int total) {
        this.context = context;
        this.total = total;
        this.club1 = new String[total];
        this.club2 = new String[total];
        this.score1 = new Integer[total];
        this.score2 = new Integer[total];
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        sharedPreferenceHelper = new SharedPreferenceHelper(context);
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

        spinAdapter = new SpinAdapter(context, android.R.layout.simple_spinner_item, klubList);

        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View viewItem = inflater.inflate(R.layout.item_add_pertandingan, parent, false);
        viewHolder = new ViewHolder(viewItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;

        viewHolder.spinnerClubSatu.setAdapter(spinAdapter);
        viewHolder.spinnerClubDua.setAdapter(spinAdapter);

        viewHolder.spinnerClubSatu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int positions, long id) {
                 club1[position] = klubList.get(positions).getNamaKlub();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        viewHolder.spinnerClubDua.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int positions, long id) {
                 club2[position] = klubList.get(positions).getNamaKlub();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        viewHolder.scoreClubSatu.setText("0");
        viewHolder.scoreClubDua.setText("0");
        viewHolder.scoreClubSatu.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().isEmpty()) {
                    score1[position] = Integer.parseInt(viewHolder.scoreClubSatu.getText().toString());
                }
            }
        });

        viewHolder.scoreClubDua.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().isEmpty()) {
                    score2[position] = Integer.parseInt(viewHolder.scoreClubDua.getText().toString());
                }
            }
        });

        score1[position] = Integer.parseInt(viewHolder.scoreClubSatu.getText().toString());
        score2[position] = Integer.parseInt(viewHolder.scoreClubDua.getText().toString());
    }

    @Override
    public int getItemCount() {
        return total;
    }

    public String[] getClub1() {
        return club1;
    }

    public String[] getClub2() {
        return club2;
    }

    public Integer[] getScore1() {
        return score1;
    }

    public Integer[] getScore2() {
        return score2;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        Spinner spinnerClubSatu, spinnerClubDua;
        EditText scoreClubSatu, scoreClubDua;
        Button btnSave;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            spinnerClubSatu = itemView.findViewById(R.id.spinnerClubSatu);
            spinnerClubDua = itemView.findViewById(R.id.spinnerClubDua);
            scoreClubSatu = itemView.findViewById(R.id.scoreClubSatu);
            scoreClubDua = itemView.findViewById(R.id.scoreClubDua);
            btnSave = itemView.findViewById(R.id.btnSave);
        }
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
}
