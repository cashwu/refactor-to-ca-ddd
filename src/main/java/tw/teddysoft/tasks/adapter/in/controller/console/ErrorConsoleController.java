package tw.teddysoft.tasks.adapter.in.controller.console;

import tw.teddysoft.ezddd.cqrs.usecase.CqrsOutput;
import tw.teddysoft.tasks.usecase.port.in.todolist.error.ErrorInput;
import tw.teddysoft.tasks.usecase.port.in.todolist.error.ErrorUseCase;

import java.io.PrintWriter;

public class ErrorConsoleController implements ConsoleController {
    private final PrintWriter out;
    private final ErrorUseCase errorUseCase;

    public ErrorConsoleController(PrintWriter out, ErrorUseCase errorUseCase) {
        this.out = out;
        this.errorUseCase = errorUseCase;
    }

    @Override
    public Response<CqrsOutput> execute(Request request) {
        ErrorInput errorInput = new ErrorInput();
        errorInput.command = request.getCommandLine();
        var output = errorUseCase.execute(errorInput);
        out.print(errorUseCase.execute(errorInput).getMessage());
        return Response.of(output);
    }
}
