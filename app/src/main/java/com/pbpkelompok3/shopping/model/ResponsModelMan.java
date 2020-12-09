package com.pbpkelompok3.shopping.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponsModelMan {
    @SerializedName("data")
    @Expose
    private List<ManDao> mans = null;
    public List<ManDao> getData(){
        return mans;
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
