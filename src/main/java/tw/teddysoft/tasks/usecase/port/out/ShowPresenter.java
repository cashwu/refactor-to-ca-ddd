package tw.teddysoft.tasks.usecase.port.out;

import tw.teddysoft.tasks.usecase.port.TodoListDto;

public interface ShowPresenter {

    void present(TodoListDto todoListDto);
}
