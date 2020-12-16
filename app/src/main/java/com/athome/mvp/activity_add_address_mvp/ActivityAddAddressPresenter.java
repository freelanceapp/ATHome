package com.athome.mvp.activity_add_address_mvp;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.athome.R;
import com.athome.models.AddAddressModel;
import com.athome.models.UserModel;
import com.athome.preferences.Preferences;
import com.athome.remote.Api;
import com.athome.share.Common;
import com.athome.tags.Tags;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ActivityAddAddressPresenter {
    private Context context;
    private ActivityAddAddressView view;
    private Preferences preference;
    private UserModel userModel;

    public ActivityAddAddressPresenter(Context context, ActivityAddAddressView view) {
        this.context = context;
        this.view = view;
        preference = Preferences.getInstance();
        userModel = preference.getUserData(context);

    }



    public void add_address(AddAddressModel addAddressModel)
    {

        if (userModel==null){
            return;
        }
        ProgressDialog dialog = Common.createProgressDialog(context,context.getString(R.string.wait));
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        Api.getService(Tags.base_url)
                .addAddress(userModel.getData().getToken(),addAddressModel.getUser_id(),addAddressModel.getPhone(),addAddressModel.getUser_name(),addAddressModel.getAddress()+"-"+addAddressModel.getAdditionalNote(),addAddressModel.getLat(),addAddressModel.getLng(),addAddressModel.getType())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            dialog.dismiss();
                            if (response.body() != null) {
                                view.onAddedSuccess();
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
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
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

    public void update_address(AddAddressModel addAddressModel)
    {

        if (userModel==null){
            return;
        }
        ProgressDialog dialog = Common.createProgressDialog(context,context.getString(R.string.wait));
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        Api.getService(Tags.base_url)
                .updateAddress(userModel.getData().getToken(),addAddressModel.getAddress_id(),addAddressModel.getUser_id(),addAddressModel.getPhone(),addAddressModel.getUser_name(),addAddressModel.getAddress()+"-"+addAddressModel.getAdditionalNote(),addAddressModel.getLat(),addAddressModel.getLng(),addAddressModel.getType())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            dialog.dismiss();
                            if (response.body() != null) {
                                view.onUpdateSuccess();
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
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
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
