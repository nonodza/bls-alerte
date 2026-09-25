package com.bls.rendezvous;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class AppointmentMonitoringService extends Service {

    private static final String CHANNEL_ID =
            "bls_monitoring_channel";

    private static final int NOTIFICATION_ID =
            1001;

    private static final String TAG =
            "BLS_NOTIFICATION";

    private ScheduledExecutorService scheduler;

    private String selectedCenter =
            "Algiers";

    @Override
    public void onCreate() {

        super.onCreate();

        createNotificationChannel();

        Notification notification;

        try {

            // =====================================================
            // CUSTOM NOTIFICATION
            // =====================================================

            RemoteViews notificationView =
                    new RemoteViews(
                            getPackageName(),
                            R.layout.notification_monitoring
                    );

            notification =
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
                            .setOngoing(true)
                            .setSilent(true)
                            .setPriority(
                                    NotificationCompat.PRIORITY_LOW
                            )
                            .setCategory(
                                    NotificationCompat.CATEGORY_SERVICE
                            )
                            .setShowWhen(false)
                            .build();

            Log.d(
                    TAG,
                    "Custom notification created successfully"
            );

        } catch (Exception e) {

            // =====================================================
            // FALLBACK NOTIFICATION
            // =====================================================

            Log.e(
                    TAG,
                    "CUSTOM NOTIFICATION FAILED",
                    e
            );

            notification =
                    new NotificationCompat.Builder(
                            this,
                            CHANNEL_ID
                    )
                            .setSmallIcon(
                                    R.drawable.ic_bls_notification
                            )
                            .setContentTitle(
                                    "BLS Rendez-Vous"
                            )
                            .setContentText(
                                    "Monitoring is active"
                            )
                            .setOngoing(true)
                            .setSilent(true)
                            .setPriority(
                                    NotificationCompat.PRIORITY_LOW
                            )
                            .setCategory(
                                    NotificationCompat.CATEGORY_SERVICE
                            )
                            .setShowWhen(false)
                            .build();
        }

        // =====================================================
        // START FOREGROUND
        // =====================================================

        startForeground(
                NOTIFICATION_ID,
                notification
        );

        // =====================================================
        // MONITORING SCHEDULER
        // =====================================================

        scheduler =
                Executors.newSingleThreadScheduledExecutor();
    }

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
                    "Quiet notification for BLS monitoring"
            );

            channel.setSound(
                    null,
                    null
            );

            channel.enableVibration(false);
            channel.setShowBadge(false);

            NotificationManager manager =
                    getSystemService(
                            NotificationManager.class
                    );

            if (manager != null) {

                manager.createNotificationChannel(
                        channel
                );
            }
        }
    }

    @Override
    public int onStartCommand(
            Intent intent,
            int flags,
            int startId
    ) {

        if (intent != null) {

            String center =
                    intent.getStringExtra(
                            "center"
                    );

            if (center != null &&
                    center.length() > 0) {

                selectedCenter = center;
            }
        }

        // =====================================================
        // TEMPORARILY DISABLED
        // =====================================================

        // startMonitoring();

        return START_STICKY;
    }

    // =====================================================
    // START BLS MONITORING
    // =====================================================

    private void startMonitoring() {

        if (scheduler == null) {
            return;
        }

        if (scheduler.isShutdown()) {
            return;
        }

        scheduler.scheduleAtFixedRate(
                new Runnable() {

                    @Override
                    public void run() {

                        BLSMonitor monitor =
                                new BLSMonitor();

                        BLSMonitor.Result result =
                                monitor.check(
                                        selectedCenter
                                );

                        Log.d(
                                "BLS_MONITOR",
                                "Center: "
                                        + selectedCenter
                                        + " | Status: "
                                        + result.getStatus()
                                        + " | HTTP: "
                                        + result.getHttpCode()
                        );
                    }
                },
                0,
                BLSMonitor.CHECK_INTERVAL_MS,
                TimeUnit.MILLISECONDS
        );
    }

    @Override
    public void onDestroy() {

        if (scheduler != null) {

            scheduler.shutdownNow();

            scheduler = null;
        }

        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
