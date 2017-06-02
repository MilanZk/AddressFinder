package com.example.praktikant.addressfinder.net;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LocationService {

    private static final String BASE_URL = "https://sampleserver1.arcgisonline.com/ArcGIS/rest/services/Locators/ESRI_Geocode_USA/GeocodeServer/findAddressCandidates/";

    private static Retrofit getRetrofitInstance() {
        return new Retrofit.Builder().baseUrl(BASE_URL).
                addConverterFactory(GsonConverterFactory.create()).build();
    }

    public static LocationApiInterface apiInterface() {
        return getRetrofitInstance().create(LocationApiInterface.class);
    }
}
