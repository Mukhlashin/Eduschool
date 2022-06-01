package com.skillage.appedu;

import android.icu.util.Calendar;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

public class LapAbsensiSiswaFragment extends Fragment implements DatePickerDialog.OnDateSetListener {
    private DatePickerDialog dpd;
    LinearLayout items;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    Button txtTanggal;
    public LapAbsensiSiswaFragment() {

    }

    public static LapAbsensiSiswaFragment newInstance(String param1, String param2) {
        LapAbsensiSiswaFragment fragment = new LapAbsensiSiswaFragment();
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
        return inflater.inflate(R.layout.fragment_lap_absensi_siswa, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Calendar now = Calendar.getInstance();
        if (dpd == null) {
            dpd = DatePickerDialog.newInstance(
                    LapAbsensiSiswaFragment.this,
                    now.get(Calendar.YEAR),
                    now.get(Calendar.MONTH),
                    now.get(Calendar.DAY_OF_MONTH)
            );
        } else {
            dpd.initialize(
                    LapAbsensiSiswaFragment.this,
                    now.get(Calendar.YEAR),
                    now.get(Calendar.MONTH),
                    now.get(Calendar.DAY_OF_MONTH)
            );
        }

        txtTanggal= (Button) view.findViewById(R.id.txtTanggal);
        txtTanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dpd.show(requireFragmentManager(), "Datepickerdialog");
            }
        });
        items = (LinearLayout) view.findViewById(R.id.items);
        items.removeAllViews();

        ViewLapAbsensiHeader v2=new ViewLapAbsensiHeader(getContext());
        v2.activity= (AppCompatActivity) getActivity();
        v2.setData("Tanggal","Pelajaran","Guru","Kehadiran","Keterangan");
        items.addView(v2);

        String tgl;
        DBAndroid db=new DBAndroid();
        db.getRecords("Select top 1 format(tanggal,'yyyy-MM-dd') as tanggal from vw_absensi where id_siswa="+GlobalVar.idSiswa+" order by tanggal");
        if (db.Records.size()>0) {
            tgl = db.Records.get(0).get("tanggal");
        } else
             tgl=now.get(Calendar.YEAR)+"-"+String.format("%02d",  now.get(Calendar.MONTH))+"-"+String.format("%02d",now.get(Calendar.DAY_OF_MONTH));

        Refresh(tgl);
        txtTanggal.setText(GlobalFunction.FormatTanggal(tgl+" 00:00:00"));
     }

     private void Refresh(String tgl) {
         items.removeAllViews();
         ViewLapAbsensiHeader v2=new ViewLapAbsensiHeader(getContext());
         v2.activity= (AppCompatActivity) getActivity();
         v2.setData("Tanggal","Pelajaran","Guru","Kehadiran","Keterangan");
         items.addView(v2);
         DBAndroid db=new DBAndroid();
         db.getRecords("Select format(tanggal,'dd-MMM-yyyy HH:MM') as tanggal,nama_pelajaran,nama_guru,kehadiran,keterangan from vw_absensi where id_siswa="+GlobalVar.idSiswa+" and Format(tanggal,'yyyy-MM-dd')='"+tgl+"' order by tanggal");
         if (db.Records.size()>0) {
             for (int i=0; i<=db.Records.size()-1; i++) {
                 String tanggal = db.Records.get(i).get("tanggal");
                 String pelajaran = db.Records.get(i).get("nama_pelajaran");
                 String namaguru = db.Records.get(i).get("nama_guru");
                 String kehadiran = db.Records.get(i).get("kehadiran");
                 String ketHadir="";
                 switch (kehadiran) {
                     case "A":
                         ketHadir="Alpha";
                         break;
                     case "I":
                         ketHadir="Izin";
                         break;
                     case "M":
                         ketHadir="Masuk";
                         break;
                     case "S":
                         ketHadir="Sakit";
                         break;
                     default:
                         ketHadir="";
                         break;
                 }
                 String keterangan = db.Records.get(i).get("keterangan");
                 if (keterangan.toUpperCase().equals("NULL")) keterangan="-";
                 ViewLapAbsensi v=new ViewLapAbsensi(getContext());
                 v.activity= (AppCompatActivity) getActivity();
                 v.setData(tanggal,pelajaran,namaguru,ketHadir,keterangan);
                 items.addView(v);
             }
         }
     }
    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        txtTanggal.setText(GlobalFunction.FormatTanggal(year+"-"+String.format("%02d",monthOfYear+1)+"-"+String.format("%02d",dayOfMonth)+" 00:00:00"));
        txtTanggal.setTag(year+"-"+String.format("%02d", monthOfYear+1)+"-"+String.format("%02d", dayOfMonth));
        Refresh(txtTanggal.getTag().toString());

    }
}