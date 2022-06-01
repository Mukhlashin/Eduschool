package com.skillage.appedu;
import android.graphics.Bitmap;

import java.util.LinkedHashMap;

public class GlobalVar {
    public static String userID;
    public static String userName;
    public static String password;
    public static String role;
    public static String TypeUser;
    public static String URL = "http://saburai.ac.id/";
    public static String URLAPI = "http://saburai.ac.id/apisekolah/";
    public static String token;
    public static int Saldo;

    //upload
    public static String jenisUpload="";
    public static String id_materi;
    public static String namafile;

    public static String namaSekolah,namaKabupaten,namaProvinsi;

    //siswa
    public static String NIS,idKelas,kelas,namaKelas,idSekolah,idSiswa;
    public static Bitmap fotoUser;
    public static DBAndroid jadwalkuliah=new DBAndroid();
    public static DBAndroid materi=new DBAndroid();
    //guru
    public static String NIP,idGuru;
    public static String namapelajaran;

}
