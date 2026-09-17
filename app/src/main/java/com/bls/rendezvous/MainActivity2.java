package com.bls.rendezvous;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.os.Handler;

public class MainActivity2 extends Activity {

    private final int NAVY = Color.rgb(25, 35, 70);
    private final int GRAY = Color.rgb(105, 120, 145);

    private LinearLayout root;
    private LinearLayout content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setStatusBarColor(
                Color.rgb(75, 70, 120)
        );

        getWindow().setNavigationBarColor(
                Color.WHITE
        );

        showSplash();
    }

    // =====================================================
    // SPLASH
    // =====================================================

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

        splash.setBackground(
                background
        );

        TextView logo =
                new TextView(this);

        logo.setText("BLS");
        logo.setTextSize(68);
        logo.setTextColor(Color.WHITE);
        logo.setGravity(Gravity.CENTER);
        logo.setTypeface(
                Typeface.DEFAULT,
                Typeface.BOLD
        );

        splash.addView(
                logo,
                new LinearLayout.LayoutParams(
                        -1,
                        -2
                )
        );

        TextView international =
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

        splash.addView(
                international,
                new LinearLayout.LayoutParams(
                        -1,
                        -2
                )
        );

        setContentView(splash);

        new Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        showHome();
                    }
                },
                1800
        );
    }

    // =====================================================
    // HOME
    // =====================================================

    private void showHome() {

        root =
                new LinearLayout(this);

        root.setOrientation(
                LinearLayout.VERTICAL
        );

        GradientDrawable rootBackground =
                new GradientDrawable(
                        GradientDrawable.Orientation.TL_BR,
                        new int[]{
                                Color.rgb(245, 248, 255),
                                Color.rgb(238, 244, 255),
                                Color.rgb(247, 243, 252)
                        }
                );

        root.setBackground(
                rootBackground
        );

        // -------------------------------------------------
        // HEADER
        // -------------------------------------------------

        LinearLayout header =
                new LinearLayout(this);

        header.setOrientation(
                LinearLayout.VERTICAL
        );

        header.setPadding(
                dp(20),
                dp(18),
                dp(20),
                dp(10)
        );

        TextView title =
                text(
                        "BLS Rendez-Vous",
                        24,
                        NAVY
                );

        title.setTypeface(
                Typeface.DEFAULT,
                Typeface.BOLD
        );

        header.addView(title);

        TextView subtitle =
                text(
                        "Your visa appointment assistant",
                        12,
                        GRAY
                );

        header.addView(subtitle);

        root.addView(
                header,
                new LinearLayout.LayoutParams(
                        -1,
                        -2
                )
        );

        // -------------------------------------------------
        // CONTENT
        // -------------------------------------------------

        content =
                new LinearLayout(this);

        content.setOrientation(
                LinearLayout.VERTICAL
        );

        content.setPadding(
                dp(14),
                dp(4),
                dp(14),
                dp(8)
        );

        root.addView(
                content,
                new LinearLayout.LayoutParams(
                        -1,
                        0,
                        1
                )
        );

        // -------------------------------------------------
        // CURRENT APPLICATION
        // -------------------------------------------------

        LinearLayout application =
                card();

        TextView current =
                text(
                        "CURRENT APPLICATION",
                        11,
                        GRAY
                );

        current.setTypeface(
                Typeface.DEFAULT,
                Typeface.BOLD
        );

        application.addView(current);

        TextView spain =
                text(
                        "🇪🇸  Spain",
                        20,
                        NAVY
                );

        spain.setTypeface(
                Typeface.DEFAULT,
                Typeface.BOLD
        );

        application.addView(
                spain,
                margin(0, 5, 0, 2)
        );

        TextView center =
                text(
                        "Algiers Visa Center",
                        13,
                        GRAY
                );

        application.addView(center);

        TextView status =
                text(
                        "● ACTIVE",
                        12,
                        Color.rgb(35, 175, 105)
                );

        status.setTypeface(
                Typeface.DEFAULT,
                Typeface.BOLD
        );

        application.addView(
                status,
                margin(0, 8, 0, 0)
        );

        content.addView(
                application,
                margin(0, 5, 0, 8)
        );

        // =================================================
        // SERVICES CONTAINER
        // =================================================

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

        GradientDrawable servicesBackground =
                new GradientDrawable(
                        GradientDrawable.Orientation.TL_BR,
                        new int[]{
                                Color.rgb(239, 245, 255),
                                Color.rgb(246, 243, 253)
                        }
                );

        servicesBackground.setCornerRadius(
                dp(22)
        );

        servicesContainer.setBackground(
                servicesBackground
        );

        if (android.os.Build.VERSION.SDK_INT >=
                android.os.Build.VERSION_CODES.LOLLIPOP) {

            servicesContainer.setElevation(
                    dp(2)
            );
        }

        TextView servicesTitle =
                text(
                        "Services",
                        20,
                        NAVY
                );

        servicesTitle.setTypeface(
                Typeface.DEFAULT,
                Typeface.BOLD
        );

        servicesContainer.addView(
                servicesTitle,
                margin(2, 0, 0, 10)
        );

        // -------------------------------------------------
        // ROW 1
        // -------------------------------------------------

        LinearLayout row1 =
                serviceRow();

        row1.addView(
                serviceGridItem(
                        "📅",
                        "Appointments"
                )
        );

        row1.addView(
                serviceGridItem(
                        "🔔",
                        "Alerts"
                )
        );

        row1.addView(
                serviceGridItem(
                        "🏢",
                        "Centers"
                )
        );

        servicesContainer.addView(
                row1
        );

        // -------------------------------------------------
        // ROW 2
        // -------------------------------------------------

        LinearLayout row2 =
                serviceRow();

        row2.addView(
                serviceGridItem(
                        "📋",
                        "Tracking"
                )
        );

        row2.addView(
                serviceGridItem(
                        "🌍",
                        "Countries"
                )
        );

        row2.addView(
                serviceGridItem(
                        "📊",
                        "Statistics"
                )
        );

        servicesContainer.addView(
                row2
        );

        content.addView(
                servicesContainer,
                margin(0, 5, 0, 8)
        );

        // -------------------------------------------------
        // SEARCH
        // -------------------------------------------------

        TextView search =
                text(
                        "⌕   Search services",
                        14,
                        GRAY
                );

        GradientDrawable searchBackground =
                new GradientDrawable();

        searchBackground.setColor(
                Color.rgb(245, 247, 250)
        );

        searchBackground.setCornerRadius(
                dp(14)
        );

        search.setBackground(
                searchBackground
        );

        search.setPadding(
                dp(14),
                dp(12),
                dp(14),
                dp(12)
        );

        content.addView(
                search,
                margin(0, 2, 0, 5)
        );

        // -------------------------------------------------
        // BOTTOM NAVIGATION
        // -------------------------------------------------

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

        bottom.addView(
                nav("⌂", "Home")
        );

        bottom.addView(
                nav("▣", "Appointments")
        );

        bottom.addView(
                nav("●", "Alerts")
        );

        bottom.addView(
                nav("⚙", "Settings")
        );

        root.addView(
                bottom,
                new LinearLayout.LayoutParams(
                        -1,
                        dp(64)
                )
        );

        setContentView(root);
    }

    // =====================================================
    // SERVICE ROW
    // =====================================================

    private LinearLayout serviceRow() {

        LinearLayout row =
                new LinearLayout(this);

        row.setOrientation(
                LinearLayout.HORIZONTAL
        );

        row.setGravity(
                Gravity.CENTER
        );

        row.setLayoutParams(
                new LinearLayout.LayoutParams(
                        -1,
                        dp(105)
                )
        );

        return row;
    }

    // =====================================================
    // SERVICE ITEM
    // =====================================================

    private LinearLayout serviceGridItem(
            String icon,
            String title
    ) {

        LinearLayout item =
                new LinearLayout(this);

        item.setOrientation(
                LinearLayout.VERTICAL
        );

        item.setGravity(
                Gravity.CENTER
        );

        GradientDrawable background =
                new GradientDrawable(
                        GradientDrawable.Orientation.TL_BR,
                        new int[]{
                                Color.WHITE,
                                Color.rgb(242, 247, 253)
                        }
                );

        background.setCornerRadius(
                dp(18)
        );

        item.setBackground(
                background
        );

        if (android.os.Build.VERSION.SDK_INT >=
                android.os.Build.VERSION_CODES.LOLLIPOP) {

            item.setElevation(
                    dp(2)
            );
        }

        TextView iconView =
                new TextView(this);

        iconView.setText(icon);
        iconView.setTextSize(25);
        iconView.setGravity(
                Gravity.CENTER
        );

        GradientDrawable iconBackground =
                new GradientDrawable(
                        GradientDrawable.Orientation.TL_BR,
                        new int[]{
                                Color.rgb(232, 240, 252),
                                Color.rgb(244, 238, 253)
                        }
                );

        iconBackground.setShape(
                GradientDrawable.OVAL
        );

        iconView.setBackground(
                iconBackground
        );

        item.addView(
                iconView,
                new LinearLayout.LayoutParams(
                        dp(50),
                        dp(50)
                )
        );

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

        titleView.setGravity(
                Gravity.CENTER
        );

        item.addView(
                titleView,
                new LinearLayout.LayoutParams(
                        -1,
                        dp(25)
                )
        );

        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(
                        0,
                        dp(96),
                        1
                );

        params.setMargins(
                dp(4),
                dp(4),
                dp(4),
                dp(4)
        );

        item.setLayoutParams(params);

        return item;
    }

    // =====================================================
    // CARD
    // =====================================================

    private LinearLayout card() {

        LinearLayout card =
                new LinearLayout(this);

        card.setOrientation(
                LinearLayout.VERTICAL
        );

        card.setPadding(
                dp(16),
                dp(14),
                dp(16),
                dp(14)
        );

        GradientDrawable background =
                new GradientDrawable();

        background.setColor(
                Color.WHITE
        );

        background.setCornerRadius(
                dp(18)
        );

        card.setBackground(
                background
        );

        if (android.os.Build.VERSION.SDK_INT >=
                android.os.Build.VERSION_CODES.LOLLIPOP) {

            card.setElevation(
                    dp(2)
            );
        }

        return card;
    }

    // =====================================================
    // BOTTOM NAV ITEM
    // =====================================================

    private LinearLayout nav(
            String icon,
            String title
    ) {

        LinearLayout item =
                new LinearLayout(this);

        item.setOrientation(
                LinearLayout.VERTICAL
        );

        item.setGravity(
                Gravity.CENTER
        );

        TextView iconView =
                text(
                        icon,
                        22,
                        NAVY
                );

        iconView.setGravity(
                Gravity.CENTER
        );

        item.addView(
                iconView
        );

        TextView titleView =
                text(
                        title,
                        10,
                        GRAY
                );

        titleView.setGravity(
                Gravity.CENTER
        );

        item.addView(
                titleView
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

    // =====================================================
    // TEXT
    // =====================================================

    private TextView text(
            String value,
            float size,
            int color
    ) {

        TextView view =
                new TextView(this);

        view.setText(value);
        view.setTextSize(size);
        view.setTextColor(color);

        return view;
    }

    // =====================================================
    // MARGIN
    // =====================================================

    private LinearLayout.LayoutParams margin(
            int left,
            int top,
            int right,
            int bottom
    ) {

        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(
                        -1,
                        -2
                );

        params.setMargins(
                dp(left),
                dp(top),
                dp(right),
                dp(bottom)
        );

        return params;
    }

    // =====================================================
    // DP
    // =====================================================

    private int dp(int value) {

        return (int) (
                value *
                getResources()
                        .getDisplayMetrics()
                        .density
        );
    }
}
