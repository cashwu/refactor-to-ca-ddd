package tw.teddysoft.tasks.entity;

import tw.teddysoft.ezddd.core.entity.Entity;

import java.time.LocalDateTime;

public class Task implements Entity<TaskId> {
    private final TaskId id;
    private final String description;
    private boolean done;
    private LocalDateTime deadline;

    public Task(TaskId id, String description, boolean done) {
        this(id, description, done, null);
    }

    public Task(TaskId id, String description, boolean done, LocalDateTime deadline) {
        this.id = id;
        this.description = description;
        this.done = done;
        this.deadline = deadline;
    }

    public TaskId getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }
}
