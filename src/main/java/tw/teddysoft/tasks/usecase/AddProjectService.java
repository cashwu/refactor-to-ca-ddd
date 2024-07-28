package tw.teddysoft.tasks.usecase;

import tw.teddysoft.ezddd.core.usecase.UseCaseFailureException;
import tw.teddysoft.ezddd.cqrs.usecase.CqrsOutput;
import tw.teddysoft.tasks.entity.*;

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
