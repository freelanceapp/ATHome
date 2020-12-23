package com.athome.mvp.activity_cart_mvp;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.athome.R;
import com.athome.models.AddressModel;
import com.athome.models.CartDataModel;
import com.athome.models.CouponDataModel;
import com.athome.models.LogoutModel;
import com.athome.models.ProductModel;
import com.athome.models.UserModel;
import com.athome.preferences.Preferences;
import com.athome.remote.Api;
import com.athome.share.Common;
import com.athome.tags.Tags;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityCartPresenter {
    private UserModel userModel;
    private Preferences preferences;
    private CartActivityView view;
    private Context context;
    private CartDataModel cartDataModel;

    public ActivityCartPresenter(CartActivityView view, Context context) {
        this.view = view;
        this.context = context;
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(context);
        cartDataModel = preferences.getCartData(context);


    }


    public void update_cart(CartDataModel.CartModel cartModel, int amount)
    {
        int pos = getCartItemPos(cartModel);
        if (pos!=-1){
            cartModel.setAmount(amount);
            List<CartDataModel.CartModel> cartModelList = cartDataModel.getCartModelList();
            cartModelList.set(pos,cartModel);
            cartDataModel.setCartModelList(cartModelList);
            calculateTotalCost();
        }

    }

    public void getCartData(){
        view.onDataSuccess(cartDataModel);
        calculateTotalCost();
    }

    public void removeCartItem(CartDataModel.CartModel cartModel)
    {
        int pos  = getCartItemPos(cartModel);
        if (pos!=-1){
            List<CartDataModel.CartModel> cartModelList = cartDataModel.getCartModelList();
            cartModelList.remove(pos);
            cartDataModel.setCartModelList(cartModelList);
            preferences.createUpdateCartData(context,cartDataModel);
            calculateTotalCost();
            view.onCartItemRemoved(pos);

        }
    }

    private int getCartItemPos(CartDataModel.CartModel cartModel)
    {

        int pos = -1;
        for (int index=0;index<cartDataModel.getCartModelList().size();index++){
            CartDataModel.CartModel model = cartDataModel.getCartModelList().get(index);


            if (model.getId().equals(cartModel.getId())){
                pos = index;
                return pos;
            }
        }

        return pos;
    }

    private void calculateTotalCost()
    {
        double total =0.0;
        for (CartDataModel.CartModel cartModel:cartDataModel.getCartModelList()){
            total += cartModel.getCost()*cartModel.getAmount();
        }


        cartDataModel.setTotal(total);
        double totalAfterDiscount = (total-cartDataModel.getCoupon_discount())+cartDataModel.getDelivery_cost()+cartDataModel.getPackaging_cost();
        preferences.createUpdateCartData(context,cartDataModel);
        view.onCostUpdate(total,cartDataModel.getCoupon_discount(),totalAfterDiscount);

    }

    public void checkCoupon(String code)
    {
        ProgressDialog dialog = Common.createProgressDialog(context,context.getString(R.string.wait));
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        Api.getService(Tags.base_url)
                .checkCouponData(code)
                .enqueue(new Callback<CouponDataModel>() {
                    @Override
                    public void onResponse(Call<CouponDataModel> call, Response<CouponDataModel> response) {
                        dialog.dismiss();
                        if (response.isSuccessful()) {
                            if (response.body() != null&&response.body().getStatus()==200) {
                                if (response.body().getData()!=null){
                                    cartDataModel.setCoupon_code(response.body().getData().getCode());
                                    cartDataModel.setCoupon_discount(Double.parseDouble(response.body().getData().getPrice()));
                                    calculateTotalCost();
                                    view.onCouponSuccess(response.body().getData());
                                }else {
                                    view.onCouponFailed();

                                }

                            }


                        } else {

                            dialog.dismiss();
                            try {
                                Log.e("errorNotCode", response.code() + "__" + response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            if (response.code() == 500) {
                                Toast.makeText(context, "Server Error", Toast.LENGTH_SHORT).show();
                            } else {
                                view.onFailed(context.getString(R.string.failed));

                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<CouponDataModel> call, Throwable t) {
                        try {
                            dialog.dismiss();
                            if (t.getMessage() != null) {
                                Log.e("error_not_code", t.getMessage() + "__");

                                if (t.getMessage().toLowerCase().contains("failed to connect") || t.getMessage().toLowerCase().contains("unable to resolve host")) {
                                    view.onFailed(context.getString(R.string.something));
                                } else {
                                    view.onFailed(context.getString(R.string.failed));

                                }
                            }
                        } catch (Exception e) {
                            Log.e("Error", e.getMessage() + "__");
                        }
                    }
                });
    }
    public void backPress()
    {

        view.onFinished();


    }
    public void checkOut() {

        view.onCheckOut();


    }

    public void updateAddress(AddressModel addressModel){
        if (cartDataModel!=null){
            cartDataModel.setAddress(addressModel.getAddress());
            cartDataModel.setPhone(addressModel.getPhone());
        }
    }

    public void updateDelivery(int delivery_type,int packaging_type){
        if (cartDataModel!=null){
            if (delivery_type==1){
                cartDataModel.setDelivery_cost(50);
                view.onDeliveryPriceSuccess(50);
            }else {
                view.onDeliveryPriceSuccess(0);
            }

            if (packaging_type==1){
                cartDataModel.setPackaging_cost(15);
                view.onPackagingPriceSuccess(15);
            }else {
                view.onPackagingPriceSuccess(0);

            }
        }

        calculateTotalCost();
    }



}
