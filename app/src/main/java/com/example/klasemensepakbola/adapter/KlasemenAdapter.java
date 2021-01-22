package com.example.klasemensepakbola.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.klasemensepakbola.R;
import com.example.klasemensepakbola.model.Klub;

import java.util.ArrayList;
import java.util.List;

public class KlasemenAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Klub> klubList;
    private Context context;

    public KlasemenAdapter(Context context, List<Klub> klubList) {
        this.klubList = new ArrayList<>();
        this.klubList = klubList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View viewItem = inflater.inflate(R.layout.item_table_klasemen, parent, false);
        viewHolder = new ViewHolder(viewItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;

        Klub klub = klubList.get(position);

        viewHolder.tvNo.setText(String.valueOf(position + 1));
        viewHolder.tvKlub.setText(klub.getNamaKlub());
        viewHolder.tvMain.setText(String.valueOf(klub.getJumlahMain()));
        viewHolder.tvMenang.setText(String.valueOf(klub.getJumlahMenang()));
        viewHolder.tvSeri.setText(String.valueOf(klub.getJumlahSeri()));
        viewHolder.tvKalah.setText(String.valueOf(klub.getJumlahKalah()));
        viewHolder.tvGm.setText(String.valueOf(klub.getJumlahGoalMenang()));
        viewHolder.tvGk.setText(String.valueOf(klub.getJumlahGoalKalah()));
        viewHolder.tvPoint.setText(String.valueOf(klub.getJumlahPoin()));
    }

    @Override
    public int getItemCount() {
        return klubList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvNo, tvKlub, tvMain, tvMenang, tvSeri, tvKalah, tvGm, tvGk, tvPoint;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNo = itemView.findViewById(R.id.tvNo);
            tvKlub = itemView.findViewById(R.id.tvKlub);
            tvMain = itemView.findViewById(R.id.tvMain);
            tvMenang = itemView.findViewById(R.id.tvMenang);
            tvSeri = itemView.findViewById(R.id.tvSeri);
            tvKalah = itemView.findViewById(R.id.tvKalah);
            tvGm = itemView.findViewById(R.id.tvGm);
            tvGk = itemView.findViewById(R.id.tvGk);
            tvPoint = itemView.findViewById(R.id.tvPoint);
        }
    }

}
