package com.skillage.appedu;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MarketplaceAdapter extends RecyclerView.Adapter<MarketplaceAdapter.ViewHolder> {

    private Context context;
    private String namaBarang, hargaBarang, soldBarang;
    private Image imgBarang;

    DBAndroid db=new DBAndroid();
    String SQL= "Select * from kop_barang";

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
        namaBarang=db.Records.get(position).get("nama_barang");
        hargaBarang=db.Records.get(position).get("harga");
        soldBarang="soldbarang";
        holder.txtNama.setText(namaBarang);
        holder.txtHarga.setText(hargaBarang);
        holder.txtSold.setText(soldBarang);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Item Selected", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {;
        db.getRecords(SQL);
        return db.Records.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtNama,txtHarga, txtSold;
        private ImageView imgFoto;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNama = itemView.findViewById(R.id.txt_nama_barang);
            txtHarga = itemView.findViewById(R.id.txt_harga_barang);
            txtSold = itemView.findViewById(R.id.txt_sold_barang);
            imgFoto = itemView.findViewById(R.id.img_foto_barang);
        }
    }
}