package com.example.kacper.geopinion;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by kacper on 21.10.17 for Praca Inżynierska.
 */

class DatabaseManager extends SQLiteOpenHelper{
    private static final int DATABASE_VERSION=2;
    private static final String DATABASE_NAME="geopinion";
    private static final String TABLE_NAME="users";
    private static final String COLUMN_ID="id";
    private static final String COLUMN_FNAME="fname";
    private static final String COLUMN_LNAME="lname";
    private static final String COLUMN_LOGIN="login";
    private static final String COLUMN_PASS="pass";
    private static final String COLUMN_EMAIL="email";
    private static final String COLUMN_SETTINGS="settings";
    private static final String TABLE_CREATE="create table users (id integer primary key not null ,"+"fname text not null , lname text not null , login text not null , pass text not null , email text not null , settings text not null);";
    private SQLiteDatabase db;
    DatabaseManager(Context context) {
        super(context, DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
        this.db=db;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = " DROP TABLE IF EXISTS "+ TABLE_NAME;
        db.execSQL(query);
        this.onCreate(db);

    }

    void putUserToDB(User u) {
        db = this.getWritableDatabase();
        ContentValues values= new ContentValues(); //z tego co rozumiem to pomaga we wkładaniu rzeczy do bazy
        values.put(COLUMN_FNAME,u.getfName());
        values.put(COLUMN_LNAME,u.getlName());
        values.put(COLUMN_LOGIN,u.getLogin());
        values.put(COLUMN_PASS,u.getPassword());
        values.put(COLUMN_EMAIL,u.getMail());
        values.put(COLUMN_SETTINGS,u.getSettings());
        db.insert(TABLE_NAME,null,values);
        db.close(); //ZAWSZE ZAMYKAMY BAZĘ PO INSERCIE !
    }
    boolean checkIfExists(String column,String s){
        db=this.getReadableDatabase();
        String query="select * from "+TABLE_NAME+" where "+column+"='"+s+"'";
        Cursor cursor= db.rawQuery(query,null);
        Log.i("GETCOUNT",String.valueOf(cursor.getCount()));
        Log.i("GETCOLUMNCOUNT",String.valueOf(cursor.getColumnCount()));
        if (cursor.getCount()>0){
            cursor.close();
            return true;

        }        else return false;



    }
    String searchPass(String u){
            db =this.getReadableDatabase();
        String query= "select login, pass from "+TABLE_NAME;
        Cursor cursor= db.rawQuery(query,null);
        String login, pass;
        pass="wrong";
        if(cursor.moveToFirst()){

            do{

                login=cursor.getString(0);

                if(login.equals(u)){
                    pass=cursor.getString(1);
                    break;
                }

            }
                while(cursor.moveToNext());

        }
        cursor.close();
        db.close();
        return pass;
    }



}
