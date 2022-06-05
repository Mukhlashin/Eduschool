package com.skillage.appedu;

import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.rustamg.filedialogs.FileDialog;
import com.rustamg.filedialogs.OpenFileDialog;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.SimpleDateFormat;

public class ManagementFragment extends Fragment {

    DBAndroid db=new DBAndroid();
    TextView lblFileName, btnSimpan, txtTanggal, btnPilihGambar;
    EditText edtNamaBarang, edtHarga, edtKodeSupplier, edtStok, edtDeskripsi;
    private SimpleDateFormat dateFormat;
    private Calendar calendar;
    private String date;
    String filename="";
    String namaBarang, hargaBarang="", kodeSupplier="", stokBarang="", deskripsiBarang="", fotoBarang="";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_management, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        lblFileName=(TextView) view.findViewById(R.id.lbl_file_name);
        txtTanggal= (TextView) view.findViewById(R.id.txtManagementTanggal);
        btnSimpan= (TextView) view.findViewById(R.id.btnManagementSimpan);
        edtNamaBarang= (EditText) view.findViewById(R.id.edt_nama_barang);
        edtHarga= (EditText) view.findViewById(R.id.edt_harga);
        edtKodeSupplier= (EditText) view.findViewById(R.id.edt_kode_supplier);
        edtStok= (EditText) view.findViewById(R.id.edt_stok);
        edtDeskripsi= (EditText) view.findViewById(R.id.edt_deskripsi);
        btnPilihGambar=(TextView) view.findViewById(R.id.btn_pilih_gambar);


        calendar = Calendar.getInstance();

        dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        date = dateFormat.format(calendar.getTime());
        txtTanggal.setText(date);

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                namaBarang=edtNamaBarang.getText().toString();
                hargaBarang=edtHarga.getText().toString();
                kodeSupplier=edtKodeSupplier.getText().toString();
                stokBarang=edtStok.getText().toString();
                deskripsiBarang=edtDeskripsi.getText().toString();
                fotoBarang="ini seharusnya adalah fotobarang";
                filename=GlobalVar.namafile;
//                filename=GlobalVar.namafile;
                DBAndroid db=new DBAndroid();
                db.execute("Insert into kop_barang (id_sekolah,nama_barang,harga,stok,foto,deskripsi) " +
                        "values ("+GlobalVar.idSekolah+",'"+namaBarang+"',"+hargaBarang+","+stokBarang+",'"+filename+"','"+deskripsiBarang+"')");
//                lblFileName.setText("");
                GlobalFunction.ShowDialog(getActivity(),"Simpan sukses");
            }
        });

        btnPilihGambar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GlobalVar.jenisUpload="Upload Gambar";
                FileDialog dialog = new OpenFileDialog();
                dialog.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.Theme_AppEdu);
                dialog.show(getActivity().getSupportFragmentManager(), OpenFileDialog.class.getName());
            }
        });
    }

    public void updateFileName(String fileName){
        lblFileName.setText(fileName);
    }
}