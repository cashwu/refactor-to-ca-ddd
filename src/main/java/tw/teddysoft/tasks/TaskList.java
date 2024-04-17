package tw.teddysoft.tasks;

import tw.teddysoft.tasks.entity.*;
import tw.teddysoft.tasks.entity.Error;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public final class TaskList implements Runnable {
    private static final String QUIT = "quit";
    public static final String DEFAULT_TO_DO_LIST_ID = "001";
    private final ToDoList toDoList = new ToDoList(ToDoListId.of(DEFAULT_TO_DO_LIST_ID));
    private final BufferedReader in;
    private final PrintWriter out;

    private long lastId = 0;

    public static void main(String[] args) throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        new TaskList(in, out).run();
    }

    public TaskList(BufferedReader reader, PrintWriter writer) {
        this.in = reader;
        this.out = writer;
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
            execute(command);
        }
    }

    private void execute(String commandLine) {
        String[] commandRest = commandLine.split(" ", 2);
        String command = commandRest[0];
        switch (command) {
            case "show":
                new Show(toDoList, out).show();
                break;
            case "add":
                new Add(toDoList, out).add(commandRest[1]);
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





    private long nextId() {
        return ++lastId;
    }
}
