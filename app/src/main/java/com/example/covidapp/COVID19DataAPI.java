package com.example.covidapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface COVID19DataAPI {
    @GET("LATEST?disableRedirect=true")
    Call<List<WorldDataSummary>> getWorldDataSummaries();

    @GET("world/total")
    Call<List<TotalDataSummary>> getTotalDataSummaries();
}


