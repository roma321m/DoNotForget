package roman.app.donotforget;

import android.app.Application;

import roman.app.donotforget.data.DataManager;
import roman.app.donotforget.data.SQLiteDBManager;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // initHelpers here
        SQLiteDBManager.initHelper(this);
        DataManager.initHelper(this);
    }
}