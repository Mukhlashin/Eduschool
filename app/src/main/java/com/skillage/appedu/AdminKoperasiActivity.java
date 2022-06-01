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

public class AdminKoperasiActivity extends AppCompatActivity {
    TextView txtHasil;
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

//        txtHasil= findViewById(R.id.txtHasil);
        btnLogbook = findViewById(R.id.btn_logbook);
        btnManagement = findViewById(R.id.btn_management);
        btnStock = findViewById(R.id.btn_stock);

        btnLogbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView, LogbookFragment.class, null)
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
                        .replace(R.id.fragmentContainerView, ManagementFragment.class, null)
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
                        .replace(R.id.fragmentContainerView, StockFragment.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack("")
                        .commit();
            }
        });

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, MarketplaceFragment.class, null)
                .setReorderingAllowed(true)
                .addToBackStack("")
                .commit();
    }
}