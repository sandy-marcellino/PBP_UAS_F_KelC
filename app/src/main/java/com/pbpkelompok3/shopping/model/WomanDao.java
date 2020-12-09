package com.pbpkelompok3.shopping.model;

import com.google.gson.annotations.SerializedName;

public class WomanDao {
    @SerializedName("id_produkW")
    private int id;

    @SerializedName("nama_produkW")
    private String namaProduk;

    @SerializedName("harga_produkW")
    private Double hargaProduk;

    @SerializedName("deskripsi_produkW")
    private String deskripsiProduk;

    @SerializedName("gambar_produkW")
    private String gambarProduk;

    @SerializedName("stok")
    private int stokProduk;

    public WomanDao(int id, String namaProduk, Double hargaProduk, String deskripsiProduk, String gambarProduk, int stokProduk) {
        this.id = id;
        this.namaProduk = namaProduk;
        this.hargaProduk = hargaProduk;
        this.deskripsiProduk = deskripsiProduk;
        this.gambarProduk = gambarProduk;
        this.stokProduk = stokProduk;
    }

    public int getId() {
        return id;
    }

    public String getNamaProduk() {
        return namaProduk;
    }

    public Double getHargaProduk() {
        return hargaProduk;
    }

    public String getDeskripsiProduk() {
        return deskripsiProduk;
    }

    public String getGambarProduk() {
        return gambarProduk;
    }

    public int getStokProduk() {
        return stokProduk;
    }

    public String getStringHarga(){
        if(hargaProduk == 0 ){
            return "";
        }else{
            return String.valueOf(hargaProduk);
        }
    }
}
