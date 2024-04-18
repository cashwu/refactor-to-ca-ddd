package tw.teddysoft.tasks.usecase.service;

import tw.teddysoft.tasks.entity.ToDoListId;
import tw.teddysoft.tasks.usecase.port.ToDoListMapper;
import tw.teddysoft.tasks.usecase.port.in.task.view.ViewTaskDto;
import tw.teddysoft.tasks.usecase.port.in.task.view.ViewTaskInput;
import tw.teddysoft.tasks.usecase.port.in.task.view.ViewTaskOutput;
import tw.teddysoft.tasks.usecase.port.in.task.view.ViewTaskUseCase;
import tw.teddysoft.tasks.usecase.port.out.TaskPo;
import tw.teddysoft.tasks.usecase.port.out.ToDoListPo;
import tw.teddysoft.tasks.usecase.port.out.ToDoListRepository;

import static java.lang.String.format;
import java.util.Comparator;
import java.util.List;

public class ViewTaskService implements ViewTaskUseCase {

    private final ToDoListRepository repository;

    public ViewTaskService(ToDoListRepository repository) {
        this.repository = repository;
    }

    @Override
    public ViewTaskOutput execute(ViewTaskInput input) {
        if (!input.orderBy.equals(ViewTaskInput.BY_DEADLINE)){
            return ViewTaskOutput.create(ViewTaskOutput.class).fail().setMessage(format("Not supported order by '%s'", input.orderBy));
        }

        ToDoListPo toDoListPo = ToDoListMapper.toPo(repository.findById(ToDoListId.of(input.toDoListId)).get());
        List<ViewTaskDto> viewTaskDtos =
                toDoListPo.getProjectPos()
                        .stream()
                        .flatMap(p -> p.getTaskPos().stream().map(task -> new Object[]{p.getName(), task}))
                        .map(pair -> new ViewTaskDto((String) pair[0], (TaskPo) pair[1]))
                        .sorted(Comparator.comparing(ViewTaskDto::getDeadline, Comparator.nullsLast(Comparator.naturalOrder())))
                        .toList();

        return ViewTaskOutput.create(ViewTaskOutput.class).succeed().setViewTaskDtos(viewTaskDtos);
    }
}
