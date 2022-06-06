package com.skillage.appedu;

import static android.content.Context.DOWNLOAD_SERVICE;

import android.app.DownloadManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.makeramen.roundedimageview.RoundedImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;


public class HomeSiswaFragment extends Fragment {

    private long lastDownload=-1L;
    private DownloadManager mgr=null;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    TextView txtNIS,txtNISN,txtNama,txtJenisKelamin,txtTempatLahir,txtTglLahir,txtAlamat,txtAgama;
    EditText txtPassword,txtPasswordUlang;
    TextView petunjukkartu,btnAmbilFoto,lblNIS,lblNISN,llblNama,lblTempatLahir,lblJenisKelamin,lblAgama,lblAlamat;
    public HomeSiswaFragment() {

    }

    public static HomeSiswaFragment newInstance(String param1, String param2) {
        HomeSiswaFragment fragment = new HomeSiswaFragment();
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
        return inflater.inflate(R.layout.fragment_home_siswa, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        lblNIS = view.findViewById(R.id.lblNIS);
        lblNISN=view.findViewById(R.id.lblNISN);
        llblNama=view.findViewById(R.id.lblNama);
        lblJenisKelamin=view.findViewById(R.id.lblJenisKelamin);
        lblTempatLahir=view.findViewById(R.id.lblTglLahir);
        lblAgama=view.findViewById(R.id.lblAgama);
        lblAlamat=view.findViewById(R.id.lblAlamat);
        petunjukkartu=view.findViewById(R.id.petunjukkartu);

        txtNIS = view.findViewById(R.id.txtNIS);
        txtNISN=view.findViewById(R.id.txtNISN);
        txtNama=view.findViewById(R.id.txtNama);
        txtTempatLahir=view.findViewById(R.id.txtTempatLahir);
        txtTglLahir=view.findViewById(R.id.txtTglLahir);
        txtJenisKelamin=view.findViewById(R.id.txtJenisKelamin);
        txtAgama=view.findViewById(R.id.txtAgama);
        txtAlamat=view.findViewById(R.id.txtAlamat);
        txtPassword=view.findViewById(R.id.txtPassword);
        txtPasswordUlang=view.findViewById(R.id.txtPasswordUlang);
        btnAmbilFoto=view.findViewById(R.id.btnAmbilFoto);
        RoundedImageView imgFoto=view.findViewById(R.id.imgFoto);
        imgFoto.setImageBitmap(GlobalVar.fotoUser);

        btnAmbilFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        mgr=(DownloadManager) getContext().getSystemService(DOWNLOAD_SERVICE);

        Button btnSimpanFoto= view.findViewById(R.id.btnSimpanFoto);
        btnSimpanFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txtPassword.getText().toString().equals(txtPasswordUlang.getText().toString())) {
                    DBAndroid db=new DBAndroid();
                    db.execute("Update t_siswa set password='"+DBAndroid.EnkripMD5(txtPassword.getText().toString())+"' " +
                            "where id="+GlobalVar.idSiswa);
                    GlobalFunction.ShowDialog(getActivity(),"Simpan sukses");
                } else {
                    GlobalFunction.ShowDialog(getActivity(),"Isian Password Login dan Ketik Ulang Password harus sama");
                }

            }
        });

        Button btnKartuPelajar = view.findViewById(R.id.btnKartuPelajar);
        Button btnProfil = view.findViewById(R.id.btnProfil);
        RelativeLayout Kartu = view.findViewById(R.id.Kartu);
        RelativeLayout Profil=view.findViewById(R.id.Profil);
        RelativeLayout isiKartu=view.findViewById(R.id.isiKartu);
        Drawable dr = new BitmapDrawable(GlobalVar.fotoKartu);
        isiKartu.setBackground(dr);
        Profil.setVisibility(View.GONE); Kartu.setVisibility(View.GONE);
        petunjukkartu.setVisibility(View.GONE);
        Kartu.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Bitmap bmp = getBitmapFromView(Kartu);
                String filename="KartuPelajar-"+GlobalVar.NIS+".png";
                String path = Environment.getExternalStorageDirectory().toString();
                OutputStream fOut = null;
                Integer counter = 0;
                File file = new File(path, filename);
                try {
                    fOut = new FileOutputStream(file);
                    bmp.compress(Bitmap.CompressFormat.JPEG, 85, fOut); // saving the Bitmap to a file compressed as a JPEG with 85% compression rate
                    fOut.close(); // do not forget to close the stream
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    MediaStore.Images.Media.insertImage(getActivity().getContentResolver(),file.getAbsolutePath(),file.getName(),file.getName());
                    GlobalFunction.ShowDialog(getActivity(),"Kartu pelajar berhasil disimpan ke folder galery foto kamu dengan nama file "+filename+". Silakan cek galery.");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                return false;
            }
        });
        btnKartuPelajar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Kartu.setVisibility(View.VISIBLE);
                petunjukkartu.setVisibility(View.VISIBLE);
                Profil.setVisibility(View.GONE);
            }
        });
        btnProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Kartu.setVisibility(View.GONE);
                petunjukkartu.setVisibility(View.GONE);
                Profil.setVisibility(View.VISIBLE);
            }
        });

        MultiFormatWriter multi = new MultiFormatWriter();
        ImageView qrcode = (ImageView) view.findViewById(R.id.qrcode);
        ImageView qrprofil = (ImageView) view.findViewById(R.id.qrprofil);
        BitMatrix bitMatrix = null;
        try {
            bitMatrix = multi.encode(GlobalVar.NIS, BarcodeFormat.QR_CODE, 300, 300);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            qrcode.setImageBitmap(bitmap);
            qrprofil.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }

        DBAndroid db=new DBAndroid();
        db.getRecords("Select * from vw_siswa where id="+GlobalVar.idSiswa);
        if (db.Records.size()>0) {
            txtNIS.setText(db.Records.get(0).get("NIS"));
            txtNISN.setText(db.Records.get(0).get("NISN"));
            txtNama.setText(db.Records.get(0).get("nama_siswa"));
            txtJenisKelamin.setText(db.Records.get(0).get("jenis_kelamin"));
            txtTempatLahir.setText(db.Records.get(0).get("tempat_lahir"));
            txtTglLahir.setText(db.Records.get(0).get("tgl_lahir"));
            txtAlamat.setText(db.Records.get(0).get("alamat"));
            txtAgama.setText(db.Records.get(0).get("agama"));
            String nama_kelas= db.Records.get(0).get("nama_kelas");

            lblNIS.setText(db.Records.get(0).get("NIS"));
            lblNISN.setText(db.Records.get(0).get("NISN"));
            llblNama.setText(db.Records.get(0).get("nama_siswa"));
            lblJenisKelamin.setText(db.Records.get(0).get("jenis_kelamin"));
            lblTempatLahir.setText(db.Records.get(0).get("tempat_lahir")+","+db.Records.get(0).get("tgl_lahir"));
            lblAlamat.setText(db.Records.get(0).get("alamat"));
            lblAgama.setText(db.Records.get(0).get("agama"));

        }
    }

    public static Bitmap getBitmapFromView(View view) {
        //Define a bitmap with the same size as the view
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),Bitmap.Config.ARGB_8888);
        //Bind a canvas to it
        Canvas canvas = new Canvas(returnedBitmap);
        //Get the view's background
        Drawable bgDrawable =view.getBackground();
        if (bgDrawable!=null)
            //has background drawable, then draw it on the canvas
            bgDrawable.draw(canvas);
        else
            //does not have background drawable, then draw white background on the canvas
            canvas.drawColor(Color.WHITE);
        // draw the view on the canvas
        view.draw(canvas);
        //return the bitmap
        return returnedBitmap;
    }

}