package tw.teddysoft.tasks.usecase;

import tw.teddysoft.tasks.entity.Project;
import tw.teddysoft.tasks.entity.Task;
import tw.teddysoft.tasks.entity.TodoList;

import java.io.PrintWriter;

public class Show {

    private PrintWriter out;
    private TodoList todoList;

    public Show(TodoList todoList, PrintWriter out) {
        this.out = out;
        this.todoList = todoList;
    }

     public void show() {
        for (Project project : this.todoList.getProject()) {
            this.out.println(project.getName());
            for (Task task : project.getTasks()) {
                out.printf("    [%c] %s: %s%n", (task.isDone() ? 'x' : ' '), task.getId(), task.getDescription());
            }
            out.println();
        }
    }
}