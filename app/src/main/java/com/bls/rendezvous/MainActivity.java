package com.bls.rendezvous;

import android.app.Activity;
import android.app.AlertDialog;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.res.Resources;

import android.os.Bundle;
import android.os.Handler;
import android.os.Build;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LinearGradient;
import android.view.MotionEvent;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.Typeface;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;

import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Space;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.FrameLayout;

import com.google.android.material.bottomsheet.BottomSheetDialog;

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
private String selectedCountry = "";
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
// =====================================================
// MAIN APP SHELL
// =====================================================

private LinearLayout mainShell;
private FrameLayout mainContent;
private LinearLayout bottomNavigation;
    
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

// =====================================================
// MAIN SHELL
// =====================================================

mainShell =
        new LinearLayout(this);

mainShell.setOrientation(
        LinearLayout.VERTICAL
);

mainShell.setBackground(
        background
);

mainContent =
        new FrameLayout(this);

mainShell.addView(
        mainContent,
        new LinearLayout.LayoutParams(
                -1,
                0,
                1f
        )
);

mainContent.addView(
        root,
        new FrameLayout.LayoutParams(
                -1,
                -1
        )
);

setContentView(mainShell);
   
// =====================================================
// HEADER
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
        dp(6),
        dp(4),
        dp(6),
        dp(4)
);
GradientDrawable headerBg = new GradientDrawable();
headerBg.setShape(GradientDrawable.RECTANGLE);
headerBg.setColor(Color.parseColor("#162040"));
headerBg.setCornerRadius(0f);
header.setBackground(headerBg);
// =====================================================
// MENU
// =====================================================

ImageView menuIcon =
        new ImageView(this);

menuIcon.setImageResource(
        R.drawable.ic_menu
);
menuIcon.setColorFilter(
        Color.parseColor("#4FC3F7"),
        PorterDuff.Mode.SRC_IN
);
menuIcon.setPadding(
        dp(8),
        dp(8),
        dp(8),
        dp(8)
);

menuIcon.setOnClickListener(
        v -> showMainMenu()
);

header.addView(
        menuIcon,
        new LinearLayout.LayoutParams(
                dp(42),
                dp(42)
        )
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
// HEADER SPACER
// =====================================================

android.widget.Space headerSpacer =
        new android.widget.Space(this);

header.addView(
        headerSpacer,
        new LinearLayout.LayoutParams(
                0,
                1,
                1
        )
);


// =====================================================
// NOTIFICATION AREA
// =====================================================

header.addView(
        notificationContainer,
        new LinearLayout.LayoutParams(
                dp(42),
                dp(42)
        )
);

// =====================================================
// BELL - PREMIUM BLUE GLOW
// =====================================================
FrameLayout bellWrapper = new FrameLayout(this);

// =====================================================
// BELL PREMIUM BACKGROUND
// =====================================================
GradientDrawable bellBackground = new GradientDrawable();
bellBackground.setShape(GradientDrawable.OVAL);
bellBackground.setColor(Color.parseColor("#1A0B2A4A"));
bellBackground.setStroke(dp(1), Color.parseColor("#334FC3F7"));
bellWrapper.setBackground(bellBackground);
bellWrapper.setClipChildren(false);
bellWrapper.setClipToPadding(false);

// =====================================================
// MAIN BELL
// =====================================================
ImageView bell = new ImageView(this);
bell.setImageResource(R.drawable.ic_bell_outline_blue);
bell.setColorFilter(Color.parseColor("#4FC3F7"), PorterDuff.Mode.SRC_IN);
bell.setPadding(dp(8), dp(8), dp(8), dp(8));

FrameLayout.LayoutParams bellParams = new FrameLayout.LayoutParams(dp(42), dp(42), Gravity.CENTER);
bellWrapper.addView(bell, bellParams);


// =====================================================
// CLICK - NOTIFICATIONS POPUP
// =====================================================

bellWrapper.setOnClickListener(
        v -> {

            View popupView =
                    getLayoutInflater().inflate(
                            R.layout.bottomsheet_notifications_empty,
                            null
                    );

            PopupWindow notificationPopup =
                    new PopupWindow(
                            popupView,
                            dp(310),
                            dp(170),
                            true
                    );

            notificationPopup.setBackgroundDrawable(
                    new android.graphics.drawable.ColorDrawable(
                            Color.TRANSPARENT
                    )
            );

            notificationPopup.setOutsideTouchable(true);
            notificationPopup.setFocusable(true);
            notificationPopup.setElevation(dp(12));


            // =================================================
            // SHOW POPUP UNDER BELL
            // =================================================

            notificationPopup.showAsDropDown(
                    bellWrapper,
                    -dp(265),
                    dp(8)
            );


            // =================================================
            // BELL CLICK ANIMATION
            // =================================================

            bell.animate()
                    .scaleX(0.90f)
                    .scaleY(0.90f)
                    .setDuration(100)
                    .withEndAction(() ->
                            bell.animate()
                                    .scaleX(1f)
                                    .scaleY(1f)
                                    .setDuration(120)
                                    .start()
                    )
                    .start();
        }
);

// =====================================================
// ADD TO HEADER
// =====================================================

notificationContainer.addView(
        bellWrapper,
        new FrameLayout.LayoutParams(
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



// =====================================================
// ADD TOP BAR
// =====================================================

root.addView(
        header,
        new LinearLayout.LayoutParams(
                -1,
                dp(52)
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
        FrameLayout.LayoutParams.MATCH_PARENT );
heroContainer.addView(heroImage, imgParams);

// - Container
LinearLayout.LayoutParams containerParams = new LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.MATCH_PARENT, dp(88) );
containerParams.setMargins(dp(6), dp(6), dp(8), dp(12));
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
// SCHENGEN LOGO
// =====================================================
AppCompatImageView schengenLogo = new AppCompatImageView(this);
schengenLogo.setImageResource(R.drawable.ic_eu_new);
schengenLogo.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
LinearLayout.LayoutParams schengenP = new LinearLayout.LayoutParams(dp(52), dp(52));
schengenP.rightMargin = dp(12);
curTop.addView(schengenLogo, schengenP);

// =====================================================
// COUNTRY INFORMATION
// =====================================================

LinearLayout curMid =
        new LinearLayout(this);

curMid.setOrientation(
        LinearLayout.VERTICAL
);

curMid.setGravity(
        Gravity.CENTER_VERTICAL
);

curMid.setLayoutParams(
        new LinearLayout.LayoutParams(
                0,
                -2,
                1
        )
);

// =====================================================
// COUNTRY SELECTOR
// =====================================================

TextView countrySelector =
        new TextView(this);

countrySelector.setText(
        "Select Country  ⌄"
);

countrySelector.setTextSize(
        16
);

countrySelector.setTextColor(
        Color.WHITE
);

countrySelector.setTypeface(
        Typeface.DEFAULT_BOLD
);

countrySelector.setGravity(
        Gravity.CENTER
);

countrySelector.setSingleLine(
        true
);


// =====================================================
// COUNTRY SELECTOR BACKGROUND
// =====================================================

GradientDrawable countrySelectorBg =
        new GradientDrawable(
                GradientDrawable.Orientation.LEFT_RIGHT,
                new int[]{
                        Color.rgb(105, 170, 255),
                        Color.rgb(125, 90, 205)
                }
        );

countrySelectorBg.setCornerRadius(
        dp(8)
);

countrySelector.setBackground(
        countrySelectorBg
);


// =====================================================
// COUNTRY SELECTOR SIZE
// =====================================================

LinearLayout.LayoutParams countrySelectorP =
        new LinearLayout.LayoutParams(
                -2,
                dp(30)
        );

countrySelectorP.topMargin =
        dp(2);

// =====================================================
// SCHENGEN COUNTRIES
// =====================================================

countrySelector.setOnClickListener(
        v -> {

            final String[] countries = {

                    "🇦🇹  Austria",
                    "🇧🇪  Belgium",
                    "🇧🇬  Bulgaria",
                    "🇭🇷  Croatia",
                    "🇨🇿  Czech Republic",
                    "🇩🇰  Denmark",
                    "🇪🇪  Estonia",
                    "🇫🇮  Finland",
                    "🇫🇷  France",
                    "🇩🇪  Germany",
                    "🇬🇷  Greece",
                    "🇭🇺  Hungary",
                    "🇮🇸  Iceland",
                    "🇮🇹  Italy",
                    "🇱🇻  Latvia",
                    "🇱🇮  Liechtenstein",
                    "🇱🇹  Lithuania",
                    "🇱🇺  Luxembourg",
                    "🇲🇹  Malta",
                    "🇳🇱  Netherlands",
                    "🇳🇴  Norway",
                    "🇵🇱  Poland",
                    "🇵🇹  Portugal",
                    "🇷🇴  Romania",
                    "🇸🇰  Slovakia",
                    "🇸🇮  Slovenia",
                    "🇪🇸  Spain",
                    "🇸🇪  Sweden",
                    "🇨🇭  Switzerland"
            };

            AlertDialog.Builder builder =
                    new AlertDialog.Builder(this);

           builder.setItems(
        countries,
        (dialog, which) -> {

            String selectedCountry =
                    countries[which];

            countrySelector.setText(
                    selectedCountry + "  ⌄"
            );

            // Save selected country
            getSharedPreferences(
                    "RV_SETTINGS",
                    MODE_PRIVATE
            )
                    .edit()
                    .putString(
                            "selected_country",
                            selectedCountry
                    )
                    .apply();

        }
); 

            builder.show();
        }
);


// =====================================================
// ADD COUNTRY SELECTOR
// =====================================================

curMid.addView(
        countrySelector,
        countrySelectorP
);


// =====================================================
// ADD CENTER CONTENT
// =====================================================

curTop.addView(
        curMid
);

// =====================================================
// ADD CURRENT APPLICATION
// =====================================================

content.addView(curTop);



// =====================================================
// APPOINTMENT MONITORING
// =====================================================

LinearLayout monCard = card();

GradientDrawable monBg =
        new GradientDrawable(
                GradientDrawable.Orientation.RIGHT_LEFT,
                new int[]{
                        Color.rgb(224, 244, 255),
                        Color.rgb(242, 249, 255)
                }
        );

monBg.setCornerRadius(
        dp(20)
);

monCard.setBackground(
        monBg
);

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


// =====================================================
// MONITORING ICON
// =====================================================

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


// =====================================================
// MONITORING TEXT
// =====================================================

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

monText.addView(
        monTitle
);


TextView monSub =
        text(
                "Monitoring is paused",
                12,
                GRAY
        );

monText.addView(
        monSub
);

monCard.addView(
        monText
);


// =====================================================
// START BUTTON
// =====================================================

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

startBg.setCornerRadius(
        dp(20)
);

startBtn.setBackground(
        startBg
);

startBtn.setTypeface(
        Typeface.DEFAULT_BOLD
);

monCard.addView(
        startBtn
);


// =====================================================
// START MONITORING
// =====================================================

startBtn.setOnClickListener(
        new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent serviceIntent =
                        new Intent(
                                MainActivity.this,
                                AppointmentMonitoringService.class
                        );

                serviceIntent.putExtra(
                        "center",
                        selectedCenter.length() == 0
                                ? "Algiers"
                                : selectedCenter
                );

                ContextCompat.startForegroundService(
                        MainActivity.this,
                        serviceIntent
                );

                Toast.makeText(
                        MainActivity.this,
                        "Monitoring started",
                        Toast.LENGTH_SHORT
                ).show();
            }
        }
);


// =====================================================
// ADD MONITORING CARD
// =====================================================

content.addView(
        monCard,
        margin(
                0,
                0,
                0,
                12
        )
);


// =====================================================
// OFFICIAL VISA PORTAL
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

offBg.setCornerRadius(
        dp(20)
);

offCard.setBackground(
        offBg
);

offCard.setPadding(
        dp(16),
        dp(16),
        dp(16),
        dp(16)
);


// =====================================================
// OFFICIAL TOP
// =====================================================

LinearLayout offTop =
        new LinearLayout(this);

offTop.setOrientation(
        LinearLayout.HORIZONTAL
);

offTop.setGravity(
        Gravity.CENTER_VERTICAL
);


// =====================================================
// OFFICIAL ICON
// =====================================================

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


// =====================================================
// OFFICIAL TEXT
// =====================================================

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
                "Official Visa Portal",
                15,
                NAVY
        );

offTitle.setTypeface(
        Typeface.DEFAULT_BOLD
);

offText.addView(
        offTitle
);


TextView offSub =
        text(
                "Official information and visa services",
                11,
                GRAY
);

offText.addView(
        offSub
);


offTop.addView(
        offText
);

offCard.addView(
        offTop
);


// =====================================================
// OPEN OFFICIAL WEBSITE BUTTON
// =====================================================

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

openBtn.setTypeface(
        Typeface.DEFAULT_BOLD
);


// =====================================================
// OPEN BUTTON BACKGROUND
// =====================================================

GradientDrawable openBg =
        new GradientDrawable(
                GradientDrawable.Orientation.TL_BR,
                new int[]{
                        Color.rgb(60, 120, 255),
                        Color.rgb(85, 75, 210)
                }
        );

openBg.setCornerRadius(
        dp(14)
);

openBtn.setBackground(
        openBg
);


// =====================================================
// OPEN SELECTED COUNTRY PORTAL
// =====================================================

openBtn.setOnClickListener(
        v -> {

            String selectedCountry =
                    getSharedPreferences(
                            "RV_SETTINGS",
                            MODE_PRIVATE
                    )
                    .getString(
                            "selected_country",
                            ""
                    );


            // =================================================
            // NO COUNTRY SELECTED
            // =================================================

            if (selectedCountry.isEmpty()) {

                new AlertDialog.Builder(this)
                        .setTitle(
                                "Select Country"
                        )
                        .setMessage(
                                "Please select a country first."
                        )
                        .setPositiveButton(
                                "OK",
                                null
                        )
                        .show();

                return;
            }


            // =================================================
            // GET OFFICIAL PORTAL
            // =================================================

            String visaUrl =
                    getVisaPortalUrl(
                            selectedCountry
                    );


            // =================================================
            // PORTAL NOT CONFIGURED
            // =================================================

            if (visaUrl == null) {

                new AlertDialog.Builder(this)
                        .setTitle(
                                "Official Visa Portal"
                        )
                        .setMessage(
                                "The official visa portal for "
                                        + selectedCountry
                                        + " is not configured yet."
                        )
                        .setPositiveButton(
                                "OK",
                                null
                        )
                        .show();

                return;
            }


            // =================================================
            // OPEN WEBSITE
            // =================================================

            try {

                Intent browserIntent =
                        new Intent(
                                Intent.ACTION_VIEW,
                                android.net.Uri.parse(
                                        visaUrl
                                )
                        );

                startActivity(
                        browserIntent
                );

            } catch (Exception e) {

                new AlertDialog.Builder(this)
                        .setTitle(
                                "Unable to open website"
                        )
                        .setMessage(
                                "Please try again later."
                        )
                        .setPositiveButton(
                                "OK",
                                null
                        )
                        .show();
            }
        }
);


// =====================================================
// ADD OPEN BUTTON TO CARD
// =====================================================

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


// =====================================================
// ADD OFFICIAL CARD
// =====================================================

content.addView(
        offCard,
        margin(
                0,
                0,
                0,
                12
        )
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

// ==============================================
// PREMIUM BOTTOM BACKGROUND
//================================================
GradientDrawable bottomBg = new GradientDrawable(
    GradientDrawable.Orientation.TL_BR,
    new int[]{
        Color.parseColor("#162040"),
        Color.parseColor("#162040")
    }
);
bottomBg.setCornerRadius(0);
bottomBg.setStroke(
    0,
    Color.TRANSPARENT
);
bottom.setBackground(
    bottomBg
);

if (
    Build.VERSION.SDK_INT >=
    Build.VERSION_CODES.LOLLIPOP
) {
    bottom.setElevation(
        dp(8)
    );
}  
// =====================================================
// BOTTOM NAV ITEMS
// =====================================================

LinearLayout homeItem =
        navItem(
                R.drawable.ic_home,
                "Home",
                true
        );

LinearLayout activityItem =
        navItem(
                R.drawable.ic_activity,
                "Monitoring",
                false
        );

LinearLayout notificationsItem =
        navItem(
                R.drawable.ic_notifications,
                "Notifications",
                false
        );

LinearLayout profileItem =
        navItem(
                R.drawable.ic_profile,
                "Profile",
                false
        );

bottom.addView(homeItem);
bottom.addView(activityItem);
bottom.addView(notificationsItem);
bottom.addView(profileItem);
// =====================================================
// ADD BOTTOM NAVIGATION TO MAIN SHELL
// =====================================================

mainShell.addView(
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
// =====================================================
// APPLICATIONS — GLOBAL
// =====================================================

private void showApplications() {

    currentPage = "APPLICATIONS";

    // =================================================
    // ROOT
    // =================================================

    LinearLayout applicationsRoot =
            new LinearLayout(this);

    applicationsRoot.setOrientation(
            LinearLayout.VERTICAL
    );

    GradientDrawable applicationsBackground =
            new GradientDrawable(
                    GradientDrawable.Orientation.TL_BR,
                    new int[]{
                            Color.rgb(242, 246, 255),
                            Color.rgb(235, 241, 255),
                            Color.rgb(246, 240, 252)
                    }
            );

    applicationsRoot.setBackground(
            applicationsBackground
    );

    // =================================================
    // HEADER
    // =================================================

    LinearLayout header =
            new LinearLayout(this);

    header.setOrientation(
            LinearLayout.HORIZONTAL
    );

    header.setGravity(
            Gravity.CENTER_VERTICAL
    );

    header.setPadding(
            dp(18),
            dp(14),
            dp(18),
            dp(8)
    );

    // BACK BUTTON
    TextView backButton =
            text(
                    "‹",
                    34,
                    Color.parseColor("#102B52")
            );

    backButton.setGravity(
            Gravity.CENTER
    );

    header.addView(
            backButton,
            new LinearLayout.LayoutParams(
                    dp(42),
                    dp(42)
            )
    );

    backButton.setOnClickListener(
            new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    showHome();
                }
            }
    );

    // HEADER TEXT
    LinearLayout headerText =
            new LinearLayout(this);

    headerText.setOrientation(
            LinearLayout.VERTICAL
    );

    headerText.setGravity(
            Gravity.CENTER_VERTICAL
    );

    LinearLayout.LayoutParams headerTextParams =
            new LinearLayout.LayoutParams(
                    0,
                    dp(52),
                    1f
            );

    headerTextParams.setMargins(
            dp(8),
            0,
            dp(8),
            0
    );

    header.addView(
            headerText,
            headerTextParams
    );

    TextView title =
            text(
                    "My Applications",
                    23,
                    Color.parseColor("#102B52")
            );

    title.setTypeface(
            Typeface.create(
                    "sans-serif",
                    Typeface.BOLD
            )
    );

    headerText.addView(
            title
    );

    TextView subtitle =
            text(
                    "Manage your global visa applications",
                    12,
                    Color.parseColor("#71809A")
            );

    headerText.addView(
            subtitle
    );

    // GLOBAL BADGE
    TextView globalBadge =
            text(
                    "GLOBAL",
                    9,
                    Color.WHITE
            );

    globalBadge.setGravity(
            Gravity.CENTER
    );

    globalBadge.setTypeface(
            Typeface.create(
                    "sans-serif",
                    Typeface.BOLD
            )
    );

    globalBadge.setLetterSpacing(
            0.08f
    );

    GradientDrawable globalBg =
            new GradientDrawable(
                    GradientDrawable.Orientation.TL_BR,
                    new int[]{
                            Color.parseColor("#7C3AED"),
                            Color.parseColor("#2563EB")
                    }
            );

    globalBg.setCornerRadius(
            dp(7)
    );

    globalBadge.setBackground(
            globalBg
    );

    header.addView(
            globalBadge,
            new LinearLayout.LayoutParams(
                    dp(54),
                    dp(22)
            )
    );
// =================================================
// APPLICATION SUMMARY
// =================================================

LinearLayout summaryRow =
        new LinearLayout(this);

summaryRow.setOrientation(
        LinearLayout.HORIZONTAL
);

summaryRow.setGravity(
        Gravity.CENTER
);

summaryRow.setPadding(
        dp(18),
        dp(4),
        dp(18),
        dp(4)
);

// TOTAL
LinearLayout totalCard =
        applicationSummaryCard(
                "0",
                "Total",
                "#2563EB"
        );

// DRAFT
LinearLayout draftCard =
        applicationSummaryCard(
                "0",
                "Draft",
                "#7C3AED"
        );

// BOOKED
LinearLayout bookedCard =
        applicationSummaryCard(
                "0",
                "Booked",
                "#059669"
        );

summaryRow.addView(
        totalCard,
        new LinearLayout.LayoutParams(
                0,
                dp(72),
                1f
        )
);

summaryRow.addView(
        draftCard,
        new LinearLayout.LayoutParams(
                0,
                dp(72),
                1f
        )
);

summaryRow.addView(
        bookedCard,
        new LinearLayout.LayoutParams(
                0,
                dp(72),
                1f
        )
);
   applicationsRoot.addView(
        summaryRow
); 
    // =================================================
    // CONTENT SCROLL
    // =================================================

    ScrollView scroll =
            new ScrollView(this);

    scroll.setFillViewport(true);

    scroll.setVerticalScrollBarEnabled(
            false
    );

    LinearLayout content =
            new LinearLayout(this);

    content.setOrientation(
            LinearLayout.VERTICAL
    );

    content.setPadding(
            dp(18),
            dp(8),
            dp(18),
            dp(30)
    );

    scroll.addView(
            content,
            new ScrollView.LayoutParams(
                    -1,
                    -2
            )
    );

    // =================================================
    // HERO CARD
    // =================================================

    LinearLayout heroCard =
            new LinearLayout(this);

    heroCard.setOrientation(
            LinearLayout.VERTICAL
    );

    heroCard.setPadding(
            dp(20),
            dp(20),
            dp(20),
            dp(20)
    );

    GradientDrawable heroBg =
            new GradientDrawable(
                    GradientDrawable.Orientation.TL_BR,
                    new int[]{
                            Color.parseColor("#182B68"),
                            Color.parseColor("#3556B8"),
                            Color.parseColor("#6B4FD3")
                    }
            );

    heroBg.setCornerRadius(
            dp(22)
    );

    heroCard.setBackground(
            heroBg
    );

    TextView heroTitle =
            text(
                    "Your visa journey",
                    21,
                    Color.WHITE
            );

    heroTitle.setTypeface(
            Typeface.DEFAULT_BOLD
    );

    heroCard.addView(
            heroTitle
    );

    TextView heroSubtitle =
            text(
                    "Keep applications, documents and appointment details organized in one place.",
                    13,
                    Color.parseColor("#DDE6FF")
            );

    heroSubtitle.setLineSpacing(
            0,
            1.15f
    );

    LinearLayout.LayoutParams heroSubtitleParams =
            new LinearLayout.LayoutParams(
                    -1,
                    -2
            );

    heroSubtitleParams.setMargins(
            0,
            dp(7),
            0,
            dp(16)
    );

    heroCard.addView(
            heroSubtitle,
            heroSubtitleParams
    );

    // NEW APPLICATION BUTTON
    TextView newApplication =
            text(
                    "+  New Application",
                    14,
                    Color.parseColor("#182B68")
            );

    newApplication.setGravity(
            Gravity.CENTER
    );

    newApplication.setTypeface(
            Typeface.DEFAULT_BOLD
    );

    GradientDrawable newApplicationBg =
            new GradientDrawable();

    newApplicationBg.setColor(
            Color.WHITE
    );

    newApplicationBg.setCornerRadius(
            dp(12)
    );

    newApplication.setBackground(
            newApplicationBg
    );

    heroCard.addView(
            newApplication,
            new LinearLayout.LayoutParams(
                    -1,
                    dp(46)
            )
    );

    content.addView(
            heroCard,
            new LinearLayout.LayoutParams(
                    -1,
                    -2
            )
    );

    // =================================================
    // SECTION TITLE
    // =================================================

    TextView applicationsSection =
            text(
                    "Applications",
                    18,
                    Color.parseColor("#102B52")
            );

    applicationsSection.setTypeface(
            Typeface.DEFAULT_BOLD
    );

    LinearLayout.LayoutParams sectionParams =
            new LinearLayout.LayoutParams(
                    -1,
                    -2
            );

    sectionParams.setMargins(
            dp(2),
            dp(22),
            dp(2),
            dp(10)
    );

    content.addView(
            applicationsSection,
            sectionParams
    );

    // =================================================
    // EMPTY STATE CARD
    // =================================================

    LinearLayout emptyCard =
            new LinearLayout(this);

    emptyCard.setOrientation(
            LinearLayout.VERTICAL
    );

    emptyCard.setGravity(
            Gravity.CENTER
    );

    emptyCard.setPadding(
            dp(20),
            dp(28),
            dp(20),
            dp(28)
    );

    GradientDrawable emptyBg =
            new GradientDrawable();

    emptyBg.setColor(
            Color.WHITE
    );

    emptyBg.setCornerRadius(
            dp(20)
    );

    emptyBg.setStroke(
            dp(1),
            Color.parseColor("#E1E8F5")
    );

    emptyCard.setBackground(
            emptyBg
    );

    // ICON
    FrameLayout emptyIconBox =
            new FrameLayout(this);

    GradientDrawable emptyIconBg =
            new GradientDrawable();

    emptyIconBg.setShape(
            GradientDrawable.OVAL
    );

    emptyIconBg.setColor(
            Color.parseColor("#EDE9FE")
    );

    emptyIconBox.setBackground(
            emptyIconBg
    );

    ImageView emptyIcon =
            new ImageView(this);

    emptyIcon.setImageResource(
            R.drawable.ic_applications_premium
    );

    emptyIcon.setScaleType(
            ImageView.ScaleType.CENTER
    );

    emptyIconBox.addView(
            emptyIcon,
            new FrameLayout.LayoutParams(
                    -1,
                    -1
            )
    );

    emptyCard.addView(
            emptyIconBox,
            new LinearLayout.LayoutParams(
                    dp(58),
                    dp(58)
            )
    );

    TextView emptyTitle =
            text(
                    "No applications yet",
                    17,
                    Color.parseColor("#102B52")
            );

    emptyTitle.setTypeface(
            Typeface.DEFAULT_BOLD
    );

    emptyTitle.setGravity(
            Gravity.CENTER
    );

    LinearLayout.LayoutParams emptyTitleParams =
            new LinearLayout.LayoutParams(
                    -1,
                    -2
            );

    emptyTitleParams.setMargins(
            0,
            dp(14),
            0,
            dp(5)
    );

    emptyCard.addView(
            emptyTitle,
            emptyTitleParams
    );

    TextView emptySubtitle =
            text(
                    "Create your first application and keep your visa journey organized.",
                    12,
                    Color.parseColor("#71809A")
            );

    emptySubtitle.setGravity(
            Gravity.CENTER
    );

    emptySubtitle.setLineSpacing(
            0,
            1.15f
    );

    emptyCard.addView(
            emptySubtitle,
            new LinearLayout.LayoutParams(
                    -1,
                    -2
            )
    );

    content.addView(
            emptyCard,
            new LinearLayout.LayoutParams(
                    -1,
                    -2
            )
    );

    // =================================================
    // GLOBAL SUPPORT
    // =================================================

    TextView supportedTitle =
            text(
                    "Supported destinations",
                    17,
                    Color.parseColor("#102B52")
            );

    supportedTitle.setTypeface(
            Typeface.DEFAULT_BOLD
    );

    LinearLayout.LayoutParams supportedTitleParams =
            new LinearLayout.LayoutParams(
                    -1,
                    -2
            );

    supportedTitleParams.setMargins(
            dp(2),
            dp(22),
            dp(2),
            dp(10)
    );

    content.addView(
            supportedTitle,
            supportedTitleParams
    );

    TextView supportedText =
            text(
                    "Schengen  •  USA  •  Canada  •  UK",
                    13,
                    Color.parseColor("#596B86")
            );

    supportedText.setGravity(
            Gravity.CENTER
    );

    GradientDrawable supportedBg =
            new GradientDrawable();

    supportedBg.setColor(
            Color.parseColor("#F8FAFF")
    );

    supportedBg.setCornerRadius(
            dp(14)
    );

    supportedBg.setStroke(
            dp(1),
            Color.parseColor("#E1E8F5")
    );

    supportedText.setBackground(
            supportedBg
    );

    content.addView(
            supportedText,
            new LinearLayout.LayoutParams(
                    -1,
                    dp(48)
            )
    );

    applicationsRoot.addView(
            scroll,
            new LinearLayout.LayoutParams(
                    -1,
                    0,
                    1f
            )
    );

    // =================================================
    // SHOW
    // =================================================

    setContentView(
            applicationsRoot
    );
}   
   // =====================================================
// APPLICATION SUMMARY CARD
// =====================================================

private LinearLayout applicationSummaryCard(
        String number,
        String label,
        String accentColor
) {

    LinearLayout card =
            new LinearLayout(this);

    card.setOrientation(
            LinearLayout.VERTICAL
    );

    card.setGravity(
            Gravity.CENTER
    );

    GradientDrawable bg =
            new GradientDrawable();

    bg.setColor(
            Color.WHITE
    );

    bg.setCornerRadius(
            dp(16)
    );

    bg.setStroke(
            dp(1),
            Color.parseColor("#E1E8F5")
    );

    card.setBackground(
            bg
    );

    // NUMBER
    TextView numberText =
            text(
                    number,
                    21,
                    Color.parseColor(accentColor)
            );

    numberText.setGravity(
            Gravity.CENTER
    );

    numberText.setTypeface(
            Typeface.create(
                    "sans-serif",
                    Typeface.BOLD
            )
    );

    card.addView(
            numberText,
            new LinearLayout.LayoutParams(
                    -1,
                    dp(30)
            )
    );

    // LABEL
    TextView labelText =
            text(
                    label,
                    11,
                    Color.parseColor("#71809A")
            );

    labelText.setGravity(
            Gravity.CENTER
    );

    labelText.setTypeface(
            Typeface.create(
                    "sans-serif",
                    Typeface.BOLD
            )
    );

    card.addView(
            labelText,
            new LinearLayout.LayoutParams(
                    -1,
                    dp(22)
            )
    );

    return card;
} 
// =====================================================
// MAIN MENU - NAVIGATION DRAWER
// =====================================================

private void showMainMenu() {

    // =================================================
    // PREVENT DUPLICATE DRAWER
    // =================================================

    View existingDrawer =
            getWindow()
                    .getDecorView()
                    .findViewWithTag("MAIN_DRAWER");

    if (existingDrawer != null) {
        return;
    }


    // =================================================
    // FULL SCREEN OVERLAY
    // =================================================

    final FrameLayout drawerOverlay =
            new FrameLayout(this);

    drawerOverlay.setTag(
            "MAIN_DRAWER"
    );

    drawerOverlay.setBackgroundColor(
            Color.TRANSPARENT
    );


    // =================================================
    // DARK SCRIM
    // =================================================

    final View scrim =
            new View(this);

    scrim.setBackgroundColor(
            Color.parseColor("#66000000")
    );

    scrim.setAlpha(0f);


    drawerOverlay.addView(
            scrim,
            new FrameLayout.LayoutParams(
                    -1,
                    -1
            )
    );


    // =================================================
    // DRAWER
    // =================================================

    final int screenWidth =
            getResources()
                    .getDisplayMetrics()
                    .widthPixels;

    final int drawerWidth =
        (int) (screenWidth * 0.70f);

    final LinearLayout drawer =
            new LinearLayout(this);

    drawer.setOrientation(
            LinearLayout.VERTICAL
    );

    drawer.setPadding(
        0,
        0,
        0,
        dp(20)
);

    drawer.setBackgroundColor(
            Color.rgb(
                    245,
                    247,
                    252
            )
    );

    drawer.setElevation(
            dp(16)
    );


    // =================================================
// DRAWER HEADER
// =================================================

LinearLayout drawerHeader =
        new LinearLayout(this);

drawerHeader.setOrientation(
        LinearLayout.HORIZONTAL
);

drawerHeader.setGravity(
        Gravity.CENTER_VERTICAL
);

drawerHeader.setPadding(
        dp(16),
        dp(14),
        dp(16),
        dp(14)
);


// =================================================
// PREMIUM EARTH SPACE BACKGROUND
// =================================================

drawerHeader.setBackground(
        new EarthSpaceDrawable()
);

// =================================================
// RV ORBIT PREMIUM LOGO
// =================================================

FrameLayout rvLogoBox =
        new FrameLayout(this);


// =================================================
// PREMIUM GLASS LOGO BACKGROUND
// =================================================

GradientDrawable rvLogoBg =
        new GradientDrawable();

rvLogoBg.setShape(
        GradientDrawable.OVAL
);

rvLogoBg.setColor(
        Color.argb(
                55,
                90,
                150,
                230
        )
);

rvLogoBg.setStroke(
        dp(1),
        Color.argb(
                120,
                150,
                205,
                255
        )
);

rvLogoBox.setBackground(
        rvLogoBg
);


// =================================================
// PREMIUM RV TEXT
// =================================================

TextView rvLogoText =
        text(
                "RV",
                18,
                Color.WHITE
        );

rvLogoText.setGravity(
        Gravity.CENTER
);

rvLogoText.setTypeface(
        Typeface.create(
                "sans-serif",
                Typeface.BOLD
        )
);

rvLogoText.setLetterSpacing(
        0.10f
);

rvLogoText.setShadowLayer(
        dp(4),
        0,
        0,
        Color.argb(
                170,
                100,
                190,
                255
        )
);


// =================================================
// RV LETTER SPACING
// =================================================

rvLogoText.setLetterSpacing(
        0.08f
);


rvLogoBox.addView(
        rvLogoText,
        new FrameLayout.LayoutParams(
                -1,
                -1
        )
);


// =================================================
// PREMIUM ORBIT RING
// =================================================

View orbitRing =
        new View(this);

GradientDrawable orbitRingBg =
        new GradientDrawable();

orbitRingBg.setShape(
        GradientDrawable.OVAL
);

orbitRingBg.setColor(
        Color.TRANSPARENT
);

orbitRingBg.setStroke(
        dp(1),
        Color.argb(
                105,
                190,
                225,
                255
        )
);

orbitRing.setBackground(
        orbitRingBg
);


// =================================================
// ORBIT SIZE
// =================================================

FrameLayout.LayoutParams orbitRingParams =
        new FrameLayout.LayoutParams(
                dp(42),
                dp(19)
        );

orbitRingParams.gravity =
        Gravity.CENTER;

orbitRing.setRotation(
        -28f
);

rvLogoBox.addView(
        orbitRing,
        orbitRingParams
);


// =================================================
// SECOND ORBIT ACCENT
// =================================================

View orbitAccent =
        new View(this);

GradientDrawable orbitAccentBg =
        new GradientDrawable();

orbitAccentBg.setShape(
        GradientDrawable.OVAL
);

orbitAccentBg.setColor(
        Color.TRANSPARENT
);

orbitAccentBg.setStroke(
        dp(1),
        Color.argb(
                55,
                120,
                185,
                255
        )
);

orbitAccent.setBackground(
        orbitAccentBg
);


FrameLayout.LayoutParams orbitAccentParams =
        new FrameLayout.LayoutParams(
                dp(35),
                dp(14)
        );

orbitAccentParams.gravity =
        Gravity.CENTER;

orbitAccent.setRotation(
        -28f
);

rvLogoBox.addView(
        orbitAccent,
        orbitAccentParams
);


// =================================================
// ORBIT LIGHT
// =================================================

View orbitLight =
        new View(this);

GradientDrawable orbitLightBg =
        new GradientDrawable();

orbitLightBg.setShape(
        GradientDrawable.OVAL
);

orbitLightBg.setColor(
        Color.rgb(
                210,
                240,
                255
        )
);

orbitLightBg.setStroke(
        dp(1),
        Color.argb(
                170,
                140,
                205,
                255
        )
);

orbitLight.setBackground(
        orbitLightBg
);


// =================================================
// LIGHT POSITION
// =================================================

FrameLayout.LayoutParams orbitLightParams =
        new FrameLayout.LayoutParams(
                dp(5),
                dp(5)
        );

orbitLightParams.gravity =
        Gravity.TOP | Gravity.END;

orbitLightParams.setMargins(
        0,
        dp(7),
        dp(4),
        0
);

rvLogoBox.addView(
        orbitLight,
        orbitLightParams
);


// =================================================
// SMALL ACCENT
// =================================================

View rvAccent =
        new View(this);

GradientDrawable accentBg =
        new GradientDrawable();

accentBg.setColor(
        Color.parseColor("#8BB8FF")
);

accentBg.setCornerRadius(
        dp(2)
);

FrameLayout.LayoutParams accentParams =
        new FrameLayout.LayoutParams(
                dp(14),
                dp(3)
        );

accentParams.gravity =
        Gravity.BOTTOM | Gravity.END;

accentParams.setMargins(
        0,
        0,
        dp(7),
        dp(6)
);

rvLogoBox.addView(
        rvAccent,
        accentParams
);

// =================================================
// ADD LOGO
// =================================================

drawerHeader.addView(
        rvLogoBox,
        new LinearLayout.LayoutParams(
                dp(48),
                dp(48)
        )
);

// =================================================
// HEADER TEXT
// =================================================

LinearLayout headerText =
        new LinearLayout(this);

headerText.setOrientation(
        LinearLayout.VERTICAL
);

headerText.setGravity(
        Gravity.CENTER_VERTICAL
);

headerText.setPadding(
        dp(12),
        0,
        0,
        0
);

// =================================================
// PREMIUM TITLE
// =================================================

TextView drawerTitle =
        text(
                "Visa Slot Tracker",
                18,
                Color.WHITE
        );

drawerTitle.setTypeface(
        Typeface.create(
                "sans-serif",
                Typeface.BOLD
        )
);

drawerTitle.setLetterSpacing(
        0.045f
);

drawerTitle.setShadowLayer(
        dp(5),
        0,
        dp(2),
        Color.argb(
                120,
                0,
                0,
                0
        )
);

drawerTitle.setGravity(
        Gravity.CENTER_VERTICAL
);

headerText.addView(
        drawerTitle,
        new LinearLayout.LayoutParams(
                -1,
                dp(27)
        )
);

// =================================================
// PREMIUM SUBTITLE
// =================================================

TextView drawerSubtitle =
        text(
                "Smart appointment monitoring",
                10,
                Color.parseColor("#B8C7E6")
        );

drawerSubtitle.setGravity(
        Gravity.CENTER_VERTICAL
);

drawerSubtitle.setLetterSpacing(
        0.025f
);

LinearLayout.LayoutParams subtitleParams =
        new LinearLayout.LayoutParams(
                -1,
                dp(20)
        );

subtitleParams.setMargins(
        dp(-1),
        0,
        0,
        0
);

headerText.addView(
        drawerSubtitle,
        subtitleParams
);

// =================================================
// ADD HEADER TEXT
// =================================================

LinearLayout.LayoutParams textParams =
        new LinearLayout.LayoutParams(
                0,
                -2,
                1f
        );

drawerHeader.addView(
        headerText,
        textParams
);


// =================================================
// ADD HEADER
// =================================================

LinearLayout.LayoutParams headerParams =
        new LinearLayout.LayoutParams(
                -1,
                dp(120)
        );

headerParams.setMargins(
        0,
        0,
        0,
        dp(10)
);

drawer.addView(
        drawerHeader,
        headerParams
);
// =================================================
// DRAWER CONTENT
// =================================================

LinearLayout drawerContent =
        new LinearLayout(this);

drawerContent.setOrientation(
        LinearLayout.VERTICAL
);   
   // =================================================
// DRAWER SCROLL
// =================================================

ScrollView drawerScroll =
        new ScrollView(this);

drawerScroll.setFillViewport(
        true
);

drawerScroll.setVerticalScrollBarEnabled(
        false
);

drawerScroll.addView(
        drawerContent,
        new ScrollView.LayoutParams(
                -1,
                -2
        )
);
  drawer.addView(
        drawerScroll,
        new LinearLayout.LayoutParams(
                -1,
                0,
                1f
        )
);
    
// =====================================================
// DASHBOARD
// =====================================================

LinearLayout dashboardItem =
        new LinearLayout(this);

dashboardItem.setOrientation(
        LinearLayout.HORIZONTAL
);

dashboardItem.setGravity(
        Gravity.CENTER_VERTICAL
);

dashboardItem.setPadding(
        dp(12),
        0,
        dp(12),
        0
);

// =====================================================
// DASHBOARD PREMIUM BACKGROUND
// =====================================================

GradientDrawable dashboardBg =
        new GradientDrawable(
                GradientDrawable.Orientation.TL_BR,
                new int[]{
                        Color.parseColor("#F4F8FF"),
                        Color.parseColor("#E3EEFF")
                }
        );

dashboardBg.setCornerRadius(
        dp(16)
);


dashboardItem.setBackground(
        dashboardBg
);
dashboardItem.setElevation(
        dp(2)
);
  
// =====================================================
// DASHBOARD ACTIVE LEFT ACCENT
// =====================================================

View dashboardLeftAccent =
        new View(this);

GradientDrawable dashboardLeftAccentBg =
        new GradientDrawable();

dashboardLeftAccentBg.setColor(
        Color.parseColor("#2F80ED")
);

dashboardLeftAccentBg.setCornerRadii(
        new float[]{
                dp(4), dp(4),
                0, 0,
                0, 0,
                dp(4), dp(4)
        }
);

dashboardLeftAccent.setBackground(
        dashboardLeftAccentBg
);

LinearLayout.LayoutParams dashboardLeftAccentParams =
        new LinearLayout.LayoutParams(
                dp(4),
                dp(42)
        );

dashboardLeftAccentParams.gravity =
        Gravity.CENTER_VERTICAL;

dashboardItem.addView(
        dashboardLeftAccent,
        0,
        dashboardLeftAccentParams
);
    
// =====================================================
// DASHBOARD ICON BOX
// =====================================================

FrameLayout dashboardIconBox =
        new FrameLayout(this);

GradientDrawable dashboardIconBg =
        new GradientDrawable();

dashboardIconBg.setShape(
        GradientDrawable.OVAL
);

dashboardIconBg.setColor(
        Color.parseColor("#D8E9FF")
);

dashboardIconBg.setStroke(
        dp(1),
        Color.parseColor("#C7DDFF")
);

dashboardIconBox.setBackground(
        dashboardIconBg
);

// =====================================================
// DASHBOARD ICON
// =====================================================

ImageView dashboardIcon =
        new ImageView(this);

dashboardIcon.setImageResource(
        R.drawable.ic_dashboard_premium
);

dashboardIcon.setScaleType(
        ImageView.ScaleType.CENTER
);

dashboardIconBox.addView(
        dashboardIcon,
        new FrameLayout.LayoutParams(
                -1,
                -1
        )
);

LinearLayout.LayoutParams dashboardIconParams =
        new LinearLayout.LayoutParams(
                dp(42),
                dp(42)
        );

dashboardIconParams.setMargins(
        dp(10),
        0,
        0,
        0
);

dashboardItem.addView(
        dashboardIconBox,
        dashboardIconParams
);

// =====================================================
// DASHBOARD TEXT CONTAINER
// =====================================================

LinearLayout dashboardTextContainer =
        new LinearLayout(this);

dashboardTextContainer.setOrientation(
        LinearLayout.VERTICAL
);

dashboardTextContainer.setGravity(
        Gravity.CENTER_VERTICAL
);

// =====================================================
// DASHBOARD TITLE
// =====================================================

TextView dashboardText =
        text(
                "Dashboard",
                16,
                Color.parseColor("#102B52")
        );

dashboardText.setTypeface(
        Typeface.create(
                "sans-serif-medium",
                Typeface.NORMAL
        )
);

dashboardText.setLetterSpacing(
        0.01f
);

// =====================================================
// ADD TEXTS
// =====================================================

dashboardTextContainer.addView(
        dashboardText,
        new LinearLayout.LayoutParams(
                -1,
                dp(22)
        )
);


// =====================================================
// TEXT CONTAINER PARAMS
// =====================================================

LinearLayout.LayoutParams dashboardTextParams =
        new LinearLayout.LayoutParams(
                0,
                -1,
                1f
);

dashboardTextParams.setMargins(
        dp(14),
        0,
        dp(8),
        0
);

// =====================================================
// ADD TEXT CONTAINER
// =====================================================

dashboardItem.addView(
        dashboardTextContainer,
        dashboardTextParams
);

// =====================================================
// DASHBOARD ARROW
// =====================================================

TextView dashboardArrow =
        text(
                "›",
                25,
                Color.parseColor("#4A75B5")
        );

dashboardArrow.setGravity(
        Gravity.CENTER
);

dashboardArrow.setTypeface(
        Typeface.create(
                "sans-serif",
                Typeface.NORMAL
        )
);

dashboardItem.addView(
        dashboardArrow,
        new LinearLayout.LayoutParams(
                dp(26),
                -1
        )
);

// =====================================================
// DASHBOARD MARGINS
// =====================================================

LinearLayout.LayoutParams dashboardParams =
        new LinearLayout.LayoutParams(
                -1,
                dp(62)
        );

dashboardParams.setMargins(
        dp(16),
        dp(2),
        dp(16),
        dp(6)
);

// =====================================================
// ADD DASHBOARD
// =====================================================

drawerContent.addView(
        dashboardItem,
        dashboardParams
);
// =====================================================
// DASHBOARD CLICK
// =====================================================

dashboardItem.setOnClickListener(
        v -> {

            drawerOverlay.animate()
                    .translationX(-drawerOverlay.getWidth())
                    .setDuration(250)
                    .withEndAction(() -> {

                        drawerOverlay.setVisibility(
                                View.GONE
                        );

                        ViewParent parent =
                                drawerOverlay.getParent();

                        if (parent instanceof ViewGroup) {
                            ((ViewGroup) parent).removeView(
                                    drawerOverlay
                            );
                        }

                    })
                    .start();
        }
);
// =====================================================
// SLOTS AVAILABILITY
// =====================================================

LinearLayout slotsItem =
        new LinearLayout(this);

slotsItem.setOrientation(
        LinearLayout.HORIZONTAL
);

slotsItem.setGravity(
        Gravity.CENTER_VERTICAL
);

slotsItem.setPadding(
        dp(12),
        0,
        dp(12),
        0
);

// =====================================================
// PREMIUM BACKGROUND
// =====================================================

GradientDrawable slotsBg =
        new GradientDrawable();

slotsBg.setColor(
        Color.parseColor("#EEF7FF")
);

slotsBg.setCornerRadius(
        dp(16)
);

slotsBg.setStroke(
        dp(1),
        Color.parseColor("#D8EBFF")
);

slotsItem.setBackground(
        slotsBg
);

// =====================================================
// ICON BOX
// =====================================================

FrameLayout slotsIconBox =
        new FrameLayout(this);

GradientDrawable slotsIconBg =
        new GradientDrawable();

slotsIconBg.setShape(
        GradientDrawable.OVAL
);

slotsIconBg.setColor(
        Color.parseColor("#D5ECFF")
);

slotsIconBox.setBackground(
        slotsIconBg
);

// =====================================================
// ICON
// =====================================================

ImageView slotsIcon =
        new ImageView(this);

slotsIcon.setImageResource(
        R.drawable.ic_slots_premium
);

slotsIcon.setScaleType(
        ImageView.ScaleType.CENTER
);

slotsIconBox.addView(
        slotsIcon,
        new FrameLayout.LayoutParams(
                -1,
                -1
        )
);

slotsItem.addView(
        slotsIconBox,
        new LinearLayout.LayoutParams(
                dp(42),
                dp(42)
        )
);

// =====================================================
// TEXT
// =====================================================

TextView slotsText =
        text(
                "Slots Availability",
                16,
                Color.parseColor("#102B52")
        );

slotsText.setTypeface(
        Typeface.create(
                "sans-serif",
                Typeface.BOLD
        )
);

slotsText.setGravity(
        Gravity.CENTER_VERTICAL
);

slotsText.setLetterSpacing(
        0.01f
);

LinearLayout.LayoutParams slotsTextParams =
        new LinearLayout.LayoutParams(
                0,
                -1,
                1f
);

slotsTextParams.setMargins(
        dp(14),
        0,
        dp(8),
        0
);

slotsItem.addView(
        slotsText,
        slotsTextParams
);

// =====================================================
// ARROW
// =====================================================

TextView slotsArrow =
        text(
                "›",
                28,
                Color.parseColor("#1656A8")
        );

slotsArrow.setGravity(
        Gravity.CENTER
);

slotsItem.addView(
        slotsArrow,
        new LinearLayout.LayoutParams(
                dp(28),
                -1
        )
);

// =====================================================
// MARGINS
// =====================================================

LinearLayout.LayoutParams slotsParams =
        new LinearLayout.LayoutParams(
                -1,
                dp(62)
        );

slotsParams.setMargins(
        dp(16),
        dp(2),
        dp(16),
        dp(6)
);

// =====================================================
// ADD SLOTS
// =====================================================

drawerContent.addView(
        slotsItem,
        slotsParams
);
   // =====================================================
// SLOTS AVAILABILITY CLICK
// =====================================================

slotsItem.setOnClickListener(
        v -> {

            // Close drawer
            ViewParent parent =
                    drawerOverlay.getParent();

            if (parent instanceof ViewGroup) {

                ((ViewGroup) parent).removeView(
                        drawerOverlay
                );
            }

            // Open Appointments
            showAppointments();
        }
); 
// =====================================================
// APPLICATIONS
// =====================================================

LinearLayout applicationsItem =
        new LinearLayout(this);

applicationsItem.setOrientation(
        LinearLayout.HORIZONTAL
);

applicationsItem.setGravity(
        Gravity.CENTER_VERTICAL
);

applicationsItem.setPadding(
        dp(12),
        0,
        dp(12),
        0
);

// =====================================================
// APPLICATIONS PREMIUM BACKGROUND
// =====================================================

GradientDrawable applicationsBg =
        new GradientDrawable();

applicationsBg.setColor(
        Color.parseColor("#F3F0FF")
);

applicationsBg.setCornerRadius(
        dp(16)
);

applicationsBg.setStroke(
        dp(1),
        Color.parseColor("#E1D9FF")
);

applicationsItem.setBackground(
        applicationsBg
);

// =====================================================
// APPLICATIONS ICON BOX
// =====================================================

FrameLayout applicationsIconBox =
        new FrameLayout(this);

GradientDrawable applicationsIconBg =
        new GradientDrawable();

applicationsIconBg.setShape(
        GradientDrawable.OVAL
);

applicationsIconBg.setColor(
        Color.parseColor("#E3DAFF")
);

applicationsIconBox.setBackground(
        applicationsIconBg
);

// =====================================================
// APPLICATIONS ICON
// =====================================================

ImageView applicationsIcon =
        new ImageView(this);

applicationsIcon.setImageResource(
        R.drawable.ic_applications_premium
);

applicationsIcon.setScaleType(
        ImageView.ScaleType.CENTER
);

applicationsIconBox.addView(
        applicationsIcon,
        new FrameLayout.LayoutParams(
                -1,
                -1
        )
);

applicationsItem.addView(
        applicationsIconBox,
        new LinearLayout.LayoutParams(
                dp(42),
                dp(42)
        )
);

// =====================================================
// APPLICATIONS TITLE ROW
// =====================================================

LinearLayout applicationsTitleRow =
        new LinearLayout(this);

applicationsTitleRow.setOrientation(
        LinearLayout.HORIZONTAL
);

applicationsTitleRow.setGravity(
        Gravity.CENTER_VERTICAL
);

// TITLE
TextView applicationsText =
        text(
                "Applications",
                16,
                Color.parseColor("#102B52")
        );

applicationsText.setTypeface(
        Typeface.create(
                "sans-serif",
                Typeface.BOLD
        )
);

applicationsText.setGravity(
        Gravity.CENTER_VERTICAL
);

applicationsText.setLetterSpacing(
        0.01f
);

applicationsTitleRow.addView(
        applicationsText,
        new LinearLayout.LayoutParams(
                -2,
                dp(28)
        )
);

// GLOBAL BADGE
TextView applicationsGlobal =
        text(
                "GLOBAL",
                8,
                Color.WHITE
        );

applicationsGlobal.setGravity(
        Gravity.CENTER
);

applicationsGlobal.setTypeface(
        Typeface.create(
                "sans-serif",
                Typeface.BOLD
        )
);

applicationsGlobal.setLetterSpacing(
        0.08f
);

GradientDrawable applicationsGlobalBg =
        new GradientDrawable(
                GradientDrawable.Orientation.TL_BR,
                new int[]{
                        Color.parseColor("#7C3AED"),
                        Color.parseColor("#4F46E5")
                }
        );

applicationsGlobalBg.setCornerRadius(
        dp(6)
);

applicationsGlobal.setBackground(
        applicationsGlobalBg
);

LinearLayout.LayoutParams globalParams =
        new LinearLayout.LayoutParams(
                dp(48),
                dp(17)
        );

globalParams.setMargins(
        dp(8),
        0,
        0,
        0
);

applicationsTitleRow.addView(
        applicationsGlobal,
        globalParams
);

// ADD TITLE ROW
applicationsItem.addView(
        applicationsTitleRow,
        new LinearLayout.LayoutParams(
                0,
                -1,
                1f
        )
);


// =====================================================
// APPLICATIONS ARROW
// =====================================================

TextView applicationsArrow =
        text(
                "›",
                28,
                Color.parseColor("#6B4FD3")
        );

applicationsArrow.setGravity(
        Gravity.CENTER
);

applicationsArrow.setTypeface(
        Typeface.create(
                "sans-serif",
                Typeface.NORMAL
        )
);

applicationsItem.addView(
        applicationsArrow,
        new LinearLayout.LayoutParams(
                dp(28),
                -1
        )
);

// =====================================================
// APPLICATIONS MARGINS
// =====================================================

LinearLayout.LayoutParams applicationsParams =
        new LinearLayout.LayoutParams(
                -1,
                dp(62)
        );

applicationsParams.setMargins(
        dp(16),
        dp(2),
        dp(16),
        dp(6)
);

// =====================================================
// ADD APPLICATIONS
// =====================================================

drawerContent.addView(
        applicationsItem,
        applicationsParams
);
// =====================================================
// APPLICATIONS CLICK
// =====================================================

applicationsItem.setOnClickListener(
        new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                View drawerView =
                        getWindow()
                                .getDecorView()
                                .findViewWithTag(
                                        "MAIN_DRAWER"
                                );

                if (drawerView != null) {

                    ((ViewGroup) drawerView.getParent())
                            .removeView(drawerView);
                }

                showApplications();
            }
        }
);
// =====================================================
// SMART ALERTS — PRO
// =====================================================

LinearLayout smartAlertsItem =
        new LinearLayout(this);

smartAlertsItem.setOrientation(
        LinearLayout.HORIZONTAL
);

smartAlertsItem.setGravity(
        Gravity.CENTER_VERTICAL
);

smartAlertsItem.setPadding(
        dp(16),
        0,
        dp(12),
        0
);

// =====================================================
// ICON BOX
// =====================================================

FrameLayout smartAlertsIconBox =
        new FrameLayout(this);

GradientDrawable smartAlertsIconBg =
        new GradientDrawable();

smartAlertsIconBg.setShape(
        GradientDrawable.OVAL
);

smartAlertsIconBg.setColor(
        Color.parseColor("#E8F2FF")
);

smartAlertsIconBox.setBackground(
        smartAlertsIconBg
);

// =====================================================
// ICON
// =====================================================

ImageView smartAlertsIcon =
        new ImageView(this);

smartAlertsIcon.setImageResource(
        R.drawable.ic_notifications_premium
);

smartAlertsIcon.setScaleType(
        ImageView.ScaleType.CENTER
);

smartAlertsIconBox.addView(
        smartAlertsIcon,
        new FrameLayout.LayoutParams(
                -1,
                -1
        )
);

smartAlertsItem.addView(
        smartAlertsIconBox,
        new LinearLayout.LayoutParams(
                dp(42),
                dp(42)
        )
);

// =====================================================
// TEXT CONTAINER
// =====================================================

LinearLayout smartAlertsTextContainer =
        new LinearLayout(this);

smartAlertsTextContainer.setOrientation(
        LinearLayout.VERTICAL
);

smartAlertsTextContainer.setGravity(
        Gravity.CENTER_VERTICAL
);

LinearLayout.LayoutParams smartAlertsTextContainerParams =
        new LinearLayout.LayoutParams(
                0,
                -1,
                1f
);

smartAlertsTextContainerParams.setMargins(
        dp(14),
        0,
        dp(8),
        0
);

smartAlertsItem.addView(
        smartAlertsTextContainer,
        smartAlertsTextContainerParams
);

// =====================================================
// TITLE
// =====================================================

TextView smartAlertsText =
        text(
                "Smart Alerts",
                16,
                Color.parseColor("#102B52")
        );

smartAlertsText.setTypeface(
        Typeface.create(
                "sans-serif",
                Typeface.BOLD
        )
);

smartAlertsText.setGravity(
        Gravity.CENTER_VERTICAL
);

smartAlertsText.setLetterSpacing(
        0.01f
);

smartAlertsTextContainer.addView(
        smartAlertsText,
        new LinearLayout.LayoutParams(
                -1,
                dp(28)
        )
);

// =====================================================
// PRO BADGE
// =====================================================

TextView smartAlertsPro =
        text(
                "PRO",
                9,
                Color.WHITE
        );

smartAlertsPro.setGravity(
        Gravity.CENTER
);

smartAlertsPro.setTypeface(
        Typeface.create(
                "sans-serif",
                Typeface.BOLD
        )
);

GradientDrawable smartAlertsProBg =
        new GradientDrawable(
                GradientDrawable.Orientation.TL_BR,
                new int[]{
                        Color.parseColor("#7C3AED"),
                        Color.parseColor("#2563EB")
                }
        );

smartAlertsProBg.setCornerRadius(
        dp(6)
);

smartAlertsPro.setBackground(
        smartAlertsProBg
);

smartAlertsTextContainer.addView(
        smartAlertsPro,
        new LinearLayout.LayoutParams(
                dp(34),
                dp(17)
        )
);

// =====================================================
// ARROW
// =====================================================

TextView smartAlertsArrow =
        text(
                "›",
                28,
                Color.parseColor("#1656A8")
        );

smartAlertsArrow.setGravity(
        Gravity.CENTER
);

smartAlertsItem.addView(
        smartAlertsArrow,
        new LinearLayout.LayoutParams(
                dp(28),
                -1
        )
);

// =====================================================
// MARGINS
// =====================================================

LinearLayout.LayoutParams smartAlertsParams =
        new LinearLayout.LayoutParams(
                -1,
                dp(58)
        );

smartAlertsParams.setMargins(
        dp(16),
        dp(0),
        dp(16),
        dp(2)
);

// =====================================================
// ADD
// =====================================================

drawerContent.addView(
        smartAlertsItem,
        smartAlertsParams
);

    // =====================================================
// DOCUMENTS
// =====================================================

LinearLayout documentsItem =
        new LinearLayout(this);

documentsItem.setOrientation(
        LinearLayout.HORIZONTAL
);

documentsItem.setGravity(
        Gravity.CENTER_VERTICAL
);

documentsItem.setPadding(
        dp(16),
        0,
        dp(12),
        0
);

// =====================================================
// ICON BOX
// =====================================================

FrameLayout documentsIconBox =
        new FrameLayout(this);

GradientDrawable documentsIconBg =
        new GradientDrawable();

documentsIconBg.setShape(
        GradientDrawable.OVAL
);

documentsIconBg.setColor(
        Color.parseColor("#E8F2FF")
);

documentsIconBox.setBackground(
        documentsIconBg
);

// =====================================================
// ICON
// =====================================================

ImageView documentsIcon =
        new ImageView(this);

documentsIcon.setImageResource(
        R.drawable.ic_documents_premium
);

documentsIcon.setScaleType(
        ImageView.ScaleType.CENTER
);

documentsIconBox.addView(
        documentsIcon,
        new FrameLayout.LayoutParams(
                -1,
                -1
        )
);

documentsItem.addView(
        documentsIconBox,
        new LinearLayout.LayoutParams(
                dp(42),
                dp(42)
        )
);

// =====================================================
// TEXT
// =====================================================

TextView documentsText =
        text(
                "Documents",
                16,
                Color.parseColor("#102B52")
        );

documentsText.setTypeface(
        Typeface.create(
                "sans-serif",
                Typeface.BOLD
        )
);

documentsText.setGravity(
        Gravity.CENTER_VERTICAL
);

documentsText.setLetterSpacing(
        0.01f
);

LinearLayout.LayoutParams documentsTextParams =
        new LinearLayout.LayoutParams(
                0,
                -1,
                1f
);

documentsTextParams.setMargins(
        dp(14),
        0,
        dp(8),
        0
);

documentsItem.addView(
        documentsText,
        documentsTextParams
);

// =====================================================
// ARROW
// =====================================================

TextView documentsArrow =
        text(
                "›",
                28,
                Color.parseColor("#1656A8")
        );

documentsArrow.setGravity(
        Gravity.CENTER
);

documentsItem.addView(
        documentsArrow,
        new LinearLayout.LayoutParams(
                dp(28),
                -1
        )
);

// =====================================================
// MARGINS
// =====================================================

LinearLayout.LayoutParams documentsParams =
        new LinearLayout.LayoutParams(
                -1,
                dp(58)
        );

documentsParams.setMargins(
        dp(16),
        dp(0),
        dp(16),
        dp(2)
);

// =====================================================
// ADD
// =====================================================

drawerContent.addView(
        documentsItem,
        documentsParams
);
   // =====================================================
// PAYMENTS
// =====================================================

LinearLayout paymentsItem =
        new LinearLayout(this);

paymentsItem.setOrientation(
        LinearLayout.HORIZONTAL
);

paymentsItem.setGravity(
        Gravity.CENTER_VERTICAL
);

paymentsItem.setPadding(
        dp(16),
        0,
        dp(12),
        0
);

// =====================================================
// ICON BOX
// =====================================================

FrameLayout paymentsIconBox =
        new FrameLayout(this);

GradientDrawable paymentsIconBg =
        new GradientDrawable();

paymentsIconBg.setShape(
        GradientDrawable.OVAL
);

paymentsIconBg.setColor(
        Color.parseColor("#E8F2FF")
);

paymentsIconBox.setBackground(
        paymentsIconBg
);

// =====================================================
// ICON
// =====================================================

ImageView paymentsIcon =
        new ImageView(this);

paymentsIcon.setImageResource(
        R.drawable.ic_payments_premium
);

paymentsIcon.setScaleType(
        ImageView.ScaleType.CENTER
);

paymentsIconBox.addView(
        paymentsIcon,
        new FrameLayout.LayoutParams(
                -1,
                -1
        )
);

paymentsItem.addView(
        paymentsIconBox,
        new LinearLayout.LayoutParams(
                dp(42),
                dp(42)
        )
);

// =====================================================
// TEXT
// =====================================================

TextView paymentsText =
        text(
                "Payments",
                16,
                Color.parseColor("#102B52")
        );

paymentsText.setTypeface(
        Typeface.create(
                "sans-serif",
                Typeface.BOLD
        )
);

paymentsText.setGravity(
        Gravity.CENTER_VERTICAL
);

paymentsText.setLetterSpacing(
        0.01f
);

LinearLayout.LayoutParams paymentsTextParams =
        new LinearLayout.LayoutParams(
                0,
                -1,
                1f
);

paymentsTextParams.setMargins(
        dp(14),
        0,
        dp(8),
        0
);

paymentsItem.addView(
        paymentsText,
        paymentsTextParams
);

// =====================================================
// ARROW
// =====================================================

TextView paymentsArrow =
        text(
                "›",
                28,
                Color.parseColor("#1656A8")
        );

paymentsArrow.setGravity(
        Gravity.CENTER
);

paymentsItem.addView(
        paymentsArrow,
        new LinearLayout.LayoutParams(
                dp(28),
                -1
        )
);

// =====================================================
// MARGINS
// =====================================================

LinearLayout.LayoutParams paymentsParams =
        new LinearLayout.LayoutParams(
                -1,
                dp(58)
        );

paymentsParams.setMargins(
        dp(16),
        dp(0),
        dp(16),
        dp(2)
);

// =====================================================
// ADD
// =====================================================

drawerContent.addView(
        paymentsItem,
        paymentsParams
);
   
// =====================================================
// MULTI-CENTER MONITORING — PRO
// =====================================================

LinearLayout multiCenterItem =
        new LinearLayout(this);

multiCenterItem.setOrientation(
        LinearLayout.HORIZONTAL
);

multiCenterItem.setGravity(
        Gravity.CENTER_VERTICAL
);

multiCenterItem.setPadding(
        dp(16),
        0,
        dp(12),
        0
);

// =====================================================
// ICON BOX
// =====================================================

FrameLayout multiCenterIconBox =
        new FrameLayout(this);

GradientDrawable multiCenterIconBg =
        new GradientDrawable();

multiCenterIconBg.setShape(
        GradientDrawable.OVAL
);

multiCenterIconBg.setColor(
        Color.parseColor("#E8F2FF")
);

multiCenterIconBox.setBackground(
        multiCenterIconBg
);

// =====================================================
// ICON
// =====================================================

ImageView multiCenterIcon =
        new ImageView(this);

multiCenterIcon.setImageResource(
        R.drawable.ic_monitoring_premium
);

multiCenterIcon.setScaleType(
        ImageView.ScaleType.CENTER
);

multiCenterIconBox.addView(
        multiCenterIcon,
        new FrameLayout.LayoutParams(
                -1,
                -1
        )
);

multiCenterItem.addView(
        multiCenterIconBox,
        new LinearLayout.LayoutParams(
                dp(42),
                dp(42)
        )
);

// =====================================================
// TEXT CONTAINER
// =====================================================

LinearLayout multiCenterTextContainer =
        new LinearLayout(this);

multiCenterTextContainer.setOrientation(
        LinearLayout.VERTICAL
);

multiCenterTextContainer.setGravity(
        Gravity.CENTER_VERTICAL
);

LinearLayout.LayoutParams multiCenterTextContainerParams =
        new LinearLayout.LayoutParams(
                0,
                -1,
                1f
);

multiCenterTextContainerParams.setMargins(
        dp(14),
        0,
        dp(8),
        0
);

multiCenterItem.addView(
        multiCenterTextContainer,
        multiCenterTextContainerParams
);

// =====================================================
// TITLE
// =====================================================

TextView multiCenterText =
        text(
                "Multi-Center Monitoring",
                16,
                Color.parseColor("#102B52")
        );

multiCenterText.setTypeface(
        Typeface.create(
                "sans-serif",
                Typeface.BOLD
        )
);

multiCenterText.setGravity(
        Gravity.CENTER_VERTICAL
);

multiCenterText.setLetterSpacing(
        0.01f
);

multiCenterTextContainer.addView(
        multiCenterText,
        new LinearLayout.LayoutParams(
                -1,
                dp(28)
        )
);

// =====================================================
// PRO BADGE
// =====================================================

TextView multiCenterPro =
        text(
                "PRO",
                9,
                Color.WHITE
        );

multiCenterPro.setGravity(
        Gravity.CENTER
);

multiCenterPro.setTypeface(
        Typeface.create(
                "sans-serif",
                Typeface.BOLD
        )
);

GradientDrawable multiCenterProBg =
        new GradientDrawable(
                GradientDrawable.Orientation.TL_BR,
                new int[]{
                        Color.parseColor("#7C3AED"),
                        Color.parseColor("#2563EB")
                }
        );

multiCenterProBg.setCornerRadius(
        dp(6)
);

multiCenterPro.setBackground(
        multiCenterProBg
);

multiCenterTextContainer.addView(
        multiCenterPro,
        new LinearLayout.LayoutParams(
                dp(34),
                dp(17)
        )
);

// =====================================================
// ARROW
// =====================================================

TextView multiCenterArrow =
        text(
                "›",
                28,
                Color.parseColor("#1656A8")
        );

multiCenterArrow.setGravity(
        Gravity.CENTER
);

multiCenterItem.addView(
        multiCenterArrow,
        new LinearLayout.LayoutParams(
                dp(28),
                -1
        )
);

// =====================================================
// MARGINS
// =====================================================

LinearLayout.LayoutParams multiCenterParams =
        new LinearLayout.LayoutParams(
                -1,
                dp(58)
        );

multiCenterParams.setMargins(
        dp(16),
        dp(0),
        dp(16),
        dp(2)
);

// =====================================================
// ADD
// =====================================================

drawerContent.addView(
        multiCenterItem,
        multiCenterParams
);

    // =====================================================
// SETTINGS
// =====================================================

LinearLayout settingsItem =
        new LinearLayout(this);

settingsItem.setOrientation(
        LinearLayout.HORIZONTAL
);

settingsItem.setGravity(
        Gravity.CENTER_VERTICAL
);

settingsItem.setPadding(
        dp(16),
        0,
        dp(12),
        0
);

// =====================================================
// ICON BOX
// =====================================================

FrameLayout settingsIconBox =
        new FrameLayout(this);

GradientDrawable settingsIconBg =
        new GradientDrawable();

settingsIconBg.setShape(
        GradientDrawable.OVAL
);

settingsIconBg.setColor(
        Color.parseColor("#E8F2FF")
);

settingsIconBox.setBackground(
        settingsIconBg
);

// =====================================================
// ICON
// =====================================================

ImageView settingsIcon =
        new ImageView(this);

settingsIcon.setImageResource(
        R.drawable.ic_settings_premium
);

settingsIcon.setScaleType(
        ImageView.ScaleType.CENTER
);

settingsIconBox.addView(
        settingsIcon,
        new FrameLayout.LayoutParams(
                -1,
                -1
        )
);

settingsItem.addView(
        settingsIconBox,
        new LinearLayout.LayoutParams(
                dp(42),
                dp(42)
        )
);

// =====================================================
// TEXT
// =====================================================

TextView settingsText =
        text(
                "Settings",
                16,
                Color.parseColor("#102B52")
        );

settingsText.setTypeface(
        Typeface.create(
                "sans-serif",
                Typeface.BOLD
        )
);

settingsText.setGravity(
        Gravity.CENTER_VERTICAL
);

settingsText.setLetterSpacing(
        0.01f
);

LinearLayout.LayoutParams settingsTextParams =
        new LinearLayout.LayoutParams(
                0,
                -1,
                1f
);

settingsTextParams.setMargins(
        dp(14),
        0,
        dp(8),
        0
);

settingsItem.addView(
        settingsText,
        settingsTextParams
);

// =====================================================
// ARROW
// =====================================================

TextView settingsArrow =
        text(
                "›",
                28,
                Color.parseColor("#1656A8")
        );

settingsArrow.setGravity(
        Gravity.CENTER
);

settingsItem.addView(
        settingsArrow,
        new LinearLayout.LayoutParams(
                dp(28),
                -1
        )
);

// =====================================================
// MARGINS
// =====================================================

LinearLayout.LayoutParams settingsParams =
        new LinearLayout.LayoutParams(
                -1,
                dp(58)
        );

settingsParams.setMargins(
        dp(16),
        dp(0),
        dp(16),
        dp(2)
);

// =====================================================
// ADD
// =====================================================

drawerContent.addView(
        settingsItem,
        settingsParams
);
   // =====================================================
// HELP & SUPPORT
// =====================================================

LinearLayout helpItem =
        new LinearLayout(this);

helpItem.setOrientation(
        LinearLayout.HORIZONTAL
);

helpItem.setGravity(
        Gravity.CENTER_VERTICAL
);

helpItem.setPadding(
        dp(16),
        0,
        dp(12),
        0
);

// =====================================================
// ICON BOX
// =====================================================

FrameLayout helpIconBox =
        new FrameLayout(this);

GradientDrawable helpIconBg =
        new GradientDrawable();

helpIconBg.setShape(
        GradientDrawable.OVAL
);

helpIconBg.setColor(
        Color.parseColor("#E8F2FF")
);

helpIconBox.setBackground(
        helpIconBg
);

// =====================================================
// ICON
// =====================================================

ImageView helpIcon =
        new ImageView(this);

helpIcon.setImageResource(
        R.drawable.ic_help_premium
);

helpIcon.setScaleType(
        ImageView.ScaleType.CENTER
);

helpIconBox.addView(
        helpIcon,
        new FrameLayout.LayoutParams(
                -1,
                -1
        )
);

helpItem.addView(
        helpIconBox,
        new LinearLayout.LayoutParams(
                dp(42),
                dp(42)
        )
);

// =====================================================
// TEXT
// =====================================================

TextView helpText =
        text(
                "Help & Support",
                16,
                Color.parseColor("#102B52")
        );

helpText.setTypeface(
        Typeface.create(
                "sans-serif",
                Typeface.BOLD
        )
);

helpText.setGravity(
        Gravity.CENTER_VERTICAL
);

helpText.setLetterSpacing(
        0.01f
);

LinearLayout.LayoutParams helpTextParams =
        new LinearLayout.LayoutParams(
                0,
                -1,
                1f
);

helpTextParams.setMargins(
        dp(14),
        0,
        dp(8),
        0
);

helpItem.addView(
        helpText,
        helpTextParams
);

// =====================================================
// ARROW
// =====================================================

TextView helpArrow =
        text(
                "›",
                28,
                Color.parseColor("#1656A8")
        );

helpArrow.setGravity(
        Gravity.CENTER
);

helpItem.addView(
        helpArrow,
        new LinearLayout.LayoutParams(
                dp(28),
                -1
        )
);

// =====================================================
// MARGINS
// =====================================================

LinearLayout.LayoutParams helpParams =
        new LinearLayout.LayoutParams(
                -1,
                dp(58)
        );

helpParams.setMargins(
        dp(16),
        dp(0),
        dp(16),
        dp(2)
);

// =====================================================
// ADD
// =====================================================

drawerContent.addView(
        helpItem,
        helpParams
);
    // =====================================================
// MORE SECTION
// =====================================================

TextView moreTitle =
        text(
                "MORE",
                12,
                Color.parseColor("#6B7C93")
        );

moreTitle.setTypeface(
        Typeface.create(
                "sans-serif",
                Typeface.BOLD
        )
);

moreTitle.setLetterSpacing(
        0.12f
);

LinearLayout.LayoutParams moreTitleParams =
        new LinearLayout.LayoutParams(
                -1,
                dp(36)
        );

moreTitleParams.setMargins(
        dp(20),
        dp(10),
        dp(16),
        dp(0)
);

drawerContent.addView(
        moreTitle,
        moreTitleParams
);
  // =====================================================
// SHARE APP
// =====================================================

LinearLayout shareItem =
        new LinearLayout(this);

shareItem.setOrientation(
        LinearLayout.HORIZONTAL
);

shareItem.setGravity(
        Gravity.CENTER_VERTICAL
);

shareItem.setPadding(
        dp(16),
        0,
        dp(12),
        0
);

// =====================================================
// ICON BOX
// =====================================================

FrameLayout shareIconBox =
        new FrameLayout(this);

GradientDrawable shareIconBg =
        new GradientDrawable();

shareIconBg.setShape(
        GradientDrawable.OVAL
);

shareIconBg.setColor(
        Color.parseColor("#E8F2FF")
);

shareIconBox.setBackground(
        shareIconBg
);

// =====================================================
// ICON
// =====================================================

ImageView shareIcon =
        new ImageView(this);

shareIcon.setImageResource(
        R.drawable.ic_share_premium
);

shareIcon.setScaleType(
        ImageView.ScaleType.CENTER
);

shareIconBox.addView(
        shareIcon,
        new FrameLayout.LayoutParams(
                -1,
                -1
        )
);

shareItem.addView(
        shareIconBox,
        new LinearLayout.LayoutParams(
                dp(42),
                dp(42)
        )
);

// =====================================================
// TEXT
// =====================================================

TextView shareText =
        text(
                "Share App",
                16,
                Color.parseColor("#102B52")
        );

shareText.setTypeface(
        Typeface.create(
                "sans-serif",
                Typeface.BOLD
        )
);

shareText.setGravity(
        Gravity.CENTER_VERTICAL
);

shareText.setLetterSpacing(
        0.01f
);

LinearLayout.LayoutParams shareTextParams =
        new LinearLayout.LayoutParams(
                0,
                -1,
                1f
);

shareTextParams.setMargins(
        dp(14),
        0,
        dp(8),
        0
);

shareItem.addView(
        shareText,
        shareTextParams
);

// =====================================================
// ARROW
// =====================================================

TextView shareArrow =
        text(
                "›",
                28,
                Color.parseColor("#1656A8")
        );

shareArrow.setGravity(
        Gravity.CENTER
);

shareItem.addView(
        shareArrow,
        new LinearLayout.LayoutParams(
                dp(28),
                -1
        )
);

// =====================================================
// MARGINS
// =====================================================

LinearLayout.LayoutParams shareParams =
        new LinearLayout.LayoutParams(
                -1,
                dp(58)
        );

shareParams.setMargins(
        dp(16),
        dp(0),
        dp(16),
        dp(2)
);

// =====================================================
// ADD
// =====================================================

drawerContent.addView(
        shareItem,
        shareParams
);
 // =====================================================
// RATE US
// =====================================================

LinearLayout rateItem =
        new LinearLayout(this);

rateItem.setOrientation(
        LinearLayout.HORIZONTAL
);

rateItem.setGravity(
        Gravity.CENTER_VERTICAL
);

rateItem.setPadding(
        dp(16),
        0,
        dp(12),
        0
);

// =====================================================
// ICON BOX
// =====================================================

FrameLayout rateIconBox =
        new FrameLayout(this);

GradientDrawable rateIconBg =
        new GradientDrawable();

rateIconBg.setShape(
        GradientDrawable.OVAL
);

rateIconBg.setColor(
        Color.parseColor("#E8F2FF")
);

rateIconBox.setBackground(
        rateIconBg
);

// =====================================================
// ICON
// =====================================================

ImageView rateIcon =
        new ImageView(this);

rateIcon.setImageResource(
        R.drawable.ic_rate_premium
);

rateIcon.setScaleType(
        ImageView.ScaleType.CENTER
);

rateIconBox.addView(
        rateIcon,
        new FrameLayout.LayoutParams(
                -1,
                -1
        )
);

rateItem.addView(
        rateIconBox,
        new LinearLayout.LayoutParams(
                dp(42),
                dp(42)
        )
);

// =====================================================
// TEXT
// =====================================================

TextView rateText =
        text(
                "Rate us",
                16,
                Color.parseColor("#102B52")
        );

rateText.setTypeface(
        Typeface.create(
                "sans-serif",
                Typeface.BOLD
        )
);

rateText.setGravity(
        Gravity.CENTER_VERTICAL
);

rateText.setLetterSpacing(
        0.01f
);

LinearLayout.LayoutParams rateTextParams =
        new LinearLayout.LayoutParams(
                0,
                -1,
                1f
);

rateTextParams.setMargins(
        dp(14),
        0,
        dp(8),
        0
);

rateItem.addView(
        rateText,
        rateTextParams
);

// =====================================================
// ARROW
// =====================================================

TextView rateArrow =
        text(
                "›",
                28,
                Color.parseColor("#1656A8")
        );

rateArrow.setGravity(
        Gravity.CENTER
);

rateItem.addView(
        rateArrow,
        new LinearLayout.LayoutParams(
                dp(28),
                -1
        )
);

// =====================================================
// MARGINS
// =====================================================

LinearLayout.LayoutParams rateParams =
        new LinearLayout.LayoutParams(
                -1,
                dp(58)
        );

rateParams.setMargins(
        dp(16),
        dp(0),
        dp(16),
        dp(2)
);

// =====================================================
// ADD
// =====================================================

drawerContent.addView(
        rateItem,
        rateParams
);
    // =====================================================
// FEEDBACK
// =====================================================

LinearLayout feedbackItem =
        new LinearLayout(this);

feedbackItem.setOrientation(
        LinearLayout.HORIZONTAL
);

feedbackItem.setGravity(
        Gravity.CENTER_VERTICAL
);

feedbackItem.setPadding(
        dp(16),
        0,
        dp(12),
        0
);

// =====================================================
// ICON BOX
// =====================================================

FrameLayout feedbackIconBox =
        new FrameLayout(this);

GradientDrawable feedbackIconBg =
        new GradientDrawable();

feedbackIconBg.setShape(
        GradientDrawable.OVAL
);

feedbackIconBg.setColor(
        Color.parseColor("#E8F2FF")
);

feedbackIconBox.setBackground(
        feedbackIconBg
);

// =====================================================
// ICON
// =====================================================

ImageView feedbackIcon =
        new ImageView(this);

feedbackIcon.setImageResource(
        R.drawable.ic_feedback_premium
);

feedbackIcon.setScaleType(
        ImageView.ScaleType.CENTER
);

feedbackIconBox.addView(
        feedbackIcon,
        new FrameLayout.LayoutParams(
                -1,
                -1
        )
);

feedbackItem.addView(
        feedbackIconBox,
        new LinearLayout.LayoutParams(
                dp(42),
                dp(42)
        )
);

// =====================================================
// TEXT
// =====================================================

TextView feedbackText =
        text(
                "Feedback",
                16,
                Color.parseColor("#102B52")
        );

feedbackText.setTypeface(
        Typeface.create(
                "sans-serif",
                Typeface.BOLD
        )
);

feedbackText.setGravity(
        Gravity.CENTER_VERTICAL
);

feedbackText.setLetterSpacing(
        0.01f
);

LinearLayout.LayoutParams feedbackTextParams =
        new LinearLayout.LayoutParams(
                0,
                -1,
                1f
);

feedbackTextParams.setMargins(
        dp(14),
        0,
        dp(8),
        0
);

feedbackItem.addView(
        feedbackText,
        feedbackTextParams
);

// =====================================================
// ARROW
// =====================================================

TextView feedbackArrow =
        text(
                "›",
                28,
                Color.parseColor("#1656A8")
        );

feedbackArrow.setGravity(
        Gravity.CENTER
);

feedbackItem.addView(
        feedbackArrow,
        new LinearLayout.LayoutParams(
                dp(28),
                -1
        )
);

// =====================================================
// MARGINS
// =====================================================

LinearLayout.LayoutParams feedbackParams =
        new LinearLayout.LayoutParams(
                -1,
                dp(58)
        );

feedbackParams.setMargins(
        dp(16),
        dp(0),
        dp(16),
        dp(2)
);

// =====================================================
// ADD
// =====================================================

drawerContent.addView(
        feedbackItem,
        feedbackParams
);
  // =====================================================
// LOG OUT
// =====================================================

LinearLayout logoutItem =
        new LinearLayout(this);

logoutItem.setOrientation(
        LinearLayout.HORIZONTAL
);

logoutItem.setGravity(
        Gravity.CENTER_VERTICAL
);

logoutItem.setPadding(
        dp(16),
        0,
        dp(12),
        0
);

// =====================================================
// ICON BOX
// =====================================================

FrameLayout logoutIconBox =
        new FrameLayout(this);

GradientDrawable logoutIconBg =
        new GradientDrawable();

logoutIconBg.setShape(
        GradientDrawable.OVAL
);

logoutIconBg.setColor(
        Color.parseColor("#FDECEF")
);

logoutIconBox.setBackground(
        logoutIconBg
);

// =====================================================
// ICON
// =====================================================

ImageView logoutIcon =
        new ImageView(this);

logoutIcon.setImageResource(
        R.drawable.ic_logout_premium
);

logoutIcon.setScaleType(
        ImageView.ScaleType.CENTER
);

logoutIconBox.addView(
        logoutIcon,
        new FrameLayout.LayoutParams(
                -1,
                -1
        )
);

logoutItem.addView(
        logoutIconBox,
        new LinearLayout.LayoutParams(
                dp(42),
                dp(42)
        )
);

// =====================================================
// TEXT
// =====================================================

TextView logoutText =
        text(
                "Log out",
                16,
                Color.parseColor("#B43F50")
        );

logoutText.setTypeface(
        Typeface.create(
                "sans-serif",
                Typeface.BOLD
        )
);

logoutText.setGravity(
        Gravity.CENTER_VERTICAL
);

logoutText.setLetterSpacing(
        0.01f
);

LinearLayout.LayoutParams logoutTextParams =
        new LinearLayout.LayoutParams(
                0,
                -1,
                1f
);

logoutTextParams.setMargins(
        dp(14),
        0,
        dp(8),
        0
);

logoutItem.addView(
        logoutText,
        logoutTextParams
);

// =====================================================
// ARROW
// =====================================================

TextView logoutArrow =
        text(
                "›",
                28,
                Color.parseColor("#C94B5B")
        );

logoutArrow.setGravity(
        Gravity.CENTER
);

logoutItem.addView(
        logoutArrow,
        new LinearLayout.LayoutParams(
                dp(28),
                -1
        )
);

// =====================================================
// MARGINS
// =====================================================

LinearLayout.LayoutParams logoutParams =
        new LinearLayout.LayoutParams(
                -1,
                dp(58)
        );

logoutParams.setMargins(
        dp(16),
        dp(0),
        dp(16),
        dp(12)
);

// =====================================================
// ADD
// =====================================================

drawerContent.addView(
        logoutItem,
        logoutParams
);
    
    // =================================================
    // ADD DRAWER
    // =================================================

    FrameLayout.LayoutParams drawerParams =
            new FrameLayout.LayoutParams(
                    drawerWidth,
                    -1
            );

    drawerParams.gravity =
            Gravity.START;


    drawerOverlay.addView(
            drawer,
            drawerParams
    );


    // =================================================
    // ADD OVERLAY ABOVE CURRENT SCREEN
    // =================================================

    addContentView(
            drawerOverlay,
            new ViewGroup.LayoutParams(
                    -1,
                    -1
            )
    );


    // =================================================
    // START POSITION
    // =================================================

    drawer.setTranslationX(
            -drawerWidth
    );


    // =================================================
    // OPEN ANIMATION
    // =================================================

    drawer.animate()
            .translationX(0)
            .setDuration(280)
            .setInterpolator(
                    new android.view.animation.DecelerateInterpolator()
            )
            .start();

    scrim.animate()
            .alpha(1f)
            .setDuration(240)
            .start();


    // =================================================
    // CLOSE DRAWER
    // =================================================

    final Runnable closeDrawer =
            () -> {

                drawer.animate()
                        .translationX(
                                -drawerWidth
                        )
                        .setDuration(220)
                        .setInterpolator(
                                new android.view.animation.DecelerateInterpolator()
                        )
                        .withEndAction(
                                () -> {

                                    ViewParent parent =
                                            drawerOverlay.getParent();

                                    if (parent instanceof ViewGroup) {

                                        ((ViewGroup) parent)
                                                .removeView(
                                                        drawerOverlay
                                                );
                                    }
                                }
                        )
                        .start();


                scrim.animate()
                        .alpha(0f)
                        .setDuration(180)
                        .start();
            };


    // =================================================
    // CLOSE WHEN CLICKING OUTSIDE
    // =================================================

    scrim.setOnClickListener(
            v -> closeDrawer.run()
    );
}  
// =========================================================
// EARTH SPACE DRAWABLE
// =========================================================

private static class EarthSpaceDrawable
        extends Drawable {

    private final Paint paint =
            new Paint(Paint.ANTI_ALIAS_FLAG);

    private final Paint lightPaint =
            new Paint(Paint.ANTI_ALIAS_FLAG);

    @Override
    public void draw(Canvas canvas) {

        int width = getBounds().width();
        int height = getBounds().height();

        // =================================================
        // DARK SPACE
        // =================================================

        LinearGradient spaceGradient =
                new LinearGradient(
                        0,
                        0,
                        width,
                        height,
                        new int[]{
                                Color.rgb(2, 7, 20),
                                Color.rgb(5, 18, 42),
                                Color.rgb(8, 30, 58)
                        },
                        null,
                        Shader.TileMode.CLAMP
                );

        paint.setShader(spaceGradient);

        canvas.drawRect(
                0,
                0,
                width,
                height,
                paint
        );

        paint.setShader(null);


        // =================================================
        // EARTH GLOW
        // =================================================

        float earthRadius =
                height * 1.35f;

        float earthX =
                width * 0.82f;

        float earthY =
                height * 1.05f;

        RadialGradient earthGlow =
                new RadialGradient(
                        earthX,
                        earthY,
                        earthRadius,
                        new int[]{
                                Color.argb(190, 35, 120, 210),
                                Color.argb(100, 15, 65, 130),
                                Color.argb(0, 5, 20, 45)
                        },
                        new float[]{
                                0f,
                                0.65f,
                                1f
                        },
                        Shader.TileMode.CLAMP
                );

        paint.setShader(earthGlow);

        canvas.drawCircle(
                earthX,
                earthY,
                earthRadius,
                paint
        );

        paint.setShader(null);


        // =================================================
        // EARTH SURFACE
        // =================================================

        float surfaceRadius =
                height * 1.02f;

        RadialGradient earthSurface =
                new RadialGradient(
                        earthX - height * 0.20f,
                        earthY - height * 0.20f,
                        surfaceRadius,
                        new int[]{
                                Color.rgb(20, 75, 125),
                                Color.rgb(8, 42, 78),
                                Color.rgb(2, 15, 35)
                        },
                        null,
                        Shader.TileMode.CLAMP
                );

        paint.setShader(earthSurface);

        canvas.drawCircle(
                earthX,
                earthY,
                surfaceRadius,
                paint
        );

        paint.setShader(null);


        // =================================================
        // CITY LIGHTS
        // =================================================

        lightPaint.setStyle(
                Paint.Style.FILL
        );

        lightPaint.setColor(
                Color.rgb(255, 210, 105)
        );

        float[][] lights = {

                {0.66f, 0.63f},
                {0.71f, 0.69f},
                {0.76f, 0.58f},
                {0.80f, 0.72f},
                {0.70f, 0.80f},
                {0.84f, 0.64f},
                {0.61f, 0.74f},
                {0.75f, 0.87f},
                {0.87f, 0.78f},
                {0.65f, 0.88f},
                {0.81f, 0.54f},
                {0.91f, 0.69f}
        };

        for (float[] point : lights) {

            float x =
                    width * point[0];

            float y =
                    height * point[1];

            canvas.drawCircle(
                    x,
                    y,
                    dpStatic(1.2f),
                    lightPaint
            );
        }
    }


    private float dpStatic(float value) {

        return value *
                Resources.getSystem()
                        .getDisplayMetrics()
                        .density;
    }


    @Override
    public void setAlpha(int alpha) {
        paint.setAlpha(alpha);
    }

@Override
public void setColorFilter(
        ColorFilter colorFilter) {
    paint.setColorFilter(colorFilter);
}

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }
}

        
  // =========================================================
// APPOINTMENTS / MONITORING
// =========================================================

private void showAppointments() {

    currentPage = "APPOINTMENTS";

    root.removeAllViews();

    // =====================================================
    // SCROLL
    // =====================================================

    ScrollView scroll =
            new ScrollView(this);

    scroll.setFillViewport(true);
    scroll.setVerticalScrollBarEnabled(false);

    // =====================================================
    // PAGE
    // =====================================================

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

    // =====================================================
    // PREMIUM BACKGROUND
    // =====================================================

    GradientDrawable pageBg =
            new GradientDrawable(
                    GradientDrawable.Orientation.TL_BR,
                    new int[]{
                            Color.rgb(245, 248, 255),
                            Color.rgb(238, 244, 255),
                            Color.rgb(247, 243, 252)
                    }
            );

    page.setBackground(pageBg);

    scroll.addView(
            page,
            new ScrollView.LayoutParams(
                    -1,
                    -2
            )
    );

    root.addView(
            scroll,
            new LinearLayout.LayoutParams(
                    -1,
                    0,
                    1f
            )
    );

    // =====================================================
    // BACK
    // =====================================================

    TextView back =
            text(
                    "‹  Back",
                    17,
                    BLUE
            );

    back.setOnClickListener(
            v -> showHome()
    );

    page.addView(
            back,
            margin(0, 0, 0, 10)
    );

    // =====================================================
    // TITLE
    // =====================================================

    TextView title =
            text(
                    "Visa Monitoring",
                    28,
                    NAVY
            );

    title.setTypeface(
            Typeface.DEFAULT_BOLD
    );

    page.addView(title);

    // =====================================================
    // SUBTITLE
    // =====================================================

    page.addView(
            text(
                    "Monitor visa appointment availability and get notified when a slot appears.",
                    13,
                    GRAY
            ),
            margin(0, 5, 0, 20)
    );

    // =====================================================
    // COUNTRY CARD
    // =====================================================

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
                    selectedCountry.length() == 0
                            ? "Select Country"
                            : selectedCountry,
                    18,
                    selectedCountry.length() == 0
                            ? BLUE
                            : NAVY
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
                    "Choose the country you want to monitor.",
                    11,
                    GRAY
            ),
            margin(0, 2, 0, 0)
    );

    // =====================================================
    // COUNTRY SELECTOR
    // =====================================================

    countryCard.setOnClickListener(
            v -> {

                final String[] countries = {

                        "🇦🇹  Austria",
                        "🇧🇪  Belgium",
                        "🇧🇬  Bulgaria",
                        "🇭🇷  Croatia",
                        "🇨🇿  Czech Republic",
                        "🇩🇰  Denmark",
                        "🇪🇪  Estonia",
                        "🇫🇮  Finland",
                        "🇫🇷  France",
                        "🇩🇪  Germany",
                        "🇬🇷  Greece",
                        "🇭🇺  Hungary",
                        "🇮🇸  Iceland",
                        "🇮🇹  Italy",
                        "🇱🇻  Latvia",
                        "🇱🇮  Liechtenstein",
                        "🇱🇹  Lithuania",
                        "🇱🇺  Luxembourg",
                        "🇲🇹  Malta",
                        "🇳🇱  Netherlands",
                        "🇳🇴  Norway",
                        "🇵🇱  Poland",
                        "🇵🇹  Portugal",
                        "🇷🇴  Romania",
                        "🇸🇰  Slovakia",
                        "🇸🇮  Slovenia",
                        "🇪🇸  Spain",
                        "🇸🇪  Sweden",
                        "🇨🇭  Switzerland"
                };

                AlertDialog.Builder builder =
                        new AlertDialog.Builder(
                                MainActivity.this
                        );

                builder.setTitle(
                        "Select Country"
                );

                builder.setItems(
                        countries,
                        (dialog, which) -> {

                            selectedCountry =
                                    countries[which];

                            countryValue.setText(
                                    selectedCountry
                            );

                            countryValue.setTextColor(
                                    NAVY
                            );
                        }
                );

                builder.setNegativeButton(
                        "CANCEL",
                        null
                );

                builder.show();
            }
    );

    page.addView(
            countryCard,
            margin(0, 0, 0, 12)
    );

    // =====================================================
    // MONITORING CARD
    // =====================================================

    LinearLayout monitoringCard =
            card();

    TextView monitoringTitle =
            text(
                    "Appointment Monitoring",
                    17,
                    NAVY
            );

    monitoringTitle.setTypeface(
            Typeface.DEFAULT_BOLD
    );

    monitoringCard.addView(
            monitoringTitle
    );

    monitoringCard.addView(
            text(
                    "The app will monitor appointment availability and notify you when a slot is detected.",
                    12,
                    GRAY
            ),
            margin(0, 6, 0, 12)
    );

    // =====================================================
    // STATUS
    // =====================================================

    TextView monitoringStatus =
            text(
                    "Monitoring is paused",
                    13,
                    GRAY
            );

    monitoringStatus.setTypeface(
            Typeface.DEFAULT_BOLD
    );

    monitoringCard.addView(
            monitoringStatus,
            margin(0, 0, 0, 10)
    );

    // =====================================================
    // START MONITORING
    // =====================================================

    Button startMonitoring =
            smallButton(
                    "START MONITORING"
            );

    startMonitoring.setOnClickListener(
            v -> {

                if (selectedCountry.length() == 0) {

                    new AlertDialog.Builder(
                            MainActivity.this
                    )
                            .setTitle(
                                    "Select a country"
                            )
                            .setMessage(
                                    "Please select a country before starting monitoring."
                            )
                            .setPositiveButton(
                                    "OK",
                                    null
                            )
                            .show();

                    return;
                }

                monitoringStatus.setText(
                        "Monitoring is active • " +
                        selectedCountry
                );

                monitoringStatus.setTextColor(
                        GREEN
                );

                startMonitoring.setText(
                        "MONITORING ACTIVE"
                );
            }
    );

    monitoringCard.addView(
            startMonitoring
    );

    page.addView(
            monitoringCard,
            margin(0, 0, 0, 12)
    );

    // =====================================================
    // AVAILABILITY CARD
    // =====================================================

    LinearLayout availabilityCard =
            card();

    TextView availabilityTitle =
            text(
                    "Appointment Availability",
                    17,
                    NAVY
            );

    availabilityTitle.setTypeface(
            Typeface.DEFAULT_BOLD
    );

    availabilityCard.addView(
            availabilityTitle
    );

    TextView availabilityStatus =
            text(
                    "No appointment detected yet",
                    13,
                    GRAY
            );

    availabilityStatus.setTypeface(
            Typeface.DEFAULT_BOLD
    );

    availabilityCard.addView(
            availabilityStatus,
            margin(0, 6, 0, 0)
    );

    availabilityCard.addView(
            text(
                    "You will be notified when an available appointment is detected.",
                    11,
                    GRAY
            ),
            margin(0, 3, 0, 10)
    );

    Button checkButton =
            smallButton(
                    "CHECK NOW"
            );

    checkButton.setOnClickListener(
            v -> {

                if (selectedCountry.length() == 0) {

                    new AlertDialog.Builder(
                            MainActivity.this
                    )
                            .setTitle(
                                    "Select a country"
                            )
                            .setMessage(
                                    "Please select a country first."
                            )
                            .setPositiveButton(
                                    "OK",
                                    null
                            )
                            .show();

                    return;
                }

                availabilityStatus.setText(
                        "Checking " +
                        selectedCountry +
                        "..."
                );

                availabilityStatus.setTextColor(
                        BLUE
                );
            }
    );

    availabilityCard.addView(
            checkButton
    );

    page.addView(
            availabilityCard,
            margin(0, 0, 0, 12)
    );

    // =====================================================
    // HOW IT WORKS
    // =====================================================

    LinearLayout infoCard =
            card();

    TextView infoTitle =
            text(
                    "How it works",
                    16,
                    NAVY
            );

    infoTitle.setTypeface(
            Typeface.DEFAULT_BOLD
    );

    infoCard.addView(
            infoTitle
    );

    infoCard.addView(
            text(
                    "1. Select a country\n" +
                    "2. Start monitoring\n" +
                    "3. Get notified when an appointment appears\n" +
                    "4. Open the official booking page",
                    12,
                    GRAY
            ),
            margin(0, 7, 0, 0)
    );

    page.addView(
            infoCard,
            margin(0, 0, 0, 5)
    );
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
// PREMIUM BOTTOM NAV ITEM
// =========================================================
private LinearLayout navItem(
        int iconRes,
        String label,
        boolean isHome
) {

    LinearLayout item =
            new LinearLayout(this);

    item.setOrientation(
            LinearLayout.VERTICAL
    );

    item.setGravity(
            Gravity.CENTER
    );

    item.setLayoutParams(
            new LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    1f
            )
    );

    // =================================================
    // ICON HOLDER
    // =================================================

    FrameLayout holder =
            new FrameLayout(this);

    LinearLayout.LayoutParams holderLp;

    if (isHome) {

        holder.setBackgroundResource(
                R.drawable.bg_home_pill
        );

        holderLp =
                new LinearLayout.LayoutParams(
                        dp(56),
                        dp(32)
                );

    } else {

        holder.setBackground(null);

        holderLp =
                new LinearLayout.LayoutParams(
                        dp(38),
                        dp(38)
                );
    }

    holderLp.gravity =
            Gravity.CENTER;

    holderLp.bottomMargin =
            dp(2);

    holder.setLayoutParams(
            holderLp
    );

    // =================================================
    // ICON
    // =================================================

    ImageView icon =
            new ImageView(this);

    icon.setImageResource(
            iconRes
    );

    icon.setScaleType(
            ImageView.ScaleType.FIT_CENTER
    );

    int iconSize =
            isHome ? dp(20) : dp(24);

    FrameLayout.LayoutParams iconLp =
            new FrameLayout.LayoutParams(
                    iconSize,
                    iconSize
            );

    iconLp.gravity =
            Gravity.CENTER;

    icon.setLayoutParams(
            iconLp
    );

    holder.addView(icon);

    // =================================================
    // TEXT
    // =================================================

    TextView text =
            new TextView(this);

    text.setText(label);

    text.setTextSize(11);

    text.setGravity(
            Gravity.CENTER
    );

    text.setMaxLines(1);

    text.setTranslationY(
            isHome ? 0 : dp(-2)
    );

    // =================================================
    // TEXT COLOR
    // =================================================

    if (isHome) {

        text.setTextColor(
                Color.WHITE
        );

        text.setTypeface(
                null,
                android.graphics.Typeface.BOLD
        );

    } else {

        text.setTextColor(
                Color.parseColor(
                        "#8A9BB5"
                )
        );
    }

    // =================================================
    // ADD VIEWS
    // =================================================

    item.addView(
            holder
    );

    item.addView(
            text
    );

    // =================================================
    // CLICK
    // =================================================

    item.setOnClickListener(
            new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    if (label.equals("Home")) {

                        showHome();

                    } else if (
                            label.equals("Monitoring")
                    ) {

                        page(
                                "Monitoring",
                                "Monitor your appointment availability",
                                new String[]{
                                        "Monitoring is currently paused"
                                }
                        );

                    } else if (
                            label.equals("Notifications")
                    ) {

                        showAlerts();

                    } else if (
                            label.equals("Profile")
                    ) {

                        showSettings();
                    }
                }
            }
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


// =====================================================
// OFFICIAL VISA PORTAL URL
// =====================================================

private String getVisaPortalUrl(
        String selectedCountry
) {

    if (selectedCountry == null) {
        return null;
    }

    String country =
            selectedCountry
                    .replaceAll(
                            "[^\\p{L}\\s]",
                            ""
                    )
                    .trim();

    switch (country) {

        case "Austria":
            return "https://www.bmeia.gv.at/en/travel-stay/entry-and-residence-in-austria/visa";

        case "Belgium":
            return "https://diplomatie.belgium.be/en/travel-belgium/visa-belgium";

        case "Bulgaria":
            return "https://www.mfa.bg/en/services-travel/consular-services/travel-to-bulgaria/visas";

        case "Croatia":
            return "https://mvep.gov.hr/consular-information-22801/visas-22807/22807";

        case "Czech Republic":
            return "https://mzv.gov.cz/jnp/en/information_for_aliens/visa/index.html";

        case "Denmark":
            return "https://um.dk/en/travel-and-residence/how-to-apply-for-a-visa";

        case "Estonia":
            return "https://vm.ee/en/consular-visa-and-travel-information/visa";

        case "Finland":
            return "https://um.fi/visa-to-visit-finland";

        case "France":
            return "https://france-visas.gouv.fr/en";

        case "Germany":
            return "https://www.auswaertiges-amt.de/en/visa-service";

        case "Greece":
            return "https://www.mfa.gr/en/visas";

        case "Hungary":
            return "https://konzinfo.mfa.gov.hu/en";

        case "Iceland":
            return "https://island.is/en/visa";

        case "Italy":
            return "https://vistoperitalia.esteri.it/home/en";

        case "Latvia":
            return "https://www.mfa.gov.lv/en/visas";

        case "Liechtenstein":
            return "https://www.llv.li/en/national-administration/office-for-construction-and-infrastructure/immigration-and-passports/entry-and-residence/visa";

        case "Lithuania":
            return "https://keliauk.urm.lt/en/entry-to-lithuania/visas";

        case "Luxembourg":
            return "https://guichet.public.lu/en/citoyens/immigration/plus-3-mois/entree-sejour/visa.html";

        case "Malta":
            return "https://identita.gov.mt/visas-and-citizenship/visa-applications";

        case "Netherlands":
            return "https://www.netherlandsworldwide.nl/visa-the-netherlands";

        case "Norway":
            return "https://www.udi.no/en/want-to-apply/visit-and-holiday/";

        case "Poland":
            return "https://www.gov.pl/web/diplomacy/visas";

        case "Portugal":
            return "https://vistos.mne.gov.pt/en";

        case "Romania":
            return "https://eviza.mae.ro";

        case "Slovakia":
            return "https://www.mzv.sk/web/en/visa-and-services";

        case "Slovenia":
            return "https://www.gov.si/en/topics/entry-and-residence/visas";

        case "Spain":
            return "https://www.exteriores.gob.es/en/ServiciosAlCiudadano/Paginas/Visados.aspx";

        case "Sweden":
            return "https://www.migrationsverket.se/en/you-want-to-apply/visit-sweden.html";

        case "Switzerland":
    return "https://www.eda.admin.ch/countries/algeria/en/home/visa/entry-ch.html";

default:
    return null;
}
}

// =========================================================
// OLD VISA METHODS — TEMPORARY
// =========================================================

private void showPreviousSpainVisaPage() {
}

private void showVisaValidityPage() {
}

private void showVisaTypePage() {
}

private void showVisaIssuingCountryPage() {
}

}
