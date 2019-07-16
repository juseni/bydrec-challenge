package com.bydrecchallenge.myapplication.interfaces;

import com.bydrecchallenge.myapplication.models.BydrecData;
import retrofit2.Call;
import retrofit2.http.GET;

import java.util.List;

public interface ApiRetrofitService {

    @GET("fixtures.json")
    Call<List<BydrecData>> getFixtures();

    @GET("results.json")
    Call<List<BydrecData>> getResults();
}
