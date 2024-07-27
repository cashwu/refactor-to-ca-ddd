package tw.teddysoft.tasks.entity;

import java.util.*;

public class Tasks {

    private final Map<ProjectName, List<Task>> tasks = new LinkedHashMap<>();

    public Set<? extends Map.Entry<ProjectName, List<Task>>> entrySet() {
        return tasks.entrySet();
    }

    public void put(ProjectName name, ArrayList<Task> objects) {

        this.tasks.put(name, objects);
    }

    public List<Task> get(ProjectName project) {
        return this.tasks.get(project);
    }
}
