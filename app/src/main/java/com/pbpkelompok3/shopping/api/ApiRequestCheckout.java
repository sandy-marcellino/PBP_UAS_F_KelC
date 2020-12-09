package com.pbpkelompok3.shopping.api;

import com.pbpkelompok3.shopping.model.ResponsCheckoutModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiRequestCheckout {
    @FormUrlEncoded
    @POST("orders")
    Call<ResponsCheckoutModel> sendOrder(@Field("id_user") int idUser,
                                         @Field("address") String address,
                                         @Field("city") String city,
                                         @Field("province") String province,
                                         @Field("postal_code") String postalCode,
                                         @Field("total_harga") Double totalHarga,
                                         @Field("bukti_tf") String buktiTf,
                                         @Field("isPay") int isPay);
}
