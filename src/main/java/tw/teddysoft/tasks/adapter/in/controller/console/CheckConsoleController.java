package tw.teddysoft.tasks.adapter.in.controller.console;

import tw.teddysoft.ezddd.cqrs.usecase.CqrsOutput;
import tw.teddysoft.tasks.io.standard.ToDoListApp;
import tw.teddysoft.tasks.usecase.port.in.task.setdone.SetDoneInput;
import tw.teddysoft.tasks.usecase.port.in.task.setdone.SetDoneUseCase;

public class CheckConsoleController implements ConsoleController {
    private final SetDoneUseCase setDoneUseCase;

    public CheckConsoleController(SetDoneUseCase setDoneUseCase) {
        this.setDoneUseCase = setDoneUseCase;
    }

    @Override
    public Response<CqrsOutput> execute(Request request) {
        SetDoneInput input = new SetDoneInput();
        input.toDoListId = ToDoListApp.DEFAULT_TO_DO_LIST_ID;
        input.taskId = request.getArg(0);
        input.done = true;
        return Response.of(setDoneUseCase.execute(input));
    }
}
