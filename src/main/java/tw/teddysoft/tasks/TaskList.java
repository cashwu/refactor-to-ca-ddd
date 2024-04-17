package tw.teddysoft.tasks;

import tw.teddysoft.tasks.adapter.presenter.HelpConsolePresenter;
import tw.teddysoft.tasks.adapter.presenter.ShowConsolePresenter;
import tw.teddysoft.tasks.entity.*;
import tw.teddysoft.tasks.adapter.controller.ToDoListConsoleController;
import tw.teddysoft.tasks.usecase.port.in.task.add.AddTaskUseCase;
import tw.teddysoft.tasks.usecase.port.in.task.setdone.SetDoneUseCase;
import tw.teddysoft.tasks.usecase.port.in.todolist.help.HelpUseCase;
import tw.teddysoft.tasks.usecase.port.in.todolist.show.ShowUseCase;
import tw.teddysoft.tasks.usecase.port.out.ToDoListRepository;
import tw.teddysoft.tasks.adapter.repository.ToDoListInMemoryRepository;
import tw.teddysoft.tasks.usecase.port.out.todolist.show.ShowPresenter;
import tw.teddysoft.tasks.usecase.service.*;
import tw.teddysoft.tasks.usecase.port.in.project.add.AddProjectUseCase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public final class TaskList implements Runnable {
    private static final String QUIT = "quit";
    public static final String DEFAULT_TO_DO_LIST_ID = "001";
    private final ToDoList toDoList = new ToDoList(ToDoListId.of(DEFAULT_TO_DO_LIST_ID));
    private final BufferedReader in;
    private final PrintWriter out;
    private final ToDoListRepository repository;
    private final ShowUseCase showUseCase;
    private final ShowPresenter showPresenter;
    private final AddProjectUseCase addProjectUseCase;
    private final AddTaskUseCase addTaskUseCase;
    private final SetDoneUseCase setDoneUseCase;
    private final HelpUseCase helpUseCase;

    public static void main(String[] args) throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        new TaskList(in, out).run();
    }

    public TaskList(BufferedReader reader, PrintWriter writer) {
        this.in = reader;
        this.out = writer;
        repository = new ToDoListInMemoryRepository();
        if (repository.findById(ToDoListId.of(DEFAULT_TO_DO_LIST_ID)).isEmpty())
            repository.save(toDoList);

        showUseCase = new ShowService(repository);
        showPresenter = new ShowConsolePresenter(out);
        addProjectUseCase = new AddProjectService(repository);
        addTaskUseCase = new AddTaskService(repository);
        setDoneUseCase = new SetDoneService(repository);
        helpUseCase = new HelpService(new HelpConsolePresenter(out));
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
                    helpUseCase).execute(command);
        }
    }
}
