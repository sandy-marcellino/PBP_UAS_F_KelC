package com.pbpkelompok3.shopping.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.pbpkelompok3.shopping.DetailFragment;
import com.pbpkelompok3.shopping.R;
import com.pbpkelompok3.shopping.api.UserApi;
import com.pbpkelompok3.shopping.model.ProductWoman;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class WomanRecyclerViewAdapter extends RecyclerView.Adapter<WomanRecyclerViewAdapter.ProductViewHolder>{
    private List<ProductWoman> productList;
    private List<ProductWoman> productListFiltered;
    private Context context;
    private View view;

    public WomanRecyclerViewAdapter(Context context, List<ProductWoman> productList) {
        this.context = context;
        this.productList = productList;
        this.productListFiltered = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_product_woman, parent, false);

        return new WomanRecyclerViewAdapter.ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WomanRecyclerViewAdapter.ProductViewHolder holder, int position) {
        final ProductWoman productW = productListFiltered.get(position);

        NumberFormat formatter = new DecimalFormat("#,###");
        holder.txtNama.setText(productW.getNama_produkW());
        holder.txtdeskripsi.setText(productW.getDeskripsi_produkW());
        holder.txtHarga.setText("IDR "+ formatter.format(productW.getHarga_produkW()));
        Glide.with(context)
                .load(UserApi.URL_IMAGE+productW.getGambar_produkW())
                .into(holder.ivGambar);

        holder.mParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(productW.getStok()!=0){
                    Bundle args = new Bundle();
                    args.putInt("id",productW.getId_produkW());
                    args.putString("nama",productW.getNama_produkW());
                    args.putString("deskripsi",productW.deskripsi_produkW);
                    args.putDouble("harga",productW.harga_produkW);
                    args.putString("gambar",productW.gambar_produkW);
                    args.putInt("stok",productW.stok);
                    args.putString("kategori","woman");

                    FragmentManager manager = ((AppCompatActivity)context).getSupportFragmentManager();
                    DetailFragment fragmentDetail = new DetailFragment();
                    fragmentDetail.setArguments(args);
                    manager.beginTransaction()
                            .replace(R.id.fragment_woman,fragmentDetail)
                            .addToBackStack(null)
                            .commit();
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("ATTENTION !");
                    builder.setMessage("Product is SOLD OUT !");
                    builder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return (productListFiltered != null) ? productListFiltered.size() : 0;
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder{

        private TextView txtNama, txtHarga, txtdeskripsi, txtstok;
        private ImageView ivGambar;
        private LinearLayout mParent;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNama = itemView.findViewById(R.id.txtNamaProductW);
            txtdeskripsi = itemView.findViewById(R.id.txtDetailProductW);
            txtHarga = itemView.findViewById(R.id.txtHargaProductW);
            ivGambar = itemView.findViewById(R.id.imageProductW);
            mParent = itemView.findViewById(R.id.linear_layout_woman);
        }
    }

    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String userInput = charSequence.toString();
                if (userInput.isEmpty()) {
                    productListFiltered = productList;
                }
                else {
                    List<ProductWoman> filteredList = new ArrayList<>();
                    for(ProductWoman produk : productList) {
                        if(String.valueOf(produk.getNama_produkW()).toLowerCase().contains(userInput) ||
                                produk.getStringHarga().toLowerCase().contains(userInput) ||
                                produk.getDeskripsi_produkW().toLowerCase().contains(userInput)) {
                            filteredList.add(produk);
                        }
                    }
                    productListFiltered = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = productListFiltered;
                return filterResults;
            }
            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                productListFiltered = (ArrayList<ProductWoman>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}