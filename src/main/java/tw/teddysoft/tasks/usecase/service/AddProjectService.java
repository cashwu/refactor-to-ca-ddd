package tw.teddysoft.tasks.usecase.service;

import tw.teddysoft.ezddd.core.usecase.UseCaseFailureException;
import tw.teddysoft.ezddd.cqrs.usecase.CqrsOutput;
import tw.teddysoft.tasks.entity.ProjectName;
import tw.teddysoft.tasks.entity.ToDoList;
import tw.teddysoft.tasks.entity.ToDoListId;
import tw.teddysoft.tasks.usecase.port.in.project.add.AddProjectInput;
import tw.teddysoft.tasks.usecase.port.in.project.add.AddProjectUseCase;
import tw.teddysoft.tasks.usecase.port.out.ToDoListRepository;

public class AddProjectService implements AddProjectUseCase {

    private final ToDoListRepository repository;

    public AddProjectService(ToDoListRepository repository) {
        this.repository = repository;
    }

    @Override
    public CqrsOutput execute(AddProjectInput input) throws UseCaseFailureException {
        ToDoList toDoList = repository.findById(ToDoListId.of(input.toDoListId)).get();
        toDoList.addProject(ProjectName.of(input.projectName));
        repository.save(toDoList);

        return CqrsOutput.create().succeed().setId(input.toDoListId);
    }
}
