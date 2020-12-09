package com.pbpkelompok3.shopping.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.pbpkelompok3.shopping.database.UserDao;

import java.util.List;

public class UserResponse {
    @SerializedName("data")
    @Expose
    private List<UserDao> users = null;

    public List<UserDao> getUsers(){
        return users;
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


//
//    public void setUsers(List<UserDao> users){
//        this.users = users;
//    }
//
//    public void setMessage(String message) {
//        this.message = message;
//    }
}
