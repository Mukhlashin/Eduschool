package com.skillage.appedu;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class UploadFile extends AsyncTask<String, String, String> {
  public String FileName;
  public String TargetFileName;
  File sourceFile;
  int totalSize;
  public Context context;
  public String FILE_UPLOAD_URL;
  ProgressDialog mProgress;
  public String hasilUpload="";
  Activity activity;
  public boolean sukses=false;
  public String pesanUntukDitampilkan="";
  @Override

  protected void onPreExecute() {
    mProgress=new ProgressDialog(activity);
    sourceFile = new File(FileName);
    totalSize = (int)sourceFile.length();
    if (totalSize>25*1024*1024) {
      NumberFormat formatter = new DecimalFormat("#0.00");
      GlobalFunction.ShowDialog( activity ,"Maaf. Ukuran File terlalu besar ("+formatter.format(1.0*totalSize/(1024*1024))+" MB). Yang diperbolehkan adalah maksimal 25MB. Silakan ambil foto lagi.");
      cancel(true);
    } else {
      mProgress.setTitle("Mengupload  "+sourceFile.getName()+"...");
      mProgress.setCanceledOnTouchOutside(false);
      mProgress.setCancelable(false);
      mProgress.show();
    }
    super.onPreExecute();
  }

  @Override
  protected void onProgressUpdate(String... progress) {
    int persen= Integer.parseInt(progress[0]);
  }

  @Override
  protected String doInBackground(String... args) {

    HttpURLConnection.setFollowRedirects(false);
    HttpURLConnection connection = null;
    String fileName = sourceFile.getName();

    try {
      connection = (HttpURLConnection) new URL(FILE_UPLOAD_URL).openConnection();
      connection.setRequestMethod("POST");
      String boundary = "---------------------------boundary";
      String tail = "\r\n--" + boundary + "--\r\n";
      connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
      connection.setDoOutput(true);

      String metadataPart = "--" + boundary + "\r\n"
              + "Content-Disposition: form-data; name=\"metadata\"\r\n\r\n"
              + "" + "\r\n";

      String fileHeader1 = "--" + boundary + "\r\n"
              + "Content-Disposition: form-data; name=\"fileToUpload\"; filename=\""
              + TargetFileName + "\"\r\n"
              + "Content-Type: application/octet-stream\r\n"
              + "Content-Transfer-Encoding: binary\r\n";

      long fileLength = sourceFile.length() + tail.length();
      String fileHeader2 = "Content-length: " + fileLength + "\r\n";
      String fileHeader = fileHeader1 + fileHeader2 + "\r\n";
      String stringData = metadataPart + fileHeader;

      long requestLength = stringData.length() + fileLength;
      connection.setRequestProperty("Content-length", "" + requestLength);
      connection.setFixedLengthStreamingMode((int) requestLength);
      connection.connect();

      DataOutputStream out = new DataOutputStream(connection.getOutputStream());
      out.writeBytes(stringData);
      out.flush();

      int progress = 0;
      int bytesRead = 0;
      byte buf[] = new byte[1024];
      BufferedInputStream bufInput = new BufferedInputStream(new FileInputStream(sourceFile));
      while ((bytesRead = bufInput.read(buf)) != -1) {
        // write output
        out.write(buf, 0, bytesRead);
        out.flush();
        progress += bytesRead; // Here progress is total uploaded bytes

        publishProgress(""+(int)((progress*100)/totalSize)); // sending progress percent to publishProgress
      }

      // Write closing boundary and close stream
      out.writeBytes(tail);
      out.flush();
      out.close();

      // Get server response
      BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
      String line = "";
      StringBuilder builder = new StringBuilder();
      while((line = reader.readLine()) != null) {
        builder.append(line);
      }

      hasilUpload=builder.toString();

    } catch (Exception e) {
      String s=e.getMessage();
      hasilUpload=e.getMessage();
      sukses=false;
    } finally {
      if (connection != null) connection.disconnect();
    }
    return null;
  }

  @Override
  protected void onPostExecute(String result) {
    Log.e("Response", "Response from server: " + result);
    try {
      mProgress.dismiss();
    } catch (Exception e){

    }

    if (hasilUpload.toUpperCase().contains("SUKSES") ||  hasilUpload.toUpperCase().contains("SUDAH ADA")) sukses=true; else sukses=false;
    if (sukses && !pesanUntukDitampilkan.trim().equals(""))
     GlobalFunction.ShowDialog(activity,pesanUntukDitampilkan);
    else if (!sukses) {
      //GlobalFunction.ShowDialog(activity,"Upload file gagal");
      int i=1;
    }
    super.onPostExecute(result);
  }
}