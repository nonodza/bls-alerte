package com.bls.rendezvous;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class AlertsActivity extends Activity {

    private final int NAVY = Color.rgb(18, 45, 75);
    private final int GRAY = Color.rgb(100, 100, 100);
    private final int LIGHT = Color.rgb(245, 247, 250);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showAlertsPage();
    }

    private void showAlertsPage() {

        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setBackgroundColor(LIGHT);

        LinearLayout header = new LinearLayout(this);
        header.setOrientation(LinearLayout.VERTICAL);
        header.setGravity(Gravity.CENTER);
        header.setPadding(20, 30, 20, 25);

        TextView title = new TextView(this);
        title.setText("BLS Alerts");
        title.setTextSize(25);
        title.setTextColor(NAVY);
        title.setTypeface(Typeface.DEFAULT_BOLD);
        title.setGravity(Gravity.CENTER);
        header.addView(title);

        TextView subtitle = new TextView(this);
        subtitle.setText(
                "Stay updated with important visa information"
        );
        subtitle.setTextSize(13);
        subtitle.setTextColor(GRAY);
        subtitle.setGravity(Gravity.CENTER);
        header.addView(subtitle);

        root.addView(header);

        ScrollView scroll = new ScrollView(this);

        LinearLayout content = new LinearLayout(this);
        content.setOrientation(LinearLayout.VERTICAL);
        content.setPadding(16, 10, 16, 20);

        LinearLayout welcome = alertCard();

        TextView welcomeTitle = text(
                "IMPORTANT UPDATES",
                17,
                NAVY
        );
        welcomeTitle.setTypeface(Typeface.DEFAULT_BOLD);
        welcome.addView(welcomeTitle);

        welcome.addView(
                text(
                        "Receive important information about BLS Spain visa services, appointments and updates.",
                        13,
                        GRAY
                ),
                margin(0, 8, 0, 0)
        );

        content.addView(
                welcome,
                margin(0, 0, 0, 12)
        );

        LinearLayout appointment = alertCard();

        TextView appointmentTitle = text(
                "APPOINTMENT ALERTS",
                16,
                NAVY
        );
        appointmentTitle.setTypeface(Typeface.DEFAULT_BOLD);
        appointment.addView(appointmentTitle);

        appointment.addView(
                text(
                        "Get notified when important appointment information is available.",
                        13,
                        GRAY
                ),
                margin(0, 7, 0, 0)
        );

        content.addView(
                appointment,
                margin(0, 0, 0, 10)
        );

        LinearLayout updates = alertCard();

        TextView updatesTitle = text(
                "BLS UPDATES",
                16,
                NAVY
        );
        updatesTitle.setTypeface(Typeface.DEFAULT_BOLD);
        updates.addView(updatesTitle);

        updates.addView(
                text(
                        "Important announcements and changes will appear here.",
                        13,
                        GRAY
                ),
                margin(0, 7, 0, 0)
        );

        content.addView(
                updates,
                margin(0, 0, 0, 10)
        );

        LinearLayout official = alertCard();

        TextView officialTitle = text(
                "OFFICIAL SOURCE",
                16,
                NAVY
        );
        officialTitle.setTypeface(Typeface.DEFAULT_BOLD);
        official.addView(officialTitle);

        official.addView(
                text(
                        "Alerts will be based on official BLS Spain information.",
                        13,
                        GRAY
                ),
                margin(0, 7, 0, 0)
        );

        content.addView(official);

        scroll.addView(content);

        root.addView(
                scroll,
                new LinearLayout.LayoutParams(
                        -1,
                        0,
                        1
                )
        );

        setContentView(root);
    }

    private LinearLayout alertCard() {

        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setPadding(18, 16, 18, 16);

        GradientDrawable background = new GradientDrawable();
        background.setColor(Color.WHITE);
        background.setCornerRadius(18);
        background.setStroke(
                1,
                Color.rgb(225, 225, 225)
        );

        card.setBackground(background);

        return card;
    }

    private TextView text(
            String value,
            int size,
            int color
    ) {

        TextView t = new TextView(this);

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
                left,
                top,
                right,
                bottom
        );

        return p;
    }
}
