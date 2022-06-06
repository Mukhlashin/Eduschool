package com.skillage.appedu;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class MarketplaceFragment extends Fragment {

    private RecyclerView recyclerView;
    TextView txtLoading;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_marketplace, container, false);
        Button btnAdd=view.findViewById(R.id.btnAdd);
        Button btnRefresh=view.findViewById(R.id.btnRefresh);
        RelativeLayout panelButton=view.findViewById(R.id.panelButton);
        if (!GlobalVar.role.equals("R.11")) {
            panelButton.setVisibility(View.GONE);

        }
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoadingProduk l=new LoadingProduk();
                l.execute();
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GlobalVar.idProduk="";
                FragmentManager fragmentManager = ((AppCompatActivity) getActivity()).getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentAdminContainer, ManagementFragment.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack("")
                        .commit();
            }
        });
        recyclerView = view.findViewById(R.id.rv_marketplace);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new GridLayoutManager(container.getContext(), 5));
        recyclerView.setAdapter(new MarketplaceAdapter(getContext()));
        //progressBar.hide();
        return view;
    }

    private class LoadingProduk extends AsyncTask<String, Integer, Integer> {

        DBAndroid db=new DBAndroid();
        ProgressDialog progressDialog=new ProgressDialog(getContext());
        @Override
        protected void onPreExecute() {
            progressDialog.setTitle("Loading produk...");
            progressDialog.show();
            super.onPreExecute();
        }

        protected Integer doInBackground(String... urls) {
            String SQL= "Select * from kop_barang where id_unit_bisnis="+GlobalVar.idUnitBisnis;
            db.getRecords(SQL);
            GlobalVar.produk.clear();
            for (int i=0; i<=db.Records.size()-1; i++) {
                String id= db.Records.get(i).get("id_barang");
                String nama= db.Records.get(i).get("nama_barang");
                String harga= db.Records.get(i).get("harga");
                String stok= db.Records.get(i).get("stok");
                String foto= db.Records.get(i).get("foto");
                Bitmap fotonya= GlobalFunction.loadImageFromURL(GlobalVar.URL+"foto/"+foto);
                Product p=new Product(id,fotonya,nama,harga,stok);
                GlobalVar.produk.add(p);

            }
            return db.Records.size();
        }

        protected void onProgressUpdate(Integer... progress) {

        }

        protected void onPostExecute(Integer result) {
            progressDialog.hide();
            recyclerView.setAdapter(new MarketplaceAdapter(getContext()));
        }
    }
}



