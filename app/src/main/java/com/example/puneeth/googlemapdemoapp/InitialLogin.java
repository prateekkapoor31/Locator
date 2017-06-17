package com.example.puneeth.googlemapdemoapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ExpandableListView;

import android.widget.ListView;
import android.widget.Toast;



/**
 * Created by Puneeth on 26-03-2017.
 */

public class InitialLogin extends Activity{
    EditText et,fname;
    String cname,aname;
    double latitude,longitude;
    Bundle b;
    GPSTracker gps;
    public void goTo(){
        Intent ne=new Intent(this,MapsActivity.class);
        startActivity(ne);
    }
    public void onCreate(Bundle savedInstanceState) {
        Log.d("2", "In goto,");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.initial_activity);
        b=new Bundle();
        //Toast.makeText(this.getApplicationContext(),"logged in",Toast.LENGTH_SHORT);
        et=(EditText)findViewById(R.id.editText2);
        fname=(EditText)findViewById(R.id.unam);
    }

    public void updateLocation(View v){
        Log.d("2", "befor going to add user");
        String name,method;
        cname=fname.getText().toString();
        gps=new GPSTracker(InitialLogin.this);
        if(gps.canGetLocation()){
            latitude = gps.getLatitude();
            longitude = gps.getLongitude();
            Toast.makeText(getApplicationContext(), "Your Location is - \nLat: "
                    + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
        }else{
            gps.showSettingsAlert();
        }
        Log.d("2", "in addUser "+cname);
        method="updatelocation";
        Log.d("2", "going to add user");
        Log.d("2","lat "+latitude);
        Log.d("2","long "+longitude);
        if(!cname.isEmpty()) {
            BackgroundTask b = new BackgroundTask(this);
            b.execute(method, cname, String.valueOf(latitude), String.valueOf(longitude));
        }else{
            Toast.makeText(this.getApplicationContext(),"Fill all fields",Toast.LENGTH_SHORT).show();
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void findUser(View v){
        Log.d("2","going to find user");
        String name="",method="find";
        String lat="",lon="",a="";
        name=et.getText().toString();
        Log.d("2","going to find user2");
        //gps=new GPSTracker(InitialLogin.this);
        //Intent intent=new Intent(this,MapsActivity.class);
        //b.putString("lat",String.valueOf(gps.getLatitude()));
        //b.putString("lon",String.valueOf(gps.getLongitude()));
        //intent.putExtras(b);
        //startActivity(intent,b);
        if(!name.isEmpty()) {
            BackgroundTask b = new BackgroundTask(this);
            b.execute(method, name);
        }else{
            Toast.makeText(this.getApplicationContext(),"Fill all fields",Toast.LENGTH_SHORT).show();
        }
    }

}


