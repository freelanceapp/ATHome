package com.athome.mvp.activity_menu_mvp;

import com.athome.models.MenuDataModel;

public interface ActivityMenuView {
    void onSuccess(MenuDataModel data);
    void onRemoveFavoriteSuccess();
    void onFailed(String msg);
    void onProgressShow();
    void onProgressHide();

}
