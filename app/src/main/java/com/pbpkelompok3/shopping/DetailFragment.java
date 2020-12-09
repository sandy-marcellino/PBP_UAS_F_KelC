package com.pbpkelompok3.shopping;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;
import com.pbpkelompok3.shopping.api.UserApi;
import com.pbpkelompok3.shopping.model.ProductMan;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.android.volley.Request.Method.POST;
import static com.android.volley.Request.Method.PUT;

public class DetailFragment extends Fragment {

   private MaterialTextView tvNama, tvDesc, tvHarga, tvStok;
   private ImageView ivGambar;
   private ImageButton ibClose;
   private MaterialButton btnAddToCart;
   private String nama,desc,gambar,kategori;
   private double harga;
   private int stok,idProduk,idUser,stokBaru;
   private SharedPreferences preferences;
   private ProductMan man = new ProductMan();

   public DetailFragment(){

   }

   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      View v = inflater.inflate(R.layout.fragment_detail,container,false);

//      ibClose = v.findViewById(R.id.ibClose);
      btnAddToCart = v.findViewById(R.id.btnAddToCart);

      tvNama = v.findViewById(R.id.txtName);
      tvDesc = v.findViewById(R.id.txtDescription);
      tvHarga = v.findViewById(R.id.txtPrice);
      tvStok = v.findViewById(R.id.txtStok);
      ivGambar = v.findViewById(R.id.ivImage);



      idProduk = getArguments().getInt("id",0);
      nama = getArguments().getString("nama","");
      desc = getArguments().getString("deskripsi","");
      harga = getArguments().getDouble("harga",0);
      gambar = getArguments().getString("gambar","");
      stok = getArguments().getInt("stok",0);
      kategori = getArguments().getString("kategori","");

      tvNama.setText(nama);
      tvDesc.setText(desc);
      tvHarga.setText(String.valueOf(harga));
      tvStok.setText(String.valueOf(stok));
      Glide.with(getContext())
              .load(UserApi.URL_IMAGE+gambar)
              .into(ivGambar);


      preferences  = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
      idUser = preferences.getInt("id", Context.MODE_PRIVATE);


      return v;
   }

   public void loadFragment(Fragment fragment) {
      FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
      FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
      if (Build.VERSION.SDK_INT >= 26) {
         fragmentTransaction.setReorderingAllowed(false);
      }
      fragmentTransaction.replace(R.id.edit_profile_fragment, fragment)
              .detach(this)
              .attach(this)
              .commit();
   }

   public void closeFragment(){
      FragmentTransaction transaction = getFragmentManager().beginTransaction();
      transaction.hide(DetailFragment.this).detach(this)
              .attach(this).commit();
   }

   @Override
   public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
      super.onViewCreated(view, savedInstanceState);
      btnAddToCart.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            addToCart(idProduk,idUser,harga,kategori,nama,gambar);
            if(kategori.equalsIgnoreCase("woman")){
               int stokBaru = stok - 1;
               updateProductWoman(idProduk,stokBaru);
               closeFragment();
            }else{
               stokBaru = stok - 1;
               updateProductMan(idProduk,stokBaru);
               closeFragment();
            }
         }
      });
   }

   public void addToCart(int id_produk, int id_user, double total_harga, String kategori, String nama, String gambar){
      RequestQueue queue = Volley.newRequestQueue(getContext());

      final ProgressDialog progressDialog;
      progressDialog = new ProgressDialog(getContext());
      progressDialog.setMessage("loading....");
      progressDialog.setTitle("Add to cart...");
      progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
      progressDialog.show();

      StringRequest stringRequest = new StringRequest(POST, UserApi.URL_ADD_CART, new Response.Listener<String>() {
         @Override
         public void onResponse(String response) {
            progressDialog.dismiss();
            try {
               JSONObject obj = new JSONObject(response);
               if(obj.getString("status").equals("success"))
               {
                  Toast.makeText(getContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
               }else{
                  Toast.makeText(getContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
               }
            } catch (JSONException e) {
               e.printStackTrace();
            }
         }
      }, new Response.ErrorListener() {
         @Override
         public void onErrorResponse(VolleyError error) {
            progressDialog.dismiss();
            Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
         }
      }){
         @Override
         protected Map<String, String> getParams()
         {
            Map<String, String>  params = new HashMap<String, String>();
            params.put("id_produkCart", String.valueOf(id_produk));
            params.put("id_userCart", String.valueOf(id_user));
            params.put("total_harga", String.valueOf(total_harga));
            params.put("kategori", kategori);
            params.put("nama_produk", nama);
            params.put("gambar", gambar);

            return params;
         }
      };
      queue.add(stringRequest);
   }

   public void updateProductWoman(int id, int jumlah){
      RequestQueue queue = Volley.newRequestQueue(getContext());
      StringRequest stringRequest = new StringRequest(PUT, UserApi.URL_WOMAN_EDIT + id, new Response.Listener<String>() {
         @Override
         public void onResponse(String response) {
         }
      }, new Response.ErrorListener() {
         @Override
         public void onErrorResponse(VolleyError error) {
            Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
         }
      }){
         @Override
         protected Map<String, String> getParams()
         {
            Map<String, String>  params = new HashMap<String, String>();
            params.put("stok", String.valueOf(jumlah));
            return params;
         }
      };
      queue.add(stringRequest);
   }

   public void updateProductMan(int id, int jumlah){
      RequestQueue queue = Volley.newRequestQueue(getContext());

      StringRequest stringRequest = new StringRequest(PUT, UserApi.URL_MAN_EDIT + id, new Response.Listener<String>() {
         @Override
         public void onResponse(String response) {

         }
      }, new Response.ErrorListener() {
         @Override
         public void onErrorResponse(VolleyError error) {
            Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
         }
      }){
         @Override
         protected Map<String, String> getParams()
         {
            Map<String, String>  params = new HashMap<String, String>();
            params.put("stok", String.valueOf(jumlah));
            return params;
         }
      };
      queue.add(stringRequest);
   }
}