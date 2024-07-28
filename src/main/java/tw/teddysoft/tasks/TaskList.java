package tw.teddysoft.tasks;

import tw.teddysoft.tasks.entity.*;
import tw.teddysoft.tasks.usecase.Execute;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public final class TaskList implements Runnable {
    private static final String QUIT = "quit";
    private final BufferedReader in;
    private final PrintWriter out;
    private TodoListId DEFAULT_TO_DO_LIST_ID = TodoListId.of(123);
    private final TodoList todoList = new TodoList(DEFAULT_TO_DO_LIST_ID);

    public TaskList(BufferedReader reader, PrintWriter writer) {
        this.in = reader;
        this.out = writer;
    }

    public static void main(String[] args) throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        new TaskList(in, out).run();
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
            new Execute(todoList, out).execute(command);
        }
    }

}
