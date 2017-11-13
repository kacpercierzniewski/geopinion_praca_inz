package com.example.kacper.geopinion;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;


public class OpinionsAdapter extends RecyclerView.Adapter<OpinionsAdapter.ViewHolder> {
    OpinionsAdapter(List<OpinionElement> opinionElements) {
        this.opinionElements = opinionElements;
    }

    private List<OpinionElement> opinionElements;

    public  ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){

View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.opinion_item,parent,false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        OpinionElement opinionElement = opinionElements.get(position);

        holder.TVopinionAuthor.setText("Autor: "+opinionElement.getUser_fname()+" "+opinionElement.getUser_lname());
        holder.TVopinionText.setText(opinionElement.getOpinion_text());
        holder.ratingBar.setRating(opinionElement.getOpinion_stars());

    }

    @Override
    public int getItemCount() {
        return opinionElements.size();
    }

     class ViewHolder extends RecyclerView.ViewHolder{
        TextView TVopinionAuthor;
        TextView TVopinionText;
        RatingBar ratingBar;

        ViewHolder(View itemView) {
            super(itemView);
            TVopinionAuthor = itemView.findViewById(R.id.TVtitle);
            TVopinionText= itemView.findViewById(R.id.TVDescription);
            ratingBar=itemView.findViewById(R.id.RBrating);
        }


    }
}
