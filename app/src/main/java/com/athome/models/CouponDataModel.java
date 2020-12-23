package com.athome.models;

import java.io.Serializable;

public class CouponDataModel implements Serializable {
    private CouponModel data;
    private int status;
    public static class CouponModel implements Serializable{
        private int id;
        private String code;
        private String price;
        private String status;

        public int getId() {
            return id;
        }

        public String getCode() {
            return code;
        }

        public String getPrice() {
            return price;
        }

        public String getStatus() {
            return status;
        }
    }

    public CouponModel getData() {
        return data;
    }

    public int getStatus() {
        return status;
    }
}
