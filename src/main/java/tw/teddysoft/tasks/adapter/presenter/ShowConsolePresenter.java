package tw.teddysoft.tasks.adapter.presenter;

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
                out.printf("    [%c] %d: %s%n", (task.done? 'x' : ' '), Long.valueOf(task.id), task.description);
            }
            out.println();
        }
    }
}
