package com.pbpkelompok3.shopping.model;

import com.google.gson.annotations.SerializedName;

public class CartDao {

    @SerializedName("id_Cart")
    private int id;

    @SerializedName("id_produkCart")
    private int idProdukCart;

    @SerializedName("id_userCart")
    private int idUserCart;

    @SerializedName("total_harga")
    private double total;

    @SerializedName("isPay")
    private int isPay;

    @SerializedName("kategori")
    private String kategori;

    @SerializedName("nama_produk")
    private String nama;

    @SerializedName("gambar")
    private String gambar;

    public CartDao(int id, int idProdukCart, int idUserCart, double total, int isPay, String kategori,String nama, String gambar) {
        this.nama = nama;
        this.gambar = gambar;
        this.id = id;
        this.idProdukCart = idProdukCart;
        this.idUserCart = idUserCart;
        this.total = total;
        this.isPay = isPay;
        this.kategori = kategori;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdProdukCart() {
        return idProdukCart;
    }

    public void setIdProdukCart(int idProdukCart) {
        this.idProdukCart = idProdukCart;
    }

    public int getIdUserCart() {
        return idUserCart;
    }

    public void setIdUserCart(int idUserCart) {
        this.idUserCart = idUserCart;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getIsPay() {
        return isPay;
    }

    public void setIsPay(int isPay) {
        this.isPay = isPay;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }
}
