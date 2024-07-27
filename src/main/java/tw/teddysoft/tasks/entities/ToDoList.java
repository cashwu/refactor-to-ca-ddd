package tw.teddysoft.tasks.entities;

import java.util.*;

public class ToDoList {
    private final List<Project> project = new ArrayList<>();

    private long lastId = 0;

    public ToDoList() {
    }

    public List<Project> getProject() {
        return project;
    }

    public void addProject(ProjectName name, ArrayList<Task> tasks) {
        this.project.add(new Project(name, tasks));
    }

    public List<Task> getTasks(ProjectName projectName) {
        return this.project.stream().filter(a -> a.getName().equals(projectName))
                .findFirst()
                .map(Project::getTasks)
                .orElse(null);

    }

    public TaskId nextId() {
        return TaskId.of(++lastId);
    }
}