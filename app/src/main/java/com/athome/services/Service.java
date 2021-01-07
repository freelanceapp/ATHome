package com.athome.services;


import com.athome.models.AddFavoriteDataModel;
import com.athome.models.AddressDataModel;
import com.athome.models.AllCategoryModel;
import com.athome.models.CategoryDataModel;
import com.athome.models.CouponDataModel;
import com.athome.models.LogoutModel;
import com.athome.models.MenuDataModel;
import com.athome.models.OrderDataModel;
import com.athome.models.PlaceGeocodeData;
import com.athome.models.PlaceMapDetailsData;
import com.athome.models.ProductDataModel;
import com.athome.models.SendCartModel;
import com.athome.models.SingleCommentDataModel;
import com.athome.models.SingleOrderModel;
import com.athome.models.SingleProductDataModel;
import com.athome.models.SliderDataModel;
import com.athome.models.SubCategoryDataModel;
import com.athome.models.UserModel;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
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
    Call<UserModel> signUp(@Field("phone") String phone,
                           @Field("name") String name,
                           @Field("email") String email,
                           @Field("password") String password
    );

    @FormUrlEncoded
    @POST("api/login")
    Call<UserModel> login(@Field("phone") String phone,
                          @Field("password") String password
    );



    @GET("api/slider")
    Call<SliderDataModel> get_slider(@Query("location") String lang);

    @GET("api/main-categories")
    Call<AllCategoryModel> getCategory();

    @GET("api/feature")
    Call<ProductDataModel> getFeatureProducts(@Query("user_id") String user_id);


    @GET("api/best")
    Call<ProductDataModel> getMostSellerProducts(@Query("user_id") String user_id);

    @GET("api/best")
    Call<ProductDataModel> getOtherProducts(@Query("user_id") String user_id);

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
                                                @Query("product_id") String product_id
    );

    @FormUrlEncoded
    @POST("api/action-wishlists")
    Call<AddFavoriteDataModel> add_remove_favorite(@Header("Authorization") String token,
                                                   @Field("user_id") String user_id,
                                                   @Field("product_id") String product_id
    );

    @GET("api/my-wishlists")
    Call<ProductDataModel> getMyFavorite(@Header("Authorization") String token,
                                         @Query("user_id") String user_id
    );

    @GET("api/my-address")
    Call<AddressDataModel> getMyAddress(@Header("Authorization") String token,
                                        @Query("user_id") String user_id
    );


    @FormUrlEncoded
    @POST("api/add-address")
    Call<ResponseBody> addAddress(@Header("Authorization") String token,
                                  @Field("user_id") String user_id,
                                  @Field("phone") String phone,
                                  @Field("name") String name,
                                  @Field("address") String address,
                                  @Field("google_lat") double google_lat,
                                  @Field("google_long") double google_long,
                                  @Field("type") String type
    );

    @FormUrlEncoded
    @POST("api/edit-address")
    Call<ResponseBody> updateAddress(@Header("Authorization") String token,
                                     @Field("id") String address_id,
                                     @Field("user_id") String user_id,
                                     @Field("phone") String phone,
                                     @Field("name") String name,
                                     @Field("address") String address,
                                     @Field("google_lat") double google_lat,
                                     @Field("google_long") double google_long,
                                     @Field("type") String type
    );

    @FormUrlEncoded
    @POST("api/delete-address")
    Call<ResponseBody> deleteAddress(@Header("Authorization") String token,
                                     @Field("id") String address_id
    );

    @FormUrlEncoded
    @POST("api/logout")
    Call<LogoutModel> logout(@Header("Authorization") String token,
                             @Field("user_id") int user_id,
                             @Field("phone_token") String phone_token,
                             @Field("software_type") String software_type

    );

    @FormUrlEncoded
    @POST("api/update-firebase")
    Call<LogoutModel> updateFirebaseToken(@Header("Authorization") String token,
                                          @Field("user_id") int user_id,
                                          @Field("phone_token") String phone_token,
                                          @Field("software_type") String software_type

    );

    @FormUrlEncoded
    @POST("api/StoreContact")
    Call<ResponseBody> contactUs(@Field("name") String name,
                                 @Field("email") String email,
                                 @Field("phone") String phone,
                                 @Field("text") String message
    );

    @FormUrlEncoded
    @POST("api/StoreComment")
    Call<SingleCommentDataModel> addComment(@Header("Authorization") String token,
                                            @Field("user_id") String user_id,
                                            @Field("product_id") String product_id,
                                            @Field("text") String message
    );


    @GET("api/GetMenu")
    Call<MenuDataModel> getMenu(@Header("Authorization") String token,
                                @Query("user_id") String user_id
    );

    @FormUrlEncoded
    @POST("api/DeleteMenu")
    Call<LogoutModel> deleteITemMenu(@Header("Authorization") String token,
                                     @Field("menu_id") String menu_id
    );

    @FormUrlEncoded
    @POST("api/StoreMenu")
    Call<LogoutModel> addToMenu(@Header("Authorization") String token,
                                @Field("user_id") String user_id,
                                @Field("product_id") String product_id,
                                @Field("status") String status,
                                @Field("amount") int amount
    );

    @GET("api/GetCoupon")
    Call<CouponDataModel> checkCouponData(@Query("code") String code
    );


    @POST("api/StoreOrder")
    Call<SingleOrderModel> sendOrder(@Header("Authorization") String token,
                                     @Body SendCartModel body);


    @GET("api/GetOrders")
    Call<OrderDataModel> getOrders(@Header("Authorization") String token,
                                   @Query("user_id") String user_id
    );

    @GET("api/OneOrder")
    Call<SingleOrderModel> getSingleOrders(@Query("order_id") String order_id
    );

    @GET("api/GetProducts")
    Call<CategoryDataModel> getProductsByAnyCategoryId(@Query("category_id") int category_id
    );
}