package tw.teddysoft.tasks.usecase.port.in.task.today;

import tw.teddysoft.tasks.usecase.port.in.task.view.ViewTaskDto;
import tw.teddysoft.tasks.usecase.port.out.TaskPo;

public class TodayDto extends ViewTaskDto {
    public TodayDto(String projectName, TaskPo task) {
        super(projectName, task);
    }
}
