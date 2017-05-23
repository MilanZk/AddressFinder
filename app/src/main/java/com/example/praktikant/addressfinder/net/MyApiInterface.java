package com.example.praktikant.addressfinder.net;

import com.example.praktikant.addressfinder.net.model.Location;
import com.example.praktikant.addressfinder.net.model.ResponseData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Praktikant on 23.5.2017..
 */

public interface MyApiInterface {


    @FormUrlEncoded
    @POST("?")
    Call<ResponseData>createResponse(@Field("address") String address, @Field("city") String city, @Field("state") String state, @Field("Zip") String postal,@Field("f") String pjson);




}
