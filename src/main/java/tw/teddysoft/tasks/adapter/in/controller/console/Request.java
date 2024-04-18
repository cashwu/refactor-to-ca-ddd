package tw.teddysoft.tasks.adapter.in.controller.console;

import java.util.*;

public class Request {
    private final String command;
    private final List<String> args;
    private final String commandLine;

    public Request(String commandLine) {
        Objects.requireNonNull(commandLine);

        this.commandLine = commandLine;
        String[] commandRest = commandLine.split(" ", 2);
        this.command = commandRest[0];
        if(commandRest.length > 1)
            args = parseArguments(commandRest[1]);
        else
            args = new ArrayList<>();
    }

    public List<String> getArgs() {
        return Collections.unmodifiableList(args);
    }

    public String getArg(int index) {
        return args.get(index);
    }
    public String command() {
        return command;
    }

    public String getCommandLine() {
        return commandLine;
    }

    public static List<String> parseArguments(String input) {
        Map<String, String> map = new HashMap<>();
        String[] words = input.split("\\s+");
        return Arrays.asList(words);
    }
}
