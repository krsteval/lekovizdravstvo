package com.tadi.lekovizdravstvomk.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;

import com.tadi.lekovizdravstvomk.MainActivity;
import com.tadi.lekovizdravstvomk.R;
import com.tadi.lekovizdravstvomk.model.Drug;

public class DrugsAdapter  extends RecyclerView.Adapter<DrugsAdapter.MyViewHolder> {

    private List<Drug> moviesList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView ime, cena, jacina, proizvoditel, generika;

        public MyViewHolder(View view) {
            super(view);
            ime = (TextView) view.findViewById(R.id.ime);
            cena = (TextView) view.findViewById(R.id.cena);
            jacina = (TextView) view.findViewById(R.id.jacina);
            generika = (TextView) view.findViewById(R.id.generika);
            proizvoditel = (TextView) view.findViewById(R.id.proizvoditel);

            ime.setSelected(true);
            cena.setSelected(true);
            generika.setSelected(true);
            proizvoditel.setSelected(true);
        }
    }


    public DrugsAdapter(List<Drug> moviesList) {
        this.moviesList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_drug, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Drug drug = moviesList.get(position);
        holder.ime.setText(drug.getLatinicno_ime());
        holder.cena.setText(drug.getGolemoprodazna_cena().toString() + "(Големопродажна) \n" + drug.getMaloprodazna_cena().toString()+"(Малопродажна)");
        holder.jacina.setText(drug.getJacina());
        holder.proizvoditel.setText(drug.getProizvoditel());
        holder.generika.setText(drug.getFarmacevska_forma());

        holder.ime.setSelected(true);
//        holder.cena.setSelected(true);
        holder.generika.setSelected(true);
        holder.proizvoditel.setSelected(true);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context c = v.getContext();
                ((MainActivity)c).loadFragmentForAction("drug_register_details", drug);
            }
        });
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}