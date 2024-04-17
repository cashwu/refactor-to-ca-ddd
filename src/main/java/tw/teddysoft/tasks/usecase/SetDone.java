package tw.teddysoft.tasks.usecase;

import tw.teddysoft.tasks.entity.Project;
import tw.teddysoft.tasks.entity.Task;
import tw.teddysoft.tasks.entity.TaskId;
import tw.teddysoft.tasks.entity.ToDoList;

import java.io.PrintWriter;

public class SetDone {

    private final ToDoList toDoList;
    private final PrintWriter out;

    public SetDone(ToDoList toDoList, PrintWriter out) {
        this.toDoList = toDoList;
        this.out = out;
    }

    public void setDone(String idString, boolean done) {
        TaskId id = TaskId.of(idString);
        for (Project project : toDoList.getProjects()) {
            for (Task task : project.getTasks()) {
                if (task.getId().equals(id)) {
                    toDoList.setDone(task.getId(), done);
                    return;
                }
            }
        }
        out.printf("Could not find a task with an ID of %s.", id);
        out.println();
    }
}
