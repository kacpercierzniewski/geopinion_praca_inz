package com.example.kacper.geopinion;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import org.apache.commons.validator.routines.EmailValidator;

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    private DatabaseManager manager= new DatabaseManager(this);
    private boolean error=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

        public void onSignUpButtonClick(View view) {
        error=false;
        String settings= "0";
        TextView errorText= (TextView)findViewById(R.id.TVerror);
        errorText.setText(""); //czyścimy tekst po ponownym kliknięciu przycisku
        EditText fName= (EditText)findViewById(R.id.TFfName);
        EditText lName= (EditText)findViewById(R.id.TFlName);
        EditText login= (EditText)findViewById(R.id.TFlogin);
        EditText pass= (EditText)findViewById(R.id.TFpass);
        EditText pass2= (EditText)findViewById(R.id.TFpass2);
        EditText email= (EditText)findViewById(R.id.TFemail);
        checkLetters(login,true);
        checkLetters(fName,false);
        checkLetters(lName,false);
        checkNoInputError(fName); //TODO: mogą być nie tylko znaki alfanumeryczne np. nasze polskie ą czy ę
        checkNoInputError(lName);// TODO: to samo co wyżej
        checkNoInputError(login);
        checkNoInputError(pass);
        checkNoInputError(pass2);
        checkNoInputError(email);
        checkPasswordsMatch(pass,pass2);
        checkPasswordSecurity(pass);
        checkEmail(email);


            if(manager.checkIfExists("login",login.getText().toString())){
                login.setError(getString(R.string.loginExists));
                error=true;
            }

            if(manager.checkIfExists("email",email.getText().toString())){
                email.setError(getString(R.string.emailExists));
                error=true;
            }

        if (!error){ 
            makeToast(getString(R.string.accountCreated));
            Log.i("INFO","no errors");
            User user= new User(fName.getText().toString(),lName.getText().toString(),login.getText().toString(),pass.getText().toString(),email.getText().toString(),settings);
            manager.putUserToDB(user);
            Log.i("INFO","reg success");
            Intent myIntent= new Intent(this, MainActivity.class);
            startActivity(myIntent);

            //wkładamy dane do bazy
        }
        }

        private void checkLetters(EditText l, boolean isLogin){
            String lStr= l.getText().toString();
            String regex="^[\\p{L}\\s]+$";

            if (isLogin){
                regex="^[a-zA-Z0-9]+$";
            }
            if (lStr.length()>20){

                l.setError(getString(R.string.max20characters));
                error=true;
            }
            if (!(Pattern.matches(regex,lStr))){
                l.setError(getString(R.string.onlyAlphanumeric));
                error=true;

            }
        }

        private void checkNoInputError(EditText textField) {
            if (textField.getText().toString().equals("")) {
                textField.setError("Pole nie może być puste.");
                error = true;
            }

        }
        private void checkPasswordsMatch(EditText p1, EditText p2){
            String p1str= p1.getText().toString();
            String p2str= p2.getText().toString();

            if (!(p1str.equals(p2str))){
                p2.setError(getString(R.string.differentPasswords));
                error=true;

            }

        }
        private void checkPasswordSecurity(EditText p1){
            String p1str= p1.getText().toString();
            if (p1str.length()<8){
                p1.setError("Hasło jest za krótkie.");
                error=true;
            }
           String  regex="[a-zA-Z0-9]+$";
            if ((Pattern.matches(regex,p1str))){
                p1.setError("Wymagany conajmniej jeden znak specjalny");
                error=true;
            }

        }
        private void checkEmail(EditText m){
            String mStr= m.getText().toString();
            if (!(EmailValidator.getInstance().isValid(mStr))){
                m.setError("Nieprawidłowy email.");
                error=true;
            }
        }


    private void makeToast(String text){
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

    }
        }

