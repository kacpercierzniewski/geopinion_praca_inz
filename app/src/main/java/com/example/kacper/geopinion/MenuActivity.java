package com.example.kacper.geopinion;

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
        Log.i("LOGIN",bundle.getString("login"));
    }
    public void onButtonClick(View v){
        if (v.getId()==R.id.Bmap){
            Intent intent = new Intent(this,MapActivity.class);
            startActivity(intent);


        }


    }
}
