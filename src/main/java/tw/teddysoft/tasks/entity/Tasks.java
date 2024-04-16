package tw.teddysoft.tasks.entity;

import java.util.*;

public class Tasks {
    private final Map<String, List<Task>> tasks;

    public Tasks() {
        this.tasks = new LinkedHashMap<>();
    }
    public Set<Map.Entry<String, List<Task>>> entrySet() {
        return tasks.entrySet();
    }

    public void put(String name, ArrayList<Task> tasks) {
        this.tasks.put(name, tasks);
    }

    public List<Task> get(String project) {
        return this.tasks.get(project);
    }
}

