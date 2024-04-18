package tw.teddysoft.tasks.usecase.port.in.task.view;

import tw.teddysoft.tasks.usecase.port.out.TaskPo;

import java.time.LocalDateTime;

public class ViewTaskDto {
    public String projectName;
    public String taskId;
    public String description;
    public boolean done;
    public LocalDateTime deadline;

    public ViewTaskDto(String projectName, TaskPo task) {
        this.projectName = projectName;
        this.taskId = task.getId();
        this.description = task.getDescription();
        this.done = task.getDone();
        this.deadline = task.getDeadline();
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }
}
