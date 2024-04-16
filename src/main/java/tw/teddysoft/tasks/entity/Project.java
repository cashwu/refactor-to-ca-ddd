package tw.teddysoft.tasks.entity;

import java.util.*;
import tw.teddysoft.ezddd.core.entity.Entity;
public class Project implements Entity<ProjectName> {
    private ProjectName name;
    private final List<Task> tasks;

    public Project() {
        tasks = new ArrayList<>();
    }

    public Project(ProjectName name, ArrayList<Task> tasks) {
        this();
        this.name = name;
        this.tasks.addAll(tasks);
    }

    public ProjectName getName() {
        return name;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    @Override
    public ProjectName getId() {
        return name;
    }
}
