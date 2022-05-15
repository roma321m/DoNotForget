package roman.app.donotforget.activities;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

import roman.app.donotforget.adapters.Adapter_task;
import roman.app.donotforget.services.ForegroundServiceManager;
import roman.app.donotforget.R;
import roman.app.donotforget.validators.Validator;
import roman.app.donotforget.data.DataManager;
import roman.app.donotforget.data.Task;

public class MainActivity extends AppCompatActivity {

    public static final String TAG_SQL= "sql";
    public static final String TAG_SERVICE = "service";

    private TextInputLayout main_textInputLayout;
    private TextInputEditText main_textInputEditText;
    private MaterialButton main_BTN_add;

    private RecyclerView main_LST_tasks;
    private LinearLayoutManager linearLayoutManager;

    private Validator validator;

    private DataManager dataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!serviceRunning(ForegroundServiceManager.class)) {
            Intent serviceIntent = new Intent(this, ForegroundServiceManager.class);
            startForegroundService(serviceIntent);
        }

        dataManager = DataManager.getInstance();

        findViews();
        setTasks(dataManager.getTasks());

        validator = Validator.Builder.make(main_textInputLayout)
                .addWatcher(new Validator.Watcher_NotEmpty("Can't be empty"))
                .build();

        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        main_LST_tasks.setLayoutManager(linearLayoutManager);
        main_LST_tasks.setHasFixedSize(true);
        main_LST_tasks.setItemAnimator(new DefaultItemAnimator());


        main_BTN_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validator.validateIt()) {
                    addNewTask();
                } else {
                    main_textInputEditText.setText("");
                }
            }
        });
    }

    private void addNewTask() {
        dataManager.addTask(new Task(main_textInputEditText.getText() + ""));
        setTasks(dataManager.getTasks());
        main_textInputEditText.clearFocus();
        main_textInputEditText.getText().clear();
        main_textInputLayout.setError("");
    }

    private void setTasks(ArrayList<Task> tasks) {
        Adapter_task adapter_task = new Adapter_task(this, tasks);
        main_LST_tasks.setAdapter(adapter_task);

        adapter_task.setTaskClickListener(task -> {
            // TODO: 15/05/2022 - clicked done - pop up dialog box.
            Toast.makeText(MainActivity.this, "done clicked - " + task.getDescription(), Toast.LENGTH_SHORT).show(); // temp
        });
    }

    public boolean serviceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    private void findViews() {
        main_textInputLayout = findViewById(R.id.main_textInputLayout);
        main_textInputEditText = findViewById(R.id.main_textInputEditText);
        main_BTN_add = findViewById(R.id.main_BTN_add);
        main_LST_tasks = findViewById(R.id.main_LST_tasks);
    }
}