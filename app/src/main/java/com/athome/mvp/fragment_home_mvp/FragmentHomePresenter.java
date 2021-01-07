package com.athome.mvp.fragment_home_mvp;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.athome.R;
import com.athome.models.AddFavoriteDataModel;
import com.athome.models.AllCategoryModel;
import com.athome.models.ProductDataModel;
import com.athome.models.ProductModel;
import com.athome.models.SliderDataModel;
import com.athome.models.UserModel;
import com.athome.preferences.Preferences;
import com.athome.remote.Api;
import com.athome.tags.Tags;

import java.io.IOException;

import io.paperdb.Paper;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentHomePresenter {
    private Context context;
    private FragmentHomeView view;
    private Preferences preference;
    private UserModel userModel;
    private double lat = 0.0, lng = 0.0;
    private String lang="ar";

    public FragmentHomePresenter(Context context, FragmentHomeView view, double lat, double lng) {
        this.context = context;
        this.view = view;
        preference = Preferences.getInstance();
        userModel = preference.getUserData(context);
        this.lat = lat;
        this.lng = lng;
        Paper.init(context);
        lang = Paper.book().read("lang","ar");
    }


    public void getSlider() {
        view.onProgressSliderShow();
        String type;
        if (lang.equals("ar")){
            type="1";
        }else {
            type="2";
        }
        Api.getService(Tags.base_url).get_slider(type).enqueue(new Callback<SliderDataModel>() {
            @Override
            public void onResponse(Call<SliderDataModel> call, Response<SliderDataModel> response) {
                view.onProgressSliderHide();

                if (response.isSuccessful()) {
                    if (response.body() != null && response.body().getStatus() == 200 && response.body().getData() != null) {
                        view.onSliderSuccess(response.body().getData());

                    }

                } else {
                    try {
                        view.onFailed(context.getString(R.string.failed));
                        Log.e("Error_code", response.code() + "_" + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(Call<SliderDataModel> call, Throwable t) {
                try {
                    view.onProgressCategoryHide();

                    Log.e("Error", t.getMessage());

                } catch (Exception e) {

                }

            }
        });

    }

    public void getCategory() {
        view.onProgressCategoryShow();
        Api.getService(Tags.base_url)
                .getCategory()
                .enqueue(new Callback<AllCategoryModel>() {
                    @Override
                    public void onResponse(Call<AllCategoryModel> call, Response<AllCategoryModel> response) {
                        view.onProgressCategoryHide();
                        if (response.isSuccessful()) {
                            if (response.body() != null && response.body().getStatus() == 200 && response.body().getData() != null) {
                                view.onSuccess(response.body().getData());

                            }


                        } else {
                            view.onProgressCategoryHide();

                            try {
                                Log.e("errorNotCode", response.code() + "__" + response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            if (response.code() == 500) {
                                Toast.makeText(context, "Server Error", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, context.getString(R.string.failed), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<AllCategoryModel> call, Throwable t) {
                        try {
                            view.onProgressCategoryHide();


                            if (t.getMessage() != null) {
                                Log.e("error_not_code", t.getMessage() + "__");

                                if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
                                    Toast.makeText(context, context.getString(R.string.something), Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(context, context.getString(R.string.failed), Toast.LENGTH_SHORT).show();
                                }
                            }
                        } catch (Exception e) {
                            Log.e("Error", e.getMessage() + "__");
                        }
                    }
                });
    }

    public void getFeaturedProducts() {
        String user_id = "all";
        if (userModel != null) {
            user_id = String.valueOf(userModel.getData().getId());
        }
        view.onProgressFeaturedProductsShow();
        Api.getService(Tags.base_url)
                .getFeatureProducts(user_id)
                .enqueue(new Callback<ProductDataModel>() {
                    @Override
                    public void onResponse(Call<ProductDataModel> call, Response<ProductDataModel> response) {
                        view.onProgressFeaturedProductsHide();
                        if (response.isSuccessful()) {
                            if (response.body() != null && response.body().getStatus() == 200 && response.body().getData() != null) {
                                view.onFeaturedProductSuccess(response.body().getData());

                            }


                        } else {
                            view.onProgressFeaturedProductsHide();

                            try {
                                Log.e("errorNotCode", response.code() + "__" + response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            if (response.code() == 500) {
                                Toast.makeText(context, "Server Error", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, context.getString(R.string.failed), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ProductDataModel> call, Throwable t) {
                        try {
                            view.onProgressFeaturedProductsHide();


                            if (t.getMessage() != null) {
                                Log.e("error_not_code", t.getMessage() + "__");

                                if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
                                    Toast.makeText(context, context.getString(R.string.something), Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(context, context.getString(R.string.failed), Toast.LENGTH_SHORT).show();
                                }
                            }
                        } catch (Exception e) {
                            Log.e("Error", e.getMessage() + "__");
                        }
                    }
                });
    }

    public void getMostSellerProducts() {
        String user_id = "all";
        if (userModel != null) {
            user_id = String.valueOf(userModel.getData().getId());
        }
        view.onProgressMostSellerShow();
        Api.getService(Tags.base_url)
                .getMostSellerProducts(user_id)
                .enqueue(new Callback<ProductDataModel>() {
                    @Override
                    public void onResponse(Call<ProductDataModel> call, Response<ProductDataModel> response) {
                        view.onProgressMostSellerHide();
                        if (response.isSuccessful()) {
                            if (response.body() != null && response.body().getStatus() == 200 && response.body().getData() != null) {
                                view.onMostSellerSuccess(response.body().getData());

                            }


                        } else {
                            view.onProgressMostSellerHide();

                            try {
                                Log.e("errorNotCode", response.code() + "__" + response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            if (response.code() == 500) {
                                view.onFailed("Server Error");

                            } else {
                                Toast.makeText(context, context.getString(R.string.failed), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ProductDataModel> call, Throwable t) {
                        try {
                            view.onProgressMostSellerHide();


                            if (t.getMessage() != null) {
                                Log.e("error_not_code", t.getMessage() + "__");

                                if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
                                    Toast.makeText(context, context.getString(R.string.something), Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(context, context.getString(R.string.failed), Toast.LENGTH_SHORT).show();
                                }
                            }
                        } catch (Exception e) {
                            Log.e("Error", e.getMessage() + "__");
                        }
                    }
                });
    }

    public void getOtherProducts() {
        String user_id = "all";
        if (userModel != null) {
            user_id = String.valueOf(userModel.getData().getId());
        }
        view.onProgressOtherProductsShow();
        Api.getService(Tags.base_url)
                .getOtherProducts(user_id)
                .enqueue(new Callback<ProductDataModel>() {
                    @Override
                    public void onResponse(Call<ProductDataModel> call, Response<ProductDataModel> response) {
                        view.onProgressOtherProductsHide();
                        if (response.isSuccessful()) {
                            if (response.body() != null && response.body().getStatus() == 200 && response.body().getData() != null) {
                                view.onOtherProductSuccess(response.body().getData());

                            }


                        } else {
                            view.onProgressOtherProductsHide();

                            try {
                                Log.e("errorNotCode", response.code() + "__" + response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            if (response.code() == 500) {
                                view.onFailed("Server Error");

                            } else {
                                Toast.makeText(context, context.getString(R.string.failed), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ProductDataModel> call, Throwable t) {
                        try {
                            view.onProgressOtherProductsHide();


                            if (t.getMessage() != null) {
                                Log.e("error_not_code", t.getMessage() + "__");

                                if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
                                    Toast.makeText(context, context.getString(R.string.something), Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(context, context.getString(R.string.failed), Toast.LENGTH_SHORT).show();
                                }
                            }
                        } catch (Exception e) {
                            Log.e("Error", e.getMessage() + "__");
                        }
                    }
                });
    }



    public void getOfferProducts()
    {
        String user_id = "all";
        if (userModel != null) {
            user_id = String.valueOf(userModel.getData().getId());
        }
        view.onProgressOfferShow();
        Api.getService(Tags.base_url)
                .getOfferProducts(user_id)
                .enqueue(new Callback<ProductDataModel>() {
                    @Override
                    public void onResponse(Call<ProductDataModel> call, Response<ProductDataModel> response) {
                        view.onProgressOfferHide();
                        if (response.isSuccessful()) {
                            if (response.body() != null && response.body().getStatus() == 200 && response.body().getData() != null) {
                                view.onOfferSuccess(response.body().getData());

                            }


                        } else {
                            view.onProgressOfferHide();

                            try {
                                Log.e("errorNotCode", response.code() + "__" + response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            if (response.code() == 500) {
                                Toast.makeText(context, "Server Error", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, context.getString(R.string.failed), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ProductDataModel> call, Throwable t) {
                        try {
                            view.onProgressOfferHide();


                            if (t.getMessage() != null) {
                                Log.e("error_not_code", t.getMessage() + "__");

                                if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
                                    view.onFailed(context.getString(R.string.something));
                                } else {
                                    view.onFailed(context.getString(R.string.something));

                                }
                            }
                        } catch (Exception e) {
                            Log.e("Error", e.getMessage() + "__");
                        }
                    }
                });
    }

    public void add_remove_favorite(ProductModel productModel, int position, String type)
    {
        if (userModel == null) {
            if (productModel.getIs_wishlist()!=null){
                productModel.setIs_wishlist(null);
            }else {
                productModel.setIs_wishlist(new ProductModel.IsWishList());
            }
            view.onUserNotRegister(context.getString(R.string.pls_signin_signup),productModel,position,type);
            return;
        }
        String user_id = String.valueOf(userModel.getData().getId());
        Api.getService(Tags.base_url)
                .add_remove_favorite(userModel.getData().getToken(),user_id, String.valueOf(productModel.getId()))
                .enqueue(new Callback<AddFavoriteDataModel>() {
                    @Override
                    public void onResponse(Call<AddFavoriteDataModel> call, Response<AddFavoriteDataModel> response) {
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                productModel.setIs_wishlist(response.body().getData());
                                view.onFavoriteActionSuccess(productModel,position,type);
                            }


                        } else {

                            try {
                                Log.e("errorNotCode", response.code() + "__" + response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            if (productModel.getIs_wishlist()!=null){
                                productModel.setIs_wishlist(null);
                            }else {
                                productModel.setIs_wishlist(new ProductModel.IsWishList());
                            }
                            view.onFavoriteActionSuccess(productModel,position,type);

                            if (response.code() == 500) {
                                Toast.makeText(context, "Server Error", Toast.LENGTH_SHORT).show();
                            } else {
                                view.onFailed(context.getString(R.string.failed));

                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<AddFavoriteDataModel> call, Throwable t) {
                        try {
                            if (productModel.getIs_wishlist()!=null){
                                productModel.setIs_wishlist(null);
                            }else {
                                productModel.setIs_wishlist(new ProductModel.IsWishList());
                            }
                            view.onFavoriteActionSuccess(productModel,position,type);
                            if (t.getMessage() != null) {
                                Log.e("error_not_code", t.getMessage() + "__");

                                if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
                                    view.onFailed(context.getString(R.string.something));
                                } else {
                                    view.onFailed(context.getString(R.string.failed));

                                }
                            }
                        } catch (Exception e) {
                            Log.e("Error", e.getMessage() + "__");
                        }
                    }
                });
    }
}
