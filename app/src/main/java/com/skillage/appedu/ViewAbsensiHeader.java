package com.skillage.appedu;

import android.app.DownloadManager;
import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class ViewAbsensiHeader extends RelativeLayout  {
    private long lastDownload=-1L;
    private DownloadManager mgr=null;

    Context context;
    View rootView;
    LinearLayout item,panelDownload;
    TextView txtID,txtNIS,txtNama;
    RadioButton opMasuk,opSakit,opIzin,opAlpha;
    String kode,nama_pelajaran,id,nama_kelas,id_guru,nama_guru,keterangan,nama_file,nama_file_upload;
    AppCompatActivity activity;

    public ViewAbsensiHeader(Context context) {
        super(context);
        this.context=context;
        init(context);
    }

    private void init(final Context context) {
        rootView = inflate(context, R.layout.viewabsensiheader, this);
        txtID= findViewById(R.id.txtID);
        txtNIS=findViewById(R.id.txtNIS);
        txtNama=findViewById(R.id.txtNama);
        opMasuk=findViewById(R.id.opMasuk);
        opSakit=findViewById(R.id.opSakit);
        opIzin=findViewById(R.id.opIzin);
        opAlpha=findViewById(R.id.opAlpa);
    }

    public void setData(String id,String nis,String nama,String kehadiran){
    }

}
