package tw.teddysoft.tasks.entity;

import tw.teddysoft.ezddd.core.entity.AggregateRoot;
import tw.teddysoft.ezddd.core.entity.DomainEvent;

import java.util.*;

public class ToDoList extends AggregateRoot<ToDoListId, DomainEvent> {

    private final ToDoListId id;
    private final List<Project> projects;

    public ToDoList(ToDoListId id) {
        this.id = id;
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

    @Override
    public ToDoListId getId() {
        return id;
    }
}

