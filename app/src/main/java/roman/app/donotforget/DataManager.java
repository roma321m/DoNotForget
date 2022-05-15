package roman.app.donotforget;

import android.content.Context;

import java.util.ArrayList;

public class DataManager {
    public static int MAX_TIME_IN_HOURS = 5;

    private static DataManager single_instance = null;
    private Context context;

    private ArrayList<Task> tasks;

    private DataManager(Context context) {
        this.context = context;
        tasks = new ArrayList<>();
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
        if (task != null)
            tasks.add(task);
    }

    public int getSize(){
        return tasks.size();
    }
}
