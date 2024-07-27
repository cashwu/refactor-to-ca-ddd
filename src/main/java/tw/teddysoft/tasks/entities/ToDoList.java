package tw.teddysoft.tasks.entities;

import java.util.*;

public class ToDoList {
    private final List<Project> project = new ArrayList<>();

    public ToDoList() {
    }

    public List<Project> entrySet() {
        return project;
    }

    public void put(ProjectName name, ArrayList<Task> tasks) {
        this.project.add(new Project(name, tasks));
    }

    public List<Task> get(ProjectName projectName) {
        return this.project.stream().filter(a -> a.getName().equals(projectName))
                .findFirst()
                .map(Project::getTasks)
                .orElse(null);

    }
}