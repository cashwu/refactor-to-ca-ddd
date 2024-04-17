package tw.teddysoft.tasks.usecase.port.in.task.today;

import tw.teddysoft.ezddd.core.usecase.Input;

import java.time.LocalDateTime;

public class TodayInput implements Input {
    public String toDoListId;
    public LocalDateTime today;
}
