package tw.teddysoft.tasks.usecase.port.in.show;

import tw.teddysoft.ezddd.cqrs.usecase.CqrsOutput;
import tw.teddysoft.tasks.usecase.port.TodoListDto;

public class ShowOutput extends CqrsOutput<ShowOutput> {

    public TodoListDto todoListDto;
}
