package com.skillage.appedu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.rustamg.filedialogs.FileDialog;

import java.io.File;

public class AdminKoperasiActivity extends AppCompatActivity implements FileDialog.OnFileSelectedListener {
    TextView txtUnitBisnis, txtKoperasi, txtYayasan;
    Button btnLogbook, btnManagement, btnStock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_admin_koperasi);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        txtUnitBisnis= findViewById(R.id.txt_unit_bisnis);
        txtKoperasi= findViewById(R.id.txt_koperasi);
        txtYayasan= findViewById(R.id.txt_yayasan);

        btnLogbook = findViewById(R.id.btn_logbook);
        btnManagement = findViewById(R.id.btn_management);
        btnStock = findViewById(R.id.btn_stock);

        txtUnitBisnis.setText("Ini seharusnya nama unit bisnis");
        txtKoperasi.setText(GlobalVar.namaSekolah);
        txtYayasan.setText(GlobalVar.namaYayasan);

        btnLogbook.setOnClickListener(new View.OnClickListener() {
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

        btnManagement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentAdminContainer, ManagementFragment.class, null)
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
                        .replace(R.id.fragmentAdminContainer, StockFragment.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack("")
                        .commit();
            }
        });

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentAdminContainer, MarketplaceFragment.class, null)
                .setReorderingAllowed(true)
                .addToBackStack("")
                .commit();
    }

    @Override
    public void onFileSelected(FileDialog dialog, File file) {
        String filenamepath=file.getAbsolutePath();
        if (GlobalVar.jenisUpload=="Upload Gambar") {
            ManagementFragment fragment = (ManagementFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentAdminContainer);
            fragment.updateFileName(filenamepath);

            GlobalVar.namafile="Id_sekolah-"+GlobalVar.idSekolah+"-"+GlobalVar.namaYayasan+"-"+file.getName();
            UploadFile up1 = new UploadFile();
            up1.activity=this;
            up1.context=this;
            up1.TargetFileName=GlobalVar.namafile;
            up1.FILE_UPLOAD_URL=  GlobalVar.URLAPI+ "upload.php";
            up1.FileName = filenamepath;
            up1.execute();
        }
    }
}