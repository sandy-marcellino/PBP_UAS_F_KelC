package com.pbpkelompok3.shopping.adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.pbpkelompok3.shopping.CRUDWomanActivity;
import com.pbpkelompok3.shopping.LoginActivity;
import com.pbpkelompok3.shopping.R;
import com.pbpkelompok3.shopping.api.ApiRequestProdukWoman;
import com.pbpkelompok3.shopping.api.Retroserver;
import com.pbpkelompok3.shopping.api.UserApi;
import com.pbpkelompok3.shopping.model.ResponsModelWoman;
import com.pbpkelompok3.shopping.model.WomanDao;
import com.pbpkelompok3.shopping.tambahProdukWomanActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdmPrdctWomanRecyclerViewAdapter extends RecyclerView.Adapter<AdmPrdctWomanRecyclerViewAdapter.HolderData> {
    private List<WomanDao> pmList;
    private Context ctx;

    public AdmPrdctWomanRecyclerViewAdapter(Context ctx, List<WomanDao> pmList)
    {
        this.ctx = ctx;
        this.pmList = pmList;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_woman_admin,parent,false);
        HolderData holder = new HolderData(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holder, int position) {
        final WomanDao pm = pmList.get(position);
        holder.tvNamaProdukW.setText(pm.getNamaProduk());
        holder.tvHargaProdukW.setText(String.valueOf(pm.getHargaProduk()));
        holder.tvDeskripsiProdukW.setText(pm.getDeskripsiProduk());
        holder.pm = pm;
        Glide.with(ctx)
                .load(UserApi.URL_IMAGE+pm.getGambarProduk())
                .into(holder.tvImageProductWoman);
    }

    @Override
    public int getItemCount() {
        if(pmList == null) {
            Toast.makeText(ctx, "Belum ada produk",Toast.LENGTH_SHORT).show();
        }
        return pmList == null ? 0 : pmList.size();
    }

    class HolderData extends RecyclerView.ViewHolder{
        TextView tvNamaProdukW, tvDeskripsiProdukW, tvHargaProdukW;
        ImageView tvImageProductWoman;
        Button btnUpdateCvProductWoman;
        Button btnDeleteCvProductWoman;
        WomanDao pm;
        public HolderData (View v)
        {
            super(v);

            tvNamaProdukW = (TextView) v.findViewById(R.id.txtCvNamaProductW);
            tvDeskripsiProdukW = (TextView) v.findViewById(R.id.txtCvDeskripsiProductW);
            tvHargaProdukW = (TextView) v.findViewById(R.id.txtCvHargaProductW);
            tvImageProductWoman = v.findViewById(R.id.imageCvProductWoman);
            btnUpdateCvProductWoman = v.findViewById(R.id.btnUpdateCvProductWoman);
            btnDeleteCvProductWoman = v.findViewById(R.id.btnDeleteCvProducWoman);

            btnDeleteCvProductWoman.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ApiRequestProdukWoman apipman = Retroserver.getClient().create(ApiRequestProdukWoman.class);
                    Call<ResponsModelWoman> call = apipman.deleteProduk(pm.getId());
                    call.enqueue(new Callback<ResponsModelWoman>() {
                        @Override
                        public void onResponse(Call<ResponsModelWoman> call, Response<ResponsModelWoman> response) {
                            Toast.makeText(ctx, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<ResponsModelWoman> call, Throwable t) {
                            Log.d("RETRO", "Failure: " + "Gagal");
                            Toast.makeText(ctx, "Produk sudah dihapus", Toast.LENGTH_SHORT).show();
                        }
                    });
                    Intent i = new Intent(ctx, CRUDWomanActivity.class);
                    ctx.startActivity(i);
                    ((Activity) ctx).finish();
                }
            });

            btnUpdateCvProductWoman.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent goEdit = new Intent(ctx, tambahProdukWomanActivity.class);
                    goEdit.putExtra("id_produkW", pm.getId());
                    goEdit.putExtra("nama_produkW", pm.getNamaProduk());
                    goEdit.putExtra("harga_produkW", pm.getHargaProduk());
                    goEdit.putExtra("deskripsi_produkW", pm.getDeskripsiProduk());
                    goEdit.putExtra("gambar_produkW", pm.getGambarProduk());
                    goEdit.putExtra("stok", pm.getStokProduk());

                    ctx.startActivity(goEdit);
                    ((Activity) ctx).finish();
                }
            });
        }
    }
}
