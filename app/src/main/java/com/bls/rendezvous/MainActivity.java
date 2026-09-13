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

        TextView secure = new TextView(this);
        secure.setText("Secure • Fast • Official");
        secure.setTextSize(14);
        secure.setTextColor(Color.parseColor("#B0B0D0"));
        secure.setGravity(Gravity.CENTER);
        secure.setPadding(0, 0, 0, dp(40));
        main.addView(secure);

        main.addView(createCard("Passport Services", "Apply, Renew & Track Passport", "P"));
        main.addView(createCard("Visa Centers", "Find Nearest BLS Center\n& Directions", "V"));

        TextView btn = new TextView(this);
        btn.setText("Get Started");
        btn.setTextSize(20);
        btn.setTextColor(Color.WHITE);
        btn.setGravity(Gravity.CENTER);
        btn.setTypeface(null, android.graphics.Typeface.BOLD);
        GradientDrawable btnBg = new GradientDrawable();
        btnBg.setCornerRadius(dp(30));
        btnBg.setColor(Color.parseColor("#66FFFFFF"));
        btnBg.setStroke(1, Color.parseColor("#88FFFFFF"));
        btn.setBackground(btnBg);
        LinearLayout.LayoutParams bp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dp(60));
        bp.topMargin = dp(40);
        bp.leftMargin = dp(20);
        bp.rightMargin = dp(20);
        btn.setLayoutParams(bp);
        btn.setPadding(0, dp(15), 0, dp(15));
        main.addView(btn);

        scroll.addView(main);
        setContentView(scroll);
    }

    private LinearLayout createCard(String title, String desc, String letter) {
        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.HORIZONTAL);
        card.setGravity(Gravity.CENTER_VERTICAL);
        card.setPadding(dp(20), dp(20), dp(20), dp(20));
        
        GradientDrawable cardBg = new GradientDrawable();
        cardBg.setCornerRadius(dp(20));
        cardBg.setColor(Color.parseColor("#55FFFFFF"));
        cardBg.setStroke(1, Color.parseColor("#77FFFFFF"));
        card.setBackground(cardBg);
        
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.bottomMargin = dp(20);
        card.setLayoutParams(lp);

        TextView ico = new TextView(this);
        ico.setText(letter);
        ico.setTextSize(22);
        ico.setTextColor(Color.WHITE);
        ico.setGravity(Gravity.CENTER);
        ico.setTypeface(null, android.graphics.Typeface.BOLD);
        GradientDrawable icoBg = new GradientDrawable();
        icoBg.setCornerRadius(dp(15));
        if(letter.equals("P")) {
            icoBg.setColors(new int[]{Color.parseColor("#FF6B6B"), Color.parseColor("#9B59B6")});
        } else {
            icoBg.setColors(new int[]{Color.parseColor("#3498DB"), Color.parseColor("#1ABC9C")});
        }
        icoBg.setOrientation(GradientDrawable.Orientation.TL_BR);
        ico.setBackground(icoBg);
        LinearLayout.LayoutParams ip = new LinearLayout.LayoutParams(dp(60), dp(60));
        ico.setLayoutParams(ip);
        card.addView(ico);

        LinearLayout texts = new LinearLayout(this);
        texts.setOrientation(LinearLayout.VERTICAL);
        texts.setPadding(dp(16), 0, 0, 0);
        
        TextView t = new TextView(this);
        t.setText(title);
        t.setTextSize(18);
        t.setTextColor(Color.WHITE);
        t.setTypeface(null, android.graphics.Typeface.BOLD);
        texts.addView(t);

        TextView d = new TextView(this);
        d.setText(desc);
        d.setTextSize(13);
        d.setTextColor(Color.parseColor("#E0E0E0"));
        d.setPadding(0, dp(4), 0, 0);
        texts.addView(d);
