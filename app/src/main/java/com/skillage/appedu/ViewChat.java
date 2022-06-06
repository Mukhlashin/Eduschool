package com.skillage.appedu;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;

public class ViewChat extends RelativeLayout {
    View rootView;
    FrameLayout ListObject;
    LinearLayout isi;
    TextView txtID,txtUser,txtPerihal,txtJam, btnBalas,btnSalin,btnHapus;
    RelativeLayout rbox;
    Context context;
    CircleImageView fotouser;
    String ID,NamaFile,TypeFile;
    View myself;
    ImageView img;

    private String userID;
    public ViewChat(Context context) {
        super(context);
        this.context=context;
        init(context);
    }

    private void init(final Context context) {
        rootView = inflate(context, R.layout.viewchat, this);
        rbox = (RelativeLayout) rootView.findViewById(R.id.box);
        ListObject=(FrameLayout) rootView.findViewById(R.id.ListObject);
        isi=(LinearLayout) rootView.findViewById(R.id.isi);
        txtID= (TextView) rootView.findViewById(R.id.txtID);
        txtUser=(TextView) rootView.findViewById(R.id.txtUser);
        txtJam=(TextView) rootView.findViewById(R.id.txtJam);
        txtPerihal =(TextView) rootView.findViewById(R.id.txtPerihal);
        btnBalas=(TextView) rootView.findViewById(R.id.btnBalas);
        btnSalin=(TextView) rootView.findViewById(R.id.btnSalin);
        btnHapus=(TextView) rootView.findViewById(R.id.btnHapus);
        fotouser=(CircleImageView) rootView.findViewById(R.id.fotouser);
        img=(ImageView) rootView.findViewById(R.id.btnZoom);
        img.setVisibility(INVISIBLE);
        img.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TypeFile.toUpperCase().equals("VIDEO")) {
                    Intent i=new Intent(getContext(),ShowVideoChat.class);
                    i.putExtra("filename","chat/"+NamaFile);
                    getContext().startActivity(i);
                }
                if (TypeFile.toUpperCase().equals("PDF")) {
                    Intent i=new Intent(getContext(),ShowPDFChat.class);
                    i.putExtra("filename",NamaFile);
                    getContext().startActivity(i);
                }
                if (TypeFile.toUpperCase().equals("FOTO")) {
                    Intent i=new Intent(getContext(),DialogGambar.class);
                    i.putExtra("filename","chat/"+NamaFile);
                    getContext().startActivity(i);
                }
            }
        });

        myself=this;

        btnHapus.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(getContext())
                        .setTitle("Hapus")
                        .setMessage("Ingin menghapus pesan ini?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                DBAndroid db=new DBAndroid();
                                db.execute("Delete from Chat where ID="+txtID.getText().toString());
                                ((ViewGroup) myself.getParent()).removeView(myself);

                            }})
                        .setNegativeButton(android.R.string.no, null).show();
            }
        });

    }

    public void setData(String ID,Bitmap Fotouser, String UserID, String UserFullName, String Perihal, String Tanggal, String NamaFile, String ObjectType){
        this.userID=UserID;
        if (UserID.toUpperCase().equals(GlobalVar.userID.toUpperCase())) {
            btnHapus.setVisibility(VISIBLE);
        } else btnHapus.setVisibility(GONE);
        this.ID=ID;
        txtID.setText(UserID);
        txtUser.setText(UserFullName);
        txtPerihal.setText(Perihal);
        txtJam.setText(Tanggal);

        if (Fotouser==null) {
            fotouser.setImageDrawable(getResources().getDrawable(R.drawable.noimage));
        } else {
            fotouser.setImageBitmap(Fotouser);
        }

        if (UserID.equals(GlobalVar.userID)) {
            rbox.setBackgroundResource(R.drawable.bubble_out);
        } else {
            rbox.setBackgroundResource(R.drawable.bubble_in);
        }
        this.NamaFile=NamaFile;
        this.TypeFile=ObjectType;
        img.setVisibility(INVISIBLE);
        if (!NamaFile.equals("")) {
            if (ObjectType.toUpperCase().equals("PDF")) {
                img.setVisibility(VISIBLE);
                ImageView t=new ImageView(getContext());
                t.setImageDrawable(getResources().getDrawable( R.drawable.ic_pdf_black));
                ListObject.addView(t);
            }
            if (ObjectType.toUpperCase().equals("FOTO")) {
                String url = GlobalVar.URL+ "uploadsiswa/" + NamaFile;
                Bitmap bmp = GlobalFunction.loadImageFromURL(url);
                ImageView foto = new ImageView(context);
                if (bmp!=null) {
                    img.setVisibility(VISIBLE);
                    FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(500, 300);
                    layoutParams.gravity = Gravity.CENTER_HORIZONTAL;
                    layoutParams.topMargin=5;
                    foto.setLayoutParams(layoutParams);
                    foto.setImageBitmap(bmp);
                    foto.setTag(ID);
                    foto.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });
                    ListObject.addView(foto);
                }
                if (ListObject.getChildCount()!=0) ListObject.setVisibility(VISIBLE); else ListObject.setVisibility(GONE);
            }
            if (ObjectType.toUpperCase().equals("VIDEO")) {
                img.setVisibility(VISIBLE);
                String url = GlobalVar.URL+ "chat/" + NamaFile;
                VideoView video = new VideoView(context);
                FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(700, 300);
                layoutParams.gravity = Gravity.CENTER_HORIZONTAL;
                layoutParams.topMargin=10;
                layoutParams.leftMargin=20;

                video.setLayoutParams(layoutParams);

                Uri uri = Uri.parse(url);
                video.setVideoURI(uri);
                video.seekTo(1);
                video.setTag(ID);
                video.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (video.isPlaying()) video.pause(); else  video.start();
                    }
                });

                ListObject.addView(video);
                if (ListObject.getChildCount()!=0) ListObject.setVisibility(VISIBLE); else ListObject.setVisibility(GONE);
            }
        }
    }

    public void setData(String ID,String Fotouser, String UserID, String UserFullName, String Perihal, String Tanggal, String NamaFile, String ObjectType){
        this.userID=UserID;
        if (UserID.toUpperCase().equals(GlobalVar.userID.toUpperCase())) {
            btnHapus.setVisibility(VISIBLE);
        } else btnHapus.setVisibility(GONE);
        this.ID=ID;
        txtID.setText(UserID);
        txtUser.setText(UserFullName);
        txtPerihal.setText(Perihal);
        txtJam.setText(Tanggal);

        if (Fotouser.equals("null") || Fotouser.equals("") || Fotouser.equals("jpg") || Fotouser.equals(".jpg")) {
            fotouser.setImageDrawable(getResources().getDrawable(R.drawable.noimage));
        } else {
            Glide
                    .with(getContext())
                    .load(GlobalVar.URL+"foto/"+Fotouser)
                    .centerCrop()
                    .into(fotouser);
        }

        if (UserID.equals(GlobalVar.userID)) {
            rbox.setBackgroundResource(R.drawable.bubble_out);
        } else {
            rbox.setBackgroundResource(R.drawable.bubble_in);
        }
        this.NamaFile=NamaFile;
        this.TypeFile=ObjectType;
        img.setVisibility(INVISIBLE);
        if (!NamaFile.equals("")) {
            if (ObjectType.toUpperCase().equals("PDF")) {
                img.setVisibility(VISIBLE);
                ImageView t=new ImageView(getContext());
                t.setImageDrawable(getResources().getDrawable( R.drawable.ic_pdf_black));
                ListObject.addView(t);
            }
            if (ObjectType.toUpperCase().equals("FOTO")) {
                String url = GlobalVar.URL+ "chat/" + NamaFile;
                Bitmap bmp = GlobalFunction.loadImageFromURL(url);
                ImageView foto = new ImageView(context);
                if (bmp!=null) {
                    img.setVisibility(VISIBLE);
                    FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(500, 300);
                    layoutParams.gravity = Gravity.CENTER_HORIZONTAL;
                    layoutParams.topMargin=5;
                    foto.setLayoutParams(layoutParams);
                    foto.setImageBitmap(bmp);
                    foto.setTag(ID);
                    foto.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });
                    ListObject.addView(foto);
                }
                if (ListObject.getChildCount()!=0) ListObject.setVisibility(VISIBLE); else ListObject.setVisibility(GONE);
            }
            if (ObjectType.toUpperCase().equals("VIDEO")) {
                img.setVisibility(VISIBLE);
                String url = GlobalVar.URL+ "chat/" + NamaFile;
                VideoView video = new VideoView(context);
                FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(700, 300);
                layoutParams.gravity = Gravity.CENTER_HORIZONTAL;
                layoutParams.topMargin=10;
                layoutParams.leftMargin=20;

                video.setLayoutParams(layoutParams);

                Uri uri = Uri.parse(url);
                video.setVideoURI(uri);
                video.seekTo(1);
                video.setTag(ID);
                video.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       if (video.isPlaying()) video.pause(); else  video.start();
                    }
                });

                ListObject.addView(video);
                if (ListObject.getChildCount()!=0) ListObject.setVisibility(VISIBLE); else ListObject.setVisibility(GONE);
            }
        }
    }
}
