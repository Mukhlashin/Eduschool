package com.skillage.appedu;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Anton on 12/12/2017.
 */

public class GlobalFunction {
  public static Context mcontext;
  public static void ShowDialogDefault(Context contex, String msg, String title){
    AlertDialog alertDialog = new AlertDialog.Builder(contex).create();
    alertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
    alertDialog.setTitle(title);
    alertDialog.setMessage(msg);
    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
            new DialogInterface.OnClickListener() {
              public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
              }
            });
    alertDialog.show();
  }

  public static void ShowAlert(Activity activity, String title, String message){
    Intent intent = new Intent(activity, MyDialog.class);
    intent.putExtra("Pesan",message);
    activity.startActivity(intent);
  }
  public static void ShowDialog(Activity activity, String message){
    Intent intent = new Intent(activity, MyDialog.class);
    intent.putExtra("Pesan",message);
    activity.startActivity(intent);
  }

  public static void TampilDialog(Context context, String message){
    Intent intent = new Intent(context, MyDialog.class);
    intent.putExtra("Pesan",message);
    context.startActivity(intent);
  }

  public static void OpenFile(Activity context,String filePath){
    File file = new File(filePath);
    MimeTypeMap map = MimeTypeMap.getSingleton();
    String ext = MimeTypeMap.getFileExtensionFromUrl(file.getName());
    String type = map.getMimeTypeFromExtension(ext);

    if (type == null)
      type = "*/*";

    Intent intent = new Intent(Intent.ACTION_VIEW);
    Uri data = Uri.fromFile(file);

    intent.setDataAndType(data, type);
    context.startActivity(intent);
  }
  public static void ShowDialogAndClose(Activity activity, String message){
    Intent intent = new Intent(activity, MyDialog2.class);
    intent.putExtra("Pesan",message);
    activity.startActivity(intent);
  }

  public static void ShowConfirmDialog(Context context, String message){
    mcontext=context;
    Intent intent = new Intent(context, MyConfirmDialog.class);
    intent.putExtra("Pesan",message);
    context.startActivity(intent);
  }

  public static void ShowAlert2(Activity activity, String title, String message) {

    TextView judul = new TextView(activity);
    judul.setText(title);
    judul.setPadding(10, 10, 10, 10);
    judul.setGravity(Gravity.CENTER);
    judul.setTextColor(Color.WHITE);
    judul.setBackgroundColor(ContextCompat.getColor(activity, R.color.light_blue_900));
    judul.setTextSize(20);

    AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.MyDialogTheme);
    builder.setCustomTitle(judul);
    builder.setMessage(Html.fromHtml("<font color='#FF7F27'>" + message+"</font>"));
    builder.setCancelable(false);
    builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
      public void onClick(DialogInterface dialog, int id) {
        dialog.cancel();

      }

    });

    AlertDialog alert = builder.create();
    alert.show();
    Button nbutton = alert.getButton(DialogInterface.BUTTON_NEGATIVE);
    //nbutton.setBackgroundColor(ContextCompat.getColor(activity, R.color.greendark));
    nbutton.setBackground(ContextCompat.getDrawable(activity,R.drawable.round_bg_button_1));
    nbutton.setTextColor(Color.WHITE);

  }

  public static void setMargins (Context con, View view, int left, int top, int right, int bottom) {
    if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
      ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();

      final float scale = con.getResources().getDisplayMetrics().density;
      int pixel_left =  (int)(left * scale + 0.5f);
      int pixel_top =  (int)(top * scale + 0.5f);
      int pixel_right =  (int)(right * scale + 0.5f);
      int pixel_bottom=  (int)(bottom * scale + 0.5f);

      p.setMargins(pixel_left, pixel_top, pixel_right, pixel_bottom);
      view.requestLayout();
    }
  }

  public static int convertPixelsToDp(Context context, int px){
    Resources resources = context.getResources();
    DisplayMetrics metrics = resources.getDisplayMetrics();
    int dp = px / (metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    return dp;
  }

  public static String getTanggal(){
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date date = new Date();
    return  dateFormat.format(date);
  }
  public static String FormatTanggal(String date){
    String namaHari=""; String tgl="";
    SimpleDateFormat sd2=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    SimpleDateFormat sd=new SimpleDateFormat("dd-MMM-yyyy");
    try {
      Date d;
      if (date.split("-")[0].length()==4) {
        d=sd2.parse(date);
        tgl=sd.format(d);
      } else {
        d=sd.parse(date);
        tgl=date;
      }
      Calendar c = Calendar.getInstance();
      c.setTime(d);
      int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
      switch (dayOfWeek){
        case 1 : namaHari= "Minggu"; break;
        case 2 : namaHari= "Senin"; break;
        case 3 : namaHari= "Selasa"; break;
        case 4 : namaHari= "Rabu"; break;
        case 5 : namaHari= "Kamis"; break;
        case 6 : namaHari= "Jumat"; break;
        case 7 : namaHari= "Sabtu"; break;
      }
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return namaHari+","+ tgl;
  }

  public static String getFirstName(String nama2){
    String[] splited2 = nama2.split("\\s+");
    String firstname2 = "";
    String lastname2="";
    if (splited2.length>1) {
      firstname2 = nama2.split(" ")[0];
      lastname2="";
      for (int w=1; w<splited2.length; w++){
        lastname2=lastname2+splited2[w];
      }
    } else {
      firstname2=nama2;
      lastname2="";
    }
    return firstname2;
  }

  public static String getLastName(String nama2){
    String[] splited2 = nama2.split("\\s+");
    String firstname2 = "";
    String lastname2="";
    if (splited2.length>1) {
      firstname2 = nama2.split(" ")[0];
      lastname2="";
      for (int w=1; w<splited2.length; w++){
        lastname2=lastname2+splited2[w];
      }
    } else {
      firstname2=nama2;
      lastname2="";
    }
    return lastname2;
  }

  public static void ShowDialogAndLogin(Activity activity, String message){
    Intent intent = new Intent(activity, MyDialog3.class);
    intent.putExtra("Pesan",message);
    activity.startActivity(intent);
  }

  public static String JSonparser(String json, String key){
    JSONObject obj = null;
    try {
      obj = new JSONObject(json);
      String hasil = obj.get(key).toString();
      return hasil;
    } catch (JSONException e) {
      e.printStackTrace();
      return "";
    }
  }

  public static int getIndex(Spinner spinner, String myString){
    for (int i=0;i<spinner.getCount();i++){
      if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
        return i;
      }
    }
    return 0;
  }


  public static Bitmap loadImageFromURL(String alamat){
    Bitmap bmp=null;
    URL url = null;
    try {
      url = new URL(alamat);
      bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
    } catch (MalformedURLException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return  bmp;
  }


  public static int LoadSaldo(String userID){
    DBAndroid db=new DBAndroid();
    db.getRecords("select role from t_usr where userid='" + userID +"'");
    if (db.Records.size()>0) {
      String role = db.Records.get(0).get("role");
      switch (role) {
        case "R.10":
          db.getRecords("select saldo from t_siswa");
          break;
        case "R.5":
          db.getRecords("select saldo from t_guru");
          break;
      }
      if (db.Records.size()>0) {
        try {
          return Integer.parseInt(db.Records.get(0).get("saldo"));
        } catch (Exception e) {
          return 0;
        }
      } else return 0;
    } else return 0;
  }
}
