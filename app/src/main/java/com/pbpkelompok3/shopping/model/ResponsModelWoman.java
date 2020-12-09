package com.pbpkelompok3.shopping.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponsModelWoman {
    @SerializedName("data")
    @Expose
    private List<WomanDao> womans = null;
    public List<WomanDao> getData(){
        return womans;
    }

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
