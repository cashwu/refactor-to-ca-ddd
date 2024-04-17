package tw.teddysoft.tasks.usecase.service;

import tw.teddysoft.tasks.entity.ToDoList;
import tw.teddysoft.tasks.entity.ToDoListId;
import tw.teddysoft.tasks.usecase.port.ToDoListMapper;
import tw.teddysoft.tasks.usecase.port.in.todolist.show.ShowInput;
import tw.teddysoft.tasks.usecase.port.in.todolist.show.ShowOutput;
import tw.teddysoft.tasks.usecase.port.in.todolist.show.ShowUseCase;
import tw.teddysoft.tasks.usecase.port.out.ToDoListRepository;

public class ShowService implements ShowUseCase {

    private final ToDoListRepository repository;

    public ShowService(ToDoListRepository repository) {
        this.repository = repository;
    }

    @Override
    public ShowOutput execute(ShowInput input) {
        ToDoList toDoList = repository.findById(ToDoListId.of(input.toDoListId)).get();
        ShowOutput output = new ShowOutput();
        output.toDoListDto = ToDoListMapper.toDto(toDoList);
        return output.succeed().setMessage("");
    }
}
