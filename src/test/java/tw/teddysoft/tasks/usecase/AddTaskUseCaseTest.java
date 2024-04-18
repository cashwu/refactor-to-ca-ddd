package tw.teddysoft.tasks.usecase;

import org.junit.jupiter.api.Test;
import tw.teddysoft.ezddd.core.usecase.ExitCode;
import tw.teddysoft.tasks.adapter.out.repository.ToDoListInMemoryRepository;
import tw.teddysoft.tasks.adapter.out.repository.ToDoListInMemoryRepositoryPeer;
import tw.teddysoft.tasks.entity.ToDoList;
import tw.teddysoft.tasks.entity.ToDoListId;
import tw.teddysoft.tasks.io.standard.ToDoListApp;
import tw.teddysoft.tasks.entity.TaskId;
import tw.teddysoft.tasks.usecase.port.out.ToDoListRepository;
import tw.teddysoft.tasks.usecase.port.in.task.add.AddTaskUseCase;
import tw.teddysoft.tasks.usecase.port.in.task.add.AddTaskInput;
import tw.teddysoft.tasks.usecase.service.AddTaskService;


import static org.junit.jupiter.api.Assertions.*;
import static tw.teddysoft.tasks.usecase.TestUtil.run_add_project_use_case;

public class AddTaskUseCaseTest {

    @Test
    public void add_task_with_customisable_id_use_case(){
        ToDoListRepository repository = new ToDoListInMemoryRepository(new ToDoListInMemoryRepositoryPeer());
        repository.save(new ToDoList(ToDoListId.of(ToDoListApp.DEFAULT_TO_DO_LIST_ID), 0L));
        var projectName = run_add_project_use_case("p1", repository);

        AddTaskUseCase useCase = new AddTaskService(repository);
        AddTaskInput input = new AddTaskInput();
        input.toDoListId = ToDoListApp.DEFAULT_TO_DO_LIST_ID;
        input.projectName = projectName;
        input.description = "Study CA";
        input.done = false;
        input.taskId = "MyTaskId";
        var output = useCase.execute(input);

        assertEquals(ExitCode.SUCCESS, output.getExitCode());
        assertEquals(input.taskId, output.getId());

        ToDoList toDoList = repository.findById(ToDoListId.of(ToDoListApp.DEFAULT_TO_DO_LIST_ID)).get();
        assertTrue(toDoList.containTask(TaskId.of(input.taskId)));
    }

    @Test
    public void customized_task_id_disallow_spaces(){
        RuntimeException thrown = assertThrows(
                RuntimeException.class,
                () -> TaskId.of("A ID"),
                "Task id cannot contain space"
        );

        assertThrows(
                RuntimeException.class,
                () -> new TaskId(" B ID"),
                "Task id cannot contain space"
        );
    }

    @Test
    public void customized_task_id_disallow_special_characters(){
        RuntimeException thrown = assertThrows(
                RuntimeException.class,
                () -> TaskId.of("A:"),
                "Task id cannot contain special characters"
        );

        assertEquals("Task Id cannot contains special characters :, \\, /, %, $", thrown.getMessage());

        assertThrows(
                RuntimeException.class,
                () -> TaskId.of("\\"),
                "Task id cannot contain special characters"
        );
    }
}
