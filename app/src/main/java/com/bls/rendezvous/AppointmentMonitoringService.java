package com.bls.rendezvous;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.widget.RemoteViews;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

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


    // =====================================================
    // MONITORING
    // =====================================================

    private Thread monitoringThread;

    private volatile boolean monitoring =
            false;

    private String selectedCenter =
            "Algiers";


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
                ACTION_STOP.equals(
                        intent.getAction()
                )) {

            stopMonitoring();

            stopForeground(true);

            stopSelf();

            return START_NOT_STICKY;
        }


        // =================================================
        // READ CENTER
        // =================================================

        if (intent != null) {

            String center =
                    intent.getStringExtra(
                            "center"
                    );

            if (center != null &&
                    center.length() > 0) {

                selectedCenter =
                        center;
            }
        }


        // =================================================
        // PENDING INTENT FLAGS
        // =================================================

        int pendingFlags =
                PendingIntent.FLAG_UPDATE_CURRENT;

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

        stopIntent.setAction(
                ACTION_STOP
        );


        PendingIntent stopPendingIntent =
                PendingIntent.getService(
                        this,
                        2001,
                        stopIntent,
                        pendingFlags
                );


        // =================================================
        // VIEW LOG INTENT
        // =================================================

        Intent logIntent =
                new Intent(
                        this,
                        MainActivity.class
                );

        logIntent.addFlags(
                Intent.FLAG_ACTIVITY_SINGLE_TOP |
                Intent.FLAG_ACTIVITY_CLEAR_TOP
        );


        PendingIntent logPendingIntent =
                PendingIntent.getActivity(
                        this,
                        2002,
                        logIntent,
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


        notificationView.setTextViewText(
                R.id.notification_description,
                "Monitoring is active • "
                        + selectedCenter
        );


        // =================================================
        // STOP BUTTON CLICK
        // =================================================

        notificationView.setOnClickPendingIntent(
                R.id.notification_stop,
                stopPendingIntent
        );


        // =================================================
        // VIEW LOG BUTTON CLICK
        // =================================================

        notificationView.setOnClickPendingIntent(
                R.id.notification_log,
                logPendingIntent
        );


        // =================================================
        // NOTIFICATION
        // =================================================

        Notification notification =
                new NotificationCompat.Builder(
                        this,
                        CHANNEL_ID
                )

                        // =================================
                        // ICON
                        // =================================

                        .setSmallIcon(
                                R.drawable.ic_bls_notification
                        )


                        // =================================
                        // CUSTOM REMOTE VIEWS
                        // =================================

                        .setCustomContentView(
                                notificationView
                        )


                        // =================================
                        // TITLE
                        // =================================

                        .setContentTitle(
                                "BLS Rendez-Vous"
                        )


                        // =================================
                        // MAIN STATUS
                        // =================================

                        .setContentText(
                                "Monitoring is active • "
                                        + selectedCenter
                        )


                        // =================================
                        // SECONDARY TEXT
                        // =================================

                        .setSubText(
                                "BLS Spain"
                        )


                        // =================================
                        // BEHAVIOR
                        // =================================

                        .setOngoing(true)

                        .setSilent(true)

                        .setPriority(
                                NotificationCompat.PRIORITY_LOW
                        )

                        .setCategory(
                                NotificationCompat.CATEGORY_SERVICE
                        )

                        .setShowWhen(false)


                        // =================================
                        // BUILD
                        // =================================

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


            channel.setSound(
                    null,
                    null
            );


            channel.enableVibration(
                    false
            );


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


    // =====================================================
    // START MONITORING
    // =====================================================

    private void startMonitoring() {

        if (monitoring) {

            return;
        }


        monitoring =
                true;


        monitoringThread =
                new Thread(
                        new Runnable() {

                            @Override
                            public void run() {

                                BLSMonitor monitor =
                                        new BLSMonitor();


                                while (monitoring) {

                                    try {

                                        BLSMonitor.Result result =
                                                monitor.check(
                                                        selectedCenter
                                                );


                                        // =================================
                                        // LOG
                                        // =================================

                                        if (result != null) {

                                            android.util.Log.d(
                                                    "BLS_MONITOR",
                                                    "Center: "
                                                            + selectedCenter
                                                            + " | Status: "
                                                            + result.getStatus()
                                                            + " | HTTP: "
                                                            + result.getHttpCode()
                                            );
                                        }


                                        // =================================
                                        // WAIT
                                        // =================================

                                        Thread.sleep(
                                                BLSMonitor.CHECK_INTERVAL_MS
                                        );


                                    } catch (
                                            InterruptedException e
                                    ) {

                                        Thread.currentThread()
                                                .interrupt();

                                        break;


                                    } catch (
                                            Exception e
                                    ) {

                                        android.util.Log.e(
                                                "BLS_MONITOR",
                                                "Monitoring error",
                                                e
                                        );


                                        try {

                                            Thread.sleep(
                                                    10000
                                            );


                                        } catch (
                                                InterruptedException ignored
                                        ) {

                                            Thread.currentThread()
                                                    .interrupt();

                                            break;
                                        }
                                    }
                                }
                            }
                        }
                );


        monitoringThread.start();
    }


    // =====================================================
    // STOP MONITORING
    // =====================================================

    private void stopMonitoring() {

        monitoring =
                false;


        if (monitoringThread != null) {

            monitoringThread.interrupt();

            monitoringThread =
                    null;
        }
    }


    // =====================================================
    // ON DESTROY
    // =====================================================

    @Override
    public void onDestroy() {

        stopMonitoring();


        NotificationManager manager =
                getSystemService(
                        NotificationManager.class
                );


        if (manager != null) {

            manager.cancel(
                    NOTIFICATION_ID
            );
        }


        super.onDestroy();
    }


    // =====================================================
    // BIND
    // =====================================================

    @Nullable
    @Override
    public IBinder onBind(
            Intent intent
    ) {

        return null;
    }
}
