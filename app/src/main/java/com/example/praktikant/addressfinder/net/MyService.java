package com.example.praktikant.addressfinder.net;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Praktikant on 23.5.2017..
 */

public class MyService {

    public static Retrofit getRetrofitInstance(){

        Retrofit retrofit = new Retrofit.Builder().baseUrl(MyServiceContract.BASE_URL).
                addConverterFactory(GsonConverterFactory.create()).build();

        return retrofit;

    }

    public static MyApiInterface apiInterface(){

        MyApiInterface apiService = getRetrofitInstance().create(MyApiInterface.class);

        return apiService;
    }
}
