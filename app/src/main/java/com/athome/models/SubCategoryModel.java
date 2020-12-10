package com.athome.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class SubCategoryModel implements Serializable {

    private String status;
    private String slug;
    private String name;
    private String category_id;
    private int id;
    private List<ChildModel> childs;

    public String getStatus() {
        return status;
    }

    public String getSlug() {
        return slug;
    }

    public String getName() {
        return name;
    }

    public String getCategory_id() {
        return category_id;
    }

    public int getId() {
        return id;
    }

    public List<ChildModel> getChilds() {
        return childs;
    }
}
