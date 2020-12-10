package com.athome.models;

import java.io.Serializable;
import java.util.List;

public class ProductDataModel implements Serializable {
    private List<ProductModel> data;
    private int status;
    private String message;

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public List<ProductModel> getData() {
        return data;
    }

}
