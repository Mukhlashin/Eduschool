package com.skillage.appedu;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ViewLapAbsensiHeader extends RelativeLayout {
    Context context;
    View rootView;
    LinearLayout item;
    TextView txtTanggal,txtGuru,txtPelajaran,txtKehadiran,txtKeterangan;
    String tanggal,pelajaran,kehadiran,keterangan,guru;
    AppCompatActivity activity;

    public ViewLapAbsensiHeader(Context context) {
        super(context);
        this.context=context;
        init(context);
    }

    private void init(final Context context) {
        rootView = inflate(context, R.layout.viewlapabsensiheader, this);
        item = (LinearLayout) rootView.findViewById(R.id.item);
        txtKehadiran = (TextView) rootView.findViewById(R.id.txtKehadiran);
        txtTanggal = (TextView) rootView.findViewById(R.id.txtTanggal);
        txtGuru = (TextView) rootView.findViewById(R.id.txtGuru);
        txtKehadiran = (TextView) rootView.findViewById(R.id.txtKehadiran);
        txtKeterangan = (TextView) rootView.findViewById(R.id.txtKeterangan);
        txtPelajaran = (TextView) rootView.findViewById(R.id.txtPelajaran);
        item.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    public void setData(String tanggal, String pelajaran,String guru,String kehadiran,String keterangan){
        this.tanggal=tanggal;
        this.pelajaran=pelajaran;
        this.guru=guru;
        this.keterangan=keterangan;
        this.kehadiran=kehadiran;
        txtTanggal.setText(tanggal);
        txtGuru.setText(guru);
        txtPelajaran.setText(pelajaran);
        txtKehadiran.setText(kehadiran);
        txtKeterangan.setText(keterangan);
   }
}
