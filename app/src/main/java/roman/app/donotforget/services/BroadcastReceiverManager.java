package roman.app.donotforget.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import roman.app.donotforget.data.DataManager;
import roman.app.donotforget.data.SQLiteDBManager;

public class BroadcastReceiverManager extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)){
            SQLiteDBManager.initHelper(context);
            DataManager.initHelper(context);
            Intent serviceIntent = new Intent(context, ForegroundServiceManager.class);
            context.startForegroundService(serviceIntent);
        }
    }
}
