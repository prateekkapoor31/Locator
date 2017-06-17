package com.example.puneeth.googlemapdemoapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.system.Os;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Puneeth on 26-03-2017.
 */

public class BackgroundTask extends AsyncTask<String,Void,String> {
    Context ctx;
    AlertDialog ad;
    Bundle b;
    String afterl="",aftrad="";
    String c_user="";
    public AsyncResponse deligate=null;
    BackgroundTask(Context ctx){
        this.ctx=ctx;
    }
    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }



    @Override
    protected void onPreExecute() {
        ad=new AlertDialog.Builder(ctx).create();
        ad.setTitle("Login information");
        b=new Bundle();
    }

    @Override
    protected String doInBackground(String... params) {
        String method = params[0];
        String temp="0";
        Log.d("2", "method:" + method);
        String reg_url = "http://www.androidpjt.esy.es/register.php";
        String loc_url="http://www.androidpjt.esy.es/location.php";
        String find_url="http://www.androidpjt.esy.es/find.php";
        if(method.equals("register")){
            String user_name=params[1];
            String user_pass=params[2];
            String lat=params[3];
            String lon=params[4];
            try {
                URL url=new URL(reg_url);
                HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream=httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String data=URLEncoder.encode("user","UTF-8")+"="+URLEncoder.encode(user_name,"UTF-8")+"&"+
                        URLEncoder.encode("pass","UTF-8")+"="+URLEncoder.encode(user_pass,"UTF-8")+"&"+
                        URLEncoder.encode("lat","UTF-8")+"="+URLEncoder.encode(lat,"UTF-8")+"&"+
                        URLEncoder.encode("lon","UTF-8")+"="+URLEncoder.encode(lon,"UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream=httpURLConnection.getInputStream();
                inputStream.close();
                return "successregestr";
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        if (method.equals("updatelocation")){
            String user="",lat="",lon="";
            user=params[1];
            lat=params[2];
            lon=params[3];
            try{
                URL url=new URL(loc_url);
                HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream=httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String data=URLEncoder.encode("user","UTF-8")+"="+URLEncoder.encode(user,"UTF-8")+"&"+
                        URLEncoder.encode("lat","UTF-8")+"="+URLEncoder.encode(lat,"UTF-8")+"&"+
                        URLEncoder.encode("lon","UTF-8")+"="+URLEncoder.encode(lon,"UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream=httpURLConnection.getInputStream();
                inputStream.close();
                Log.d("2","updated");
                return "successupdate";
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        if(method.equals("find")){
            String user="";
            user=params[1];
            Log.d("2","in find for: "+user);
            try {
                URL url=new URL(find_url);
                HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream=httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String data=URLEncoder.encode("user","UTF-8")+"="+URLEncoder.encode(user,"UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream=httpURLConnection.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String response="";
                String line="";
                while((line=bufferedReader.readLine())!=null){
                    response+=line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return response;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onPostExecute(String result) {
        if(result.equals("successregestr"))
            Toast.makeText(ctx,"successful register",Toast.LENGTH_LONG).show();
        else if (result.equals("successupdate"))
            Toast.makeText(ctx,"successful update",Toast.LENGTH_LONG).show();
        else if (result.equals("nf")){
            Toast.makeText(ctx,"cannot find user",Toast.LENGTH_LONG).show();
        }
        else if(result.contains(" ")){
            Log.d("2","in onpostexcute for find:  "+result);
            Intent intent=new Intent(ctx,MapsActivity.class);
            String[] lalo=result.split("\\s+");
            b.putString("lat",String.valueOf(lalo[0]));
            b.putString("lon",String.valueOf(lalo[1]));
            Log.d("2","Latitude: "+lalo[0]+" longitude: "+lalo[1]);
            intent.putExtras(b);
            Log.d("2","going to map");
            ctx.startActivity(intent,b);
        }
        else {
            Toast.makeText(ctx,"fill all fields",Toast.LENGTH_LONG).show();
        }
    }
}
