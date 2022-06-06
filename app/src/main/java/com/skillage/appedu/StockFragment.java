package com.skillage.appedu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class StockFragment extends Fragment {
    LinearLayout items;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_stock, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        items = view.findViewById(R.id.items);
        SwipeRefreshLayout refresh=view.findViewById(R.id.refresh);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                RefreshData();
                refresh.setRefreshing(false);
            }
        });
        RefreshData();
    }

    private  void RefreshData(){
        items.removeAllViews();
        ViewStokHeader v2=new ViewStokHeader(getContext());
        items.addView(v2);
        DBAndroid db=new DBAndroid();
        db.getRecords("Select * from kop_barang where id_unit_bisnis="+GlobalVar.idUnitBisnis);
        if (db.Records.size()>0) {
            for (int i=0; i<db.Records.size()-1; i++) {
                ViewStok v=new ViewStok(getContext());
                String harga=GlobalFunction.formatAngka( db.Records.get(i).get("harga"));
                v.setData(db.Records.get(i).get("nama_barang"),db.Records.get(i).get("deskripsi"),harga,db.Records.get(i).get("stok"));
                items.addView(v);
            }
        }
    }
}