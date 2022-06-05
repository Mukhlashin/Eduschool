package com.skillage.appedu;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

public class CartAdapter extends  RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private Context context;
    private String namaBarang, hargaBarang;
    private Image imgBarang;

    DBAndroid db=new DBAndroid();
    String SQL= "Select * from kop_barang where id_sekolah='"+GlobalVar.idSekolah+"'";

    public CartAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        namaBarang=db.Records.get(position).get("nama_barang");
        hargaBarang=db.Records.get(position).get("harga");
        holder.txtNama.setText(namaBarang);
        holder.txtHarga.setText(hargaBarang);
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
//                fragmentManager.beginTransaction()
//                        .replace(R.id.fragmentContainerView, ProductFragment.class, null)
//                        .setReorderingAllowed(true)
//                        .addToBackStack("")
//                        .commit();
//            }
//        });
    }

    @Override
    public int getItemCount() {;
        db.getRecords(SQL);
        return db.Records.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtNama,txtHarga;
        private ImageView imgFoto;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNama = itemView.findViewById(R.id.nama_barang);
            txtHarga = itemView.findViewById(R.id.harga_barang);
            imgFoto = itemView.findViewById(R.id.foto_barang);
        }
    }
}