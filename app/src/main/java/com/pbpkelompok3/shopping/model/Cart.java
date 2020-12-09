package com.pbpkelompok3.shopping.model;

public class Cart {
    private int id_cart, id_productCart, id_userCart, isPay=0;
    private double totalHarga;
    private String kategori,gambar, nama_produk;

    public Cart(int id_cart, int id_productCart, int id_userCart, double totalHarga,String kategori, String nama_produk, String gambar) {
        this.id_cart = id_cart;
        this.id_productCart = id_productCart;
        this.id_userCart = id_userCart;
        this.totalHarga = totalHarga;
        this.kategori = kategori;
        this.nama_produk = nama_produk;
        this.gambar  = gambar;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public String getNama_produk() {
        return nama_produk;
    }

    public void setNama_produk(String nama_produk) {
        this.nama_produk = nama_produk;
    }

    public int getId_cart() {
        return id_cart;
    }

    public void setId_cart(int id_cart) {
        this.id_cart = id_cart;
    }

    public int getId_productCart() {
        return id_productCart;
    }

    public void setId_productCart(int id_productCart) {
        this.id_productCart = id_productCart;
    }

    public int getId_userCart() {
        return id_userCart;
    }

    public void setId_userCart(int id_userCart) {
        this.id_userCart = id_userCart;
    }

    public int getIsPay() {
        return isPay;
    }

    public void setIsPay(int isPay) {
        this.isPay = isPay;
    }

    public double getTotalHarga() {
        return totalHarga;
    }

    public void setTotalHarga(double totalHarga) {
        this.totalHarga = totalHarga;
    }
}
