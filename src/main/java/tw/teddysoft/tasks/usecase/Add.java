package tw.teddysoft.tasks.usecase;

import tw.teddysoft.tasks.entity.ProjectName;
import tw.teddysoft.tasks.entity.Task;
import tw.teddysoft.tasks.entity.ToDoList;

import java.io.PrintWriter;
import java.util.List;

public class Add {

    private final ToDoList toDoList;
    private final PrintWriter out;

    public Add(ToDoList toDoList, PrintWriter out) {
        this.toDoList = toDoList;
        this.out = out;
    }

    public void add(String commandLine) {
        String[] subcommandRest = commandLine.split(" ", 2);
        String subcommand = subcommandRest[0];
        if (subcommand.equals("project")) {
            addProject(ProjectName.of(subcommandRest[1]));
        } else if (subcommand.equals("task")) {
            String[] projectTask = subcommandRest[1].split(" ", 2);
            addTask(ProjectName.of(projectTask[0]), projectTask[1]);
        }
    }

    private void addProject(ProjectName projectName) {
        toDoList.addProject(projectName);
    }

    private void addTask(ProjectName projectName, String description) {
        List<Task> projectTasks = toDoList.getTasks(projectName);
        if (projectTasks == null) {
            out.printf("Could not find a project with the name \"%s\".", projectName);
            out.println();
            return;
        }
        toDoList.addTask(projectName, description, false);
    }

}
