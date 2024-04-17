package tw.teddysoft.tasks.usecase;

import tw.teddysoft.tasks.adapter.presenter.HelpConsolePresenter;
import tw.teddysoft.tasks.adapter.presenter.ShowConsolePresenter;
import tw.teddysoft.tasks.entity.ToDoList;
import tw.teddysoft.tasks.usecase.port.in.todolist.error.ErrorInput;
import tw.teddysoft.tasks.usecase.port.in.todolist.error.ErrorUseCase;
import tw.teddysoft.tasks.usecase.port.in.todolist.help.HelpInput;
import tw.teddysoft.tasks.usecase.port.in.todolist.help.HelpUseCase;
import tw.teddysoft.tasks.usecase.port.in.todolist.show.ShowInput;
import tw.teddysoft.tasks.usecase.port.in.todolist.show.ShowOutput;
import tw.teddysoft.tasks.usecase.port.in.todolist.show.ShowUseCase;
import tw.teddysoft.tasks.usecase.port.out.ToDoListRepository;
import tw.teddysoft.tasks.usecase.port.in.project.add.AddProjectInput;
import tw.teddysoft.tasks.usecase.port.in.project.add.AddProjectUseCase;
import tw.teddysoft.tasks.TaskList;
import tw.teddysoft.tasks.usecase.port.out.todolist.show.ShowPresenter;
import tw.teddysoft.tasks.usecase.service.*;
import tw.teddysoft.tasks.usecase.port.in.task.add.AddTaskUseCase;
import tw.teddysoft.tasks.usecase.port.in.task.add.AddTaskInput;
import tw.teddysoft.tasks.usecase.port.in.task.setdone.SetDoneUseCase;
import tw.teddysoft.tasks.usecase.port.in.task.setdone.SetDoneInput;

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
                ShowUseCase showUseCase = new ShowService(repository);
                ShowInput showInput = new ShowInput();
                showInput.toDoListId = TaskList.DEFAULT_TO_DO_LIST_ID;
                ShowOutput output = showUseCase.execute(showInput);
                ShowPresenter showPresenter = new ShowConsolePresenter(out);
                showPresenter.present(output.toDoListDto);
                break;
            case "add":
                add(commandRest[1]);
                break;
            case "check":
                setDone(commandRest[1], true);
                break;
            case "uncheck":
                setDone(commandRest[1], false);
                break;
            case "help":
                HelpUseCase helpUseCase = new HelpService(new HelpConsolePresenter(out));
                HelpInput helpInput = new HelpInput();
                helpUseCase.execute(helpInput);
                break;
            default:
                ErrorUseCase errorUseCase = new ErrorService();
                ErrorInput errorInput = new ErrorInput();
                errorInput.command = commandLine;
                out.print(errorUseCase.execute(errorInput).getMessage());
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

    private void setDone(String taskId, boolean done) {
        SetDoneUseCase setDoneUseCase = new SetDoneService(repository);
        SetDoneInput input = new SetDoneInput();
        input.toDoListId = TaskList.DEFAULT_TO_DO_LIST_ID;
        input.taskId = taskId;
        input.done = done;
        out.print(setDoneUseCase.execute(input).getMessage());
    }
}
