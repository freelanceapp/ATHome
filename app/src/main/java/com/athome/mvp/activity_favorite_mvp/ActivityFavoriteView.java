package com.athome.mvp.activity_favorite_mvp;

import com.athome.models.ProductModel;

import java.util.List;

public interface ActivityFavoriteView {
    void onSuccess(List<ProductModel> data);
    void onRemoveFavoriteSuccess();
    void onFailed(String msg);
    void showProgressDialog();
    void hideProgressDialog();
    void onProgressShow();
    void onProgressHide();

}
