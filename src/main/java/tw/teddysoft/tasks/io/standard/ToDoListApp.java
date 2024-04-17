package tw.teddysoft.tasks.io.standard;

import tw.teddysoft.tasks.adapter.presenter.HelpConsolePresenter;
import tw.teddysoft.tasks.adapter.presenter.ShowConsolePresenter;
import tw.teddysoft.tasks.adapter.repository.ToDoListInMemoryRepositoryPeer;
import tw.teddysoft.tasks.entity.*;
import tw.teddysoft.tasks.adapter.controller.console.ToDoListConsoleController;
import tw.teddysoft.tasks.usecase.port.in.task.add.AddTaskUseCase;
import tw.teddysoft.tasks.usecase.port.in.task.setdone.SetDoneUseCase;
import tw.teddysoft.tasks.usecase.port.in.todolist.error.ErrorUseCase;
import tw.teddysoft.tasks.usecase.port.in.todolist.help.HelpUseCase;
import tw.teddysoft.tasks.usecase.port.in.todolist.show.ShowUseCase;
import tw.teddysoft.tasks.adapter.repository.ToDoListInMemoryRepository;
import tw.teddysoft.tasks.usecase.port.out.todolist.show.ShowPresenter;
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
    private final ShowUseCase showUseCase;
    private final ShowPresenter showPresenter;
    private final AddProjectUseCase addProjectUseCase;
    private final AddTaskUseCase addTaskUseCase;
    private final SetDoneUseCase setDoneUseCase;
    private final HelpUseCase helpUseCase;
    private final ErrorUseCase errorUseCase;

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

        new ToDoListApp(
                in,
                out,
                showUseCase,
                showPresenter,
                addProjectUseCase,
                addTaskUseCase,
                setDoneUseCase,
                helpUseCase,
                errorUseCase
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
            ErrorUseCase errorUseCase) {

        this.in = reader;
        this.out = writer;
        this.showUseCase = showUseCase;
        this.showPresenter = showPresenter;
        this.addProjectUseCase = addProjectUseCase;
        this.addTaskUseCase = addTaskUseCase;
        this.setDoneUseCase = setDoneUseCase;
        this.helpUseCase = helpUseCase;
        this.errorUseCase = errorUseCase;

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
            new ToDoListConsoleController(
                    out,
                    showUseCase,
                    showPresenter,
                    addProjectUseCase,
                    addTaskUseCase,
                    setDoneUseCase,
                    helpUseCase,
                    errorUseCase).execute(command);
        }
    }
}
