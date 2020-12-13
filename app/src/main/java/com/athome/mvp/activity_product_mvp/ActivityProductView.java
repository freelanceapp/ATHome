package com.athome.mvp.activity_product_mvp;

import com.athome.models.ProductModel;

import java.util.List;

public interface ActivityProductView {
    void onSuccess(List<ProductModel> data);
    void onFailed(String msg);
    void onProgressShow();
    void onProgressHide();

}
