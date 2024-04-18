package tw.teddysoft.tasks.adapter.in.controller.console;

import tw.teddysoft.ezddd.cqrs.usecase.CqrsOutput;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ConsoleControllerExecutor {
    private final PrintWriter out;
    private final Map<String, ConsoleController<CqrsOutput>> controllerMap;

    public ConsoleControllerExecutor(PrintWriter out){
        this.out = out;
        controllerMap = new HashMap<>();
    }

    public void registerController(String command, ConsoleController<CqrsOutput> controller){
        Objects.requireNonNull(command);
        Objects.requireNonNull(controller);

        controllerMap.put(command, controller);
    }

    public void execute(String commandLine) {
        try{
            String[] commandRest = commandLine.split(" ", 2);
            String command = commandRest[0];
            if (controllerMap.containsKey(command)) {
                var controller = controllerMap.get(command);
                var response = controller.execute(new Request(commandLine));
                out.print(response.result().getMessage());
            }
            else {
                controllerMap.get(ToDoListCommands.ERROR).execute(new Request(commandLine));
            }
        }
        catch (Exception e){
            out.println(e.getMessage());
        }
    }
}
