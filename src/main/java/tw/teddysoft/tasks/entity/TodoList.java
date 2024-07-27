package tw.teddysoft.tasks.entity;

import java.util.*;

public class TodoList {

    private final List<Project> projects = new ArrayList<>();

    public List<Project> getProject() {
        return projects;
    }

    public void addProject(Project project) {
        this.projects.add(project);
    }

    public List<Task> getTasks(ProjectName projectName) {

        return projects.stream().filter(project -> project.getName().equals(projectName))
                .findFirst()
                .map(Project::getTasks)
                .orElse(null);
    }
}
