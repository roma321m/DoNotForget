package roman.app.donotforget;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class Adapter_task extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public interface TaskClickListener {
        void doneClicked(Task task);
    }

    private Activity activity;
    private ArrayList<Task> list;
    private TaskClickListener taskClickListener;

    public Adapter_task(Activity activity, ArrayList<Task> list) {
        this.activity = activity;
        this.list = list;
    }

    public Adapter_task setTaskClickListener(TaskClickListener taskClickListener) {
        this.taskClickListener = taskClickListener;
        return this;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder
    onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.task_item, viewGroup, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        myViewHolder viewHolder = (myViewHolder) holder;

        Task task = getItem(position);
        viewHolder.task_LBL_text.setText(task.getDescription());
    }

    @Override
    public int getItemCount() {
        if (list == null) return 0;
        return list.size();
    }

    private Task getItem(int position) {
        return list.get(position);
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        public MaterialTextView task_LBL_text;
        private MaterialButton task_BTN_done;

        public myViewHolder(final View itemView) {
            super(itemView);

            findView(itemView);

            task_BTN_done.setOnClickListener(view -> {
                if (taskClickListener != null) {
                    taskClickListener.doneClicked(getItem(getAdapterPosition()));
                }
            });
        }

        private void findView(View itemView) {
            this.task_LBL_text = itemView.findViewById(R.id.task_LBL_text);
            this.task_BTN_done = itemView.findViewById(R.id.task_BTN_done);
        }
    }
}

