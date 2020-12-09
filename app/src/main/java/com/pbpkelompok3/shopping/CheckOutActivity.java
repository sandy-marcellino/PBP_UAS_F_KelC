package com.pbpkelompok3.shopping;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.pbpkelompok3.shopping.api.ApiRequestCheckout;
import com.pbpkelompok3.shopping.api.Retroserver;
import com.pbpkelompok3.shopping.model.ResponsCheckoutModel;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckOutActivity extends AppCompatActivity implements View.OnClickListener {
    TextInputEditText txAddress, txCity, txProvince, txPostalCode;
    TextView txTotalHarga;
    String sAddress, sCity, sProvince, sPostalCode, sBuktiTf;
    Double dTotalHarga;
    Button btnBatal, btnBayar, btnBuktiTf;
    ImageView ivBuktiTf;
    int idUser;
    private Bitmap bitmap;
    final int IMG_REQUEST = 777;
    Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);

        extras = getIntent().getExtras();
        idUser = extras.getInt("id_user");
        dTotalHarga = extras.getDouble("total_harga");

        txAddress = findViewById(R.id.txtAddress);
        txCity = findViewById(R.id.txtCity);
        txProvince = findViewById(R.id.txtProvince);
        txPostalCode = findViewById(R.id.txtPostal);
        txTotalHarga = findViewById(R.id.tampilTotalHarga);
        btnBayar = findViewById(R.id.btnBayarCheck);
        btnBatal = findViewById(R.id.btnBatalC);
        btnBuktiTf = findViewById(R.id.btnOpenGalBayar);
        ivBuktiTf = findViewById(R.id.ivBuktiTf);

        txTotalHarga.setText(dTotalHarga.toString());

        btnBayar.setOnClickListener(this);
        btnBatal.setOnClickListener(this);
        btnBuktiTf.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btnOpenGalBayar:
                selectImage();
                break;
            case R.id.btnBatalC:
                gotoCart();
                break;
            case R.id.btnBayarCheck:
                if(validasi()) {
                    uploadData();
                }
                break;
        }
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
                ivBuktiTf.setImageBitmap(bitmap);
                btnBayar.setEnabled(true);
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

    private void gotoCart() {
        Intent i = new Intent(CheckOutActivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }

    private boolean validasi() {
        boolean valid = true;

        sAddress = txAddress.getText().toString();
        sCity = txCity.getText().toString();
        sProvince = txProvince.getText().toString();
        sPostalCode = txPostalCode.getText().toString();
        sBuktiTf = "data:image/jpeg;base64," + imageToString();

        if(sAddress.length() == 0) {
            txAddress.setError("Address tidak boleh kosong!");
            valid = false;
        }
        if(sCity.length() == 0) {
            txCity.setError("City tidak boleh kosong!");
            valid = false;
        }
        if(sProvince.length() == 0) {
            txProvince.setError("Province tidak boleh kosong!");
            valid = false;
        }
        if(sPostalCode.length() == 0) {
            txPostalCode.setError("Postal code tidak boleh kosong!");
            valid = false;
        }

        return valid;
    }

    private void uploadData() {
        ApiRequestCheckout api = Retroserver.getClient().create(ApiRequestCheckout.class);
        Call<ResponsCheckoutModel> sendOrder = api.sendOrder(idUser, sAddress, sCity, sProvince, sPostalCode, dTotalHarga, sBuktiTf, 1);

        sendOrder.enqueue(new Callback<ResponsCheckoutModel>() {
            @Override
            public void onResponse(Call<ResponsCheckoutModel> call, Response<ResponsCheckoutModel> response) {
                Log.d("RETOR", " response : " + response.body().getMessage().toString());
                String status = response.body().getStatus();
                if(status.equalsIgnoreCase("success")) {
                    // hapus cart


                    Toast.makeText(CheckOutActivity.this, "Checkout Berhasil", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(CheckOutActivity.this, "Checkout tidak berhasil", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponsCheckoutModel> call, Throwable t) {
                Log.d("RETOR", " failure : gagal");
            }
        });

        Intent i = new Intent(CheckOutActivity.this, CheckoutBerhasilActivity.class);
        startActivity(i);
        finish();
    }
}