package com.skillage.appedu;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.rustamg.filedialogs.FileDialog;

import java.io.File;

public class AdminKoperasiActivity extends AppCompatActivity implements FileDialog.OnFileSelectedListener {
    TextView txtUnitBisnis, txtKoperasi, txtYayasan;
    Button btn_daftarpesanan, btnManagement, btnStock,btn_lappenjualan;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_admin_koperasi);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        progressDialog = new ProgressDialog(this,R.style.Custom);

        txtUnitBisnis= findViewById(R.id.txt_unit_bisnis);
        txtKoperasi= findViewById(R.id.txt_koperasi);
        txtYayasan= findViewById(R.id.txt_yayasan);

        btn_daftarpesanan = findViewById(R.id.btn_daftarpesanan);
        btnManagement = findViewById(R.id.btn_management);
        btnStock = findViewById(R.id.btn_stock);
        btn_lappenjualan=findViewById(R.id.btn_lappenjualan);

        txtUnitBisnis.setText(GlobalVar.namaUnitBisnis);
        txtKoperasi.setText(GlobalVar.namaSekolah);
        txtYayasan.setText(GlobalVar.namaYayasan);

        btn_daftarpesanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentAdminContainer, LogbookFragment.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack("")
                        .commit();
            }
        });

        btn_lappenjualan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentAdminContainer, LapPenjualanFragment.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack("")
                        .commit();
            }
        });

        btnManagement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadingProduk l=new LoadingProduk();
                l.execute();
            }
        });

        btnStock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentAdminContainer, StockFragment.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack("")
                        .commit();
            }
        });

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentAdminContainer, HomeKoerasiFragment.class, null)
                .setReorderingAllowed(true)
                .addToBackStack("")
                .commit();
    }

    private class LoadingProduk extends AsyncTask<String, Integer, Integer> {

        DBAndroid db=new DBAndroid();

        @Override
        protected void onPreExecute() {
            progressDialog.setTitle("Loading produk...");
            progressDialog.show();
            super.onPreExecute();
        }

        protected Integer doInBackground(String... urls) {
            String SQL= "Select * from kop_barang where id_unit_bisnis="+GlobalVar.idUnitBisnis;
            db.getRecords(SQL);
            GlobalVar.produk.clear();
            for (int i=0; i<=db.Records.size()-1; i++) {
                String id= db.Records.get(i).get("id_barang");
                String nama= db.Records.get(i).get("nama_barang");
                String harga= db.Records.get(i).get("harga");
                String stok= db.Records.get(i).get("stok");
                String foto= db.Records.get(i).get("foto");
                Bitmap fotonya= GlobalFunction.loadImageFromURL(GlobalVar.URL+"foto/"+foto);
                Product p=new Product(id,fotonya,nama,harga,stok);
                GlobalVar.produk.add(p);
                setProgress((i/db.Records.size())*100);
            }
            return db.Records.size();
        }

        protected void onProgressUpdate(Integer... progress) {

        }

        protected void onPostExecute(Integer result) {
            progressDialog.hide();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.fragmentAdminContainer, MarketplaceFragment.class, null)
                    .setReorderingAllowed(true)
                    .addToBackStack("")
                    .commit();
        }
    }
    @Override
    public void onFileSelected(FileDialog dialog, File file) {
        String filenamepath=file.getAbsolutePath();
        if (GlobalVar.jenisUpload=="Upload Gambar") {
            ManagementFragment fragment = (ManagementFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentAdminContainer);
            GlobalVar.namafile="Id_UnutBisnis-"+GlobalVar.idUnitBisnis+"-"+GlobalVar.namaUnitBisnis+"-"+file.getName();
            GlobalVar.namafileAsal=filenamepath;
        }
    }
}