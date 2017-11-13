package com.example.kacper.geopinion;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import com.orhanobut.hawk.Hawk;

import java.util.List;

public class CheckOpinionsActivity extends AppCompatActivity {
private DatabaseManager manager= new DatabaseManager(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_opinions);
        String venue_id=Hawk.get("venue_id");
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerViewForCheckOpinions);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<OpinionElement> opinions=manager.getOpinionDetalis(venue_id);
        RecyclerView.Adapter adapter = new OpinionsAdapter(opinions);
        recyclerView.setAdapter(adapter);

    }
}
