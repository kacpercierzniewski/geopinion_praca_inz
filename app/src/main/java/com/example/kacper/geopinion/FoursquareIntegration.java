package com.example.kacper.geopinion;

import com.example.kacper.geopinion.Model.FoursquareModel;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by kacper on 24.10.17 for Praca Inżynierska.
 */

public interface FoursquareIntegration {
    @GET("venues/explore/")
    Call<FoursquareModel> requestFoursquareModel(
            @Query("client_id") String client_id,
            @Query("client_secret") String client_secret,
            @Query("v") String v,
            @Query("ll") String ll,
            @Query("query") String query);

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.foursquare.com/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
