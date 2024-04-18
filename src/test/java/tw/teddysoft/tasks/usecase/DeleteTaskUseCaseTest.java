package tw.teddysoft.tasks.usecase;

import org.junit.jupiter.api.Test;
import tw.teddysoft.ezddd.core.usecase.ExitCode;
import tw.teddysoft.tasks.adapter.out.repository.ToDoListInMemoryRepository;
import tw.teddysoft.tasks.adapter.out.repository.ToDoListInMemoryRepositoryPeer;
import tw.teddysoft.tasks.entity.TaskId;
import tw.teddysoft.tasks.entity.ToDoList;
import tw.teddysoft.tasks.entity.ToDoListId;
import tw.teddysoft.tasks.io.standard.ToDoListApp;
import tw.teddysoft.tasks.usecase.port.in.task.delete.DeleteTaskInput;
import tw.teddysoft.tasks.usecase.port.in.task.delete.DeleteTaskUseCase;
import tw.teddysoft.tasks.usecase.port.out.ToDoListRepository;
import tw.teddysoft.tasks.usecase.service.DeleteTaskService;

import static org.junit.jupiter.api.Assertions.*;
import static tw.teddysoft.tasks.usecase.TestUtil.run_add_project_use_case;
import static tw.teddysoft.tasks.usecase.TestUtil.run_add_task_use_case;

public class DeleteTaskUseCaseTest {

    @Test
    public void delete_an_existing_task_use_case(){
        ToDoListRepository repository = new ToDoListInMemoryRepository(new ToDoListInMemoryRepositoryPeer());
        repository.save(new ToDoList(ToDoListId.of(ToDoListApp.DEFAULT_TO_DO_LIST_ID), 0L));
        var projectName = run_add_project_use_case("p1", repository);
        var taskId1 = run_add_task_use_case(projectName, repository);
        var taskId2 = run_add_task_use_case(projectName, repository);


        DeleteTaskUseCase useCase = new DeleteTaskService(repository);
        DeleteTaskInput input = new DeleteTaskInput();
        input.toDoListId = ToDoListApp.DEFAULT_TO_DO_LIST_ID;
        input.taskId = taskId1;
        var output = useCase.execute(input);

        assertEquals(ExitCode.SUCCESS, output.getExitCode());
        assertEquals(input.taskId, output.getId());
        ToDoList toDoList = repository.findById(ToDoListId.of(ToDoListApp.DEFAULT_TO_DO_LIST_ID)).get();
        assertFalse(toDoList.containTask(TaskId.of(input.taskId)));
        assertTrue(toDoList.containTask(TaskId.of(taskId2)));
    }

    @Test
    public void delete_an_non_existing_task_use_case(){
        ToDoListRepository repository = new ToDoListInMemoryRepository(new ToDoListInMemoryRepositoryPeer());
        repository.save(new ToDoList(ToDoListId.of(ToDoListApp.DEFAULT_TO_DO_LIST_ID), 0L));
        var projectName = run_add_project_use_case("p1", repository);
        var taskId1 = run_add_task_use_case(projectName, repository);
        var taskId2 = run_add_task_use_case(projectName, repository);


        DeleteTaskUseCase useCase = new DeleteTaskService(repository);
        DeleteTaskInput input = new DeleteTaskInput();
        input.toDoListId = ToDoListApp.DEFAULT_TO_DO_LIST_ID;
        input.taskId = "non-existing-task";
        var output = useCase.execute(input);

        assertEquals(ExitCode.FAILURE, output.getExitCode());
        assertEquals(input.taskId, output.getId());
        ToDoList toDoList = repository.findById(ToDoListId.of(ToDoListApp.DEFAULT_TO_DO_LIST_ID)).get();
        assertTrue(toDoList.containTask(TaskId.of(taskId1)));
        assertTrue(toDoList.containTask(TaskId.of(taskId2)));
    }

}
