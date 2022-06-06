package com.skillage.appedu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.rustamg.filedialogs.FileDialog;

import java.io.File;

public class UploadTugasSiswaFragment extends Fragment  {
    LinearLayout items;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public UploadTugasSiswaFragment() {

    }

    public static UploadTugasSiswaFragment newInstance(String param1, String param2) {
        UploadTugasSiswaFragment fragment = new UploadTugasSiswaFragment();
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
        return inflater.inflate(R.layout.fragment_upload_tugas, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        items=(LinearLayout) view.findViewById(R.id.items);
        items.removeAllViews();
        DBAndroid db=new DBAndroid();
        db.getRecords("select * from vw_tugas_siswa where id_sekolah="+GlobalVar.idSekolah+" and semester='"+GlobalVar.Semester+"' and tahun_pelajaran='"+GlobalVar.TahunPelajaran+"' and nama_kelas='"+ GlobalVar.namaKelas+"'");
        for (int i=0; i<=db.Records.size()-1;i++) {
            ViewTugas v=new ViewTugas(getContext());
            v.activity= (AppCompatActivity) getActivity() ;
            String id= db.Records.get(i).get("id");
            String kode= db.Records.get(i).get("kode");
            String nama_pelajaran= db.Records.get(i).get("nama_pelajaran");
            String nama_kelas= db.Records.get(i).get("nama_kelas");
            String id_guru= db.Records.get(i).get("id_guru");
            String nama_guru= db.Records.get(i).get("nama_guru");
            String keterangan= db.Records.get(i).get("keterangan");
            String nama_file= db.Records.get(i).get("nama_file");
            String nama_file_upload= db.Records.get(i).get("nama_file_upload");
            v.setData(id,kode,nama_pelajaran,nama_kelas,id_guru,nama_guru,keterangan,nama_file,nama_file_upload);
            items.addView(v);
        }
    }



}