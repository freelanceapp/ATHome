package com.athome.mvp.activity_order_details_mvp;

import com.athome.models.CartDataModel;
import com.athome.models.ProductModel;
import com.athome.models.SingleOrderModel;

import java.util.List;

public interface ActivityOrderDetailsView {

    void onSuccess(SingleOrderModel data);

    void onFailed(String msg);

    void onProgressShow();

    void onProgressHide();


}
