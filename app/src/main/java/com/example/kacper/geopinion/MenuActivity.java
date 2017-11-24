package com.example.kacper.geopinion;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MenuActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void onButtonClick(View v){
        if (v.getId()==R.id.Bmap){
            Intent intent = new Intent(this,MapActivity.class);
            startActivity(intent);
        }
        if (v.getId()==R.id.Bopinions){
            Log.i("ACTIVITY STARTED","!");
            Intent intent = new Intent(this,CheckVenuesActivity.class);
            startActivity(intent);

        }

    }

    }

