package tw.teddysoft.tasks.usecase.service;

import tw.teddysoft.ezddd.core.usecase.ExitCode;
import tw.teddysoft.tasks.entity.ToDoListId;
import tw.teddysoft.tasks.usecase.port.ToDoListMapper;
import tw.teddysoft.tasks.usecase.port.in.task.today.TodayDto;
import tw.teddysoft.tasks.usecase.port.in.task.today.TodayInput;
import tw.teddysoft.tasks.usecase.port.in.task.today.TodayOutput;
import tw.teddysoft.tasks.usecase.port.in.task.today.TodayUseCase;
import tw.teddysoft.tasks.usecase.port.out.TaskPo;
import tw.teddysoft.tasks.usecase.port.out.ToDoListPo;
import tw.teddysoft.tasks.usecase.port.out.ToDoListRepository;

import java.util.Comparator;
import java.util.List;

public class TodayService implements TodayUseCase {

    private final ToDoListRepository repository;

    public TodayService(ToDoListRepository repository) {
        this.repository = repository;
    }

    @Override
    public TodayOutput execute(TodayInput input) {
        ToDoListPo toDoListPo = ToDoListMapper.toPo(repository.findById(ToDoListId.of(input.toDoListId)).get());
        if (null == input.today){
            return TodayOutput.create(TodayOutput.class).fail().setMessage("Input argument 'today' cannot be null.");
        }

        List<TodayDto> todayDtos =
                toDoListPo.getProjectPos()
                        .stream()
                        .flatMap(p -> p.getTaskPos().stream().map(task -> new Object[]{p.getName(), task}))
                        .map(pair -> new TodayDto((String) pair[0], (TaskPo) pair[1]))
                        .filter(t -> (null != t.getDeadline()) &&  t.getDeadline().toLocalDate().equals(input.today.toLocalDate()))
                        .sorted(Comparator.comparing(TodayDto::getDeadline, Comparator.nullsLast(Comparator.naturalOrder())))
                        .toList();

        return TodayOutput.create(TodayOutput.class).setExitCode(ExitCode.SUCCESS).setMessage("").setTodayDtos(todayDtos);
    }
}
