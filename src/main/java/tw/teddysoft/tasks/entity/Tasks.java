package tw.teddysoft.tasks.entity;

import java.util.*;

public class Tasks {
    private final List<Project> projects;

    public Tasks() {
        this.projects = new ArrayList<>();
    }
    public List<Project>  entrySet() {
        return projects;
    }

    public void put(ProjectName name, ArrayList<Task> tasks) {
        this.projects.add(new Project(name, tasks));
    }

    public List<Task> get(ProjectName projectName) {
        return projects.stream().filter(x -> x.getName().equals(projectName))
                .findFirst()
                .map(Project::getTasks)
                .orElse(null);
    }
}

