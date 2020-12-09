package com.pbpkelompok3.shopping.api;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiRequestUser {
    @POST("register")
    @FormUrlEncoded
    Call<UserResponse> register(@Field("first_name") String first_name,
                                @Field("last_name") String last_name,
                                @Field("email") String email,
                                @Field("password") String password,
                                @Field("no_telp") String no_telp);

    @POST("login")
    @FormUrlEncoded
    Call<UserResponse> login(@Field("email") String email,
                             @Field("password") String password);

    @GET("user/{id}")
    Call<UserResponse> getUserById(@Path("id") int id,
                                   @Query("data") String data);
}
