package com.athome.mvp.activity_my_address_mvp;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.athome.R;
import com.athome.models.AddFavoriteDataModel;
import com.athome.models.AddressDataModel;
import com.athome.models.ProductDataModel;
import com.athome.models.ProductModel;
import com.athome.models.UserModel;
import com.athome.mvp.fragment_search_mvp.FragmentSearchView;
import com.athome.preferences.Preferences;
import com.athome.remote.Api;
import com.athome.tags.Tags;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ActivityAddressPresenter {
    private Context context;
    private ActivityMyAddressView view;
    private Preferences preference;
    private UserModel userModel;

    public ActivityAddressPresenter(Context context, ActivityMyAddressView view) {
        this.context = context;
        this.view = view;
        preference = Preferences.getInstance();
        userModel = preference.getUserData(context);

    }


    public void getMyAddress() {
        if (userModel == null) {
            return;
        }
        String user_id  = String.valueOf(userModel.getData().getId());

        view.onProgressShow();
        Api.getService(Tags.base_url)
                .getMyAddress(userModel.getData().getToken(),user_id)
                .enqueue(new Callback<AddressDataModel>() {
                    @Override
                    public void onResponse(Call<AddressDataModel> call, Response<AddressDataModel> response) {
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
                    public void onFailure(Call<AddressDataModel> call, Throwable t) {
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
