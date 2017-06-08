package com.example.praktikant.addressfinder.net;

import com.example.praktikant.addressfinder.utils.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LocationService {

    private static Retrofit getRetrofitInstance() {
        return new Retrofit.Builder().baseUrl(Constants.BASE_URL).
                addConverterFactory(GsonConverterFactory.create()).build();
    }

    public static LocationApiInterface apiInterface() {
        return getRetrofitInstance().create(LocationApiInterface.class);
    }
}
