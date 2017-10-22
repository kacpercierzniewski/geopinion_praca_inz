package com.example.kacper.geopinion;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import org.apache.commons.validator.routines.EmailValidator;

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    boolean error=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

    }

    public void onSignUpButtonClick(View view) {

        TextView errorText= (TextView)findViewById(R.id.TVerror);
        errorText.setText(""); //czyścimy tekst po ponownym kliknięciu przycisku
        EditText fName= (EditText)findViewById(R.id.TFfName);
        EditText lName= (EditText)findViewById(R.id.TFlName);
        EditText login= (EditText)findViewById(R.id.TFlogin);
        EditText pass= (EditText)findViewById(R.id.TFpass);
        EditText pass2= (EditText)findViewById(R.id.TFpass2);
        EditText email= (EditText)findViewById(R.id.TFemail);
        checkAlphanumeric(login);
        checkAlphanumeric(fName);
        checkAlphanumeric(lName);
        checkNoInputError(fName);
        checkNoInputError(lName);
        checkNoInputError(login);
        checkNoInputError(pass);
        checkNoInputError(pass2);
        checkNoInputError(email);
        checkPasswordsMatch(pass,pass2);
        checkPasswordSecurity(pass);
        checkEmail(email);
        }
        void checkAlphanumeric(EditText l){
            String lStr= l.getText().toString();
            String regex="^[a-zA-Z0-9]+$";
            if (lStr.length()>20){

                l.setError(getString(R.string.max20characters));
            }
            if (!(Pattern.matches(regex,lStr))){
                l.setError(getString(R.string.onlyAlphanumeric));

            }

        }
        void checkNoInputError(EditText textField) {
            if (textField.getText().toString().equals("")) {
                textField.setError("Pole nie może być puste.");
                error = true;
            }

        }
        void checkPasswordsMatch(EditText p1, EditText p2){
            String p1str= p1.getText().toString();
            String p2str= p2.getText().toString();

            if (!(p1str.equals(p2str))){
                p2.setError(getString(R.string.differentPasswords));
                error=true;

            }

        }
        void checkPasswordSecurity(EditText p1){
            String p1str= p1.getText().toString();
            if (p1str.length()<8){
                p1.setError("Hasło jest za krótkie.");
            }
        }
        void checkEmail(EditText m){
            String mStr= m.getText().toString();
            if (!(EmailValidator.getInstance().isValid(mStr))){
                m.setError("Nieprawidłowy email.");
            }


        }

        }

