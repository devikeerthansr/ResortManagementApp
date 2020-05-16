package com.example.resortmanagement;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;

import com.example.resortmanagement.util.DBOperator;

public class MainActivity extends Activity  {
    private static int TIME_OUT = 3000;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        }, TIME_OUT);
        //copy database file
        try
        { DBOperator.copyDB(getBaseContext()); }
        catch
        (Exception e){ e.printStackTrace();
        }

    }


}

