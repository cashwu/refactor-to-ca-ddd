package tw.teddysoft.tasks.usecase;

import tw.teddysoft.tasks.entity.Project;
import tw.teddysoft.tasks.entity.ProjectName;
import tw.teddysoft.tasks.entity.Task;
import tw.teddysoft.tasks.entity.TodoList;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Add {
    private String commandLine;
    private TodoList todoList;
    private PrintWriter out;

    public Add(TodoList todoList, PrintWriter out) {
        this.todoList = todoList;
        this.out = out;
    }

    public void add(String commandLine) {
        this.commandLine = commandLine;
        String[] subcommandRest = commandLine.split(" ", 2);
        String subcommand = subcommandRest[0];
        if (subcommand.equals("project")) {
            addProject(ProjectName.of(subcommandRest[1]));
        } else if (subcommand.equals("task")) {
            String[] projectTask = subcommandRest[1].split(" ", 2);
            addTask(ProjectName.of(projectTask[0]), projectTask[1]);
        }
    }

    public void addProject(ProjectName projectName) {
        todoList.addProject(new Project(projectName, new ArrayList<Task>()));
    }

    public void addTask(ProjectName projectName, String description) {
        List<Task> projectTasks = todoList.getTasks(projectName);
        if (projectTasks == null) {
            out.printf("Could not find a project with the name \"%s\".", projectName);
            out.println();
            return;
        }
        projectTasks.add(new Task(todoList.nextId(), description, false));
    }
}