package tw.teddysoft.tasks.entities;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Project {
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
}