package com.pbpkelompok3.shopping.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.pbpkelompok3.shopping.R;
import com.pbpkelompok3.shopping.api.UserApi;
import com.pbpkelompok3.shopping.model.Cart;
import com.pbpkelompok3.shopping.model.ProductMan;
import com.pbpkelompok3.shopping.model.ProductWoman;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.android.volley.Request.Method.DELETE;
import static com.android.volley.Request.Method.PUT;

public class AdapterCart extends RecyclerView.Adapter<AdapterCart.CartViewHolder>{
    private List<Cart> cartList;
    private Context context;
    private View view;
    private int idProduk;
    private String kategori;
    private int jumlahBaru;
    private ProductMan man = new ProductMan();
    private AdapterCart.deleteItemListener mListener;
    private ProductWoman woman = new ProductWoman();

    public AdapterCart(Context context, List<Cart> cartList, AdapterCart.deleteItemListener mListener){
        this.context = context;
        this.cartList = cartList;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_cart, parent, false);

        return new AdapterCart.CartViewHolder(view);
    }

    public interface deleteItemListener {
        void deleteItem( Boolean delete);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterCart.CartViewHolder holder, int position) {
        final Cart cart = cartList.get(position);

        NumberFormat formatter = new DecimalFormat("#,###");
        holder.txtNama.setText(cart.getNama_produk());
        holder.txtHarga.setText("IDR "+ formatter.format(cart.getTotalHarga()));
        Glide.with(context)
                .load(UserApi.URL_IMAGE+cart.getGambar())
                .into(holder.ivGambar);
        holder.ibDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Do you sure want to remove from cart ?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteCart(cart.getId_cart(),cart.getKategori());
                        if(cart.getKategori().equalsIgnoreCase("man")){
                            idProduk =  cart.getId_productCart();
                            updateProductMan(idProduk);
                        }else{
                            idProduk =  cart.getId_productCart();
                            updateProductWoman(idProduk);
                        }
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {

        private TextView txtNama, txtHarga;
        private ImageView ivGambar;
        private ImageButton ibDelete;
        private LinearLayout mParent;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNama = itemView.findViewById(R.id.namaProduk);
            txtHarga = itemView.findViewById(R.id.hargaProduk);
            ibDelete = itemView.findViewById(R.id.btnHapusCart);
            ivGambar = itemView.findViewById(R.id.gambar);
            mParent = itemView.findViewById(R.id.linear_layout_cart);
        }
    }

    public void deleteCart(int idCart, String kategori){
        RequestQueue queue = Volley.newRequestQueue(context);

        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("loading....");
        progressDialog.setTitle("Removing from cart...");
        progressDialog.setProgressStyle(android.app.ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(DELETE, UserApi.URL_DELETE_CART+idCart+"/"+kategori, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject obj = new JSONObject(response);
                    Toast.makeText(context, obj.getString("message"), Toast.LENGTH_SHORT).show();
                    notifyDataSetChanged();
                    mListener.deleteItem(true);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(stringRequest);
    }

    public void updateProductWoman(int id){
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(PUT, UserApi.URL_WOMAN_INC + id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Gagal", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("id_produkW", String.valueOf(id));
                return params;
            }
        };
        queue.add(stringRequest);
    }

    public void updateProductMan(int id){
        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(PUT, UserApi.URL_MAN_INC + id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Gagal", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(stringRequest);
    }
}
