package tw.teddysoft.tasks.entity;

import java.util.*;

public class ToDoList {
    private final List<Project> projects;

    public ToDoList() {
        this.projects = new ArrayList<>();
    }
    public List<Project> getProjects() {
        return projects;
    }

    public void addProject(ProjectName name, ArrayList<Task> tasks) {
        this.projects.add(new Project(name, tasks));
    }

    public List<Task> getTasks(ProjectName projectName) {
        return projects.stream().filter(x -> x.getName().equals(projectName))
                .findFirst()
                .map(Project::getTasks)
                .orElse(null);
    }
}

