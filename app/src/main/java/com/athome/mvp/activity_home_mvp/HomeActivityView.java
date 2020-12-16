package com.athome.mvp.activity_home_mvp;

public interface HomeActivityView {
    void onHomeFragmentSelected();
    void onCategoryFragmentSelected();
    void onNavigateToLoginActivity();
    void onFinished();
    void onNavigateToCartActivity();
    void onFailed(String msg);
}
