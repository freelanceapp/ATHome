package com.athome.mvp.fragment_home_mvp;

import com.athome.models.ProductModel;
import com.athome.models.SingleCategoryModel;
import com.athome.models.SliderDataModel;

import java.util.List;

public interface FragmentHomeView {
    void onSliderSuccess(List<SliderDataModel.SliderModel> sliderModelList);
    void onSuccess(List<SingleCategoryModel> data);
    void onFeaturedProductSuccess(List<ProductModel> data);
    void onMostSellerSuccess(List<ProductModel> data);
    void onOfferSuccess(List<ProductModel> data);
    void onFailed(String msg);
    void onUserNotRegister(String msg,ProductModel productModel,int position,String type);
    void onFavoriteActionSuccess(ProductModel productModel,int position,String type);
    void onProgressSliderShow();
    void onProgressSliderHide();
    void onProgressCategoryShow();
    void onProgressCategoryHide();
    void onProgressFeaturedProductsShow();
    void onProgressFeaturedProductsHide();
    void onProgressMostSellerShow();
    void onProgressMostSellerHide();
    void onProgressOfferShow();
    void onProgressOfferHide();

}
