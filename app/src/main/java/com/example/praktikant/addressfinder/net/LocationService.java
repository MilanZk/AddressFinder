package com.example.praktikant.addressfinder.net;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Praktikant on 23.5.2017..
 */

public class LocationService {

    public static final String BASE_URL = "https://sampleserver1.arcgisonline.com/ArcGIS/rest/services/Locators/ESRI_Geocode_USA/GeocodeServer/findAddressCandidates/";

    public static Retrofit getRetrofitInstance(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).
                addConverterFactory(GsonConverterFactory.create()).build();
        return retrofit;
    }

    public static LocationApiInterface apiInterface(){
        LocationApiInterface apiService = getRetrofitInstance().create(LocationApiInterface.class);
        return apiService;
    }
}
