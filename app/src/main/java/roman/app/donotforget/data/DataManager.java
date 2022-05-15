package roman.app.donotforget.data;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;

import roman.app.donotforget.activities.MainActivity;

public class DataManager {
    public static int MAX_TIME_IN_HOURS = 5;

    private static DataManager single_instance = null;
    private Context context;
    private SQLiteDBManager sqLiteDBManager;

    private ArrayList<Task> tasks;

    private DataManager(Context context) {
        this.context = context;
        tasks = new ArrayList<>();
        sqLiteDBManager = SQLiteDBManager.getInstance();
        setDataFromSQLite();
    }

    public static DataManager initHelper(Context context) {
        if (single_instance == null) {
            single_instance = new DataManager(context);
        }
        return single_instance;
    }

    public static DataManager getInstance() {
        return single_instance;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void addTask(Task task) {
        if (task != null){
            tasks.add(task);
            boolean res = sqLiteDBManager.saveToDB(task);
            if (res)
                Log.e(MainActivity.TAG_SQL, "add new task: " + task.toString());
        }
    }

    public void removeTask(Task task) {
        tasks.remove(task);
        boolean t = sqLiteDBManager.deleteFromDB(task);
        if (t)
            Log.e(MainActivity.TAG_SQL, "task deleted " + task.toString());
        else
            Log.e(MainActivity.TAG_SQL, "error while deleting a task " + task.toString());
    }

    private void setDataFromSQLite() {
        ArrayList<Task> t = sqLiteDBManager.readFromDB();
        if (t.isEmpty())
            return;
        tasks = t;
    }
}
