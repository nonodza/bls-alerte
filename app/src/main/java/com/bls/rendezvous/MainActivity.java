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

    // Appointment selections
    private String selectedWilaya = "";
    private String selectedCenter = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window window = getWindow();
        window.setStatusBarColor(Color.rgb(75, 70, 120));
        window.setNavigationBarColor(Color.WHITE);

        showSplash();
    }

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

        logoContainer.addView(bls,
                new LinearLayout.LayoutParams(-1, -2));

        final TextView international = new TextView(this);
        international.setText("international");
        international.setTextSize(16);
        international.setTextColor(Color.rgb(235, 245, 255));
        international.setGravity(Gravity.CENTER);

        LinearLayout.LayoutParams internationalParams =
                new LinearLayout.LayoutParams(-1, -2);
        internationalParams.topMargin = dp(-2);

        logoContainer.addView(international, internationalParams);

        splash.addView(logoContainer,
                new LinearLayout.LayoutParams(-1, -2));

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
        header.setPadding(dp(20), dp(18), dp(20), dp(8));

        LinearLayout titles = new LinearLayout(this);
        titles.setOrientation(LinearLayout.VERTICAL);

        TextView appName = text("BLS Rendez-Vous", 24, NAVY);
        appName.setTypeface(Typeface.DEFAULT_BOLD);

        titles.addView(appName);
        titles.addView(text(
                "Your visa appointment assistant",
                12,
                GRAY
        ));

        header.addView(
                titles,
                new LinearLayout.LayoutParams(0, -2, 1)
        );

        TextView settings = text("⚙", 25, NAVY);
        settings.setGravity(Gravity.CENTER);

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSettings();
            }
        });

        header.addView(
                settings,
                new LinearLayout.LayoutParams(dp(48), dp(48))
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
                new LinearLayout.LayoutParams(-1, 0, 1)
        );

        LinearLayout current = card();

        LinearLayout currentTop = new LinearLayout(this);
        currentTop.setGravity(Gravity.CENTER_VERTICAL);

        LinearLayout country = new LinearLayout(this);
        country.setOrientation(LinearLayout.VERTICAL);

        TextView label = text(
                "CURRENT APPLICATION",
                10,
                GRAY
        );

        TextView countryName = text(
                "🇪🇸  Spain",
                21,
                NAVY
        );

        countryName.setTypeface(Typeface.DEFAULT_BOLD);

        TextView center = text(
                "Algiers Visa Center",
                12,
                GRAY
        );

        country.addView(label);
        country.addView(countryName);
        country.addView(center);

        currentTop.addView(
                country,
                new LinearLayout.LayoutParams(0, -2, 1)
        );

        TextView active = text(
                "● ACTIVE",
                11,
                GREEN
        );

        currentTop.addView(active);
        current.addView(currentTop);

        TextView interval = text(
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

        LinearLayout monitor = card();

        LinearLayout monitorRow = new LinearLayout(this);
        monitorRow.setGravity(Gravity.CENTER_VERTICAL);

        TextView monitorIcon = text(
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

        LinearLayout monitorInfo = new LinearLayout(this);
        monitorInfo.setOrientation(LinearLayout.VERTICAL);

        TextView monitorTitle = text(
                "Appointment Monitoring",
                15,
                NAVY
        );

        monitorTitle.setTypeface(Typeface.DEFAULT_BOLD);

        TextView monitorStatus = text(
                "Monitoring is active",
                12,
                GREEN
        );

        monitorInfo.addView(monitorTitle);
        monitorInfo.addView(monitorStatus);

        monitorRow.addView(
                monitorInfo,
                new LinearLayout.LayoutParams(0, -2, 1)
        );

        Button monitorButton = smallButton("STOP");

        monitorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                monitorButton.setText("START");
                monitorStatus.setText(
                        "Monitoring is paused"
                );
                monitorStatus.setTextColor(GRAY);
            }
        });

        monitorRow.addView(monitorButton);
        monitor.addView(monitorRow);

        content.addView(
                monitor,
                margin(0, 0, 0, 10)
        );

        TextView services = text(
                "Services",
                19,
                NAVY
        );

        services.setTypeface(Typeface.DEFAULT_BOLD);

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

        TextView search = text(
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

        LinearLayout official = card();

        TextView officialTitle = text(
                "Official BLS Spain",
                15,
                NAVY
        );

        officialTitle.setTypeface(Typeface.DEFAULT_BOLD);

        official.addView(officialTitle);

        official.addView(
                text(
                        "Official information and visa services",
                        12,
                        GRAY
                ),
                margin(0, 4, 0, 8)
        );

        Button open = smallButton(
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

        LinearLayout bottom = new LinearLayout(this);

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

        LinearLayout page = new LinearLayout(this);
        page.setOrientation(LinearLayout.VERTICAL);
        page.setPadding(
                dp(20),
                dp(20),
                dp(20),
                dp(15)
        );

        GradientDrawable bg =
                new GradientDrawable(
                        GradientDrawable.Orientation.TL_BR,
                        new int[]{
                                Color.rgb(245, 248, 255),
                                Color.rgb(238, 244, 255),
                                Color.rgb(247, 243, 252)
                        }
                );

        page.setBackground(bg);

        root.addView(page);

        TextView back = text(
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
                margin(0, 0, 0, 10)
        );

        TextView title = text(
                "Appointments",
                28,
                NAVY
        );

        title.setTypeface(Typeface.DEFAULT_BOLD);

        page.addView(title);

        page.addView(
                text(
                        "Find the appointment that matches your travel plan.",
                        13,
                        GRAY
                ),
                margin(0, 5, 0, 18)
        );

        // COUNTRY

        LinearLayout countryCard = appointmentChoiceCard();

        TextView countryLabel = text(
                "COUNTRY",
                10,
                GRAY
        );

        countryCard.addView(countryLabel);

        TextView countryValue = text(
                "🇪🇸  Spain",
                18,
                NAVY
        );

        countryValue.setTypeface(Typeface.DEFAULT_BOLD);

        countryCard.addView(
                countryValue,
                margin(0, 5, 0, 0)
        );

        countryCard.addView(
                text(
                        "Visa appointment",
                        11,
                        GRAY
                ),
                margin(0, 2, 0, 0)
        );

        page.addView(
                countryCard,
                margin(0, 0, 0, 8)
        );

        // RESIDENCE / WILAYA

        final LinearLayout residenceCard =
                appointmentChoiceCard();

        residenceCard.addView(
                text(
                        "YOUR RESIDENCE",
                        10,
                        GRAY
                )
        );

        final TextView residenceValue =
                text(
                        "Select your wilaya",
                        17,
                        NAVY
                );

        residenceValue.setTypeface(
                Typeface.DEFAULT_BOLD
        );

        residenceCard.addView(
                residenceValue,
                margin(0, 5, 0, 0)
        );

        final TextView residenceHint =
                text(
                        "Required to determine your BLS center",
                        11,
                        GRAY
                );

        residenceCard.addView(
                residenceHint,
                margin(0, 2, 0, 0)
        );

        TextView residenceArrow =
                text(
                        "›",
                        28,
                        BLUE
                );

        residenceArrow.setGravity(Gravity.CENTER);

        residenceCard.addView(
                residenceArrow,
                new LinearLayout.LayoutParams(
                        dp(35),
                        dp(45)
                )
        );

        residenceCard.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showWilayaSelector(
                                residenceValue,
                                residenceHint
                        );
                    }
                }
        );

        page.addView(
                residenceCard,
                margin(0, 0, 0, 8)
        );

        // VISA CENTER

        final LinearLayout centerCard =
                appointmentChoiceCard();

        centerCard.addView(
                text(
                        "VISA CENTER",
                        10,
                        GRAY
                )
        );

        final TextView centerValue =
                text(
                        "Waiting for residence",
                        17,
                        NAVY
                );

        centerValue.setTypeface(
                Typeface.DEFAULT_BOLD
        );

        centerCard.addView(
                centerValue,
                margin(0, 5, 0, 0)
        );

        final TextView centerHint =
                text(
                        "Select your wilaya first",
                        11,
                        GRAY
                );

        centerCard.addView(
                centerHint,
                margin(0, 2, 0, 0)
        );

        page.addView(
                centerCard,
                margin(0, 0, 0, 8)
        );

        // VISA TYPE

        LinearLayout visaCard =
                appointmentChoiceCard();

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
                margin(0, 5, 0, 0)
        );

        visaCard.addView(
                text(
                        "Schengen visa",
                        11,
                        GRAY
                ),
                margin(0, 2, 0, 0)
        );

        page.addView(
                visaCard,
                margin(0, 0, 0, 12)
        );

        // CONTINUE

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
                                            "Please select your Wilaya of residence first. " +
                                            "The app will then determine the correct BLS center."
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

        // SMART MATCH INFO

        LinearLayout smart = card();

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
                        "The app will use your residence, visa type, " +
                        "applicant details and travel plan to determine " +
                        "the appropriate appointment path.",
                        12,
                        GRAY
                ),
                margin(0, 5, 0, 0)
        );

        page.addView(
                smart,
                margin(0, 12, 0, 0)
        );

        ScrollView scroll =
                new ScrollView(this);

        // We already have a page layout, so make the content scrollable
        root.removeAllViews();

        scroll.addView(page);

        root.addView(
                scroll,
                new LinearLayout.LayoutParams(
                        -1,
                        0,
                        1
                )
        );

        // Keep center references updated after wilaya selection
        residenceCard.setTag(
                new TextView[]{
                        residenceValue,
                        residenceHint,
                        centerValue,
                        centerHint
                }
        );
    }

    private LinearLayout appointmentChoiceCard() {

        LinearLayout c = new LinearLayout(this);

        c.setOrientation(
                LinearLayout.VERTICAL
        );

        c.setPadding(
                dp(16),
                dp(13),
                dp(45),
                dp(13)
        );

        GradientDrawable bg =
                new GradientDrawable();

        bg.setColor(Color.WHITE);
        bg.setCornerRadius(dp(18));
        bg.setStroke(
                dp(1),
                Color.rgb(225, 230, 240)
        );

        c.setBackground(bg);

        return c;
    }

    // =========================================================
    // WILAYA SELECTOR
    // =========================================================

    private void showWilayaSelector(
            final TextView residenceValue,
            final TextView residenceHint
    ) {

        final String[] wilayas = {

                "Adrar",
                "Ain Defla",
                "Ain Temouchent",
                "Algiers",
                "Annaba",
                "Batna",
                "Bechar",
                "Bejaia",
                "Biskra",
                "Blida",
                "Bordj Bou Arreridj",
                "Bouira",
                "Boumerdes",
                "Chlef",
                "Constantine",
                "Djelfa",
                "El Bayadh",
                "El Oued",
                "El Tarf",
                "Ghardaia",
                "Guelma",
                "Illizi",
                "Jijel",
                "Khenchela",
                "Laghouat",
                "Medea",
                "Mila",
                "Mostaganem",
                "M'Sila",
                "Mascara",
                "Naama",
                "Oran",
                "Ouargla",
                "Oum El Bouaghi",
                "Relizane",
                "Saida",
                "Setif",
                "Sidi Bel Abbes",
                "Skikda",
                "Souk Ahras",
                "Tamanrasset",
                "Tebessa",
                "Tiaret",
                "Tindouf",
                "Tipaza",
                "Tissemsilt",
                "Tizi-Ouzou",
                "Tlemcen"
        };

        AlertDialog dialog =
                new AlertDialog.Builder(
                        MainActivity.this
                )
                        .setTitle(
                                "Select your Wilaya"
                        )
                        .setItems(
                                wilayas,
                                null
                        )
                        .create();

        dialog.setOnShowListener(
                new android.content.DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(
                            android.content.DialogInterface d
                    ) {

                        android.widget.ListView list =
                                ((AlertDialog) d)
                                        .getListView();

                        list.setOnItemClickListener(
                                new android.widget.AdapterView.OnItemClickListener() {

                                    @Override
                                    public void onItemClick(
                                            android.widget.AdapterView<?> parent,
                                            View view,
                                            int position,
                                            long id
                                    ) {

                                        selectedWilaya =
                                                wilayas[position];

                                        selectedCenter =
                                                getBlsCenter(
                                                        selectedWilaya
                                                );

                                        residenceValue.setText(
                                                selectedWilaya
                                        );

                                        residenceHint.setText(
                                                "Residence selected"
                                        );

                                        updateAppointmentCenter(
                                                selectedCenter
                                        );

                                        dialog.dismiss();
                                    }
                                }
                        );
                    }
                }
        );

        dialog.show();
    }

    private void updateAppointmentCenter(
            String center
    ) {

        if (root == null) {
            return;
        }

        TextView centerValue =
                findTextViewByText(
                        "Waiting for residence"
                );

        if (centerValue == null) {
            centerValue =
                    findTextViewByText(
                            "Algiers Visa Center"
                    );
        }

        TextView centerHint =
                findTextViewByText(
                        "Select your wilaya first"
                );

        if (centerHint == null) {
            centerHint =
                    findTextViewByText(
                            "Based on your residence"
                    );
        }

        if (centerValue != null) {

            centerValue.setText(
                    center + " Visa Center"
            );

            centerValue.setTextColor(
                    NAVY
            );
        }

        if (centerHint != null) {

            centerHint.setText(
                    "Based on your residence"
            );

            centerHint.setTextColor(
                    GREEN
            );
        }
    }

    private TextView findTextViewByText(
            String value
    ) {

        return findTextViewRecursive(
                root,
                value
        );
    }

    private TextView findTextViewRecursive(
            View view,
            String value
    ) {

        if (view instanceof TextView) {

            TextView t =
                    (TextView) view;

            if (value.equals(
                    t.getText().toString()
            )) {
                return t;
            }
        }

        if (view instanceof LinearLayout) {

            LinearLayout layout =
                    (LinearLayout) view;

            for (int i = 0;
                 i < layout.getChildCount();
                 i++) {

                TextView result =
                        findTextViewRecursive(
                                layout.getChildAt(i),
                                value
                        );

                if (result != null) {
                    return result;
                }
            }
        }

        if (view instanceof ScrollView) {

            ScrollView scroll =
                    (ScrollView) view;

            if (scroll.getChildCount() > 0) {

                return findTextViewRecursive(
                        scroll.getChildAt(0),
                        value
                );
            }
        }

        return null;
    }

    // =========================================================
    // OFFICIAL CURRENT BLS JURISDICTION
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

        return "Algiers";
    }

    // =========================================================
    // NEXT STEP
    // =========================================================

    private void showVisaTypePage() {

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
                        "Next, we will determine the correct appointment category.",
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
                margin(0,0,0,10)
        );

        LinearLayout tourism =
                appointmentChoiceCard();

        tourism.addView(
                text(
                        "SHORT STAY",
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
                        "Schengen visa",
                        12,
                        GRAY
                ),
                margin(0,2,0,0)
        );

        page.addView(
                tourism,
                margin(0,0,0,10)
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
                        "After choosing the visa purpose, " +
                        "the app will determine the appropriate " +
                        "appointment category before checking availability.",
                        12,
                        GRAY
                ),
                margin(0,5,0,0)
        );

        page.addView(
                info,
                margin(0,5,0,0)
        );
    }

    // =========================================================
    // OTHER ORIGINAL PAGES
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
    // GENERIC UI
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
