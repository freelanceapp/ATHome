package com.athome.mvp.activity_sign_up_mvp;


import com.athome.models.UserModel;

public interface ActivitySignUpView {
    void onSignUpValid(UserModel userModel);
    void onFailed(String msg);
    void onServer();
    void onLoad();
    void onFinishload();
    void onnotconnect(String msg);
}
