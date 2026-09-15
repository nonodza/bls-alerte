
package com.bls.rendezvous;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class AlertsActivity extends Activity {

    private int NAVY = Color.rgb(18, 45, 75);
    private int GOLD = Color.rgb(190, 150, 60);
    private int GRAY = Color.rgb(100, 100, 100);
    private int LIGHT = Color.rgb(245, 247, 250);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        showAlertsPage();
    }

    private void showAlertsPage() {

        LinearLayout root =
                new LinearLayout(this);

        root.setOrientation(
                LinearLayout.VERTICAL
        );

        root.setBackgroundColor(
                LIGHT
        );

        // Header

        LinearLayout header =
                new LinearLayout(this);

        header.setOrientation(
                LinearLayout.VERTICAL
        );

        header.setGravity(
                Gravity.CENTER
        );

        header.setPadding(
                20, 30, 20, 25
        );

        TextView title =
                new TextView(this);

        title.setText(
                "BLS Alerts"
        );

        title.setTextSize(25);

        title.setTextColor(
                NAVY
        );

        title.setTypeface(
                Typeface.DEFAULT_BOLD
        );

        title.setGravity(
                Gravity.CENTER
        );

        header.addView(
                title
        );

        TextView subtitle =
                new TextView(this);

        subtitle.setText(
                "Stay updated with important visa information"
        );

        subtitle.setTextSize(13);

        subtitle.setTextColor(
                GRAY
        );

        subtitle.setGravity(
                Gravity.CENTER
        );

        header.addView(
                subtitle
        );

        root.addView(
                header
        );

        // Scroll content

        ScrollView scroll =
                new ScrollView(this);

        LinearLayout content =
                new LinearLayout(this);

        content.setOrientation(
                LinearLayout.VERTICAL
        );

        content.setPadding(
                16, 10, 16, 20
        );

        // Welcome card

        LinearLayout welcome =
                alertCard();

        TextView welcomeTitle =
                text(
                        "🔔  IMPORTANT UPDATES",
                        17,
                        NAVY
                );

        welcomeTitle.setTypeface(
                Typeface.DEFAULT_BOLD
        );

        welcome.addView(
                welcomeTitle
        );

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

        // Appointment alerts

        LinearLayout appointment =
                alertCard();

        TextView appointmentTitle =
                text(
                        "APPOINTMENT ALERTS",
                        16,
                        NAVY
                );

        appointmentTitle.setTypeface(
                Typeface.DEFAULT_BOLD
        );

        appointment.addView(
                appointmentTitle
        );

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

        // BLS updates

        LinearLayout updates =
                alertCard();

        TextView updatesTitle =
                text(
                        "BLS UPDATES",
                        16,
                        NAVY
                );

        updatesTitle.setTypeface(
                Typeface.DEFAULT_BOLD
        );

        updates.addView(
                updatesTitle
        );

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

        // Official source

        LinearLayout official =
                alertCard();

        TextView officialTitle =
                text(
                        "OFFICIAL SOURCE",
                        16,
                        NAVY
                );

        officialTitle.setTypeface(
                Typeface.DEFAULT_BOLD
        );

        official.addView(
                officialTitle
        );

        official.addView(
                text(
                        "Alerts will be based on official BLS Spain information.",
                        13,
                        GRAY
                ),
                margin(0, 7, 0, 0)
        );

        content.addView(
                official
        );

        scroll.addView(
                content
        );

        root.addView(
                scroll,
                new LinearLayout.LayoutParams(
                        -1,
                        0,
                        1
                )
        );

        setContentView(
                root
        );
    }

    private LinearLayout alertCard() {

        LinearLayout card =
                new LinearLayout(this);

        card.setOrientation(
                LinearLayout.VERTICAL
        );

        card.setPadding(
                18, 16, 18, 16
        );

        GradientDrawable background =
                new GradientDrawable();

        background.setColor(
                Color.WHITE
        );

        background.setCornerRadius(
            }     
