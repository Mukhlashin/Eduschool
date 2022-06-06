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

import com.makeramen.roundedimageview.RoundedImageView;

public class KoperasiMainActivity extends AppCompatActivity {
    TextView txtSaldo;
    Button btnMarketplace, btnLogbook, btnRiwayatBelanja, btnStock;
    ProgressDialog progressDialog;

    TextView txtNama,txtKeterangan,txtSekolah;
    RoundedImageView imgFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_koperasi_main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        progressDialog = new ProgressDialog(this,R.style.Custom);

        txtNama=findViewById(R.id.txtNama);
        txtKeterangan=findViewById(R.id.txtKeterangan);
        txtSekolah=findViewById(R.id.txtSekolah);
        imgFoto=findViewById(R.id.imgFoto);

        txtNama.setText(GlobalVar.userName);
        if (GlobalVar.TypeUser.toUpperCase().equals("SISWA")) {
            txtKeterangan.setText("NIS "+GlobalVar.NIS+" Kelas "+ GlobalVar.namaKelas);
        }
        else {
            txtKeterangan.setText("NIP "+GlobalVar.NIP);
        }

        txtSekolah.setText(GlobalVar.namaSekolah);
        imgFoto.setImageBitmap(GlobalVar.fotoUser);

        txtSaldo=findViewById(R.id.txtSaldo);
        txtSaldo.setText("Rp "+GlobalVar.Saldo);

        btnMarketplace = findViewById(R.id.btn_marketplace);
        btnLogbook = findViewById(R.id.btn_logbook);
        btnRiwayatBelanja = findViewById(R.id.btn_management);
        btnStock = findViewById(R.id.btn_stock);

        btnMarketplace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView, MarketplaceFragment.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack("")
                        .commit();
            }
        });

        btnLogbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView, CartFragment.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack("")
                        .commit();
            }
        });

        btnRiwayatBelanja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView, RiwayatBelanjaFragment.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack("")
                        .commit();
            }
        });

        btnStock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView, StockFragment.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack("")
                        .commit();
            }
        });

        LoadingProduk l=new LoadingProduk();
        l.execute();

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
            String SQL= "Select * from kop_barang where id_sekolah="+GlobalVar.idSekolah;
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
                    .replace(R.id.fragmentContainerView, MarketplaceFragment.class, null)
                    .setReorderingAllowed(true)
                    .addToBackStack("")
                    .commit();
        }
    }

}