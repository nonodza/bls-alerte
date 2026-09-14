package com.bls.rendezvous;

import android.content.Context;
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

    // =========================
    // COLORS
    // =========================
    private static final int BG_TOP = Color.rgb(248, 250, 255);
    private static final int BG_BOTTOM = Color.rgb(229, 235, 249);

    private static final int WHITE = Color.WHITE;
    private static final int NAVY = Color.rgb(25, 39, 72);
    private static final int BLUE = Color.rgb(48, 104, 220);
    private static final int BLUE_LIGHT = Color.rgb(236, 242, 255);
    private static final int GOLD = Color.rgb(183, 137, 69);
    private static final int GREEN = Color.rgb(35, 160, 100);
    private static final int RED = Color.rgb(210, 70, 70);
    private static final int TEXT = Color.rgb(40, 48, 66);
    private static final int MUTED = Color.rgb(115, 125, 145);
    private static final int BORDER = Color.rgb(225, 230, 240);

    private FrameLayout contentContainer;

    // =========================
    // ACTIVITY
    // =========================
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        buildMainInterface();
    }

    // =========================
    // MAIN INTERFACE
    // =========================
    private void buildMainInterface() {

        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);

        GradientDrawable background = new GradientDrawable(
                GradientDrawable.Orientation.TL_BR,
                new int[]{BG_TOP, Color.rgb(241, 245, 253), BG_BOTTOM}
        );
        root.setBackground(background);

        // Content area
        contentContainer = new FrameLayout(this);

        LinearLayout.LayoutParams contentParams =
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        0,
                        1
                );

        root.addView(contentContainer, contentParams);

        // Bottom navigation
        View bottomNavigation = createBottomNavigation();

        root.addView(
                bottomNavigation,
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        dp(76)
                )
        );

        setContentView(root);

        showHome();
    }

    // =========================
    // HOME
    // =========================
    private void showHome() {

        contentContainer.removeAllViews();

        ScrollView scrollView = new ScrollView(this);
        scrollView.setFillViewport(true);
        scrollView.setVerticalScrollBarEnabled(false);

        LinearLayout page = new LinearLayout(this);
        page.setOrientation(LinearLayout.VERTICAL);
        page.setPadding(dp(20), dp(18), dp(20), dp(25));

        // Header
        LinearLayout header = new LinearLayout(this);
        header.setOrientation(LinearLayout.HORIZONTAL);
        header.setGravity(Gravity.CENTER_VERTICAL);

        LinearLayout titleBox = new LinearLayout(this);
        titleBox.setOrientation(LinearLayout.VERTICAL);

        TextView appName = text(
                "BLS Rendez-Vous",
                23,
                NAVY,
                true
        );

        TextView subtitle = text(
                "Your visa appointment assistant",
                13,
                MUTED,
                false
        );

        titleBox.addView(appName);
        titleBox.addView(subtitle);

        header.addView(
                titleBox,
                new LinearLayout.LayoutParams(
                        0,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        1
                )
        );

        TextView settingsButton = text("⚙", 25, NAVY, false);
        settingsButton.setGravity(Gravity.CENTER);

        GradientDrawable settingsBg = rounded(
                WHITE,
                18,
                BORDER
        );
        settingsButton.setBackground(settingsBg);

        header.addView(
                settingsButton,
                new LinearLayout.LayoutParams(dp(48), dp(48))
        );

        settingsButton.setOnClickListener(v -> openSettings());

        page.addView(header);

        addSpace(page, 18);

        // Current application card
        page.addView(createCurrentApplicationCard());

        addSpace(page, 18);

        // Section title
        page.addView(
                sectionTitle("Services", "Everything you need in one place")
        );

        addSpace(page, 12);

        // TRUE 2 x 3 GRID
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
                weightParams()
        );

        row1.addView(
                serviceCard(
                        "🔔",
                        "Alerts",
                        "Availability alerts",
                        GOLD,
                        v -> showAlerts()
                ),
                weightParams()
        );

        page.addView(row1);

        addSpace(page, 12);

        LinearLayout row2 = new LinearLayout(this);
        row2.setOrientation(LinearLayout.HORIZONTAL);

        row2.addView(
                serviceCard(
                        "🏢",
                        "Centers",
                        "Visa centers",
                        Color.rgb(95, 105, 190),
                        v -> showCenters()
                ),
                weightParams()
        );

        row2.addView(
                serviceCard(
                        "🔎",
                        "Tracking",
                        "Track application",
                        GREEN,
                        v -> showTracking()
                ),
                weightParams()
        );

        page.addView(row2);

        addSpace(page, 12);

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
                weightParams()
        );

        row3.addView(
                serviceCard(
                        "📊",
                        "Statistics",
                        "Your activity",
                        Color.rgb(130, 90, 180),
                        v -> showStatistics()
                ),
                weightParams()
        );

        page.addView(row3);

        addSpace(page, 18);

        // Search
        page.addView(createSearchCard());

        addSpace(page, 14);

        // Official BLS
        page.addView(createOfficialCard());

        scrollView.addView(page);

        contentContainer.addView(scrollView);
    }

    // =========================
    // CURRENT APPLICATION CARD
    // =========================
    private View createCurrentApplicationCard() {

        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setPadding(dp(18), dp(17), dp(18), dp(17));

        card.setBackground(rounded(
                WHITE,
                22,
                BORDER
        ));

        // Top row
        LinearLayout top = new LinearLayout(this);
        top.setOrientation(LinearLayout.HORIZONTAL);
        top.setGravity(Gravity.CENTER_VERTICAL);

        LinearLayout countryBox = new LinearLayout(this);
        countryBox.setOrientation(LinearLayout.VERTICAL);

        TextView small = text(
                "CURRENT APPLICATION",
                11,
                MUTED,
                true
        );

        TextView country = text(
                "🇪🇸  Spain",
                21,
                NAVY,
                true
        );

        countryBox.addView(small);
        countryBox.addView(country);

        top.addView(
                countryBox,
                new LinearLayout.LayoutParams(
                        0,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        1
                )
        );

        TextView active = text(
                "● ACTIVE",
                12,
                GREEN,
                true
        );

        top.addView(active);

        card.addView(top);

        addSpace(card, 15);

        // Location
        LinearLayout location = new LinearLayout(this);
        location.setOrientation(LinearLayout.HORIZONTAL);

        TextView locationText = text(
                "📍  Algiers Visa Center",
                14,
                TEXT,
                false
        );

        location.addView(locationText);

        card.addView(location);

        addSpace(card, 12);

        // Monitoring
        LinearLayout monitoring = new LinearLayout(this);
        monitoring.setOrientation(LinearLayout.HORIZONTAL);
        monitoring.setGravity(Gravity.CENTER_VERTICAL);

        TextView monitorLabel = text(
                "Monitoring",
                13,
                MUTED,
                false
        );

        monitoring.addView(
                monitorLabel,
                new LinearLayout.LayoutParams(
                        0,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        1
                )
        );

        TextView monitorValue = text(
                "Every 2 minutes",
                13,
                NAVY,
                true
        );

        monitoring.addView(monitorValue);

        card.addView(monitoring);

        return card;
    }

    // =========================
    // SERVICE CARD
    // =========================
    private View serviceCard(
            String icon,
            String title,
            String subtitle,
            int iconColor,
            View.OnClickListener listener
    ) {

        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setPadding(dp(16), dp(15), dp(14), dp(15));
        card.setClickable(true);
        card.setFocusable(true);

        card.setBackground(rounded(
                WHITE,
                20,
                BORDER
        ));

        // Icon
        TextView iconView = text(
                icon,
                25,
                iconColor,
                false
        );

        GradientDrawable iconBg = rounded(
                BLUE_LIGHT,
                14,
                Color.TRANSPARENT
        );

        iconView.setBackground(iconBg);
        iconView.setGravity(Gravity.CENTER);

        card.addView(
                iconView,
                new LinearLayout.LayoutParams(
                        dp(46),
                        dp(46)
                )
        );

        addSpace(card, 12);

        TextView titleView = text(
                title,
                15,
                NAVY,
                true
        );

        card.addView(titleView);

        addSpace(card, 3);

        TextView subView = text(
                subtitle,
                11,
                MUTED,
                false
        );

        card.addView(subView);

        card.setOnClickListener(listener);

        return card;
    }

    // =========================
    // SEARCH
    // =========================
    private View createSearchCard() {

        LinearLayout search = new LinearLayout(this);
        search.setOrientation(LinearLayout.HORIZONTAL);
        search.setGravity(Gravity.CENTER_VERTICAL);
        search.setPadding(dp(16), 0, dp(16), 0);

        search.setBackground(rounded(
                WHITE,
                18,
                BORDER
        ));

        TextView icon = text(
                "🔎",
                20,
                MUTED,
                false
        );

        search.addView(
                icon,
                new LinearLayout.LayoutParams(
                        dp(35),
                        ViewGroup.LayoutParams.WRAP_CONTENT
                )
        );

        TextView label = text(
                "Search countries, centers or services",
                13,
                MUTED,
                false
        );

        search.addView(
                label,
                new LinearLayout.LayoutParams(
                        0,
                        dp(58),
                        1
                )
        );

        search.setOnClickListener(v -> showSearch());

        return search;
    }

    // =========================
    // OFFICIAL BLS
    // =========================
    private View createOfficialCard() {

        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.HORIZONTAL);
        card.setGravity(Gravity.CENTER_VERTICAL);
        card.setPadding(dp(16), dp(14), dp(16), dp(14));

        card.setBackground(rounded(
                NAVY,
                20,
                NAVY
        ));

        LinearLayout textBox = new LinearLayout(this);
        textBox.setOrientation(LinearLayout.VERTICAL);

        TextView title = text(
                "Official BLS",
                15,
                WHITE,
                true
        );

        TextView sub = text(
                "Visit the official visa information",
                11,
                Color.rgb(190, 200, 220),
                false
        );

        textBox.addView(title);
        textBox.addView(sub);

        card.addView(
                textBox,
                new LinearLayout.LayoutParams(
                        0,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        1
                )
        );

        TextView arrow = text(
                "↗",
                24,
                WHITE,
                true
        );

        card.addView(arrow);

        card.setOnClickListener(v -> openOfficialBLS());

        return card;
    }

    // =========================
    // BOTTOM NAVIGATION
    // =========================
    private View createBottomNavigation() {

        LinearLayout nav = new LinearLayout(this);
        nav.setOrientation(LinearLayout.HORIZONTAL);
        nav.setGravity(Gravity.CENTER);
        nav.setPadding(dp(8), dp(6), dp(8), dp(6));

        nav.setBackground(rounded(
                WHITE,
                0,
                BORDER
        ));

        nav.addView(
                navigationItem(
                        "⌂",
                        "Home",
                        v -> showHome()
                ),
                navParams()
        );

        nav.addView(
                navigationItem(
                        "▣",
                        "Appointments",
                        v -> showAppointments()
                ),
                navParams()
        );

        nav.addView(
                navigationItem(
                        "●",
                        "Alerts",
                        v -> showAlerts()
                ),
                navParams()
        );

        nav.addView(
                navigationItem(
                        "⚙",
                        "Settings",
                        v -> openSettings()
                ),
                navParams()
        );

        return nav;
    }

    private View navigationItem(
            String icon,
            String label,
            View.OnClickListener listener
    ) {

        LinearLayout item = new LinearLayout(this);
        item.setOrientation(LinearLayout.VERTICAL);
        item.setGravity(Gravity.CENTER);
        item.setClickable(true);

        TextView iconView = text(
                icon,
                22,
                NAVY,
                true
        );

        iconView.setGravity(Gravity.CENTER);

        TextView labelView = text(
                label,
                10,
                MUTED,
                false
        );

        labelView.setGravity(Gravity.CENTER);

        item.addView(iconView);
        item.addView(labelView);

        item.setOnClickListener(listener);

        return item;
    }

    // =========================
    // SIMPLE PAGES
    // =========================
    private void showAppointments() {
        showSimplePage(
                "Appointments",
                "Find and manage your visa appointments.",
                new String[]{
                        "Spain",
                        "France",
                        "Italy"
                }
        );
    }

    private void showAlerts() {
        showSimplePage(
                "Alerts",
                "Get notified when appointment information changes.",
                new String[]{
                        "Monitoring status",
                        "Availability alerts",
                        "Notification settings"
                }
        );
    }

    private void showCenters() {
        showSimplePage(
                "Visa Centers",
                "Choose the center you want to monitor.",
                new String[]{
                        "Algiers",
                        "Oran",
                        "Annaba"
                }
        );
    }

    private void showCountries() {
        showSimplePage(
                "Countries",
                "Choose your destination country.",
                new String[]{
                        "🇪🇸 Spain",
                        "🇫🇷 France",
                        "🇮🇹 Italy"
                }
        );
    }

    private void showTracking() {
        showSimplePage(
                "Track Application",
                "Follow your visa application status.",
                new String[]{
                        "Enter application reference",
                        "Passport information",
                        "Check status"
                }
        );
    }

    private void showStatistics() {
        showSimplePage(
                "Statistics",
                "Overview of your monitoring activity.",
                new String[]{
                        "Checks: 0",
                        "Alerts: 0",
                        "Appointments: 0"
                }
        );
    }

    private void showSearch() {
        showSimplePage(
                "Search",
                "Search across countries, centers and services.",
                new String[]{
                        "Spain",
                        "Algiers",
                        "Appointments",
                        "Tracking"
                }
        );
    }

    // =========================
    // SIMPLE PAGE UI
    // =========================
    private void showSimplePage(
            String title,
            String description,
            String[] items
    ) {

        ScrollView scroll = new ScrollView(this);
        scroll.setFillViewport(true);
        scroll.setVerticalScrollBarEnabled(false);

        LinearLayout page = new LinearLayout(this);
        page.setOrientation(LinearLayout.VERTICAL);
        page.setPadding(dp(20), dp(22), dp(20), dp(30));

        // Back button
        TextView back = text(
                "‹  Back",
                15,
                BLUE,
                true
        );

        back.setPadding(0, 0, 0, dp(10));

        back.setOnClickListener(v -> showHome());

        page.addView(back);

        TextView titleView = text(
                title,
                28,
                NAVY,
                true
        );

        page.addView(titleView);

        addSpace(page, 6);

        TextView desc = text(
                description,
                14,
                MUTED,
                false
        );

        page.addView(desc);

        addSpace(page, 20);

        for (String item : items) {

            LinearLayout card = new LinearLayout(this);
            card.setOrientation(LinearLayout.HORIZONTAL);
            card.setGravity(Gravity.CENTER_VERTICAL);
            card.setPadding(dp(17), dp(16), dp(17), dp(16));

            card.setBackground(rounded(
                    WHITE,
                    20,
                    BORDER
            ));

            TextView icon = text(
                    "•",
                    22,
                    BLUE,
                    true
            );

            card.addView(
                    icon,
                    new LinearLayout.LayoutParams(
                            dp(35),
                            ViewGroup.LayoutParams.WRAP_CONTENT
                    )
            );

            TextView itemText = text(
                    item,
                    15,
                    TEXT,
                    true
            );

            card.addView(
                    itemText,
                    new LinearLayout.LayoutParams(
                            0,
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            1
                    )
            );

            TextView arrow = text(
                    "›",
                    25,
                    MUTED,
                    false
            );

            card.addView(arrow);

            page.addView(card);

            addSpace(page, 12);
        }

        scroll.addView(page);
        contentContainer.removeAllViews();
        contentContainer.addView(scroll);
    }

    // =========================
    // SETTINGS
    // =========================
    private void openSettings() {

        try {

            Intent intent = new Intent(
                    MainActivity.this,
                    SettingsActivity.class
            );

            startActivity(intent);

        } catch (Exception e) {

            showSimplePage(
                    "Settings",
                    "Application settings",
                    new String[]{
                            "Language",
                            "Theme",
                            "Country",
                            "Visa center",
                            "Monitoring interval",
                            "Notifications"
                    }
            );
        }
    }

    // =========================
    // OFFICIAL WEBSITE
    // =========================
    private void openOfficialBLS() {

        try {

            Intent intent = new Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://algeria.blsspainvisa.com/")
            );

            startActivity(intent);

        } catch (Exception ignored) {
        }
    }

    // =========================
    // HELPERS
    // =========================

    private TextView sectionTitle(
            String title,
            String subtitle
    ) {

        LinearLayout box = new LinearLayout(this);
        box.setOrientation(LinearLayout.VERTICAL);

        TextView titleView = text(
                title,
                20,
                NAVY,
                true
        );

        TextView subView = text(
                subtitle,
                12,
                MUTED,
                false
        );

        box.addView(titleView);
        box.addView(subView);

        return createSectionContainer(box);
    }

    private TextView createSectionContainer(View view) {

        FrameLayout frame = new FrameLayout(this);

        frame.addView(
                view,
                new FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                )
        );

        TextView result = new TextView(this);
        result.setVisibility(View.GONE);

        // Return title-like TextView while preserving the view in a container
        return buildSectionText(view);
    }

    private TextView buildSectionText(View view) {

        TextView invisibleHolder = new TextView(this);

        invisibleHolder.setText("");

        // This TextView acts as a lightweight container replacement.
        // The actual section is rebuilt below by copying its content.
        if (view instanceof LinearLayout) {

            LinearLayout original = (LinearLayout) view;

            if (original.getChildCount() >= 2) {

                TextView title =
                        (TextView) original.getChildAt(0);

                TextView subtitle =
                        (TextView) original.getChildAt(1);

                invisibleHolder.setText(
                        title.getText() + "\n" + subtitle.getText()
                );

                invisibleHolder.setTextSize(0);
                invisibleHolder.setPadding(0, 0, 0, 0);

                // Not used visually; section is rebuilt directly.
            }
        }

        // We return a proper TextView with the title and subtitle
        invisibleHolder.setText("");

        return new SectionView(this, view);
    }

    // Custom TextView carrying a child view
    private static class SectionView extends TextView {

        private final View child;

        public SectionView(Context context, View child) {
            super(context);
            this.child = child;

            setGravity(Gravity.LEFT);
            setPadding(0, 0, 0, 0);
        }

        @Override
        protected void onMeasure(
                int widthMeasureSpec,
                int heightMeasureSpec
        ) {

            child.measure(
                    widthMeasureSpec,
                    MeasureSpec.makeMeasureSpec(
                            0,
                            MeasureSpec.UNSPECIFIED
                    )
            );

            setMeasuredDimension(
                    MeasureSpec.getSize(widthMeasureSpec),
                    child.getMeasuredHeight()
            );
        }

        @Override
        protected void onDraw(
                android.graphics.Canvas canvas
        ) {

            child.layout(
                    0,
                    0,
                    getWidth(),
                    child.getMeasuredHeight()
            );

            child.draw(canvas);
        }
    }

    private TextView text(
            String value,
            float size,
            int color,
            boolean bold
    ) {

        TextView t = new TextView(this);

        t.setText(value);
        t.setTextSize(size);
        t.setTextColor(color);

        if (bold) {
            t.setTypeface(
                    android.graphics.Typeface.DEFAULT,
                    android.graphics.Typeface.BOLD
            );
        }

        t.setGravity(Gravity.CENTER_VERTICAL);

        return t;
    }

    private GradientDrawable rounded(
            int color,
            int radius,
            int strokeColor
    ) {

        GradientDrawable drawable = new GradientDrawable();

        drawable.setColor(color);
        drawable.setCornerRadius(dp(radius));

        if (strokeColor != Color.TRANSPARENT) {
            drawable.setStroke(dp(1), strokeColor);
        }

        return drawable;
    }

    private LinearLayout.LayoutParams weightParams() {

        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(
                        0,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        1
                );

        params.setMargins(
                dp(4),
                0,
                dp(4),
                0
        );

        return params;
    }

    private LinearLayout.LayoutParams navParams() {

        return new LinearLayout.LayoutParams(
                0,
                ViewGroup.LayoutParams.MATCH_PARENT,
                1
        );
    }

    private void addSpace(
            LinearLayout parent,
            int height
    ) {

        View space = new View(this);

        parent.addView(
                space,
                new LinearLayout.LayoutParams(
                        1,
                        dp(height)
                )
        );
    }

    private int dp(int value) {

        return (int) (
                value *
                        getResources()
                                .getDisplayMetrics()
                                .density
        );
    }
}
