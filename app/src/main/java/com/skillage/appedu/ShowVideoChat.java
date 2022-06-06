package com.skillage.appedu;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.IOException;

public class ShowVideoChat extends AppCompatActivity {
    VideoView videoView;
    ProgressDialog progressDialog;
    Button btnTutup;
    ImageView btnDownload;
    String file ;
    String videourl ;
    private long lastDownload=-1L;
    private DownloadManager mgr=null;
    ProgressBar spinnerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_video_chat);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        mgr=(DownloadManager) this.getSystemService(DOWNLOAD_SERVICE);
        progressDialog = new ProgressDialog(this,
                R.style.AppTheme_Dark_Dialog);
        spinnerView = (ProgressBar) findViewById(R.id.my_spinner);
        btnDownload=(ImageView)  findViewById(R.id.btnDownload);
        ImageView btnClose=(ImageView) findViewById(R.id.btnClose);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnTutup=(Button) findViewById(R.id.btnTutup);
        videoView = (VideoView) findViewById(R.id.videoView);

        MediaController mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) videoView.getLayoutParams();
        params.width = metrics.widthPixels;
        params.height = metrics.heightPixels;
        params.leftMargin = 0;
        videoView.setLayoutParams(params);

        final MediaPlayer.OnInfoListener onInfoToPlayStateListener = new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mp, int what, int extra) {
                if (MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START == what) {
                    spinnerView.setVisibility(View.GONE);
                }
                if (MediaPlayer.MEDIA_INFO_BUFFERING_START == what) {
                    spinnerView.setVisibility(View.VISIBLE);
                }
                if (MediaPlayer.MEDIA_INFO_BUFFERING_END == what) {
                    spinnerView.setVisibility(View.VISIBLE);
                }
                return false;
            }
        };
        videoView.setOnInfoListener(onInfoToPlayStateListener);

        Bundle b=getIntent().getExtras();
        file = b.getString("filename");

        if (GlobalFunction.isFileExist(this,file.replace("chat",""))) {
            videourl=getFilesDir().toString() + file.replace("chat","") ;
            File f=new File(videourl);
            videourl=f.getPath();
            btnDownload.setVisibility(View.GONE);
        } else {
            videourl=GlobalVar.URL+ "uploadsiswa/" +file ;
        }
        Uri uri = Uri.parse(videourl);
        videoView.setVideoURI(uri);
        videoView.start();

        MediaPlayer mp = new MediaPlayer();
        try {
            mp.setDataSource(videourl);
            mp.prepare();
            mp.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                @Override
                public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
                    int orientation = -1;
                    if(width < height){
                        orientation = 0;
                        RelativeLayout.LayoutParams relativeParams = (RelativeLayout.LayoutParams) videoView.getLayoutParams();
                        relativeParams.setMargins(0, 100, 0, 100);  // left, top, right, bottom
                        videoView.setLayoutParams(relativeParams);
                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);}
                    else{
                        orientation = 1;
                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);}
                }
            });
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //setRequestedOrientation(videoView.getWidth() < videoView.getHeight() ? ActivityInfo.SCREEN_ORIENTATION_PORTRAIT : ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        btnTutup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri=Uri.parse(videourl);
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
                Snackbar.make(v,"Mendownload video...", Snackbar.LENGTH_SHORT).show();

            }
        });
    }



}