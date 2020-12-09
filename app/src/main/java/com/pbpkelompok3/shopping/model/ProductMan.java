package com.pbpkelompok3.shopping.model;

import java.io.Serializable;

public class ProductMan implements Serializable {
    public int id_produkM;
    public String nama_produkM;
    public double harga_produkM;
    public String deskripsi_produkM;
    public String gambar_produkM;
    public int stok;
    public String kategori;

    public ProductMan(){}

    public ProductMan(int id_produkM, String nama_produkM, double harga_produkM, String deskripsi_produkM, String gambar_produkM, int stok) {
        this.id_produkM = id_produkM;
        this.nama_produkM = nama_produkM;
        this.harga_produkM = harga_produkM;
        this.deskripsi_produkM = deskripsi_produkM;
        this.gambar_produkM = gambar_produkM;
        this.stok = stok;
    }

    public int getId_produkM() {
        return id_produkM;
    }

    public void setId_produkM(int id_produkM) {
        this.id_produkM = id_produkM;
    }

    public String getNama_produkM() {
        return nama_produkM;
    }

    public void setNama_produkM(String nama_produkM) {
        this.nama_produkM = nama_produkM;
    }

    public double getHarga_produkM() {
        return harga_produkM;
    }

    public String getStringHarga(){
        if(harga_produkM == 0 ){
            return "";
        }else{
            return String.valueOf(harga_produkM);
        }
    }

    public String getKategori() {
        return kategori;
    }

    public void setHarga_produkM(double harga_produkM) {
        this.harga_produkM = harga_produkM;
    }

    public String getDeskripsi_produkM() {
        return deskripsi_produkM;
    }

    public void setDeskripsi_produkM(String deskripsi_produkM) {
        this.deskripsi_produkM = deskripsi_produkM;
    }

    public String getGambar_produkM() {
        return gambar_produkM;
    }

    public void setGambar_produkM(String gambar_produkM) {
        this.gambar_produkM = gambar_produkM;
    }

    public int getStok() {
        return stok;
    }

    public void setStok(int stok) {
        this.stok = stok;
    }
}
