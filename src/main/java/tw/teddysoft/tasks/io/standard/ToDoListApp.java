package tw.teddysoft.tasks.io.standard;

import tw.teddysoft.tasks.adapter.in.controller.console.*;
import tw.teddysoft.tasks.adapter.out.presenter.HelpConsolePresenter;
import tw.teddysoft.tasks.adapter.out.presenter.ShowConsolePresenter;
import tw.teddysoft.tasks.adapter.out.presenter.ViewTaskConsolePresenter;
import tw.teddysoft.tasks.adapter.out.repository.ToDoListInMemoryRepository;
import tw.teddysoft.tasks.adapter.out.repository.ToDoListInMemoryRepositoryPeer;
import tw.teddysoft.tasks.entity.*;
import tw.teddysoft.tasks.usecase.port.in.task.add.AddTaskUseCase;
import tw.teddysoft.tasks.usecase.port.in.task.deadline.DeadlineUseCase;
import tw.teddysoft.tasks.usecase.port.in.task.delete.DeleteTaskUseCase;
import tw.teddysoft.tasks.usecase.port.in.task.setdone.SetDoneUseCase;
import tw.teddysoft.tasks.usecase.port.in.task.today.TodayUseCase;
import tw.teddysoft.tasks.usecase.port.in.task.view.ViewTaskUseCase;
import tw.teddysoft.tasks.usecase.port.in.todolist.error.ErrorUseCase;
import tw.teddysoft.tasks.usecase.port.in.todolist.help.HelpUseCase;
import tw.teddysoft.tasks.usecase.port.in.todolist.show.ShowUseCase;
import tw.teddysoft.tasks.usecase.port.out.todolist.show.ShowPresenter;
import tw.teddysoft.tasks.usecase.port.out.todolist.view.ViewTaskPresenter;
import tw.teddysoft.tasks.usecase.service.*;
import tw.teddysoft.tasks.usecase.port.in.project.add.AddProjectUseCase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public final class ToDoListApp implements Runnable {
    private static final String QUIT = "quit";
    public static final String DEFAULT_TO_DO_LIST_ID = "001";
    private final BufferedReader in;
    private final PrintWriter out;
    private final ConsoleControllerExecutor consoleControllerExecutor;

    public static void main(String[] args) {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        var repository = new ToDoListInMemoryRepository(new ToDoListInMemoryRepositoryPeer());
        repository.save(new ToDoList(ToDoListId.of(DEFAULT_TO_DO_LIST_ID)));
        var showUseCase = new ShowService(repository);
        var showPresenter = new ShowConsolePresenter(out);
        var addProjectUseCase = new AddProjectService(repository);
        var addTaskUseCase = new AddTaskService(repository);
        var setDoneUseCase = new SetDoneService(repository);
        var helpUseCase = new HelpService(new HelpConsolePresenter(out));
        var errorUseCase = new ErrorService();
        var deadlineUseCase = new DeadlineService(repository);
        var todayUseCase = new TodayService(repository);
        var deleteTaskUseCase = new DeleteTaskService(repository);
        var viewTaskUseCase = new ViewTaskService(repository);
        var viewTaskPresenter = new ViewTaskConsolePresenter(out);

        new ToDoListApp(
                in,
                out,
                showUseCase,
                showPresenter,
                addProjectUseCase,
                addTaskUseCase,
                setDoneUseCase,
                helpUseCase,
                errorUseCase,
                deadlineUseCase,
                todayUseCase,
                deleteTaskUseCase,
                viewTaskUseCase,
                viewTaskPresenter
        ).run();
    }

    public ToDoListApp(
            BufferedReader reader,
            PrintWriter writer,
            ShowUseCase showUseCase,
            ShowPresenter showPresenter,
            AddProjectUseCase addProjectUseCase,
            AddTaskUseCase addTaskUseCase,
            SetDoneUseCase setDoneUseCase,
            HelpUseCase helpUseCase,
            ErrorUseCase errorUseCase,
            DeadlineUseCase deadlineUseCase,
            TodayUseCase todayUseCase,
            DeleteTaskUseCase deleteTaskUseCase,
            ViewTaskUseCase viewTaskUseCase,
            ViewTaskPresenter viewTaskPresenter) {

        this.in = reader;
        this.out = writer;
        consoleControllerExecutor = new ConsoleControllerExecutor(out);
        consoleControllerExecutor.registerController(ToDoListCommands.HELP, new HelpConsoleController(helpUseCase));
        consoleControllerExecutor.registerController(ToDoListCommands.ADD, new AddConsoleController(addProjectUseCase, addTaskUseCase));
        consoleControllerExecutor.registerController(ToDoListCommands.SHOW, new ShowConsoleController(showUseCase, showPresenter));
        consoleControllerExecutor.registerController(ToDoListCommands.CHECK, new CheckConsoleController(setDoneUseCase));
        consoleControllerExecutor.registerController(ToDoListCommands.UNCHECK, new UncheckConsoleController(setDoneUseCase));
        consoleControllerExecutor.registerController(ToDoListCommands.DEADLINE, new DeadlineConsoleController(deadlineUseCase));
        consoleControllerExecutor.registerController(ToDoListCommands.TODAY, new TodayConsoleController(todayUseCase, viewTaskPresenter));
        consoleControllerExecutor.registerController(ToDoListCommands.DELETE, new DeleteConsoleController(deleteTaskUseCase));
        consoleControllerExecutor.registerController(ToDoListCommands.VIEW, new ViewConsoleController(viewTaskUseCase, viewTaskPresenter));
        consoleControllerExecutor.registerController(ToDoListCommands.ERROR, new ErrorConsoleController(out, errorUseCase));
    }

    public void run() {
        while (true) {
            out.print("> ");
            out.flush();
            String command;
            try {
                command = in.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (command.equals(QUIT)) {
                break;
            }
            consoleControllerExecutor.execute(command);
        }
    }
}
