package tw.teddysoft.tasks.adapter.in.controller.console;

import tw.teddysoft.ezddd.cqrs.usecase.CqrsOutput;
import tw.teddysoft.tasks.io.standard.ToDoListApp;
import tw.teddysoft.tasks.usecase.port.in.todolist.show.ShowInput;
import tw.teddysoft.tasks.usecase.port.in.todolist.show.ShowOutput;
import tw.teddysoft.tasks.usecase.port.in.todolist.show.ShowUseCase;
import tw.teddysoft.tasks.usecase.port.out.todolist.show.ShowPresenter;

public class ShowConsoleController implements ConsoleController {
    private final ShowUseCase showUseCase;
    private final ShowPresenter showPresenter;

    public ShowConsoleController(ShowUseCase showUseCase, ShowPresenter showPresenter) {
        this.showUseCase = showUseCase;
        this.showPresenter = showPresenter;
    }

    @Override
    public Response<CqrsOutput> execute(Request request) {
        ShowInput showInput = new ShowInput();
        showInput.toDoListId = ToDoListApp.DEFAULT_TO_DO_LIST_ID;
        ShowOutput output = showUseCase.execute(showInput);
        showPresenter.present(output.toDoListDto);
        return Response.of(output);
    }
}
