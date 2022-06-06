package com.skillage.appedu;

import android.graphics.Bitmap;

public class Pembeli {
    String id,no_faktur,total;
    Bitmap foto;

    String tanggal,userid,N,nama,pembeli,idpembeli;

    public Pembeli(String no_faktur,String tanggal,String userid,String N,String nama,String pembeli,String idpembeli,String total, Bitmap foto) {
        this.id=id;
        this.no_faktur=no_faktur;
        this.tanggal=tanggal;
        this.userid=userid;
        this.N=N;
        this.nama=nama;
        this.pembeli=pembeli;
        this.idpembeli=idpembeli;
        this.total=total;
        this.foto=foto;
    }
}

