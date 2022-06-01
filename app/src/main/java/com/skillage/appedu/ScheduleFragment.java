package com.skillage.appedu;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

;import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class ScheduleFragment extends Fragment {
    LinearLayout listJadwal;

    static String hari;
    public ScheduleFragment() {
        // Required empty public constructor
    }

    public static ScheduleFragment newInstance(String h) {
        ScheduleFragment fragment = new ScheduleFragment();
        hari=h;
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_schedule, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listJadwal = (LinearLayout) view.findViewById(R.id.listJadwal);
        listJadwal.removeAllViews();

        Bundle b= getArguments();
        hari= b.getString("hari").toUpperCase();
        
        for (int i=0; i<GlobalVar.jadwalkuliah.Records.size();i++) {
            if (GlobalVar.jadwalkuliah.Records.get(i).get("hari").toUpperCase().equals(hari)) {
                String jam = GlobalVar.jadwalkuliah.Records.get(i).get("jam_mulai")+"-"+GlobalVar.jadwalkuliah.Records.get(i).get("jam_selesai");
                String ruang = GlobalVar.jadwalkuliah.Records.get(i).get("tahun_pelajaran");
                String kelas = GlobalVar.jadwalkuliah.Records.get(i).get("nama_kelas");
                String namamk = GlobalVar.jadwalkuliah.Records.get(i).get("nama_pelajaran");
                String namadosen = GlobalVar.jadwalkuliah.Records.get(i).get("nama_guru");
                ViewJadwal v = new ViewJadwal(getContext());
                v.setData(namamk, "Kelas " + kelas, namadosen, jam);
                listJadwal.addView(v);
            }
        }
    }

}
