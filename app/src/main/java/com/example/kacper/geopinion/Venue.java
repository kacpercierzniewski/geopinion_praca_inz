package com.example.kacper.geopinion;

/**
 * Created by kacper on 21.10.17.
 */

public class Venue {
    public String getVenue_id() {
        return venue_id;
    }

    public float getAverage_stars() {
        return average_stars;
    }

    public float getSum_stars() {
        return sum_stars;
    }



    private String venue_id;
    private float average_stars;
    private float sum_stars;
    Venue(String venue_id, float average_stars, float sum_stars){
        this.venue_id=venue_id;
        this.average_stars=average_stars;
        this.sum_stars=sum_stars;

    }

    

}
