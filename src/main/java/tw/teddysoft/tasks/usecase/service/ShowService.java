package tw.teddysoft.tasks.usecase.service;

import tw.teddysoft.ezddd.core.usecase.UseCaseFailureException;
import tw.teddysoft.tasks.entity.TodoList;
import tw.teddysoft.tasks.entity.TodoListId;
import tw.teddysoft.tasks.usecase.port.TodoListMapper;
import tw.teddysoft.tasks.usecase.port.in.show.ShowInput;
import tw.teddysoft.tasks.usecase.port.in.show.ShowOutput;
import tw.teddysoft.tasks.usecase.port.in.show.ShowUseCase;
import tw.teddysoft.tasks.usecase.port.TodoListDto;
import tw.teddysoft.tasks.usecase.port.out.TodoListRepository;

public class ShowService implements ShowUseCase {

    private TodoListRepository todoListRepository;

    public ShowService(TodoListRepository todoListRepository) {
        this.todoListRepository = todoListRepository;
    }

    @Override
    public ShowOutput execute(ShowInput input) throws UseCaseFailureException {

        TodoList todoList = todoListRepository.findById(TodoListId.of(input.toDoListId))
                .get();

        TodoListDto todoListDto = TodoListMapper.toDto(todoList);

        ShowOutput showOutput = new ShowOutput();
        showOutput.todoListDto = todoListDto;

        return showOutput;
    }
}
