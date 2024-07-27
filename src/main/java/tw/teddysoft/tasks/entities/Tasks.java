package tw.teddysoft.tasks.entities;

import java.util.*;

public class Tasks {
    private Map<ProjectName, List<Task>> tasks = new LinkedHashMap<>();

    public Tasks() {
    }

    public Set<? extends Map.Entry<ProjectName, List<Task>>> entrySet() {
        return tasks.entrySet();
    }

    public void put(ProjectName name, ArrayList<Task> tasks) {
        this.tasks.put(name, tasks);
    }

    public List<Task> get(ProjectName projectName) {
        return this.tasks.get(projectName);
    }
}