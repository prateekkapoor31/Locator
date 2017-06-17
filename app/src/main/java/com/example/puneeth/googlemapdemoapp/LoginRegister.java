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

public class LoginRegister extends Activity {
    String u;
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_register_activity);
    }
    public void login(View v){
        Intent values=new Intent(LoginRegister.this,InitialLogin.class);
        values.putExtra("username",u);
        startActivity(values);

    }

    public void register(View v){
        Intent i=new Intent(this,RegisterU.class);
        startActivity(i);
    }
}
