package com.tadi.lekovizdravstvomk.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;
import com.tadi.lekovizdravstvomk.R;
import com.tadi.lekovizdravstvomk.model.Drug;

public class DrugsAdapter  extends RecyclerView.Adapter<DrugsAdapter.MyViewHolder> {

    private List<Drug> moviesList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView latinicno_ime, ime, jacina;

        public MyViewHolder(View view) {
            super(view);
            latinicno_ime = (TextView) view.findViewById(R.id.latinicno_ime);
            ime = (TextView) view.findViewById(R.id.ime);
            jacina = (TextView) view.findViewById(R.id.jacina);
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
        Drug movie = moviesList.get(position);
        holder.latinicno_ime.setText(movie.getLatinicno_ime());
        holder.ime.setText(movie.getIme());
        holder.jacina.setText(movie.getJacina());
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}