package roman.app.donotforget.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class SQLiteDBManager extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "DoNotForget_database";
    public static final String TASKS_TABLE_NAME = "tasks";
    public static final String TASKS_COLUMN_ID = "_id";
    public static final String TASKS_COLUMN_DESCRIPTION = "description";
    public static final String TASKS_COLUMN_INITIATION = "initiation";

    private static SQLiteDBManager single_instance = null;

    private SQLiteDBManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static SQLiteDBManager initHelper(Context context) {
        if (single_instance == null) {
            single_instance = new SQLiteDBManager(context);
        }
        return single_instance;
    }

    public static SQLiteDBManager getInstance() {
        return single_instance;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TASKS_TABLE_NAME + " (" +
                TASKS_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TASKS_COLUMN_DESCRIPTION + " TEXT, " +
                TASKS_COLUMN_INITIATION + " INTEGER" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TASKS_TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public boolean saveToDB(Task task) {
        if (task == null)
            return false;
        SQLiteDatabase database = single_instance.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TASKS_COLUMN_DESCRIPTION, task.getDescription());
        values.put(TASKS_COLUMN_INITIATION, task.getInitiationTime());
        long newRowId = database.insert(TASKS_TABLE_NAME, null, values);
        if (newRowId == -1) {
            database.close();
            return false;
        }
        database.close();
        return true;
    }

    public boolean deleteFromDB(Task task) {
        SQLiteDatabase database = single_instance.getWritableDatabase();
        int row = database.delete(TASKS_TABLE_NAME, TASKS_COLUMN_INITIATION + "=" + task.getInitiationTime(), null);
        if (row == -1) {
            database.close();
            return false;
        }
        database.close();
        return true;
    }

    public ArrayList<Task> readFromDB() {
        SQLiteDatabase database = single_instance.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + TASKS_TABLE_NAME, null);
        if (cursor.getCount() == 0) {
            database.close();
            return new ArrayList<>();
        }
        ArrayList<Task> tasks = new ArrayList<>();
        while (cursor.moveToNext()) {
            tasks.add(new Task(cursor.getString(1), cursor.getLong(2)));
        }
        database.close();
        return tasks;
    }
}
