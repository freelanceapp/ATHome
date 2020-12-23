package com.athome.mvp.activity_product_details_mvp;

import com.athome.models.CartDataModel;
import com.athome.models.ProductModel;

import java.util.List;

public interface ActivityProductDetailsView {

    void onSuccess(ProductModel data);

    void onFailed(String msg);

    void onProgressShow();

    void onProgressHide();

    void onUserNotRegister(String msg,ProductModel productModel);

    void onFavoriteActionSuccess(ProductModel productModel);

    void onAddToMenuSuccess();

    void onCartUpdated(double totalCost, int itemCount, List<CartDataModel.CartModel> cartModelList);

    void onCartCountUpdated(int count);
    void onAmountSelectedFromCart(int amount);
}
