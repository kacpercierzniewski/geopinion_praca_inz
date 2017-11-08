package com.example.kacper.geopinion;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.orhanobut.hawk.Hawk;


public class OpinionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opinion);
        Bundle extras= getIntent().getExtras();
        Log.i("VENUE ID:",extras.getString("venue_id"));
        Log.i("VENUE NAME:",extras.getString("venue_name"));
        Log.i("USER ID:", String.valueOf(Hawk.get("login")));

    }

}
