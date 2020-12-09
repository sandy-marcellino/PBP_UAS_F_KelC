package com.pbpkelompok3.shopping;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class CheckoutBerhasilActivity extends AppCompatActivity {
    Button btnKembali;
    TextView tvThanks, tvCheckout;
    Animation bottomtotop, toptobottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout_berhasil);

        btnKembali = findViewById(R.id.kembali);
        tvThanks = findViewById(R.id.tvThanks);
        tvCheckout = findViewById(R.id.tvCheckout);

        bottomtotop = AnimationUtils.loadAnimation(this, R.anim.bottomtotop);
        toptobottom = AnimationUtils.loadAnimation(this, R.anim.toptobottom);

        tvCheckout.startAnimation(toptobottom);
        tvThanks.startAnimation(toptobottom);
        btnKembali.startAnimation(bottomtotop);

        btnKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CheckoutBerhasilActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}