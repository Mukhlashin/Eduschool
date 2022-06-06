package com.skillage.appedu;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class Product {
    String id,namaproduk,harga,stok;
    Bitmap foto;
    public Product(String id,Bitmap foto,String namaproduk,String harga,String stok) {
        this.id=id;
        this.foto=foto;
        this.namaproduk=namaproduk;
        this.harga=harga;
        this.stok=stok;
    }
}

