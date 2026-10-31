
package com.bls.rendezvous;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.widget.RemoteViews;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ManualCheckService extends Service {

    public static final String ACTION_START =
            "com.bls.rendezvous.MANUAL_CHECK_START";

    public static final String ACTION_CANCEL =
            "com.bls.rendezvous.MANUAL_CHECK_CANCEL";

    public static final String ACTION_DISMISS =
            "com.bls.rendezvous.MANUAL_CHECK_DISMISS";

    public static final String PREFS_NAME =
            "manual_check_results";

    public static final String KEY_STATUS = "status";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_DURATION = "duration";
    public static final String KEY_FINISHED = "finished";

    private static final String CHANNEL_ID =
            "manual_check_channel_v2";

    private static final int NOTIFICATION_ID = 2001;

    private final Handler mainHandler =
            new Handler(Looper.getMainLooper());

    private volatile boolean cancelled = false;
    private String selectedCountry = "";
    private Thread checkThread;


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

        if (intent == null) {

            stopSelf(startId);

            return START_NOT_STICKY;
        }


        String action = intent.getAction();


        // =================================================
        // DISMISS
        // =================================================

        if (ACTION_DISMISS.equals(action)) {

            NotificationManager manager =
                    getSystemService(NotificationManager.class);

            if (manager != null) {

                manager.cancel(NOTIFICATION_ID);
            }

            stopSelf(startId);

            return START_NOT_STICKY;
        }


        // =================================================
        // CANCEL
        // =================================================

        if (ACTION_CANCEL.equals(action)) {

            cancelled = true;

            if (checkThread != null) {

                checkThread.interrupt();
            }

            stopForeground(true);

            stopSelf();

            return START_NOT_STICKY;
        }


        // =================================================
        // START
        // =================================================

        if (ACTION_START.equals(action)) {

            if (checkThread != null &&
                    checkThread.isAlive()) {

                return START_NOT_STICKY;
            }


            String center =
                    intent.getStringExtra("center");
           selectedCountry = intent.getStringExtra("country");

if (selectedCountry == null || selectedCountry.trim().isEmpty()) {
    selectedCountry = "Unknown country";
}

            if (center == null ||
                    center.trim().isEmpty()) {

                center = "Algiers";
            }
            selectedCountry = intent.getStringExtra("country");

if (selectedCountry == null ||
        selectedCountry.trim().isEmpty()) {

    selectedCountry = "Unknown country";
}

            cancelled = false;


            saveResult(
                    "CHECKING",
                    "Checking BLS website...",
                    0L,
                    false
            );


            startForeground(
                    NOTIFICATION_ID,
                    buildProgressNotification(
                            "Checking BLS website..."
                    )
            );


            startManualCheck(
                    center,
                    startId
            );
        }


        return START_NOT_STICKY;
    }


    // =====================================================
    // START MANUAL CHECK
    // =====================================================

    private void startManualCheck(
            String center,
            int startId
    ) {

        final String selectedCenter = center;


        checkThread = new Thread(() -> {

            long startTime =
                    System.currentTimeMillis();


            String finalStatus;
            String finalMessage;

            long duration;


            try {

                BLSMonitor monitor =
                        new BLSMonitor();


                BLSMonitor.Result result =
                        monitor.check(selectedCenter);


                duration =
                        System.currentTimeMillis()
                                - startTime;


                if (cancelled ||
                        Thread.currentThread().isInterrupted()) {

                    return;
                }


                finalStatus =
                        result.getStatus().name();


                finalMessage =
                        result.getMessage();


                if (finalMessage == null ||
                        finalMessage.trim().isEmpty()) {

                    finalMessage =
                            "Check finished.";
                }


            } catch (Exception e) {

                if (cancelled ||
                        Thread.currentThread().isInterrupted()) {

                    return;
                }


                duration =
                        System.currentTimeMillis()
                                - startTime;


                finalStatus = "ERROR";


                finalMessage =
                        "Manual check failed. Please try again.";
            }


            if (cancelled ||
                    Thread.currentThread().isInterrupted()) {

                return;
            }


            saveResult(
                    finalStatus,
                    finalMessage,
                    duration,
                    true
            );


            final String notificationMessage =
                    "Result: "
                            + finalStatus
                            + " — "
                            + finalMessage;


            mainHandler.post(() -> {

                if (cancelled) {

                    return;
                }


                stopForeground(true);


                showFinalNotification(
                        notificationMessage
                );


                checkThread = null;


                stopSelfResult(startId);
            });

        });


        checkThread.start();
    }


    // =====================================================
    // SAVE RESULT
    // =====================================================

    private void saveResult(
            String status,
            String message,
            long duration,
            boolean finished
    ) {

        SharedPreferences prefs =
                getSharedPreferences(
                        PREFS_NAME,
                        MODE_PRIVATE
                );


        prefs.edit()

                .putString(
                        KEY_STATUS,
                        status
                )

                .putString(
                        KEY_MESSAGE,
                        message == null ? "" : message
                )

                .putLong(
                        KEY_DURATION,
                        duration
                )

                .putBoolean(
                        KEY_FINISHED,
                        finished
                )

                .apply();
    }


    // =====================================================
    // PROGRESS NOTIFICATION
    // =====================================================

    private Notification buildProgressNotification(
            String message
    ) {

        Intent cancelIntent =
                new Intent(
                        this,
                        ManualCheckService.class
                );


        cancelIntent.setAction(
                ACTION_CANCEL
        );


        PendingIntent cancelPendingIntent =
                PendingIntent.getService(
                        this,
                        2002,
                        cancelIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                                | PendingIntent.FLAG_IMMUTABLE
                );


        return new Notification.Builder(
                this,
                CHANNEL_ID
        )

                .setSmallIcon(
                        android.R.drawable.stat_notify_sync
                )

                .setContentTitle(
                        "Manual BLS Check"
                )

                .setContentText(
                        message
                )

                .setOngoing(true)

                .setOnlyAlertOnce(true)

                .addAction(
                        android.R.drawable.ic_menu_close_clear_cancel,
                        "CANCEL",
                        cancelPendingIntent
                )

                .build();
    }


    // =====================================================
    // SHOW FINAL NOTIFICATION
    // =====================================================

    private void showFinalNotification(
            String message
    ) {

        NotificationManager manager =
                getSystemService(NotificationManager.class);


        if (manager == null) {

            return;
        }


        SharedPreferences prefs =
                getSharedPreferences(
                        PREFS_NAME,
                        MODE_PRIVATE
                );


        String status =
                prefs.getString(
                        KEY_STATUS,
                        "UNKNOWN"
                );


        String description;


        // =================================================
        // STATUS DESCRIPTION
        // =================================================

        switch (status) {

            case "WEBSITE_OK":

                description =
                        "BLS website is reachable";

                break;


            case "NO_APPOINTMENT":

                description =
                        "No appointment was detected";

                break;


            case "ACCESS_BLOCKED":

                description =
                        "Website access is blocked";

                break;


            case "TOO_MANY_REQUESTS":

                description =
                        "Please wait before trying again";

                break;


            case "WEBSITE_UNAVAILABLE":
            case "SERVER_ERROR":

                description =
                        "BLS website is temporarily unavailable";

                break;


            case "CONNECTION_ERROR":

                description =
                        "Check your internet connection";

                break;


            case "ERROR":

                description =
                        "Manual check failed";

                break;


            default:

                description =
                        "Your BLS check has finished";

                break;
        }


        // =================================================
        // CHECK TIME
        // =================================================

        String checkTime =
                new SimpleDateFormat(
                        "HH:mm:ss",
                        Locale.getDefault()
                ).format(
                        new Date()
                );


        // =================================================
        // INFORMATION TEXT
        // =================================================

        SpannableStringBuilder statusInfo =
                new SpannableStringBuilder();


        int labelColor =
                Color.rgb(220, 235, 255);


        int valueColor =
                Color.rgb(0, 255, 255);


        int start =
                statusInfo.length();


        statusInfo.append(
                "Last check: "
        );


        statusInfo.setSpan(
                new ForegroundColorSpan(labelColor),
                start,
                statusInfo.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );


        start =
                statusInfo.length();


        statusInfo.append(
                checkTime
        );


        statusInfo.setSpan(
                new ForegroundColorSpan(valueColor),
                start,
                statusInfo.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );


        // =================================================
        // REMOTE VIEWS
        // =================================================

        RemoteViews remoteViews =
                new RemoteViews(
                        getPackageName(),
                        R.layout.notification_manual_check
                );


        // =================================================
        // PREMIUM RV LOGO
        // =================================================

        remoteViews.setImageViewResource(
                R.id.notification_rv,
                R.drawable.rv_premium_logo
        );


        // =================================================
        // TITLE
        // =================================================

        remoteViews.setTextViewText(
                R.id.notification_title,
                "BLS Rendez-Vous"
        );


        // =================================================
        // DESCRIPTION
        // =================================================

        remoteViews.setTextViewText(
                R.id.notification_description,
                description
        );


        // =================================================
        // COLORED INFORMATION
        // =================================================

        remoteViews.setTextViewText(
                R.id.notification_info,
                statusInfo
        );


        // =================================================
        // OPEN BLS INTENT
        // =================================================

        Intent openIntent =
                new Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(
                                "https://algeria.blsspainvisa.com/algiers/"
                        )
                );


        PendingIntent openPendingIntent =
                PendingIntent.getActivity(
                        this,
                        2010,
                        openIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                                | PendingIntent.FLAG_IMMUTABLE
                );


        // =================================================
        // DISMISS INTENT
        // =================================================

        Intent dismissIntent =
                new Intent(
                        this,
                        ManualCheckService.class
                );


        dismissIntent.setAction(
                ACTION_DISMISS
        );


        PendingIntent dismissPendingIntent =
                PendingIntent.getService(
                        this,
                        2011,
                        dismissIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                                | PendingIntent.FLAG_IMMUTABLE
                );


        // =================================================
        // FINAL NOTIFICATION
        // =================================================

        Notification notification =
                new Notification.Builder(
                        this,
                        CHANNEL_ID
                )

                        .setSmallIcon(
                                android.R.drawable.stat_notify_sync
                        )

                        .setCustomContentView(
                                remoteViews
                        )

                        .setContentTitle(
                                "BLS Rendez-Vous"
                        )

                        .setContentText(
                                description
                        )

                        .setContentIntent(
                                openPendingIntent
                        )

                        .addAction(
                                android.R.drawable.ic_menu_view,
                                "Open BLS",
                                openPendingIntent
                        )

                        .addAction(
                                android.R.drawable.ic_menu_close_clear_cancel,
                                "Dismiss",
                                dismissPendingIntent
                        )

                        .setAutoCancel(true)

                        .setOngoing(false)

                        .setOnlyAlertOnce(true)

                        .build();


        manager.notify(
                NOTIFICATION_ID,
                notification
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
                "Appointment Availability",
                NotificationManager.IMPORTANCE_DEFAULT
        );


            channel.setDescription(
                    "Progress and results of a manual BLS check"
            );


            NotificationManager manager =
                    getSystemService(NotificationManager.class);


            if (manager != null) {

                manager.createNotificationChannel(
                        channel
                );
            }
        }
    }


    // =====================================================
    // ON DESTROY
    // =====================================================

    @Override
    public void onDestroy() {

        cancelled = true;


        if (checkThread != null) {

            checkThread.interrupt();
        }


        mainHandler.removeCallbacksAndMessages(null);


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
