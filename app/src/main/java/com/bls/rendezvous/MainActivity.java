package com.bls.rendezvous;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setGravity(Gravity.CENTER);
        layout.setBackgroundColor(Color.rgb(245, 248, 253));

        TextView title = new TextView(this);
        title.setText("BLS Rendez-Vous");
        title.setTextSize(28);
        title.setTextColor(Color.rgb(20, 35, 65));
        title.setGravity(Gravity.CENTER);

        layout.addView(
                title,
                new LinearLayout.LayoutParams(
                        -1,
                        -2
                )
        );

        TextView subtitle = new TextView(this);
        subtitle.setText("Visa Appointment Assistant");
        subtitle.setTextSize(15);
        subtitle.setTextColor(Color.GRAY);
        subtitle.setGravity(Gravity.CENTER);

        layout.addView(
                subtitle,
                new LinearLayout.LayoutParams(
                        -1,
                        -2
                )
        );

        setContentView(layout);
    }
}
