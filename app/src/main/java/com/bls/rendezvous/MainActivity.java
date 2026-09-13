package com.bls.rendezvous;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.view.Gravity;
import android.graphics.Color;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        TextView tv = new TextView(this);
        tv.setText("TEST OK\nCa marche!");
        tv.setTextSize(30);
        tv.setTextColor(Color.WHITE);
        tv.setBackgroundColor(Color.parseColor("#4CAF50"));
        tv.setGravity(Gravity.CENTER);
        
        setContentView(tv);
    }
}
