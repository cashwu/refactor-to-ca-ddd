package tw.teddysoft.tasks.usecase.port.in.task.view;

import tw.teddysoft.ezddd.core.usecase.Input;

public class ViewTaskInput implements Input {
    public static final String BY_DEADLINE = "deadline";

    public String toDoListId;
    public String orderBy;
}
