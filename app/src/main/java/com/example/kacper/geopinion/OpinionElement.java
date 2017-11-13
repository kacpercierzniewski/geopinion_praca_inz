package com.example.kacper.geopinion;

class OpinionElement {


    OpinionElement(String user_fname, String user_lname, String opinion_text, float opinion_stars) {
        this.user_fname = user_fname;
        this.user_lname = user_lname;
        this.opinion_stars = opinion_stars;
        this.opinion_text = opinion_text;
    }

    String getUser_fname() {
        return user_fname;
    }

    String getUser_lname() {
        return user_lname;
    }

    float getOpinion_stars() {
        return opinion_stars;
    }

    String getOpinion_text() {
        return opinion_text;
    }

    private String user_fname;
    private String user_lname;
    private float opinion_stars;
    private String opinion_text;






}
