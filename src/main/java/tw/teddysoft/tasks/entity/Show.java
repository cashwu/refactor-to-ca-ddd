package tw.teddysoft.tasks.entity;

import java.io.PrintWriter;

public class Show {

    private final ToDoList toDoList;
    private final PrintWriter out;

    public Show(ToDoList toDoList, PrintWriter out) {
        this.toDoList = toDoList;
        this.out = out;
    }

    public void show() {
        for (Project project : toDoList.getProjects()) {
            out.println(project.getName());
            for (Task task : project.getTasks()) {
                out.printf("    [%c] %s: %s%n", (task.isDone() ? 'x' : ' '), task.getId(), task.getDescription());
            }
            out.println();
        }
    }
}
