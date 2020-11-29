package com.athome.mvp.activity_sign_up_mvp;

import android.app.FragmentManager;
import android.content.Context;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.athome.models.SignUpModel;

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
                sign_up_with_image(signUpModel);

          //  }
        }
    }
    public void showDateDialog(FragmentManager fragmentManager){
        try {
            datePickerDialog.show(fragmentManager,"");

        }catch (Exception e){}
    }

    private void sign_up_with_image(SignUpModel signUpModel) {

    }

    private void sign_up_without_image(SignUpModel signUpModel) {


    }


}
