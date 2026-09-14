package com.bls.rendezvous;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.content.Intent;
import android.net.Uri;
import android.content.SharedPreferences;
import android.view.Gravity;
import android.view.View;
import android.widget.*;

public class MainActivity extends Activity {

    private SharedPreferences prefs;
    private int lang = 0;
    private String country = "Spain";
    private String center = "Algiers";
    private boolean monitoring = false;
    private int checks = 0;

    private final int BG = Color.rgb(10, 12, 22);
    private final int CARD = Color.rgb(24, 27, 40);
    private final int TEXT = Color.WHITE;
    private final int MUTED = Color.rgb(165, 170, 185);
    private final int GOLD = Color.rgb(210, 165, 85);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prefs = getSharedPreferences("bls_global", MODE_PRIVATE);

        lang = prefs.getInt("lang", 0);
        country = prefs.getString("country", "Spain");
        center = prefs.getString("center", "Algiers");
        monitoring = prefs.getBoolean("monitoring", false);
        checks = prefs.getInt("checks", 0);

        home();
    }

    private void home() {

        LinearLayout root = page();

        TextView top = text("BLS Rendez-Vous", 25, TEXT, true);
        root.addView(top);

        TextView sub = text(
                lang == 2 ? "منصة عالمية لخدمات التأشيرات والمواعيد" :
                lang == 1 ? "Plateforme mondiale pour visas et rendez-vous" :
                "Global platform for visas and appointments",
                13, MUTED, false
        );
        root.addView(sub);

        root.addView(space(18));

        LinearLayout status = card();
        TextView st = text(
                monitoring ?
                        "● " + (lang == 2 ? "المراقبة تعمل" :
                                lang == 1 ? "Surveillance active" :
                                "Monitoring active")
                        :
                        "○ " + (lang == 2 ? "المراقبة متوقفة" :
                                lang == 1 ? "Surveillance arrêtée" :
                                "Monitoring stopped"),
                16,
                monitoring ? GOLD : MUTED,
                true
        );
        status.addView(st);

        TextView info = text(
                "🌍 " + country + "   •   🏢 " + center,
                13, MUTED, false
        );
        status.addView(info);
        root.addView(status);

        root.addView(space(15));

        TextView monitor = button(
                monitoring ?
                        "⏹  " + (lang == 2 ? "إيقاف المراقبة" :
                                lang == 1 ? "Arrêter la surveillance" :
                                "Stop Monitoring")
                        :
                        "▶  " + (lang == 2 ? "بدء المراقبة" :
                                lang == 1 ? "Démarrer la surveillance" :
                                "Start Monitoring")
        );

        monitor.setOnClickListener(v -> {
            monitoring = !monitoring;
            prefs.edit().putBoolean("monitoring", monitoring).apply();
            home();
        });

        root.addView(monitor);

        root.addView(space(20));

        root.addView(menu(
                "🌍  Countries",
                "Spain • France • Italy",
                v -> countries()
        ));

        root.addView(menu(
                "🏢  Visa Centers",
                "Algiers • Oran • Annaba",
                v -> centers()
        ));

        root.addView(menu(
                "📅  Appointments",
                "Find and manage appointments",
                v -> appointments()
        ));

        root.addView(menu(
                "🔔  Alerts",
                "Appointment availability alerts",
                v -> alerts()
        ));

        root.addView(menu(
                "📊  Statistics",
                "Monitoring and check statistics",
                v -> statistics()
        ));

        root.addView(menu(
                "🔎  Search",
                "Search countries and services",
                v -> search()
        ));

        root.addView(menu(
                "📋  Track Application",
                "Track your visa application",
                v -> tracking()
        ));

        root.addView(menu(
                "🌐  Official BLS",
                "Official website",
                v -> official()
        ));

        root.addView(menu(
                "⚙️  Settings",
                "Language • Theme • Monitoring",
                v -> settings()
        ));

        show(root);
    }

    private void countries() {

        LinearLayout root = page();
        addBack(root);

        root.addView(title("🌍 Countries"));

        root.addView(countryCard("🇪🇸 Spain", "BLS Spain", "Spain"));
        root.addView(countryCard("🇫🇷 France", "Visa services", "France"));
        root.addView(countryCard("🇮🇹 Italy", "Visa services", "Italy"));

        root.addView(space(15));

        root.addView(infoCard(
                "More countries",
                "Germany • UK • Portugal • Netherlands • Belgium\n" +
                "The platform is designed to add more countries later."
        ));

        show(root);
    }

    private View countryCard(String name, String description, String value) {

        LinearLayout c = card();

        TextView a = text(name, 17, TEXT, true);
        TextView b = text(description, 12, MUTED, false);

        c.addView(a);
        c.addView(b);

        c.setOnClickListener(v -> {

            country = value;

            prefs.edit()
                    .putString("country", country)
                    .apply();

            Toast.makeText(
                    this,
                    "Selected: " + country,
                    Toast.LENGTH_SHORT
            ).show();

            home();
        });

        return c;
    }

    private void centers() {

        LinearLayout root = page();
        addBack(root);

        root.addView(title("🏢 Visa Centers"));

        root.addView(centerCard("🇩🇿 Algiers", "Algiers"));
        root.addView(centerCard("🇩🇿 Oran", "Oran"));
        root.addView(centerCard("🇩🇿 Annaba", "Annaba"));

        root.addView(space(15));

        TextView maps = button("📍 Open Centers in Maps");

        maps.setOnClickListener(v ->
                open("https://www.google.com/maps/search/BLS+Spain+Algeria")
        );

        root.addView(maps);

        show(root);
    }

    private View centerCard(String name, String value) {

        LinearLayout c = card();

        c.addView(text(
                name + (center.equals(value) ? "  ✓" : ""),
                17,
                center.equals(value) ? GOLD : TEXT,
                true
        ));

        c.addView(text(
                "Visa application center",
                12,
                MUTED,
                false
        ));

        c.setOnClickListener(v -> {

            center = value;

            prefs.edit()
                    .putString("center", center)
                    .apply();

            Toast.makeText(
                    this,
                    "Center: " + center,
                    Toast.LENGTH_SHORT
            ).show();

            home();
        });

        return c;
    }

    private void appointments() {

        LinearLayout root = page();
        addBack(root);

        root.addView(title("📅 Appointments"));

        root.addView(infoCard(
                "Selected",
                country + " • " + center
        ));

        TextView find = button("🔎 Check Official Appointment Page");

        find.setOnClickListener(v -> official());

        root.addView(find);

        root.addView(menu(
                "🔔 Appointment Monitoring",
                monitoring ? "Monitoring is active" : "Monitoring is stopped",
                v -> {
                    monitoring = !monitoring;
                    prefs.edit().putBoolean("monitoring", monitoring).apply();
                    appointments();
                }
        ));

        root.addView(infoCard(
                "Important",
                "Availability shown by the official provider is the authoritative source. " +
                "This application does not bypass CAPTCHA or anti-bot protection."
        ));

        show(root);
    }

    private void alerts() {

        LinearLayout root = page();
        addBack(root);

        root.addView(title("🔔 Alerts"));

        root.addView(infoCard(
                "Notifications",
                "Receive an alert when a meaningful change is detected."
        ));

        root.addView(menu(
                monitoring ? "● Monitoring enabled" : "○ Monitoring disabled",
                monitoring ? "The monitoring state is active" :
                        "Start monitoring from the Home screen",
                v -> {
                    monitoring = !monitoring;
                    prefs.edit().putBoolean("monitoring", monitoring).apply();
                    alerts();
                }
        ));

        root.addView(infoCard(
                "Protection",
                "The application respects website security mechanisms and does not attempt to bypass CAPTCHA, Cloudflare or similar protections."
        ));

        show(root);
    }

    private void statistics() {

        LinearLayout root = page();
        addBack(root);

        root.addView(title("📊 Statistics"));

        root.addView(stat("Monitoring", monitoring ? "ACTIVE" : "STOPPED"));
        root.addView(stat("Country", country));
        root.addView(stat("Center", center));
        root.addView(stat("Checks", String.valueOf(checks)));
        root.addView(stat("Interval", "120 seconds"));

        TextView reset = button("Reset Statistics");

        reset.setOnClickListener(v -> {

            checks = 0;

            prefs.edit()
                    .putInt("checks", 0)
                    .apply();

            statistics();
        });

        root.addView(reset);

        show(root);
    }

    private View stat(String a, String b) {

        LinearLayout c = card();

        LinearLayout.LayoutParams p =
                new LinearLayout.LayoutParams(
                        -1,
                        -2
                );

        c.setLayoutParams(p);

        c.addView(text(a, 13, MUTED, false));
        c.addView(text(b, 18, TEXT, true));

        return c;
    }

    private void search() {

        LinearLayout root = page();
        addBack(root);

        root.addView(title("🔎 Search"));

        EditText input = new EditText(this);

        input.setHint("Search country, center or service");
        input.setTextColor(TEXT);
        input.setHintTextColor(MUTED);
        input.setSingleLine(true);

        GradientDrawable bg = new GradientDrawable();
        bg.setColor(CARD);
        bg.setCornerRadius(18);

        input.setBackground(bg);
        input.setPadding(20, 12, 20, 12);

        root.addView(input);

        TextView search = button("Search");

        root.addView(search);

        LinearLayout results = new LinearLayout(this);
        results.setOrientation(LinearLayout.VERTICAL);

        root.addView(results);

        search.setOnClickListener(v -> {

            results.removeAllViews();

            String q = input.getText().toString().toLowerCase();

            if (q.contains("spain") || q.contains("espagne") ||
                    q.contains("إسبانيا")) {

                results.addView(
                        menu(
                                "🇪🇸 Spain",
                                "BLS Spain",
                                x -> countries()
                        )
                );
            }

            if (q.contains("france") || q.contains("français") ||
                    q.contains("فرنسا")) {

                results.addView(
                        menu(
                                "🇫🇷 France",
                                "Visa services",
                                x -> countries()
                        )
                );
            }

            if (q.contains("italy") || q.contains("italie") ||
                    q.contains("إيطاليا")) {

                results.addView(
                        menu(
                                "🇮🇹 Italy",
                                "Visa services",
                                x -> countries()
                        )
                );
            }

            if (q.contains("algiers") ||
                    q.contains("الجزائر")) {

                results.addView(
                        menu(
                                "🏢 Algiers",
                                "Visa Center",
                                x -> centers()
                        )
                );
            }

            if (results.getChildCount() == 0) {

                results.addView(
                        infoCard(
                                "No result",
                                "No matching service was found."
                        )
                );
            }
        });

        show(root);
    }

    private void tracking() {

        LinearLayout root = page();
        addBack(root);

        root.addView(title("📋 Track Application"));

        EditText input = new EditText(this);

        input.setHint("Application / reference number");
        input.setTextColor(TEXT);
        input.setHintTextColor(MUTED);
        input.setSingleLine(true);

        GradientDrawable bg = new GradientDrawable();
        bg.setColor(CARD);
        bg.setCornerRadius(18);

        input.setBackground(bg);
        input.setPadding(20, 12, 20, 12);

        root.addView(input);

        TextView track = button("Open Official Tracking");

        track.setOnClickListener(v ->
                open("https://algeria.blsspainvisa.com/")
        );

        root.addView(track);

        root.addView(infoCard(
                "Notice",
                "The application does not generate or invent application status. " +
                "For a real status, use the official provider."
        ));

        show(root);
    }

    private void settings() {

        LinearLayout root = page();
        addBack(root);

        root.addView(title("⚙️ Settings"));

        root.addView(menu(
                "🌐 Language",
                lang == 0 ? "English" :
                        lang == 1 ? "Français" :
                                "العربية",
                v -> language()
        ));

        root.addView(menu(
                "🌍 Country",
                country,
                v -> countries()
        ));

        root.addView(menu(
                "🏢 Center",
                center,
                v -> centers()
        ));

        root.addView(menu(
                "⏱ Monitoring interval",
                "120 seconds",
                v -> Toast.makeText(
                        this,
                        "Default interval: 120 seconds",
                        Toast.LENGTH_SHORT
                ).show()
        ));

        root.addView(menu(
                "🔔 Notifications",
                "Alerts enabled when monitoring is active",
                v -> alerts()
        ));

        root.addView(menu(
                "🌙 Theme",
                "System",
                v -> Toast.makeText(
                        this,
                        "Theme setting prepared for the next version",
                        Toast.LENGTH_SHORT
                ).show()
        ));

        root.addView(infoCard(
                "BLS Rendez-Vous",
                "Version 2.0\nGlobal visa and appointment platform"
        ));

        show(root);
    }

    private void language() {

        LinearLayout root = page();
        addBack(root);

        root.addView(title("🌐 Language"));

        root.addView(languageCard("English", 0));
        root.addView(languageCard("Français", 1));
        root.addView(languageCard("العربية", 2));

        show(root);
    }

    private View languageCard(String name, int value) {

        LinearLayout c = card();

        c.addView(text(
                name + (lang == value ? "  ✓" : ""),
                17,
                lang == value ? GOLD : TEXT,
                true
        ));

        c.setOnClickListener(v -> {

            lang = value;

            prefs.edit()
                    .putInt("lang", lang)
                    .apply();

            home();
        });

        return c;
    }

    private void official() {

        open("https://algeria.blsspainvisa.com/");
    }

    private LinearLayout page() {

        LinearLayout root = new LinearLayout(this);

        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(20, 35, 20, 30);
        root.setBackgroundColor(BG);

        return root;
    }

    private void addBack(LinearLayout root) {

        TextView back = text("← Back", 15, MUTED, true);

        back.setPadding(0, 0, 0, 20);

        back.setOnClickListener(v -> home());

        root.addView(back);
    }

    private TextView title(String value) {

        return text(value, 24, TEXT, true);
    }

    private TextView text(
            String value,
            int size,
            int color,
            boolean bold
    ) {

        TextView t = new TextView(this);

        t.setText(value);
        t.setTextSize(size);
        t.setTextColor(color);
        t.setPadding(0, 4, 0, 4);

        if (bold) {
            t.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        }

        return t;
    }

    private LinearLayout card() {

        LinearLayout c = new LinearLayout(this);

        c.setOrientation(LinearLayout.VERTICAL);
        c.setPadding(18, 15, 18, 15);

        GradientDrawable bg = new GradientDrawable();

        bg.setColor(CARD);
        bg.setCornerRadius(20);

        c.setBackground(bg);

        LinearLayout.LayoutParams lp =
                new LinearLayout.LayoutParams(
                        -1,
                        -2
                );

        lp.setMargins(0, 0, 0, 12);

        c.setLayoutParams(lp);

        return c;
    }

    private View menu(
            String title,
            String subtitle,
            View.OnClickListener click
    ) {

        LinearLayout c = card();

        TextView a = text(title, 16, TEXT, true);
        TextView b = text(subtitle, 12, MUTED, false);

        c.addView(a);
        c.addView(b);

        c.setOnClickListener(click);

        return c;
    }

    private View infoCard(
            String title,
            String description
    ) {

        LinearLayout c = card();

        c.addView(text(title, 16, GOLD, true));
        c.addView(text(description, 12, MUTED, false));

        return c;
    }

    private TextView button(String value) {

        TextView b = new TextView(this);

        b.setText(value);
        b.setTextSize(16);
        b.setTextColor(BG);
        b.setGravity(Gravity.CENTER);
        b.setTypeface(Typeface.DEFAULT, Typeface.BOLD);

        GradientDrawable bg = new GradientDrawable();

        bg.setColor(GOLD);
        bg.setCornerRadius(30);

        b.setBackground(bg);

        LinearLayout.LayoutParams lp =
                new LinearLayout.LayoutParams(
                        -1,
                        56
                );

        lp.setMargins(0, 8, 0, 12);

        b.setLayoutParams(lp);

        return b;
    }

    private View space(int height) {

        Space s = new Space(this);

        s.setLayoutParams(
                new LinearLayout.LayoutParams(
                        1,
                        height
                )
        );

        return s;
    }

    private void show(LinearLayout root) {

        ScrollView scroll = new ScrollView(this);

        scroll.setFillViewport(true);
        scroll.addView(root);

        setContentView(scroll);
    }

    private void open(String url) {

        try {

            startActivity(
                    new Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse(url)
                    )
            );

        } catch (Exception e) {

            Toast.makeText(
                    this,
                    "Unable to open link",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }
}
