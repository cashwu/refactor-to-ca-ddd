package tw.teddysoft.tasks.adapter.in.controller.console;

import tw.teddysoft.ezddd.cqrs.usecase.CqrsOutput;
import tw.teddysoft.tasks.io.standard.ToDoListApp;
import tw.teddysoft.tasks.usecase.port.in.task.view.ViewTaskInput;
import tw.teddysoft.tasks.usecase.port.in.task.view.ViewTaskOutput;
import tw.teddysoft.tasks.usecase.port.in.task.view.ViewTaskUseCase;
import tw.teddysoft.tasks.usecase.port.out.todolist.view.ViewTaskPresenter;

public class ViewConsoleController implements ConsoleController {

    private final ViewTaskUseCase viewTaskUseCase;
    private final ViewTaskPresenter viewTaskPresenter;

    public ViewConsoleController(ViewTaskUseCase viewTaskUseCase, ViewTaskPresenter viewTaskPresenter) {
        this.viewTaskUseCase = viewTaskUseCase;
        this.viewTaskPresenter = viewTaskPresenter;
    }

    @Override
    public Response<CqrsOutput> execute(Request request) {
        ViewTaskInput viewTaskInput = new ViewTaskInput();
        viewTaskInput.toDoListId = ToDoListApp.DEFAULT_TO_DO_LIST_ID;
        viewTaskInput.orderBy = ViewTaskInput.BY_DEADLINE;
        ViewTaskOutput viewTaskOutput = viewTaskUseCase.execute(viewTaskInput);
        viewTaskPresenter.present(viewTaskOutput.viewTaskDtos);
        return Response.of(viewTaskOutput);
    }
}
