package tw.teddysoft.tasks.entity;

import java.util.*;

public class TodoList {

    private long lastId = 0;

    private final List<Project> projects = new ArrayList<>();

    public List<Project> getProjects() {
        return projects;
    }

    public void addProject(ProjectName name, ArrayList<Task> tasks) {

        this.projects.add(new Project(name, tasks));
    }

    public List<Task> getProject(ProjectName projectName) {

        return this.projects.stream().filter(a -> a.getName().equals(projectName))
                .findFirst()
                .map(a -> a.getTasks())
                .orElse(null);
    }

    public TaskId nextId() {
        return TaskId.of(++lastId);
    }
}
