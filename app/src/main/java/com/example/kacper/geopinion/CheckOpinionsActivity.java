package com.example.kacper.geopinion;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.List;

public class CheckOpinionsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private  RecyclerView.Adapter adapter;
    private DatabaseManager manager= new DatabaseManager(this);
    private List listItems;
    List<VenueElement> venues= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_opinion);

        recyclerView= (RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        manager.getVenues(venues);
        adapter = new MyAdapter(venues,this);
        recyclerView.setAdapter(adapter);

    }

    public void onButtonClick(View v){
         //   if(v.getId()==R.id.list_item){
          //   Hawk.put("venue_id",venues.get(adapter.getItemCount()).getVenue_id());
        //     Intent intent= new Intent(this, CheckVenueActivity.class);
        //     startActivity(intent);
       //
Log.i("CLICKED",String.valueOf(v.getId()));

    }

}
