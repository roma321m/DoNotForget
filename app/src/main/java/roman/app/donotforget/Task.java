package roman.app.donotforget;

import androidx.annotation.NonNull;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class Task {
    private String description;
    private Date initiationTime;

    public Task() {
    }

    public Task(String description) {
        initiationTime = Calendar.getInstance().getTime();
        if (description == null || description.isEmpty()) {
            this.description = "Default";
            return;
        }
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public Task setDescription(String description) {
        this.description = description;
        return this;
    }

    public Date getInitiationTime() {
        return initiationTime;
    }

    public Task setInitiationTime(Date initiationTime) {
        this.initiationTime = initiationTime;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return getInitiationTime().equals(task.getInitiationTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getInitiationTime());
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
