package com.athome.services;


import com.athome.models.AllCategoryModel;
import com.athome.models.PlaceGeocodeData;
import com.athome.models.PlaceMapDetailsData;
import com.athome.models.Slider_Model;
import com.athome.models.UserModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Service {

    @GET("place/findplacefromtext/json")
    Call<PlaceMapDetailsData> searchOnMap(@Query(value = "inputtype") String inputtype,
                                          @Query(value = "input") String input,
                                          @Query(value = "fields") String fields,
                                          @Query(value = "language") String language,
                                          @Query(value = "key") String key
    );

    @GET("geocode/json")
    Call<PlaceGeocodeData> getGeoData(@Query(value = "latlng") String latlng,
                                      @Query(value = "language") String language,
                                      @Query(value = "key") String key);
    @FormUrlEncoded
    @POST("api/register")
    Call<UserModel> signup(
                           @Field("phone") String phone,
                           @Field("name") String name,
                           @Field("email") String email,
                           @Field("password") String password


    );

    @GET("api/slider")
    Call<Slider_Model> get_slider();

    @GET("api/main-categories")
    Call<AllCategoryModel> getCategory(
    );

}