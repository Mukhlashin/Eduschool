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
        btnSimpan= (TextView) view.findViewById(R.id.btnManagementSimpan);
        edtNamaBarang= (EditText) view.findViewById(R.id.edt_nama_barang);
        edtHarga= (EditText) view.findViewById(R.id.edt_harga);
        edtStok= (EditText) view.findViewById(R.id.edt_stok);
        edtDeskripsi= (EditText) view.findViewById(R.id.edt_deskripsi);
        btnPilihGambar=(TextView) view.findViewById(R.id.btn_pilih_gambar);

        if (!GlobalVar.idProduk.equals("")) {
            DBAndroid db=new DBAndroid();
            db.getRecords("Select * from kop_barang where id_barang="+GlobalVar.idProduk);
            if (db.Records.size()>0) {
                edtNamaBarang.setText(db.Records.get(0).get("nama_barang"));
                String harga=db.Records.get(0).get("harga");
                if (harga!="") {
                    harga= String.format("%.0f",Double.parseDouble(harga));
                }
                edtHarga.setText(harga);
                edtDeskripsi.setText(db.Records.get(0).get("deskripsi"));
                edtStok.setText(db.Records.get(0).get("stok"));
            }
        }
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                namaBarang=edtNamaBarang.getText().toString();
                hargaBarang=edtHarga.getText().toString();
                stokBarang=edtStok.getText().toString();
                deskripsiBarang=edtDeskripsi.getText().toString();
                filename=GlobalVar.namafile;

                UploadFile up1 = new UploadFile();
                up1.activity=getActivity();
                up1.context=getContext();
                up1.TargetFileName=GlobalVar.namafile;
                up1.FILE_UPLOAD_URL=  GlobalVar.URLAPI+ "upload_produk.php";
                up1.FileName = GlobalVar.namafileAsal;
                up1.execute();

                DBAndroid db=new DBAndroid();
                if (!GlobalVar.idProduk.equals("")) {
                    String harga=edtHarga.getText().toString();
                    db.execute("update kop_barang set nama_barang='"+edtNamaBarang.getText()+"',harga="+harga+",stok="+edtStok.getText()+",deskripsi='"+edtDeskripsi.getText()+"',foto='"+GlobalVar.namafile+"' " +
                            "where id_barang="+GlobalVar.idProduk);

                } else {
                    db.execute("Insert into kop_barang (id_unit_bisnis, id_sekolah,nama_barang,harga,stok,deskripsi,foto) " +
                            "values ("+GlobalVar.idUnitBisnis+","+ GlobalVar.idSekolah+",'"+namaBarang+"',"+hargaBarang+","+stokBarang+",'"+deskripsiBarang+"','"+GlobalVar.namafile+"')");
                    GlobalFunction.ShowDialog(getActivity(),"Tambah produk sukses");
                }

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