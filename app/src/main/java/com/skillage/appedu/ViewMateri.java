package com.skillage.appedu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class ViewMateri extends RelativeLayout {
    Context context;
    View rootView;
    LinearLayout item;
    TextView txtNamaMK,txtDosen,txtKeterangan,txtDownload;
    String kode,nama_pelajaran,id,nama_kelas,id_guru,nama_guru,keterangan,nama_file;
    AppCompatActivity activity;

    public ViewMateri(Context context) {
        super(context);
        this.context=context;
        init(context);
    }

    private void init(final Context context) {
        rootView = inflate(context, R.layout.viewmateri, this);
        item = (LinearLayout) rootView.findViewById(R.id.item);
        txtNamaMK = (TextView) rootView.findViewById(R.id.txtNamaMK);
        txtDosen = (TextView) rootView.findViewById(R.id.txtDosen);
        txtKeterangan = (TextView) rootView.findViewById(R.id.txtKeterangan);
        txtDownload = (TextView) rootView.findViewById(R.id.txtDownload);
        item.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nama_file.toUpperCase().contains("PDF")) {
                    Fragment f = ShowPDFFragment.newInstance(nama_file,keterangan);
                    activity.getSupportFragmentManager().beginTransaction()
                            .setCustomAnimations(
                                    R.anim.enter_from_right,  // enter
                                    R.anim.exit_to_left,  // exit
                                    R.anim.enter_from_left,   // popEnter
                                    R.anim.exit_to_right // popExit
                            )
                            .replace(R.id.mainframe, f)
                            .addToBackStack(null)
                            .commit();
                }
                if (nama_file.toUpperCase().contains("AVI") || nama_file.toUpperCase().contains("MP4")) {
                    Fragment f = ShowVideoFragment.newInstance(nama_file,keterangan);
                    activity.getSupportFragmentManager().beginTransaction()
                            .setCustomAnimations(
                                    R.anim.enter_from_right,  // enter
                                    R.anim.exit_to_left,  // exit
                                    R.anim.enter_from_left,   // popEnter
                                    R.anim.exit_to_right // popExit
                            )
                            .replace(R.id.mainframe, f)
                            .addToBackStack(null)
                            .commit();
                }

            }
        });
    }

    public void setData(String id,String kode,String nama_pelajaran,String nama_kelas,String id_guru,String nama_guru,String keterangan,String nama_file){
        this.id=id;
        this.kode=kode;
        this.nama_pelajaran=nama_pelajaran;
        this.nama_kelas=nama_kelas;
        this.id_guru=id_guru;
        this.keterangan=keterangan;
        this.nama_file=nama_file;
        txtNamaMK.setText(nama_pelajaran);
        txtDosen.setText(nama_guru);
        txtKeterangan.setText(keterangan);
   }
}
