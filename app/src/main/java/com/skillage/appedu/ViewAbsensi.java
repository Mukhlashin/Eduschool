package com.skillage.appedu;

import static android.content.Context.DOWNLOAD_SERVICE;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
import com.rustamg.filedialogs.FileDialog;
import com.rustamg.filedialogs.OpenFileDialog;


public class ViewAbsensi extends RelativeLayout  {
    private long lastDownload=-1L;
    private DownloadManager mgr=null;

    Context context;
    View rootView;
    LinearLayout item,panelDownload;
    TextView txtID,txtNIS,txtNama;
    RadioButton opMasuk,opSakit,opIzin,opAlpha;
    String kode,nama_pelajaran,id,nama_kelas,id_guru,nama_guru,keterangan,nama_file,nama_file_upload;
    AppCompatActivity activity;

    public ViewAbsensi(Context context) {
        super(context);
        this.context=context;
        init(context);
    }

    private void init(final Context context) {
        rootView = inflate(context, R.layout.viewabsensi, this);
        txtID= findViewById(R.id.txtID);
        txtNIS=findViewById(R.id.txtNIS);
        txtNama=findViewById(R.id.txtNama);
        opMasuk=findViewById(R.id.opMasuk);
        opSakit=findViewById(R.id.opSakit);
        opIzin=findViewById(R.id.opIzin);
        opAlpha=findViewById(R.id.opAlpa);
    }

    public void setData(String id,String nis,String nama,String kehadiran){
        this.id=id;
        switch (kehadiran) {
            case "M":
                opMasuk.setChecked(true);
                break;
            case "A":
                opAlpha.setChecked(true);
                break;
            case "S":
                opSakit.setChecked(true);
                break;
            case "I":
                opIzin.setChecked(true);
                break;
            default:
                opIzin.setChecked(false);
                opMasuk.setChecked(false);
                opAlpha.setChecked(false);
                opSakit.setChecked(false);
        }
        txtNIS.setText(nis);
        txtNama.setText(nama);

   }

}
