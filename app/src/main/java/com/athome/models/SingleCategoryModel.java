package com.athome.models;

import java.io.Serializable;
import java.util.List;

public class SingleCategoryModel implements Serializable {


   private int id;
   private String name;
   private String slug;
   private String status;
   private String photo;
   private String is_featured;
   private String image;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSlug() {
        return slug;
    }

    public String getStatus() {
        return status;
    }

    public String getPhoto() {
        return photo;
    }

    public String getIs_featured() {
        return is_featured;
    }

    public String getImage() {
        return image;
    }
}
