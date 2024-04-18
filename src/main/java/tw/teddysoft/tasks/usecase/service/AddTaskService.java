package tw.teddysoft.tasks.usecase.service;

import tw.teddysoft.ezddd.core.usecase.ExitCode;
import tw.teddysoft.ezddd.core.usecase.UseCaseFailureException;
import tw.teddysoft.ezddd.cqrs.usecase.CqrsOutput;
import tw.teddysoft.tasks.entity.ProjectName;
import tw.teddysoft.tasks.entity.TaskId;
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
            StringBuilder out = new StringBuilder();
            out.append(format("Could not find a project with the name \"%s\".", input.projectName));
            out.append("\n");
            return CqrsOutput.create().setExitCode(ExitCode.FAILURE).setMessage(out.toString());
        }

        String taskId;
        if (null == input.taskId){
            toDoList.addTask(
                    ProjectName.of(input.projectName),
                    input.description,
                    input.done);
            taskId = String.valueOf(toDoList.getTaskLastId());
        }else {
            toDoList.addTask(
                    ProjectName.of(input.projectName),
                    TaskId.of(input.taskId),
                    input.description,
                    input.done);
            taskId = input.taskId;
        }

        repository.save(toDoList);
        return CqrsOutput.create().setExitCode(ExitCode.SUCCESS).setMessage("").setId(taskId);
    }
}
