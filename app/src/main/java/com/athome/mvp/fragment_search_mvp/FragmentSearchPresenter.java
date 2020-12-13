package com.athome.mvp.fragment_search_mvp;

import android.content.Context;
import android.util.Log;

import com.athome.R;
import com.athome.models.ProductDataModel;
import com.athome.models.UserModel;
import com.athome.preferences.Preferences;
import com.athome.remote.Api;
import com.athome.tags.Tags;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentSearchPresenter {
    private Context context;
    private FragmentSearchView view;
    private Preferences preference;
    private UserModel userModel;
    private double lat = 0.0, lng = 0.0;

    public FragmentSearchPresenter(Context context, FragmentSearchView view, double lat, double lng) {
        this.context = context;
        this.view = view;
        preference = Preferences.getInstance();
        userModel = preference.getUserData(context);
        this.lat = lat;
        this.lng = lng;
    }


    public void getSearch(String query) {
        String user_id = "all";
        if (userModel != null) {
            user_id = String.valueOf(userModel.getData().getId());
        }
        view.onProgressShow();
        Api.getService(Tags.base_url)
                .search(user_id, query)
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


}
