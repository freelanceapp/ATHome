package com.athome.mvp.activity_my_address_mvp;

import com.athome.models.AddressModel;
import com.athome.models.ProductModel;

import java.util.List;

public interface ActivityMyAddressView {
    void onSuccess(List<AddressModel> data);
    void onFailed(String msg);
    void onProgressShow();
    void onProgressHide();
    void onRemovedSuccess();


}
