package tw.teddysoft.tasks.usecase.port.in.task.today;

import tw.teddysoft.ezddd.cqrs.usecase.CqrsOutput;
import tw.teddysoft.tasks.usecase.port.ToDoListDto;

public class TodayOutput extends CqrsOutput<TodayOutput> {
    public ToDoListDto toDoListDto;
}
