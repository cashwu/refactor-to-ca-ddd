package tw.teddysoft.tasks.usecase.port.out.todolist.view;

import tw.teddysoft.tasks.usecase.port.in.task.view.ViewTaskDto;

import java.util.List;

public interface ViewTaskPresenter {
    void present(List<ViewTaskDto> viewTaskDtos);
}