package com.athome.mvp.activity_favorite_mvp;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.athome.R;
import com.athome.models.AddFavoriteDataModel;
import com.athome.models.ProductDataModel;
import com.athome.models.ProductModel;
import com.athome.models.UserModel;
import com.athome.mvp.activity_product_mvp.ActivityProductView;
import com.athome.preferences.Preferences;
import com.athome.remote.Api;
import com.athome.share.Common;
import com.athome.tags.Tags;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ActivityFavoritePresenter {
    private Context context;
    private ActivityFavoriteView view;
    private Preferences preference;
    private UserModel userModel;

    public ActivityFavoritePresenter(Context context, ActivityFavoriteView view) {
        this.context = context;
        this.view = view;
        preference = Preferences.getInstance();
        userModel = preference.getUserData(context);

    }


    public void getProducts() {
        if (userModel == null) {
            return;
        }
        String user_id = String.valueOf(userModel.getData().getId());
        view.onProgressShow();
        Api.getService(Tags.base_url)
                .getMyFavorite(userModel.getData().getToken(),user_id)
                .enqueue(new Callback<ProductDataModel>() {
                    @Override
                    public void onResponse(Call<ProductDataModel> call, Response<ProductDataModel> response) {
                        view.onProgressHide();
                        if (response.isSuccessful()) {
                            if (response.body() != null && response.body().getStatus() == 200 && response.body().getData() != null) {
                                view.onSuccess(response.body().getData());

                            }


                        } else {
                            view.onProgressHide();
                            try {
                                Log.e("errorNotCode", response.code() + "__" + response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            if (response.code() == 500) {
                                view.onFailed("Server Error");

                            } else {
                                view.onFailed(context.getString(R.string.failed));

                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ProductDataModel> call, Throwable t) {
                        try {
                            view.onProgressHide();


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


    public void remove_favorite(ProductModel productModel)
    {

        if (userModel==null){
            return;
        }
        ProgressDialog dialog = Common.createProgressDialog(context,context.getString(R.string.wait));
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        String user_id = String.valueOf(userModel.getData().getId());
        Api.getService(Tags.base_url)
                .add_remove_favorite(userModel.getData().getToken(),user_id, String.valueOf(productModel.getId()))
                .enqueue(new Callback<AddFavoriteDataModel>() {
                    @Override
                    public void onResponse(Call<AddFavoriteDataModel> call, Response<AddFavoriteDataModel> response) {
                        if (response.isSuccessful()) {
                            dialog.dismiss();
                            if (response.body() != null) {
                                view.onRemoveFavoriteSuccess();
                            }


                        } else {

                            dialog.dismiss();
                            try {
                                Log.e("errorNotCode", response.code() + "__" + response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

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
                            dialog.dismiss();
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
