package com.example.kacper.geopinion;

/**
 * Created by kacper on 21.10.17.
 */

public class Opinion {



    int getUser_id() {
        return user_id;
    }

    String getVenue_id() {
        return venue_id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    float getStars() {
        return stars;
    }


    private int user_id;
    private String venue_id;
    private String text;
    private float stars;

    Opinion(int user_id, String venue_id, String text, float stars){
        this.user_id=user_id;
        this.venue_id=venue_id;
        this.text=text;
        this.stars=stars;
    }
    Opinion(int user_id, String venue_id){
        this.user_id=user_id;
        this.venue_id=venue_id;

    }

}
