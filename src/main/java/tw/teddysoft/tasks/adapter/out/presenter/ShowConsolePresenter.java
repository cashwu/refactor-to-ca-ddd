package tw.teddysoft.tasks.adapter.out.presenter;

import tw.teddysoft.tasks.usecase.port.ProjectDto;
import tw.teddysoft.tasks.usecase.port.TaskDto;
import tw.teddysoft.tasks.usecase.port.ToDoListDto;
import tw.teddysoft.tasks.usecase.port.out.todolist.show.ShowPresenter;

import java.io.PrintWriter;

public class ShowConsolePresenter implements ShowPresenter {

    private final PrintWriter out;

    public ShowConsolePresenter(PrintWriter out) {
        this.out = out;
    }

    @Override
    public void present(ToDoListDto doListDto) {
        for (ProjectDto project : doListDto.projectDots) {
            out.println(project.name);
            for (TaskDto task : project.taskDtos) {
                if (null != task.deadline)
                    out.printf("    [%c] %s: %s  %s%n", (task.done? 'x' : ' '), task.id, task.description, task.deadline.toLocalDate().toString());
                else
                    out.printf("    [%c] %s: %s%n", (task.done? 'x' : ' '), task.id, task.description);
            }
            out.printf("%n");
            out.flush();
        }
    }
}