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

class DatabaseManager extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 10;
    private static final String DATABASE_NAME = "geopinion";
    private static final String USER_TABLE_NAME = "users";
    private static final String VENUE_TABLE_NAME = "venue";
    private static final String OPINION_TABLE_NAME = "opinion";

    private static final String COLUMN_ID = "id";
    private static final String USER_FNAME = "fname";
    private static final String USER_LNAME = "lname";
    private static final String USER_LOGIN = "login";
    private static final String USER_PASS = "pass";
    private static final String USER_EMAIL = "email";
    private static final String USER_SETTINGS = "settings";
    private static final String USER_TABLE_CREATE = "create table users (user_id integer primary key not null ," + "fname text not null , lname text not null , login text not null , pass text not null , email text not null , settings text not null);";
    private static final String VENUE_TABLE_CREATE = "create table venues (venue_id integer primary key not null, average_stars float not null, sum_stars not null);";
    private static final String OPINION_TABLE_CREATE = "create table opinions (opinion_id integer primary key not null,user_id integer not null, venue_id integer not null, text text not null,stars integer not null,FOREIGN KEY(user_id) REFERENCES users(user_id),FOREIGN KEY(venue_id) REFERENCES venues(venue_id));";
    private SQLiteDatabase db;

    DatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(USER_TABLE_CREATE);
        db.execSQL(VENUE_TABLE_CREATE);
        db.execSQL(OPINION_TABLE_CREATE);
        this.db = db;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = " DROP TABLE IF EXISTS " + USER_TABLE_NAME;
        String query2 = " DROP TABLE IF EXISTS " + VENUE_TABLE_NAME;
        String query3 = " DROP TABLE IF EXISTS " + OPINION_TABLE_NAME;

        db.execSQL(query);
        db.execSQL(query2);
        db.execSQL(query3);
        onCreate(db);

    }

    void putUserToDB(User u) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues(); //z tego co rozumiem to pomaga we wkładaniu rzeczy do bazy
        values.put(USER_FNAME, u.getfName());
        values.put(USER_LNAME, u.getlName());
        values.put(USER_LOGIN, u.getLogin());
        values.put(USER_PASS, u.getPassword());
        values.put(USER_EMAIL, u.getMail());
        values.put(USER_SETTINGS, u.getSettings());
        db.insert(USER_TABLE_NAME, null, values);
        db.close(); //ZAWSZE ZAMYKAMY BAZĘ PO INSERCIE !
    }

    boolean checkIfExists(String column, String s) {
        db = this.getReadableDatabase();
        String query = "select * from " + USER_TABLE_NAME + " where " + column + "='" + s + "'";
        Cursor cursor = db.rawQuery(query, null);
        Log.i("GETCOUNT", String.valueOf(cursor.getCount()));
        Log.i("GETCOLUMNCOUNT", String.valueOf(cursor.getColumnCount()));
        if (cursor.getCount() > 0) {
            cursor.close();
            Log.i("ERROR", s + " istnieje");
            return true;
        } else return false;


    }

    Integer getUserId(String login) {
        db = this.getReadableDatabase();
        String query = "select user_id from" + USER_TABLE_NAME + "where login=" + login;
        Cursor cursor = db.rawQuery(query, null);
        int user_id = cursor.getInt(0);
        cursor.close();
        return user_id;


    }

    boolean checkPass(String u, String p) {
        db = this.getReadableDatabase();
        String query = "select login, pass from " + USER_TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        String login = null;
        if (cursor.moveToFirst()) {

            do {

                login = cursor.getString(0);

                if (login.equals(u)) {
                    if (cursor.getString(1).equals(p)) {
                        cursor.close();
                        db.close();
                        return true;
                    } else {
                        cursor.close();
                        db.close();
                        return false;
                    }
                }

            }
            while (cursor.moveToNext());

            return false;
        }
return false;
    }
}
