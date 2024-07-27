package tw.teddysoft.tasks.entity;

import tw.teddysoft.ezddd.core.entity.Entity;

import java.util.ArrayList;
import java.util.List;

public class Project implements Entity<ProjectName> {

    private ProjectName name;
    private List<Task> tasks;

    public Project() {
        tasks = new ArrayList<>();
    }

    public Project(ProjectName name, List<Task> tasks) {
        this();

        this.name = name;
        this.tasks.addAll(tasks);
    }

    public ProjectName getName() {
        return name;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    @Override
    public ProjectName getId() {
        return name;
    }

    public boolean containTask(TaskId id) {
        return this.tasks.stream().anyMatch(task -> task.getId().equals(id));
    }

    public void setTaskDone(TaskId taskId, boolean done) {
        this.tasks.stream().filter(task -> task.getId().equals(taskId))
                .findFirst()
                .ifPresent(task -> task.setDone(done));
    }
}
