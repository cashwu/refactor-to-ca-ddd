package tw.teddysoft.tasks.entities;

import tw.teddysoft.ezddd.core.entity.AggregateRoot;
import tw.teddysoft.ezddd.core.entity.DomainEvent;

import java.util.*;

public class ToDoList extends AggregateRoot<ToDoListId, DomainEvent> {
    private final List<Project> project = new ArrayList<>();

    private final ToDoListId toDoListId;
    private long lastId = 0;

    public ToDoList(ToDoListId toDoListId) {
        this.toDoListId = toDoListId;
    }

    public List<Project> getProject() {
        return project.stream().map(a -> (Project) new ReadOnlyProject(a)).toList();
    }

    public void addProject(ProjectName name, ArrayList<Task> tasks) {
        this.project.add(new Project(name, tasks));
    }

    public List<Task> getTasks(ProjectName projectName) {
        return this.project.stream().filter(a -> a.getName().equals(projectName))
                .findFirst()
                .map(Project::getTasks)
                .orElse(null);
    }

    public TaskId nextId() {
        return TaskId.of(++lastId);
    }

    @Override
    public ToDoListId getId() {
        return this.toDoListId;
    }

    public void setDone(TaskId taskId, boolean done) {

        this.project.stream().filter(a -> a.containTask(taskId))
                .findFirst()
                .ifPresent(a -> a.setTaskDone(taskId, done));
    }
}