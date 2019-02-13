package com.example.myweather;

import android.app.Application;

import com.example.myweather.tasks.RetrofitRequest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class App extends Application {

    private Retrofit retrofit;
    private static RetrofitRequest retrofitRequest;

    @Override
    public void onCreate() {
        super.onCreate();

        retrofit = new Retrofit.Builder()
                .baseUrl("http://api.apixu.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitRequest = retrofit.create(RetrofitRequest.class);
    }

    public static RetrofitRequest getApi () {
        return retrofitRequest;
    }

}

