package tw.teddysoft.tasks.usecase;

import org.junit.jupiter.api.Test;
import tw.teddysoft.ezddd.core.usecase.ExitCode;
import tw.teddysoft.tasks.adapter.repository.ToDoListInMemoryRepository;
import tw.teddysoft.tasks.adapter.repository.ToDoListInMemoryRepositoryPeer;
import tw.teddysoft.tasks.entity.ToDoList;
import tw.teddysoft.tasks.entity.ToDoListId;
import tw.teddysoft.tasks.io.standard.ToDoListApp;
import tw.teddysoft.tasks.usecase.port.out.ToDoListRepository;
import tw.teddysoft.tasks.usecase.port.in.task.today.TodayUseCase;
import tw.teddysoft.tasks.usecase.port.in.task.today.TodayInput;
import tw.teddysoft.tasks.usecase.service.TodayService;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static tw.teddysoft.tasks.usecase.TestUtil.*;

public class TodayUseCaseTest {


    @Test
    public void today_usecase(){
        ToDoListRepository repository = new ToDoListInMemoryRepository(new ToDoListInMemoryRepositoryPeer());
        repository.save(new ToDoList(ToDoListId.of(ToDoListApp.DEFAULT_TO_DO_LIST_ID), 0L));
        var projectName1 = run_add_project_use_case("p1", repository);
        var projectName2 = run_add_project_use_case("p2", repository);
        var taskId1 = run_add_task_use_case(projectName1, repository);
        var taskId2 = run_add_task_use_case(projectName1, repository);
        var taskId3 = run_add_task_use_case(projectName2, repository);

        LocalDateTime deadline = LocalDateTime.of(2024, 3, 5, 12, 0, 0);
        run_deadline_use_case(taskId1, deadline, repository);
        run_deadline_use_case(taskId2, LocalDateTime.of(2024, 8, 8, 12, 0, 0), repository);
        run_deadline_use_case(taskId3, deadline, repository);

        TodayUseCase todayUseCase = new TodayService(repository);
        TodayInput input = new TodayInput();
        input.toDoListId = ToDoListApp.DEFAULT_TO_DO_LIST_ID;
        input.today = deadline;

        var output = todayUseCase.execute(input);
        assertEquals(ExitCode.SUCCESS, output.getExitCode());
        assertEquals(2, output.toDoListDto.projectDots.stream().mapToLong(p->p.taskDtos.size()).sum());
    }

}
