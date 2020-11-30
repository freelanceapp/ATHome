package com.athome.mvp.activity_order_checkout_mvp;

import android.content.Context;
import android.util.Log;

import com.athome.models.UserModel;
import com.athome.mvp.activity_cart_mvp.CartActivityView;
import com.athome.preferences.Preferences;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityOrderCheckoutPresenter {
    private UserModel userModel;
    private Preferences preferences;
    private OrderCheckoutActivityView view;
    private Context context;

    public ActivityOrderCheckoutPresenter(OrderCheckoutActivityView view, Context context) {
        this.view = view;
        this.context = context;
    }

    public void backPress() {

        view.onFinished();


    }



}
