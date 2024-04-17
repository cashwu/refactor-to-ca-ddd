package tw.teddysoft.tasks.usecase.service;

import tw.teddysoft.ezddd.core.usecase.UseCaseFailureException;
import tw.teddysoft.ezddd.cqrs.usecase.CqrsOutput;
import tw.teddysoft.tasks.entity.ProjectName;
import tw.teddysoft.tasks.entity.ToDoList;
import tw.teddysoft.tasks.entity.ToDoListId;
import tw.teddysoft.tasks.usecase.port.in.task.add.AddTaskInput;
import tw.teddysoft.tasks.usecase.port.in.task.add.AddTaskUseCase;
import tw.teddysoft.tasks.usecase.port.out.ToDoListRepository;

import static java.lang.String.format;

public class AddTaskService implements AddTaskUseCase {

    private final ToDoListRepository repository;

    public AddTaskService(ToDoListRepository repository) {
        this.repository = repository;
    }

    @Override
    public CqrsOutput execute(AddTaskInput input) throws UseCaseFailureException {
        ToDoList toDoList = repository.findById(ToDoListId.of(input.toDoListId)).get();
        if (toDoList.getProject(ProjectName.of(input.projectName)).isEmpty()){
            StringBuffer sb = new StringBuffer();
            sb.append(format("Could not find a project with the name \"%s\".", input.projectName));
            sb.append("\n");
            return CqrsOutput.create().fail().setMessage(sb.toString());
        }

        toDoList.addTask(
                ProjectName.of(input.projectName),
                input.description,
                input.done);
        repository.save(toDoList);
        return CqrsOutput.create().succeed().setId(toDoList.getId().value());
    }
}
