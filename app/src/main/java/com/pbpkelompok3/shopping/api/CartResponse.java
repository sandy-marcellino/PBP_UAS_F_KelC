package com.pbpkelompok3.shopping.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.pbpkelompok3.shopping.model.CartDao;

import java.util.List;

public class CartResponse {
        @SerializedName("data")
        @Expose
        private List<CartDao> carts = null;

        public List<CartDao> getCarts() {
            return carts;
        }

        @SerializedName("status")
        @Expose
        private String status;

        public String getStatus() {
            return status;
        }


        @SerializedName("message")
        @Expose
        private String message;

        public String getMessage() {
            return message;
        }
}
