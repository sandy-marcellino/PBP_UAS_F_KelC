package com.pbpkelompok3.shopping.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ResponsCheckoutModel {
    @SerializedName("status")
    @Expose
    private String status;
    public String getStatus(){
        return status;
    }

    @SerializedName("message")
    @Expose
    private String message;

    public String getMessage(){
        return message;
    }
}
