package tw.teddysoft.tasks.usecase;

import tw.teddysoft.tasks.entity.TodoList;
import tw.teddysoft.tasks.usecase.port.out.TodoListRepository;

import java.io.PrintWriter;

public class Execute {
    private String commandLine;
    private TodoList todoList;
    private PrintWriter out;
    private TodoListRepository todoListRepository;

    public Execute(TodoList todoList, PrintWriter out, TodoListRepository todoListRepository) {
        this.todoList = todoList;
        this.out = out;
        this.todoListRepository = todoListRepository;
    }

    public void execute(String commandLine) {
        this.commandLine = commandLine;
        this.todoList = todoList;
        this.out = out;
        String[] commandRest = commandLine.split(" ", 2);
        String command = commandRest[0];
        switch (command) {
            case "show":
                new Show(todoList, out).show();
                break;
            case "add":
                new Add(todoList, out, todoListRepository).add(commandRest[1]);
                break;
            case "check":
                new SetDone(todoList, out).setDone(commandRest[1], true);
                break;
            case "uncheck":
                new SetDone(todoList, out).setDone(commandRest[1], false);
                break;
            case "help":
                new Help(out).help();
                break;
            default:
                new Error(out).error(command);
                break;
        }
    }
}