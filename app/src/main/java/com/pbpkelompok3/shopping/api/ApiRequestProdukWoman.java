package com.pbpkelompok3.shopping.api;

import com.pbpkelompok3.shopping.model.ResponsModelMan;
import com.pbpkelompok3.shopping.model.ResponsModelWoman;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiRequestProdukWoman {
    @FormUrlEncoded
    @POST("woman")
    Call<ResponsModelWoman> sendProdukWoman(@Field("nama_produkW") String nama_produk,
                                        @Field("harga_produkW") Double harga_produk,
                                        @Field("deskripsi_produkW") String detail_produk,
                                        @Field("gambar_produkW") String foto_produk,
                                        @Field("stok") int stok);

    @GET("woman")
    Call<ResponsModelWoman> getProdukWoman(@Query("data") String data);

    @FormUrlEncoded
    @PUT("woman/{id}")
    Call<ResponsModelWoman> updateProdukWoman(@Path("id") int id,
                                          @Field("nama_produkW") String nama_produk,
                                          @Field("harga_produkW") Double harga_produk,
                                          @Field("deskripsi_produkW") String detail_produk,
                                          @Field("gambar_produkW") String gambar_produk,
                                          @Field("stok") int stok);

    @DELETE("woman/{id}")
    Call<ResponsModelWoman> deleteProduk(@Path("id") int id);
}
