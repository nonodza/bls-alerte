package com.bls.rendezvous;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Button;

public class MainActivity extends Activity {

    private final int NAVY = Color.rgb(25, 35, 70);
    private final int BLUE = Color.rgb(55, 105, 205);
    private final int PURPLE = Color.rgb(58, 35, 125);
    private final int CYAN = Color.rgb(45, 185, 205);
    private final int GREEN = Color.rgb(35, 175, 105);
    private final int GRAY = Color.rgb(105, 120, 145);
    private final int LIGHT = Color.rgb(245, 248, 253);
    private final int WHITE = Color.WHITE;

    private LinearLayout root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window window = getWindow();
        window.setStatusBarColor(Color.rgb(75, 70, 120));
        window.setNavigationBarColor(Color.WHITE);

        showSplash();
    }

    // =========================================================
    // SPLASH SCREEN
    // =========================================================

    private void showSplash() {

        LinearLayout splash = new LinearLayout(this);
        splash.setOrientation(LinearLayout.VERTICAL);
        splash.setGravity(Gravity.CENTER);

        GradientDrawable background =
                new GradientDrawable(
                        GradientDrawable.Orientation.TL_BR,
                        new int[]{
                                Color.rgb(55, 30, 125),
                                Color.rgb(55, 85, 170),
                                Color.rgb(35, 145, 185),
                                Color.rgb(25, 195, 175)
                        }
                );

        splash.setBackground(background);

        TextView logo = new TextView(this);
        logo.setText("BLS");
        logo.setTextSize(76);
        logo.setTextColor(Color.WHITE);
        logo.setTypeface(Typeface.DEFAULT_BOLD);
        logo.setGravity(Gravity.CENTER);

        splash.addView(
                logo,
                new LinearLayout.LayoutParams(
                        -1,
                        -2
                )
        );

        TextView subtitle = new TextView(this);
        subtitle.setText("Visa Appointment Services");
        subtitle.setTextSize(17);
        subtitle.setTextColor(Color.WHITE);
        subtitle.setGravity(Gravity.CENTER);

        LinearLayout.LayoutParams sp =
                new LinearLayout.LayoutParams(
                        -1,
                        -2
                );

        sp.topMargin = dp(8);

        splash.addView(subtitle, sp);

        setContentView(splash);

        logo.setAlpha(0f);
        subtitle.setAlpha(0f);

        logo.animate()
                .alpha(1f)
                .setDuration(600)
                .start();

        subtitle.animate()
                .alpha(1f)
                .setDuration(700)
                .setStartDelay(250)
                .start();

        new Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {

                        splash.animate()
                                .alpha(0f)
                                .setDuration(500)
                                .withEndAction(
                                        new Runnable() {
                                            @Override
                                            public void run() {
                                                showHome();
                                            }
                                        }
                                )
                                .start();

                    }
                },
                1500
        );
    }

    // =========================================================
    // HOME
    // =========================================================

    private void showHome() {

        root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);

        GradientDrawable background =
                new GradientDrawable(
                        GradientDrawable.Orientation.TL_BR,
                        new int[]{
                                Color.rgb(245, 248, 255),
                                Color.rgb(238, 244, 255),
                                Color.rgb(247, 243, 252)
                        }
                );

        root.setBackground(background);

        setContentView(root);

        // ---------------- HEADER ----------------

        LinearLayout header = new LinearLayout(this);
        header.setGravity(Gravity.CENTER_VERTICAL);
        header.setPadding(
                dp(20),
                dp(18),
                dp(20),
                dp(8)
        );

        LinearLayout titles =
                new LinearLayout(this);

        titles.setOrientation(
                LinearLayout.VERTICAL
        );

        TextView appName =
                text(
                        "BLS Rendez-Vous",
                        24,
                        NAVY
                );

        appName.setTypeface(
                Typeface.DEFAULT_BOLD
        );

        titles.addView(appName);

        titles.addView(
                text(
                        "Your visa appointment assistant",
                        12,
                        GRAY
                )
        );

        header.addView(
                titles,
                new LinearLayout.LayoutParams(
                        0,
                        -2,
                        1
                )
        );

        TextView settings =
                text(
                        "⚙",
                        25,
                        NAVY
                );

        settings.setGravity(
                Gravity.CENTER
        );

        settings.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showSettings();
                    }
                }
        );

        header.addView(
                settings,
                new LinearLayout.LayoutParams(
                        dp(48),
                        dp(48)
                )
        );

        root.addView(header);

        // ---------------- SCROLL AREA ----------------

        ScrollView scroll =
                new ScrollView(this);

        scroll.setFillViewport(true);

        LinearLayout content =
                new LinearLayout(this);

        content.setOrientation(
                LinearLayout.VERTICAL
        );

        content.setPadding(
                dp(18),
                dp(5),
                dp(18),
                dp(15)
        );

        scroll.addView(content);

        root.addView(
                scroll,
                new LinearLayout.LayoutParams(
                        -1,
                        0,
                        1
                )
        );

        // ---------------- CURRENT APPLICATION ----------------

        LinearLayout current = card();

        LinearLayout currentTop =
                new LinearLayout(this);

        currentTop.setGravity(
                Gravity.CENTER_VERTICAL
        );

        LinearLayout country =
                new LinearLayout(this);

        country.setOrientation(
                LinearLayout.VERTICAL
        );

        TextView label =
                text(
                        "CURRENT APPLICATION",
                        10,
                        GRAY
                );

        TextView countryName =
                text(
                        "🇪🇸  Spain",
                        21,
                        NAVY
                );

        countryName.setTypeface(
                Typeface.DEFAULT_BOLD
        );

        TextView center =
                text(
                        "Algiers Visa Center",
                        12,
                        GRAY
                );

        country.addView(label);
        country.addView(countryName);
        country.addView(center);

        currentTop.addView(
                country,
                new LinearLayout.LayoutParams(
                        0,
                        -2,
                        1
                )
        );

        TextView active =
                text(
                        "● ACTIVE",
                        11,
                        GREEN
                );

        currentTop.addView(active);

        current.addView(currentTop);

        TextView interval =
                text(
                        "Monitoring every 2 minutes",
                        12,
                        GRAY
                );

        current.addView(
                interval,
                margin(0, 12, 0, 0)
        );

        content.addView(
                current,
                margin(0, 5, 0, 10)
        );

        // ---------------- MONITORING ----------------

        LinearLayout monitor =
                card();

        LinearLayout monitorRow =
                new LinearLayout(this);

        monitorRow.setGravity(
                Gravity.CENTER_VERTICAL
        );

        TextView monitorIcon =
                text(
                        "◉",
                        28,
                        BLUE
                );

        monitorIcon.setGravity(
                Gravity.CENTER
        );

        monitorRow.addView(
                monitorIcon,
                new LinearLayout.LayoutParams(
                        dp(45),
                        dp(45)
                )
        );

        LinearLayout monitorInfo =
                new LinearLayout(this);

        monitorInfo.setOrientation(
                LinearLayout.VERTICAL
        );

        TextView monitorTitle =
                text(
                        "Appointment Monitoring",
                        15,
                        NAVY
                );

        monitorTitle.setTypeface(
                Typeface.DEFAULT_BOLD
        );

        TextView monitorStatus =
                text(
                        "Monitoring is active",
                        12,
                        GREEN
                );

        monitorInfo.addView(monitorTitle);
        monitorInfo.addView(monitorStatus);

        monitorRow.addView(
                monitorInfo,
                new LinearLayout.LayoutParams(
                        0,
                        -2,
                        1
                )
        );

        Button monitorButton =
                smallButton("STOP");

        monitorButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        monitorButton.setText("START");
                        monitorStatus.setText(
                                "Monitoring is paused"
                        );
                        monitorStatus.setTextColor(
                                GRAY
                        );
                    }
                }
        );

        monitorRow.addView(monitorButton);

        monitor.addView(monitorRow);

        content.addView(
                monitor,
                margin(0, 0, 0, 10)
        );

        // ---------------- SERVICES TITLE ----------------

        TextView services =
                text(
                        "Services",
                        19,
                        NAVY
                );

        services.setTypeface(
                Typeface.DEFAULT_BOLD
        );

        content.addView(
                services,
                margin(2, 3, 0, 3)
        );

        // =====================================================
        // 2 x 3 SERVICES
        // =====================================================

        LinearLayout row1 =
                new LinearLayout(this);

        row1.addView(
                service(
                        "📅",
                        "Appointments",
                        "Find appointments",
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                showAppointments();
                            }
                        }
                )
        );

        row1.addView(
                service(
                        "🔔",
                        "Alerts",
                        "Availability alerts",
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                showAlerts();
                            }
                        }
                )
        );

        content.addView(row1);

        LinearLayout row2 =
                new LinearLayout(this);

        row2.addView(
                service(
                        "🏢",
                        "Centers",
                        "Visa centers",
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                showCenters();
                            }
                        }
                )
        );

        row2.addView(
                service(
                        "📋",
                        "Tracking",
                        "Track application",
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                showTracking();
                            }
                        }
                )
        );

        content.addView(row2);

        LinearLayout row3 =
                new LinearLayout(this);

        row3.addView(
                service(
                        "🌍",
                        "Countries",
                        "Choose country",
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                showCountries();
                            }
                        }
                )
        );

        row3.addView(
                service(
                        "📊",
                        "Statistics",
                        "Monitoring data",
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                showStatistics();
                            }
                        }
                )
        );

        content.addView(row3);

        // ---------------- SEARCH ----------------

        TextView search =
                text(
                        "⌕   Search services",
                        14,
                        GRAY
                );

        GradientDrawable searchBg =
                new GradientDrawable();

        searchBg.setColor(Color.WHITE);
        searchBg.setCornerRadius(dp(18));
        searchBg.setStroke(
                dp(1),
                Color.rgb(225, 230, 240)
        );

        search.setBackground(searchBg);
        search.setPadding(
                dp(18),
                0,
                dp(18),
                0
        );

        content.addView(
                search,
                new LinearLayout.LayoutParams(
                        -1,
                        dp(50)
                )
        );

        // ---------------- OFFICIAL BLS ----------------

        LinearLayout official =
                card();

        TextView officialTitle =
                text(
                        "Official BLS Spain",
                        15,
                        NAVY
                );

        officialTitle.setTypeface(
                Typeface.DEFAULT_BOLD
        );

        official.addView(officialTitle);

        official.addView(
                text(
                        "Official information and visa services",
                        12,
                        GRAY
                ),
                margin(0, 4, 0, 8)
        );

        Button open =
                smallButton(
                        "OPEN OFFICIAL WEBSITE"
                );

        open.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        try {

                            android.content.Intent intent =
                                    new android.content.Intent(
                                            android.content.Intent.ACTION_VIEW,
                                            android.net.Uri.parse(
                                                    "https://algeria.blsspainvisa.com/"
                                            )
                                    );

                            startActivity(intent);

                        } catch (Exception ignored) {
                        }
                    }
                }
        );

        official.addView(open);

        content.addView(
                official,
                margin(0, 10, 0, 5)
        );

        // ---------------- BOTTOM NAVIGATION ----------------

        LinearLayout bottom =
                new LinearLayout(this);

        bottom.setGravity(
                Gravity.CENTER
        );

        bottom.setBackgroundColor(
                Color.WHITE
        );

        bottom.setPadding(
                dp(5),
                dp(4),
                dp(5),
                dp(5)
        );

        bottom.addView(
                nav(
                        "⌂",
                        "Home",
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                showHome();
                            }
                        }
                )
        );

        bottom.addView(
                nav(
                        "▣",
                        "Appointments",
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                showAppointments();
                            }
                        }
                )
        );

        bottom.addView(
                nav(
                        "●",
                        "Alerts",
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                showAlerts();
                            }
                        }
                )
        );

        bottom.addView(
                nav(
                        "⚙",
                        "Settings",
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                showSettings();
                            }
                        }
                )
        );

        root.addView(
                bottom,
                new LinearLayout.LayoutParams(
                        -1,
                        dp(64)
                )
        );
    }

    // =========================================================
    // SERVICE CARD
    // =========================================================

    private LinearLayout service(
            String icon,
            String title,
            String subtitle,
            View.OnClickListener listener
    ) {

        LinearLayout card =
                new LinearLayout(this);

        card.setOrientation(
                LinearLayout.VERTICAL
        );

        card.setGravity(
                Gravity.CENTER
        );

        card.setPadding(
                dp(8),
                dp(10),
                dp(8),
                dp(8)
        );

        GradientDrawable bg =
                new GradientDrawable();

        bg.setColor(Color.WHITE);
        bg.setCornerRadius(dp(19));
        bg.setStroke(
                dp(1),
                Color.rgb(226, 231, 240)
        );

        card.setBackground(bg);

        TextView i =
                text(
                        icon,
                        25,
                        NAVY
                );

        i.setGravity(Gravity.CENTER);

        card.addView(i);

        TextView t =
                text(
                        title,
                        14,
                        NAVY
                );

        t.setTypeface(
                Typeface.DEFAULT_BOLD
        );

        t.setGravity(Gravity.CENTER);

        card.addView(t);

        TextView s =
                text(
                        subtitle,
                        10,
                        GRAY
                );

        s.setGravity(Gravity.CENTER);

        card.addView(s);

        card.setOnClickListener(listener);

        LinearLayout.LayoutParams p =
                new LinearLayout.LayoutParams(
                        0,
                        dp(112),
                        1
                );

        p.setMargins(
                dp(5),
                dp(5),
                dp(5),
                dp(5)
        );

        card.setLayoutParams(p);

        return card;
    }

    // =========================================================
    // NAVIGATION ITEM
    // =========================================================

    private LinearLayout nav(
            String icon,
            String name,
            View.OnClickListener listener
    ) {

        LinearLayout item =
                new LinearLayout(this);

        item.setOrientation(
                LinearLayout.VERTICAL
        );

        item.setGravity(
                Gravity.CENTER
        );

        TextView i =
                text(
                        icon,
                        20,
                        NAVY
                );

        i.setGravity(Gravity.CENTER);

        TextView n =
                text(
                        name,
                        10,
                        GRAY
                );

        n.setGravity(Gravity.CENTER);

        item.addView(i);
        item.addView(n);

        item.setOnClickListener(listener);

        item.setLayoutParams(
                new LinearLayout.LayoutParams(
                        0,
                        -1,
                        1
                )
        );

        return item;
    }

    // =========================================================
    // PAGES
    // =========================================================

    private void showAppointments() {
        page(
                "Appointments",
                "Find and manage your visa appointments.",
                new String[]{
                        "🇪🇸 Spain",
                        "Algiers Visa Center",
                        "Oran Visa Center",
                        "Annaba Visa Center"
                }
        );
    }

    private void showAlerts() {
        page(
                "Alerts",
                "Appointment availability notifications.",
                new String[]{
                        "🔔 Availability alerts",
                        "● Monitoring active",
                        "⏱ Every 2 minutes",
                        "Notifications enabled"
                }
        );
    }

    private void showCenters() {
        page(
                "Visa Centers",
                "Choose your preferred visa center.",
                new String[]{
                        "Algiers",
                        "Oran",
                        "Annaba"
                }
        );
    }

    private void showTracking() {
        page(
                "Application Tracking",
                "Track the status of your visa application.",
                new String[]{
                        "Application number",
                        "Passport number",
                        "Check application status"
                }
        );
    }

    private void showCountries() {
        page(
                "Countries",
                "Choose a country for visa services.",
                new String[]{
                        "🇪🇸 Spain",
                        "🇫🇷 France",
                        "🇮🇹 Italy",
                        "🇩🇪 Germany",
                        "🇵🇹 Portugal"
                }
        );
    }

    private void showStatistics() {
        page(
                "Statistics",
                "Your appointment monitoring activity.",
                new String[]{
                        "Checks today: 0",
                        "Appointments detected: 0",
                        "Monitoring status: Active"
                }
        );
    }

    private void showSettings() {
        page(
                "Settings",
                "Customize BLS Rendez-Vous.",
                new String[]{
                        "🌐 Language",
                        "🎨 Theme",
                        "🌍 Country",
                        "🏢 Visa center",
                        "⏱ Monitoring interval",
                        "🔔 Notifications"
                }
        );
    }

    // =========================================================
    // SIMPLE PAGE
    // =========================================================

    private void page(
            String title,
            String subtitle,
            String[] items
    ) {

        root.removeAllViews();

        LinearLayout page =
                new LinearLayout(this);

        page.setOrientation(
                LinearLayout.VERTICAL
        );

        page.setPadding(
                dp(20),
                dp(25),
                dp(20),
                dp(20)
        );

        GradientDrawable bg =
                new GradientDrawable(
                        GradientDrawable.Orientation.TL_BR,
                        new int[]{
                                Color.rgb(245,248,255),
                                Color.rgb(238,244,255),
                                Color.rgb(247,243,252)
                        }
                );

        page.setBackground(bg);

        root.addView(page);

        TextView back =
                text(
                        "‹  Back",
                        17,
                        BLUE
                );

        back.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showHome();
                    }
                }
        );

        page.addView(
                back,
                margin(0,0,0,15)
        );

        TextView titleView =
                text(
                        title,
                        28,
                        NAVY
                );

        titleView.setTypeface(
                Typeface.DEFAULT_BOLD
        );

        page.addView(titleView);

        page.addView(
                text(
                        subtitle,
                        13,
                        GRAY
                ),
                margin(0,5,0,18)
        );

        for (String item : items) {

            LinearLayout c = card();

            c.addView(
                    text(
                            item,
                            15,
                            NAVY
                    )
            );

            page.addView(
                    c,
                    margin(0,5,0,5)
            );
        }
    }

    // =========================================================
    // CARD
    // =========================================================

    private LinearLayout card() {

        LinearLayout c =
                new LinearLayout(this);

        c.setOrientation(
                LinearLayout.VERTICAL
        );

        c.setPadding(
                dp(16),
                dp(14),
                dp(16),
                dp(14)
        );

        GradientDrawable bg =
                new GradientDrawable();

        bg.setColor(WHITE);
        bg.setCornerRadius(dp(19));
        bg.setStroke(
                dp(1),
                Color.rgb(225,230,240)
        );

        c.setBackground(bg);

        return c;
    }

    // =========================================================
    // BUTTON
    // =========================================================

    private Button smallButton(String label) {

        Button b =
                new Button(this);

        b.setText(label);
        b.setTextSize(10);
        b.setTextColor(WHITE);

        GradientDrawable bg =
                new GradientDrawable();

        bg.setColor(BLUE);
        bg.setCornerRadius(dp(15));

        b.setBackground(bg);

        return b;
    }

    // =========================================================
    // TEXT
    // =========================================================

    private TextView text(
            String value,
            float size,
            int color
    ) {

        TextView t =
                new TextView(this);

        t.setText(value);
        t.setTextSize(size);
        t.setTextColor(color);

        return t;
    }

    // =========================================================
    // MARGIN
    // =========================================================

    private LinearLayout.LayoutParams margin(
            int left,
            int top,
            int right,
            int bottom
    ) {

        LinearLayout.LayoutParams p =
                new LinearLayout.LayoutParams(
                        -1,
                        -2
                );

        p.setMargins(
                dp(left),
                dp(top),
                dp(right),
                dp(bottom)
        );

        return p;
    }

    // =========================================================
    // DP
    // =========================================================

    private int dp(int value) {

        return (int)(
                value *
                getResources()
                        .getDisplayMetrics()
                        .density
                + 0.5f
        );
    }

    @Override
    private void showSplash() {
    ...
    }
