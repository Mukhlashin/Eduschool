package com.skillage.appedu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;


public class RiwayatBelanjaFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    LinearLayout items;

    public RiwayatBelanjaFragment() {

    }

    public static RiwayatBelanjaFragment newInstance(String param1, String param2) {
        RiwayatBelanjaFragment fragment = new RiwayatBelanjaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_riwayat_belanja, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        items = view.findViewById(R.id.items);
        items.removeAllViews();
        ViewBelanjaHeader v2=new ViewBelanjaHeader(getContext());
        v2.setData("","","","","");
        items.addView(v2);
        DBAndroid db=new DBAndroid();
        db.getRecords("select no_faktur,tanggal,nama_unit_bisnis, sum(total) as total,status from vw_barang_keluar " +
                "where userid='"+GlobalVar.userID+"' "+
                "group by no_faktur,tanggal,nama_unit_bisnis,status order by tanggal,no_faktur");
        if (db.Records.size()>0) {
            for (int i=0; i<=db.Records.size()-1; i++) {
                String no_faktur=db.Records.get(i).get("no_faktur");
                String tanggal=db.Records.get(i).get("tanggal");
                String nama_unit_bisnis=db.Records.get(i).get("nama_unit_bisnis");
                String total=GlobalFunction.formatAngka(db.Records.get(i).get("total"));
                String status=db.Records.get(i).get("status");
                ViewBelanja v=new ViewBelanja(getContext());
                v.activity= (AppCompatActivity) getActivity() ;
                v.context=getContext();
                v.setData(no_faktur,tanggal,nama_unit_bisnis,total,status);
                items.addView(v);
            }
        } else {
            TextView t=new TextView(getContext());
            t.setText("Kamu belum mempunyai riwayat belanja");
            items.addView(t);
        }
    }


}