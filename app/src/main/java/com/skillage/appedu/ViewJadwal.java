package com.skillage.appedu;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ViewJadwal extends RelativeLayout {
    Context context;
    View rootView;
    LinearLayout item;
    TextView txtNamaMK,txtDosen,txtRuang,txtJam;

    public ViewJadwal(Context context) {
        super(context);
        this.context=context;
        init(context);
    }

    private void init(final Context context) {
        rootView = inflate(context, R.layout.viewjadwal, this);
        item = (LinearLayout) rootView.findViewById(R.id.item);
        txtNamaMK = (TextView) rootView.findViewById(R.id.txtNamaMK);
        txtDosen = (TextView) rootView.findViewById(R.id.txtDosen);
        txtRuang = (TextView) rootView.findViewById(R.id.txtRuang);
        txtJam = (TextView) rootView.findViewById(R.id.txtJam);
        item.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    public void setData(String namamk , String dosen, String ruang, String jam){
        txtNamaMK.setText(namamk);
        txtDosen.setText(dosen);
        txtRuang.setText(ruang);
        txtJam.setText(jam);
   }
}
