package com.example.kacper.geopinion;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void onButtonClick(View view) {
        if(view.getId()==R.id.BsignUp){
            Intent myIntent = new Intent(this, RegisterActivity.class);
            startActivity(myIntent);
        }
        if (view.getId()==R.id.BsignIn){
                                //-TO-DO
            //logowanko
        }
    }
}
