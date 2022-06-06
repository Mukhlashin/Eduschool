package com.skillage.appedu;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class LogbookFragment extends Fragment {
    SwipeRefreshLayout refreshLayout;
    LinearLayout items;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_logbook, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        refreshLayout= view.findViewById(R.id.refresh);
        items=view.findViewById(R.id.items);
        refreshLayout.setColorSchemeResources(
                android.R.color.holo_green_dark, android.R.color.holo_blue_dark,
                android.R.color.holo_orange_dark, android.R.color.holo_red_dark);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                LoadingProduk l = new LoadingProduk();
                l.execute();
            }
        });

        LoadingProduk l = new LoadingProduk();
        l.execute();
    }



    private class LoadingProduk extends AsyncTask<String, Integer, Integer> {
        DBAndroid db=new DBAndroid();
        ProgressDialog progressDialog=new ProgressDialog(getContext());
        @Override
        protected void onPreExecute() {
            progressDialog.setTitle("Loading pesanan...");
            progressDialog.show();
            super.onPreExecute();
        }

        protected Integer doInBackground(String... urls) {
            String SQL= "Select * from vw_rekap_transaksi where id_unit_bisnis="+GlobalVar.idUnitBisnis;
            db.getRecords(SQL);
            GlobalVar.pembeli.clear();
            for (int i=0; i<=db.Records.size()-1; i++) {
                String no_faktur= db.Records.get(i).get("no_faktur");
                String tanggal= db.Records.get(i).get("tanggal");
                String userid= db.Records.get(i).get("userid");
                String N= db.Records.get(i).get("N");
                String nama= db.Records.get(i).get("nama");
                String pembeli= db.Records.get(i).get("pembeli");
                String idpembeli= db.Records.get(i).get("idpembeli");
                String total= db.Records.get(i).get("total");
                Bitmap fotonya= GlobalFunction.loadImageFromURL(GlobalVar.URL+"foto/"+idpembeli+".jpg");
                Pembeli p=new Pembeli(no_faktur,tanggal,userid,N,nama,pembeli,idpembeli,total,fotonya);
                GlobalVar.pembeli.add(p);
            }
            return db.Records.size();
        }

        protected void onProgressUpdate(Integer... progress) {

        }

        protected void onPostExecute(Integer result) {
            progressDialog.hide();
            items.removeAllViews();
            ViewPembeliHeader v2=new ViewPembeliHeader(getContext());
            v2.setData("","","","","");
            items.addView(v2);
            for (int i=0; i<=GlobalVar.pembeli.size()-1; i++) {
                ViewPembeli v=new ViewPembeli(getContext());
                v.activity= (AppCompatActivity) getActivity();
                String total=GlobalFunction.formatAngka( GlobalVar.pembeli.get(i).total);
                v.setData(GlobalVar.pembeli.get(i).no_faktur,GlobalVar.pembeli.get(i).tanggal,GlobalVar.pembeli.get(i).nama,total,GlobalVar.pembeli.get(i).pembeli);
                items.addView(v);
            }

            refreshLayout.setRefreshing(false);
        }
    }
}