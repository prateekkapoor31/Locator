package com.example.puneeth.googlemapdemoapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Puneeth on 26-03-2017.
 */

public class RegisterU extends Activity {
    EditText un,pw,cpw,uss;
    GPSTracker gps;
    double latitude,longitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        un=(EditText)findViewById(R.id.editText3);
        pw=(EditText)findViewById(R.id.editText5);
        cpw=(EditText)findViewById(R.id.editText6);

    }

    public void register(View v){
        String u,p,cp;
        u=un.getText().toString();
        p=pw.getText().toString();
        cp=cpw.getText().toString();
        if((!(u.isEmpty()&&p.isEmpty()&&cp.isEmpty()))&&(p.equals(cp))){
            String method="register"; gps=new GPSTracker(RegisterU.this);
            if(gps.canGetLocation()){
                latitude = gps.getLatitude();
                longitude = gps.getLongitude();
                //Toast.makeText(getApplicationContext(), "Your Location is - \nLat: "
                        //+ latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
            }else{
                gps.showSettingsAlert();
            }
            BackgroundTask b = new BackgroundTask(this);
            b.execute(method,u,p,String.valueOf(latitude),String.valueOf(longitude));
            finish();
            //Intent i = new Intent(this,LoginRegister.class);
            //startActivity(i);
        }
        else{
            Toast.makeText(this.getApplicationContext(),"Fill all fields",Toast.LENGTH_SHORT).show();
        }
    }
}
