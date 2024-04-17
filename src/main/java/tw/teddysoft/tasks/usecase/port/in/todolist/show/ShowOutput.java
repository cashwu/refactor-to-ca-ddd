package tw.teddysoft.tasks.usecase.port.in.todolist.show;

import tw.teddysoft.ezddd.cqrs.usecase.CqrsOutput;
import tw.teddysoft.tasks.usecase.port.ToDoListDto;

public class ShowOutput extends CqrsOutput<ShowOutput> {
    public ToDoListDto toDoListDto;
}
