package tw.teddysoft.tasks.usecase.port.in.task.setdone;

import tw.teddysoft.ezddd.core.usecase.Input;

public class SetDoneInput implements Input {
    public String toDoListId;
    public String taskId;
    public boolean done;
}
