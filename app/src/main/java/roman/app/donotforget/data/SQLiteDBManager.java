package roman.app.donotforget.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteDBManager extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "DoNotForget_database";
    public static final String TASKS_TABLE_NAME = "tasks";
    public static final String TASKS_COLUMN_ID = "_id";
    public static final String TASKS_COLUMN_DESCRIPTION = "description";
    public static final String TASKS_COLUMN_INITIATION = "initiation";

    private static SQLiteDBManager single_instance = null;
    private Context context;

    private SQLiteDBManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
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
                TASKS_COLUMN_INITIATION + " LONG" + ")");
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
        if (newRowId == -1)
            return false;
        return true;
    }

    public Cursor readFromDB() {
        SQLiteDatabase database = single_instance.getReadableDatabase();
        return database.rawQuery("SELECT * FROM "+TASKS_TABLE_NAME,null);
    }
}
