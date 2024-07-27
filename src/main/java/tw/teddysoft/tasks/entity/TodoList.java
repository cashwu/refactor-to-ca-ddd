package tw.teddysoft.tasks.entity;

import tw.teddysoft.ezddd.core.entity.AggregateRoot;
import tw.teddysoft.ezddd.core.entity.DomainEvent;

import java.util.*;

public class TodoList extends AggregateRoot<TodoListId, DomainEvent> {

    private TodoListId todoListId;
    private long lastId = 0;

    public TodoList(TodoListId todoListId) {
        this.todoListId = todoListId;
    }

    private final List<Project> projects = new ArrayList<>();

    public List<Project> getProjects() {
        return projects.stream().map(a -> (Project)new ReadOnlyProject(a))
                .toList();
    }

    public void addProject(ProjectName name, ArrayList<Task> tasks) {

        this.projects.add(new Project(name, tasks));
    }

    public List<Task> getProject(ProjectName projectName) {

        return this.projects.stream().filter(a -> a.getName().equals(projectName))
                .findFirst()
                .map(a -> a.getTasks())
                .orElse(null);
    }

    public TaskId nextId() {
        return TaskId.of(++lastId);
    }

    @Override
    public TodoListId getId() {
        return todoListId;
    }

    public void setDone(TaskId id, boolean done) {
        this.projects.stream().filter(a -> a.containTask(id))
                .findFirst()
                .ifPresent(a -> a.setTaskDone(id, done));
    }
}
