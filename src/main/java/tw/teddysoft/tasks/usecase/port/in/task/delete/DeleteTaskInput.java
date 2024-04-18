package tw.teddysoft.tasks.usecase.port.in.task.delete;

import tw.teddysoft.ezddd.core.usecase.Input;

public class DeleteTaskInput implements Input {
    public String toDoListId;
    public String taskId;
}
