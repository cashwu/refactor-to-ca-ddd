package tw.teddysoft.tasks.entity;

import tw.teddysoft.ezddd.core.entity.AggregateRoot;
import tw.teddysoft.ezddd.core.entity.DomainEvent;

import java.util.*;

public class TodoList extends AggregateRoot<TodoListId, DomainEvent> {

    private final TodoListId todoListId;
    private final List<Project> projects = new ArrayList<>();
    private long lastId = 0;

    public TodoList(TodoListId todoListId) {
        this.todoListId = todoListId;
    }

    public List<Project> getProject() {
        return projects;
    }

    public void addProject(Project project) {
        this.projects.add(project);
    }

    public List<Task> getTasks(ProjectName projectName) {

        return projects.stream().filter(project -> project.getName().equals(projectName))
                .findFirst()
                .map(Project::getTasks)
                .orElse(null);
    }

    public TaskId nextId() {
        return TaskId.of(++lastId);
    }

    @Override
    public TodoListId getId() {
        return todoListId;
    }
}
