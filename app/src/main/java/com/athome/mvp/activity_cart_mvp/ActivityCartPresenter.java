package com.athome.mvp.activity_cart_mvp;

import android.content.Context;
import android.util.Log;

import com.athome.models.UserModel;
import com.athome.preferences.Preferences;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityCartPresenter {
    private UserModel userModel;
    private Preferences preferences;
    private CartActivityView view;
    private Context context;

    public ActivityCartPresenter(CartActivityView view, Context context) {
        this.view = view;
        this.context = context;
    }

    public void backPress() {

        view.onFinished();


    }
    public void checkdata() {

        view.onopenpay();


    }


}
