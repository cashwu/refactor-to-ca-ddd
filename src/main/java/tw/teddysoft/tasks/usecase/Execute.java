package tw.teddysoft.tasks.usecase;

import tw.teddysoft.tasks.entity.ToDoList;
import tw.teddysoft.tasks.usecase.port.out.ToDoListRepository;

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
                new Add(toDoList, out, repository).add(commandRest[1]);
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

    private void check(String idString) {
        new SetDone(toDoList, out).setDone(idString, true);
    }

    private void uncheck(String idString) {
        new SetDone(toDoList, out).setDone(idString, false);
    }
}
