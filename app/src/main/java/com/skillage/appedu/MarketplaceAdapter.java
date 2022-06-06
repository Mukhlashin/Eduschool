package com.skillage.appedu;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

public class MarketplaceAdapter extends RecyclerView.Adapter<MarketplaceAdapter.ViewHolder> {

    private Context context;
    private String id_produk,harga;
    DBAndroid db=new DBAndroid();
    public MarketplaceAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.marketplace_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtIdProduk.setText(GlobalVar.produk.get(position).id);
        harga =GlobalVar.produk.get(position).harga;
        if (harga!="") {
            harga= String.format("%,.0f",Double.parseDouble(harga));
        }
        holder.imgFoto.setImageBitmap(GlobalVar.produk.get(position).foto);
        holder.txtNama.setText(GlobalVar.produk.get(position).namaproduk);
        holder.txtHarga.setText("Rp."+harga);
        holder.txtSold.setText("Stok :"+GlobalVar.produk.get(position).stok);
        if (!GlobalVar.role.equals("R.11")) {
            holder.txtSold.setVisibility(View.GONE);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (GlobalVar.role.equals("R.11")) {
                    GlobalVar.idProduk= GlobalVar.produk.get(position).id;
                    View view = v.getRootView();
                    //AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    FragmentManager fragmentManager =((FragmentActivity) context).getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.fragmentAdminContainer, ManagementFragment.class, null)
                            .setReorderingAllowed(true)
                            .addToBackStack("")
                            .commit();
                } else {
                    GlobalVar.idProduk= GlobalVar.produk.get(position).id;
                    View view = v.getRootView();
                    FragmentManager fragmentManager =((FragmentActivity) context).getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.fragmentContainerView, ProductFragment.class, null)
                            .setReorderingAllowed(true)
                            .addToBackStack("")
                            .commit();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return GlobalVar.produk.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtNama,txtHarga, txtSold,txtIdProduk;
        private ImageView imgFoto;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtIdProduk=itemView.findViewById(R.id.txtIdProduk);
            txtNama = itemView.findViewById(R.id.txt_nama_barang);
            txtHarga = itemView.findViewById(R.id.txt_harga_barang);
            txtSold = itemView.findViewById(R.id.txt_sold_barang);
            imgFoto = itemView.findViewById(R.id.img_foto_barang);
        }
    }
}