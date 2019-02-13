package com.example.myweather.tasks;

import com.example.myweather.POJO.PostModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitRequest {
    @GET("/v1/current.json")
    Call<PostModel> getData(@Query("key")String resourceName, @Query("q") String count);
}

