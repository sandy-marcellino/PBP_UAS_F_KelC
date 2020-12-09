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
import com.pbpkelompok3.shopping.model.ProductMan;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class ManRecyclerViewAdapter extends RecyclerView.Adapter<ManRecyclerViewAdapter.ProductViewHolder>{
    private List<ProductMan> productList;
    private List<ProductMan> productListFiltered;
    private Context context;
    private View view;

    public ManRecyclerViewAdapter(Context context, List<ProductMan> productList) {
        this.context = context;
        this.productList = productList;
        this.productListFiltered = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_product_man, parent, false);

        return new ManRecyclerViewAdapter.ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ManRecyclerViewAdapter.ProductViewHolder holder, int position) {
        final ProductMan productM = productListFiltered.get(position);


        NumberFormat formatter = new DecimalFormat("#,###");
        holder.txtNama.setText(productM.getNama_produkM());
        holder.txtdeskripsi.setText(productM.getDeskripsi_produkM());
        holder.txtHarga.setText("IDR "+ formatter.format(productM.getHarga_produkM()));
        Glide.with(context)
                .load(UserApi.URL_IMAGE+productM.getGambar_produkM())
                .into(holder.ivGambar);

        holder.mParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(productM.getStok()!=0){
                    Bundle args = new Bundle();
                    args.putInt("id",productM.getId_produkM());
                    args.putString("nama",productM.getNama_produkM());
                    args.putString("deskripsi",productM.deskripsi_produkM);
                    args.putDouble("harga",productM.harga_produkM);
                    args.putString("gambar",productM.gambar_produkM);
                    args.putInt("stok",productM.stok);
                    args.putString("kategori","man");


                    FragmentManager manager = ((AppCompatActivity)context).getSupportFragmentManager();
                    DetailFragment fragmentDetail = new DetailFragment();
                    fragmentDetail.setArguments(args);
                    manager.beginTransaction()
                            .replace(R.id.fragment_man,fragmentDetail)
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

    public class ProductViewHolder extends RecyclerView.ViewHolder {

        private TextView txtNama, txtHarga, txtdeskripsi, txtstok;
        private ImageView ivGambar;
        private LinearLayout mParent;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNama = itemView.findViewById(R.id.txtNamaProductM);
            txtdeskripsi = itemView.findViewById(R.id.txtDeskripsiProductM);
            txtHarga = itemView.findViewById(R.id.txtHargaProductM);
            ivGambar = itemView.findViewById(R.id.imageProductM);
            mParent = itemView.findViewById(R.id.linear_layout_man);
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
                    List<ProductMan> filteredList = new ArrayList<>();
                    for(ProductMan produk : productList) {
                        if(String.valueOf(produk.getNama_produkM()).toLowerCase().contains(userInput) ||
                                produk.getStringHarga().toLowerCase().contains(userInput) ||
                                produk.getDeskripsi_produkM().toLowerCase().contains(userInput)) {
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
                productListFiltered = (ArrayList<ProductMan>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
