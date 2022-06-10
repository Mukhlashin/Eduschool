package com.skillage.appedu;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;

public class ViewLapJual extends RelativeLayout {
    Context context;
    View rootView;
    AppCompatActivity activity;
    String nofaktur;
    ConstraintLayout item;
    TextView txtTanggal,txtNoFaktur,txtNamaUnitUsaha,txtStatus,txtTotal;

    public ViewLapJual(Context context) {
        super(context);
        this.context=context;
        init(context);
    }

    private void init(final Context context) {
        rootView = inflate(context, R.layout.viewbelanja, this);
        item=(ConstraintLayout) rootView.findViewById(R.id.item);
        txtTanggal = (TextView) rootView.findViewById(R.id.txtTanggal);
        txtNoFaktur = (TextView) rootView.findViewById(R.id.txtNoFaktur);
        txtNamaUnitUsaha = (TextView) rootView.findViewById(R.id.txtNamaUnitUsaha);
        txtStatus = (TextView) rootView.findViewById(R.id.txtStatus);
        txtTotal = (TextView) rootView.findViewById(R.id.txtTotal);
        item.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                GlobalVar.nofaktur=nofaktur;
                FragmentManager fragmentManager = activity.getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentAdminContainer, DetilPenjualan.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack("")
                        .commit();
            }
        });
    }

    public void setData(String nofaktur, String tanggal, String unitusaha,String total,String status){
        this.nofaktur=nofaktur;
        txtTanggal.setText(tanggal);
        txtNoFaktur.setText(nofaktur);
        txtNamaUnitUsaha.setText(unitusaha);
        txtTotal.setText(total);
        txtStatus.setText(status);
    }
}
