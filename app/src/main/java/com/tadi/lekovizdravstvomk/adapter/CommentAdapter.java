package com.tadi.lekovizdravstvomk.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tadi.lekovizdravstvomk.MainActivity;
import com.tadi.lekovizdravstvomk.R;
import com.tadi.lekovizdravstvomk.model.Drug;
import com.tadi.lekovizdravstvomk.model.ReviewForDrugs;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.MyViewHolder> {

    private List<ReviewForDrugs> comments;
    public boolean isGridLayout;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView  ime,dateCreated, reviewBody;
        public RelativeLayout layoutCena, layoutJacina;
        public MyViewHolder(View view) {
            super(view);
            ime = (TextView) view.findViewById(R.id.ime);
            dateCreated = (TextView) view.findViewById(R.id.dateCreated);
            reviewBody = (TextView) view.findViewById(R.id.message);
//            ime.setSelected(true);
            ime.setSelected(true);
            reviewBody.setSelected(true);
        }
    }

    public void customNotifyDataSetChanged(boolean isGridLayout){
        this.isGridLayout = isGridLayout;
        notifyDataSetChanged();
    }
    public CommentAdapter(List<ReviewForDrugs> comments) {
        this.comments = comments;
        this.isGridLayout = true;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_comment, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final ReviewForDrugs reviewForDrugs = comments.get(position);
        holder.ime.setText(reviewForDrugs.getIdUser());
        holder.dateCreated.setText(reviewForDrugs.getDate().toString());
        holder.reviewBody.setText(reviewForDrugs.getReviewBody());


        holder.ime.setSelected(true);
//        holder.cena.setSelected(true);
        holder.dateCreated.setSelected(true);
 
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }
}