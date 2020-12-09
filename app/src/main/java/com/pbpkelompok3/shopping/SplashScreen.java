package com.pbpkelompok3.shopping;



import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {
    private static int SPLASH_DURATION = 3000;
    private int cekLogin = 0, cekID;
    SharedPreferences sharedPreferences;
    ImageView splash_logo;
    Animation toptobottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        splash_logo = findViewById(R.id.splash_logo);
        toptobottom = AnimationUtils.loadAnimation(this, R.anim.toptobottom);
        splash_logo.setAnimation(toptobottom);

        sharedPreferences  = getSharedPreferences("login", Context.MODE_PRIVATE);
        if(sharedPreferences != null){
            cekLogin = sharedPreferences.getInt("isLogin",Context.MODE_PRIVATE);
            cekID = sharedPreferences.getInt("id", Context.MODE_PRIVATE);
            Log.d("CEK LOGIN: %d",  String.valueOf(cekLogin));
            if(cekLogin == 1){
                if(cekID == 1) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent i = new Intent(SplashScreen.this,AdminMainActivity.class);
                            startActivity(i);
                            finish();
                        }
                    },SPLASH_DURATION);
                }else {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent i = new Intent(SplashScreen.this,MainActivity.class);
                            startActivity(i);
                            finish();
                        }
                    },SPLASH_DURATION);
                }
            }else{
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent i = new Intent(SplashScreen.this,LoginActivity.class);
                        startActivity(i);
                        finish();
                    }
                },SPLASH_DURATION);
            }
        }
    }
}