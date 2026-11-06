package com.bls.rendezvous;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.widget.RemoteViews;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AppointmentMonitoringService extends Service {

    // =====================================================
    // CONSTANTS
    // =====================================================

    private static final String CHANNEL_ID =
            "bls_monitoring_channel";

    private static final int NOTIFICATION_ID =
            1001;

    private static final String ACTION_STOP =
            "STOP_MONITORING";

    // Shared monitoring state
    public static final String MONITOR_PREFS =
            "RV_MONITORING";

    public static final String KEY_MONITORING_ACTIVE =
            "monitoring_active";


    // =====================================================
    // MONITORING
    // =====================================================

    private Thread monitoringThread;

    private volatile boolean monitoring =
            false;

    private String selectedCenter =
            "Algiers";


    // =====================================================
    // MONITORING INFORMATION
    // =====================================================

    private int checkedCount =
            0;


    // =====================================================
    // NEXT CHECK COUNTDOWN
    // =====================================================

    private volatile long nextCheckTimestamp =
            0L;


    // =====================================================
    // LIVE NOTIFICATION CLOCK
    // =====================================================

    private Handler notificationClockHandler;

    private Runnable notificationClockRunnable;


    // =====================================================
    // SAVE MONITORING STATE
    // =====================================================

    private void saveMonitoringState(boolean active) {

        getSharedPreferences(
                MONITOR_PREFS,
                MODE_PRIVATE
        ).edit()
                .putBoolean(
                        KEY_MONITORING_ACTIVE,
                        active
                )
                .apply();
    }


    // =====================================================
    // ON CREATE
    // =====================================================

    @Override
    public void onCreate() {

        super.onCreate();

        createNotificationChannel();
    }


    // =====================================================
    // ON START COMMAND
    // =====================================================

    @Override
    public int onStartCommand(
            Intent intent,
            int flags,
            int startId
    ) {

  // =================================================
// STOP ACTION
// =================================================

if (intent != null &&
        ACTION_STOP.equals(intent.getAction())) {

    android.util.Log.d(
            "BLS_STOP",
            "STOP command received"
    );

    stopMonitoring();

    stopForeground(true);

    NotificationManager manager =
            getSystemService(NotificationManager.class);

    if (manager != null) {
        manager.cancel(NOTIFICATION_ID);
    }

    stopSelf();

    return START_NOT_STICKY;
}


// =================================================
// PAUSE FOR MANUAL CHECK
// =================================================

if (intent != null &&
        ACTION_PAUSE_FOR_MANUAL_CHECK.equals(intent.getAction())) {

    android.util.Log.d(
            "BLS_MONITOR",
            "Pause requested for manual check"
    );

    // Keep a reference to the running thread
    Thread threadToStop = monitoringThread;

    // Stop monitoring and interrupt its thread
    stopMonitoring();

    // Wait until the monitoring thread has finished
    new Thread(() -> {

        if (threadToStop != null) {

            try {
                threadToStop.join();

            } catch (InterruptedException e) {

                Thread.currentThread().interrupt();

                android.util.Log.e(
                        "BLS_MONITOR",
                        "Waiting for monitoring thread interrupted",
                        e
                );
            }
        }

        // Return to the main thread after monitoring stops
        new Handler(Looper.getMainLooper()).post(() -> {

            stopForeground(true);

            NotificationManager manager =
                    getSystemService(NotificationManager.class);

            if (manager != null) {
                manager.cancel(NOTIFICATION_ID);
            }

            // Notify MainActivity that monitoring has stopped
            Intent pausedIntent =
                    new Intent(ACTION_MONITORING_PAUSED);

            pausedIntent.setPackage(getPackageName());

            sendBroadcast(pausedIntent);

            stopSelf();
        });

    }).start();

    return START_NOT_STICKY;
}      

        // =================================================
        // READ CENTER
        // =================================================

        if (intent != null) {

            String center =
                    intent.getStringExtra("center");

            if (center != null &&
                    !center.trim().isEmpty()) {

                selectedCenter = center;
            }
        }


        // =================================================
        // PENDING INTENT FLAGS
        // =================================================

        int pendingFlags =
                PendingIntent.FLAG_CANCEL_CURRENT;

        if (Build.VERSION.SDK_INT >=
                Build.VERSION_CODES.M) {

            pendingFlags |=
                    PendingIntent.FLAG_IMMUTABLE;
        }


        // =================================================
        // STOP INTENT
        // =================================================

        Intent stopIntent =
                new Intent(
                        this,
                        AppointmentMonitoringService.class
                );

        stopIntent.setAction(ACTION_STOP);

        PendingIntent stopPendingIntent =
                PendingIntent.getService(
                        this,
                        2001,
                        stopIntent,
                        pendingFlags
                );


        // =================================================
        // REMOTE VIEWS
        // =================================================

        RemoteViews notificationView =
                new RemoteViews(
                        getPackageName(),
                        R.layout.notification_monitoring
                );


        updateStatus(notificationView);

        updateInformation(notificationView);


        // =================================================
        // STOP BUTTON
        // =================================================

        notificationView.setOnClickPendingIntent(
                R.id.btnQuitter,
                stopPendingIntent
        );


        // =================================================
        // NOTIFICATION
        // =================================================

        Notification notification =
                new NotificationCompat.Builder(
                        this,
                        CHANNEL_ID
                )
                        .setSmallIcon(
                                R.drawable.ic_bls_notification
                        )
                        .setCustomContentView(
                                notificationView
                        )
                        .setContentTitle(
                                "BLS Rendez-Vous"
                        )
                        .setContentText(
                                "Monitoring is active • "
                                        + selectedCenter
                        )
                        .setSubText("BLS Spain")
                        .setOngoing(true)
                        .setAutoCancel(false)
                        .setSilent(true)
                        .setPriority(
                                NotificationCompat.PRIORITY_LOW
                        )
                        .setCategory(
                                NotificationCompat.CATEGORY_SERVICE
                        )
                        .setShowWhen(false)
                        .build();


        // =================================================
        // START FOREGROUND
        // =================================================

        startForeground(
                NOTIFICATION_ID,
                notification
        );


        // =================================================
        // START MONITORING
        // =================================================

        startMonitoring();

        return START_STICKY;
    }


    // =====================================================
    // UPDATE STATUS
    // =====================================================

    private void updateStatus(
            RemoteViews notificationView
    ) {

        String statusText =
                "Monitoring  ACTIVE  •  "
                        + selectedCenter;

        SpannableString status =
                new SpannableString(statusText);


        int monitoringStart =
                statusText.indexOf("Monitoring");

        if (monitoringStart >= 0) {

            status.setSpan(
                    new ForegroundColorSpan(Color.WHITE),
                    monitoringStart,
                    monitoringStart + "Monitoring".length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            );
        }


        int activeStart =
                statusText.indexOf("ACTIVE");

        if (activeStart >= 0) {

            status.setSpan(
                    new ForegroundColorSpan(
                            Color.rgb(53, 208, 127)
                    ),
                    activeStart,
                    activeStart + "ACTIVE".length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            );
        }


        int centerStart =
                statusText.indexOf(selectedCenter);

        if (centerStart >= 0) {

            status.setSpan(
                    new ForegroundColorSpan(Color.WHITE),
                    centerStart,
                    centerStart + selectedCenter.length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            );
        }


        notificationView.setTextViewText(
                R.id.notification_description,
                status
        );
    }


    // =====================================================
    // UPDATE INFORMATION
    // =====================================================

    private void updateInformation(
            RemoteViews notificationView
    ) {

        String currentTime =
                new SimpleDateFormat(
                        "HH:mm:ss",
                        Locale.getDefault()
                ).format(new Date());


        long remainingMillis =
                nextCheckTimestamp
                        - System.currentTimeMillis();

        long remainingSeconds =
                Math.max(
                        0L,
                        (remainingMillis + 999L) / 1000L
                );


        String timesText =
                checkedCount == 1 ? "time" : "times";

        String countText =
                formatCheckedCount(checkedCount);


        int labelColor =
                Color.rgb(220, 235, 255);

        int valueColor =
                Color.rgb(0, 255, 255);


        SpannableStringBuilder info =
                new SpannableStringBuilder();


        int start = info.length();

        info.append("Last check: ");

        info.setSpan(
                new ForegroundColorSpan(labelColor),
                start,
                info.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );


        start = info.length();

        info.append(currentTime);

        info.setSpan(
                new ForegroundColorSpan(valueColor),
                start,
                info.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );


        info.append("  •  ");

        start = info.length();

        info.append("Checked: ");

        info.setSpan(
                new ForegroundColorSpan(labelColor),
                start,
                info.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );


        start = info.length();

        info.append(countText);

        info.setSpan(
                new ForegroundColorSpan(valueColor),
                start,
                info.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );


        info.append(" ");
        info.append(timesText);
        info.append("  •  ");


        start = info.length();

        info.append("Next: ");

        info.setSpan(
                new ForegroundColorSpan(labelColor),
                start,
                info.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );


        start = info.length();

        info.append(
                String.valueOf(remainingSeconds)
        );

        info.append("s");

        info.setSpan(
                new ForegroundColorSpan(valueColor),
                start,
                info.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );


        notificationView.setTextViewText(
                R.id.notification_info,
                info
        );
    }


    // =====================================================
    // FORMAT CHECK COUNT
    // =====================================================

    private String formatCheckedCount(int count) {

        return String.format(
                Locale.US,
                "%,d",
                count
        );
    }


    // =====================================================
    // NOTIFICATION CHANNEL
    // =====================================================

    private void createNotificationChannel() {

        if (Build.VERSION.SDK_INT >=
                Build.VERSION_CODES.O) {

            NotificationChannel channel =
                    new NotificationChannel(
                            CHANNEL_ID,
                            "BLS Monitoring",
                            NotificationManager.IMPORTANCE_LOW
                    );

            channel.setDescription(
                    "BLS appointment monitoring"
            );

            channel.setSound(null, null);
            channel.enableVibration(false);

            NotificationManager manager =
                    getSystemService(
                            NotificationManager.class
                    );

            if (manager != null) {
                manager.createNotificationChannel(channel);
            }
        }
    }


    // =====================================================
    // START MONITORING
    // =====================================================

    private void startMonitoring() {

        if (monitoring) {
            return;
        }

        monitoring = true;

        // Save active state
        saveMonitoringState(true);


        // =================================================
        // INITIAL COUNTDOWN
        // =================================================

        nextCheckTimestamp =
                System.currentTimeMillis()
                        + BLSMonitor.CHECK_INTERVAL_MS;


        // =================================================
        // START LIVE CLOCK
        // =================================================

        startNotificationClock();


        // =================================================
        // MONITORING THREAD
        // =================================================

        monitoringThread =
                new Thread(() -> {

                    BLSMonitor monitor =
                            new BLSMonitor();

                    while (monitoring) {

                        try {

                            BLSMonitor.Result result =
                                    monitor.check(selectedCenter);

                            checkedCount++;

                            nextCheckTimestamp =
                                    System.currentTimeMillis()
                                            + BLSMonitor.CHECK_INTERVAL_MS;

                            if (result != null) {

                                android.util.Log.d(
                                        "BLS_MONITOR",
                                        "Center: "
                                                + selectedCenter
                                                + " | Status: "
                                                + result.getStatus()
                                                + " | HTTP: "
                                                + result.getHttpCode()
                                                + " | Checked: "
                                                + checkedCount
                                );
                            }

                            updateMonitoringNotification();

                            Thread.sleep(
                                    BLSMonitor.CHECK_INTERVAL_MS
                            );

                        } catch (InterruptedException e) {

                            Thread.currentThread().interrupt();
                            break;

                        } catch (Exception e) {

                            android.util.Log.e(
                                    "BLS_MONITOR",
                                    "Monitoring error",
                                    e
                            );

                            nextCheckTimestamp =
                                    System.currentTimeMillis()
                                            + 10000L;

                            try {

                                Thread.sleep(10000);

                            } catch (InterruptedException ignored) {

                                Thread.currentThread().interrupt();
                                break;
                            }
                        }
                    }
                });

        monitoringThread.start();
    }


    // =====================================================
    // START LIVE NOTIFICATION CLOCK
    // =====================================================

    private void startNotificationClock() {

        stopNotificationClock();

        notificationClockHandler =
                new Handler(Looper.getMainLooper());

        notificationClockRunnable =
                new Runnable() {

                    @Override
                    public void run() {

                        if (!monitoring) {
                            return;
                        }

                        updateMonitoringNotification();

                        if (notificationClockHandler != null) {

                            notificationClockHandler.postDelayed(
                                    this,
                                    1000
                            );
                        }
                    }
                };

        notificationClockHandler.post(
                notificationClockRunnable
        );
    }


    // =====================================================
    // STOP LIVE NOTIFICATION CLOCK
    // =====================================================

    private void stopNotificationClock() {

        if (notificationClockHandler != null &&
                notificationClockRunnable != null) {

            notificationClockHandler.removeCallbacks(
                    notificationClockRunnable
            );
        }

        notificationClockHandler = null;
        notificationClockRunnable = null;
    }


    // =====================================================
    // UPDATE MONITORING NOTIFICATION
    // =====================================================

    private void updateMonitoringNotification() {

        if (!monitoring) {
            return;
        }

        Handler handler =
                new Handler(Looper.getMainLooper());

        handler.post(() -> {

            if (!monitoring) {
                return;
            }

            int pendingFlags =
                    PendingIntent.FLAG_CANCEL_CURRENT;

            if (Build.VERSION.SDK_INT >=
                    Build.VERSION_CODES.M) {

                pendingFlags |=
                        PendingIntent.FLAG_IMMUTABLE;
            }


            Intent stopIntent =
                    new Intent(
                            AppointmentMonitoringService.this,
                            AppointmentMonitoringService.class
                    );

            stopIntent.setAction(ACTION_STOP);

            PendingIntent stopPendingIntent =
                    PendingIntent.getService(
                            AppointmentMonitoringService.this,
                            2001,
                            stopIntent,
                            pendingFlags
                    );


            RemoteViews notificationView =
                    new RemoteViews(
                            getPackageName(),
                            R.layout.notification_monitoring
                    );

            updateStatus(notificationView);
            updateInformation(notificationView);

            notificationView.setOnClickPendingIntent(
                    R.id.btnQuitter,
                    stopPendingIntent
            );


            Notification notification =
                    new NotificationCompat.Builder(
                            AppointmentMonitoringService.this,
                            CHANNEL_ID
                    )
                            .setSmallIcon(
                                    R.drawable.ic_bls_notification
                            )
                            .setCustomContentView(
                                    notificationView
                            )
                            .setContentTitle(
                                    "BLS Rendez-Vous"
                            )
                            .setContentText(
                                    "Monitoring is active • "
                                            + selectedCenter
                            )
                            .setSubText("BLS Spain")
                            .setOngoing(true)
                            .setAutoCancel(false)
                            .setSilent(true)
                            .setPriority(
                                    NotificationCompat.PRIORITY_LOW
                            )
                            .setCategory(
                                    NotificationCompat.CATEGORY_SERVICE
                            )
                            .setShowWhen(false)
                            .build();


            NotificationManager manager =
                    getSystemService(
                            NotificationManager.class
                    );

            if (manager != null) {

                manager.notify(
                        NOTIFICATION_ID,
                        notification
                );
            }
        });
    }


    // =====================================================
    // STOP MONITORING
    // =====================================================

    private void stopMonitoring() {

        monitoring = false;

        // Save inactive state
        saveMonitoringState(false);


        // =================================================
        // STOP LIVE CLOCK
        // =================================================

        stopNotificationClock();


        // =================================================
        // RESET COUNTDOWN
        // =================================================

        nextCheckTimestamp = 0L;


        // =================================================
        // STOP THREAD
        // =================================================

        if (monitoringThread != null) {

            monitoringThread.interrupt();
            monitoringThread = null;
        }
    }


    // =====================================================
    // ON DESTROY
    // =====================================================

    @Override
    public void onDestroy() {

        stopMonitoring();

        NotificationManager manager =
                getSystemService(NotificationManager.class);

        if (manager != null) {
            manager.cancel(NOTIFICATION_ID);
        }

        super.onDestroy();
    }


    // =====================================================
    // BIND
    // =====================================================

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }
                }
