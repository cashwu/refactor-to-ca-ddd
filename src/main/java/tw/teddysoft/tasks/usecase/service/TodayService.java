package tw.teddysoft.tasks.usecase.service;

import tw.teddysoft.ezddd.core.usecase.ExitCode;
import tw.teddysoft.tasks.entity.ToDoList;
import tw.teddysoft.tasks.entity.ToDoListId;
import tw.teddysoft.tasks.usecase.port.ToDoListMapper;
import tw.teddysoft.tasks.usecase.port.in.task.today.TodayInput;
import tw.teddysoft.tasks.usecase.port.in.task.today.TodayOutput;
import tw.teddysoft.tasks.usecase.port.in.task.today.TodayUseCase;
import tw.teddysoft.tasks.usecase.port.out.ProjectPo;
import tw.teddysoft.tasks.usecase.port.out.ToDoListPo;
import tw.teddysoft.tasks.usecase.port.out.ToDoListRepository;

import java.util.ArrayList;
import java.util.List;

public class TodayService implements TodayUseCase {

    private final ToDoListRepository repository;

    public TodayService(ToDoListRepository repository) {
        this.repository = repository;
    }

    @Override
    public TodayOutput execute(TodayInput input) {
        ToDoListPo toDoListPo = ToDoListMapper.toPo(repository.findById(ToDoListId.of(input.toDoListId)).get());
        ToDoListPo resultToDoListPo = new ToDoListPo();
        resultToDoListPo.setId(toDoListPo.getId());
        resultToDoListPo.setLastId(toDoListPo.getLastId());
        List<ProjectPo> projectPos = new ArrayList<>();
        for(var project : toDoListPo.getProjectPos()){
            var tasks = project.getTaskPos();
            tasks.removeIf(t -> (null == t.getDeadline()) || (null == input.today) || !(t.getDeadline().toLocalDate().equals(input.today.toLocalDate())));
            project.setTaskPos(tasks);
            projectPos.add(project);
        }
        resultToDoListPo.setProjectPos(projectPos);

        TodayOutput output = new TodayOutput();
        output.toDoListDto = ToDoListMapper.toDto(ToDoListMapper.toDomain(resultToDoListPo));
        return output.setExitCode(ExitCode.SUCCESS).setMessage("");
    }
}
