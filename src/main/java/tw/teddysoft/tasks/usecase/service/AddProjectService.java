package tw.teddysoft.tasks.usecase.service;

import tw.teddysoft.ezddd.core.usecase.UseCaseFailureException;
import tw.teddysoft.ezddd.cqrs.usecase.CqrsOutput;
import tw.teddysoft.tasks.entity.*;
import tw.teddysoft.tasks.usecase.port.out.TodoListRepository;
import tw.teddysoft.tasks.usecase.port.in.addproject.AddProjectInput;
import tw.teddysoft.tasks.usecase.port.in.addproject.AddProjectUseCase;

import java.util.ArrayList;

public class AddProjectService implements AddProjectUseCase {

    private final TodoListRepository repository;

    public AddProjectService(TodoListRepository repository) {
        this.repository = repository;
    }

    @Override
    public CqrsOutput execute(AddProjectInput input) throws UseCaseFailureException {

        TodoList todoList = repository.findById(TodoListId.of(input.toDoListId)).get();
        todoList.addProject(new Project(ProjectName.of(input.projectName), new ArrayList<Task>()));

        repository.save(todoList);

        return CqrsOutput.create().setId(todoList.getId().value()).succeed();
    }
}
