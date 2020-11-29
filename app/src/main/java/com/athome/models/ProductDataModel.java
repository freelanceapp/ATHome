package com.athome.models;

import java.io.Serializable;
import java.util.List;

public class ProductDataModel implements Serializable {
    private List<SingleProductDataModel> data;

    public List<SingleProductDataModel> getData() {
        return data;
    }

}
