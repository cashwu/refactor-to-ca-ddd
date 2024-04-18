package tw.teddysoft.tasks.adapter.controller.console;

import tw.teddysoft.ezddd.core.usecase.Input;
import tw.teddysoft.tasks.usecase.port.in.task.deadline.DeadlineInput;
import tw.teddysoft.tasks.usecase.port.in.task.deadline.DeadlineUseCase;
import tw.teddysoft.tasks.usecase.port.in.task.delete.DeleteTaskUseCase;
import tw.teddysoft.tasks.usecase.port.in.task.today.TodayInput;
import tw.teddysoft.tasks.usecase.port.in.task.today.TodayOutput;
import tw.teddysoft.tasks.usecase.port.in.todolist.error.ErrorInput;
import tw.teddysoft.tasks.usecase.port.in.todolist.error.ErrorUseCase;
import tw.teddysoft.tasks.usecase.port.in.todolist.help.HelpUseCase;
import tw.teddysoft.tasks.usecase.port.in.todolist.show.ShowInput;
import tw.teddysoft.tasks.usecase.port.in.todolist.show.ShowOutput;
import tw.teddysoft.tasks.usecase.port.in.todolist.show.ShowUseCase;
import tw.teddysoft.tasks.usecase.port.in.project.add.AddProjectInput;
import tw.teddysoft.tasks.usecase.port.in.project.add.AddProjectUseCase;
import tw.teddysoft.tasks.io.standard.ToDoListApp;
import tw.teddysoft.tasks.usecase.port.out.todolist.show.ShowPresenter;
import tw.teddysoft.tasks.usecase.port.in.task.add.AddTaskUseCase;
import tw.teddysoft.tasks.usecase.port.in.task.add.AddTaskInput;
import tw.teddysoft.tasks.usecase.port.in.task.setdone.SetDoneUseCase;
import tw.teddysoft.tasks.usecase.port.in.task.setdone.SetDoneInput;
import tw.teddysoft.tasks.usecase.port.in.task.today.TodayUseCase;
import tw.teddysoft.tasks.usecase.port.in.task.delete.DeleteTaskInput;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ToDoListConsoleController {

    private final PrintWriter out;
    private final ShowUseCase showUseCase;
    private final ShowPresenter showPresenter;
    private AddProjectUseCase addProjectUseCase;
    private final AddTaskUseCase addTaskUseCase;
    private final SetDoneUseCase setDoneUseCase;
    private final HelpUseCase helpUseCase;
    private final ErrorUseCase errorUseCase;
    private final DeadlineUseCase deadlineUseCase;
    private final TodayUseCase todayUseCase;
    private final DeleteTaskUseCase deleteTaskUseCase;

    public ToDoListConsoleController(
            PrintWriter out,
            ShowUseCase showUseCase,
            ShowPresenter showPresenter,
            AddProjectUseCase addProjectUseCase,
            AddTaskUseCase addTaskUseCase,
            SetDoneUseCase setDoneUseCase,
            HelpUseCase helpUseCase,
            ErrorUseCase errorUseCase,
            DeadlineUseCase deadlineUseCase,
            TodayUseCase todayUseCase,
            DeleteTaskUseCase deleteTaskUseCase) {

        this.out = out;
        this.showUseCase = showUseCase;
        this.showPresenter = showPresenter;
        this.addProjectUseCase = addProjectUseCase;
        this.addTaskUseCase = addTaskUseCase;
        this.setDoneUseCase = setDoneUseCase;
        this.helpUseCase = helpUseCase;
        this.errorUseCase = errorUseCase;
        this.deadlineUseCase = deadlineUseCase;
        this.todayUseCase = todayUseCase;
        this.deleteTaskUseCase = deleteTaskUseCase;
    }

    public void execute(String commandLine) {
        String[] commandRest = commandLine.split(" ", 2);
        String command = commandRest[0];
        switch (command) {
            case "show":
                ShowInput showInput = new ShowInput();
                showInput.toDoListId = ToDoListApp.DEFAULT_TO_DO_LIST_ID;
                ShowOutput output = showUseCase.execute(showInput);
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
                helpUseCase.execute(new Input.NullInput());
                break;
            case "deadline":
                String[] subcommandRest = commandRest[1].split(" ", 2);
                DeadlineInput deadlineInput = new DeadlineInput();
                deadlineInput.toDoListId = ToDoListApp.DEFAULT_TO_DO_LIST_ID;
                deadlineInput.taskId = subcommandRest[0];
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String dateTimeString = subcommandRest[1] + " 00:00:00";
                deadlineInput.deadline = LocalDateTime.parse(dateTimeString, formatter);
                out.print(deadlineUseCase.execute(deadlineInput).getMessage());
                break;
            case "today":
                TodayInput todayInput = new TodayInput();
                todayInput.toDoListId = ToDoListApp.DEFAULT_TO_DO_LIST_ID;
                todayInput.today = LocalDateTime.now();
                TodayOutput todayOutput = todayUseCase.execute(todayInput);
                showPresenter.present(todayOutput.toDoListDto);
                break;
            case "delete":
                String taskId = commandRest[1].split(" ", 2)[0];
                DeleteTaskInput deleteTaskInput = new DeleteTaskInput();
                deleteTaskInput.toDoListId = ToDoListApp.DEFAULT_TO_DO_LIST_ID;
                deleteTaskInput.taskId = taskId;
                out.println(deleteTaskUseCase.execute(deleteTaskInput).getMessage());
                break;
            default:
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
            AddProjectInput addProjectInput = new AddProjectInput();
            addProjectInput.toDoListId = ToDoListApp.DEFAULT_TO_DO_LIST_ID;
            addProjectInput.projectName = subcommandRest[1];
            addProjectUseCase.execute(addProjectInput);
        } else if (subcommand.equals("task")) {
            String[] projectTask = subcommandRest[1].split(" ", 2);
            AddTaskInput addTaskInput = new AddTaskInput();
            addTaskInput.toDoListId = ToDoListApp.DEFAULT_TO_DO_LIST_ID;
            addTaskInput.projectName = projectTask[0];
            addTaskInput.description = projectTask[1];
            addTaskInput.done = false;
            out.print(addTaskUseCase.execute(addTaskInput).getMessage());
        } else if (subcommand.equals("task2")) {
            String[] projectTask = subcommandRest[1].split(" ", 3);
            AddTaskInput addTaskInput = new AddTaskInput();
            addTaskInput.toDoListId = ToDoListApp.DEFAULT_TO_DO_LIST_ID;
            addTaskInput.projectName = projectTask[0];
            addTaskInput.taskId = projectTask[1];
            addTaskInput.description = projectTask[2];
            addTaskInput.done = false;
            out.print(addTaskUseCase.execute(addTaskInput).getMessage());
        }
    }

    private void setDone(String taskId, boolean done) {
        SetDoneInput input = new SetDoneInput();
        input.toDoListId = ToDoListApp.DEFAULT_TO_DO_LIST_ID;
        input.taskId = taskId;
        input.done = done;
        out.print(setDoneUseCase.execute(input).getMessage());
    }
}
