package com.athome.mvp.activity_product_details_mvp;

import com.athome.models.ProductModel;
import com.athome.models.SingleProductDataModel;

import java.util.List;

public interface ActivityProductDetailsView {

    void onSuccess(ProductModel data);

    void onFailed(String msg);

    void onProgressShow();

    void onProgressHide();

}
