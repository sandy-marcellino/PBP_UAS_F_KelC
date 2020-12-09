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
import com.pbpkelompok3.shopping.CRUDManActivity;
import com.pbpkelompok3.shopping.R;
import com.pbpkelompok3.shopping.api.ApiRequestProdukMan;
import com.pbpkelompok3.shopping.api.Retroserver;
import com.pbpkelompok3.shopping.api.UserApi;
import com.pbpkelompok3.shopping.model.ManDao;
import com.pbpkelompok3.shopping.model.ResponsModelMan;
import com.pbpkelompok3.shopping.tambahProdukManActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdmPrdctManRecyclerViewAdapter extends RecyclerView.Adapter<AdmPrdctManRecyclerViewAdapter.HolderData> {
    private List<ManDao> pmList;
    private Context ctx;

    public AdmPrdctManRecyclerViewAdapter(Context ctx, List<ManDao> pmList)
    {
        this.ctx = ctx;
        this.pmList = pmList;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_man_admin,parent,false);
        HolderData holder = new HolderData(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holder, int position) {
        final ManDao pm = pmList.get(position);
        holder.tvNamaProdukM.setText(pm.getNamaProduk());
        holder.tvHargaProdukM.setText(String.valueOf(pm.getHargaProduk()));
        holder.tvDeskripsiProdukM.setText(pm.getDeskripsiProduk());
        holder.pm = pm;
        Glide.with(ctx)
                .load(UserApi.URL_IMAGE+pm.getGambarProduk())
                .into(holder.tvImageProductMan);
    }

    @Override
    public int getItemCount() {
        if(pmList == null) {
            Toast.makeText(ctx, "Belum ada produk",Toast.LENGTH_SHORT).show();
        }
        return pmList == null ? 0 : pmList.size();
    }

    class HolderData extends RecyclerView.ViewHolder{
        TextView tvNamaProdukM, tvDeskripsiProdukM, tvHargaProdukM;
        ImageView tvImageProductMan;
        Button btnUpdateCvProductMan;
        Button btnDeleteCvProductMan;
        ManDao pm;
        public HolderData (View v)
        {
            super(v);

            tvNamaProdukM = (TextView) v.findViewById(R.id.txtCvNamaProductM);
            tvDeskripsiProdukM = (TextView) v.findViewById(R.id.txtCvDeskripsiProductM);
            tvHargaProdukM = (TextView) v.findViewById(R.id.txtCvHargaProductM);
            tvImageProductMan = v.findViewById(R.id.imageCvProductMan);
            btnUpdateCvProductMan = v.findViewById(R.id.btnUpdateCvProductMan);
            btnDeleteCvProductMan = v.findViewById(R.id.btnDeleteCvProductMan);

            btnDeleteCvProductMan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ApiRequestProdukMan apipman = Retroserver.getClient().create(ApiRequestProdukMan.class);
                    Call<ResponsModelMan> call = apipman.deleteProduk(pm.getId());
                    call.enqueue(new Callback<ResponsModelMan>() {
                        @Override
                        public void onResponse(Call<ResponsModelMan> call, Response<ResponsModelMan> response) {
                            Toast.makeText(ctx, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<ResponsModelMan> call, Throwable t) {
                            Log.d("RETRO", "Failure: " + "Gagal");
                            Toast.makeText(ctx, "Produk sudah dihapus", Toast.LENGTH_SHORT).show();
                        }
                    });
                    Intent i = new Intent(ctx, CRUDManActivity.class);
                    ctx.startActivity(i);
                    ((Activity) ctx).finish();
                }
            });

            btnUpdateCvProductMan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent goEdit = new Intent(ctx, tambahProdukManActivity.class);
                    goEdit.putExtra("id_produkM", pm.getId());
                    goEdit.putExtra("nama_produkM", pm.getNamaProduk());
                    goEdit.putExtra("harga_produkM", pm.getHargaProduk());
                    goEdit.putExtra("deskripsi_produkM", pm.getDeskripsiProduk());
                    goEdit.putExtra("gambar_produkM", pm.getGambarProduk());
                    goEdit.putExtra("stok", pm.getStokProduk());

                    ctx.startActivity(goEdit);
                    ((Activity) ctx).finish();
                }
            });
        }
    }
}
