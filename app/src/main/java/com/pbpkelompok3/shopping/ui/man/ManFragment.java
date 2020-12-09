package com.pbpkelompok3.shopping.ui.man;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.pbpkelompok3.shopping.R;
import com.pbpkelompok3.shopping.adapter.ManRecyclerViewAdapter;
import com.pbpkelompok3.shopping.api.UserApi;
import com.pbpkelompok3.shopping.model.ProductMan;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.android.volley.Request.Method.GET;

public class ManFragment extends Fragment {

    private RecyclerView recyclerView;
    private ManRecyclerViewAdapter adapter;
    private List<ProductMan> listProduk;

    private SearchView editSearch;
    private SwipeRefreshLayout swipeRefresh;
    private View view;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_man, container, false);
        editSearch = (SearchView) view.findViewById(R.id.searchView);
        swipeRefresh = view.findViewById(R.id.refreshMan);

        loadDaftarProduk();
        return view;
    }

    public void loadDaftarProduk(){
        setAdapter();
        swipeRefresh.setRefreshing(true);
        getProduk();
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getProduk();
            }
        });
    }

    public void setAdapter(){
        listProduk = new ArrayList<ProductMan>();
        recyclerView = view.findViewById(R.id.recycler_view_man);
        adapter = new ManRecyclerViewAdapter(view.getContext(), listProduk);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    public void getProduk(){
        RequestQueue queue = Volley.newRequestQueue(view.getContext());

        final JsonObjectRequest stringRequest = new JsonObjectRequest(GET, UserApi.URL_MAN, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    if(!listProduk.isEmpty())
                        listProduk.clear();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);

                        int id_produk           = jsonObject.optInt("id_produkM");
                        String nama_produk      = jsonObject.optString("nama_produkM");
                        Double harga_produk     = jsonObject.optDouble("harga_produkM");
                        String deskripsi_produk = jsonObject.optString("deskripsi_produkM");
                        String gambar_produk    = jsonObject.optString("gambar_produkM");
                        int stok                = jsonObject.optInt("stok");

                        ProductMan produk = new ProductMan(id_produk,nama_produk,harga_produk,deskripsi_produk,gambar_produk,stok);
                        listProduk.add(produk);
                    }
                    adapter.notifyDataSetChanged();
                    swipeRefresh.setRefreshing(false);
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(view.getContext(), error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(stringRequest);
    }
}