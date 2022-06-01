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

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.rustamg.filedialogs.FileDialog;
import com.rustamg.filedialogs.OpenFileDialog;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

public class UploadTugasFragment extends Fragment implements DatePickerDialog.OnDateSetListener {
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

    public UploadTugasFragment() {

    }

    public static UploadTugasFragment newInstance(String param1, String param2) {
        UploadTugasFragment fragment = new UploadTugasFragment();
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
        return inflater.inflate(R.layout.fragment_uplaod_materi, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        txtJudul=view.findViewById(R.id.txtJudul);
        txtJudul.setText("Upload Tugas/PR");
        cbKelas= view.findViewById(R.id.cbKelas);
        cbPelajaran= view.findViewById(R.id.cbPelajaran);
        items=view.findViewById(R.id.items);
        txtTanggal= (TextView) view.findViewById(R.id.txtTanggal);
        lblFileName=(TextView) view.findViewById(R.id.lblFileName);
        btnSimpan=(TextView) view.findViewById(R.id.btnSimpan);
        txtKeterangan=(EditText) view.findViewById(R.id.txtKeterangan);
        btnPilihFile=(TextView) view.findViewById(R.id.btnPilihFile);

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                keterangan=txtKeterangan.getText().toString();
                filename=GlobalVar.namafile;
                DBAndroid db=new DBAndroid();
                db.execute("Insert into t_materi (id_pelajaran,id_guru,tahun_pelajaran,nama_kelas,jenis_materi,keterangan,nama_file) " +
                        "values ("+id_pelajaran+","+GlobalVar.idGuru+",'"+tahun_pelajaran+"','"+cbKelas.getSelectedItem()+"','"+jenis_materi+"','"+keterangan+"','"+ filename+"')");
                Refresh();
                txtKeterangan.setText("");
                lblFileName.setText("");
                GlobalFunction.ShowDialog(getActivity(),"Simpan sukses");
            }
        });

        Database db=new Database();
        db.isiSpinner(getContext(),cbKelas,"vw_jadwal","nama_kelas","id_guru="+GlobalVar.idGuru);
        if (cbKelas.getAdapter().getCount()>0) {
            cbKelas.setSelection(0);
        }

        cbKelas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                DBAndroid dbCariKelas=new DBAndroid();
                Database db=new Database();
                    db.isiSpinner(getContext(),cbPelajaran,"vw_jadwal","nama_pelajaran","nama_kelas='"+cbKelas.getSelectedItem()+"' and id_guru="+GlobalVar.idGuru);
                    if (cbPelajaran.getAdapter().getCount()>0) {
                        cbPelajaran.setSelection(0);
                    }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        Calendar now = Calendar.getInstance();
        if (dpd == null) {
            dpd = DatePickerDialog.newInstance(
                    UploadTugasFragment.this,
                    now.get(Calendar.YEAR),
                    now.get(Calendar.MONTH),
                    now.get(Calendar.DAY_OF_MONTH)
            );
        } else {
            dpd.initialize(
                    UploadTugasFragment.this,
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

        btnPilihFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GlobalVar.namapelajaran=cbPelajaran.getSelectedItem().toString();
                GlobalVar.jenisUpload="Upload Materi";

                FileDialog dialog = new OpenFileDialog();
                dialog.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.Theme_AppEdu);
                dialog.show(getActivity().getSupportFragmentManager(), OpenFileDialog.class.getName());
            }
        });


        cbPelajaran.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                DBAndroid dbCariKelas=new DBAndroid();
                dbCariKelas.getRecords("select * from vw_jadwal where nama_pelajaran='"+cbPelajaran.getSelectedItem()+"' and nama_kelas='"+cbKelas.getSelectedItem()+"' and id_guru="+GlobalVar.idGuru);
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

    public void updateFileName(String filename){
        lblFileName.setText(filename);
    }
    private void Refresh(){
        items.removeAllViews();
        ViewMateriRecordHeader v2=new ViewMateriRecordHeader(getContext());
        items.addView(v2);
        DBAndroid db=new DBAndroid();
        db.getRecords("select format(tgl_upload,'dd-MMM-yyyy HH:mm') as tgl_upload,nama_pelajaran,keterangan,nama_file from vw_materi where jenis_materi='" + jenis_materi+"' and nama_pelajaran='"+cbPelajaran.getSelectedItem()+"' and id_guru="+GlobalVar.idGuru+" and nama_kelas='"+cbKelas.getSelectedItem()+"' and id_sekolah="+GlobalVar.idSekolah+" order by tgl_upload");
        for (int a=0; a<=db.Records.size()-1; a++) {
            ViewMateriRecord v=new ViewMateriRecord(getContext());
            v.setData(db.Records.get(a).get("tgl_upload"),db.Records.get(a).get("nama_pelajaran"),db.Records.get(a).get("keterangan"),db.Records.get(a).get("nama_file"));
            items.addView(v);
        }
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        txtTanggal.setText(GlobalFunction.FormatTanggal(year+"-"+String.format("%02d",monthOfYear+1)+"-"+String.format("%02d",dayOfMonth)+" 00:00:00"));
        txtTanggal.setTag(year+"-"+String.format("%02d", monthOfYear+1)+"-"+String.format("%02d", dayOfMonth));
        Refresh();
    }

}