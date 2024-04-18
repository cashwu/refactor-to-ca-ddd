package tw.teddysoft.tasks.adapter.out.presenter;


import tw.teddysoft.tasks.usecase.port.in.task.view.ViewTaskDto;
import tw.teddysoft.tasks.usecase.port.out.todolist.view.ViewTaskPresenter;

import java.io.PrintWriter;
import java.util.List;

public class ViewTaskConsolePresenter implements ViewTaskPresenter {

    private final PrintWriter out;

    public ViewTaskConsolePresenter(PrintWriter out) {
        this.out = out;
    }

    @Override
    public void present(List<ViewTaskDto> viewTaskDtos) {
        for (ViewTaskDto viewTaskDto : viewTaskDtos) {
            out.printf("%s  [%c] %s: %s  %s%n", viewTaskDto.projectName, (viewTaskDto.done? 'x' : ' '), viewTaskDto.taskId, viewTaskDto.description, viewTaskDto.deadline);
        }
        out.println();
        out.flush();
    }
}
