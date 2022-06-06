package com.skillage.appedu;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ViewStokHeader extends RelativeLayout {
    Context context;
    View rootView;
    AppCompatActivity activity;
    LinearLayout item;
    TextView txtNamaBarang, txtDeskripsi, txtStok, txtHarga,txtTotal;

    public ViewStokHeader(Context context) {
        super(context);
        this.context=context;
        init(context);
    }

    private void init(final Context context) {
        rootView = inflate(context, R.layout.viewstokheader, this);
        item=(LinearLayout) rootView.findViewById(R.id.item);
        txtNamaBarang = (TextView) rootView.findViewById(R.id.txtNamaBarang);
        txtDeskripsi = (TextView) rootView.findViewById(R.id.txtDeskripsi);
        txtStok = (TextView) rootView.findViewById(R.id.txtStok);
        txtHarga = (TextView) rootView.findViewById(R.id.txtHarga);
        item.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    public void setData(String namabarang, String deskripsi, String harga,String stok){
        txtNamaBarang.setText(namabarang);
        txtDeskripsi.setText(deskripsi);
        txtHarga.setText(harga);
        txtStok.setText(stok);
    }
}
