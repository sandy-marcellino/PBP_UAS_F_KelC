package com.pbpkelompok3.shopping.api;

import com.pbpkelompok3.shopping.model.ResponsModelMan;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiRequestProdukMan {
    @FormUrlEncoded
    @POST("man")
    Call<ResponsModelMan> sendProduk(@Field("nama_produkM") String nama_produk,
                                     @Field("harga_produkM") Double harga_produk,
                                     @Field("deskripsi_produkM") String detail_produk,
                                     @Field("gambar_produkM") String gambar_produk,
                                     @Field("stok") int stok);

    @GET("man")
    Call<ResponsModelMan> getProdukMan(@Query("data") String data);

    @FormUrlEncoded
    @PUT("man/{id}")
    Call<ResponsModelMan> updateProdukMan(@Path("id") int id,
                                          @Field("nama_produkM") String nama_produk,
                                          @Field("harga_produkM") Double harga_produk,
                                          @Field("deskripsi_produkM") String detail_produk,
                                          @Field("gambar_produkM") String gambar_produk,
                                          @Field("stok") int stok);

    @DELETE("man/{id}")
    Call<ResponsModelMan> deleteProduk(@Path("id") int id);
}
