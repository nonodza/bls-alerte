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

    // =========================================================
    // HOME
    // =========================================================

    void home() {

        int[] colors = {
                Color.parseColor("#21104F"),
                Color.parseColor("#3B2A8A"),
                Color.parseColor("#2574A9"),
                Color.parseColor("#00BFA6")
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
        m.setPadding(
                dp(24),
                dp(55),
                dp(24),
                dp(40)
        );
        m.setBackground(bg);

        // =====================================================
        // LOGO
        // =====================================================

        TextView logo = new TextView(this);
        logo.setText("BLS");
        logo.setTextSize(76);
        logo.setTextColor(Color.WHITE);
        logo.setGravity(Gravity.CENTER);
        logo.setTypeface(null, 1);

        m.addView(logo);

        TextView subtitle = new TextView(this);
        subtitle.setText(
                "Visa Application Services\nSecure • Fast • Official"
        );
        subtitle.setTextSize(15);
        subtitle.setTextColor(
                Color.parseColor("#F5F5F5")
        );
        subtitle.setGravity(Gravity.CENTER);
        subtitle.setPadding(
                0,
                0,
                0,
                dp(32)
        );

        m.addView(subtitle);

        // =====================================================
        // PASSPORT SERVICES
        // =====================================================

        LinearLayout passportCard = card(
                "Passport Services",
                "Apply, Renew & Track Passport",
                "P",
                "#FF6B6B",
                "#9B59B6"
        );

        passportCard.setOnClickListener(v -> {
            passport();
        });

        m.addView(passportCard);

        // =====================================================
        // VISA CENTERS
        // =====================================================

        LinearLayout visaCard = card(
                "Visa Centers",
                "Find Nearest BLS Center\n& Directions",
                "V",
                "#3498DB",
                "#1ABC9C"
        );

        visaCard.setOnClickListener(v -> {

            try {

                Intent intent = new Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(
                                "https://www.google.com/maps/search/BLS+Spain+Visa+Center+Algeria"
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

        m.addView(visaCard);

        // =====================================================
        // GET STARTED
        // =====================================================

        TextView start = new TextView(this);

        start.setText("Get Started");
        start.setTextSize(19);
        start.setTextColor(Color.WHITE);
        start.setGravity(Gravity.CENTER);
        start.setTypeface(null, 1);

        GradientDrawable startBg =
                new GradientDrawable();

        startBg.setCornerRadius(dp(30));
        startBg.setColor(
                Color.parseColor("#55FFFFFF")
        );
        startBg.setStroke(
                dp(1),
                Color.parseColor("#99FFFFFF")
        );

        start.setBackground(startBg);

        LinearLayout.LayoutParams startParams =
                new LinearLayout.LayoutParams(
                        -1,
                        dp(60)
                );

        startParams.topMargin = dp(24);
        startParams.leftMargin = dp(10);
        startParams.rightMargin = dp(10);

        start.setLayoutParams(startParams);

        start.setOnClickListener(v -> {
            login();
        });

        m.addView(start);

        sv.addView(m);

        setContentView(sv);
    }

    // =========================================================
    // PASSPORT PAGE
    // =========================================================

    void passport() {

        LinearLayout page =
                new LinearLayout(this);

        page.setOrientation(
                LinearLayout.VERTICAL
        );

        page.setPadding(
                dp(24),
                dp(45),
                dp(24),
                dp(30)
        );

        page.setBackgroundColor(
                Color.parseColor("#F5F7FF")
        );

        TextView back =
                new TextView(this);

        back.setText("← Back");
        back.setTextSize(17);
        back.setTextColor(
                Color.parseColor("#2D1B69")
        );
        back.setTypeface(null, 1);
        back.setPadding(
                dp(8),
                dp(8),
                dp(8),
                dp(18)
        );

        back.setOnClickListener(v -> {
            home();
        });

        page.addView(back);

        TextView title =
                new TextView(this);

        title.setText(
                "Passport Services"
        );
        title.setTextSize(28);
        title.setTextColor(
                Color.parseColor("#2D1B69")
        );
        title.setTypeface(null, 1);

        page.addView(title);

        TextView info =
                new TextView(this);

        info.setText(
                "\n• New Passport\n" +
                "• Renew Passport\n" +
                "• Track Status\n\n" +
                "Coming Soon"
        );

        info.setTextSize(17);
        info.setTextColor(
                Color.parseColor("#333333")
        );

        page.addView(info);

        ScrollView sv =
                new ScrollView(this);

        sv.addView(page);

        setContentView(sv);
    }

    // =========================================================
    // LOGIN PAGE
    // =========================================================

    void login() {

        int[] colors = {
                Color.parseColor("#21104F"),
                Color.parseColor("#00BFA6")
        };

        GradientDrawable bg =
                new GradientDrawable(
                        GradientDrawable.Orientation.TOP_BOTTOM,
                        colors
                );

        ScrollView sv =
                new ScrollView(this);

        LinearLayout page =
                new LinearLayout(this);

        page.setOrientation(
                LinearLayout.VERTICAL
        );

        page.setGravity(
                Gravity.CENTER_HORIZONTAL
        );

        page.setPadding(
                dp(28),
                dp(80),
                dp(28),
                dp(40)
        );

        page.setBackground(bg);

        TextView title =
                new TextView(this);

        title.setText("Welcome Back");
        title.setTextSize(31);
        title.setTextColor(Color.WHITE);
        title.setGravity(Gravity.CENTER);
        title.setTypeface(null, 1);

        page.addView(title);

        // EMAIL

        EditText email =
                new EditText(this);

        email.setHint("Email");
        email.setTextSize(16);
        email.setSingleLine(true);
        email.setPadding(
                dp(16),
                0,
                dp(16),
                0
        );
        email.setBackgroundColor(Color.WHITE);

        LinearLayout.LayoutParams emailParams =
                new LinearLayout.LayoutParams(
                        -1,
                        dp(55)
                );

        emailParams.topMargin = dp(30);

        page.addView(
                email,
                emailParams
        );

        // PASSWORD

        EditText password =
                new EditText(this);

        password.setHint("Password");
        password.setTextSize(16);
        password.setSingleLine(true);
        password.setPadding(
                dp(16),
                0,
                dp(16),
                0
        );
        password.setBackgroundColor(Color.WHITE);

        LinearLayout.LayoutParams passwordParams =
                new LinearLayout.LayoutParams(
                        -1,
                        dp(55)
                );

        passwordParams.topMargin = dp(15);

        page.addView(
                password,
                passwordParams
        );

        // LOGIN

        TextView login =
                new TextView(this);

        login.setText("Login Success");
        login.setTextSize(18);
        login.setTextColor(Color.WHITE);
        login.setGravity(Gravity.CENTER);
        login.setTypeface(null, 1);

        GradientDrawable loginBg =
                new GradientDrawable();

        loginBg.setCornerRadius(
                dp(30)
        );

        loginBg.setColor(
                Color.parseColor("#2D1B69")
        );

        login.setBackground(loginBg);

        LinearLayout.LayoutParams loginParams =
                new LinearLayout.LayoutParams(
                        -1,
                        dp(56)
                );

        loginParams.topMargin = dp(30);

        page.addView(
                login,
                loginParams
        );

        login.setOnClickListener(v -> {

            Toast.makeText(
                    MainActivity.this,
                    "Login Success ✓",
                    Toast.LENGTH_SHORT
            ).show();

            home();
        });

        // BACK

        TextView back =
                new TextView(this);

        back.setText("← Back to Home");
        back.setTextSize(16);
        back.setTextColor(Color.WHITE);
        back.setGravity(Gravity.CENTER);

        back.setPadding(
                0,
                dp(24),
                0,
                dp(10)
        );

        page.addView(back);

        back.setOnClickListener(v -> {
            home();
        });

        sv.addView(page);

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

        LinearLayout card =
                new LinearLayout(this);

        card.setOrientation(
                LinearLayout.HORIZONTAL
        );

        card.setGravity(
                Gravity.CENTER_VERTICAL
        );

        card.setPadding(
                dp(20),
                dp(18),
                dp(20),
                dp(18)
        );

        GradientDrawable cardBg =
                new GradientDrawable();

        cardBg.setCornerRadius(
                dp(20)
        );

        cardBg.setColor(
                Color.parseColor("#45FFFFFF")
        );

        cardBg.setStroke(
                dp(1),
                Color.parseColor("#70FFFFFF")
        );

        card.setBackground(cardBg);

        LinearLayout.LayoutParams cardParams =
                new LinearLayout.LayoutParams(
                        -1,
                        dp(100)
                );

        cardParams.bottomMargin =
                dp(18);

        card.setLayoutParams(
                cardParams
        );

        // ICON

        TextView icon =
                new TextView(this);

        icon.setText(letter);
        icon.setTextSize(22);
        icon.setTextColor(Color.WHITE);
        icon.setGravity(Gravity.CENTER);
        icon.setTypeface(null, 1);

        GradientDrawable iconBg =
                new GradientDrawable();

        iconBg.setCornerRadius(
                dp(15)
        );

        iconBg.setColors(
                new int[]{
                        Color.parseColor(color1),
                        Color.parseColor(color2)
                }
        );

        iconBg.setOrientation(
                GradientDrawable.Orientation.TL_BR
        );

        icon.setBackground(iconBg);

        LinearLayout.LayoutParams iconParams =
                new LinearLayout.LayoutParams(
                        dp(60),
                        dp(60)
                );

        card.addView(
                icon,
                iconParams
        );

        // TEXT

        LinearLayout text =
                new LinearLayout(this);

        text.setOrientation(
                LinearLayout.VERTICAL
        );

        text.setPadding(
                dp(16),
                0,
                0,
                0
        );

        TextView titleView =
                new TextView(this);

        titleView.setText(title);
        titleView.setTextSize(18);
        titleView.setTextColor(Color.WHITE);
        titleView.setTypeface(null, 1);

        text.addView(titleView);

        TextView descriptionView =
                new TextView(this);

        descriptionView.setText(
                description
        );

        descriptionView.setTextSize(13);
        descriptionView.setTextColor(
                Color.parseColor("#E6E6E6")
        );

        text.addView(
                descriptionView
        );

        card.addView(text);

        return card;
    }

    // =========================================================
    // DP
    // =========================================================

    int dp(int value) {

        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                value,
                getResources().getDisplayMetrics()
        );
    }
}

مهم: هذه المرة لم أغيّر طريقة "setOnClickListener" الموجودة في نسختك التي كانت تعمل. التغييرات كلها تقريبًا في المظهر والمسافات.

بعد الـ Commit والـ Build، المفروض تبقى الوظائف:

Passport → الصفحة
Visa Centers → Maps
Get Started → Welcome Back
Login Success → Home
Back → Home

إذا هذه النسخة اشتغلت، لا نلمس منطق التنقل مرة أخرى ونبدأ بعدها بإضافة وظائف BLS الحقيقية فوق هذا الأساس.
