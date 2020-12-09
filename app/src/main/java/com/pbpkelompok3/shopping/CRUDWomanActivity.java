package com.pbpkelompok3.shopping;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.pbpkelompok3.shopping.adapter.AdmPrdctWomanRecyclerViewAdapter;
import com.pbpkelompok3.shopping.api.ApiRequestProdukWoman;
import com.pbpkelompok3.shopping.api.Retroserver;
import com.pbpkelompok3.shopping.model.ResponsModelWoman;
import com.pbpkelompok3.shopping.model.WomanDao;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CRUDWomanActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    Button buttonNewItem;
    private RecyclerView mRecycler;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mManager;
    private SwipeRefreshLayout refreshLayout;
    private List<WomanDao> mItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c_r_u_d_woman);

        refreshLayout = findViewById(R.id.refreshWomanAdmin);
        buttonNewItem = findViewById(R.id.buttonNewItemW);
        mRecycler = (RecyclerView) findViewById(R.id.recycler_view_woman_admin);
        mManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecycler.setLayoutManager(mManager);

        buttonNewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CRUDWomanActivity.this, tambahProdukWomanActivity.class);
                startActivity(i);
                finish();
            }
        });

        getAllData();

        refreshLayout.setOnRefreshListener(this);
    }

    public void getAllData(){
        ApiRequestProdukWoman apiwoman = Retroserver.getClient().create(ApiRequestProdukWoman.class);
        Call<ResponsModelWoman> getproduk = apiwoman.getProdukWoman("data");
        getproduk.enqueue(new Callback<ResponsModelWoman>() {
            @Override
            public void onResponse(Call<ResponsModelWoman> call, Response<ResponsModelWoman> response) {
                Log.d("RETRO", "RESPONSE: " + response.body().getStatus());
                mItems = response.body().getData();

                mAdapter = new AdmPrdctWomanRecyclerViewAdapter(CRUDWomanActivity.this, mItems);
                mRecycler.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ResponsModelWoman> call, Throwable t) {
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