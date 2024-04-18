package tw.teddysoft.tasks.adapter.in.controller.console;

import tw.teddysoft.ezddd.cqrs.usecase.CqrsOutput;
import tw.teddysoft.tasks.io.standard.ToDoListApp;
import tw.teddysoft.tasks.usecase.port.in.task.today.TodayInput;
import tw.teddysoft.tasks.usecase.port.in.task.today.TodayOutput;
import tw.teddysoft.tasks.usecase.port.in.task.today.TodayUseCase;
import tw.teddysoft.tasks.usecase.port.in.task.view.ViewTaskDto;
import tw.teddysoft.tasks.usecase.port.out.todolist.view.ViewTaskPresenter;

import java.time.LocalDateTime;

public class TodayConsoleController implements ConsoleController {
    private final TodayUseCase todayUseCase;
    private final ViewTaskPresenter viewTaskPresenter;

    public TodayConsoleController(TodayUseCase todayUseCase, ViewTaskPresenter viewTaskPresenter) {
        this.todayUseCase = todayUseCase;
        this.viewTaskPresenter = viewTaskPresenter;
    }

    @Override
    public Response<CqrsOutput> execute(Request request) {
        TodayInput todayInput = new TodayInput();
        todayInput.toDoListId = ToDoListApp.DEFAULT_TO_DO_LIST_ID;
        todayInput.today = LocalDateTime.now();
        TodayOutput todayOutput = todayUseCase.execute(todayInput);
        viewTaskPresenter.present(todayOutput.todayDtos.stream().map(x -> (ViewTaskDto) x).toList());
        return Response.of(todayOutput);
    }
}
