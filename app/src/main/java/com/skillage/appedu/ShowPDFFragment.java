package com.skillage.appedu;

import static android.content.Context.DOWNLOAD_SERVICE;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.github.barteksc.pdfviewer.PDFView;
import com.google.android.material.snackbar.Snackbar;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class ShowPDFFragment extends Fragment {

    PDFView pdfView;
    ProgressDialog progressDialog;
    ImageView btnClose;
    ImageView btnDownload;
    TextView txtJudul;
    String pdfurl;
    String file ;

    private long lastDownload=-1L;
    private DownloadManager mgr=null;


    private static final String ARG_PARAM1 = "nama_file";
    private static final String ARG_PARAM2 = "judul";

    private String mParam1;
    private String mParam2;

    TextView txtTest;
    public ShowPDFFragment() {

    }

    public static ShowPDFFragment newInstance(String param1,String param2) {
        ShowPDFFragment fragment = new ShowPDFFragment();
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
            file=mParam1;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_show_pdf, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mgr=(DownloadManager) getContext().getSystemService(DOWNLOAD_SERVICE);
        progressDialog = new ProgressDialog(getContext(),
                R.style.AppTheme_Dark_Dialog);
        btnDownload=(ImageView)  view.findViewById(R.id.btnDownload);
        btnClose=(ImageView) view.findViewById(R.id.btnClose);
        txtJudul=(TextView) view.findViewById(R.id.txtJudul);
        pdfView = (PDFView) view.findViewById(R.id.idPDFView);
        txtJudul.setText(mParam2);
        file = mParam1;
        file = URLEncoder.encode(file).replace("+", "%20");
        pdfurl = GlobalVar.URL+ "files/"+ file;
        new RetrivePDFfromUrl().execute(pdfurl);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment f = MateriDownloadSiswaFragment.newInstance("","");
                ((AppCompatActivity) getActivity()).getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(
                                R.anim.enter_from_right,  // enter
                                R.anim.exit_to_left,  // exit
                                R.anim.enter_from_left,   // popEnter
                                R.anim.exit_to_right // popExit
                        )
                        .replace(R.id.mainframe, f)
                        .addToBackStack(null)
                        .commit();
            }
        });

        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri=Uri.parse(pdfurl);
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                        .mkdirs();

                lastDownload=
                        mgr.enqueue(new DownloadManager.Request(uri)
                                .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI |
                                        DownloadManager.Request.NETWORK_MOBILE)
                                .setAllowedOverRoaming(false)
                                .setTitle("Download Video")
                                .setDescription("Download file "+file)
                                .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,
                                        file));
                Snackbar.make(v,"Mendownload pdf...", Snackbar.LENGTH_SHORT).show();

            }
        });
    }


    private class RetrivePDFfromUrl extends AsyncTask<String, Void, InputStream> {
        String aString;
        InputStream aInputStream;

        @Override
        protected void onPreExecute() {
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
            super.onPreExecute();
        }

        @Override
        protected InputStream doInBackground(String... urls) {
            try {
                URL aURL = new URL(
                        urls[0]);

                final HttpURLConnection aHttpURLConnection = (HttpURLConnection) aURL.openConnection();
                aInputStream = aHttpURLConnection.getInputStream();
                if (aHttpURLConnection.getResponseCode() == 200) {
                    BufferedInputStream aBufferedInputStream = new BufferedInputStream(
                            aInputStream);
                }
            }

            catch (IOException e) {
                String test="";
            }
            return aInputStream;
        }

        @Override
        protected void onPostExecute(InputStream inputStream) {
            progressDialog.dismiss();
            pdfView.fromStream(inputStream).load();
            pdfView.fitToWidth(0);
            pdfView.setMinZoom(60);
            pdfView.setMidZoom(80);
            pdfView.setMaxZoom(100);
            pdfView.fitToWidth();
            pdfView.enableDoubletap(false);
        }

    }


}