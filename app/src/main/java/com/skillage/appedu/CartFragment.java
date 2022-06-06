package com.skillage.appedu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CartFragment extends Fragment  {

    private RecyclerView recyclerView;
    LinearLayout panelTotal;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    TextView lblJumlah,txtJudul;
    Button btnCheckOut;

    public CartFragment() {
    }

    public static CartFragment newInstance(String param1, String param2) {
        CartFragment fragment = new CartFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        panelTotal=view.findViewById(R.id.panelTotal);
        lblJumlah= view.findViewById(R.id.lblJumlah);
        btnCheckOut=view.findViewById(R.id.btnCheckOut);
        txtJudul=view.findViewById(R.id.txtJudul);
        recyclerView = view.findViewById(R.id.rv_cart);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(container.getContext()));

        btnCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBAndroid db=new DBAndroid();
                DBAndroid db2=new DBAndroid();
                db.getRecords("Select * from kop_barang_keluar where userid='"+GlobalVar.userID+"' and status='DALAM KERANJANG'");
                if (db.Records.size()>0) {
                    for (int i=0; i<=db.Records.size()-1;i++) {
                        db2.execute("Update kop_barang_keluar set status='CHECK OUT' where id="+db.Records.get(i).get("id"));
                    }
                }
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView, RiwayatBelanjaFragment.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack("")
                        .commit();
            }
        });
        CartAdapter.MyClickListener mcl = new CartAdapter.MyClickListener() {
            @Override
            public void onItemClick(String action) {

                DBAndroid db=new DBAndroid();
                db.getRecords("select sum(total) as total from vw_barang_keluar where userid='"+GlobalVar.userID+"' and status='DALAM KERANJANG'");
                if (db.Records.size()>0) {
                    String total = db.Records.get(0).get("total");
                    if (total!="") {
                        total= String.format("%,.0f",Double.parseDouble(total));
                        if (Double.parseDouble(total)==0) {
                            panelTotal.setVisibility(View.GONE);
                            txtJudul.setText("Keranjang belanja kamu kosong");
                        }
                    }
                    lblJumlah.setText("Rp "+total);
                } else {
                    panelTotal.setVisibility(View.GONE);
                    txtJudul.setText("Keranjang belanja kamu kosong");
                }
                if (action.equals("delete")) {
                    CartAdapter adp= new CartAdapter(getContext(),this);
                    recyclerView.setAdapter(adp);
                }

            }
        };

        CartAdapter adp= new CartAdapter(getContext(),mcl);
        recyclerView.setAdapter(adp);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        GlobalVar.jenisList = "DALAM KERANJANG";
        DBAndroid db=new DBAndroid();
        db.getRecords("select sum(total) as total from vw_barang_keluar where userid='"+GlobalVar.userID+"' and status='DALAM KERANJANG'");
        if (db.Records.size()>0) {
            String total = db.Records.get(0).get("total");
            if (total!="") {
                total= String.format("%,.0f",Double.parseDouble(total));
            } else {
                panelTotal.setVisibility(View.GONE);
                txtJudul.setText("Keranjang belanja kamu kosong");
            }
            lblJumlah.setText("Rp "+total);

        } else {
            panelTotal.setVisibility(View.GONE);
            txtJudul.setText("Keranjang belanja kamu kosong");
        }
    }
}