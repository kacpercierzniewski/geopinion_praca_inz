package com.example.kacper.geopinion;

/**
 * Created by kacper on 21.10.17.
 */

class Venue {

    private String venue_id;
    private String venue_name;



    private String venue_category;
    Venue(String venue_id, String venue_name, String venue_category){
        this.venue_id=venue_id;
        this.venue_name=venue_name;
        this.venue_category=venue_category;
    }


    String getVenue_name() {
        return venue_name;
    }
    String getVenue_id() {
        return venue_id;
    }
    String getVenue_category() {return venue_category; }


}
