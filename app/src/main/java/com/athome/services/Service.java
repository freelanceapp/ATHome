package com.athome.services;


import com.athome.models.AllCategoryModel;
import com.athome.models.PlaceGeocodeData;
import com.athome.models.PlaceMapDetailsData;
import com.athome.models.ProductDataModel;
import com.athome.models.SingleProductDataModel;
import com.athome.models.SliderDataModel;
import com.athome.models.SubCategoryDataModel;
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
    Call<SliderDataModel> get_slider();

    @GET("api/main-categories")
    Call<AllCategoryModel> getCategory();

    @GET("api/feature")
    Call<ProductDataModel> getFeatureProducts(@Query("user_id") String user_id);


    @GET("api/best")
    Call<ProductDataModel> getMostSellerProducts(@Query("user_id") String user_id);

    @GET("api/offers")
    Call<ProductDataModel> getOfferProducts(@Query("user_id") String user_id);

    @GET("api/sub-with-child")
    Call<SubCategoryDataModel> getSubCategoryByCategoryId(@Query("category_id") int category_id);

    @GET("api/get-product-by-name")
    Call<ProductDataModel> search(@Query("user_id") String user_id,
                                  @Query("search_name") String search_name
    );

    @GET("api/get-product-by-name")
    Call<ProductDataModel> getProducts(@Query("user_id") String user_id,
                                       @Query("category_id") String category_id,
                                       @Query("category_id") String subcategory_id,
                                       @Query("childcategory_id") String childcategory_id

    );

    @GET("api/one-product")
    Call<SingleProductDataModel> getProductById(@Query("user_id") String user_id,
                                                @Query("product_id") String search_name
    );

}