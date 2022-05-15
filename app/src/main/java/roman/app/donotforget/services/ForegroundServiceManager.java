package roman.app.donotforget.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import roman.app.donotforget.R;
import roman.app.donotforget.activities.MainActivity;
import roman.app.donotforget.data.DataManager;

public class ForegroundServiceManager extends Service {

    private final String CHANNEL_ID = "Foreground Service";
    private final int NOTIF_ID = 1001;
    private NotificationChannel channel;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        channel = new NotificationChannel(CHANNEL_ID, CHANNEL_ID, NotificationManager.IMPORTANCE_LOW);

        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean a = false;
                while (true) {
                    //Log.e(MainActivity.TAG_SERVICE, "Service running");
                    if (DataManager.getInstance().isStateChanged()){
                        Log.e(MainActivity.TAG_SERVICE, "Service - changing notif");
                        updateNotification();
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        getSystemService(NotificationManager.class).createNotificationChannel(channel);
        startForeground(NOTIF_ID, getNotification());

        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void updateNotification() {
        Notification notification = getNotification();
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(NOTIF_ID, notification);
    }

    private Notification getNotification(){
        if (DataManager.getInstance().isAllGood())
            return setNotification("All good!","All tasks are handled on time");
        return setNotification("Wake up!", "DoNotForget doing your tasks!");
    }

    private Notification setNotification(String title, String body){
        return new Notification.Builder(this, CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(R.drawable.ic_launcher_foreground).build();
    }
}
