package tw.teddysoft.tasks.usecase.port.out.todolist.show;

import tw.teddysoft.tasks.usecase.port.ToDoListDto;

public interface ShowPresenter {
    void present(ToDoListDto doListDto);
}