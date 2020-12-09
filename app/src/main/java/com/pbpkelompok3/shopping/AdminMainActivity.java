package com.pbpkelompok3.shopping;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class AdminMainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private SharedPreferences preferences;
    Animation toptobottom, bottomtotop;
    TextView hiAdmin, hiAdmin2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

        hiAdmin = findViewById(R.id.hiAdmin);
        hiAdmin2 = findViewById(R.id.hiAdmin2);

        toptobottom = AnimationUtils.loadAnimation(this, R.anim.toptobottom);
        bottomtotop = AnimationUtils.loadAnimation(this, R.anim.bottomtotop);

        hiAdmin.setAnimation(toptobottom);
        hiAdmin2.setAnimation(bottomtotop);

        toolbar = findViewById(R.id.admintoolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_admin, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent i;
        switch (item.getItemId()) {
            case R.id.maleAdmin:
                i = new Intent(AdminMainActivity.this, CRUDManActivity.class);
                startActivity(i);
                return true;
            case R.id.femaleAdmin:
                i = new Intent(AdminMainActivity.this, CRUDWomanActivity.class);
                startActivity(i);
                return true;
            case R.id.keluarAdmin:
                preferences = getSharedPreferences("login", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("email","");
                editor.putInt("isLogin",0);
                editor.commit();
                Intent intent= new Intent(AdminMainActivity.this, LoginActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}