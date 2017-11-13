package com.example.kacper.geopinion;

public class VenueElement {

    private String venue_name;
    private String venue_id;
    private String venue_category;
    private int quantityOfOpinions;
    private float avg_stars;
    public VenueElement(String venue_id,String venue_name, String venue_category, int quantityOfOpinions, float avg_stars) {
        this.venue_name = venue_name;
        this.venue_category = venue_category;
        this.quantityOfOpinions = quantityOfOpinions;
        this.avg_stars = avg_stars;
        this.venue_id= venue_id;
    }

    public String getVenue_name() {
        return venue_name;
    }

    public String getVenue_category() {
        return venue_category;
    }

    public int getQuantityOfOpinions() {
        return quantityOfOpinions;
    }

    public float getAvg_stars() {
        return avg_stars;
    }
    public String getVenue_id() {
        return venue_id;
    }


}
