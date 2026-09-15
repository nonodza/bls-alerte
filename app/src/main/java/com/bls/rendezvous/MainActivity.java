package com.bls.rendezvous;

import android.app.Activity;
import android.app.AlertDialog;
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

    // Appointment data
    private String selectedWilaya = "";
    private String selectedCenter = "";
    private String selectedCategory = "";

    // Appointment page references
    private TextView appointmentWilayaValue;
    private TextView appointmentWilayaHint;
    private TextView appointmentCenterValue;
    private TextView appointmentCenterHint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window window = getWindow();
        window.setStatusBarColor(Color.rgb(75, 70, 120));
        window.setNavigationBarColor(Color.WHITE);

        showSplash();
    }

    // =========================================================
    // SPLASH
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

        final LinearLayout logoContainer = new LinearLayout(this);
        logoContainer.setOrientation(LinearLayout.VERTICAL);
        logoContainer.setGravity(Gravity.CENTER);

        final TextView bls = new TextView(this);
        bls.setText("BLS");
        bls.setTextSize(68);
        bls.setTextColor(Color.WHITE);
        bls.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        bls.setGravity(Gravity.CENTER);

        logoContainer.addView(
                bls,
                new LinearLayout.LayoutParams(-1, -2)
        );

        final TextView international = new TextView(this);
        international.setText("international");
        international.setTextSize(16);
        international.setTextColor(Color.rgb(235, 245, 255));
        international.setGravity(Gravity.CENTER);

        LinearLayout.LayoutParams internationalParams =
                new LinearLayout.LayoutParams(-1, -2);

        internationalParams.topMargin = dp(-2);

        logoContainer.addView(
                international,
                internationalParams
        );

        splash.addView(
                logoContainer,
                new LinearLayout.LayoutParams(-1, -2)
        );

        final TextView light = new TextView(this);
        light.setText("━━━━━━━━━━━━━━━━");
        light.setTextSize(5);
        light.setTextColor(Color.WHITE);
        light.setGravity(Gravity.CENTER);
        light.setAlpha(0f);

        LinearLayout.LayoutParams lightParams =
                new LinearLayout.LayoutParams(dp(190), dp(20));

        lightParams.gravity = Gravity.CENTER;

        splash.addView(light, lightParams);

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
                .withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        light.animate()
                                .alpha(0f)
                                .setDuration(300)
                                .start();
                    }
                })
                .start();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                splash.animate()
                        .alpha(0f)
                        .setDuration(450)
                        .withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                showHome();
                            }
                        })
                        .start();
            }
        }, 1800);
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

        LinearLayout titles = new LinearLayout(this);
        titles.setOrientation(LinearLayout.VERTICAL);

        TextView appName =
                text(
                        "BLS Rendez-Vous",
                        24,
                        NAVY
                );

        appName.setTypeface(Typeface.DEFAULT_BOLD);

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

        settings.setGravity(Gravity.CENTER);

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

        ScrollView scroll = new ScrollView(this);
        scroll.setFillViewport(true);

        LinearLayout content = new LinearLayout(this);
        content.setOrientation(LinearLayout.VERTICAL);

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

        // =====================================================
        // CURRENT APPLICATION
        // =====================================================

        LinearLayout current = card();

        LinearLayout currentTop = new LinearLayout(this);
        currentTop.setGravity(Gravity.CENTER_VERTICAL);

        LinearLayout country = new LinearLayout(this);
        country.setOrientation(LinearLayout.VERTICAL);

        country.addView(
                text(
                        "CURRENT APPLICATION",
                        10,
                        GRAY
                )
        );

        TextView countryName =
                text(
                        "🇪🇸  Spain",
                        21,
                        NAVY
                );

        countryName.setTypeface(Typeface.DEFAULT_BOLD);

        country.addView(countryName);

        country.addView(
                text(
                        "Algiers Visa Center",
                        12,
                        GRAY
                )
        );

        currentTop.addView(
                country,
                new LinearLayout.LayoutParams(
                        0,
                        -2,
                        1
                )
        );

        currentTop.addView(
                text(
                        "● ACTIVE",
                        11,
                        GREEN
                )
        );

        current.addView(currentTop);

        current.addView(
                text(
                        "Monitoring every 2 minutes",
                        12,
                        GRAY
                ),
                margin(0, 12, 0, 0)
        );

        content.addView(
                current,
                margin(0, 5, 0, 10)
        );

        // =====================================================
        // MONITORING
        // =====================================================

        LinearLayout monitor = card();

        LinearLayout monitorRow = new LinearLayout(this);
        monitorRow.setGravity(Gravity.CENTER_VERTICAL);

        TextView monitorIcon =
                text(
                        "◉",
                        28,
                        BLUE
                );

        monitorIcon.setGravity(Gravity.CENTER);

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
                        "Monitoring is paused",
                        12,
                        GRAY
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
                smallButton("START");

        monitorButton.setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        String currentText =
                                monitorButton.getText()
                                        .toString();

                        // =========================
                        // START MONITORING
                        // =========================

                        if (currentText.equals("START")) {

                            monitorButton.setText("STOP");

                            monitorStatus.setText(
                                    "Monitoring is active"
                            );

                            monitorStatus.setTextColor(
                                    GREEN
                            );

                            monitorButton.setEnabled(false);

                            android.content.Intent serviceIntent =
                                    new android.content.Intent(
                                            MainActivity.this,
                                            AppointmentMonitoringService.class
                                    );

                            try {

                                if (android.os.Build.VERSION.SDK_INT >=
                                        android.os.Build.VERSION_CODES.O) {

                                    startForegroundService(
                                            serviceIntent
                                    );

                                } else {

                                    startService(
                                            serviceIntent
                                    );
                                }

                                android.widget.Toast.makeText(
                                        MainActivity.this,
                                        "Monitoring started",
                                        android.widget.Toast.LENGTH_SHORT
                                ).show();

                            } catch (Exception e) {

                                monitorButton.setText("START");

                                monitorStatus.setText(
                                        "Monitoring is paused"
                                );

                                monitorStatus.setTextColor(
                                        GRAY
                                );

                                android.widget.Toast.makeText(
                                        MainActivity.this,
                                        "Monitoring could not start",
                                        android.widget.Toast.LENGTH_LONG
                                ).show();
                            }

                            monitorButton.setEnabled(true);

                        }

                        // =========================
                        // STOP MONITORING
                        // =========================

                        else {

                            android.content.Intent serviceIntent =
                                    new android.content.Intent(
                                            MainActivity.this,
                                            AppointmentMonitoringService.class
                                    );

                            stopService(serviceIntent);

                            monitorButton.setText("START");

                            monitorStatus.setText(
                                    "Monitoring is paused"
                            );

                            monitorStatus.setTextColor(
                                    GRAY
                            );

                            android.widget.Toast.makeText(
                                    MainActivity.this,
                                    "Monitoring stopped",
                                    android.widget.Toast.LENGTH_SHORT
                            ).show();
                        }
                    }
                }
        );

        monitorRow.addView(monitorButton);

        monitor.addView(monitorRow);

        content.addView(
                monitor,
                margin(0, 0, 0, 10)
        );

        // =====================================================
        // SERVICES
        // =====================================================

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

        LinearLayout row1 = new LinearLayout(this);

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

        LinearLayout row2 = new LinearLayout(this);

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

        LinearLayout row3 = new LinearLayout(this);

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

        // =====================================================
        // SEARCH
        // =====================================================

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

        // =====================================================
        // OFFICIAL BLS
        // =====================================================

        LinearLayout official = card();

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

        // =====================================================
        // BOTTOM NAVIGATION
        // =====================================================

        LinearLayout bottom =
                new LinearLayout(this);

        bottom.setGravity(Gravity.CENTER);
        bottom.setBackgroundColor(Color.WHITE);

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

        ScrollView scroll =
                new ScrollView(this);

        LinearLayout page =
                new LinearLayout(this);

        page.setOrientation(
                LinearLayout.VERTICAL
        );

        page.setPadding(
                dp(20),
                dp(20),
                dp(20),
                dp(25)
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

        scroll.addView(page);

        root.addView(
                scroll,
                new LinearLayout.LayoutParams(
                        -1,
                        0,
                        1
                )
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

        page.addView(
                back,
                margin(0,0,0,10)
        );

        TextView title =
                text(
                        "Appointments",
                        28,
                        NAVY
                );

        title.setTypeface(
                Typeface.DEFAULT_BOLD
        );

        page.addView(title);

        page.addView(
                text(
                        "Find the appointment that matches your travel plan.",
                        13,
                        GRAY
                ),
                margin(0,5,0,18)
        );

        // Country

        LinearLayout countryCard =
                appointmentCard();

        countryCard.addView(
                text(
                        "COUNTRY",
                        10,
                        GRAY
                )
        );

        TextView countryValue =
                text(
                        "🇪🇸  Spain",
                        18,
                        NAVY
                );

        countryValue.setTypeface(
                Typeface.DEFAULT_BOLD
        );

        countryCard.addView(
                countryValue,
                margin(0,5,0,0)
        );

        countryCard.addView(
                text(
                        "Visa appointment",
                        11,
                        GRAY
                ),
                margin(0,2,0,0)
        );

        page.addView(
                countryCard,
                margin(0,0,0,8)
        );

        // Residence

        LinearLayout residenceCard =
                appointmentCard();

        residenceCard.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showWilayaSelector();
                    }
                }
        );

        residenceCard.addView(
                text(
                        "YOUR RESIDENCE",
                        10,
                        GRAY
                )
        );

        appointmentWilayaValue =
                text(
                        selectedWilaya.length() == 0
                                ? "Select your wilaya"
                                : selectedWilaya,
                        17,
                        NAVY
                );

        appointmentWilayaValue.setTypeface(
                Typeface.DEFAULT_BOLD
        );

        residenceCard.addView(
                appointmentWilayaValue,
                margin(0,5,0,0)
        );

        appointmentWilayaHint =
                text(
                        selectedWilaya.length() == 0
                                ? "Required to determine your BLS center"
                                : "Residence selected",
                        11,
                        GRAY
                );

        residenceCard.addView(
                appointmentWilayaHint,
                margin(0,2,0,0)
        );

        page.addView(
                residenceCard,
                margin(0,0,0,8)
        );

        // Center

        LinearLayout centerCard =
                appointmentCard();

        centerCard.addView(
                text(
                        "VISA CENTER",
                        10,
                        GRAY
                )
        );

        appointmentCenterValue =
                text(
                        selectedCenter.length() == 0
                                ? "Waiting for residence"
                                : selectedCenter + " Visa Center",
                        17,
                        NAVY
                );

        appointmentCenterValue.setTypeface(
                Typeface.DEFAULT_BOLD
        );

        centerCard.addView(
                appointmentCenterValue,
                margin(0,5,0,0)
        );

        appointmentCenterHint =
                text(
                        selectedCenter.length() == 0
                                ? "Select your wilaya first"
                                : "Based on your residence",
                        11,
                        selectedCenter.length() == 0
                                ? GRAY
                                : GREEN
                );

        centerCard.addView(
                appointmentCenterHint,
                margin(0,2,0,0)
        );

        page.addView(
                centerCard,
                margin(0,0,0,8)
        );

        // Visa Type

        LinearLayout visaCard =
                appointmentCard();

        visaCard.addView(
                text(
                        "VISA TYPE",
                        10,
                        GRAY
                )
        );

        TextView visaValue =
                text(
                        "Short Stay - Tourism",
                        17,
                        NAVY
                );

        visaValue.setTypeface(
                Typeface.DEFAULT_BOLD
        );

        visaCard.addView(
                visaValue,
                margin(0,5,0,0)
        );

        visaCard.addView(
                text(
                        "Schengen visa",
                        11,
                        GRAY
                ),
                margin(0,2,0,0)
        );

        visaCard.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showVisaTypePage();
                    }
                }
        );

        page.addView(
                visaCard,
                margin(0,0,0,8)
        );

        // Applicants

        LinearLayout applicants =
                appointmentCard();

        applicants.addView(
                text(
                        "APPLICANTS",
                        10,
                        GRAY
                )
        );

        TextView applicantsValue =
                text(
                        "1 Applicant",
                        17,
                        NAVY
                );

        applicantsValue.setTypeface(
                Typeface.DEFAULT_BOLD
        );

        applicants.addView(
                applicantsValue,
                margin(0,5,0,0)
        );

        applicants.addView(
                text(
                        "Number of people",
                        11,
                        GRAY
                ),
                margin(0,2,0,0)
        );

        applicants.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        final String[] options = {
                                "1 Applicant",
                                "2 Applicants",
                                "3 Applicants",
                                "4 Applicants",
                                "5 Applicants"
                        };

                        AlertDialog.Builder builder =
                                new AlertDialog.Builder(
                                        MainActivity.this
                                );

                        builder.setTitle(
                                "Number of Applicants"
                        );

                        builder.setItems(
                                options,
                                new android.content.DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(
                                            android.content.DialogInterface dialog,
                                            int which
                                    ) {

                                        applicantsValue.setText(
                                                options[which]
                                        );
                                    }
                                }
                        );

                        builder.setNegativeButton(
                                "CANCEL",
                                null
                        );

                        builder.show();
                    }
                }
        );

        page.addView(
                applicants,
                margin(0,0,0,8)
        );

        // Travel plan

        LinearLayout travel =
                appointmentCard();

        travel.addView(
                text(
                        "TRAVEL PLAN",
                        10,
                        GRAY
                )
        );

        TextView travelValue =
                text(
                        "15 November 2026",
                        17,
                        NAVY
                );

        travelValue.setTypeface(
                Typeface.DEFAULT_BOLD
        );

        travel.addView(
                travelValue,
                margin(0,5,0,0)
        );

        travel.addView(
                text(
                        "Planned departure date",
                        11,
                        GRAY
                ),
                margin(0,2,0,0)
        );

        travel.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        final String[] dates = {
                                "15 November 2026",
                                "16 November 2026",
                                "17 November 2026",
                                "18 November 2026",
                                "19 November 2026",
                                "20 November 2026",
                                "21 November 2026"
                        };

                        AlertDialog.Builder builder =
                                new AlertDialog.Builder(
                                        MainActivity.this
                                );

                        builder.setTitle(
                                "Planned Departure Date"
                        );

                        builder.setItems(
                                dates,
                                new android.content.DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(
                                            android.content.DialogInterface dialog,
                                            int which
                                    ) {

                                        travelValue.setText(
                                                dates[which]
                                        );
                                    }
                                }
                        );

                        builder.setNegativeButton(
                                "CANCEL",
                                null
                        );

                        builder.show();
                    }
                }
        );

        page.addView(
                travel,
                margin(0,0,0,12)
        );

        // Continue

        Button continueButton =
                smallButton(
                        "CONTINUE TO VISA TYPE"
                );

        continueButton.setTextSize(11);

        continueButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (selectedWilaya.length() == 0) {

                            new AlertDialog.Builder(
                                    MainActivity.this
                            )
                                    .setTitle(
                                            "Residence required"
                                    )
                                    .setMessage(
                                            "Please select your Wilaya of residence first."
                                    )
                                    .setPositiveButton(
                                            "OK",
                                            null
                                    )
                                    .show();

                            return;
                        }

                        showVisaTypePage();
                    }
                }
        );

        page.addView(
                continueButton,
                new LinearLayout.LayoutParams(
                        -1,
                        dp(48)
                )
        );

        // Smart Match

        LinearLayout smart =
                card();

        TextView smartTitle =
                text(
                        "Smart Appointment Match",
                        15,
                        NAVY
                );

        smartTitle.setTypeface(
                Typeface.DEFAULT_BOLD
        );

        smart.addView(smartTitle);

        smart.addView(
                text(
                        "We will use your residence, center, visa type, " +
                        "applicant details and travel plan to determine " +
                        "the correct appointment path.",
                        12,
                        GRAY
                ),
                margin(0,5,0,0)
        );

        page.addView(
                smart,
                margin(0,12,0,10)
        );

        // Availability

        LinearLayout availability =
                card();

        TextView availabilityTitle =
                text(
                        "Appointment Availability",
                        15,
                        NAVY
                );

        availabilityTitle.setTypeface(
                Typeface.DEFAULT_BOLD
        );

        availability.addView(
                availabilityTitle
        );

        availability.addView(
                text(
                        "No Result Yet",
                        13,
                        GRAY
                ),
                margin(0,5,0,0)
        );

        availability.addView(
                text(
                        "Your preferences are ready for the next availability check.",
                        11,
                        GRAY
                ),
                margin(0,3,0,8)
        );

        Button check =
                smallButton("CHECK AGAIN");

        availability.addView(check);

        page.addView(
                availability,
                margin(0,0,0,10)
        );

        // Monitoring

        LinearLayout monitoring =
                card();

        TextView monitoringTitle =
                text(
                        "Appointment Monitoring",
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
                margin(0,5,0,8)
        );

        Button monitoringButton =
                smallButton(
                        "MONITORING ENABLED"
                );

        monitoring.addView(
                monitoringButton
        );

        page.addView(
                monitoring,
                margin(0,0,0,5)
        );
    }

    // =========================================================
    // 69 WILAYAS
    // =========================================================

    private void showWilayaSelector() {

        final String[] wilayas = {

                "Adrar",
                "Chlef",
                "Laghouat",
                "Oum El Bouaghi",
                "Batna",
                "Bejaia",
                "Biskra",
                "Bechar",
                "Blida",
                "Bouira",
                "Tamanrasset",
                "Tebessa",
                "Tlemcen",
                "Tiaret",
                "Tizi-Ouzou",
                "Algiers",
                "Djelfa",
                "Jijel",
                "Setif",
                "Saida",
                "Skikda",
                "Sidi Bel Abbes",
                "Annaba",
                "Guelma",
                "Constantine",
                "Medea",
                "Mostaganem",
                "M'Sila",
                "Mascara",
                "Ouargla",
                "Oran",
                "El Bayadh",
                "Illizi",
                "Bordj Bou Arreridj",
                "Boumerdes",
                "El Tarf",
                "Tindouf",
                "Tissemsilt",
                "El Oued",
                "Khenchela",
                "Souk Ahras",
                "Tipaza",
                "Mila",
                "Ain Defla",
                "Naama",
                "Ain Temouchent",
                "Ghardaia",
                "Relizane",
                "Aflou",
                "Barika",
                "El Kantara",
                "Bir El Ater",
                "El Aricha",
                "Ksar Chellala",
                "Ain Oussara",
                "Messaad",
                "Ksar El Boukhari",
                "Bou Saada",
                "El Abiodh Sidi Cheikh"
        };

        final AlertDialog dialog =
                new AlertDialog.Builder(
                        MainActivity.this
                ).create();

        LinearLayout main =
                new LinearLayout(this);

        main.setOrientation(
                LinearLayout.VERTICAL
        );

        main.setPadding(
                dp(18),
                dp(15),
                dp(18),
                dp(10)
        );

        TextView title =
                text(
                        "Select your Wilaya",
                        21,
                        NAVY
                );

        title.setTypeface(
                Typeface.DEFAULT_BOLD
        );

        main.addView(title);

        ScrollView scroll =
                new ScrollView(this);

        LinearLayout list =
                new LinearLayout(this);

        list.setOrientation(
                LinearLayout.VERTICAL
        );

        for (int i = 0; i < wilayas.length; i++) {

            final String wilaya =
                    wilayas[i];

            TextView item =
                    text(
                            wilaya,
                            16,
                            NAVY
                    );

            item.setGravity(
                    Gravity.CENTER_VERTICAL
            );

            item.setPadding(
                    dp(15),
                    0,
                    dp(15),
                    0
            );

            LinearLayout.LayoutParams itemParams =
                    new LinearLayout.LayoutParams(
                            -1,
                            dp(52)
                    );

            itemParams.setMargins(
                    0,
                    dp(3),
                    0,
                    dp(3)
            );

            list.addView(
                    item,
                    itemParams
            );

            item.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            selectedWilaya =
                                    wilaya;

                            selectedCenter =
                                    getBlsCenter(
                                            selectedWilaya
                                    );

                            updateAppointmentSelection();

                            dialog.dismiss();
                        }
                    }
            );
        }

        scroll.addView(list);

        main.addView(
                scroll,
                new LinearLayout.LayoutParams(
                        -1,
                        dp(430)
                )
        );

        Button cancel =
                smallButton("CANCEL");

        cancel.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                }
        );

        main.addView(
                cancel,
                new LinearLayout.LayoutParams(
                        -1,
                        dp(48)
                )
        );

        dialog.setView(main);

        dialog.show();

        if (dialog.getWindow() != null) {

            dialog.getWindow().setBackgroundDrawable(
                    new android.graphics.drawable.ColorDrawable(
                            Color.WHITE
                    )
            );

            dialog.getWindow().setLayout(
                    (int)(
                            getResources()
                                    .getDisplayMetrics()
                                    .widthPixels * 0.92
                    ),
                    dp(560)
            );
        }
    }

    private int getSelectedWilayaIndex(
            String[] wilayas
    ) {

        if (selectedWilaya.length() == 0) {
            return -1;
        }

        for (int i = 0; i < wilayas.length; i++) {

            if (wilayas[i].equals(
                    selectedWilaya
            )) {
                return i;
            }
        }

        return -1;
    }

    // =========================================================
    // UPDATE APPOINTMENT SELECTION
    // =========================================================

    private void updateAppointmentSelection() {

        if (appointmentWilayaValue != null) {

            appointmentWilayaValue.setText(
                    selectedWilaya
            );
        }

        if (appointmentWilayaHint != null) {

            appointmentWilayaHint.setText(
                    "Residence selected"
            );

            appointmentWilayaHint.setTextColor(
                    GREEN
            );
        }

        if (appointmentCenterValue != null) {

            appointmentCenterValue.setText(
                    selectedCenter +
                    " Visa Center"
            );
        }

        if (appointmentCenterHint != null) {

            appointmentCenterHint.setText(
                    "Based on your residence"
            );

            appointmentCenterHint.setTextColor(
                    GREEN
            );
        }
    }

    // =========================================================
    // BLS CENTER JURISDICTION
    // =========================================================

    private String getBlsCenter(
            String wilaya
    ) {

        if (wilaya.equals("Adrar")
                || wilaya.equals("Chlef")
                || wilaya.equals("Bechar")
                || wilaya.equals("Tlemcen")
                || wilaya.equals("Tiaret")
                || wilaya.equals("Saida")
                || wilaya.equals("Sidi Bel Abbes")
                || wilaya.equals("Mostaganem")
                || wilaya.equals("Mascara")
                || wilaya.equals("Oran")
                || wilaya.equals("El Bayadh")
                || wilaya.equals("Tissemsilt")
                || wilaya.equals("Naama")
                || wilaya.equals("Ain Temouchent")
                || wilaya.equals("Relizane")) {

            return "Oran";
        }

        if (wilaya.equals("Aflou")) {
            return getBlsCenter("Laghouat");
        }

        if (wilaya.equals("Barika")) {
            return getBlsCenter("Batna");
        }

        if (wilaya.equals("El Kantara")) {
            return getBlsCenter("Biskra");
        }

        if (wilaya.equals("Bir El Ater")) {
            return getBlsCenter("Tebessa");
        }

        if (wilaya.equals("El Aricha")) {
            return getBlsCenter("Tlemcen");
        }

        if (wilaya.equals("Ksar Chellala")) {
            return getBlsCenter("Tiaret");
        }

        if (wilaya.equals("Ain Oussara")) {
            return getBlsCenter("Djelfa");
        }

        if (wilaya.equals("Messaad")) {
            return getBlsCenter("Djelfa");
        }

        if (wilaya.equals("Ksar El Boukhari")) {
            return getBlsCenter("Medea");
        }

        if (wilaya.equals("Bou Saada")) {
            return getBlsCenter("M'Sila");
        }

        if (wilaya.equals("El Abiodh Sidi Cheikh")) {
            return getBlsCenter("El Bayadh");
        }

        return "Algiers";
    }

    // =========================================================
    // VISA TYPE
    // =========================================================

    private void showVisaTypePage() {

        root.removeAllViews();

        ScrollView scroll =
                new ScrollView(this);

        LinearLayout page =
                new LinearLayout(this);

        page.setOrientation(
                LinearLayout.VERTICAL
        );

        page.setPadding(
                dp(20),
                dp(25),
                dp(20),
                dp(25)
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

        scroll.addView(page);

        root.addView(
                scroll,
                new LinearLayout.LayoutParams(
                        -1,
                        0,
                        1
                )
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
                        showAppointments();
                    }
                }
        );

        page.addView(
                back,
                margin(0,0,0,15)
        );

        TextView title =
                text(
                        "Visa Type",
                        28,
                        NAVY
                );

        title.setTypeface(
                Typeface.DEFAULT_BOLD
        );

        page.addView(title);

        page.addView(
                text(
                        "Select the purpose of your trip to Spain.",
                        13,
                        GRAY
                ),
                margin(0,5,0,18)
        );

        LinearLayout selected =
                card();

        selected.addView(
                text(
                        "SELECTED CENTER",
                        10,
                        GRAY
                )
        );

        TextView center =
                text(
                        selectedCenter +
                        " Visa Center",
                        18,
                        NAVY
                );

        center.setTypeface(
                Typeface.DEFAULT_BOLD
        );

        selected.addView(
                center,
                margin(0,5,0,0)
        );

        selected.addView(
                text(
                        selectedWilaya,
                        12,
                        GREEN
                ),
                margin(0,3,0,0)
        );

        page.addView(
                selected,
                margin(0,0,0,15)
        );

        LinearLayout country =
                appointmentCard();

        country.addView(
                text(
                        "COUNTRY",
                        10,
                        GRAY
                )
        );

        TextView countryTitle =
                text(
                        "Spain",
                        18,
                        NAVY
                );

        countryTitle.setTypeface(
                Typeface.DEFAULT_BOLD
        );

        country.addView(
                countryTitle,
                margin(0,5,0,0)
        );

        page.addView(
                country,
                margin(0,0,0,10)
        );

        LinearLayout tourism =
                appointmentCard();

        tourism.addView(
                text(
                        "VISA PURPOSE",
                        10,
                        GRAY
                )
        );

        TextView tourismTitle =
                text(
                        "Tourism",
                        18,
                        NAVY
                );

        tourismTitle.setTypeface(
                Typeface.DEFAULT_BOLD
        );

        tourism.addView(
                tourismTitle,
                margin(0,5,0,0)
        );

        tourism.addView(
                text(
                        "Short Stay • Schengen Visa",
                        12,
                        GRAY
                ),
                margin(0,2,0,0)
        );

        page.addView(
                tourism,
                margin(0,0,0,15)
        );

        TextView status =
                text(
                        "✓ Tourism selected",
                        13,
                        GREEN
                );

        status.setTypeface(
                Typeface.DEFAULT_BOLD
        );

        page.addView(
                status,
                margin(3,0,0,15)
        );

        LinearLayout info =
                card();

        TextView infoTitle =
                text(
                        "Next step",
                        15,
                        NAVY
                );

        infoTitle.setTypeface(
                Typeface.DEFAULT_BOLD
        );

        info.addView(infoTitle);

        info.addView(
                text(
                        "The next step will determine the correct " +
                        "appointment category according to your " +
                        "previous Spain visa.",
                        12,
                        GRAY
                ),
                margin(0,5,0,0)
        );

        page.addView(
                info,
                margin(0,0,0,15)
        );

        Button continueButton =
                smallButton(
                        "CONTINUE"
                );

        continueButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showPreviousSpainVisaPage();
                    }
                }
        );

        page.addView(
                continueButton,
                margin(0,5,0,10)
        );
    }

    // =========================================================
    // PREVIOUS SPAIN VISA
    // =========================================================

    private void showPreviousSpainVisaPage() {

        root.removeAllViews();

        ScrollView scroll =
                new ScrollView(this);

        LinearLayout layout =
                new LinearLayout(this);

        layout.setOrientation(
                LinearLayout.VERTICAL
        );

        layout.setPadding(
                dp(20),
                dp(20),
                dp(20),
                dp(30)
        );

        TextView back =
                text(
                        "< Back",
                        16,
                        BLUE
                );

        back.setPadding(
                0,
                0,
                0,
                dp(18)
        );

        back.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showVisaTypePage();
                    }
                }
        );

        layout.addView(back);

        TextView title =
                text(
                        "Previous Spain Visa",
                        26,
                        NAVY
                );

        title.setTypeface(
                Typeface.DEFAULT_BOLD
        );

        layout.addView(title);

        TextView subtitle =
                text(
                        "Tell us about your previous Spain Schengen visa.",
                        16,
                        GRAY
                );

        subtitle.setPadding(
                0,
                dp(8),
                0,
                dp(20)
        );

        layout.addView(subtitle);

        TextView question =
                text(
                        "Have you had a Spain Schengen visa since 1 January 2021?",
                        17,
                        NAVY
                );

        question.setTypeface(
                Typeface.DEFAULT_BOLD
        );

        question.setPadding(
                0,
                0,
                0,
                dp(20)
        );

        layout.addView(question);

        TextView yes =
                text(
                        "YES\n\nYes, I had a Spain visa\nIssued on or after 1 January 2021",
                        17,
                        NAVY
                );

        yes.setPadding(
                dp(20),
                dp(20),
                dp(20),
                dp(20)
        );

        yes.setBackgroundColor(LIGHT);

        yes.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showVisaValidityPage();
                    }
                }
        );

        layout.addView(
                yes,
                margin(0,0,0,15)
        );

        TextView no =
                text(
                        "NO\n\nNo, I did not have one\nNo Spain Schengen visa since 1 January 2021",
                        17,
                        NAVY
                );

        no.setPadding(
                dp(20),
                dp(20),
                dp(20),
                dp(20)
        );

        no.setBackgroundColor(LIGHT);

        no.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectAppointmentCategory("ALG1");
                    }
                }
        );

        layout.addView(no);

        TextView why =
                text(
                        "Why do we ask?\n\nBLS uses your previous Spain visa history to determine the appropriate appointment category.",
                        14,
                        GRAY
                );

        why.setPadding(
                0,
                dp(25),
                0,
                dp(20)
        );

        layout.addView(why);

        scroll.addView(layout);

        root.addView(scroll);
    }

    // =========================================================
    // SPAIN VISA VALIDITY
    // =========================================================

    private void showVisaValidityPage() {

        root.removeAllViews();

        ScrollView scroll =
                new ScrollView(this);

        LinearLayout layout =
                new LinearLayout(this);

        layout.setOrientation(
                LinearLayout.VERTICAL
        );

        layout.setPadding(
                dp(20),
                dp(20),
                dp(20),
                dp(30)
        );

        TextView back =
                text(
                        "‹  Back",
                        16,
                        BLUE
                );

        back.setPadding(
                0,
                0,
                0,
                dp(18)
        );

        back.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showPreviousSpainVisaPage();
                    }
                }
        );

        layout.addView(back);

        TextView title =
                text(
                        "Visa Validity",
                        26,
                        NAVY
                );

        title.setTypeface(
                Typeface.DEFAULT,
                Typeface.BOLD
        );

        layout.addView(title);

        TextView subtitle =
                text(
                        "How long was your last Spain Schengen visa valid?",
                        16,
                        GRAY
                );

        subtitle.setPadding(
                0,
                dp(8),
                0,
                dp(20)
        );

        layout.addView(subtitle);

        TextView alg2 =
                text(
                        "Less than 6 months",
                        18,
                        NAVY
                );

        alg2.setPadding(
                dp(20),
                dp(22),
                dp(20),
                dp(22)
        );

        alg2.setBackgroundColor(LIGHT);

        alg2.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectAppointmentCategory("ALG2");
                    }
                }
        );

        layout.addView(alg2);

        TextView alg3 =
                text(
                        "6 months to less than 2 years",
                        18,
                        NAVY
                );

        alg3.setPadding(
                dp(20),
                dp(22),
                dp(20),
                dp(22)
        );

        alg3.setBackgroundColor(LIGHT);

        alg3.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectAppointmentCategory("ALG3");
                    }
                }
        );

        layout.addView(alg3);

        TextView alg4 =
                text(
                        "2 years or more",
                        18,
                        NAVY
                );

        alg4.setPadding(
                dp(20),
                dp(22),
                dp(20),
                dp(22)
        );

        alg4.setBackgroundColor(LIGHT);

        alg4.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectAppointmentCategory("ALG4");
                    }
                }
        );

        layout.addView(alg4);

        TextView info =
                text(
                        "Your previous Spain visa validity determines the appropriate BLS appointment category.",
                        14,
                        GRAY
                );

        info.setPadding(
                0,
                dp(20),
                0,
                0
        );

        layout.addView(info);

        scroll.addView(layout);

        root.addView(scroll);
    }

    // =========================================================
    // APPOINTMENT CATEGORY
    // =========================================================

    private void selectAppointmentCategory(
            String category
    ) {

        selectedCategory = category;

        showAppointmentCategoryPage();
    }

    private void showAppointmentCategoryPage() {

        root.removeAllViews();

        ScrollView scroll =
                new ScrollView(this);

        LinearLayout page =
                new LinearLayout(this);

        page.setOrientation(
                LinearLayout.VERTICAL
        );

        page.setPadding(
                dp(20),
                dp(25),
                dp(20),
                dp(30)
        );

        TextView back =
                text(
                        "< Back",
                        16,
                        BLUE
                );

        back.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (selectedCategory.equals("ALG1")) {
                            showPreviousSpainVisaPage();
                        } else {
                            showVisaValidityPage();
                        }
                    }
                }
        );

        page.addView(
                back,
                margin(0,0,0,18)
        );

        TextView title =
                text(
                        "Appointment Category",
                        26,
                        NAVY
                );

        title.setTypeface(
                Typeface.DEFAULT_BOLD
        );

        page.addView(
                title,
                margin(0,0,0,8)
        );

        TextView selected =
                text(
                        selectedCategory,
                        28,
                        BLUE
                );

        selected.setGravity(
                Gravity.CENTER
        );

        selected.setTypeface(
                Typeface.DEFAULT_BOLD
        );

        page.addView(
                selected,
                margin(0,0,0,20)
        );

        String description;

        if (selectedCategory.equals("ALG1")) {

            description =
                    "No Spain Schengen visa since 1 January 2021.";

        } else if (selectedCategory.equals("ALG2")) {

            description =
                    "Spain Schengen visa issued since 1 January 2021 and valid for less than 6 months.";

        } else if (selectedCategory.equals("ALG3")) {

            description =
                    "Spain Schengen visa issued since 1 January 2021 and valid for 6 months to less than 2 years.";

        } else {

            description =
                    "Spain Schengen visa issued since 1 January 2021 and valid for 2 years or more.";
        }

        LinearLayout infoCard =
                card();

        TextView infoTitle =
                text(
                        "Selected category",
                        17,
                        NAVY
                );

        infoTitle.setTypeface(
                Typeface.DEFAULT_BOLD
        );

        infoCard.addView(infoTitle);

        infoCard.addView(
                text(
                        description,
                        15,
                        GRAY
                ),
                margin(0,5,0,0)
        );

        page.addView(
                infoCard,
                margin(0,0,0,20)
        );

        LinearLayout centerCard =
                card();

        centerCard.addView(
                text(
                        "Visa Center",
                        16,
                        GRAY
                )
        );

        centerCard.addView(
                text(
                        selectedCenter,
                        18,
                        NAVY
                ),
                margin(0,5,0,0)
        );

        page.addView(
                centerCard,
                margin(0,0,0,20)
        );

        TextView confirmation =
                text(
                        "✓ Appointment category selected",
                        15,
                        GREEN
                );

        confirmation.setTypeface(
                Typeface.DEFAULT_BOLD
        );

        page.addView(
                confirmation,
                margin(0,0,0,25)
        );

        Button continueButton =
                new Button(this);

        continueButton.setText(
                "CONTINUE"
        );

        continueButton.setTextSize(16);

        continueButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showAppointmentDetailsPage();
                    }
                }
        );

        page.addView(
                continueButton,
                margin(0,0,0,10)
        );

        scroll.addView(page);

        root.addView(scroll);
    }

    // =========================================================
    // APPOINTMENT DETAILS
    // =========================================================

    private void showAppointmentDetailsPage() {

        root.removeAllViews();

        ScrollView scroll =
                new ScrollView(this);

        LinearLayout page =
                new LinearLayout(this);

        page.setOrientation(
                LinearLayout.VERTICAL
        );

        page.setPadding(
                dp(20),
                dp(25),
                dp(20),
                dp(30)
        );

        TextView back =
                text(
                        "< Back",
                        16,
                        BLUE
                );

        back.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showAppointmentCategoryPage();
                    }
                }
        );

        page.addView(
                back,
                margin(0,0,0,18)
        );

        TextView title =
                text(
                        "Appointment Details",
                        26,
                        NAVY
                );

        title.setTypeface(
                Typeface.DEFAULT_BOLD
        );

        page.addView(
                title,
                margin(0,0,0,8)
        );

        TextView subtitle =
                text(
                        "Review your appointment information before continuing.",
                        16,
                        GRAY
                );

        page.addView(
                subtitle,
                margin(0,0,0,20)
        );

        LinearLayout categoryCard =
                card();

        categoryCard.addView(
                text(
                        "Appointment Category",
                        15,
                        GRAY
                )
        );

        TextView categoryValue =
                text(
                        selectedCategory,
                        20,
                        NAVY
                );

        categoryValue.setTypeface(
                Typeface.DEFAULT_BOLD
        );

        categoryCard.addView(
                categoryValue,
                margin(0,5,0,0)
        );

        page.addView(
                categoryCard,
                margin(0,0,0,15)
        );

        LinearLayout centerCard =
                card();

        centerCard.addView(
                text(
                        "Visa Center",
                        15,
                        GRAY
                )
        );

        TextView centerValue =
                text(
                        selectedCenter,
                        20,
                        NAVY
                );

        centerValue.setTypeface(
                Typeface.DEFAULT_BOLD
        );

        centerCard.addView(
                centerValue,
                margin(0,5,0,0)
        );

        page.addView(
                centerCard,
                margin(0,0,0,15)
        );

        LinearLayout residenceCard =
                card();

        residenceCard.addView(
                text(
                        "Residence",
                        15,
                        GRAY
                )
        );

        TextView residenceValue =
                text(
                        selectedWilaya,
                        20,
                        NAVY
                );

        residenceValue.setTypeface(
                Typeface.DEFAULT_BOLD
        );

        residenceCard.addView(
                residenceValue,
                margin(0,5,0,0)
        );

        page.addView(
                residenceCard,
                margin(0,0,0,20)
        );

        TextView info =
                text(
                        "✓ Your appointment information is ready.",
                        15,
                        GREEN
                );

        info.setTypeface(
                Typeface.DEFAULT_BOLD
        );

        page.addView(
                info,
                margin(0,0,0,25)
        );

        Button openBlsButton =
                new Button(this);

        openBlsButton.setText(
                "OPEN OFFICIAL BLS"
        );

        openBlsButton.setTextSize(16);

        openBlsButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        AlertDialog.Builder builder =
                                new AlertDialog.Builder(MainActivity.this);

                        builder.setTitle(
                                "Official BLS Spain"
                        );

                        builder.setMessage(
                                "You are about to open the official BLS Spain website for Algeria."
                        );

                        builder.setNegativeButton(
                                "CANCEL",
                                null
                        );

                        builder.setPositiveButton(
                                "OPEN",
                                new android.content.DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(
                                            android.content.DialogInterface dialog,
                                            int which
                                    ) {

                                        android.content.Intent intent =
                                                new android.content.Intent(
                                                        android.content.Intent.ACTION_VIEW
                                                );

                                        intent.setData(
                                                android.net.Uri.parse(
                                                        "https://algeria.blsspainvisa.com/"
                                                )
                                        );

                                        startActivity(intent);
                                    }
                                }
                        );

                        builder.show();
                    }
                }
        );

        page.addView(
                openBlsButton,
                margin(0,0,0,10)
        );

        scroll.addView(page);

        root.addView(scroll);
    }

    // =========================================================
    // ORIGINAL SERVICE PAGES
    // =========================================================

    private void showAlerts() {

        page(
                "Alerts",
                "Manage your appointment availability alerts.",
                new String[]{
                        "Availability Alerts",
                        "Appointment notifications",
                        "Monitoring status"
                }
        );
    }

    private void showCenters() {

        page(
                "Visa Centers",
                "BLS Spain visa centers in Algeria.",
                new String[]{
                        "Algiers Visa Center",
                        "Oran Visa Center"
                }
        );
    }

    private void showTracking() {

        page(
                "Application Tracking",
                "Track your BLS Spain visa application.",
                new String[]{
                        "Application reference",
                        "Passport number",
                        "Check application status"
                }
        );
    }

    private void showCountries() {

        page(
                "Countries",
                "Choose the country for your visa appointment.",
                new String[]{
                        "🇪🇸 Spain",
                        "More countries coming soon"
                }
        );
    }

    private void showStatistics() {

        page(
                "Statistics",
                "Monitoring and appointment data.",
                new String[]{
                        "Appointments checked",
                        "Availability checks",
                        "Monitoring activity"
                }
        );
    }

    private void showSettings() {

        page(
                "Settings",
                "Application preferences and configuration.",
                new String[]{
                        "Notifications",
                        "Language",
                        "About BLS Rendez-Vous"
                }
        );
    }

    // =========================================================
    // UI HELPERS
    // =========================================================

    private LinearLayout appointmentCard() {

        LinearLayout c =
                new LinearLayout(this);

        c.setOrientation(
                LinearLayout.VERTICAL
        );

        c.setPadding(
                dp(16),
                dp(13),
                dp(16),
                dp(13)
        );

        GradientDrawable bg =
                new GradientDrawable();

        bg.setColor(Color.WHITE);
        bg.setCornerRadius(dp(18));
        bg.setStroke(
                dp(1),
                Color.rgb(225,230,240)
        );

        c.setBackground(bg);

        return c;
    }

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
                Color.rgb(226,231,240)
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

    private void page(
            String title,
            String subtitle,
            String[] items
    ) {

        root.removeAllViews();

        ScrollView scroll =
                new ScrollView(this);

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

        scroll.addView(page);

        root.addView(
                scroll,
                new LinearLayout.LayoutParams(
                        -1,
                        0,
                        1
                )
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

            LinearLayout c =
                    card();

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

    private Button smallButton(
            String label
    ) {

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
