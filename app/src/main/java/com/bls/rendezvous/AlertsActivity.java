package com.bls.rendezvous;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.core.app.NotificationCompat;

public class AlertsActivity extends Activity {

    private final int NAVY = Color.rgb(18, 45, 75);
    private final int GOLD = Color.rgb(190, 150, 60);
    private final int GREEN = Color.rgb(35, 130, 75);
    private final int RED = Color.rgb(180, 55, 55);
    private final int GRAY = Color.rgb(100, 100, 100);
    private final int LIGHT = Color.rgb(245, 247, 250);

    private static final int NOTIFICATION_PERMISSION_REQUEST = 2001;

    private static final String TEST_CHANNEL_ID =
            "bls_test_channel";

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

        centerCard.setClickable(true);

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

        categoryCard.setClickable(true);

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
                        "Background monitoring service.",
                        11,
                        GRAY
                ),
                margin(0, 2, 0, 0)
        );

        content.addView(
                monitoring,
                margin(0, 0, 0, 10)
        );

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

                            startMonitoring();

                        } else {

                            stopMonitoring();

                        }
                    }
                }
        );

        content.addView(
                monitoringButton,
                margin(0, 0, 0, 12)
        );

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
                        "You will receive a notification when monitoring detects an appointment opportunity.",
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

    private void startMonitoring() {

        if (Build.VERSION.SDK_INT >=
                Build.VERSION_CODES.TIRAMISU) {

            if (checkSelfPermission(
                    Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(
                        new String[]{
                                Manifest.permission.POST_NOTIFICATIONS
                        },
                        NOTIFICATION_PERMISSION_REQUEST
                );

                return;
            }
        }

        launchMonitoringService();
    }

    private void launchMonitoringService() {

        // TEST NOTIFICATION
        showTestNotification();

        // START FOREGROUND SERVICE
        Intent serviceIntent =
                new Intent(
                        AlertsActivity.this,
                        AppointmentMonitoringService.class
                );

        if (Build.VERSION.SDK_INT >=
                Build.VERSION_CODES.O) {

            startForegroundService(
                    serviceIntent
            );

        } else {

            startService(
                    serviceIntent
            );
        }

        monitoringEnabled = true;

        saveSettings();

        updateMonitoringStatus();
    }

    private void showTestNotification() {

        String channelId =
                TEST_CHANNEL_ID;

        NotificationManager manager =
                (NotificationManager)
                        getSystemService(
                                NOTIFICATION_SERVICE
                        );

        if (Build.VERSION.SDK_INT >=
                Build.VERSION_CODES.O) {

            NotificationChannel channel =
                    new NotificationChannel(
                            channelId,
                            "BLS Alerts",
                            NotificationManager.IMPORTANCE_HIGH
                    );

            channel.setDescription(
                    "BLS appointment alerts"
            );

            if (manager != null) {

                manager.createNotificationChannel(
                        channel
                );
            }
        }

        Notification notification =
                new NotificationCompat.Builder(
                        this,
                        channelId
                )
                        .setSmallIcon(
                                android.R.drawable.ic_popup_sync
                        )
                        .setContentTitle(
                                "BLS Rendez-Vous"
                        )
                        .setContentText(
                                "Test notification - Monitoring"
                        )
                        .setPriority(
                                NotificationCompat.PRIORITY_HIGH
                        )
                        .setCategory(
                                NotificationCompat.CATEGORY_SERVICE
                        )
                        .setAutoCancel(false)
                        .build();

        if (manager != null) {

            manager.notify(
                    5001,
                    notification
            );
        }
    }

    private void stopMonitoring() {

        Intent serviceIntent =
                new Intent(
                        AlertsActivity.this,
                        AppointmentMonitoringService.class
                );

        stopService(
                serviceIntent
        );

        NotificationManager manager =
                (NotificationManager)
                        getSystemService(
                                NOTIFICATION_SERVICE
                        );

        if (manager != null) {

            manager.cancel(5001);
        }

        monitoringEnabled = false;

        saveSettings();

        updateMonitoringStatus();
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            String[] permissions,
            int[] grantResults
    ) {

        super.onRequestPermissionsResult(
                requestCode,
                permissions,
                grantResults
        );

        if (requestCode ==
                NOTIFICATION_PERMISSION_REQUEST) {

            if (grantResults.length > 0 &&
                    grantResults[0] ==
                            PackageManager.PERMISSION_GRANTED) {

                launchMonitoringService();

            } else {

                new AlertDialog.Builder(this)
                        .setTitle(
                                "Notifications Required"
                        )
                        .setMessage(
                                "Please allow notifications so BLS monitoring can show its status and alerts."
                        )
                        .setPositiveButton(
                                "OK",
                                null
                        )
                        .show();
            }
        }
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
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(
                            DialogInterface dialog,
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

            categories = new String[]{
                    "ORAN1",
                    "ORAN2",
                    "ORAN3",
                    "ORAN4"
            };

        } else {

            categories = new String[]{
                    "ALG1",
                    "ALG2",
                    "ALG3",
                    "ALG4"
            };
        }

        int selected = 0;

        for (int i = 0;
             i < categories.length;
             i++) {

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
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(
                            DialogInterface dialog,
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
                    "● Monitoring ON • ACTIVE"
            );

            statusValue.setTextColor(
                    GREEN
            );

            monitoringButton.setText(
                    "STOP MONITORING"
            );

        }
