package tw.teddysoft.tasks.adapter.in.controller.console;

import tw.teddysoft.ezddd.cqrs.usecase.CqrsOutput;
import tw.teddysoft.tasks.io.standard.ToDoListApp;
import tw.teddysoft.tasks.usecase.port.in.project.add.AddProjectInput;
import tw.teddysoft.tasks.usecase.port.in.project.add.AddProjectUseCase;
import tw.teddysoft.tasks.usecase.port.in.task.add.AddTaskInput;
import tw.teddysoft.tasks.usecase.port.in.task.add.AddTaskUseCase;

import java.util.List;

import static java.lang.String.format;
import static tw.teddysoft.ucontract.Contract.require;

public class AddConsoleController implements ConsoleController {
    private final AddProjectUseCase addProjectUseCase;
    private final AddTaskUseCase addTaskUseCase;

    public AddConsoleController(AddProjectUseCase addProjectUseCase, AddTaskUseCase addTaskUseCase) {
        this.addProjectUseCase = addProjectUseCase;
        this.addTaskUseCase = addTaskUseCase;
    }

    @Override
    public Response<CqrsOutput> execute(Request request) {
        require(format("%s", validateRequest(request)), () -> "".equals(validateRequest(request)));

        return switch (request.getArg(0)){
            case "project" : yield Response.of(addProject(request.getArg(1)));
            case "task" : yield Response.of(addTask(request.getArg(1), getTaskDescription(2, request.getArgs())));
            case "task2": yield Response.of(addTask2(request.getArg(1), request.getArg(2), getTaskDescription(3, request.getArgs())));
            default: throw  new IllegalStateException(format("Invalid add '%s'", request.getArg(0)));
        };
    }


    private String validateRequest(Request request){
        return switch (request.getArg(0)){
            case "project" :
                if (request.getArgs().size() < 2) yield "'project name' argument";
                yield "";
            case "task" :
                if (request.getArgs().size() < 3) yield "'project name' and 'task description' arguments";
                yield "";
            case "task2":
                if (request.getArgs().size() < 4) yield "'project name', 'task id', and 'task description' arguments";
                yield "";
            default: yield "";
        };
    }

    private String getTaskDescription(int start, List<String> args) {
        StringBuilder sb = new StringBuilder();
        for (int i = start; i < args.size(); i++) {
            sb.append(args.get(i)).append(" ");
        }
        return sb.toString().trim();
    }


    public CqrsOutput addProject(String projectName) {
        AddProjectInput addProjectInput = new AddProjectInput();
        addProjectInput.toDoListId = ToDoListApp.DEFAULT_TO_DO_LIST_ID;
        addProjectInput.projectName = projectName;
        return addProjectUseCase.execute(addProjectInput);
    }
    public CqrsOutput addTask(String projectName, String description) {
        AddTaskInput addTaskInput = new AddTaskInput();
        addTaskInput.toDoListId = ToDoListApp.DEFAULT_TO_DO_LIST_ID;
        addTaskInput.projectName = projectName;
        addTaskInput.description = description;
        addTaskInput.done = false;
        return addTaskUseCase.execute(addTaskInput);
    }
    public CqrsOutput addTask2(String projectName, String taskId, String description){
        AddTaskInput addTaskInput = new AddTaskInput();
        addTaskInput.toDoListId = ToDoListApp.DEFAULT_TO_DO_LIST_ID;
        addTaskInput.projectName = projectName;
        addTaskInput.taskId = taskId;
        addTaskInput.description = description;
        addTaskInput.done = false;
        return addTaskUseCase.execute(addTaskInput);
    }
}
