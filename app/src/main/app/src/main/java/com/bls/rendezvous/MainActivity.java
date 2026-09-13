package com.bls.rendezvous;
import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.view.Gravity;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setGravity(Gravity.CENTER);
        layout.setPadding(40,40,40,40);

        Button btn1 = new Button(this); btn1.setText("🔔 BLS Alerte");
        Button btn2 = new Button(this); btn2.setText("🚀 BLS Start");
        Button btn3 = new Button(this); btn3.setText("📅 BLS Rendez-Vous");

        btn1.setOnClickListener(v -> Toast.makeText(this, "Alerte activée", Toast.LENGTH_SHORT).show());
        btn2.setOnClickListener(v -> Toast.makeText(this, "BLS Start", Toast.LENGTH_SHORT).show());
        btn3.setOnClickListener(v -> Toast.makeText(this, "Rendez-Vous", Toast.LENGTH_SHORT).show());

        layout.addView(btn1); layout.addView(btn2); layout.addView(btn3);
        setContentView(layout);
    }
                                                    }
