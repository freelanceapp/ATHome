package com.athome.mvp.activity_sign_up_mvp;


import com.athome.models.UserModel;

public interface ActivitySignUpView {
    void onSuccess(UserModel userModel);
    void onFailed(String msg);

}
