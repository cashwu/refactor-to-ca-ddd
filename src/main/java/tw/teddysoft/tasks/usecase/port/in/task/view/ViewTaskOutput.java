package tw.teddysoft.tasks.usecase.port.in.task.view;

import tw.teddysoft.ezddd.cqrs.usecase.CqrsOutput;
import tw.teddysoft.tasks.usecase.port.ToDoListDto;

import java.util.List;

public class ViewTaskOutput extends CqrsOutput<ViewTaskOutput> {
    public List<ViewTaskDto> viewTaskDtos;

    public ViewTaskOutput setViewTaskDtos(List<ViewTaskDto> viewTaskDtos) {
        this.viewTaskDtos = viewTaskDtos;
        return this;
    }
}
