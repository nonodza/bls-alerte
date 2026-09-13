package com.bls.rendezvous;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Color;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // خلفية زرقاء
        View view = new View(this);
        view.setBackgroundColor(Color.parseColor("#2196F3"));
        setContentView(view);
        
        // رسالة
        Toast.makeText(this, "BLS V2 - 21:30 NEW!", Toast.LENGTH_LONG).show();
    }
}
