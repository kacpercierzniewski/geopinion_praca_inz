package com.example.kacper.geopinion;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {
private DatabaseManager manager= new DatabaseManager(this);
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
            EditText login= (EditText)findViewById(R.id.TFlogin);
            String loginStr= login.getText().toString();
            EditText pass= (EditText)findViewById(R.id.TFpass);
            String passStr= pass.getText().toString();

            String password= manager.searchPass(loginStr); //sprawdzamy jakie mamy hasło dla danego loginu

            Log.i("VARIABLE: password",password);
            Log.i("VARIABLE: passStr",passStr);

            if (passStr.equals(password)){
                Log.i("SUCCESS","hasło się zgadza");
                Intent mainActivity = new Intent(this, MenuActivity.class);
                mainActivity.putExtra("login",loginStr);
                startActivity(mainActivity);

            }
            else
            {Log.i("FAILED","hasło się nie zgadza");

            }
        }
    }
}
