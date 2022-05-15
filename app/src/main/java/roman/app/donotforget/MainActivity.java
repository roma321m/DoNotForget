package roman.app.donotforget;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText main_textInputEditText;
    private MaterialButton main_BTN_add;

    private RecyclerView main_LST_tasks;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();

        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        main_LST_tasks.setLayoutManager(linearLayoutManager);
        main_LST_tasks.setHasFixedSize(true);
        main_LST_tasks.setItemAnimator(new DefaultItemAnimator());

        ArrayList<Task> temp = new ArrayList<>();
        for (int i=0;i<20;i++){
            temp.add(new Task("test" + i));
        }
        temp.add(new Task("long test aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"));
        setTasks(temp);
    }

    private void setTasks(ArrayList<Task> tasks){
        Adapter_task adapter_task = new Adapter_task(this, tasks);
        main_LST_tasks.setAdapter(adapter_task);
        
        adapter_task.setTaskClickListener(task -> {
            // TODO: 15/05/2022 - clicked done - pop up dialog box.
            Toast.makeText(MainActivity.this, "done clicked - " + task.getDescription(), Toast.LENGTH_SHORT).show(); // temp
        });
    }

    private void findViews() {
        main_textInputEditText = findViewById(R.id.main_textInputEditText);
        main_BTN_add = findViewById(R.id.main_BTN_add);
        main_LST_tasks = findViewById(R.id.main_LST_tasks);
    }
}