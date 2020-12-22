package com.athome.models;

import java.io.Serializable;
import java.util.List;

public class MenuDataModel implements Serializable {
    private List<MenuModel> data;
    private int status;
    public List<MenuModel> getData() {
        return data;
    }

    public int getStatus() {
        return status;
    }
}
