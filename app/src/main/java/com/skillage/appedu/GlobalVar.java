package com.skillage.appedu;
import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.HashMap;
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
    public static String namafile,namafileAsal;

    public static String namaSekolah,namaYayasan,namaUnitBisnis,namaKabupaten,namaProvinsi;
    public static String TahunPelajaran,Semester;

    //siswa
    public static String NIS,idKelas,kelas,namaKelas,idUnitBisnis,idSekolah,idSiswa;
    public static Bitmap fotoUser,fotoKartu;
    public static DBAndroid jadwalkuliah=new DBAndroid();
    public static DBAndroid materi=new DBAndroid();
    //guru
    public static String NIP,idGuru;
    public static String namapelajaran;
    public static ArrayList<Product> produk = new ArrayList<>();
    public static ArrayList<Pembeli> pembeli = new ArrayList<>();

    public static String idProduk,nofaktur,jenisList;


}
