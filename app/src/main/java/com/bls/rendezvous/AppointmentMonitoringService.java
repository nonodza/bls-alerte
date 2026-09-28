package com.bls.rendezvous;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.text.SpannableString;
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
    // LIVE NOTIFICATION CLOCK
    // =====================================================

    private Handler notificationClockHandler;

    private Runnable notificationClockRunnable;


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
        // REMOTE VIEWS
        // =================================================

        RemoteViews notificationView =
                new RemoteViews(
                        getPackageName(),
                        R.layout.notification_monitoring
                );


        // =================================================
        // UPDATE STATUS
        // =================================================

        updateStatus(
                notificationView
        );


        // =================================================
        // UPDATE INFORMATION
        // =================================================

        updateInformation(
                notificationView
        );


        // =================================================
        // STOP BUTTON
        // =================================================

        notificationView.setOnClickPendingIntent(
                R.id.notification_stop,
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

                        // =================================
                        // ICON
                        // =================================

                        .setSmallIcon(
                                R.drawable.ic_bls_notification
                        )


                        // =================================
                        // CUSTOM VIEW
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
                        // STATUS
                        // =================================

                        .setContentText(
                                "Monitoring is active • "
                                        + selectedCenter
                        )


                        // =================================
                        // SECONDARY
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
    // UPDATE STATUS
    // =====================================================

    private void updateStatus(
            RemoteViews notificationView
    ) {

        String statusText =
                "Monitoring  ACTIVE  •  "
                        + selectedCenter;


        SpannableString status =
                new SpannableString(
                        statusText
                );


        // =================================================
        // MONITORING = WHITE
        // =================================================

        int monitoringStart =
                statusText.indexOf(
                        "Monitoring"
                );

        if (monitoringStart >= 0) {

            status.setSpan(
                    new ForegroundColorSpan(
                            Color.WHITE
                    ),
                    monitoringStart,
                    monitoringStart
                            + "Monitoring".length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            );
        }


        // =================================================
        // ACTIVE = GREEN
        // =================================================

        int activeStart =
                statusText.indexOf(
                        "ACTIVE"
                );

        if (activeStart >= 0) {

            status.setSpan(
                    new ForegroundColorSpan(
                            Color.rgb(
                                    53,
                                    208,
                                    127
                            )
                    ),
                    activeStart,
                    activeStart
                            + "ACTIVE".length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            );
        }


        // =================================================
        // CENTER = WHITE
        // =================================================

        int centerStart =
                statusText.indexOf(
                        selectedCenter
                );

        if (centerStart >= 0) {

            status.setSpan(
                    new ForegroundColorSpan(
                            Color.WHITE
                    ),
                    centerStart,
                    centerStart
                            + selectedCenter.length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            );
        }


        // =================================================
        // APPLY STATUS
        // =================================================

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
                ).format(
                        new Date()
                );


        String informationText =
                "Last check: "
                        + currentTime
                        + "  •  Checked: "
                        + formatCheckedCount(
                                checkedCount
                        )
                        + " times";


        notificationView.setTextViewText(
                R.id.notification_info,
                informationText
        );
    }


    // =====================================================
    // FORMAT CHECK COUNT
    // =====================================================

    private String formatCheckedCount(
            int count
    ) {

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


        // =================================================
        // START LIVE CLOCK
        // =================================================

        startNotificationClock();


        // =================================================
        // MONITORING THREAD
        // =================================================

        monitoringThread =
                new Thread(
                        new Runnable() {

                            @Override
                            public void run() {

                                BLSMonitor monitor =
                                        new BLSMonitor();


                                while (monitoring) {

                                    try {

                                        // =================================
                                        // REAL BLS CHECK
                                        // =================================

                                        BLSMonitor.Result result =
                                                monitor.check(
                                                        selectedCenter
                                                );


                                        // =================================
                                        // COUNT REAL CHECK
                                        // =================================

                                        checkedCount++;


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
                                                            + " | Checked: "
                                                            + checkedCount
                                            );
                                        }


                                        // =================================
                                        // UPDATE AFTER REAL CHECK
                                        // =================================

                                        updateMonitoringNotification();


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
    // START LIVE NOTIFICATION CLOCK
    // =====================================================

    private void startNotificationClock() {

        stopNotificationClock();


        notificationClockHandler =
                new Handler(
                        Looper.getMainLooper()
                );


        notificationClockRunnable =
                new Runnable() {

                    @Override
                    public void run() {

                        if (!monitoring) {

                            return;
                        }


                        // =================================
                        // UPDATE LIVE TIME
                        // =================================

                        updateMonitoringNotification();


                        // =================================
                        // NEXT SECOND
                        // =================================

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


        notificationClockHandler =
                null;

        notificationClockRunnable =
                null;
    }


    // =====================================================
    // UPDATE MONITORING NOTIFICATION
    // =====================================================

    private void updateMonitoringNotification() {

        if (!monitoring) {

            return;
        }


        Handler handler =
                new Handler(
                        Looper.getMainLooper()
                );


        handler.post(
                new Runnable() {

                    @Override
                    public void run() {

                        if (!monitoring) {

                            return;
                        }


                        // =================================
                        // FLAGS
                        // =================================

                        int pendingFlags =
                                PendingIntent.FLAG_CANCEL_CURRENT;


                        if (Build.VERSION.SDK_INT >=
                                Build.VERSION_CODES.M) {

                            pendingFlags |=
                                    PendingIntent.FLAG_IMMUTABLE;
                        }


                        // =================================
                        // STOP INTENT
                        // =================================

                        Intent stopIntent =
                                new Intent(
                                        AppointmentMonitoringService.this,
                                        AppointmentMonitoringService.class
                                );


                        stopIntent.setAction(
                                ACTION_STOP
                        );


                        PendingIntent stopPendingIntent =
                                PendingIntent.getService(
                                        AppointmentMonitoringService.this,
                                        2001,
                                        stopIntent,
                                        pendingFlags
                                );


                        // =================================
                        // REMOTE VIEW
                        // =================================

                        RemoteViews notificationView =
                                new RemoteViews(
                                        getPackageName(),
                                        R.layout.notification_monitoring
                                );


                        // =================================
                        // STATUS
                        // =================================

                        updateStatus(
                                notificationView
                        );


                        // =================================
                        // INFORMATION
                        // =================================

                        updateInformation(
                                notificationView
                        );


                        // =================================
                        // STOP
                        // =================================

                        notificationView.setOnClickPendingIntent(
                                R.id.notification_stop,
                                stopPendingIntent
                        );


                        // =================================
                        // BUILD UPDATED NOTIFICATION
                        // =================================

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

                                        .setSubText(
                                                "BLS Spain"
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


                        // =================================
                        // PUBLISH
                        // =================================

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
                    }
                }
        );
    }


    // =====================================================
    // STOP MONITORING
    // =====================================================

    private void stopMonitoring() {

        monitoring =
                false;


        // =================================================
        // STOP LIVE CLOCK
        // =================================================

        stopNotificationClock();


        // =================================================
        // STOP THREAD
        // =================================================

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
