package com.bls.rendezvous;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.IBinder;

public class ManualCheckService extends Service {

    public static final String ACTION_START =
            "com.bls.rendezvous.MANUAL_CHECK_START";

    public static final String ACTION_CANCEL =
            "com.bls.rendezvous.MANUAL_CHECK_CANCEL";

    public static final String PREFS_NAME =
            "manual_check_results";

    public static final String KEY_STATUS = "status";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_DURATION = "duration";
    public static final String KEY_FINISHED = "finished";

    private static final String CHANNEL_ID =
            "manual_check_channel";

    private static final int NOTIFICATION_ID = 2001;

    private volatile boolean cancelled = false;
    private Thread checkThread;

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
    }

    @Override
    public int onStartCommand(
            Intent intent,
            int flags,
            int startId
    ) {
        if (intent == null) {
            stopSelf();
            return START_NOT_STICKY;
        }

        String action = intent.getAction();

        if (ACTION_CANCEL.equals(action)) {
            cancelled = true;

            if (checkThread != null) {
                checkThread.interrupt();
            }

            stopForeground(true);
            stopSelf();
            return START_NOT_STICKY;
        }

        if (ACTION_START.equals(action)) {
            String center = intent.getStringExtra("center");

            if (center == null || center.trim().isEmpty()) {
                center = "Algiers";
            }

            startForeground(
                    NOTIFICATION_ID,
                    buildNotification("Checking BLS website...")
            );

            startManualCheck(center);
        }

        return START_NOT_STICKY;
    }

    private void startManualCheck(String center) {
        if (checkThread != null && checkThread.isAlive()) {
            return;
        }

        cancelled = false;

        final String selectedCenter = center;

        checkThread = new Thread(() -> {
            long startTime = System.currentTimeMillis();

            saveResult(
                    "CHECKING",
                    "Checking BLS website...",
                    0L,
                    false
            );

            try {
                BLSMonitor monitor = new BLSMonitor(this);

                BLSMonitor.Result result =
                        monitor.check(selectedCenter);

                long duration =
                        System.currentTimeMillis() - startTime;

                if (cancelled || Thread.currentThread().isInterrupted()) {
                    return;
                }

                String status = result.getStatus().name();
                String message = result.getMessage();

                saveResult(
                        status,
                        message,
                        duration,
                        true
                );

                updateNotification(
                        "Check finished: " + status
                );

            } catch (Exception e) {
                if (!cancelled) {
                    long duration =
                            System.currentTimeMillis() - startTime;

                    saveResult(
                            "ERROR",
                            "Manual check failed. Please try again.",
                            duration,
                            true
                    );

                    updateNotification("Check failed");
                }
            }

            if (!cancelled) {
                stopForeground(false);
                stopSelf();
            }
        });

        checkThread.start();
    }

    private void saveResult(
            String status,
            String message,
            long duration,
            boolean finished
    ) {
        SharedPreferences prefs =
                getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        prefs.edit()
                .putString(KEY_STATUS, status)
                .putString(KEY_MESSAGE, message == null ? "" : message)
                .putLong(KEY_DURATION, duration)
                .putBoolean(KEY_FINISHED, finished)
                .apply();
    }

    private Notification buildNotification(String message) {
        Intent cancelIntent =
                new Intent(this, ManualCheckService.class);

        cancelIntent.setAction(ACTION_CANCEL);

        PendingIntent cancelPendingIntent =
                PendingIntent.getService(
                        this,
                        2002,
                        cancelIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                                | PendingIntent.FLAG_IMMUTABLE
                );

        return new Notification.Builder(this, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.stat_notify_sync)
                .setContentTitle("Manual BLS Check")
                .setContentText(message)
                .setOngoing(true)
                .setOnlyAlertOnce(true)
                .addAction(
                        android.R.drawable.ic_menu_close_clear_cancel,
                        "CANCEL",
                        cancelPendingIntent
                )
                .build();
    }

    private void updateNotification(String message) {
        NotificationManager manager =
                getSystemService(NotificationManager.class);

        if (manager != null) {
            Notification notification =
                    buildNotification(message);

            manager.notify(NOTIFICATION_ID, notification);
        }
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel =
                    new NotificationChannel(
                            CHANNEL_ID,
                            "Manual BLS Check",
                            NotificationManager.IMPORTANCE_LOW
                    );

            channel.setDescription(
                    "Progress and result of a manual BLS check"
            );

            NotificationManager manager =
                    getSystemService(NotificationManager.class);

            if (manager != null) {
                manager.createNotificationChannel(channel);
            }
        }
    }

    @Override
    public void onDestroy() {
        cancelled = true;

        if (checkThread != null) {
            checkThread.interrupt();
        }

        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
