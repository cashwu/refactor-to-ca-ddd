package tw.teddysoft.tasks.usecase.service;

import tw.teddysoft.ezddd.core.usecase.ExitCode;
import tw.teddysoft.ezddd.cqrs.usecase.CqrsOutput;
import tw.teddysoft.tasks.entity.ProjectName;
import tw.teddysoft.tasks.entity.TaskId;
import tw.teddysoft.tasks.entity.ToDoList;
import tw.teddysoft.tasks.entity.ToDoListId;
import tw.teddysoft.tasks.usecase.port.in.task.add.AddTaskInput;
import tw.teddysoft.tasks.usecase.port.in.task.add.AddTaskUseCase;
import tw.teddysoft.tasks.usecase.port.out.ToDoListRepository;

import static java.lang.String.format;
import static tw.teddysoft.ucontract.Contract.reject;

public class AddTaskService implements AddTaskUseCase {

    private final ToDoListRepository repository;

    public AddTaskService(ToDoListRepository repository) {
        this.repository = repository;
    }

    @Override
    public CqrsOutput execute(AddTaskInput input) {

        ToDoList toDoList = repository.findById(ToDoListId.of(input.toDoListId)).get();
        if (reject("Project does not exist", () -> !toDoList.containsProject(ProjectName.of(input.projectName))))
            return projectNotFoundError(input.projectName);
        if (reject("Duplicated task id", () -> isDuplicatedTaskId(toDoList, input.taskId)))
            return duplicatedTaskIdError(input.taskId);

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

    private CqrsOutput projectNotFoundError(String projectName){
        StringBuilder out = new StringBuilder();
        out.append(format("Could not find a project with the name \"%s\".%n", projectName));
//        out.append("\n");
        return CqrsOutput.create().fail().setMessage(out.toString());
    }

    private boolean isDuplicatedTaskId(ToDoList toDoList, String taskId){
        if (null == taskId) return false;
        return toDoList.containsTask(TaskId.of(taskId));
    }

    private CqrsOutput duplicatedTaskIdError(String taskId){
        StringBuilder out = new StringBuilder();
        out.append(format("Duplicated task id '%s'.%n", taskId));
        return CqrsOutput.create().fail().setMessage(out.toString());
    }
}
