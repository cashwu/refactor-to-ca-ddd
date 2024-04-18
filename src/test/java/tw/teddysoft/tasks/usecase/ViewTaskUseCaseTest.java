package tw.teddysoft.tasks.usecase;

import org.junit.jupiter.api.Test;
import tw.teddysoft.ezddd.core.usecase.ExitCode;
import tw.teddysoft.tasks.adapter.out.repository.ToDoListInMemoryRepository;
import tw.teddysoft.tasks.adapter.out.repository.ToDoListInMemoryRepositoryPeer;
import tw.teddysoft.tasks.io.standard.ToDoListApp;
import tw.teddysoft.tasks.usecase.port.out.ToDoListRepository;
import tw.teddysoft.tasks.usecase.port.in.task.view.ViewTaskUseCase;
import tw.teddysoft.tasks.usecase.port.in.task.view.ViewTaskInput;
import tw.teddysoft.tasks.usecase.service.ViewTaskService;
import tw.teddysoft.tasks.entity.ToDoList;
import tw.teddysoft.tasks.entity.ToDoListId;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static tw.teddysoft.tasks.usecase.TestUtil.*;

public class ViewTaskUseCaseTest {

    @Test
    public void view_tasks_by_deadline_use_case(){
        ToDoListRepository repository = new ToDoListInMemoryRepository(new ToDoListInMemoryRepositoryPeer());
        repository.save(new ToDoList(ToDoListId.of(ToDoListApp.DEFAULT_TO_DO_LIST_ID), 0L));
        var projectName1 = run_add_project_use_case("p1", repository);
        var projectName2 = run_add_project_use_case("p2", repository);
        var projectName3 = run_add_project_use_case("p3", repository);
        var taskId1 = run_add_task_use_case(projectName1, repository);
        var taskId2 = run_add_task_use_case(projectName1, repository);
        var taskId3 = run_add_task_use_case(projectName2, repository);
        var taskId4 = run_add_task_use_case(projectName2, repository);
        var taskId5 = run_add_task_use_case(projectName2, repository);
        var taskId6 = run_add_task_use_case(projectName3, repository);
        var taskId7 = run_add_task_use_case(projectName3, repository);
        run_deadline_use_case(taskId1, LocalDateTime.of(2024, 3, 5, 12, 0, 0), repository);
        run_deadline_use_case(taskId2, LocalDateTime.of(2024, 8, 1, 12, 0, 0), repository);
        run_deadline_use_case(taskId3, LocalDateTime.of(2024, 3, 5, 11, 0, 0), repository);
        run_deadline_use_case(taskId4, LocalDateTime.of(2024, 8, 8, 12, 0, 0), repository);
        run_deadline_use_case(taskId5, LocalDateTime.of(2024, 7, 10, 12, 0, 0), repository);
        run_deadline_use_case(taskId7, LocalDateTime.of(2022, 7, 10, 12, 0, 0), repository);

        ViewTaskUseCase useCase = new ViewTaskService(repository);
        ViewTaskInput input = new ViewTaskInput();
        input.toDoListId = ToDoListApp.DEFAULT_TO_DO_LIST_ID;
        input.orderBy = ViewTaskInput.BY_DEADLINE;
        var output = useCase.execute(input);

        assertEquals(ExitCode.SUCCESS, output.getExitCode());
        assertEquals(7, output.viewTaskDtos.size());
        assertEquals(taskId7, output.viewTaskDtos.get(0).taskId);
        assertEquals(taskId3, output.viewTaskDtos.get(1).taskId);
        assertEquals(taskId1, output.viewTaskDtos.get(2).taskId);
        assertEquals(taskId5, output.viewTaskDtos.get(3).taskId);
        assertEquals(taskId2, output.viewTaskDtos.get(4).taskId);
        assertEquals(taskId4, output.viewTaskDtos.get(5).taskId);
        assertEquals(taskId6, output.viewTaskDtos.get(6).taskId);
    }

}
