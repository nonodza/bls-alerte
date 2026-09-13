package com.bls.rendezvous;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.widget.*;
import android.view.Gravity;
import android.view.View;
import android.content.Intent;
import android.net.Uri;
import android.util.TypedValue;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        home();
    }

    void home() {

        int[] colors = {
                Color.parseColor("#2D1B69"),
                Color.parseColor("#3B2A8A"),
                Color.parseColor("#2E86AB"),
                Color.parseColor("#00C9A7")
        };

        GradientDrawable bg = new GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM,
                colors
        );

        ScrollView sv = new ScrollView(this);
        sv.setFillViewport(true);

        LinearLayout m = new LinearLayout(this);
        m.setOrientation(LinearLayout.VERTICAL);
        m.setGravity(Gravity.CENTER_HORIZONTAL);
        m.setPadding(dp(24), dp(60), dp(24), dp(40));
        m.setBackground(bg);

        TextView t1 = new TextView(this);
        t1.setText("BLS");
        t1.setTextSize(90);
        t1.setTextColor(Color.WHITE);
        t1.setGravity(Gravity.CENTER);
        t1.setTypeface(null, 1);
        m.addView(t1);

        TextView t2 = new TextView(this);
        t2.setText("Visa Application Services\nSecure • Fast • Official");
        t2.setTextSize(16);
        t2.setTextColor(Color.WHITE);
        t2.setGravity(Gravity.CENTER);
        t2.setPadding(0, 0, 0, dp(40));
        m.addView(t2);

        // =========================
        // PASSPORT CARD
        // =========================

        LinearLayout c1 = card(
                "Passport Services",
                "Apply, Renew & Track Passport",
                "P",
                "#FF6B6B",
                "#9B59B6"
        );

        c1.setClickable(true);
        c1.setFocusable(true);

        c1.setOnClickListener(v -> {
            Toast.makeText(
                    MainActivity.this,
                    "Passport Services ✓",
                    Toast.LENGTH_SHORT
            ).show();

            passport();
        });

        m.addView(c1);

        // =========================
        // VISA CENTERS CARD
        // =========================

        LinearLayout c2 = card(
                "Visa Centers",
                "Find Nearest BLS Center\n& Directions",
                "V",
                "#3498DB",
                "#1ABC9C"
        );

        c2.setClickable(true);
        c2.setFocusable(true);

        c2.setOnClickListener(v -> {

            Toast.makeText(
                    MainActivity.this,
                    "Opening Maps...",
                    Toast.LENGTH_SHORT
            ).show();

            try {
                Intent intent = new Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(
                                "https://maps.google.com/?q=BLS+Spain+Algeria"
                        )
                );

                startActivity(intent);

            } catch (Exception e) {

                Toast.makeText(
                        MainActivity.this,
                        "Impossible d'ouvrir Maps",
                        Toast.LENGTH_SHORT
                ).show();
            }
        });

        m.addView(c2);

        // =========================
        // GET STARTED
        // =========================

        TextView btn = new TextView(this);

        btn.setText("Get Started");
        btn.setTextSize(20);
        btn.setTextColor(Color.WHITE);
        btn.setGravity(Gravity.CENTER);
        btn.setTypeface(null, 1);

        GradientDrawable bbg = new GradientDrawable();
        bbg.setCornerRadius(dp(30));
        bbg.setColor(Color.parseColor("#66FFFFFF"));
        bbg.setStroke(
                dp(1),
                Color.parseColor("#88FFFFFF")
        );

        btn.setBackground(bbg);

        LinearLayout.LayoutParams pr =
                new LinearLayout.LayoutParams(
                        -1,
                        dp(60)
                );

        pr.topMargin = dp(40);
        pr.leftMargin = dp(20);
        pr.rightMargin = dp(20);

        btn.setLayoutParams(pr);
        btn.setPadding(
                0,
                dp(15),
                0,
                dp(15)
        );

        btn.setClickable(true);
        btn.setFocusable(true);

        btn.setOnClickListener(v -> {

            Toast.makeText(
                    MainActivity.this,
                    "Get Started ✓",
                    Toast.LENGTH_SHORT
            ).show();

            login();
        });

        m.addView(btn);

        sv.addView(m);

        setContentView(sv);
    }

    // =========================================================
    // PASSPORT PAGE
    // =========================================================

    void passport() {

        LinearLayout m = new LinearLayout(this);

        m.setOrientation(LinearLayout.VERTICAL);

        m.setPadding(
                dp(24),
                dp(60),
                dp(24),
                dp(24)
        );

        m.setBackgroundColor(
                Color.parseColor("#F5F7FF")
        );

        TextView b = new TextView(this);

        b.setText("← Back");
        b.setTextSize(16);
        b.setTextColor(
                Color.parseColor("#2D1B69")
        );

        b.setPadding(
                dp(10),
                dp(10),
                dp(10),
                dp(10)
        );

        b.setClickable(true);
        b.setFocusable(true);

        b.setOnClickListener(v -> home());

        m.addView(b);

        TextView h = new TextView(this);

        h.setText(
                "\nPassport Services\n\nComing Soon Pro!"
        );

        h.setTextSize(22);
        h.setTypeface(null, 1);

        m.addView(h);

        ScrollView sv = new ScrollView(this);

        sv.addView(m);

        setContentView(sv);
    }

    // =========================================================
    // LOGIN PAGE
    // =========================================================

    void login() {

        int[] colors = {
                Color.parseColor("#2D1B69"),
                Color.parseColor("#00C9A7")
        };

        GradientDrawable bg = new GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM,
                colors
        );

        LinearLayout m = new LinearLayout(this);

        m.setOrientation(LinearLayout.VERTICAL);
        m.setGravity(Gravity.CENTER);
        m.setPadding(
                dp(28),
                dp(80),
                dp(28),
                dp(28)
        );

        m.setBackground(bg);

        TextView h = new TextView(this);

        h.setText("Welcome Back");
        h.setTextSize(30);
        h.setTypeface(null, 1);
        h.setTextColor(Color.WHITE);
        h.setGravity(Gravity.CENTER);

        m.addView(h);

        TextView btn = new TextView(this);

        btn.setText("Login Success →");
        btn.setTextColor(
                Color.parseColor("#0F0C29")
        );
        btn.setTextSize(18);
        btn.setGravity(Gravity.CENTER);
        btn.setTypeface(null, 1);

        GradientDrawable bbg = new GradientDrawable();

        bbg.setCornerRadius(dp(30));
        bbg.setColor(Color.WHITE);

        btn.setBackground(bbg);

        LinearLayout.LayoutParams pr =
                new LinearLayout.LayoutParams(
                        -1,
                        dp(56)
                );

        pr.topMargin = dp(40);

        btn.setLayoutParams(pr);

        btn.setPadding(
                0,
                dp(15),
                0,
                dp(15)
        );

        btn.setClickable(true);
        btn.setFocusable(true);

        btn.setOnClickListener(v -> {

            Toast.makeText(
                    MainActivity.this,
                    "Login ✓",
                    Toast.LENGTH_SHORT
            ).show();

            home();
        });

        m.addView(btn);

        TextView bk = new TextView(this);

        bk.setText("← Back to Home");
        bk.setTextColor(Color.WHITE);
        bk.setGravity(Gravity.CENTER);
        bk.setTextSize(16);

        bk.setPadding(
                0,
                dp(20),
                0,
                0
        );

        bk.setClickable(true);
        bk.setFocusable(true);

        bk.setOnClickListener(v -> home());

        m.addView(bk);

        ScrollView sv = new ScrollView(this);

        sv.addView(m);

        setContentView(sv);
    }

    // =========================================================
    // CARD
    // =========================================================

    LinearLayout card(
            String title,
            String description,
            String letter,
            String color1,
            String color2
    ) {

        LinearLayout ca = new LinearLayout(this);

        ca.setOrientation(LinearLayout.HORIZONTAL);
        ca.setGravity(Gravity.CENTER_VERTICAL);

        ca.setPadding(
                dp(20),
                dp(20),
                dp(20),
                dp(20)
        );

        GradientDrawable g = new GradientDrawable();

        g.setCornerRadius(dp(20));
        g.setColor(Color.parseColor("#55FFFFFF"));
        g.setStroke(
                dp(1),
                Color.parseColor("#77FFFFFF")
        );

        ca.setBackground(g);

        LinearLayout.LayoutParams lp =
                new LinearLayout.LayoutParams(
                        -1,
                        dp(100)
                );

        lp.bottomMargin = dp(20);

        ca.setLayoutParams(lp);

        // مهم:
        // العناصر الداخلية لا تأخذ الضغط من البطاقة

        TextView ic = new TextView(this);

        ic.setText(letter);
        ic.setTextSize(22);
        ic.setTextColor(Color.WHITE);
        ic.setGravity(Gravity.CENTER);
        ic.setTypeface(null, 1);

        ic.setClickable(false);
        ic.setFocusable(false);

        GradientDrawable ig = new GradientDrawable();

        ig.setCornerRadius(dp(15));

        ig.setColors(new int[]{
                Color.parseColor(color1),
                Color.parseColor(color2)
        });

        ig.setOrientation(
                GradientDrawable.Orientation.TL_BR
        );

        ic.setBackground(ig);

        ic.setLayoutParams(
                new LinearLayout.LayoutParams(
                        dp(60),
                        dp(60)
                )
        );

        ca.addView(ic);

        LinearLayout tx = new LinearLayout(this);

        tx.setOrientation(
                LinearLayout.VERTICAL
        );

        tx.setPadding(
                dp(16),
                0,
                0,
                0
        );

        tx.setClickable(false);
        tx.setFocusable(false);

        TextView t = new TextView(this);

        t.setText(title);
        t.setTextSize(18);
        t.setTextColor(Color.WHITE);
        t.setTypeface(null, 1);

        t.setClickable(false);
        t.setFocusable(false);

        tx.addView(t);

        TextView d = new TextView(this);

        d.setText(description);
        d.setTextSize(13);
        d.setTextColor(
                Color.parseColor("#E0E0E0")
        );

        d.setClickable(false);
        d.setFocusable(false);

        tx.addView(d);

        ca.addView(tx);

        return ca;
    }

    // =========================================================
    // DP
    // =========================================================

    int dp(int v) {

        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                v,
                getResources().getDisplayMetrics()
        );
    }
}
