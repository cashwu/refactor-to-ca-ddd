package tw.teddysoft.tasks.usecase;

import tw.teddysoft.tasks.entity.Project;
import tw.teddysoft.tasks.entity.Task;
import tw.teddysoft.tasks.entity.TaskId;
import tw.teddysoft.tasks.entity.TodoList;

import java.io.PrintWriter;

public class SetDone {
    private TodoList todoList;
    private PrintWriter out;

    public SetDone(TodoList todoList, PrintWriter out) {
        this.todoList = todoList;
        this.out = out;
    }

    public void setDone(String idString, boolean done) {
//        int value = Integer.parseInt(idString);
        TaskId id = TaskId.of(idString);
        for (Project project : todoList.getProject()) {
            for (Task task : project.getTasks()) {
                if (task.getId().equals(id)) {
                    //task.setDone(done);
                    todoList.setDone(task.getId(), done);
                    return;
                }
            }
        }
        out.printf("Could not find a task with an ID of %s.", id);
        out.println();
    }
}