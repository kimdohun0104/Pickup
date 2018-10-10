package com.example.dsm2018.pickup;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHelp {
    private final String BASE_URL = "http://52.78.89.71:9000/";

    private Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();

    public RetrofitService retrofitService = retrofit.create(RetrofitService.class);

}
