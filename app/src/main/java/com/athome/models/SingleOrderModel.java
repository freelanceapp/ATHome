package com.athome.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SingleOrderModel implements Serializable {
    @SerializedName(value = "data", alternate = {"order"})
    private OrderModel data;
    private int status;

    public OrderModel getOrder() {
        return data;
    }

    public int getStatus() {
        return status;
    }
}
