package com.skillage.appedu;

import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.rustamg.filedialogs.FileDialog;
import com.rustamg.filedialogs.OpenFileDialog;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

public class DownloadTugasSiswaFragment extends Fragment  {
    private DatePickerDialog dpd;
    Spinner cbKelas,cbPelajaran;
    LinearLayout items;
    TextView txtTanggal,btnSimpan,btnPilihFile,txtKeterangan;
    TextView lblFileName,txtJudul;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    String filenamepath="";
    String filename="";
    String tahun_pelajaran, id_pelajaran="",kelas="",jenis_materi="tugas",keterangan="";
    private String mParam1;
    private String mParam2;

    public DownloadTugasSiswaFragment() {

    }

    public static DownloadTugasSiswaFragment newInstance(String param1, String param2) {
        DownloadTugasSiswaFragment fragment = new DownloadTugasSiswaFragment();
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
        return inflater.inflate(R.layout.fragment_download_tugas_siswa, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        cbKelas= view.findViewById(R.id.cbKelas);
        cbPelajaran= view.findViewById(R.id.cbPelajaran);
        items=view.findViewById(R.id.items);

        Database db=new Database();
        db.isiSpinner(getContext(),cbKelas,"vw_jadwal","nama_kelas","id_guru="+GlobalVar.idGuru+" and tahun_pelajaran='"+GlobalVar.TahunPelajaran+"' and semester='"+GlobalVar.Semester+"'");
        if (cbKelas.getAdapter().getCount()>0) {
            cbKelas.setSelection(0);
        }

        cbKelas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                DBAndroid dbCariKelas=new DBAndroid();
                Database db=new Database();
                    db.isiSpinner(getContext(),cbPelajaran,"vw_jadwal","nama_pelajaran","tahun_pelajaran='"+GlobalVar.TahunPelajaran+"' and Semester='"+GlobalVar.Semester+"' and nama_kelas='"+cbKelas.getSelectedItem()+"' and id_guru="+GlobalVar.idGuru);
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
                DBAndroid dbCariKelas=new DBAndroid();
                dbCariKelas.getRecords("select * from vw_jadwal where nama_pelajaran='"+cbPelajaran.getSelectedItem()+"' and nama_kelas='"+cbKelas.getSelectedItem()+"' and id_guru="+GlobalVar.idGuru+" and tahun_pelajaran='"+GlobalVar.TahunPelajaran+"' and Semester='"+GlobalVar.Semester+"'");
                if (dbCariKelas.Records.size()>0) {
                    kelas = dbCariKelas.Records.get(0).get("kelas");
                    tahun_pelajaran = dbCariKelas.Records.get(0).get("tahun_pelajaran");
                }
                DBAndroid db=new DBAndroid();
                db.getRecords("Select * from t_pelajaran where nama_pelajaran='"+cbPelajaran.getSelectedItem()+"' and id_sekolah="+GlobalVar.idSekolah+" and kelas="+kelas);
                if (db.Records.size()>0) {
                    id_pelajaran=db.Records.get(0).get("id");
                }
                Refresh();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Refresh();
    }

    private void Refresh(){
        items.removeAllViews();
        //ViewMateriRecordHeader v2=new ViewMateriRecordHeader(getContext());
        //items.addView(v2);
        DBAndroid db=new DBAndroid();
        db.getRecords("select * from vw_tugas_siswa where where isnull(nama_siswa,'')<>'' and id_pelajaran="+id_pelajaran+" and nama_kelas='"+ cbKelas.getSelectedItem()+"' and tahun_pelajaran='"+GlobalVar.TahunPelajaran+"' and Semester='"+GlobalVar.Semester+"'");
        if (db.Records.size()>0) {
            for (int i = 0; i <= db.Records.size() - 1; i++) {
                ViewTugas v = new ViewTugas(getContext());
                v.activity = (AppCompatActivity) getActivity();
                String id = db.Records.get(i).get("id");
                String kode = db.Records.get(i).get("kode");
                String nama_pelajaran = db.Records.get(i).get("nama_pelajaran");
                String nama_kelas = db.Records.get(i).get("nama_kelas");
                String id_guru = db.Records.get(i).get("id_guru");
                String nama_siswa = db.Records.get(i).get("nama_siswa");
                String keterangan = db.Records.get(i).get("keterangan");
                String nilai = db.Records.get(i).get("nilai");
                String nama_file_upload = db.Records.get(i).get("nama_file_upload");
                v.setData(id, kode, nama_pelajaran, nama_kelas, id_guru, nama_siswa, keterangan, nilai, nama_file_upload);
                items.addView(v);
            }
        } else {
            TextView t=new TextView(getContext());
            t.setText("Belum ada yg mengupload tugas");
            items.addView(t);
        }
    }

}