package com.athome.mvp.activity_login_presenter;

import com.athome.models.UserModel;

public interface ActivityLoginView {
    void onLoginSuccess(UserModel userModel);
    void onFailed(String msg);

}
