package com.skillage.appedu;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DBAndroid {
    public  ArrayList<Hashtable<String,String>> Records=new ArrayList<Hashtable<String,String>>();

    public void getRecords(String SQL){
        Records.clear();
        try {
            SQL= URLEncoder.encode(SQL, String.valueOf(StandardCharsets.UTF_8));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

        Request request = new Request.Builder()
                .url(GlobalVar.URLAPI+ "get.php?SQL="+SQL+"&token="+ GlobalVar.token + "&user="+GlobalVar.userID)
                .get()
                .build();
        try {
            Response response = client.newCall(request).execute();
            String strBody=response.body().string();
            JSONArray data = new JSONArray(strBody);
            for (int i = 0; i < data.length(); i++) {
                JSONObject rekord = data.getJSONObject(i);

                Hashtable<String,String> hm=new Hashtable<String,String>();
                for(int k = 0; k<rekord.names().length(); k++){
                    String fieldName= rekord.names().getString(k);
                    String fieldvalue=rekord.getString(rekord.names().getString(k));
                    if (fieldvalue.toUpperCase().equals("NULL")) fieldvalue="";
                    if (fieldvalue.contains("date")) {
                        JSONObject datatgl = new JSONObject(fieldvalue);
                        fieldvalue=datatgl.getString("date").substring(0,10).trim();
                    }
                    hm.put(fieldName,fieldvalue);
                }
                Records.add(hm);
            }
        } catch (Exception e){
            int y=1;
            String s="1";
        }

        int x=1;
    }

    public String execute(String SQL) {
        Records.clear();
        try {
            SQL= URLEncoder.encode(SQL, String.valueOf(StandardCharsets.UTF_8));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

        Request request = new Request.Builder()
                .url(GlobalVar.URLAPI+"execute.php?SQL="+SQL+"&token="+GlobalVar.token + "&user="+GlobalVar.userID)
                .get()
                .build();
        try {
            Response response = client.newCall(request).execute();
            String strBody=response.body().string();
            return strBody;
        } catch (Exception e){
            return e.getMessage();
        }

    }

    public String execute2(String SQL){
        BufferedReader in = null;
        String data = "";
        try {
            HttpClient client = new DefaultHttpClient();
            SQL= URLEncoder.encode(SQL,"UTF-8");
            URI website = new URI(GlobalVar.URLAPI+"execute.php?SQL="+SQL+"&token="+GlobalVar.token + "&user="+GlobalVar.userID);
            HttpGet request = new HttpGet();
            request.setURI(website);
            HttpResponse response = client.execute(request);
            response.getStatusLine().getStatusCode();

            in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuffer sb = new StringBuffer("");
            String l = "";
            String nl = System.getProperty("line.separator");
            while ((l = in.readLine()) !=null){
                sb.append(l + nl);
            }
            in.close();
            data = sb.toString();

        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return data.replace("\n","").trim();
    }



    public static String EnkripMD5(String enk) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(enk.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
            int x=1;
        }
        return null;
    }

    public boolean found(String table, String field, String criteria){
        getRecords("Select "+field+" from "+table+" where "+criteria);
        if (Records.size()>0) {
            return true;
        } else {
            return false;
        }
    }


    public Boolean isEmpty() {
        return Records.size()==0;
    }
    public Hashtable<String,String> data(){
        return  Records.get(0);
    }

    public static String Enkrip(String pwd) {
        String s = "";
        for (int i = 1; (i <= pwd.length()); i++) {
            char kar=  pwd.charAt(i - 1);
            s = (s + ((char)(( (int) kar + 12))));
        }
        return s;
    }

}

