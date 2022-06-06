package com.skillage.appedu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class ProductFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        ImageView fotoProduk=view.findViewById(R.id.fotoProduk);
        TextView txtNamaProduk=view.findViewById(R.id.txtNamaProduk);
        TextView txtHarga=view.findViewById(R.id.txtHarga);
        TextView txtDeskripsi=view.findViewById(R.id.txtDeskripsi);
        TextView txtPesan=view.findViewById(R.id.txtPesan);
        TextView txtNamaUnitBisnis=view.findViewById(R.id.txtNamaUnitBisnis);

        AppCompatButton btnLihatKeranjang = view.findViewById(R.id.btnLihatKeranjang);
        AppCompatButton btnMasukkanKeranjang = view.findViewById(R.id.btnMasukkanKeranjang);

        btnLihatKeranjang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView, CartFragment.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack("")
                        .commit();
            }
        });

        btnMasukkanKeranjang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBAndroid db=new DBAndroid();
                String no_faktur="";
                int total=Integer.parseInt(txtHarga.getText().toString().replace(".",""));

                db.getRecords("Select no_faktur from kop_barang_keluar where userid='"+GlobalVar.userID+"' and status='DALAM KERANJANG'");
                if (db.Records.size()>0) {
                    no_faktur= db.Records.get(0).get("no_faktur");
                } else {
                    db.getRecords("Select max(no_faktur)  as no_faktur from vw_barang_keluar where id_sekolah="+GlobalVar.idSekolah);
                    if (db.Records.size()>0) {
                        if (!db.Records.get(0).get("no_faktur").equals("")) {
                            no_faktur=Integer.parseInt(db.Records.get(0).get("no_faktur"))+"";
                            no_faktur= String.format("%09d" , Integer.parseInt( no_faktur)+1);;
                        } else {
                            no_faktur="000000001";
                        }
                    } else {
                        no_faktur="000000001";
                    }
                }
                String SQL;
                db.getRecords("Select * from kop_barang_keluar where id_barang="+GlobalVar.idProduk+" and userid='"+GlobalVar.userID+"' and status='DALAM KERANJANG'");
                if (db.Records.size()>0) {
                    SQL="Update kop_barang_keluar set qty=qty+1 where id_barang="+GlobalVar.idProduk+" and userid='"+GlobalVar.userID+"' and status='DALAM KERANJANG'";
                } else {
                    SQL= "insert into kop_barang_keluar (status,id_barang,no_faktur,tanggal,qty,harga_jual,userid) " +
                            "values ('DALAM KERANJANG',"+GlobalVar.idProduk+",'"+no_faktur+"',getDate(),1,"+txtHarga.getText().toString().replace(".","")+",'"+GlobalVar.userID+"')";
                }
                db.execute(SQL);
                txtPesan.setVisibility(View.VISIBLE);
            }
        });

        DBAndroid db=new DBAndroid();
        db.getRecords("Select * from vw_barang where id_barang="+GlobalVar.idProduk);
        if (db.Records.size()>0) {
            fotoProduk.setImageBitmap(GlobalFunction.loadImageFromURL(GlobalVar.URL+"/foto/"+db.Records.get(0).get("foto")));
            txtNamaProduk.setText(db.Records.get(0).get("nama_barang"));
            txtNamaUnitBisnis.setText(db.Records.get(0).get("nama_unit_bisnis"));
            String harga =db.Records.get(0).get("harga");
            if (harga!="") {
                harga= String.format("%,.0f",Double.parseDouble(harga));
            }
            txtHarga.setText(harga);
            txtDeskripsi.setText(db.Records.get(0).get("deskripsi"));

        }

    }
}