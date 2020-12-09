package com.pbpkelompok3.shopping;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.pbpkelompok3.shopping.adapter.AdmPrdctManRecyclerViewAdapter;
import com.pbpkelompok3.shopping.api.ApiRequestProdukMan;
import com.pbpkelompok3.shopping.api.Retroserver;
import com.pbpkelompok3.shopping.model.ManDao;
import com.pbpkelompok3.shopping.model.ResponsModelMan;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CRUDManActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    Button buttonNewItem;
    private RecyclerView mRecycler;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mManager;
    private SwipeRefreshLayout refreshLayout;
    private List<ManDao> mItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c_r_u_d_man);

        refreshLayout = findViewById(R.id.refreshManAdmin);
        buttonNewItem = findViewById(R.id.buttonNewItem);
        mRecycler = (RecyclerView) findViewById(R.id.recycler_view_man_admin);
        mManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecycler.setLayoutManager(mManager);

        buttonNewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CRUDManActivity.this, tambahProdukManActivity.class);
                startActivity(i);
                finish();
            }
        });

        getAllData();

        refreshLayout.setOnRefreshListener(this);
    }

    public void getAllData(){
        ApiRequestProdukMan apiman = Retroserver.getClient().create(ApiRequestProdukMan.class);
        Call<ResponsModelMan> getproduk = apiman.getProdukMan("data");
        getproduk.enqueue(new Callback<ResponsModelMan>() {
            @Override
            public void onResponse(Call<ResponsModelMan> call, Response<ResponsModelMan> response) {
                Log.d("RETRO", "RESPONSE: " + response.body().getStatus());
                mItems = response.body().getData();

                mAdapter = new AdmPrdctManRecyclerViewAdapter(CRUDManActivity.this, mItems);
                mRecycler.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ResponsModelMan> call, Throwable t) {
                Log.d("RETRO", "FAILURE: " + "respon gagal");
            }
        });
    }

    @Override
    public void onRefresh() {
        getAllData();
        refreshLayout.setRefreshing(false);
    }
}