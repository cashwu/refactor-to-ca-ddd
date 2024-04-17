package tw.teddysoft.tasks.entity;

import tw.teddysoft.ezddd.core.entity.AggregateRoot;
import tw.teddysoft.ezddd.core.entity.DomainEvent;

import java.util.*;

public class ToDoList extends AggregateRoot<ToDoListId, DomainEvent> {

    private final ToDoListId id;
    private final List<Project> projects;
    private long lastTaskId;

    public ToDoList(ToDoListId id) {
        this(id, 0);
    }

    public ToDoList(ToDoListId id, long lastTaskId) {
        this.id = id;
        this.lastTaskId = lastTaskId;
        this.projects = new ArrayList<>();
    }

    public ToDoList(ToDoListId id, long lastTaskId, List<Project> projects) {
        this(id, lastTaskId);
        this.projects.addAll(projects);
    }

    public List<Project> getProjects() {
        return projects.stream().map(project -> (Project) new ReadOnlyProject(project)).toList();
    }

    public void addProject(ProjectName name) {
        if (projects.stream().anyMatch(project -> project.getName().equals(name))) return;
        this.projects.add(new Project(name));
    }

    public List<Task> getTasks(ProjectName projectName) {
        return projects.stream()
                .filter(x -> x.getName().equals(projectName))
                .findFirst()
                .map(project -> project.getTasks().stream()
                        .map(t -> (Task) new ReadOnlyTask(t))
                        .toList())
                .orElse(null);
    }

    public void addTask(ProjectName name, String description, boolean done) {
        Optional<Project> project = getProject(name);
        project.ifPresent(p -> p.addTask(new Task(TaskId.of(nextTaskId()), description, done)));
    }

    public Optional<Project> getProject(ProjectName projectName) {
        return projects.stream().filter(p -> p.getName().equals(projectName)).findFirst();
    }


    @Override
    public ToDoListId getId() {
        return id;
    }

    public void setDone(TaskId taskId, boolean done) {
        projects.stream()
                .filter(p -> p.containTask(taskId))
                .findFirst()
                .ifPresent(p -> p.setTaskDone(taskId, done));
    }

    public boolean containTask(TaskId taskId) {
        return projects.stream().anyMatch(p-> p.containTask(taskId));
    }

    public long getTaskLastId(){
        return lastTaskId;
    }

    private long nextTaskId() {
        return ++lastTaskId;
    }
}

