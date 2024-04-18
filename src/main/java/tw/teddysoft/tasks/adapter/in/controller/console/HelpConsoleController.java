package tw.teddysoft.tasks.adapter.in.controller.console;

import tw.teddysoft.ezddd.core.usecase.Input;
import tw.teddysoft.ezddd.cqrs.usecase.CqrsOutput;
import tw.teddysoft.tasks.usecase.port.in.todolist.help.HelpOutput;
import tw.teddysoft.tasks.usecase.port.in.todolist.help.HelpUseCase;

public class HelpConsoleController implements ConsoleController {
    private final HelpUseCase helpUseCase;

    public HelpConsoleController(HelpUseCase helpUseCase) {
        this.helpUseCase = helpUseCase;
    }

    @Override
    public Response<CqrsOutput> execute(Request request) {
        HelpOutput output = helpUseCase.execute(new Input.NullInput());
        return Response.of(output);
    }
}
