package tw.teddysoft.tasks.entity;

import java.util.*;

public class Tasks {

//    private final Map<ProjectName, List<Task>> tasks = new LinkedHashMap<>();
    private final List<Project> tasks = new ArrayList<>();

//    public Set<? extends Map.Entry<ProjectName, List<Task>>> entrySet() {
//        return tasks.entrySet();
//    }
    public List<Project> entrySet() {
        return tasks;
    }

    public void put(ProjectName name, ArrayList<Task> objects) {

//        this.tasks.put(name, objects);

        this.tasks.add(new Project(name, objects));
    }

    public List<Task> get(ProjectName project) {

        return this.tasks.stream().filter(a -> a.getName().equals(project))
                .findFirst()
                .map(a -> a.getTasks())
                .orElse(null);
    }
}
