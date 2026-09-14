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

        final LinearLayout splash = new LinearLayout(this);
        splash.setOrientation(LinearLayout.VERTICAL);
        splash.setGravity(Gravity.CENTER);

        GradientDrawable background =
                new GradientDrawable(
                        GradientDrawable.Orientation.TL_BR,
                        new int[]{
                                Color.rgb(45, 25, 110),
                                Color.rgb(55, 75, 165),
                                Color.rgb(35, 145, 185),
                                Color.rgb(25, 190, 170)
                        }
                );

        splash.setBackground(background);

        final LinearLayout logoContainer =
                new LinearLayout(this);

        logoContainer.setOrientation(
                LinearLayout.VERTICAL
        );

        logoContainer.setGravity(
                Gravity.CENTER
        );

        final TextView bls =
                new TextView(this);

        bls.setText("BLS");
        bls.setTextSize(68);
        bls.setTextColor(Color.WHITE);
        bls.setTypeface(
                Typeface.DEFAULT,
                Typeface.BOLD
        );
        bls.setGravity(Gravity.CENTER);

        logoContainer.addView(
                bls,
                new LinearLayout.LayoutParams(
                        -1,
                        -2
                )
        );

        final TextView international =
                new TextView(this);

        international.setText("international");
        international.setTextSize(16);
        international.setTextColor(
                Color.rgb(235, 245, 255)
        );
        international.setGravity(Gravity.CENTER);

        LinearLayout.LayoutParams internationalParams =
                new LinearLayout.LayoutParams(
                        -1,
                        -2
                );

        internationalParams.topMargin = dp(-2);

        logoContainer.addView(
                international,
                internationalParams
        );

        splash.addView(
                logoContainer,
                new LinearLayout.LayoutParams(
                        -1,
                        -2
                )
        );

        final TextView light =
                new TextView(this);

        light.setText("━━━━━━━━━━━━━━━━");
        light.setTextSize(5);
        light.setTextColor(Color.WHITE);
        light.setGravity(Gravity.CENTER);
        light.setAlpha(0f);

        LinearLayout.LayoutParams lightParams =
                new LinearLayout.LayoutParams(
                        dp(190),
                        dp(20)
                );

        lightParams.gravity = Gravity.CENTER;

        splash.addView(
                light,
                lightParams
        );

        setContentView(splash);

        logoContainer.setAlpha(0f);
        logoContainer.setScaleX(0.94f);
        logoContainer.setScaleY(0.94f);

        logoContainer.animate()
                .alpha(1f)
                .scaleX(1f)
                .scaleY(1f)
                .setDuration(700)
                .start();

        light.setTranslationX(-dp(150));
        light.setTranslationY(-dp(38));

        light.animate()
                .alpha(0.9f)
                .translationX(dp(150))
                .setDuration(850)
                .setStartDelay(450)
                .withEndAction(
                        new Runnable() {
                            @Override
                            public void run() {

                                light.animate()
                                        .alpha(0f)
                                        .setDuration(300)
                                        .start();
                            }
                        }
                )
                .start();

        new Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {

                        splash.animate()
                                .alpha(0f)
                                .setDuration(450)
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
                1800
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
    // APPOINTMENTS
    // =========================================================

    private void showAppointments() {

        root.removeAllViews();

        LinearLayout page =
                new LinearLayout(this);

        page.setOrientation(
                LinearLayout.VERTICAL
        );

        GradientDrawable background =
                new GradientDrawable(
                        GradientDrawable.Orientation.TL_BR,
                        new int[]{
                                Color.rgb(245, 248, 255),
                                Color.rgb(238, 244, 255),
                                Color.rgb(247, 243, 252)
                        }
                );

        page.setBackground(background);

        root.addView(page);

        LinearLayout header =
                new LinearLayout(this);

        header.setOrientation(
                LinearLayout.VERTICAL
        );

        header.setPadding(
                dp(20),
                dp(22),
                dp(20),
                dp(10)
        );

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

        header.addView(back);

        TextView title =
                text(
                        "Appointments",
                        29,
                        NAVY
                );

        title.setTypeface(
                Typeface.DEFAULT_BOLD
        );

        header.addView(
                title,
                margin(0, 10, 0, 3)
        );

        header.addView(
                text(
                        "Find the appointment that matches your travel plan.",
                        13,
                        GRAY
                )
        );

        page.addView(header);

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
                dp(25)
        );

        scroll.addView(content);

        page.addView(
                scroll,
                new LinearLayout.LayoutParams(
                        -1,
                        0,
                        1
                )
        );

        // COUNTRY

        content.addView(
                appointmentSectionTitle(
                        "COUNTRY"
                ),
                margin(0, 5, 0, 5)
        );

        content.addView(
                appointmentChoice(
                        "🇪🇸",
                        "Spain",
                        "Visa appointment",
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                            }
                        }
                ),
                margin(0, 0, 0, 12)
        );

        // VISA TYPE

        content.addView(
                appointmentSectionTitle(
                        "VISA TYPE"
                ),
                margin(0, 3, 0, 5)
        );

        content.addView(
                appointmentChoice(
                        "📄",
                        "Short Stay — Tourism",
                        "Schengen visa",
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                            }
                        }
                ),
                margin(0, 0, 0, 12)
        );

        // CENTER

        content.addView(
                appointmentSectionTitle(
                        "VISA CENTER"
                ),
                margin(0, 3, 0, 5)
        );

        content.addView(
                appointmentChoice(
                        "🏢",
                        "Algiers Visa Center",
                        "Algiers",
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                            }
                        }
                ),
                margin(0, 0, 0, 12)
        );

        // APPLICANTS

        content.addView(
                appointmentSectionTitle(
                        "APPLICANTS"
                ),
                margin(0, 3, 0, 5)
        );

        content.addView(
                appointmentChoice(
                        "👤",
                        "1 Applicant",
                        "Number of people",
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                            }
                        }
                ),
                margin(0, 0, 0, 12)
        );

        // TRAVEL DATE

        content.addView(
                appointmentSectionTitle(
                        "TRAVEL PLAN"
                ),
                margin(0, 3, 0, 5)
        );

        content.addView(
                appointmentChoice(
                        "📅",
                        "15 November 2026",
                        "Planned departure date",
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                            }
                        }
                ),
                margin(0, 0, 0, 15)
        );

        // SMART MATCH

        LinearLayout smart =
                appointmentBox();

        TextView smartTitle =
                text(
                        "✦  Smart Appointment Match",
                        16,
                        NAVY
                );

        smartTitle.setTypeface(
                Typeface.DEFAULT_BOLD
        );

        smart.addView(smartTitle);

        smart.addView(
                text(
                        "We will look for appointments that fit your selected center and travel plan.",
                        12,
                        GRAY
                ),
                margin(0, 6, 0, 0)
        );

        content.addView(
                smart,
                margin(0, 0, 0, 12)
        );

        // AVAILABILITY

        final LinearLayout status =
                appointmentBox();

        LinearLayout statusTop =
                new LinearLayout(this);

        statusTop.setGravity(
                Gravity.CENTER_VERTICAL
        );

        TextView statusTitle =
                text(
                        "Appointment availability",
                        15,
                        NAVY
                );

        statusTitle.setTypeface(
                Typeface.DEFAULT_BOLD
        );

        statusTop.addView(
                statusTitle,
                new LinearLayout.LayoutParams(
                        0,
                        -2,
                        1
                )
        );

        final TextView statusDot =
                text(
                        "● READY",
                        11,
                        GREEN
                );

        statusTop.addView(statusDot);

        status.addView(statusTop);

        status.addView(
                text(
                        "Your preferences are ready for the next availability check.",
                        12,
                        GRAY
                ),
                margin(0, 5, 0, 12)
        );

        final Button check =
                smallButton(
                        "CHECK AVAILABILITY"
                );

        check.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        statusDot.setText(
                                "● CHECKING"
                        );

                        statusDot.setTextColor(
                                BLUE
                        );

                        check.setText(
                                "CHECKING..."
                        );

                        check.setEnabled(false);

                        new Handler().postDelayed(
                                new Runnable() {
                                    @Override
                                    public void run() {

                                        statusDot.setText(
                                                "● NO RESULT YET"
                                        );

                                        statusDot.setTextColor(
                                                GRAY
                                        );

                                        check.setText(
                                                "CHECK AGAIN"
                                        );

                                        check.setEnabled(true);
                                    }
                                },
                                1200
                        );
                    }
                }
        );

        status.addView(check);

        content.addView(
                status,
                margin(0, 0, 0, 12)
        );

        // MONITORING

        LinearLayout monitoring =
                appointmentBox();

        TextView monitoringTitle =
                text(
                        "🔔 Appointment monitoring",
                        15,
                        NAVY
                );

        monitoringTitle.setTypeface(
                Typeface.DEFAULT_BOLD
        );

        monitoring.addView(
                monitoringTitle
        );

        monitoring.addView(
                text(
                        "Get notified when a suitable appointment becomes available.",
                        12,
                        GRAY
                ),
                margin(0, 5, 0, 10)
        );

        final Button monitorButton =
                smallButton(
                        "ENABLE MONITORING"
                );

        monitorButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        monitorButton.setText(
                                "MONITORING ENABLED"
                        );
                    }
                }
        );

        monitoring.addView(
                monitorButton
        );

        content.addView(
                monitoring,
                margin(0, 0, 0, 5)
        );
    }

    // =========================================================
    // APPOINTMENT SECTION TITLE
    // =========================================================

    private TextView appointmentSectionTitle(
            String value
    ) {

        TextView t =
                text(
                        value,
                        10,
                        GRAY
                );

        t.setTypeface(
                Typeface.DEFAULT_BOLD
        );

        return t;
    }

    // =========================================================
    // APPOINTMENT CHOICE
    // =========================================================

    private LinearLayout appointmentChoice(
            String icon,
            String title,
            String subtitle,
            View.OnClickListener listener
    ) {

        LinearLayout card =
                new LinearLayout(this);

        card.setGravity(
                Gravity.CENTER_VERTICAL
        );

        card.setPadding(
                dp(15),
                dp(13),
                dp(15),
                dp(13)
        );

        GradientDrawable bg =
                new GradientDrawable();

        bg.setColor(Color.WHITE);
        bg.setCornerRadius(dp(19));
        bg.setStroke(
                dp(1),
                Color.rgb(225, 230, 240)
        );

        card.setBackground(bg);

        TextView iconView =
                text(
                        icon,
                        23,
                        NAVY
                );

        iconView.setGravity(
                Gravity.CENTER
        );

        card.addView(
                iconView,
                new LinearLayout.LayoutParams(
                        dp(45),
                        dp(45)
                )
        );

        LinearLayout info =
                new LinearLayout(this);

        info.setOrientation(
                LinearLayout.VERTICAL
        );

        TextView titleView =
                text(
                        title,
                        15,
                        NAVY
                );

        titleView.setTypeface(
                Typeface.DEFAULT_BOLD
        );

        TextView subtitleView =
                text(
                        subtitle,
                        11,
                        GRAY
                );

        info.addView(titleView);

        info.addView(
                subtitleView,
                margin(0, 3, 0, 0)
        );

        LinearLayout.LayoutParams infoParams =
                new LinearLayout.LayoutParams(
                        0,
                        -2,
                        1
                );

        infoParams.leftMargin =
                dp(10);

        card.addView(
                info,
                infoParams
        );

        TextView arrow =
                text(
                        "›",
                        25,
                        GRAY
                );

        arrow.setGravity(
                Gravity.CENTER
        );

        card.addView(
                arrow,
                new LinearLayout.LayoutParams(
                        dp(30),
                        dp(45)
                )
        );

        card.setOnClickListener(
                listener
        );

        return card;
    }

    // =========================================================
    // APPOINTMENT BOX
    // =========================================================

    private LinearLayout appointmentBox() {

        LinearLayout box =
                new LinearLayout(this);

        box.setOrientation(
                LinearLayout.VERTICAL
        );

        box.setPadding(
                dp(17),
                dp(16),
                dp(17),
                dp(16)
        );

        GradientDrawable bg =
                new GradientDrawable();

        bg.setColor(Color.WHITE);
        bg.setCornerRadius(dp(20));
        bg.setStroke(
                dp(1),
                Color.rgb(225, 230, 240)
        );

        box.setBackground(bg);

        return box;
    }

    // =========================================================
    // OTHER PAGES
    // =========================================================

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
    // NAVIGATION
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
    public void onBackPressed() {
        showHome();
    }
}
