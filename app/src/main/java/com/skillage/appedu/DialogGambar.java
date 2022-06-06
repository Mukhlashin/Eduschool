package com.skillage.appedu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import uk.co.senab.photoview.PhotoView;


public class DialogGambar extends AppCompatActivity {
    Button btnOK;
    PhotoView foto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_gambar);
        this.setFinishOnTouchOutside(false);

        btnOK = (Button) findViewById(R.id.btnOK);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        foto = (PhotoView) findViewById(R.id.foto);
        Intent i=getIntent();
        String namafile=i.getStringExtra("filename");
        CircularProgressDrawable circularProgressDrawable=new CircularProgressDrawable(this);
        circularProgressDrawable.setStrokeWidth(5f);
        circularProgressDrawable.setCenterRadius(30f);
        circularProgressDrawable.start();
        Glide
                .with(this)
                .load(GlobalVar.URL+"uploadsiswa/"+namafile)
                .placeholder(circularProgressDrawable)
                .thumbnail(0.7f)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .centerCrop()
                .into(foto);

    }
}
