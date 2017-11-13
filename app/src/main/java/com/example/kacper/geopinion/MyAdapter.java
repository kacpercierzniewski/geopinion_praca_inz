package com.example.kacper.geopinion;

import android.content.Context;
import android.media.Rating;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    public MyAdapter(List<VenueElement> venueElements, Context context) {
        this.venueElements = venueElements;
        this.context = context;
    }

    List<VenueElement> venueElements;
    private Context context;
    public  ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){

View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        VenueElement venueElement = venueElements.get(position);

        holder.textViewHead.setText(venueElement.getVenue_name());
        holder.textViewDesc.setText("Kategorie: "+venueElement.getVenue_category()+ "\n   Ilość Opinii:"+venueElement.getQuantityOfOpinions());
        holder.ratingBar.setRating(venueElement.getAvg_stars());
        Log.i("RATING: ",String.valueOf(venueElement.getAvg_stars()));
        Log.i("RATING BAR: ",String.valueOf(holder.ratingBar.getRating()));

    }

    @Override
    public int getItemCount() {
        return venueElements.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView textViewHead;
        public TextView textViewDesc;
        RatingBar ratingBar;


        public ViewHolder(View itemView) {
            super(itemView);

            textViewHead= itemView.findViewById(R.id.textViewHead);
            textViewDesc= itemView.findViewById(R.id.textViewDesc);
            ratingBar=itemView.findViewById(R.id.ratingBar2);
        }
    }
}
