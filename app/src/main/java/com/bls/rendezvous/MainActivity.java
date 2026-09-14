package com.bls.rendezvous;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private final int BG_TOP = Color.rgb(248, 250, 255);
    private final int BG_BOTTOM = Color.rgb(224, 232, 249);

    private final int WHITE = Color.WHITE;
    private final int NAVY = Color.rgb(25, 39, 72);
    private final int BLUE = Color.rgb(52, 105, 215);
    private final int BLUE_LIGHT = Color.rgb(238, 244, 255);
    private final int GOLD = Color.rgb(183, 137, 69);
    private final int GREEN = Color.rgb(35, 160, 100);
    private final int TEXT = Color.rgb(45, 53, 70);
    private final int MUTED = Color.rgb(115, 125, 145);
    private final int BORDER = Color.rgb(225, 230, 240);

    private FrameLayout content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createApp();
    }

    // =========================================================
    // APP
    // =========================================================

    private void createApp() {

        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);

        GradientDrawable gradient = new GradientDrawable(
                GradientDrawable.Orientation.TL_BR,
                new int[]{
                        BG_TOP,
                        Color.rgb(241, 245, 253),
                        BG_BOTTOM
                }
        );

        root.setBackground(gradient);

        content = new FrameLayout(this);

        root.addView(
                content,
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        0,
                        1
                )
        );

        root.addView(
                createBottomNavigation(),
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        dp(72)
                )
        );

        setContentView(root);

        showHome();
    }

    // =========================================================
    // HOME
    // =========================================================

    private void showHome() {

        content.removeAllViews();

        ScrollView scroll = new ScrollView(this);
        scroll.setVerticalScrollBarEnabled(false);

        LinearLayout page = new LinearLayout(this);
        page.setOrientation(LinearLayout.VERTICAL);
        page.setPadding(
                dp(20),
                dp(18),
                dp(20),
                dp(25)
        );

        // HEADER
        LinearLayout header = new LinearLayout(this);
        header.setOrientation(LinearLayout.HORIZONTAL);
        header.setGravity(Gravity.CENTER_VERTICAL);

        LinearLayout titleBox = new LinearLayout(this);
        titleBox.setOrientation(LinearLayout.VERTICAL);

        TextView title = makeText(
                "BLS Rendez-Vous",
                23,
                NAVY,
                true
        );

        TextView subtitle = makeText(
                "Your visa appointment assistant",
                13,
                MUTED,
                false
        );

        titleBox.addView(title);
        titleBox.addView(subtitle);

        header.addView(
                titleBox,
                new LinearLayout.LayoutParams(
                        0,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        1
                )
        );

        TextView settings = makeText(
                "⚙",
                24,
                NAVY,
                false
        );

        settings.setGravity(Gravity.CENTER);
        settings.setBackground(
                rounded(WHITE, 18, BORDER)
        );

        settings.setOnClickListener(
                v -> openSettings()
        );

        header.addView(
                settings,
                new LinearLayout.LayoutParams(
                        dp(48),
                        dp(48)
                )
        );

        page.addView(header);

        space(page, 18);

        // CURRENT APPLICATION
        page.addView(createApplicationCard());

        space(page, 20);

        // SERVICES TITLE
        page.addView(
                createSectionTitle(
                        "Services",
                        "Everything you need in one place"
                )
        );

        space(page, 12);

        // ROW 1
        LinearLayout row1 = new LinearLayout(this);
        row1.setOrientation(LinearLayout.HORIZONTAL);

        row1.addView(
                serviceCard(
                        "📅",
                        "Appointments",
                        "Find & manage",
                        BLUE,
                        v -> showAppointments()
                ),
                gridParams()
        );

        row1.addView(
                serviceCard(
                        "🔔",
                        "Alerts",
                        "Availability alerts",
                        GOLD,
                        v -> showAlerts()
                ),
                gridParams()
        );

        page.addView(row1);

        space(page, 12);

        // ROW 2
        LinearLayout row2 = new LinearLayout(this);
        row2.setOrientation(LinearLayout.HORIZONTAL);

        row2.addView(
                serviceCard(
                        "🏢",
                        "Centers",
                        "Visa centers",
                        BLUE,
                        v -> showCenters()
                ),
                gridParams()
        );

        row2.addView(
                serviceCard(
                        "🔎",
                        "Tracking",
                        "Track application",
                        GREEN,
                        v -> showTracking()
                ),
                gridParams()
        );

        page.addView(row2);

        space(page, 12);

        // ROW 3
        LinearLayout row3 = new LinearLayout(this);
        row3.setOrientation(LinearLayout.HORIZONTAL);

        row3.addView(
                serviceCard(
                        "🌍",
                        "Countries",
                        "Visa destinations",
                        BLUE,
                        v -> showCountries()
                ),
                gridParams()
        );

        row3.addView(
                serviceCard(
                        "📊",
                        "Statistics",
                        "Your activity",
                        Color.rgb(130, 90, 180),
                        v -> showStatistics()
                ),
                gridParams()
        );

        page.addView(row3);

        space(page, 18);

        // SEARCH
        page.addView(createSearch());

        space(page, 14);

        // OFFICIAL BLS
        page.addView(createOfficialCard());

        scroll.addView(page);
        content.addView(scroll);
    }

    // =========================================================
    // CURRENT APPLICATION
    // =========================================================

    private View createApplicationCard() {

        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setPadding(
                dp(18),
                dp(17),
                dp(18),
                dp(17)
        );

        card.setBackground(
                rounded(WHITE, 22, BORDER)
        );

        LinearLayout top = new LinearLayout(this);
        top.setOrientation(LinearLayout.HORIZONTAL);
        top.setGravity(Gravity.CENTER_VERTICAL);

        LinearLayout country = new LinearLayout(this);
        country.setOrientation(LinearLayout.VERTICAL);

        TextView small = makeText(
                "CURRENT APPLICATION",
                10,
                MUTED,
                true
        );

        TextView countryName = makeText(
                "🇪🇸  Spain",
                21,
                NAVY,
                true
        );

        country.addView(small);
        country.addView(countryName);

        top.addView(
                country,
                new LinearLayout.LayoutParams(
                        0,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        1
                )
        );

        TextView active = makeText(
                "● ACTIVE",
                12,
                GREEN,
                true
        );

        top.addView(active);

        card.addView(top);

        space(card, 14);

        TextView center = makeText(
                "📍  Algiers Visa Center",
                14,
                TEXT,
                false
        );

        card.addView(center);

        space(card, 12);

        LinearLayout monitor = new LinearLayout(this);
        monitor.setOrientation(LinearLayout.HORIZONTAL);

        TextView monitorText = makeText(
                "Monitoring",
                13,
                MUTED,
                false
        );

        monitor.addView(
                monitorText,
                new LinearLayout.LayoutParams(
                        0,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        1
                )
        );

        TextView interval = makeText(
                "Every 2 minutes",
                13,
                NAVY,
                true
        );

        monitor.addView(interval);

        card.addView(monitor);

        return card;
    }

    // =========================================================
    // SECTION TITLE
    // =========================================================

    private View createSectionTitle(
            String title,
            String subtitle
    ) {

        LinearLayout box = new LinearLayout(this);
        box.setOrientation(LinearLayout.VERTICAL);

        TextView t = makeText(
                title,
                20,
                NAVY,
                true
        );

        TextView s = makeText(
                subtitle,
                12,
                MUTED,
                false
        );

        box.addView(t);
        box.addView(s);

        return box;
    }

    // =========================================================
    // SERVICE CARD
    // =========================================================

    private View serviceCard(
            String icon,
            String title,
            String subtitle,
            int iconColor,
            View.OnClickListener click
    ) {

        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setPadding(
                dp(15),
                dp(15),
                dp(14),
                dp(15)
        );

        card.setBackground(
                rounded(WHITE, 20, BORDER)
        );

        TextView iconView = makeText(
                icon,
                24,
                iconColor,
                false
        );

        iconView.setGravity(Gravity.CENTER);
        iconView.setBackground(
                rounded(BLUE_LIGHT, 14, Color.TRANSPARENT)
        );

        card.addView(
                iconView,
                new LinearLayout.LayoutParams(
                        dp(46),
                        dp(46)
                )
        );

        space(card, 10);

        TextView titleView = makeText(
                title,
                15,
                NAVY,
                true
        );

        card.addView(titleView);

        space(card, 3);

        TextView subtitleView = makeText(
                subtitle,
                11,
                MUTED,
                false
        );

        card.addView(subtitleView);

        card.setOnClickListener(click);

        return card;
    }

    // =========================================================
    // SEARCH
    // =========================================================

    private View createSearch() {

        LinearLayout search = new LinearLayout(this);
        search.setOrientation(LinearLayout.HORIZONTAL);
        search.setGravity(Gravity.CENTER_VERTICAL);
        search.setPadding(
                dp(15),
                0,
                dp(15),
                0
        );

        search.setBackground(
                rounded(WHITE, 18, BORDER)
        );

        TextView icon = makeText(
                "🔎",
                19,
                MUTED,
                false
        );

        search.addView(
                icon,
                new LinearLayout.LayoutParams(
                        dp(35),
                        ViewGroup.LayoutParams.MATCH_PARENT
                )
        );

        TextView text = makeText(
                "Search countries, centers or services",
                13,
                MUTED,
                false
        );

        search.addView(
                text,
                new LinearLayout.LayoutParams(
                        0,
                        dp(56),
                        1
                )
        );

        search.setOnClickListener(
                v -> showSearch()
        );

        return search;
    }

    // =========================================================
    // OFFICIAL BLS
    // =========================================================

    private View createOfficialCard() {

        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.HORIZONTAL);
        card.setGravity(Gravity.CENTER_VERTICAL);
        card.setPadding(
                dp(16),
                dp(14),
                dp(16),
                dp(14)
        );

        card.setBackground(
                rounded(NAVY, 20, NAVY)
        );

        LinearLayout texts = new LinearLayout(this);
        texts.setOrientation(LinearLayout.VERTICAL);

        TextView title = makeText(
                "Official BLS",
                15,
                WHITE,
                true
        );

        TextView subtitle = makeText(
                "Official visa information",
                11,
                Color.rgb(190, 200, 220),
                false
        );

        texts.addView(title);
        texts.addView(subtitle);

        card.addView(
                texts,
                new LinearLayout.LayoutParams(
                        0,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        1
                )
        );

        TextView arrow = makeText(
                "↗",
                24,
                WHITE,
                true
        );

        card.addView(arrow);

        card.setOnClickListener(
                v -> openOfficialBLS()
        );

        return card;
    }

    // =========================================================
    // BOTTOM NAVIGATION
    // =========================================================

    private View createBottomNavigation() {

        LinearLayout nav = new LinearLayout(this);
        nav.setOrientation(LinearLayout.HORIZONTAL);
        nav.setGravity(Gravity.CENTER);
        nav.setPadding(
                dp(8),
                dp(5),
                dp(8),
                dp(5)
        );

        nav.setBackground(
                rounded(WHITE, 0, BORDER)
        );

        nav.addView(
                navItem(
                        "⌂",
                        "Home",
                        v -> showHome()
                ),
                navParams()
        );

        nav.addView(
                navItem(
                        "▣",
                        "Appointments",
                        v -> showAppointments()
                ),
                navParams()
        );

        nav.addView(
                navItem(
                        "●",
                        "Alerts",
                        v -> showAlerts()
                ),
                navParams()
        );

        nav.addView(
                navItem(
                        "⚙",
                        "Settings",
                        v -> openSettings()
                ),
                navParams()
        );

        return nav;
    }

    private View navItem(
            String icon,
            String name,
            View.OnClickListener listener
    ) {

        LinearLayout item = new LinearLayout(this);
        item.setOrientation(LinearLayout.VERTICAL);
        item.setGravity(Gravity.CENTER);
        item.setClickable(true);

        TextView i = makeText(
                icon,
                21,
                NAVY,
                true
        );

        i.setGravity(Gravity.CENTER);

        TextView n = makeText(
                name,
                
