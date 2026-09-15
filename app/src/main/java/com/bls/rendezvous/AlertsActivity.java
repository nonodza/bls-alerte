package com.bls.rendezvous;
import android.content.Intent;
import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class AlertsActivity extends Activity {

    private final int NAVY = Color.rgb(18, 45, 75);
    private final int GOLD = Color.rgb(190, 150, 60);
    private final int GREEN = Color.rgb(35, 130, 75);
    private final int RED = Color.rgb(180, 55, 55);
    private final int GRAY = Color.rgb(100, 100, 100);
    private final int LIGHT = Color.rgb(245, 247, 250);

    private SharedPreferences preferences;

    private TextView centerValue;
    private TextView categoryValue;
    private TextView statusValue;
    private Button monitoringButton;

    private String selectedCenter = "Algiers";
    private String selectedCategory = "ALG1";

    private boolean monitoringEnabled = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preferences = getSharedPreferences(
                "bls_alerts",
                MODE_PRIVATE
        );

        selectedCenter = preferences.getString(
                "center",
                "Algiers"
        );

        selectedCategory = preferences.getString(
                "category",
                "ALG1"
        );

        monitoringEnabled = preferences.getBoolean(
                "monitoring",
                false
        );

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
                20,
                28,
                20,
                22
        );

        TextView title =
                text(
                        "Alerts",
                        25,
                        NAVY
                );

        title.setTypeface(
                Typeface.DEFAULT_BOLD
        );

        title.setGravity(
                Gravity.CENTER
        );

        header.addView(title);

        TextView subtitle =
                text(
                        "Manage your appointment availability alerts.",
                        13,
                        GRAY
                );

        subtitle.setGravity(
                Gravity.CENTER
        );

        header.addView(
                subtitle,
                margin(0, 5, 0, 0)
        );

        root.addView(header);

        // Scroll

        ScrollView scroll =
                new ScrollView(this);

        LinearLayout content =
                new LinearLayout(this);

        content.setOrientation(
                LinearLayout.VERTICAL
        );

        content.setPadding(
                16,
                5,
                16,
                25
        );

        // Availability Alerts

        LinearLayout availability =
                alertCard();

        TextView availabilityTitle =
                text(
                        "AVAILABILITY ALERTS",
                        17,
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
                        "Choose the BLS center and appointment category you want to monitor.",
                        13,
                        GRAY
                ),
                margin(0, 7, 0, 0)
        );

        content.addView(
                availability,
                margin(0, 0, 0, 12)
        );

        // Center

        LinearLayout centerCard =
                alertCard();

        centerCard.addView(
                text(
                        "BLS CENTER",
                        11,
                        GRAY
                )
        );

        centerValue =
                text(
                        selectedCenter,
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

        centerCard.addView(
                text(
                        "Select the center to monitor.",
                        11,
                        GRAY
                ),
                margin(0, 2, 0, 0)
        );

        centerCard.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showCenterDialog();
                    }
                }
        );

        content.addView(
                centerCard,
                margin(0, 0, 0, 8)
        );

        // Category

        LinearLayout categoryCard =
                alertCard();

        categoryCard.addView(
                text(
                        "APPOINTMENT CATEGORY",
                        11,
                        GRAY
                )
        );

        categoryValue =
                text(
                        selectedCategory,
                        17,
                        NAVY
                );

        categoryValue.setTypeface(
                Typeface.DEFAULT_BOLD
        );

        categoryCard.addView(
                categoryValue,
                margin(0, 5, 0, 0)
        );

        categoryCard.addView(
                text(
                        "Select the category to monitor.",
                        11,
                        GRAY
                ),
                margin(0, 2, 0, 0)
        );

        categoryCard.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showCategoryDialog();
                    }
                }
        );

        content.addView(
                categoryCard,
                margin(0, 0, 0, 12)
        );

        // Monitoring

        LinearLayout monitoring =
                alertCard();

        monitoring.addView(
                text(
                        "MONITORING STATUS",
                        11,
                        GRAY
                )
        );

        statusValue =
                text(
                        "",
                        17,
                        NAVY
                );

        statusValue.setTypeface(
                Typeface.DEFAULT_BOLD
        );

        monitoring.addView(
                statusValue,
                margin(0, 5, 0, 0)
        );

        monitoring.addView(
                text(
                        "Background monitoring will be added in the next stage.",
                        11,
                        GRAY
                ),
                margin(0, 2, 0, 0)
        );

        content.addView(
                monitoring,
                margin(0, 0, 0, 10)
        );

        // Monitoring button

        monitoringButton =
                new Button(this);

        monitoringButton.setTextSize(
                15
        );

        monitoringButton.setTypeface(
                Typeface.DEFAULT_BOLD
        );

        monitoringButton.setOnClickListener(
        new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!monitoringEnabled) {

                    Intent serviceIntent =
                            new Intent(
                                    AlertsActivity.this,
                                    AppointmentMonitoringService.class
                            );

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

                    monitoringEnabled = true;

                } else {

                    Intent serviceIntent =
                            new Intent(
                                    AlertsActivity.this,
                                    AppointmentMonitoringService.class
                            );

                    stopService(serviceIntent);

                    monitoringEnabled = false;
                }

                saveSettings();

                updateMonitoringStatus();
            }
        }
);

        content.addView(
                monitoringButton,
                margin(0, 0, 0, 12)
        );

        // Appointment notifications

        LinearLayout notifications =
                alertCard();

        notifications.addView(
                text(
                        "APPOINTMENT NOTIFICATIONS",
                        16,
                        NAVY
                )
        );

        notifications.addView(
                text(
                        "Notifications will be enabled when background monitoring is connected.",
                        13,
                        GRAY
                ),
                margin(0, 7, 0, 0)
        );

        content.addView(
                notifications
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

        setContentView(root);

        updateMonitoringStatus();
    }

    private void showCenterDialog() {

        final String[] centers = {
                "Algiers",
                "Oran"
        };

        int selected = 0;

        if (selectedCenter.equals("Oran")) {
            selected = 1;
        }

        AlertDialog.Builder builder =
                new AlertDialog.Builder(this);

        builder.setTitle(
                "Select BLS Center"
        );

        builder.setSingleChoiceItems(
                centers,
                selected,
                new android.content.DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(
                            android.content.DialogInterface dialog,
                            int which
                    ) {

                        selectedCenter =
                                centers[which];

                        selectedCategory =
                                selectedCenter.equals("Oran")
                                        ? "ORAN1"
                                        : "ALG1";

                        saveSettings();

                        dialog.dismiss();

                        showAlertsPage();
                    }
                }
        );

        builder.setNegativeButton(
                "CANCEL",
                null
        );

        builder.show();
    }

    private void showCategoryDialog() {

        final String[] categories;

        if (selectedCenter.equals("Oran")) {

            categories = new String[] {
                    "ORAN1",
                    "ORAN2",
                    "ORAN3",
                    "ORAN4"
            };

        } else {

            categories = new String[] {
                    "ALG1",
                    "ALG2",
                    "ALG3",
                    "ALG4"
            };
        }

        int selected = 0;

        for (int i = 0; i < categories.length; i++) {

            if (categories[i].equals(
                    selectedCategory
            )) {
                selected = i;
                break;
            }
        }

        AlertDialog.Builder builder =
                new AlertDialog.Builder(this);

        builder.setTitle(
                "Select Appointment Category"
        );

        builder.setSingleChoiceItems(
                categories,
                selected,
                new android.content.DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(
                            android.content.DialogInterface dialog,
                            int which
                    ) {

                        selectedCategory =
                                categories[which];

                        saveSettings();

                        dialog.dismiss();

                        showAlertsPage();
                    }
                }
        );

        builder.setNegativeButton(
                "CANCEL",
                null
        );

        builder.show();
    }

    private void updateMonitoringStatus() {

        if (monitoringEnabled) {

            statusValue.setText(
                    "● Monitoring ON"
            );

            statusValue.setTextColor(
                    GREEN
            );

            monitoringButton.setText(
                    "STOP MONITORING"
            );

        } else {

            statusValue.setText(
                    "● Monitoring OFF"
            );

            statusValue.setTextColor(
                    RED
            );

            monitoringButton.setText(
                    "START MONITORING"
            );
        }
    }

    private void saveSettings() {

        preferences.edit()
                .putString(
                        "center",
                        selectedCenter
                )
                .putString(
                        "category",
                        selectedCategory
                )
                .putBoolean(
                        "monitoring",
                        monitoringEnabled
                )
                .apply();
    }

    private LinearLayout alertCard() {

        LinearLayout card =
                new LinearLayout(this);

        card.setOrientation(
                LinearLayout.VERTICAL
        );

        card.setPadding(
                18,
                16,
                18,
                16
        );

        GradientDrawable background =
                new GradientDrawable();

        background.setColor(
                Color.WHITE
        );

        background.setCornerRadius(
                18
        );

        background.setStroke(
                1,
                Color.rgb(225, 225, 225)
        );

        card.setBackground(
                background
        );

        return card;
    }

    private TextView text(
            String value,
            int size,
            int color
    ) {

        TextView t =
                new TextView(this);

        t.setText(
                value
        );

        t.setTextSize(
                size
        );

        t.setTextColor(
                color
        );

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
