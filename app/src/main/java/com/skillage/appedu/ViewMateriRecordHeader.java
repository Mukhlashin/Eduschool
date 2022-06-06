package com.skillage.appedu;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ViewMateriRecordHeader extends RelativeLayout {
    Context context;
    View rootView;
    LinearLayout item;
    TextView txtTanggal,txtPelajaran,txtNamaFile,txtKeterangan;
    String tanggal,pelajaran,keterangan,namafile;
    AppCompatActivity activity;

    public ViewMateriRecordHeader(Context context) {
        super(context);
        this.context=context;
        init(context);
    }

    private void init(final Context context) {
        rootView = inflate(context, R.layout.viewmaterirekordheader, this);
        item = (LinearLayout) rootView.findViewById(R.id.item);
        txtTanggal = (TextView) rootView.findViewById(R.id.txtTanggal);
        txtPelajaran = (TextView) rootView.findViewById(R.id.txtPelajaran);
        txtKeterangan = (TextView) rootView.findViewById(R.id.txtKeterangan);
        txtNamaFile = (TextView) rootView.findViewById(R.id.txtNamaFile);
        item.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    public void setData(String tanggal, String pelajaran,String keterangan,String namafile){
        this.tanggal=tanggal;
        this.pelajaran=pelajaran;
        this.pelajaran=pelajaran;
        this.keterangan=keterangan;
        this.namafile=namafile;

   }
}
