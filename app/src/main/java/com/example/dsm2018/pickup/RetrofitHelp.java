package com.example.dsm2018.pickup;

import retrofit2.Retrofit;

public class RetrofitHelp {
    private final String BASE_URL = "http://192.168.43.33:9000/";

    private Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).build();

    public RetrofitService retrofitService = retrofit.create(RetrofitService.class);

}
