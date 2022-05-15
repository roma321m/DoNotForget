package roman.app.donotforget.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import roman.app.donotforget.R;
import roman.app.donotforget.activities.MainActivity;

public class ForegroundServiceManager extends Service {

    private final String CHANNEL_ID = "Foreground Service";
    private NotificationChannel channel;
    private Notification.Builder allGoodNotification;
    private Notification.Builder wakeUpNotification;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        channel = new NotificationChannel(CHANNEL_ID,CHANNEL_ID, NotificationManager.IMPORTANCE_LOW);
        allGoodNotification = new Notification.Builder(this, CHANNEL_ID)
                .setContentTitle("All good!")
                .setContentText("All tasks are handled on time")
                .setSmallIcon(R.drawable.ic_launcher_foreground);
        wakeUpNotification = new Notification.Builder(this, CHANNEL_ID)
                .setContentTitle("Wake up!")
                .setContentText("DoNotForget doing your tasks!")
                .setSmallIcon(R.drawable.ic_launcher_foreground);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    Log.e(MainActivity.TAG_SERVICE, "Service running");
                    // TODO: 15/05/2022 check the tasks
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        getSystemService(NotificationManager.class).createNotificationChannel(channel);
        startForeground(1001, allGoodNotification.build());

        return super.onStartCommand(intent, flags, startId);
    }

    // TODO: 15/05/2022 - add implementation to change the notification.

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
