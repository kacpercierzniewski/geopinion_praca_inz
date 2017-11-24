package com.example.kacper.geopinion;

import com.example.kacper.geopinion.Model.FoursquareSearch;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by kacper on 24.10.17 for Praca Inżynierska.
 */

public interface FoursquareIntegration {
    @GET("venues/search")
    Call<FoursquareSearch> requestFoursquareModel(
            @Query("client_id") String client_id,
            @Query("client_secret") String client_secret,
            @Query("ll") String ll,
            @Query("v") String v,
            @Query("limit") String limit

    );

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.foursquare.com/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
