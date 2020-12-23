package com.athome.mvp.activity_cart_mvp;


import com.athome.models.CartDataModel;
import com.athome.models.CouponDataModel;

public interface CartActivityView {
    void onFinished();
    void onCheckOut();
    void onDataSuccess(CartDataModel cartDataModel);
    void onCartItemRemoved(int pos);
    void onCostUpdate(double totalItemCost,double discount,double totalCost);
    void onFailed(String msg);
    void onCouponSuccess(CouponDataModel.CouponModel couponModel);
    void onCouponFailed();
    void onDeliveryPriceSuccess(double cost);
    void onPackagingPriceSuccess(double cost);


}
