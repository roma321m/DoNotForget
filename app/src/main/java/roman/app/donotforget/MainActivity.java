package roman.app.donotforget;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText main_textInputEditText;
    private MaterialButton main_BTN_add;
    private RecyclerView main_LST_tasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();

    }

    private void findViews() {
        main_textInputEditText = findViewById(R.id.main_textInputEditText);
        main_BTN_add = findViewById(R.id.main_BTN_add);
        main_LST_tasks = findViewById(R.id.main_LST_tasks);
    }
}