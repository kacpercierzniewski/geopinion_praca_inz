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
        Bundle bundle = getIntent().getExtras();
        checkRoot();
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
    private void checkRoot(){
        Context context= getApplicationContext();
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setMessage("Write your message here.");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }


    }

