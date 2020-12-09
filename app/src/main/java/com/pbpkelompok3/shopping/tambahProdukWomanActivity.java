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
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;
import com.pbpkelompok3.shopping.api.ApiRequestProdukMan;
import com.pbpkelompok3.shopping.api.ApiRequestProdukWoman;
import com.pbpkelompok3.shopping.api.Retroserver;
import com.pbpkelompok3.shopping.api.UserApi;
import com.pbpkelompok3.shopping.model.ResponsModelMan;
import com.pbpkelompok3.shopping.model.ResponsModelWoman;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class tambahProdukWomanActivity extends AppCompatActivity implements View.OnClickListener{
    TextInputEditText etNamaProduk, etHargaProduk, etDeskripsiProduk, etStokProduk;
    Button btnCreateProdukWoman, btnOpenGalWoman, btnUpdateProdukWoman, btnBatalProdukWoman;
    ImageView ivProdukWomanAdmin;
    private Bitmap bitmap;
    final int IMG_REQUEST = 777;
    int idProduk;
    private String Image,sNamaProduk,sDetailProduk;
    private Double dHargaProduk;
    private int sStokProduk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_produk_woman);

        etNamaProduk = findViewById(R.id.txtadminNamaProdukWoman);
        etHargaProduk = findViewById(R.id.txtadminHargaProdukWoman);
        etDeskripsiProduk = findViewById(R.id.txtadminDeskripsiProdukWoman);
        etStokProduk = findViewById(R.id.txtadminStokProdukWoman);
        btnCreateProdukWoman = findViewById(R.id.btnCreateProdukWoman);
        btnOpenGalWoman = findViewById(R.id.btnOpenGalWoman);
        ivProdukWomanAdmin = findViewById(R.id.ivProdukWomanAdmin);
        btnUpdateProdukWoman = findViewById(R.id.btnUpdateProdukWoman);
        btnBatalProdukWoman = findViewById(R.id.btnBatalProdukWoman);

        Intent data = getIntent();
        idProduk = data.getIntExtra("id_produkW", 0);
        if(idProduk != 0) {
            btnCreateProdukWoman.setVisibility(View.GONE);
            btnUpdateProdukWoman.setVisibility(View.VISIBLE);
            btnUpdateProdukWoman.setEnabled(false);
            etNamaProduk.setText(data.getStringExtra("nama_produkW"));
            etHargaProduk.setText(String.valueOf(data.getDoubleExtra("harga_produkW", 0)));
            etDeskripsiProduk.setText(data.getStringExtra("deskripsi_produkW"));
            etStokProduk.setText(String.valueOf(data.getIntExtra("stok", 0)));
            Glide.with(this)
                    .load(UserApi.URL_IMAGE+data.getStringExtra("gambar_produkW"))
                    .into(ivProdukWomanAdmin);
        }

        btnOpenGalWoman.setOnClickListener(this);
        btnCreateProdukWoman.setOnClickListener(this);
        btnUpdateProdukWoman.setOnClickListener(this);
        btnBatalProdukWoman.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btnOpenGalWoman:
                selectImage();
                break;
            case R.id.btnCreateProdukWoman:
                if(cekInputValid()) {
                    uploadData();
                }
                break;
            case R.id.btnUpdateProdukWoman:
                if(cekInputValid()) {
                    updateData();
                }
                break;
            case R.id.btnBatalProdukWoman:
                goCRUDWoman();
                break;
        }
    }

    private void uploadData()
    {
        ApiRequestProdukWoman apipwoman = Retroserver.getClient().create(ApiRequestProdukWoman.class);
        Call<ResponsModelWoman> call = apipwoman.sendProdukWoman(sNamaProduk, dHargaProduk, sDetailProduk, Image, sStokProduk);
        Log.d("RETRO", "HASIL KONVERT : " + Image);

        call.enqueue(new Callback<ResponsModelWoman>() {
            @Override
            public void onResponse(Call<ResponsModelWoman> call, Response<ResponsModelWoman> response) {
                Log.d("RETRO", "response: " + response.body().toString());
            }

            @Override
            public void onFailure(Call<ResponsModelWoman> call, Throwable t) {
                Log.d("RETRO", "Failure: " + "Gagal mengirim request");
            }
        });

        Intent i = new Intent(tambahProdukWomanActivity.this, CRUDWomanActivity.class);
        startActivity(i);
        finish();
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

    private void goCRUDWoman()
    {
        Intent i = new Intent(tambahProdukWomanActivity.this, CRUDWomanActivity.class);
        startActivity(i);
        finish();
    }

    private void updateData()
    {
        String Image = "data:image/jpeg;base64," + imageToString();
        ApiRequestProdukWoman api = Retroserver.getClient().create(ApiRequestProdukWoman.class);
        Call<ResponsModelWoman> update = api.updateProdukWoman(idProduk,
                etNamaProduk.getText().toString(),
                Double.parseDouble(etHargaProduk.getText().toString()),
                etDeskripsiProduk.getText().toString(),
                Image,
                Integer.parseInt(etStokProduk.getText().toString()));
        update.enqueue(new Callback<ResponsModelWoman>() {
            @Override
            public void onResponse(Call<ResponsModelWoman> call, Response<ResponsModelWoman> response) {
                Toast.makeText(tambahProdukWomanActivity.this, "Produk berhasil diupdate", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponsModelWoman> call, Throwable t) {
                Log.d("RETRO", "Failure: " + "Gagal");
            }
        });

        Intent i = new Intent(tambahProdukWomanActivity.this, CRUDWomanActivity.class);
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
                ivProdukWomanAdmin.setImageBitmap(bitmap);
                btnOpenGalWoman.setEnabled(false);
                btnCreateProdukWoman.setEnabled(true);
                btnUpdateProdukWoman.setEnabled(true);
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