package com.skillage.appedu;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CartAdapterPembeli extends  RecyclerView.Adapter<CartAdapterPembeli.ViewHolder> {

    private Context context;
    private String id_barang,namaBarang, hargaBarang,jumlah;
    private Image imgBarang;

    public interface MyClickListener {
        void onItemClick(String action);
    }

    MyClickListener mMyClickListener;

    DBAndroid db=new DBAndroid();

    public CartAdapterPembeli(Context context, MyClickListener mcl) {
        this.context = context;
        mMyClickListener= mcl;
        String SQL= "Select * from vw_barang_keluar where  id_sekolah='"+GlobalVar.idSekolah+"' and no_faktur='"+GlobalVar.nofaktur+"' and status='CHECK OUT'";
        db.getRecords(SQL);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_pembeli, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        namaBarang=db.Records.get(position).get("nama_barang");
        id_barang = db.Records.get(position).get("id_barang");
        hargaBarang=db.Records.get(position).get("harga_jual");
        String nofaktur=db.Records.get(position).get("no_faktur");
        jumlah=db.Records.get(position).get("qty");
        if (hargaBarang!="") {
            hargaBarang= String.format("%,.0f",Double.parseDouble(hargaBarang));
        }
        holder.txtNama.setText(namaBarang);
        holder.txtNama.setTag(id_barang);
        holder.txtHarga.setText("Rp "+hargaBarang);
        holder.txtHarga.setTag(hargaBarang.replace(".",""));
        holder.txtQty.setText(jumlah);
        holder.imgFoto.setImageBitmap(GlobalFunction.loadImageFromURL(GlobalVar.URL+"/foto/"+db.Records.get(position).get("foto")));

        holder.btnDelete.setVisibility(View.GONE);
        holder.btnPlus.setVisibility(View.GONE);
        holder.btnMinus.setVisibility(View.GONE);


    }

    @Override
    public int getItemCount() {;
        return db.Records.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtNama,txtHarga;
        private ImageView imgFoto;
        ImageView foto_barang;
        TextView txtQty;
        Button btnPlus,btnMinus;
        ImageView btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNama = itemView.findViewById(R.id.nama_barang);
            txtHarga = itemView.findViewById(R.id.harga_barang);
            imgFoto = itemView.findViewById(R.id.foto_barang);
            foto_barang=itemView.findViewById(R.id.foto_barang);
            txtQty=itemView.findViewById(R.id.edt_kuantitas);
            btnPlus = itemView.findViewById(R.id.btnPlus);
            btnMinus = itemView.findViewById(R.id.btnMinus);
            btnDelete = itemView.findViewById(R.id.btnDelete);


        }
    }
}