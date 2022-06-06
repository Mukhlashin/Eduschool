package com.skillage.appedu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MyConfirmDialog extends AppCompatActivity {
    Button btnOK,btnBatal;
    TextView txtPesan;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_confirm);
        this.setFinishOnTouchOutside(false);

        btnBatal= (Button) findViewById(R.id.btnBatal);
        btnBatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 finish();
            }
        });
        btnOK = (Button) findViewById(R.id.btnOK);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) GlobalFunction.mcontext).finish();  //activity yg memanggil dialog ditutup
                finish(); //dirinya sendiri jg ditutup
            }
        });
        txtPesan = (TextView) findViewById(R.id.txtPesan);
        Intent i=getIntent();
        String pesan=i.getStringExtra("Pesan");
        txtPesan.setText(pesan);
    }
}
