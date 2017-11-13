package com.example.kacper.geopinion;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CheckVenuesActivity extends AppCompatActivity {
    private DatabaseManager manager = new DatabaseManager(this);
    private List<VenueElement> venues = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_venues);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        manager.getVenues(venues);
        RecyclerView.Adapter adapter = new VenuesAdapter(venues, this);
        recyclerView.setAdapter(adapter);

    }

    public void onButtonClick(View v) {

        }

    }


