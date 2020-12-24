package com.athome.mvp.fragment_order_mvp;

import com.athome.models.CommentModel;
import com.athome.models.OrderModel;

import java.util.List;

public interface FragmentOrderView {
    void onSuccess(List<OrderModel> data);
    void onFailed(String msg);
    void onProgressShow();
    void onProgressHide();

}
