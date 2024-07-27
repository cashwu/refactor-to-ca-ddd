package tw.teddysoft.tasks.entities;

import tw.teddysoft.ezddd.core.entity.Entity;

import java.util.ArrayList;

public class Project implements Entity<ProjectName> {
    private final ProjectName name;
    private final ArrayList<Task> tasks;

    public Project(ProjectName name, ArrayList<Task> tasks) {
        this.name = name;
        this.tasks = tasks;
    }

    public ProjectName getName() {
        return name;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    @Override
    public ProjectName getId() {
        return this.name;
    }
}