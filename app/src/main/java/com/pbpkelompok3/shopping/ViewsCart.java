package com.pbpkelompok3.shopping;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.pbpkelompok3.shopping.adapter.AdapterCart;
import com.pbpkelompok3.shopping.api.UserApi;
import com.pbpkelompok3.shopping.model.Cart;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.android.volley.Request.Method.GET;

public class ViewsCart extends Fragment {
    private RecyclerView recyclerView;
    private AdapterCart adapter;
    private List<Cart> listCart;

    private Button btnBayar;
    private TextView totalBiaya;
    private View view;
    private SharedPreferences preferences;
    private int idUser;
    private double totalHarga=0;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_views_cart, container, false);

        preferences  = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
        idUser = preferences.getInt("id", Context.MODE_PRIVATE);
        loadCart();

        btnBayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), CheckOutActivity.class);
                i.putExtra("id_user", idUser);
                i.putExtra("total_harga", totalHarga);
                startActivity(i);
            }
        });

        return view;
    }

    public void loadCart(){
        getCart();
        setAdapter();
    }

    public void setAdapter(){
        listCart = new ArrayList<Cart>();
        btnBayar = view.findViewById(R.id.btnBayarCart);
        totalBiaya = view.findViewById(R.id.totalBiayaCart);
        recyclerView = view.findViewById(R.id.recycler_view_cart);
        adapter = new AdapterCart(view.getContext(), listCart,
                new AdapterCart.deleteItemListener() {
                    @Override
                    public void deleteItem(Boolean delete) {
                        if(delete){
                            loadCart();
                        }
                    }
                });
        int orientation = getResources().getConfiguration().orientation;
        int gridData = 1;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            gridData = 2;
        }
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(view.getContext(),gridData);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    public void getCart(){
        RequestQueue queue = Volley.newRequestQueue(view.getContext());

        final JsonObjectRequest stringRequest = new JsonObjectRequest(GET, UserApi.URL_CART + idUser, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    if(!listCart.isEmpty())
                        listCart.clear();
                    totalHarga = response.getDouble("total");
                    totalBiaya.setText(String.valueOf(totalHarga));
                    if(jsonArray.length()!=0){
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = (JSONObject) jsonArray.get(i);

                            int id_cart           = jsonObject.optInt("id_cart");
                            int id_produk           = jsonObject.optInt("id_produkCart");
                            int id_user           = jsonObject.optInt("id_userCart");
                            String kategori         = jsonObject.optString("kategori");
                            double total_harga     = jsonObject.optDouble("total_harga");
                            String nama_produk         = jsonObject.optString("nama_produk");
                            String gambar         = jsonObject.optString("gambar");
                            Cart cart = new Cart(id_cart,id_produk,id_user,total_harga,kategori,nama_produk,gambar);
                            listCart.add(cart);
                        }
                        adapter.notifyDataSetChanged();
                    }else{
                        Toast.makeText(getContext(), "You have not shopping yet", Toast.LENGTH_SHORT).show();
                    }
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