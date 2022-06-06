package com.skillage.appedu;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import butterknife.BindView;


public class ChatSiswaFragment extends Fragment {


    private static final String ARG_PARAM1 = "kelas";
    private static final String ARG_PARAM2 = "idguru";

    private String mParam1;
    private String mParam2;

    String ID="";
    LinearLayout itemJudul;
    public String type="Agribisnis";

    LinearLayout itemLaporan;
    EditText txtEntri;
    TextView btnKirim;
    ImageView btnAttach;

    public ChatSiswaFragment() {

    }

    public static ChatSiswaFragment newInstance(String param1, String param2) {
        ChatSiswaFragment fragment = new ChatSiswaFragment();
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
        return inflater.inflate(R.layout.fragment_chat_siswa, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        itemLaporan = view.findViewById(R.id.itemLaporan);
        itemJudul=view.findViewById(R.id.itemJudul);
        txtEntri= view.findViewById(R.id.txtEntri);
        btnKirim=view.findViewById(R.id.btnKirim);
        btnAttach=view.findViewById(R.id.btnAttach);

        btnKirim.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (txtEntri.getText().toString().trim().equals("")) {
                        GlobalFunction.ShowDialog(getActivity(),"Teks pesan tidak boleh kosong");
                        return;
                    }
                    btnKirim.setEnabled(false);
                    DBAndroid db=new DBAndroid();
                    String tgl = GlobalFunction.getTanggal();
                    db.execute("Insert into chat (id_sekolah,kelas,userid,content,tanggal) " +
                            "values ("+GlobalVar.idSekolah+",'"+GlobalVar.namaKelas+"','"+GlobalVar.userID+"','"+txtEntri.getText()+"','"+tgl+"')");
                    ShowLast();
                    txtEntri.setText("");
                    txtEntri.requestFocus();
                    btnKirim.setEnabled(true);
                }
            });

        RefreshData();
    }


    private  void RefreshData(){
        itemJudul.removeAllViews();
        DBAndroid db=new DBAndroid();
        db.getRecords("Select id,tanggal, role,userid,username,content,filename,filetype,filefoto from ViewChat where id_sekolah="+GlobalVar.idSekolah+" and kelas='"+GlobalVar.namaKelas+"' order by Tanggal ASC");
        for (int i=0; i<db.Records.size(); i++){
            ViewChat v=new ViewChat(getContext());
            String ID=db.Records.get(i).get("id");
            String Tanggal=db.Records.get(i).get("tanggal");
            String UserID=db.Records.get(i).get("userid");
            String FullName=db.Records.get(i).get("username");
            String Pesan=db.Records.get(i).get("content");
            String NamaFile=db.Records.get(i).get("filename");
            String TipeFile=db.Records.get(i).get("filetype");
            String IDFoto=db.Records.get(i).get("filefoto");
            String Role=db.Records.get(i).get("role");
            if (Role.equals("R.10")) {
                // siswa
                DBAndroid db2=new DBAndroid();
                db2.getRecords("Select id from t_siswa where userid='"+UserID+"'");
                if (db2.Records.size()>0) {
                    IDFoto= db2.Records.get(0).get("id")+".jpg";
                }
            } else {
                DBAndroid db2=new DBAndroid();
                db2.getRecords("Select id from t_guru where userid='"+UserID+"'");
                if (db2.Records.size()>0) {
                    IDFoto= db2.Records.get(0).get("id")+".jpg";
                }
            }
            v.setData(ID,IDFoto,UserID,FullName,Pesan,Tanggal,NamaFile,TipeFile);

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(500, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.weight = 1.0f;
            if (UserID.equals(GlobalVar.userID))
                layoutParams.gravity = Gravity.RIGHT;
            else
                layoutParams.gravity = Gravity.LEFT;

            layoutParams.topMargin=5;
            v.setLayoutParams(layoutParams);

            itemLaporan.addView(v);
        }
     
    }
    private void ShowLast(){
        DBAndroid db=new DBAndroid();
        ViewChat v2=new ViewChat(getContext());
        db.getRecords("Select id,tanggal, userid,username,content,filename,filetype,filefoto from ViewChat where userid='"+GlobalVar.userID+"' order by ID DESC");
        String ID=db.Records.get(0).get("id");
        String Tanggal=db.Records.get(0).get("tanggal");
        String UserID=db.Records.get(0).get("userid");
        String FullName=db.Records.get(0).get("name");
        String Pesan=db.Records.get(0).get("content");
        String NamaFile=db.Records.get(0).get("filename");
        String TipeFile=db.Records.get(0).get("filetype");
        String IDFoto=db.Records.get(0).get("filefoto");
        v2.setData(ID,IDFoto,UserID,FullName,Pesan,Tanggal,NamaFile,TipeFile);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(700, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.weight = 1.0f;
        if (UserID.equals(GlobalVar.userID))
            layoutParams.gravity = Gravity.RIGHT;
        else
            layoutParams.gravity = Gravity.LEFT;

        layoutParams.topMargin=5;
        v2.setLayoutParams(layoutParams);
        itemLaporan.addView(v2);
    }


}