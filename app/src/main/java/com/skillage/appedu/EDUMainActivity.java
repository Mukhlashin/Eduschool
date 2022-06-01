package com.skillage.appedu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.makeramen.roundedimageview.RoundedImageView;
import com.rustamg.filedialogs.FileDialog;

import java.io.File;

public class EDUMainActivity extends AppCompatActivity  implements FileDialog.OnFileSelectedListener{
    LinearLayout menuSiswa,menuGuru;
    LinearLayout menuJadwal,menuModul,menuUploadTugas,menuAbsensi,menuChatSekolahSiswa,menuRaporNilai;
    LinearLayout menuJadwalMengajar,menuGuruPresensi,menuGuruUploadMateri,menuGuruUploadTugas,menuGuruBankSoal,menuGuruInputNilai,menuChatOnlineGuru;
    TextView txtNama,txtKeterangan,txtSekolah;
    RoundedImageView imgFoto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_edumain);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        menuSiswa=findViewById(R.id.menuSiswa);
        menuGuru=findViewById(R.id.menuGuru);
        txtNama=findViewById(R.id.txtNama);
        txtKeterangan=findViewById(R.id.txtKeterangan);
        txtSekolah=findViewById(R.id.txtSekolah);
        imgFoto=findViewById(R.id.imgFoto);

        txtNama.setText(GlobalVar.userName);
        if (GlobalVar.TypeUser.toUpperCase().equals("SISWA")) {
            txtKeterangan.setText("NIS "+GlobalVar.NIS+" Kelas "+ GlobalVar.namaKelas);
            menuSiswa.setVisibility(View.VISIBLE);
            menuGuru.setVisibility(View.GONE);
        }
        else {
            txtKeterangan.setText("NIP "+GlobalVar.NIP);
            menuSiswa.setVisibility(View.GONE);
            menuGuru.setVisibility(View.VISIBLE);
        }

        txtSekolah.setText(GlobalVar.namaSekolah);

        imgFoto.setImageBitmap(GlobalVar.fotoUser);

        menuJadwal = findViewById(R.id.menuJadwal);
        menuModul=findViewById(R.id.menuModul);
        menuUploadTugas=findViewById(R.id.menuUploadTugas);
        menuAbsensi=findViewById(R.id.menuAbsensi);

        menuJadwalMengajar=findViewById(R.id.menuJadwalMengajar);
        menuGuruPresensi=findViewById(R.id.menuGuruPresensi);
        menuGuruUploadMateri=findViewById(R.id.menuGuruUploadMateri);
        menuGuruUploadTugas=findViewById(R.id.menuGuruUploadTugas);

        menuJadwal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jadwal();
            }
        });
        menuModul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modul();
            }
        });
        menuUploadTugas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadtugas();
            }
        });
        menuAbsensi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lapabsensi();
            }
        });

        menuJadwalMengajar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jadwalguru();
            }
        });
        menuGuruPresensi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                absensisiswa();
            }
        });
        menuGuruUploadMateri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadmateri();
            }
        });

        menuGuruUploadTugas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadtugaspr();
            }
        });

        boolean bl= haveStoragePermission();
        homePage();
    }



    private void homePage(){
        Fragment f = HomeSiswaFragment.newInstance("","");
        getSupportFragmentManager().beginTransaction()
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
    private void jadwal(){
        Fragment f = JadwalSiswaFragment.newInstance("Test Dong","Test2");
        getSupportFragmentManager().beginTransaction()
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
    private void modul(){
        Fragment f = MateriDownloadSiswaFragment.newInstance("Test Dong","Test2");
        getSupportFragmentManager().beginTransaction()
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
    private void uploadtugas(){
        Fragment f = UploadTugasSiswaFragment.newInstance("Test Dong","Test2");
        getSupportFragmentManager().beginTransaction()
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
    private void lapabsensi(){
        Fragment f = LapAbsensiSiswaFragment.newInstance("Test Dong","Test2");
        getSupportFragmentManager().beginTransaction()
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
    private void jadwalguru(){
        Fragment f = JadwalGuruFragment.newInstance("Test Dong","Test2");
        getSupportFragmentManager().beginTransaction()
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
    private void absensisiswa(){
        Fragment f = AbsensiSiswaFragment.newInstance("Test Dong","Test2");
        getSupportFragmentManager().beginTransaction()
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

    private void uploadmateri(){
        Fragment f = UploadMateriFragment.newInstance("Test Dong","Test2");
        getSupportFragmentManager().beginTransaction()
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

    private void uploadtugaspr(){
        Fragment f = UploadTugasFragment.newInstance("Test Dong","Test2");
        getSupportFragmentManager().beginTransaction()
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

    @Override
    public void onFileSelected(FileDialog dialog, File file) {
        String filenamepath=file.getAbsolutePath();
        if (GlobalVar.jenisUpload=="Upload Tugas Siswa") {
            String filename="Siswa-"+GlobalVar.idSiswa+"-"+file.getName();
            UploadFile up1 = new UploadFile();
            up1.activity=this;
            up1.context=this;
            up1.TargetFileName=filename;
            up1.FILE_UPLOAD_URL=  GlobalVar.URLAPI+ "upload.php";
            up1.FileName = filenamepath;
            up1.execute();

            DBAndroid db=new DBAndroid();
            db.execute("Insert into t_upload_tugas (id_siswa,id_materi,nama_file) " +
                    "values ("+GlobalVar.idSiswa+","+GlobalVar.id_materi+",'"+filename+"')");

            Fragment f = UploadTugasSiswaFragment.newInstance("Test Dong","Test2");
            getSupportFragmentManager().beginTransaction()
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

        if (GlobalVar.jenisUpload=="Upload Materi") {
            UploadMateriFragment fragment = (UploadMateriFragment) getSupportFragmentManager().findFragmentById(R.id.mainframe);
            fragment.updateFileName(filenamepath);

            GlobalVar.namafile="Materi-"+GlobalVar.idGuru+"-"+GlobalVar.namapelajaran+"-"+file.getName();
            UploadFile up1 = new UploadFile();
            up1.activity=this;
            up1.context=this;
            up1.TargetFileName=GlobalVar.namafile;
            up1.FILE_UPLOAD_URL=  GlobalVar.URLAPI+ "upload.php";
            up1.FileName = filenamepath;
            up1.execute();
        }

        }

    public  boolean haveStoragePermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.e("Permission error","You have permission");
                return true;
            } else {

                Log.e("Permission error","You have asked for permission");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //you dont need to worry about these stuff below api level 23
            Log.e("Permission error","You already have the permission");
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0]== PackageManager.PERMISSION_GRANTED){

        }
    }


}