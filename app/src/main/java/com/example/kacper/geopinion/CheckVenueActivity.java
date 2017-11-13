package com.example.kacper.geopinion;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import com.orhanobut.hawk.Hawk;

public class CheckVenueActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_venue);
        Log.i("VENUE ID: ", String.valueOf(Hawk.get("venue_id")));
    }
}
