package com.skillage.appedu;

import static android.content.Context.DOWNLOAD_SERVICE;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
import com.rustamg.filedialogs.FileDialog;
import com.rustamg.filedialogs.OpenFileDialog;

import java.io.File;


public class ViewTugas extends RelativeLayout  {
    private long lastDownload=-1L;
    private DownloadManager mgr=null;

    Context context;
    View rootView;
    LinearLayout item,panelDownload;
    TextView txtNamaMK,txtDosen,txtKeterangan,txtDownload,txtDownload2;
    String kode,nama_pelajaran,id,nama_kelas,id_guru,nama_guru,keterangan,nama_file,nama_file_upload;
    AppCompatActivity activity;

    public ViewTugas(Context context) {
        super(context);
        this.context=context;
        init(context);
    }

    private void init(final Context context) {
        rootView = inflate(context, R.layout.viewtugas, this);
        item = (LinearLayout) rootView.findViewById(R.id.item);
        panelDownload=(LinearLayout) rootView.findViewById(R.id.panelDownload);
        txtNamaMK = (TextView) rootView.findViewById(R.id.txtNamaMK);
        txtDosen = (TextView) rootView.findViewById(R.id.txtDosen);
        txtKeterangan = (TextView) rootView.findViewById(R.id.txtKeterangan);
        txtDownload = (TextView) rootView.findViewById(R.id.txtDownload);
        txtDownload2 = (TextView) rootView.findViewById(R.id.txtDownload2);
        mgr=(DownloadManager) getContext().getSystemService(DOWNLOAD_SERVICE);

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

    public void setData(String id,String kode,String nama_pelajaran,String nama_kelas,String id_guru,String nama_guru,String keterangan,String nama_file,String nama_file_upload){
        this.id=id;
        this.kode=kode;
        this.nama_pelajaran=nama_pelajaran;
        this.nama_kelas=nama_kelas;
        this.id_guru=id_guru;
        this.keterangan=keterangan;
        this.nama_file=nama_file;
        this.nama_file_upload= nama_file_upload;
        txtNamaMK.setText(nama_pelajaran);
        txtDosen.setText(nama_guru);
        txtKeterangan.setText(keterangan);
        if (nama_file.toUpperCase().equals("NULL") || nama_file.trim().equals("")) {
            panelDownload.setVisibility(GONE);
        }
        if (nama_file_upload==null) nama_file="";
        if (nama_file_upload.toUpperCase().equals("NULL") || nama_file_upload.trim().equals("")) {
            if (GlobalVar.TypeUser.toUpperCase().equals("SISWA"))
                txtDownload2.setText("Kamu belum mengupload tugas. Tekan untuk upload");
           else
                txtDownload2.setText("Siswa belum mengupload tugas");
            txtDownload2.setTextColor(getResources().getColor(R.color.blue_700));
        } else {
            txtDownload2.setText("Download");

        }

        txtDownload2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!txtDownload2.getText().toString().equals("Download")) {
                    FileDialog dialog = new OpenFileDialog();
                    dialog.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.Theme_AppEdu);
                    //Bundle args = new Bundle();
                    //args.putString(FileDialog.EXTENSION, "png");
                    //dialog.setArguments(args);
                    GlobalVar.jenisUpload="Upload Tugas Siswa";
                    GlobalVar.id_materi= id;

                    dialog.show(activity.getSupportFragmentManager(), OpenFileDialog.class.getName());
                } else {
                    Uri uri=Uri.parse(GlobalVar.URL+"/uploadsiswa/"+nama_file_upload);
                    Environment
                            .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                            .mkdirs();

                    lastDownload=
                            mgr.enqueue(new DownloadManager.Request(uri)
                                    .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI |
                                            DownloadManager.Request.NETWORK_MOBILE)
                                    .setAllowedOverRoaming(false)
                                    .setTitle("Download Video")
                                    .setDescription("Download file "+nama_file_upload)
                                    .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,
                                            nama_file_upload));
                    Snackbar.make(view,"Mendownload file...", Snackbar.LENGTH_SHORT).show();
                }
            }
        });

   }

}
