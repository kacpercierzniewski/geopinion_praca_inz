package com.example.kacper.geopinion;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import com.orhanobut.hawk.Hawk;

import java.util.List;


public class VenuesAdapter extends RecyclerView.Adapter<VenuesAdapter.ViewHolder> {
    VenuesAdapter(List<VenueElement> venueElements, Context ctx) {
        this.venueElements = venueElements;
        this.ctx=ctx;
    }

    private List<VenueElement> venueElements;
    private Context ctx;

    public  ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){

View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.venue_item,parent,false);
        return new ViewHolder(view,ctx);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        VenueElement venueElement = venueElements.get(position);

        holder.textViewHead.setText(venueElement.getVenue_name());
        holder.textViewDesc.setText("Kategorie: "+venueElement.getVenue_category()+ "\n\nIlość Opinii:"+venueElement.getQuantityOfOpinions());
        holder.ratingBar.setRating(venueElement.getAvg_stars());
        holder.id.setText(String.valueOf(venueElement.getVenue_id()));

    }

    @Override
    public int getItemCount() {
        return venueElements.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView textViewHead;
        TextView textViewDesc;
        RatingBar ratingBar;
        TextView id;
        Context ctx;

        ViewHolder(View itemView, Context ctx) {
            super(itemView);
            itemView.setOnClickListener(this);
            this.ctx=ctx;
            textViewHead= itemView.findViewById(R.id.TVtitle);
            textViewDesc= itemView.findViewById(R.id.TVDescription);
            ratingBar=itemView.findViewById(R.id.RBrating);
            id=itemView.findViewById(R.id.TVid);
        }

        @Override
        public void onClick(View v) {
            Hawk.put("venue_id",id.getText());
            Log.i("VENUE ID:",String.valueOf(Hawk.get("venue_id")));
            Intent intent= new Intent(this.ctx,CheckOpinionsActivity.class);
                   this.ctx.startActivity(intent);



        }
    }
}
