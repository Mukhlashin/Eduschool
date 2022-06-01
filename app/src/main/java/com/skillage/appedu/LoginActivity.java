package com.skillage.appedu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {
    AppCompatButton btnLogin,btnDaftar;
     EditText txtUserID,txtPassword;

    String role,userid;
    ProgressDialog progressDialog ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        btnLogin = (AppCompatButton) findViewById(R.id.btnLogin);
        btnDaftar = (AppCompatButton) findViewById(R.id.btnDaftar);
        txtUserID=(EditText) findViewById(R.id.txtUserID);
        txtPassword=(EditText) findViewById(R.id.txtPassword);

        progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);

        //txtUserID.setText("indra@gmail.com");
        txtUserID.setText("antonsiswa@gmail.com");

        txtUserID.setText("");
        txtPassword.setText("");
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GlobalVar.token=DBAndroid.EnkripMD5(txtUserID.getText().toString());
                GlobalVar.userID=txtUserID.getText().toString();
                DBAndroid db=new DBAndroid();
                String SQL= "Select * from t_usr where userid='"+txtUserID.getText()+"'";
                db.getRecords(SQL);
                if (db.Records.size()==0) {
                    GlobalFunction.ShowDialog(LoginActivity.this,"User tidak ditemukan");
                } else {
                    role=db.Records.get(0).get("role");
                    userid=db.Records.get(0).get("userid");
                    switch (role) {
                        case "R.10" : //siswa
                            LoadingSiswa s = new LoadingSiswa();
                            s.execute();
                            break;
                        case "R.5" : //guru
                            LoadingGuru g = new LoadingGuru();
                            g.execute();
                            break;
                    }
                }
            }
        });
    }


    private class LoadingSiswa extends AsyncTask<String, String, String> {
        DBAndroid db=new DBAndroid();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Loading siswa...");
            progressDialog.show();
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected String doInBackground(String... params) {
            db.getRecords("Select * from vw_siswa where userid='"+userid+"'");
            return null;
        }

        @Override
        protected void onPostExecute(String rs) {
            if (!db.isEmpty()) {
                GlobalVar.userName= db.Records.get(0).get("nama_siswa");
                GlobalVar.NIS=db.Records.get(0).get("NIS");
                GlobalVar.idSiswa=db.Records.get(0).get("id");
                GlobalVar.TypeUser="SISWA";
                GlobalVar.idKelas =db.Records.get(0).get("id_kelas");
                GlobalVar.kelas =db.Records.get(0).get("kelas");
                GlobalVar.namaKelas =db.Records.get(0).get("nama_kelas");
                GlobalVar.idSekolah =db.Records.get(0).get("id_sekolah");
                GlobalVar.namaSekolah =db.Records.get(0).get("nama_sekolah");
                GlobalVar.namaKabupaten =db.Records.get(0).get("nama_kabupaten");
                GlobalVar.namaProvinsi =db.Records.get(0).get("nama_provinsi");
                String saldo=db.Records.get(0).get("saldo");
                if (saldo.toUpperCase().equals("NULL")) saldo="0";
                GlobalVar.Saldo= Integer.parseInt( saldo);
                GlobalVar.fotoUser = GlobalFunction.loadImageFromURL(GlobalVar.URL+"/foto/"+db.Records.get(0).get("id")+".jpg");
                if (GlobalVar.fotoUser==null) {
                    GlobalVar.fotoUser= BitmapFactory.decodeResource(getResources(), R.drawable.photo_male_1);
                }
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                progressDialog.hide();
            }
        }

    }

    private class LoadingGuru extends AsyncTask<String, String, String> {
        DBAndroid db=new DBAndroid();
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Loading guru...");
            progressDialog.show();

        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected String doInBackground(String... params) {
            db.getRecords("Select * from vw_guru where userid='"+userid+"'");
            return null;
        }

        @Override
        protected void onPostExecute(String rs) {
            if (!db.isEmpty()) {
                GlobalVar.userName=db.Records.get(0).get("nama_guru");
                GlobalVar.NIP=db.Records.get(0).get("NIP");
                GlobalVar.TypeUser="GURU";
                GlobalVar.idGuru =db.Records.get(0).get("id");
                GlobalVar.idSekolah =db.Records.get(0).get("id_sekolah");
                GlobalVar.namaSekolah =db.Records.get(0).get("nama_sekolah");
                GlobalVar.namaKabupaten =db.Records.get(0).get("nama_kabupaten");
                GlobalVar.namaProvinsi =db.Records.get(0).get("nama_provinsi");
                String saldo=db.Records.get(0).get("saldo");
                if (saldo.toUpperCase().equals("NULL")) saldo="0";
                GlobalVar.Saldo= Integer.parseInt( saldo);
                GlobalVar.fotoUser = GlobalFunction.loadImageFromURL(GlobalVar.URL+"/foto/"+db.Records.get(0).get("id")+".jpg");
                if (GlobalVar.fotoUser==null) {
                    GlobalVar.fotoUser= BitmapFactory.decodeResource(getResources(), R.drawable.photo_male_1);
                }
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                progressDialog.hide();
            }
        }

    }


}