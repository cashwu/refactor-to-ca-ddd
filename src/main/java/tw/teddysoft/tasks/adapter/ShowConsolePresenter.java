package tw.teddysoft.tasks.adapter;

import tw.teddysoft.tasks.usecase.port.ProjectDto;
import tw.teddysoft.tasks.usecase.port.TaskDto;
import tw.teddysoft.tasks.usecase.port.TodoListDto;
import tw.teddysoft.tasks.usecase.port.out.ShowPresenter;

import java.io.PrintWriter;

public class ShowConsolePresenter implements ShowPresenter {

    private PrintWriter out;

    public ShowConsolePresenter(PrintWriter out) {
        this.out = out;
    }

    @Override
    public void present(TodoListDto todoListDto) {

        for (ProjectDto projectDto : todoListDto.projects) {
            this.out.println(projectDto.name);
            for (TaskDto taskDto : projectDto.tasks) {
                out.printf("    [%c] %s: %s%n", (taskDto.done ? 'x' : ' '), taskDto.id, taskDto.description);
            }
            out.println();
        }
    }
}
