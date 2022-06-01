package com.skillage.appedu;

import static android.content.Context.DOWNLOAD_SERVICE;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

public class ViewMateriRecord extends RelativeLayout {
    Context context;
    View rootView;
    LinearLayout item;
    TextView txtTanggal,txtPelajaran,txtNamaFile,txtKeterangan;
    String tanggal,pelajaran,keterangan,namafile;
    AppCompatActivity activity;

    private long lastDownload=-1L;
    private DownloadManager mgr=null;


    public ViewMateriRecord(Context context) {
        super(context);
        this.context=context;
        init(context);
    }

    private void init(final Context context) {
        rootView = inflate(context, R.layout.viewmaterirekord, this);
        item = (LinearLayout) rootView.findViewById(R.id.item);
        txtTanggal = (TextView) rootView.findViewById(R.id.txtTanggal);
        txtPelajaran = (TextView) rootView.findViewById(R.id.txtPelajaran);
        txtKeterangan = (TextView) rootView.findViewById(R.id.txtKeterangan);
        txtNamaFile = (TextView) rootView.findViewById(R.id.txtNamaFile);

        mgr=(DownloadManager) getContext().getSystemService(DOWNLOAD_SERVICE);

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
        txtTanggal.setText(tanggal);
        txtPelajaran.setText(pelajaran);
        txtNamaFile.setText("Download");
        txtKeterangan.setText(keterangan);
        txtNamaFile.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri=Uri.parse(GlobalVar.URL+ "files/"+namafile);
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                        .mkdirs();

                lastDownload=
                        mgr.enqueue(new DownloadManager.Request(uri)
                                .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI |
                                        DownloadManager.Request.NETWORK_MOBILE)
                                .setAllowedOverRoaming(false)
                                .setTitle("Download Video")
                                .setDescription("Download file "+namafile)
                                .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,
                                        namafile));
                Snackbar.make(rootView,"Mendownload pdf...", Snackbar.LENGTH_SHORT).show();
            }
        });
   }
}
