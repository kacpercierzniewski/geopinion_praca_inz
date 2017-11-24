package com.example.kacper.geopinion;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import com.orhanobut.hawk.Hawk;


public class ExpressOpinionActivity extends AppCompatActivity {
    private TextView tv;
    private RatingBar rb;
    private DatabaseManager db = new DatabaseManager(this);
    private int user_id;
    private String venue_id;
    private String venue_name;
    private String venue_category;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_express_opinion);
        findViewsById();
        user_id= db.getUserId(String.valueOf(Hawk.get("login")));
        Log.i("USER ID:",String.valueOf(user_id));
        venue_id=Hawk.get("venue_id");
        venue_name=Hawk.get("venue_name");
        venue_category=Hawk.get("venue_category");

        Hawk.destroy();

    }

    public void onButtonClick(View v){
        Log.i("BUTTON CLICKED:","!");
        Log.i("ID OF VENUE", String.valueOf(Hawk.get("venue_id")));
        String text = String.valueOf(tv.getText());
        float stars = rb.getRating();
        Log.i("RATING: ",String.valueOf(stars));
        Opinion opinion = new Opinion(user_id,venue_id, text, stars);
        Venue venue= new Venue(venue_id,venue_name,venue_category);
        if (text.equals("")) {
            tv.setError("Nie dodano opisu.");
        }
        else {
            if (!(db.checkIfOpinionExists(opinion))) {
                makeToast("Opinia wystawiona pomyślnie!");
                db.putOpinionToDB(opinion);
                db.putVenueToDBIfPossible(venue, opinion);

                this.finish();


               } else {
                makeToast(getString(R.string.opinionExists));
            }
        }

    }

  private void  findViewsById(){

      tv=(TextView)findViewById(R.id.ETexpressOpinion);
      rb=(RatingBar)findViewById(R.id.ratingBar);
  }

    private void makeToast(String text){
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

    }
}
