package tw.teddysoft.tasks.entities;

import tw.teddysoft.ezddd.core.entity.Entity;

import java.util.List;

public class Project implements Entity<ProjectName> {
    private final ProjectName name;
    private final List<Task> tasks;

    public Project(ProjectName name, List<Task> tasks) {
        this.name = name;
        this.tasks = tasks;
    }

    public ProjectName getName() {
        return name;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    @Override
    public ProjectName getId() {
        return this.name;
    }

    public boolean containTask(TaskId taskId) {
        return this.tasks.stream().anyMatch(task -> task.getId().equals(taskId));
    }

    public void setTaskDone(TaskId taskId, boolean done) {
        this.tasks.stream().filter(task -> task.getId().equals(taskId))
                .findFirst()
                .ifPresent(task -> task.setDone(done));
    }
}