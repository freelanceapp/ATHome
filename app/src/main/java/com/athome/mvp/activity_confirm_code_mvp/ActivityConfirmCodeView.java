package com.athome.mvp.activity_confirm_code_mvp;


import com.athome.models.UserModel;

public interface ActivityConfirmCodeView {
    void onCounterStarted(String time);
    void onCounterFinished();
    void onCodeFailed(String msg);
    void onUserFound(UserModel userModel);
    void onUserNoFound();
}
