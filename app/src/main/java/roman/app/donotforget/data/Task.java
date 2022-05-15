package roman.app.donotforget.data;

import androidx.annotation.NonNull;

import java.util.Calendar;

public class Task {
    private String description;
    private long initiationTime;

    public Task() {
    }

    public Task(String description) {
        initiationTime = Calendar.getInstance().getTimeInMillis();
        if (description == null || description.isEmpty()) {
            this.description = "Default";
            return;
        }
        this.description = description;
    }

    public Task(String description, long initiationTime) {
        this.initiationTime = initiationTime;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public Task setDescription(String description) {
        this.description = description;
        return this;
    }

    public long getInitiationTime() {
        return initiationTime;
    }

    public Task setInitiationTime(long initiationTime) {
        this.initiationTime = initiationTime;
        return this;
    }

    @NonNull
    @Override
    public String toString() {
        return "Task{" +
                "description='" + description + '\'' +
                ", initiationTime=" + initiationTime +
                '}';
    }
}
