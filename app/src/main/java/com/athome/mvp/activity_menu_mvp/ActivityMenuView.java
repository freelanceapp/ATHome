package com.athome.mvp.activity_menu_mvp;

import com.athome.models.MenuModel;
import com.athome.models.ProductModel;

import java.util.List;

public interface ActivityMenuView {
    void onSuccess(List<MenuModel> data);
    void onRemoveFavoriteSuccess();
    void onFailed(String msg);
    void onProgressShow();
    void onProgressHide();

}
