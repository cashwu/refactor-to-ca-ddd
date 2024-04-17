package tw.teddysoft.tasks.usecase;

import tw.teddysoft.tasks.entity.ToDoList;
import tw.teddysoft.tasks.usecase.port.out.ToDoListRepository;
import tw.teddysoft.tasks.usecase.port.in.project.add.AddProjectInput;
import tw.teddysoft.tasks.usecase.port.in.project.add.AddProjectUseCase;
import tw.teddysoft.tasks.TaskList;
import tw.teddysoft.tasks.usecase.service.AddProjectService;
import tw.teddysoft.tasks.usecase.port.in.task.add.AddTaskUseCase;
import tw.teddysoft.tasks.usecase.port.in.task.add.AddTaskInput;
import tw.teddysoft.tasks.usecase.service.AddTaskService;

import java.io.PrintWriter;

public class Execute {

    private final ToDoList toDoList;
    private final PrintWriter out;
    private final ToDoListRepository repository;

    public Execute(ToDoList toDoList, PrintWriter out, ToDoListRepository repository) {
        this.toDoList = toDoList;
        this.out = out;
        this.repository = repository;
    }

    public void execute(String commandLine) {
        String[] commandRest = commandLine.split(" ", 2);
        String command = commandRest[0];
        switch (command) {
            case "show":
                new Show(toDoList, out).show();
                break;
            case "add":
                add(commandRest[1]);
                break;
            case "check":
                check(commandRest[1]);
                break;
            case "uncheck":
                uncheck(commandRest[1]);
                break;
            case "help":
                new Help(out).help();
                break;
            default:
                new Error(out).error(command);
                break;
        }
    }

    private void add(String commandLine) {
        String[] subcommandRest = commandLine.split(" ", 2);
        String subcommand = subcommandRest[0];
        if (subcommand.equals("project")) {
            AddProjectUseCase addProjectUseCase = new AddProjectService(repository);
            AddProjectInput addProjectInput = new AddProjectInput();
            addProjectInput.toDoListId = TaskList.DEFAULT_TO_DO_LIST_ID;
            addProjectInput.projectName = subcommandRest[1];
            addProjectUseCase.execute(addProjectInput);
        } else if (subcommand.equals("task")) {
            String[] projectTask = subcommandRest[1].split(" ", 2);
            AddTaskUseCase addTaskUseCase = new AddTaskService(repository);
            AddTaskInput addTaskInput = new AddTaskInput();
            addTaskInput.toDoListId = TaskList.DEFAULT_TO_DO_LIST_ID;
            addTaskInput.projectName = projectTask[0];
            addTaskInput.description = projectTask[1];
            addTaskInput.done = false;
            out.print(addTaskUseCase.execute(addTaskInput).getMessage());
        }
    }

    private void check(String idString) {
        new SetDone(toDoList, out).setDone(idString, true);
    }

    private void uncheck(String idString) {
        new SetDone(toDoList, out).setDone(idString, false);
    }
}
