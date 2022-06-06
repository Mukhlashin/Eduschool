package com.skillage.appedu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        LinearLayout menuKoperasi = findViewById(R.id.menuKoperasi);
        LinearLayout menuEDU = findViewById(R.id.menuEDU);
        TextView lblNotice = findViewById(R.id.lblNotice);
        TextView lblKeterangan1 = findViewById(R.id.lblKeterangan1);
        TextView lblJumlahKet1 = findViewById(R.id.lblJumlahKet1);
        TextView lblKeterangan2 = findViewById(R.id.lblKeterangan2);
        TextView lblJumlahKet2 = findViewById(R.id.lblJumlahKet2);


        DBAndroid db=new DBAndroid();
        if (GlobalVar.TypeUser.toUpperCase().equals("SISWA")) {
            lblKeterangan1.setText("Jumlah Tugas");
            lblKeterangan2.setText("Jumlah Upload");
            db.getRecords("select * from vw_materi " +
                    "where jenis_materi='tugas' and nama_kelas='"+GlobalVar.namaKelas+"' and id_sekolah="+GlobalVar.idSekolah);
            lblJumlahKet1.setText(db.Records.size()+"");
            db.getRecords("select * from t_upload_tugas " +
                    "where id_siswa="+GlobalVar.idSiswa);
            lblJumlahKet2.setText(db.Records.size()+"");

        }

        if (GlobalVar.TypeUser.toUpperCase().equals("GURU")) {
            lblKeterangan1.setText("Jumlah Materi");
            lblKeterangan2.setText("Jumlah Tugas");

            db.getRecords("select * from vw_materi " +
                    "where jenis_materi='pelajaran' and id_guru='"+GlobalVar.idGuru+"' and id_sekolah="+GlobalVar.idSekolah);
            lblJumlahKet1.setText(db.Records.size()+"");
            db.getRecords("select * from vw_materi " +
                    "where jenis_materi='tugas' and id_guru='"+GlobalVar.idGuru+"' and id_sekolah="+GlobalVar.idSekolah);
            lblJumlahKet2.setText(db.Records.size()+"");

        }

        db.getRecords("Select top 1 info from t_info where id_sekolah="+GlobalVar.idSekolah+" order by tanggal desc");
        if (db.Records.size()>0)
            lblNotice.setText(db.Records.get(0).get("info"));

        menuEDU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),EDUMainActivity.class);
                startActivity(i);
            }
        });

        menuKoperasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),KoperasiMainActivity.class);
                startActivity(i);
            }
        });

    }
}