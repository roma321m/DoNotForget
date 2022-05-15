package roman.app.donotforget;

import android.app.Application;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // initHelpers here
        DataManager.initHelper(this);
    }
}