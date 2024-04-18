package tw.teddysoft.tasks.adapter.in.controller.console;

import tw.teddysoft.ezddd.cqrs.usecase.CqrsOutput;
import tw.teddysoft.tasks.io.standard.ToDoListApp;
import tw.teddysoft.tasks.usecase.port.in.task.delete.DeleteTaskInput;
import tw.teddysoft.tasks.usecase.port.in.task.delete.DeleteTaskUseCase;

public class DeleteConsoleController implements ConsoleController {
    private DeleteTaskUseCase deleteTaskUseCase;

    public DeleteConsoleController(DeleteTaskUseCase deleteTaskUseCase) {
        this.deleteTaskUseCase = deleteTaskUseCase;
    }
    @Override
    public Response<CqrsOutput> execute(Request request) {
        DeleteTaskInput deleteTaskInput = new DeleteTaskInput();
        deleteTaskInput.toDoListId = ToDoListApp.DEFAULT_TO_DO_LIST_ID;
        deleteTaskInput.taskId = request.getArg(0);
        return Response.of(deleteTaskUseCase.execute(deleteTaskInput));
    }
}
