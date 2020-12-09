package com.pbpkelompok3.shopping;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;
import com.pbpkelompok3.shopping.api.ApiRequestProdukMan;
import com.pbpkelompok3.shopping.api.Retroserver;
import com.pbpkelompok3.shopping.api.UserApi;
import com.pbpkelompok3.shopping.model.ResponsModelMan;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class tambahProdukManActivity extends AppCompatActivity implements View.OnClickListener{
    TextInputEditText etNamaProduk, etHargaProduk, etDeskripsiProduk, etStokProduk;
    Button btnCreateProdukMan, btnOpenGalMan, btnUpdateProdukman, btnBatalProdukMan;
    ImageView ivProdukManAdmin;
    private Bitmap bitmap;
    final int IMG_REQUEST = 777;
    int idProduk;
    private String Image,sNamaProduk,sDetailProduk;
    private Double dHargaProduk;
    private int sStokProduk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_produk_man);

        etNamaProduk = findViewById(R.id.txtadminNamaProdukMan);
        etHargaProduk = findViewById(R.id.txtadminHargaProdukMan);
        etDeskripsiProduk = findViewById(R.id.txtadminDeskripsiProdukMan);
        etStokProduk = findViewById(R.id.txtadminStokProdukMan);
        btnCreateProdukMan = findViewById(R.id.btnCreateProdukman);
        btnOpenGalMan = findViewById(R.id.btnOpenGalMan);
        ivProdukManAdmin = findViewById(R.id.ivProdukManAdmin);
        btnUpdateProdukman = findViewById(R.id.btnUpdateProdukman);
        btnBatalProdukMan = findViewById(R.id.btnBatalProdukMan);

        Intent data = getIntent();
        idProduk = data.getIntExtra("id_produkM", 0);
        if(idProduk != 0) {
            btnCreateProdukMan.setVisibility(View.GONE);
            btnUpdateProdukman.setVisibility(View.VISIBLE);
            btnUpdateProdukman.setEnabled(false);
            etNamaProduk.setText(data.getStringExtra("nama_produkM"));
            etHargaProduk.setText(String.valueOf(data.getDoubleExtra("harga_produkM", 0)));
            etDeskripsiProduk.setText(data.getStringExtra("deskripsi_produkM"));
            etStokProduk.setText(String.valueOf(data.getIntExtra("stok", 0)));
            Glide.with(this)
                    .load(UserApi.URL_IMAGE+data.getStringExtra("gambar_produkM"))
                    .into(ivProdukManAdmin);
        }

        btnOpenGalMan.setOnClickListener(this);
        btnCreateProdukMan.setOnClickListener(this);
        btnUpdateProdukman.setOnClickListener(this);
        btnBatalProdukMan.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btnOpenGalMan:
                selectImage();
                break;
            case R.id.btnCreateProdukman:
                if(cekInputValid()) {
                    uploadData();
                }
                break;
            case R.id.btnUpdateProdukman:
                if(cekInputValid()) {
                    updateData();
                }
                break;
            case R.id.btnBatalProdukMan:
                gotoCRUDMan();
                break;
        }
    }

    private boolean cekInputValid() {
         Image = "data:image/jpeg;base64," + imageToString();
         sNamaProduk = etNamaProduk.getText().toString();
         sDetailProduk = etDeskripsiProduk.getText().toString();

         boolean valid = true;
         if(sNamaProduk.length() == 0){
             etNamaProduk.setError("Nama produk masih kosong!");
             valid = false;
         }
        if(sDetailProduk.length() == 0){
            etDeskripsiProduk.setError("Deskripsi produk masih kosong!");
            valid = false;
        }

        try {
            dHargaProduk = Double.parseDouble(etHargaProduk.getText().toString());
        }catch (Exception e){
            etHargaProduk.setError("Harga produk tidak valid!");
            valid = false;
        }

        try {
            sStokProduk = Integer.parseInt(etStokProduk.getText().toString());
        }catch (Exception e){
            etStokProduk.setError("Stok produk tidak valid!");
            valid = false;
        }

        return valid;
    }

    private void uploadData()
    {
        ApiRequestProdukMan apipman = Retroserver.getClient().create(ApiRequestProdukMan.class);
        Call<ResponsModelMan> call = apipman.sendProduk(sNamaProduk, dHargaProduk, sDetailProduk, Image, sStokProduk);
//        Log.d("RETRO", "HASIL KONVERT : " + imageToString());

        call.enqueue(new Callback<ResponsModelMan>() {
            @Override
            public void onResponse(Call<ResponsModelMan> call, Response<ResponsModelMan> response) {
                Log.d("RETRO", "response: " + response.body().getMessage());
//                Toast.makeText(tambahProdukManActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponsModelMan> call, Throwable t) {
                Log.d("RETRO", "Failure: " + "Gagal mengirim request");
            }
        });

        Intent i = new Intent(tambahProdukManActivity.this, CRUDManActivity.class);
        startActivity(i);
        finish();
    }

    private void gotoCRUDMan()
    {
        Intent i = new Intent(tambahProdukManActivity.this, CRUDManActivity.class);
        startActivity(i);
        finish();
    }

    private void updateData()
    {
        ApiRequestProdukMan api = Retroserver.getClient().create(ApiRequestProdukMan.class);
        Call<ResponsModelMan> update = api.updateProdukMan(idProduk,sNamaProduk,dHargaProduk,sDetailProduk,Image,sStokProduk);
        update.enqueue(new Callback<ResponsModelMan>() {
            @Override
            public void onResponse(Call<ResponsModelMan> call, Response<ResponsModelMan> response) {
            }

            @Override
            public void onFailure(Call<ResponsModelMan> call, Throwable t) {
                Log.d("RETRO", "Failure: " + "Gagal");
            }
        });

        Intent i = new Intent(tambahProdukManActivity.this, CRUDManActivity.class);
        startActivity(i);
        finish();
    }

    private void selectImage()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMG_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == IMG_REQUEST && resultCode == RESULT_OK && data != null)
        {
            Uri path = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                ivProdukManAdmin.setImageBitmap(bitmap);
                btnOpenGalMan.setEnabled(false);
                btnUpdateProdukman.setEnabled(true);
                btnCreateProdukMan.setEnabled(true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String imageToString()
    {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imgByte = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgByte, Base64.DEFAULT);
    }
}