package com.athome.mvp.fragment_category_mvp;

import com.athome.models.ProductModel;
import com.athome.models.SingleCategoryModel;
import com.athome.models.SliderDataModel;
import com.athome.models.SubCategoryModel;

import java.util.List;

public interface FragmentCategoryView {
    void onSuccess(List<SingleCategoryModel> data);
    void onSubCategorySuccess(List<SubCategoryModel> data);
    void onFailed(String msg);
    void onProgressCategoryShow();
    void onProgressCategoryHide();
    void onProgressSubCategoryShow();
    void onProgressSubCategoryHide();
}
