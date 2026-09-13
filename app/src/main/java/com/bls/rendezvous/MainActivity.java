package com.bls.rendezvous;

import android.os.Bundle;
import android.widget.TextView;
import android.view.Gravity;
import android.graphics.Color;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        TextView tv = new TextView(this);
        tv.setText("BLS Rendez-Vous\nV2 - BLUE TEST\nCa marche! ✅");
        tv.setTextSize(28);
        tv.setTextColor(Color.WHITE);
        tv.setBackgroundColor(Color.parseColor("#2196F3"));
        tv.setGravity(Gravity.CENTER);
        
        setContentView(tv);
    }
}
