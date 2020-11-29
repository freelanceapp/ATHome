package com.athome.mvp.activity_product_details_mvp;

import android.app.FragmentManager;
import android.content.Context;

import com.athome.mvp.activity_sign_up_mvp.ActivitySignUpView;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.athome.models.SignUpModel;

public class ActivityProductDetailsPresenter {
    private Context context;
    private ActivityProductDetailsView view;

    private DatePickerDialog datePickerDialog;



    public ActivityProductDetailsPresenter(Context context, ActivityProductDetailsView view)
    {
        this.context = context;
        this.view = view;


    }



}
