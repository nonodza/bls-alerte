package com.bls.rendezvous;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class AppointmentMonitoringService extends Service {

    private static final String CHANNEL_ID =
            "bls_monitoring_channel";

    private static final int NOTIFICATION_ID =
            1001;

    @Override
    public void onCreate() {
        super.onCreate();

        createNotificationChannel();

        Notification notification =
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
                        .build();

        startForeground(
                NOTIFICATION_ID,
                notification
        );
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

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
