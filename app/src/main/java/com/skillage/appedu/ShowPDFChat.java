package com.skillage.appedu;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class ShowPDFChat extends AppCompatActivity {
    PDFView pdfView;
    ProgressDialog progressDialog;
    ImageView btnDownload;
    Button btnTutup;
    String pdfurl, file;

    private long lastDownload=-1L;
    private DownloadManager mgr=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_pdf_chat);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitle("Wikitani");
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

        }
        progressDialog = new ProgressDialog(this,
                R.style.AppTheme_Dark_Dialog);

        btnTutup=(Button) findViewById(R.id.btnTutup);
        pdfView = (PDFView) findViewById(R.id.idPDFView);
        btnDownload=(ImageView) findViewById(R.id.btnDownload);
        mgr=(DownloadManager) this.getSystemService(DOWNLOAD_SERVICE);

        Bundle b=getIntent().getExtras();
        file = b.getString("filename");
        file = URLEncoder.encode(file).replace("+", "%20");
        pdfurl = GlobalVar.URL+ "uploadsiswa/"+ file;
        new RetrivePDFfromUrl().execute(pdfurl);

        btnTutup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
                                .setTitle("Download PDF")
                                .setDescription("Download file "+file)
                                .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,
                                        file));
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