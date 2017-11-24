package com.example.kacper.geopinion;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.orhanobut.hawk.Hawk;
import com.scottyab.rootbeer.RootBeer;

public class MainActivity extends AppCompatActivity {
    private DatabaseManager manager= new DatabaseManager(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (isRooted()) {
            RootCheckerDialog rootCheckerDialog = new RootCheckerDialog();
            rootCheckerDialog.show(getFragmentManager(), "MyDialog");
        }
        }
boolean isRooted(){

    RootBeer rootBeer = new RootBeer(getApplicationContext());
    return rootBeer.isRooted();

}
    public void onButtonClick(View view) {
        if (view.getId()==R.id.BnoRoot){

            Log.i("CICKED","!");
            finish();


        }
        if(view.getId()==R.id.BsignUp){
            Intent myIntent = new Intent(this, RegisterActivity.class);
            startActivity(myIntent);
        }
        if (view.getId()==R.id.BsignIn){
            EditText login = (EditText) findViewById(R.id.TFlogin);
            String loginStr= login.getText().toString();
            EditText pass = (EditText) findViewById(R.id.TFpass);
            String passStr= pass.getText().toString();


            Log.i("VARIABLE: passStr",passStr);

            if (manager.checkPass(loginStr,passStr)){
                Log.i("SUCCESS","hasło się zgadza");
                Hawk.init(getApplicationContext()).build();
                Hawk.put("login",loginStr);
                Hawk.put("user_id",manager.getUserId(loginStr));
                Intent mainActivity = new Intent(this, MenuActivity.class);
                startActivity(mainActivity);

            }
            else
            {
                makeToast(getString(R.string.wrongCredentials));
                Log.i("FAILED","hasło się nie zgadza");


            }
        }
    }
    private void makeToast(String text){
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

    }

}
