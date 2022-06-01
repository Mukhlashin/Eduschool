package com.skillage.appedu;

import static android.content.Context.DOWNLOAD_SERVICE;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.net.URLEncoder;

public class ShowVideoFragment extends Fragment {

    VideoView videoView;
    ProgressDialog progressDialog;
    Button btnTutup;
    ImageView btnDownload;
    String file ;
    String videourl ;
    TextView txtJudul;
    private long lastDownload=-1L;
    private DownloadManager mgr=null;
    ProgressBar spinnerView;

    private static final String ARG_PARAM1 = "nama_file";
    private static final String ARG_PARAM2 = "judul";

    private String mParam1;
    private String mParam2;

    TextView txtTest;
    public ShowVideoFragment() {

    }

    public static ShowVideoFragment newInstance(String param1,String param2) {
        ShowVideoFragment fragment = new ShowVideoFragment();
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
        return inflater.inflate(R.layout.activity_show_video, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mgr=(DownloadManager) getContext().getSystemService(DOWNLOAD_SERVICE);
        progressDialog = new ProgressDialog(getContext(),
                R.style.AppTheme_Dark_Dialog);
        spinnerView = (ProgressBar) view.findViewById(R.id.my_spinner);
        btnDownload=(ImageView)  view.findViewById(R.id.btnDownload);
        txtJudul= view.findViewById(R.id.txtJudul);
        txtJudul.setText(mParam2);
        ImageView btnClose=(ImageView) view.findViewById(R.id.btnClose);
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
        btnTutup=(Button) view.findViewById(R.id.btnTutup);
        videoView = (VideoView) view.findViewById(R.id.videoView);

        MediaController mediaController = new MediaController(getContext());
        videoView.setMediaController(mediaController);

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
        file=mParam1;
        videourl= GlobalVar.URL+"/files/"+  file ;
        String file1 = videourl.substring(videourl.lastIndexOf('/') + 1);
        file1 = URLEncoder.encode(file1).replace("+", "%20");
        videourl =  videourl.substring(0, videourl.lastIndexOf('/')+1)+file1;

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
                        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);}
                    else{
                        orientation = 1;
                        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);}
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