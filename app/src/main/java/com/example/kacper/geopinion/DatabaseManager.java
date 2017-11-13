package com.example.kacper.geopinion;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kacper on 21.10.17 for Praca Inżynierska.
 */

class DatabaseManager extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 18;
    private static final String DATABASE_NAME = "geopinion";

    private static final String USER_TABLE_NAME = "users";
    private static final String VENUE_TABLE_NAME = "venues";
    private static final String OPINION_TABLE_NAME = "opinions";

    private static final String USER_FNAME = "fname";
    private static final String USER_LNAME = "lname";
    private static final String USER_LOGIN = "login";
    private static final String USER_PASS = "pass";
    private static final String USER_EMAIL = "email";
    private static final String USER_SETTINGS = "settings";

    private static final String OPINION_USERID= "user_id";
    private static final String OPINION_VENUEID= "venue_api_id";
    private static final String OPINION_TEXT= "text";
    private static final String OPINION_STARS= "stars";

    private static final String VENUE_API_ID= "venue_api_id";
    private static final String VENUE_AVERAGESTARS= "average_stars";
    private static final String VENUE_SUMSTARS= "sum_stars";
    private static final String VENUE_CATEGORY= "photo_url";
 //   private static final String VENUE_PHOTO_URL= "category";

    private static final String USER_TABLE_CREATE = "create table users (user_id integer primary key not null ," + "fname text not null , lname text not null , login text not null , pass text not null , email text not null , settings text not null);";
    private static final String VENUE_TABLE_CREATE = "create table venues (venue_api_id text primary key  not null,venue_name text not null,photo_url text, category text, average_stars float not null, sum_stars float not null);";
    private static final String OPINION_TABLE_CREATE = "create table opinions (opinion_id integer primary key not null,user_id integer not null, venue_api_id text not null, text text not null,stars integer not null,FOREIGN KEY(user_id) REFERENCES users(user_id),FOREIGN KEY(venue_api_id) REFERENCES venues(venue_api_id));";
    private static final String VENUE_NAME ="venue_name";
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
    void putOpinionToDB(Opinion o){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues(); //z tego co rozumiem to pomaga we wkładaniu rzeczy do bazy
        values.put(OPINION_USERID, o.getUser_id());
        values.put(OPINION_VENUEID, o.getVenue_id());
        values.put(OPINION_TEXT,o.getText());
        values.put(OPINION_STARS,o.getStars());
        db.insert(OPINION_TABLE_NAME, null, values);
        Log.i("ADDED SUCCESSFULY","!");
        db.close(); //ZAWSZE ZAMYKAMY BAZĘ PO INSERCIE !

    }

    void putVenueToDBIfPossible(Venue v, Opinion o){
        Log.i("VENUE CATEGORY: ",String.valueOf(v.getVenue_category()));

        if(!checkIfVenueExists(v.getVenue_id())) {
            db = this.getWritableDatabase();
            ContentValues values = new ContentValues(); //z tego co rozumiem to pomaga we wkładaniu rzeczy do bazy
            values.put(VENUE_API_ID, v.getVenue_id());
            values.put(VENUE_AVERAGESTARS, o.getStars());
            values.put(VENUE_SUMSTARS, o.getStars());
            values.put(VENUE_NAME,v.getVenue_name());
            values.put(VENUE_CATEGORY,v.getVenue_category());
            db.insert(VENUE_TABLE_NAME, null, values);
            db.close(); //ZAWSZE ZAMYKAMY BAZĘ PO INSERCIE !
        }
        else {
            updateVenueData(v,o);
        }
    }
    private void updateVenueData(Venue v, Opinion o){
        db=this.getReadableDatabase();
        String query= "select sum_stars from venues where venue_api_id='"+v.getVenue_id()+"'";
        Cursor cursor = db.rawQuery(query, null);
        float old_sum_stars=0;
        int quantityOfOpinions= getQuantityOfOpinions(v.getVenue_id());
        if (cursor.moveToFirst()) {
            old_sum_stars = cursor.getInt(0);
            cursor.close();
        }

        float new_sum_stars= old_sum_stars+o.getStars();
        float new_average_stars= new_sum_stars/quantityOfOpinions;
        String updateQuery= "UPDATE venues SET average_stars='"+new_average_stars+"' ,sum_stars='"+new_sum_stars+"' WHERE venue_api_id='"+v.getVenue_id()+"';";
        db.execSQL(updateQuery);
        }

      void   getVenues(List<VenueElement> venues){

        db=this.getReadableDatabase();
        String query=" select * from venues";
        Cursor cursor= db.rawQuery(query,null);
        if (cursor.moveToFirst()){
            do {
                int quantityOfOpinions= getQuantityOfOpinions(cursor.getString(0));

                VenueElement venueElement= new VenueElement(cursor.getString(0),cursor.getString(1),cursor.getString(2),quantityOfOpinions,cursor.getFloat(4));
                venues.add(venueElement);

            }
            while (cursor.moveToNext());
            cursor.close();
        }

        }
    private boolean checkIfVenueExists(String id){
        db=this.getReadableDatabase();
        String query= "select * from venues where venue_api_id='"+id+"'";
        Cursor cursor= db.rawQuery(query,null);
        return cursorCounter(cursor);
    }
    boolean checkIfOpinionExists(Opinion o){
        db=this.getReadableDatabase();
        String query="SELECT * from opinions where user_id='"+o.getUser_id()+"' and venue_api_id='"+o.getVenue_id()+"';";
        Cursor cursor= db.rawQuery(query,null);
        return cursorCounter(cursor);

    }

    private boolean cursorCounter(Cursor cursor){

        if (cursor.getCount()>0)
        {
            cursor.close();
            return true;
        }
        else{
            cursor.close();
            return false;
        }
    }
    boolean checkIfExists(String column, String s) {
        db = this.getReadableDatabase();
        String query = "select * from " + USER_TABLE_NAME + " where " + column + "='" + s + "'";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.getCount() > 0) {
            cursor.close();
            Log.i("ERROR", s + " istnieje");
            return true;
        } else
        {             Log.i("OK", s + " nie istnieje");

            return false;
        }


    }

    Integer getUserId(String login) {
        db = this.getReadableDatabase();
        String query = "select user_id from " + USER_TABLE_NAME + " where login='" + login+"';";
        Cursor cursor = db.rawQuery(query, null);
        int user_id=-1;
        if (cursor.moveToFirst()) {
             user_id = cursor.getInt(0);
            cursor.close();

        }
        else{
            Log.i("WRONG USER ID","!");

        }
        return user_id;

    }
    List<OpinionElement> getOpinionDetalis(String venue_id){
         List<OpinionElement> opinionList = new ArrayList<>();

        db=this.getReadableDatabase();
        String query="select users.fname,users.lname,  opinions.text,opinions.stars FROM opinions  JOIN users ON users.user_id=opinions.user_id WHERE opinions.venue_api_id='"+venue_id+"' ";
        Cursor cursor = db.rawQuery(query,null);
        Log.i("CURSOR", String.valueOf(cursor));
        if (cursor.moveToFirst()) {

            do {

                OpinionElement element= new OpinionElement(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getFloat(3));
                opinionList.add(element);

            }
            while (cursor.moveToNext());

        }

        return opinionList;

    }
    private Integer getQuantityOfOpinions(String id) {
        db = this.getReadableDatabase();
        String query = "select * from opinions where venue_api_id='" + id + "';";
        Cursor cursor = db.rawQuery(query, null);
        int quantityOfOpinions = cursor.getCount();
        cursor.close();
        return quantityOfOpinions;


    }
    boolean checkPass(String u, String p) {
        db = this.getReadableDatabase();
        String query = "select login, pass from " + USER_TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        String login ;
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
