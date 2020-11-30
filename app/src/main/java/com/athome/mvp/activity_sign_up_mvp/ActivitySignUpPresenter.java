package com.athome.mvp.activity_sign_up_mvp;

import android.app.FragmentManager;
import android.content.Context;
import android.util.Log;

import com.athome.R;
import com.athome.models.UserModel;
import com.athome.remote.Api;
import com.athome.tags.Tags;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.athome.models.SignUpModel;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivitySignUpPresenter {
    private Context context;
    private ActivitySignUpView view;

    private DatePickerDialog datePickerDialog;



    public ActivitySignUpPresenter(Context context, ActivitySignUpView view)
    {
        this.context = context;
        this.view = view;


    }




    public void checkData(SignUpModel signUpModel)
    {
        if (signUpModel.isDataValid(context)){
//            if (signUpModel.getImageUrl().isEmpty()){
//                sign_up_without_image(signUpModel);
//            }else {
                sign_up(signUpModel);

          //  }
        }
    }
    public void showDateDialog(FragmentManager fragmentManager){
        try {
            datePickerDialog.show(fragmentManager,"");

        }catch (Exception e){}
    }

    private void sign_up(SignUpModel signUpModel) {
        view.onLoad();
        Api.getService(Tags.base_url)
                .signup(signUpModel.getPhone_code()+ signUpModel.getPhone(), signUpModel.getName(), signUpModel.getEmail(), signUpModel.getPassword())
                .enqueue(new Callback<UserModel>() {
                    @Override
                    public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                        view.onFinishload();
                        if (response.isSuccessful() && response.body() != null) {
                            //  Log.e("eeeeee", response.body().getUser().getName());
                            if(response.body().status==200){
                            view.onSignupValid(response.body());}
                            else {
                                view.onFailed(response.body().message);
                            }
                        } else {
                            try {
                                Log.e("mmmmmmmmmmssss", response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }


                            if (response.code() == 500) {
                                view.onServer();
                            } else {
                                view.onFailed(context.getResources().getString(R.string.failed));
                                //  Toast.makeText(VerificationCodeActivity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<UserModel> call, Throwable t) {
                        try {
                            view.onFinishload();
                            if (t.getMessage() != null) {
                                Log.e("msg_category_error", t.getMessage() + "__");

                                if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
                                    view.onnotconnect(t.getMessage().toLowerCase());
                                    //  Toast.makeText(VerificationCodeActivity.this, getString(R.string.something), Toast.LENGTH_SHORT).show();
                                } else {
                                    view.onFailed(context.getResources().getString(R.string.failed));
                                    // Toast.makeText(VerificationCodeActivity.this, getString(R.string.failed), Toast.LENGTH_SHORT).show();
                                }
                            }
                        } catch (Exception e) {
                            Log.e("Error", e.getMessage() + "__");
                        }
                    }
                });


    }





}
