package com.pbpkelompok3.shopping.model;

import com.google.gson.annotations.SerializedName;

public class ManDao {

    @SerializedName("id_produkM")
    private int id;

    @SerializedName("nama_produkM")
    private String namaProduk;

    @SerializedName("harga_produkM")
    private Double hargaProduk;

    @SerializedName("deskripsi_produkM")
    private String deskripsiProduk;

    @SerializedName("gambar_produkM")
    private String gambarProduk;

    @SerializedName("stok")
    private int stokProduk;

    public ManDao(int id, String namaProduk, Double hargaProduk, String deskripsiProduk, String gambarProduk, int stokProduk) {
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

    public void setNamaProduk(String namaProduk) {
        this.namaProduk = namaProduk;
    }

    public void setHargaProduk(Double hargaProduk) {
        this.hargaProduk = hargaProduk;
    }

    public void setDeskripsiProduk(String deskripsiProduk) {
        this.deskripsiProduk = deskripsiProduk;
    }

    public void setGambarProduk(String gambarProduk) {
        this.gambarProduk = gambarProduk;
    }

    public void setStokProduk(int stokProduk) {
        this.stokProduk = stokProduk;
    }

    public String getStringHarga(){
        if(hargaProduk == 0 ){
            return "";
        }else{
            return String.valueOf(hargaProduk);
        }
    }


    public String getGambarProduk() {
        return gambarProduk;
    }

    public int getStokProduk() {
        return stokProduk;
    }
}
