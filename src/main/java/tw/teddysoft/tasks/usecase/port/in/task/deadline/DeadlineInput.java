package tw.teddysoft.tasks.usecase.port.in.task.deadline;

import tw.teddysoft.ezddd.core.usecase.Input;

import java.time.LocalDateTime;

public class DeadlineInput implements Input {
    public String toDoListId;
    public String taskId;
    public LocalDateTime deadline;
}
