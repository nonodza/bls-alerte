package com.bls.rendezvous;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Build;
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
import android.widget.ImageView;
import android.widget.FrameLayout;

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

    // =========================================================
    // PAGE NAVIGATION
    // =========================================================

    private String currentPage = "HOME";

    private String selectedWilaya = "";
    private String selectedCenter = "";
    private String selectedCategory = "";

    private TextView appointmentWilayaValue;
    private TextView appointmentWilayaHint;
    private TextView appointmentCenterValue;
    private TextView appointmentCenterHint;

    // =========================================================
    // CREATE
    // =========================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window window = getWindow();

        window.setStatusBarColor(
                Color.rgb(75, 70, 120)
        );

        window.setNavigationBarColor(
                Color.WHITE
        );

        showSplash();
    }

    // =========================================================
    // SPLASH
    // =========================================================

    private void showSplash() {

        final LinearLayout splash =
                new LinearLayout(this);

        splash.setOrientation(
                LinearLayout.VERTICAL
        );

        splash.setGravity(
                Gravity.CENTER
        );

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

        bls.setGravity(
                Gravity.CENTER
        );

        logoContainer.addView(
                bls,
                new LinearLayout.LayoutParams(
                        -1,
                        -2
                )
        );

        final TextView international =
                new TextView(this);

        international.setText(
                "international"
        );

        international.setTextSize(16);

        international.setTextColor(
                Color.rgb(235, 245, 255)
        );

        international.setGravity(
                Gravity.CENTER
        );

        LinearLayout.LayoutParams internationalParams =
                new LinearLayout.LayoutParams(
                        -1,
                        -2
                );

        internationalParams.topMargin =
                dp(-2);

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

        light.setText(
                "━━━━━━━━━━━━━━━━"
        );

        light.setTextSize(5);
        light.setTextColor(Color.WHITE);
        light.setGravity(Gravity.CENTER);
        light.setAlpha(0f);

        LinearLayout.LayoutParams lightParams =
                new LinearLayout.LayoutParams(
                        dp(190),
                        dp(20)
                );

        lightParams.gravity =
                Gravity.CENTER;

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

        light.setTranslationX(
                -dp(150)
        );

        light.setTranslationY(
                -dp(38)
        );

        light.animate()
                .alpha(0.9f)
                .translationX(dp(150))
                .setDuration(850)
                .setStartDelay(450)
                .withEndAction(
    new Runnable() {
        @Override
        public void run() {

            try {
                showHome();

            } catch (Exception e) {

                StringBuilder details =
                        new StringBuilder();

                details.append(e.toString());
                details.append("\n\n");

                for (StackTraceElement element : e.getStackTrace()) {
                    details.append(element.toString());
                    details.append("\n");
                }

                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("BLS Rendez-Vous Error")
                        .setMessage(details.toString())
                        .setPositiveButton("OK", null)
                        .show();
            }
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

        currentPage = "HOME";

        root =
                new LinearLayout(this);

        root.setOrientation(
                LinearLayout.VERTICAL
        );

        GradientDrawable background =
        new GradientDrawable(
                GradientDrawable.Orientation.TL_BR,
                new int[]{
                        Color.rgb(222, 231, 249),
                        Color.rgb(207, 219, 244),
                        Color.rgb(225, 211, 239)
                }
        );

background.setCornerRadius(0);

        setContentView(root);

 // =====================================================
// HEADER — MODERN TOP BAR
// =====================================================

LinearLayout header =
        new LinearLayout(this);

header.setOrientation(
        LinearLayout.HORIZONTAL
);

header.setGravity(
        Gravity.CENTER_VERTICAL
);

header.setPadding(
        dp(16),
        dp(8),
        dp(16),
        dp(8)
);

// =====================================================
// MENU
// =====================================================

ImageView menuIcon =
        new ImageView(this);

// Material-style menu vector
menuIcon.setImageResource(
        R.drawable.ic_menu
);


menuIcon.setPadding(
        dp(8),
        dp(8),
        dp(8),
        dp(8)
);

header.addView(
        menuIcon,
        new LinearLayout.LayoutParams(
                dp(42),
                dp(42)
        )
);

// =====================================================
// TITLE
// =====================================================

TextView appName =
        text(
                "BLS Rendez-Vous",
                20,
                NAVY
        );

appName.setTypeface(
        Typeface.DEFAULT,
        Typeface.BOLD
);

appName.setGravity(
        Gravity.CENTER_VERTICAL
);

LinearLayout.LayoutParams titleParams =
        new LinearLayout.LayoutParams(
                0,
                dp(42),
                1
);

titleParams.leftMargin =
        dp(6);

header.addView(
        appName,
        titleParams
);

// =====================================================
// NOTIFICATION AREA
// =====================================================

FrameLayout notificationContainer =
        new FrameLayout(this);

notificationContainer.setClipChildren(
        false
);

notificationContainer.setClipToPadding(
        false
);

// =====================================================
// BELL
// =====================================================

ImageView bell =
        new ImageView(this);

bell.setImageResource(
        R.drawable.ic_bell
);

bell.setPadding(
        dp(8),
        dp(8),
        dp(8),
        dp(8)
);

notificationContainer.addView(
        bell,
        new android.widget.FrameLayout.LayoutParams(
                dp(42),
                dp(42),
                Gravity.CENTER
        )
);

// =====================================================
// NOTIFICATION DOT
// =====================================================

View notificationDot =
        new View(this);

GradientDrawable dotBg =
        new GradientDrawable();

dotBg.setShape(
        GradientDrawable.OVAL
);

dotBg.setColor(
        Color.rgb(235, 65, 85)
);

notificationDot.setBackground(
        dotBg
);

android.widget.FrameLayout.LayoutParams dotParams =
        new android.widget.FrameLayout.LayoutParams(
                dp(8),
                dp(8)
);

dotParams.gravity =
        Gravity.TOP | Gravity.RIGHT;

dotParams.rightMargin =
        dp(5);

dotParams.topMargin =
        dp(4);

notificationContainer.addView(
        notificationDot,
        dotParams
);

header.addView(
        notificationContainer,
        new LinearLayout.LayoutParams(
                dp(42),
                dp(42)
        )
);

// =====================================================
// ADD TOP BAR
// =====================================================

root.addView(
        header,
        new LinearLayout.LayoutParams(
                -1,
                -2
        )
);

// =====================================================
// SCROLL CONTENT
// =====================================================

ScrollView scroll = new ScrollView(this);
scroll.setFillViewport(true);
scroll.setClipToPadding(false);

LinearLayout content = new LinearLayout(this);
content.setOrientation(LinearLayout.VERTICAL);
content.setPadding(
        dp(18),
        dp(5),
        dp(18),
        dp(28)
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
// HERO - FINAL -
// =====================================================
FrameLayout heroContainer = new FrameLayout(this);
GradientDrawable heroBg = new GradientDrawable();
heroBg.setCornerRadius(dp(22));
heroBg.setColor(Color.rgb(7, 11, 42));
heroContainer.setBackground(heroBg);
heroContainer.setClipToOutline(true);

ImageView heroImage = new ImageView(this);
heroImage.setImageResource(R.drawable.hero_final);
heroImage.setScaleType(ImageView.ScaleType.CENTER_CROP);

FrameLayout.LayoutParams imgParams = new FrameLayout.LayoutParams(
    FrameLayout.LayoutParams.MATCH_PARENT,
    FrameLayout.LayoutParams.MATCH_PARENT
);
heroContainer.addView(heroImage, imgParams);

//   - Container 
LinearLayout.LayoutParams containerParams = new LinearLayout.LayoutParams(
    LinearLayout.LayoutParams.MATCH_PARENT,
    dp(88)
);
containerParams.setMargins(dp(16), dp(6), dp(16), dp(12));
content.addView(heroContainer, containerParams);
        
// =====================================================
// CURRENT APPLICATION TOP
// =====================================================

LinearLayout curTop =
        new LinearLayout(this);

curTop.setOrientation(
        LinearLayout.HORIZONTAL
);

curTop.setGravity(
        Gravity.CENTER_VERTICAL
);


// =====================================================
// SPAIN
// =====================================================

TextView flag =
        text(
                "🇪🇸",
                30,
                NAVY
        );

flag.setGravity(
        Gravity.CENTER
);

LinearLayout.LayoutParams flagP =
        new LinearLayout.LayoutParams(
                dp(52),
                dp(52)
        );

flagP.rightMargin =
        dp(12);

curTop.addView(
        flag,
        flagP
);


// =====================================================
// COUNTRY INFORMATION
// =====================================================

LinearLayout curMid =
        new LinearLayout(this);

curMid.setOrientation(
        LinearLayout.VERTICAL
);

curMid.setLayoutParams(
        new LinearLayout.LayoutParams(
                0,
                -2,
                1
        )
);

TextView curLabel =
        text(
                "CURRENT APPLICATION",
                11,
                Color.rgb(90, 130, 255)
        );

curLabel.setTypeface(
        Typeface.DEFAULT_BOLD
);

curMid.addView(curLabel);

TextView spain =
        text(
                "Spain",
                20,
                NAVY
        );

spain.setTypeface(
        Typeface.DEFAULT_BOLD
);

curMid.addView(spain);

TextView alg =
        text(
                "Algiers Visa Center",
                12,
                GRAY
        );

curMid.addView(alg);

curTop.addView(curMid);


// =====================================================
// ACTIVE BADGE
// =====================================================

TextView active =
        text(
                "● ACTIVE",
                11,
                Color.rgb(0, 170, 90)
        );

active.setPadding(
        dp(12),
        dp(6),
        dp(12),
        dp(6)
);

GradientDrawable activeBg =
        new GradientDrawable();

activeBg.setColor(
        Color.rgb(220, 255, 235)
);

activeBg.setCornerRadius(
        dp(20)
);

active.setBackground(activeBg);

active.setTypeface(
        Typeface.DEFAULT_BOLD
);

curTop.addView(active); 
content.addView(curTop); 

// MONITORING INFO
LinearLayout mon = new LinearLayout(this); 
mon.setOrientation(LinearLayout.HORIZONTAL); 
mon.setGravity(Gravity.CENTER_VERTICAL); 
mon.setPadding(0, dp(8), 0, 0); 
TextView clock = text("◷", 17, GRAY); 
clock.setGravity(Gravity.CENTER); 
mon.addView(clock, new LinearLayout.LayoutParams(dp(20), dp(20))); 
TextView every = text("Monitoring every 2 minutes", 12, GRAY); 
every.setPadding(dp(6), 0, 0, 0); 
mon.addView(every); 
content.addView(mon); 
        
// content.addView(current, margin(0, 0, 0, 12));


// =====================================================
// APPOINTMENT MONITORING
// =====================================================

LinearLayout monCard = card();

GradientDrawable monBg =
        new GradientDrawable(
                GradientDrawable.Orientation.BL_TR,
                new int[]{
                        Color.rgb(245, 242, 255),
                        Color.rgb(221, 202, 247)
                }
        );

monBg.setCornerRadius(dp(20));

monCard.setBackground(monBg);

monCard.setPadding(
        dp(14),
        dp(11),
        dp(14),
        dp(11)
);

monCard.setOrientation(
        LinearLayout.HORIZONTAL
);

monCard.setGravity(
        Gravity.CENTER_VERTICAL
);


// Monitoring icon
TextView monIcon =
     text(
      "◷",
       24,
      BLUE
);

monIcon.setGravity(
        Gravity.CENTER
);

monCard.addView(
        monIcon,
        new LinearLayout.LayoutParams(
                dp(44),
                dp(44)
        )
);


// Monitoring text
LinearLayout monText =
        new LinearLayout(this);

monText.setOrientation(
        LinearLayout.VERTICAL
);

monText.setLayoutParams(
        new LinearLayout.LayoutParams(
                0,
                -2,
                1
        )
);

monText.setPadding(
        dp(12),
        0,
        dp(12),
        0
);

TextView monTitle =
        text(
                "Appointment Monitoring",
                15,
                NAVY
        );

monTitle.setTypeface(
        Typeface.DEFAULT_BOLD
);

monText.addView(monTitle);

TextView monSub =
        text(
                "Monitoring is paused",
                12,
                GRAY
        );

monText.addView(monSub);

monCard.addView(monText);


// START BUTTON
TextView startBtn =
        text(
                "START",
                13,
                Color.WHITE
        );

startBtn.setGravity(
        Gravity.CENTER
);

startBtn.setPadding(
        dp(18),
        dp(12),
        dp(18),
        dp(12)
);

GradientDrawable startBg =
        new GradientDrawable(
                GradientDrawable.Orientation.TL_BR,
                new int[]{
                        Color.rgb(60, 120, 255),
                        Color.rgb(90, 70, 220)
                }
        );

startBg.setCornerRadius(dp(20));

startBtn.setBackground(startBg);

startBtn.setTypeface(
        Typeface.DEFAULT_BOLD
);

monCard.addView(startBtn);

content.addView(
        monCard,
        margin(0, 0, 0, 12)
);


// =====================================================
// OFFICIAL BLS SPAIN
// =====================================================

LinearLayout offCard = card();

GradientDrawable offBg =
        new GradientDrawable(
                GradientDrawable.Orientation.TL_BR,
                new int[]{
                        Color.rgb(242, 248, 255),
                        Color.rgb(194, 224, 249)
                }
        );

offBg.setCornerRadius(dp(20));

offCard.setBackground(offBg);

offCard.setPadding(
        dp(16), dp(16), dp(16), dp(16)
);


LinearLayout offTop =
        new LinearLayout(this);

offTop.setOrientation(
        LinearLayout.HORIZONTAL
);

offTop.setGravity(
        Gravity.CENTER_VERTICAL
);


// Official icon
TextView offIcon =
        text(
                "✓",
                16,
                BLUE
        );

offIcon.setGravity(
        Gravity.CENTER
);

offTop.addView(
        offIcon,
        new LinearLayout.LayoutParams(
                dp(26),
                dp(26)
        )
);


// Official text
LinearLayout offText =
        new LinearLayout(this);

offText.setOrientation(
        LinearLayout.VERTICAL
);

offText.setPadding(
        dp(12),
        0,
        0,
        0
);

TextView offTitle =
        text(
                "Official BLS Spain",
                15,
                NAVY
        );

offTitle.setTypeface(
        Typeface.DEFAULT_BOLD
);

offText.addView(offTitle);

TextView offSub =
        text(
                "Official information and visa services",
                11,
                GRAY
        );

offText.addView(offSub);

offTop.addView(offText);

offCard.addView(offTop);


// OPEN WEBSITE BUTTON
TextView openBtn =
        text(
                "OPEN OFFICIAL WEBSITE",
                13,
                Color.WHITE
        );

openBtn.setGravity(
        Gravity.CENTER
);

openBtn.setPadding(
        0,
        dp(14),
        0,
        dp(14)
);

GradientDrawable openBg =
        new GradientDrawable(
                GradientDrawable.Orientation.TL_BR,
                new int[]{
                        Color.rgb(60, 120, 255),
                        Color.rgb(85, 75, 210)
                }
        );

openBg.setCornerRadius(dp(14));

openBtn.setBackground(openBg);

openBtn.setTypeface(
        Typeface.DEFAULT_BOLD
);

LinearLayout.LayoutParams openP =
        new LinearLayout.LayoutParams(
                -1,
                -2
        );

openP.topMargin =
        dp(14);

offCard.addView(
        openBtn,
        openP
);

content.addView(
        offCard,
        margin(0, 0, 0, 12)
);

   // =====================================================
        // SERVICES CONTAINER
        // =====================================================

        LinearLayout servicesContainer =
        new LinearLayout(this);

servicesContainer.setOrientation(
        LinearLayout.VERTICAL
);

servicesContainer.setPadding(
        dp(12),
        dp(14),
        dp(12),
        dp(10)
);

// =====================================================
// SERVICES TITLE
// =====================================================

TextView servicesTitle =
        text(
                "Services",
                20,
                NAVY
        );

servicesTitle.setTypeface(
        Typeface.DEFAULT_BOLD
);

servicesContainer.addView(
        servicesTitle,
        margin(2, 0, 0, 10)
);
  // =====================================================
// SERVICES GRID — 2 COLUMNS
// =====================================================

// =====================================================
// ROW 1
// =====================================================

LinearLayout row1 =
        new LinearLayout(this);

row1.setOrientation(
        LinearLayout.HORIZONTAL
);

row1.setGravity(
        Gravity.CENTER
);

row1.addView(
        serviceGridItem(
                "calendar",
                "Appointments",
                "Find appointments",
                false,
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showAppointments();
                    }
                }
        )
);

row1.addView(
        serviceGridItem(
                "bell",
                "Alerts",
                "Availability alerts",
                true,
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showAlerts();
                    }
                }
        )
);

servicesContainer.addView(
        row1,
        new LinearLayout.LayoutParams(
                -1,
                dp(82)
        )
);

// =====================================================
// ROW 2
// =====================================================

LinearLayout row2 =
        new LinearLayout(this);

row2.setOrientation(
        LinearLayout.HORIZONTAL
);

row2.setGravity(
        Gravity.CENTER
);

row2.addView(
        serviceGridItem(
                "building",
                "Centers",
                "Visa centers",
                false,
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showCenters();
                    }
                }
        )
);

row2.addView(
        serviceGridItem(
                "tracking",
                "Tracking",
                "Track application",
                true,
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showTracking();
                    }
                }
        )
);

servicesContainer.addView(
        row2,
        new LinearLayout.LayoutParams(
                -1,
                dp(82)
        )
);

// =====================================================
// ROW 3
// =====================================================

LinearLayout row3 =
        new LinearLayout(this);

row3.setOrientation(
        LinearLayout.HORIZONTAL
);

row3.setGravity(
        Gravity.CENTER
);

row3.addView(
        serviceGridItem(
                "globe",
                "Countries",
                "Visa countries",
                false,
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showCountries();
                    }
                }
        )
);

row3.addView(
        serviceGridItem(
                "stats",
                "Statistics",
                "View statistics",
                true,
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showStatistics();
                    }
                }
        )
);

servicesContainer.addView(
        row3,
        new LinearLayout.LayoutParams(
                -1,
                dp(82)
        )
);

// =====================================================
// ADD SERVICES CONTAINER TO HOME
// =====================================================

content.addView(
        servicesContainer,
        margin(0, 10, 0, 10)
);
       
        // =====================================================
        // BOTTOM NAVIGATION
        // =====================================================

        LinearLayout bottom =
                new LinearLayout(this);

        bottom.setOrientation(
                LinearLayout.HORIZONTAL
        );

        bottom.setGravity(
                Gravity.CENTER
        );

        bottom.setPadding(
                dp(4),
                dp(4),
                dp(4),
                dp(4)
        );

        bottom.setBackgroundColor(
                Color.TRANSPARENT
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
// SERVICE GRID ITEM - HORIZONTAL PROFESSIONAL
// =========================================================

private LinearLayout serviceGridItem(
        String icon,
        String title,
        String description,
        boolean purple,
                View.OnClickListener listener
) {
    // =====================================================
    // CARD
    // =====================================================

    LinearLayout item =
            new LinearLayout(this);

    item.setOrientation(
            LinearLayout.HORIZONTAL
    );

    item.setGravity(
            Gravity.CENTER_VERTICAL
    );

    item.setPadding(
            dp(10),
            dp(8),
            dp(10),
            dp(8)
    );

    // =====================================================
    // CARD BACKGROUND
    // =====================================================

    GradientDrawable cardBg;

if (purple) {

    cardBg =
            new GradientDrawable(
                    GradientDrawable.Orientation.TL_BR,
                    new int[]{
                            Color.rgb(255, 255, 255),
                            Color.rgb(245, 239, 255),
                            Color.rgb(235, 225, 250)
                    }
            );

} else {

    cardBg =
            new GradientDrawable(
                    GradientDrawable.Orientation.TL_BR,
                    new int[]{
                            Color.rgb(255, 255, 255),
                            Color.rgb(239, 247, 255),
                            Color.rgb(222, 237, 255)
                    }
            );
}

cardBg.setCornerRadius(
        dp(18)
);

cardBg.setStroke(
        dp(1),
        Color.rgb(225, 230, 242)
);

item.setBackground(
        cardBg
);

if (Build.VERSION.SDK_INT >=
        Build.VERSION_CODES.LOLLIPOP) {

    item.setElevation(
            dp(2)
    );
}

    // =====================================================
    // CARD SIZE
    // =====================================================

    LinearLayout.LayoutParams itemParams =
        new LinearLayout.LayoutParams(
                0,
                dp(70),
                1
        );

    itemParams.setMargins(
            dp(6),
            dp(6),
            dp(6),
            dp(6)
    );

    item.setLayoutParams(
            itemParams
    );

    // =====================================================
// ICON CIRCLE - PROFESSIONAL VECTOR
// =====================================================

ImageView iconView =
        new ImageView(this);

// =====================================================
// SELECT VECTOR ICON
// =====================================================

int iconRes = 0;

if (icon.equals("calendar")) {

    iconRes = R.drawable.ic_calendar;

} else if (icon.equals("bell")) {

    iconRes = R.drawable.ic_bell;

} else if (icon.equals("building")) {

    iconRes = R.drawable.ic_building;

} else if (icon.equals("tracking")) {

    iconRes = R.drawable.ic_tracking;

} else if (icon.equals("globe")) {

    iconRes = R.drawable.ic_globe;

} else if (icon.equals("stats")) {

    iconRes = R.drawable.ic_statistics;
}

// =====================================================
// SET VECTOR ICON
// =====================================================

if (iconRes != 0) {

    iconView.setImageResource(
            iconRes
    );
}

// =====================================================
// ICON SIZE
// =====================================================

iconView.setScaleType(
        ImageView.ScaleType.CENTER_INSIDE
);

iconView.setPadding(
        dp(9),
        dp(9),
        dp(9),
        dp(9)
);

// =====================================================
// CIRCLE BACKGROUND
// =====================================================

GradientDrawable iconBg =
        new GradientDrawable();

iconBg.setColor(
        purple
                ? Color.rgb(241, 233, 255)
                : Color.rgb(232, 242, 255)
);

iconBg.setShape(
        GradientDrawable.OVAL
);

iconBg.setStroke(
        dp(1),
        purple
                ? Color.rgb(226, 214, 245)
                : Color.rgb(213, 229, 250)
);

iconView.setBackground(
        iconBg
);

// =====================================================
// ICON SIZE / MARGIN
// =====================================================

LinearLayout.LayoutParams iconParams =
        new LinearLayout.LayoutParams(
                dp(42),
                dp(42)
        );

iconParams.setMargins(
        0,
        0,
        dp(6),
        0
);

// =====================================================
// ADD ICON
// =====================================================

item.addView(
        iconView,
        iconParams
);

    // =====================================================
    // TEXT CONTAINER
    // =====================================================

    LinearLayout textContainer =
            new LinearLayout(this);

    textContainer.setOrientation(
            LinearLayout.VERTICAL
    );

    textContainer.setGravity(
            Gravity.CENTER_VERTICAL
    );

    LinearLayout.LayoutParams textParams =
            new LinearLayout.LayoutParams(
                    0,
                    -1,
                    1
            );

    item.addView(
            textContainer,
            textParams
    );

    // =====================================================
    // TITLE
    // =====================================================

    TextView titleView =
            text(
                    title,
                    12,
                    NAVY
            );

    titleView.setTypeface(
            Typeface.DEFAULT,
            Typeface.BOLD
    );

    titleView.setMaxLines(
            1
    );

    titleView.setEllipsize(
            android.text.TextUtils.TruncateAt.END
    );

    textContainer.addView(
            titleView,
            new LinearLayout.LayoutParams(
                    -1,
                    dp(21)
            )
    );

    // =====================================================
    // DESCRIPTION
    // =====================================================

    TextView descriptionView =
            text(
                    description,
                    10,
                    Color.rgb(145, 150, 165)
            );

    descriptionView.setMaxLines(
            1
    );

    descriptionView.setEllipsize(
            android.text.TextUtils.TruncateAt.END
    );

    textContainer.addView(
            descriptionView,
            new LinearLayout.LayoutParams(
                    -1,
                    dp(19)
            )
    );

    // =====================================================
    // ARROW
    // =====================================================

    TextView arrowView =
            new TextView(this);

    arrowView.setText(
            ">"
    );

    arrowView.setTextSize(
            20
    );

    arrowView.setTextColor(
            Color.rgb(145, 148, 158)
    );

    arrowView.setGravity(
            Gravity.CENTER
    );

    arrowView.setIncludeFontPadding(
            false
    );

    item.addView(
            arrowView,
            new LinearLayout.LayoutParams(
                    dp(20),
                    -1
            )
    );

    // =====================================================
    // CLICK
    // =====================================================

    item.setOnClickListener(
            listener
    );

    return item;
}
    
    // =========================================================
    // APPOINTMENTS
    // =========================================================

    private void showAppointments() {

        currentPage = "APPOINTMENTS";

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
                                Color.rgb(245, 248, 255),
                                Color.rgb(238, 244, 255),
                                Color.rgb(247, 243, 252)
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
                margin(0, 0, 0, 10)
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
                margin(0, 5, 0, 18)
        );

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
                margin(0, 5, 0, 0)
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
                margin(0, 2, 0, 0)
        );

        page.addView(
                residenceCard,
                margin(0, 0, 0, 8)
        );

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
                margin(0, 5, 0, 0)
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
                margin(0, 2, 0, 0)
        );

        page.addView(
                centerCard,
                margin(0, 0, 0, 8)
        );

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
                margin(0, 0, 0, 8)
        );

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
                margin(0, 5, 0, 0)
        );

        applicants.addView(
                text(
                        "Number of people",
                        11,
                        GRAY
                ),
                margin(0, 2, 0, 0)
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
                margin(0, 0, 0, 8)
        );

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
                margin(0, 5, 0, 0)
        );

        travel.addView(
                text(
                        "Planned departure date",
                        11,
                        GRAY
                ),
                margin(0, 2, 0, 0)
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
                margin(0, 0, 0, 12)
        );

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
                margin(0, 5, 0, 0)
        );

        page.addView(
                smart,
                margin(0, 12, 0, 10)
        );

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
                margin(0, 5, 0, 0)
        );

        availability.addView(
                text(
                        "Your preferences are ready for the next availability check.",
                        11,
                        GRAY
                ),
                margin(0, 3, 0, 8)
        );

        Button check =
                smallButton(
                        "CHECK AGAIN"
                );

        availability.addView(check);

        page.addView(
                availability,
                margin(0, 0, 0, 10)
        );

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
                margin(0, 5, 0, 8)
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
                margin(0, 0, 0, 5)
        );
    }

    // =========================================================
    // WILAYAS
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
                smallButton(
                        "CANCEL"
                );

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
                                    .widthPixels
                                    * 0.92
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

        currentPage = "VISA_TYPE";

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
                                Color.rgb(245, 248, 255),
                                Color.rgb(238, 244, 255),
                                Color.rgb(247, 243, 252)
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
                margin(0, 0, 0, 15)
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
                margin(0, 5, 0, 18)
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
                        selectedCenter.length() == 0
                                ? "Algiers Visa Center"
                                : selectedCenter + " Visa Center",
                        18,
                        NAVY
                );

        center.setTypeface(
                Typeface.DEFAULT_BOLD
        );

        selected.addView(
                center,
                margin(0, 5, 0, 0)
        );

        selected.addView(
                text(
                        selectedWilaya.length() == 0
                                ? "Algiers"
                                : selectedWilaya,
                        12,
                        GREEN
                ),
                margin(0, 3, 0, 0)
        );

        page.addView(
                selected,
                margin(0, 0, 0, 15)
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
                        "🇪🇸  Spain",
                        18,
                        NAVY
                );

        countryTitle.setTypeface(
                Typeface.DEFAULT_BOLD
        );

        country.addView(
                countryTitle,
                margin(0, 5, 0, 0)
        );

        country.addView(
                text(
                        "Schengen visa",
                        12,
                        GRAY
                ),
                margin(0, 3, 0, 0)
        );

        page.addView(
                country,
                margin(0, 0, 0, 10)
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
                margin(0, 5, 0, 0)
        );

        tourism.addView(
                text(
                        "Short Stay - Tourism",
                        12,
                        GRAY
                ),
                margin(0, 3, 0, 0)
        );

        tourism.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showPreviousSpainVisaPage();
                    }
                }
        );

        page.addView(
                tourism,
                margin(0, 0, 0, 15)
        );
    }

    // =========================================================
    // PREVIOUS SCHENGEN VISA
    // =========================================================

    private void showPreviousSpainVisaPage() {

        currentPage = "PREVIOUS_VISA";

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
                        "Previous Schengen Visa",
                        26,
                        NAVY
                );

        title.setTypeface(
                Typeface.DEFAULT_BOLD
        );

        layout.addView(title);

        TextView subtitle =
                text(
                        "Tell us about your most recent Schengen visa.",
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
                        "Have you had a Schengen visa before?",
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
                        "YES\n\nYes, I have had a Schengen visa",
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
                        showVisaIssuingCountryPage();
                    }
                }
        );

        layout.addView(
                yes,
                margin(0, 0, 0, 15)
        );

        TextView no =
                text(
                        "NO\n\nNo, I have never had a Schengen visa",
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

                        selectAppointmentCategory(
                                "ALG1"
                        );
                    }
                }
        );

        layout.addView(no);

        TextView why =
                text(
                        "Why do we ask?\n\nOnly the most recent Schengen visa issued by Spain on or after 1 January 2021 can be used for ALG2, ALG3 or ALG4.",
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
    // VISA ISSUING COUNTRY
    // =========================================================

    private void showVisaIssuingCountryPage() {

        currentPage = "ISSUING_COUNTRY";

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
                        showPreviousSpainVisaPage();
                    }
                }
        );

        layout.addView(back);

        TextView title =
                text(
                        "Visa Issuing Country",
                        26,
                        NAVY
                );

        title.setTypeface(
                Typeface.DEFAULT_BOLD
        );

        layout.addView(title);

        TextView subtitle =
                text(
                        "Which country issued your most recent Schengen visa?",
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

        TextView spain =
                text(
                        "🇪🇸  Spain\n\nMy most recent Schengen visa was issued by Spain",
                        17,
                        NAVY
                );

        spain.setPadding(
                dp(20),
                dp(20),
                dp(20),
                dp(20)
        );

        spain.setBackgroundColor(LIGHT);

        spain.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showVisaValidityPage();
                    }
                }
        );

        layout.addView(
                spain,
                margin(0, 0, 0, 12)
        );

        TextView other =
                text(
                        "🌍  Other Schengen country\n\nMy most recent Schengen visa was issued by another Schengen country",
                        17,
                        NAVY
                );

        other.setPadding(
                dp(20),
                dp(20),
                dp(20),
                dp(20)
        );

        other.setBackgroundColor(LIGHT);

        other.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        selectAppointmentCategory(
                                "ALG1"
                        );
                    }
                }
        );

        layout.addView(other);

        TextView info =
                text(
                        "Important\n\nFor ALG2, ALG3 and ALG4, the most recent Schengen visa must have been issued exclusively by Spain on or after 1 January 2021.",
                        14,
                        GRAY
                );

        info.setPadding(
                0,
                dp(25),
                0,
                0
        );

        layout.addView(info);

        scroll.addView(layout);

        root.addView(scroll);
    }

    // =========================================================
    // SPAIN VISA VALIDITY
    // =========================================================

    private void showVisaValidityPage() {

        currentPage = "VISA_VALIDITY";

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
                        showVisaIssuingCountryPage();
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
                Typeface.DEFAULT_BOLD
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

        layout.addView(
                alg2,
                margin(0, 0, 0, 12)
        );

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

        layout.addView(
                alg3,
                margin(0, 0, 0, 12)
        );

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

        layout.addView(
                alg4,
                margin(0, 0, 0, 20)
        );

        layout.addView(
                text(
                        "Your previous Spain visa validity determines the appropriate BLS appointment category.",
                        14,
                        GRAY
                )
        );

        scroll.addView(layout);

        root.addView(scroll);
    }

    // =========================================================
    // APPOINTMENT CATEGORY
    // =========================================================

    private void selectAppointmentCategory(
            String category
    ) {

        selectedCategory =
                category;

        showAppointmentCategoryPage();
    }

    private void showAppointmentCategoryPage() {

        currentPage = "CATEGORY";

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
                margin(0, 0, 0, 18)
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
                margin(0, 0, 0, 8)
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
                margin(0, 0, 0, 20)
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
                margin(0, 5, 0, 0)
        );

        page.addView(
                infoCard,
                margin(0, 0, 0, 20)
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
                margin(0, 5, 0, 0)
        );

        page.addView(
                centerCard,
                margin(0, 0, 0, 20)
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
                margin(0, 0, 0, 25)
        );

        Button continueButton =
                smallButton(
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
                margin(0, 0, 0, 10)
        );

        scroll.addView(page);

        root.addView(scroll);
    }

    // =========================================================
    // APPOINTMENT DETAILS
    // =========================================================

    private void showAppointmentDetailsPage() {

        currentPage = "DETAILS";

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
                margin(0, 0, 0, 18)
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
                margin(0, 0, 0, 8)
        );

        page.addView(
                text(
                        "Review your appointment information before continuing.",
                        16,
                        GRAY
                ),
                margin(0, 0, 0, 20)
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
                margin(0, 5, 0, 0)
        );

        page.addView(
                categoryCard,
                margin(0, 0, 0, 15)
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
                margin(0, 5, 0, 0)
        );

        page.addView(
                centerCard,
                margin(0, 0, 0, 15)
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
                margin(0, 5, 0, 0)
        );

        page.addView(
                residenceCard,
                margin(0, 0, 0, 20)
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
                margin(0, 0, 0, 25)
        );

        Button openBlsButton =
                smallButton(
                        "OPEN OFFICIAL BLS"
                );

        openBlsButton.setTextSize(13);

        openBlsButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        AlertDialog.Builder builder =
                                new AlertDialog.Builder(
                                        MainActivity.this
                                );

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
                margin(0, 0, 0, 10)
        );

        scroll.addView(page);

        root.addView(scroll);
    }

    // =========================================================
    // ALERTS
    // =========================================================

    private void showAlerts() {

        currentPage = "ALERTS";

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

    // =========================================================
    // CENTERS
    // =========================================================

    private void showCenters() {

        currentPage = "CENTERS";

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
                margin(0, 0, 0, 15)
        );

        TextView title =
                text(
                        "Visa Centers",
                        28,
                        NAVY
                );

        title.setTypeface(
                Typeface.DEFAULT_BOLD
        );

        page.addView(title);

        page.addView(
                text(
                        "Select the BLS Spain visa center you want to use.",
                        13,
                        GRAY
                ),
                margin(0, 5, 0, 20)
        );

        final android.content.SharedPreferences preferences =
                getSharedPreferences(
                        "BLS_SETTINGS",
                        MODE_PRIVATE
                );

        final String savedCenter =
                preferences.getString(
                        "selected_center",
                        ""
                );

        LinearLayout currentCard =
                card();

        currentCard.addView(
                text(
                        "CURRENT CENTER",
                        10,
                        GRAY
                )
        );

        final TextView currentValue =
                text(
                        savedCenter.length() == 0
                                ? "No center selected"
                                : savedCenter + " Visa Center",
                        18,
                        savedCenter.length() == 0
                                ? GRAY
                                : GREEN
                );

        currentValue.setTypeface(
                Typeface.DEFAULT_BOLD
        );

        currentCard.addView(
                currentValue,
                margin(0, 5, 0, 0)
        );

        currentCard.addView(
                text(
                        savedCenter.length() == 0
                                ? "Choose a center below"
                                : "Your selected center",
                        11,
                        GRAY
                ),
                margin(0, 3, 0, 0)
        );

        page.addView(
                currentCard,
                margin(0, 0, 0, 15)
        );

        LinearLayout algiersCard =
                appointmentCard();

        TextView algiersTitle =
                text(
                        "🇩🇿  Algiers Visa Center",
                        18,
                        NAVY
                );

        algiersTitle.setTypeface(
                Typeface.DEFAULT_BOLD
        );

        algiersCard.addView(algiersTitle);

        TextView algiersStatus =
                text(
                        savedCenter.equalsIgnoreCase("Algiers")
                                ? "✓ Selected"
                                : "Tap to select",
                        12,
                        savedCenter.equalsIgnoreCase("Algiers")
                                ? GREEN
                                : GRAY
                );

        algiersCard.addView(
                algiersStatus,
                margin(0, 5, 0, 0)
        );

        algiersCard.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        preferences.edit()
                                .putString(
                                        "selected_center",
                                        "Algiers"
                                )
                                .apply();

                        selectedCenter =
                                "Algiers";

                        currentValue.setText(
                                "Algiers Visa Center"
                        );

                        currentValue.setTextColor(
                                GREEN
                        );

                        appointmentCenterValue =
                                null;

                        android.widget.Toast.makeText(
                                MainActivity.this,
                                "تم اختيار: Algiers",
                                android.widget.Toast.LENGTH_SHORT
                        ).show();

                        showCenters();
                    }
                }
        );

        page.addView(
                algiersCard,
                margin(0, 0, 0, 10)
        );

        LinearLayout oranCard =
                appointmentCard();

        TextView oranTitle =
                text(
                        "🇩🇿  Oran Visa Center",
                        18,
                        NAVY
                );

        oranTitle.setTypeface(
                Typeface.DEFAULT_BOLD
        );

        oranCard.addView(oranTitle);

        TextView oranStatus =
                text(
                        savedCenter.equalsIgnoreCase("Oran")
                                ? "✓ Selected"
                                : "Tap to select",
                        12,
                        savedCenter.equalsIgnoreCase("Oran")
                                ? GREEN
                                : GRAY
                );

        oranCard.addView(
                oranStatus,
                margin(0, 5, 0, 0)
        );

        oranCard.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        preferences.edit()
                                .putString(
                                        "selected_center",
                                        "Oran"
                                )
                                .apply();

                        selectedCenter =
                                "Oran";

                        currentValue.setText(
                                "Oran Visa Center"
                        );

                        currentValue.setTextColor(
                                GREEN
                        );

                        appointmentCenterValue =
                                null;

                        android.widget.Toast.makeText(
                                MainActivity.this,
                                "تم اختيار: Oran",
                                android.widget.Toast.LENGTH_SHORT
                        ).show();

                        showCenters();
                    }
                }
        );

        page.addView(
                oranCard,
                margin(0, 0, 0, 15)
        );

        LinearLayout info =
                card();

        TextView infoTitle =
                text(
                        "BLS Spain Algeria",
                        15,
                        NAVY
                );

        infoTitle.setTypeface(
                Typeface.DEFAULT_BOLD
        );

        info.addView(infoTitle);

        info.addView(
                text(
                        "Choose the visa center that corresponds to your application.",
                        12,
                        GRAY
                ),
                margin(0, 5, 0, 0)
        );

        page.addView(
                info,
                margin(0, 0, 0, 10)
        );
    }

    // =========================================================
    // TRACKING
    // =========================================================

    private void showTracking() {

        currentPage = "TRACKING";

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

    // =========================================================
    // COUNTRIES
    // =========================================================

    private void showCountries() {

        currentPage = "COUNTRIES";

        page(
                "Countries",
                "Choose the country for your visa appointment.",
                new String[]{
                        "🇪🇸 Spain",
                        "More countries coming soon"
                }
        );
    }

    // =========================================================
    // STATISTICS
    // =========================================================

    private void showStatistics() {

        currentPage = "STATISTICS";

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

    // =========================================================
    // SETTINGS
    // =========================================================

    private void showSettings() {

        currentPage = "SETTINGS";

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
    // APPOINTMENT CARD
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
                new GradientDrawable(
                        GradientDrawable.Orientation.TL_BR,
                        new int[]{
                                Color.WHITE,
                                Color.rgb(247, 249, 253)
                        }
                );

        bg.setCornerRadius(
                dp(18)
        );

        bg.setStroke(
                dp(1),
                Color.rgb(225, 230, 240)
        );

        c.setBackground(bg);

        if (Build.VERSION.SDK_INT >=
                Build.VERSION_CODES.LOLLIPOP) {

            c.setElevation(
                    dp(1)
            );
        }

        return c;
    }

    // =========================================================
    // OLD SERVICE CARD
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
                new GradientDrawable(
                        GradientDrawable.Orientation.TL_BR,
                        new int[]{
                                Color.WHITE,
                                Color.rgb(246, 249, 254)
                        }
                );

        bg.setCornerRadius(
                dp(19)
        );

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

        i.setGravity(
                Gravity.CENTER
        );

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

        t.setGravity(
                Gravity.CENTER
        );

        card.addView(t);

        TextView s =
                text(
                        subtitle,
                        10,
                        GRAY
                );

        s.setGravity(
                Gravity.CENTER
        );

        card.addView(s);

        card.setOnClickListener(
                listener
        );

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
    // PROFESSIONAL NAV ITEM
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

        item.setPadding(
                dp(4),
                dp(3),
                dp(4),
                dp(3)
        );

        TextView i =
                text(
                        icon,
                        22,
                        NAVY
                );

        i.setGravity(
                Gravity.CENTER
        );

        i.setTypeface(
                Typeface.DEFAULT_BOLD
        );

        i.setShadowLayer(
                dp(2),
                0,
                dp(1),
                Color.WHITE
        );

        item.addView(
                i,
                new LinearLayout.LayoutParams(
                        -1,
                        dp(30)
                )
        );

        TextView n =
                text(
                        name,
                        10,
                        NAVY
                );

        n.setGravity(
                Gravity.CENTER
        );

        n.setTypeface(
                Typeface.DEFAULT_BOLD
        );

        n.setShadowLayer(
                dp(2),
                0,
                dp(1),
                Color.WHITE
        );

        item.addView(
                n,
                new LinearLayout.LayoutParams(
                        -1,
                        dp(20)
                )
        );

        item.setClickable(true);
        item.setFocusable(true);

        item.setOnClickListener(
                listener
        );

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
    // GENERIC PAGE
    // =========================================================

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
                                Color.rgb(245, 248, 255),
                                Color.rgb(238, 244, 255),
                                Color.rgb(247, 243, 252)
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
                margin(0, 0, 0, 15)
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
                margin(0, 5, 0, 18)
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
                    margin(0, 5, 0, 5)
            );
        }
    }

    // =========================================================
    // MAIN CARD
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
                new GradientDrawable(
                        GradientDrawable.Orientation.TL_BR,
                        new int[]{
                                Color.rgb(255, 255, 255),
                                Color.rgb(246, 248, 253)
                        }
                );

        bg.setCornerRadius(
                dp(19)
        );

        bg.setStroke(
                dp(1),
                Color.rgb(228, 233, 243)
        );

        c.setBackground(bg);

        if (Build.VERSION.SDK_INT >=
                Build.VERSION_CODES.LOLLIPOP) {

            c.setElevation(
                    dp(2)
            );
        }

        return c;
    }

    // =========================================================
    // SMALL BUTTON
    // =========================================================

    private Button smallButton(
            String label
    ) {

        Button b =
                new Button(this);

        b.setText(label);
        b.setTextSize(10);
        b.setTextColor(WHITE);

        GradientDrawable bg =
                new GradientDrawable(
                        GradientDrawable.Orientation.TL_BR,
                        new int[]{
                                Color.rgb(55, 105, 205),
                                Color.rgb(75, 80, 170)
                        }
                );

        bg.setCornerRadius(
                dp(15)
        );

        b.setBackground(bg);

        return b;
    }

    // =========================================================
    // TEXT HELPER
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
    // MARGIN HELPER
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
    // DP HELPER
    // =========================================================

    private int dp(
            int value
    ) {

        return (int)(
                value *
                getResources()
                        .getDisplayMetrics()
                        .density
                        + 0.5f
        );
    }

    // =========================================================
    // HARDWARE BACK NAVIGATION
    // =========================================================

    @Override
    public void onBackPressed() {

        if (currentPage.equals("HOME")) {

            super.onBackPressed();

        } else if (currentPage.equals("APPOINTMENTS")) {

            showHome();

        } else if (currentPage.equals("VISA_TYPE")) {

            showAppointments();

        } else if (currentPage.equals("PREVIOUS_VISA")) {

            showVisaTypePage();

        } else if (currentPage.equals("ISSUING_COUNTRY")) {

            showPreviousSpainVisaPage();

        } else if (currentPage.equals("VISA_VALIDITY")) {

            showVisaIssuingCountryPage();

        } else if (currentPage.equals("CATEGORY")) {

            if (selectedCategory.equals("ALG1")) {

                showPreviousSpainVisaPage();

            } else {

                showVisaValidityPage();
            }

        } else if (currentPage.equals("DETAILS")) {

            showAppointmentCategoryPage();

        } else if (currentPage.equals("ALERTS")) {

            showHome();

        } else if (currentPage.equals("CENTERS")) {

            showHome();

        } else if (currentPage.equals("TRACKING")) {

            showHome();

        } else if (currentPage.equals("COUNTRIES")) {

            showHome();

        } else if (currentPage.equals("STATISTICS")) {

            showHome();

        } else if (currentPage.equals("SETTINGS")) {

            showHome();

        } else {

            showHome();
        }
    }
}
