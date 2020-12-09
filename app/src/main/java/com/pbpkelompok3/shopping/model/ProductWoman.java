package com.pbpkelompok3.shopping.model;

public class ProductWoman{
    public int id_produkW;
    public String nama_produkW;
    public double harga_produkW;
    public String deskripsi_produkW;
    public String gambar_produkW;
    public int stok;
    public String kategori;

    public ProductWoman(){}
    public ProductWoman(int id_produkW, String nama_produkW, double harga_produkW, String deskripsi_produkW, String gambar_produkW, int stok) {
        this.id_produkW = id_produkW;
        this.nama_produkW = nama_produkW;
        this.harga_produkW = harga_produkW;
        this.deskripsi_produkW = deskripsi_produkW;
        this.gambar_produkW = gambar_produkW;
        this.stok = stok;
    }

    public int getId_produkW() {
        return id_produkW;
    }

    public void setId_produkW(int id_produkW) {
        this.id_produkW = id_produkW;
    }

    public String getNama_produkW() {
        return nama_produkW;
    }

    public void setNama_produkW(String nama_produkW) {
        this.nama_produkW = nama_produkW;
    }

    public double getHarga_produkW() {
        return harga_produkW;
    }

    public void setHarga_produkW(double harga_produkW) {
        this.harga_produkW = harga_produkW;
    }

    public String getStringHarga(){
        if(harga_produkW == 0 ){
            return "";
        }else{
            return String.valueOf(harga_produkW);
        }
    }

    public String getKategori() {
        return kategori;
    }

    public String getDeskripsi_produkW() {
        return deskripsi_produkW;
    }

    public void setDeskripsi_produkW(String deskripsi_produkW) {
        this.deskripsi_produkW = deskripsi_produkW;
    }

    public String getGambar_produkW() {
        return gambar_produkW;
    }

    public void setGambar_produkW(String gambar_produkW) {
        this.gambar_produkW = gambar_produkW;
    }

    public int getStok() {
        return stok;
    }

    public void setStok(int stok) {
        this.stok = stok;
    }
}
