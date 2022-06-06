package com.skillage.appedu;

import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

public class AbsensiSiswaFragment extends Fragment implements DatePickerDialog.OnDateSetListener {
    private DatePickerDialog dpd;
    Spinner cbKelas,cbPelajaran;
    LinearLayout items;
    TextView txtTanggal,btnSimpan;
    DBAndroid dbDataAbsen=new DBAndroid();
    String id_guru,id_kelas,id_pelajaran,tanggal;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public AbsensiSiswaFragment() {

    }

    public static AbsensiSiswaFragment newInstance(String param1, String param2) {
        AbsensiSiswaFragment fragment = new AbsensiSiswaFragment();
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
        return inflater.inflate(R.layout.fragment_absensi_siswa, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        cbKelas= view.findViewById(R.id.cbKelas);
        cbPelajaran= view.findViewById(R.id.cbPelajaran);
        items=view.findViewById(R.id.items);
        txtTanggal= (TextView) view.findViewById(R.id.txtTanggal);
        btnSimpan=(TextView) view.findViewById(R.id.btnSimpan);

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBAndroid db=new DBAndroid();
                for (int i=0; i<=items.getChildCount()-1; i++) {
                    if (items.getChildAt(i) instanceof ViewAbsensi) {
                        ViewAbsensi v=(ViewAbsensi) items.getChildAt(i);
                        String id=v.id;
                        String nis = v.txtNIS.getText().toString();
                        boolean masuk = v.opMasuk.isChecked();
                        boolean sakit = v.opSakit.isChecked();
                        boolean izin=v.opIzin.isChecked();
                        boolean alpha=v.opAlpha.isChecked();
                        String keterangan="";
                        if (masuk)
                            keterangan="M";
                        else if (sakit)
                            keterangan="S";
                        else if (izin)
                            keterangan="I";
                        else
                            keterangan="A";
                        db.execute("Delete from t_absensi where id_siswa="+id+" and id_kelas="+id_kelas+" and id_pelajaran="+id_pelajaran+" and Format(Tanggal,'yyyy-MM-dd')='"+tanggal+"'");
                        db.execute("Insert into t_absensi (tahun_pelajaran,semester, id_kelas,id_guru,id_pelajaran,id_siswa,tanggal,kehadiran) values " +
                                "('"+GlobalVar.TahunPelajaran+"','"+GlobalVar.Semester+"',"+ id_kelas+","+id_guru+","+id_pelajaran+","+id+",'"+tanggal+"','"+keterangan+"')");
                    }
                }
                GlobalFunction.ShowDialog(getActivity(),"Simpan sukses");
            }
        });
        Calendar now = Calendar.getInstance();
        if (dpd == null) {
            dpd = DatePickerDialog.newInstance(
                    AbsensiSiswaFragment.this,
                    now.get(Calendar.YEAR),
                    now.get(Calendar.MONTH),
                    now.get(Calendar.DAY_OF_MONTH)
            );
        } else {
            dpd.initialize(
                    AbsensiSiswaFragment.this,
                    now.get(Calendar.YEAR),
                    now.get(Calendar.MONTH),
                    now.get(Calendar.DAY_OF_MONTH)
            );
        }

        String tgl=now.get(Calendar.YEAR)+"-"+String.format("%02d",  now.get(Calendar.MONTH))+"-"+String.format("%02d",now.get(Calendar.DAY_OF_MONTH));
        txtTanggal.setText(GlobalFunction.FormatTanggal(tgl+" 00:00:00"));
        txtTanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dpd.show(requireFragmentManager(), "Datepickerdialog");
            }
        });

        Database db=new Database();
        db.isiSpinner(getContext(),cbKelas,"vw_jadwal","nama_kelas","id_sekolah="+GlobalVar.idSekolah+" and tahun_pelajaran='"+GlobalVar.TahunPelajaran+"' and Semester='"+GlobalVar.Semester+"'");
        if (cbKelas.getAdapter().getCount()>0) {
            cbKelas.setSelection(0);
        }

        cbKelas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Database db=new Database();
                db.isiSpinner(getContext(),cbPelajaran,"vw_jadwal","nama_pelajaran","nama_kelas='"+cbKelas.getSelectedItem()+"' and Tahun_pelajaran='"+GlobalVar.TahunPelajaran+"' and Semester='"+GlobalVar.Semester+"'");
                if (cbPelajaran.getAdapter().getCount()>0) {
                    cbPelajaran.setSelection(0);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        cbPelajaran.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Refresh();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void Refresh(){
        items.removeAllViews();
        ViewAbsensiHeader v2=new ViewAbsensiHeader(getContext());
        items.addView(v2);

        dbDataAbsen.getRecords("select id_siswa,id_kelas,NIS,nama_siswa,id_pelajaran,nama_pelajaran,id_guru,nama_guru,Format(tanggal,'yyyy-MM-dd') as tanggal,kehadiran,keterangan from vw_absensi where id_sekolah="+GlobalVar.idSekolah+" and nama_pelajaran='"+cbPelajaran.getSelectedItem()+"' and nama_kelas='"+cbKelas.getSelectedItem()+"' and Format(Tanggal,'yyyy-MM-dd')='"+txtTanggal.getTag()+"'");
        if (dbDataAbsen.Records.size()>0) {
            id_kelas= dbDataAbsen.Records.get(0).get("id_kelas");
            id_guru= dbDataAbsen.Records.get(0).get("id_guru");
            id_pelajaran= dbDataAbsen.Records.get(0).get("id_pelajaran");
            tanggal= dbDataAbsen.Records.get(0).get("tanggal");

        }
        DBAndroid db=new DBAndroid();
        db.getRecords("select * from vw_siswa where nama_kelas='"+cbKelas.getSelectedItem()+"' and id_sekolah="+GlobalVar.idSekolah+" order by nama_siswa");
        for (int a=0; a<=db.Records.size()-1; a++) {
            ViewAbsensi v=new ViewAbsensi(getContext());
            String id=db.Records.get(a).get("id");
            String nis=db.Records.get(a).get("NIS");
            String nama=db.Records.get(a).get("nama_siswa");
            String kehadiran=cariKehadiran(nis);
            v.setData(id,nis,nama,kehadiran);
            items.addView(v);
        }
    }

    private String cariKehadiran(String nis) {
        String kehadiran="";
        for (int i=0; i<=dbDataAbsen.Records.size()-1;i++) {
            if (dbDataAbsen.Records.get(i).get("NIS").equals(nis)) {
                kehadiran=dbDataAbsen.Records.get(i).get("kehadiran");
                break;
            }
        }
        return kehadiran;
    }
    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        txtTanggal.setText(GlobalFunction.FormatTanggal(year+"-"+String.format("%02d",monthOfYear+1)+"-"+String.format("%02d",dayOfMonth)+" 00:00:00"));
        txtTanggal.setTag(year+"-"+String.format("%02d", monthOfYear+1)+"-"+String.format("%02d", dayOfMonth));
        Refresh();
    }
}