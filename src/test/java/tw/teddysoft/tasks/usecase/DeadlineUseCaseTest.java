package tw.teddysoft.tasks.usecase;

import org.junit.jupiter.api.Test;
import tw.teddysoft.ezddd.core.usecase.ExitCode;
import tw.teddysoft.tasks.adapter.out.repository.ToDoListInMemoryRepository;
import tw.teddysoft.tasks.adapter.out.repository.ToDoListInMemoryRepositoryPeer;
import tw.teddysoft.tasks.entity.TaskId;
import tw.teddysoft.tasks.entity.ToDoList;
import tw.teddysoft.tasks.entity.ToDoListId;
import tw.teddysoft.tasks.io.standard.ToDoListApp;
import tw.teddysoft.tasks.usecase.port.in.project.add.AddProjectInput;
import tw.teddysoft.tasks.usecase.port.in.project.add.AddProjectUseCase;
import tw.teddysoft.tasks.usecase.port.in.task.add.AddTaskInput;
import tw.teddysoft.tasks.usecase.port.in.task.add.AddTaskUseCase;
import tw.teddysoft.tasks.usecase.port.in.task.deadline.DeadlineInput;
import tw.teddysoft.tasks.usecase.port.in.task.deadline.DeadlineUseCase;
import tw.teddysoft.tasks.usecase.port.out.ToDoListRepository;
import tw.teddysoft.tasks.usecase.service.AddProjectService;
import tw.teddysoft.tasks.usecase.service.AddTaskService;
import tw.teddysoft.tasks.usecase.service.DeadlineService;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeadlineUseCaseTest {

    @Test
    public void deadline_usecase(){
        ToDoListRepository repository = new ToDoListInMemoryRepository(new ToDoListInMemoryRepositoryPeer());
        repository.save(new ToDoList(ToDoListId.of(ToDoListApp.DEFAULT_TO_DO_LIST_ID), 0L));
        var projectName = run_add_project_use_case(repository);
        var taskId = run_add_task_use_case(projectName, repository);

        DeadlineUseCase deadlineUseCase = new DeadlineService(repository);
        DeadlineInput input = new DeadlineInput();
        input.toDoListId = ToDoListApp.DEFAULT_TO_DO_LIST_ID;
        input.taskId = taskId;
        input.deadline =  LocalDateTime.of(2024, 3, 5, 12, 0, 0);

        var output = deadlineUseCase.execute(input);
        assertEquals(ExitCode.SUCCESS, output.getExitCode());
        assertEquals(taskId, output.getId());

        var toDoList = repository.findById(ToDoListId.of(ToDoListApp.DEFAULT_TO_DO_LIST_ID)).get();
        assertEquals(input.deadline, toDoList.getTask(TaskId.of(taskId)).get().getDeadline());
    }

    private String run_add_project_use_case(ToDoListRepository repository){
        AddProjectUseCase useCase = new AddProjectService(repository);
        AddProjectInput input = new AddProjectInput();
        input.toDoListId = ToDoListApp.DEFAULT_TO_DO_LIST_ID;
        input.projectName = "p1";
        return useCase.execute(input).getId();
    }

    private String run_add_task_use_case(String projectName, ToDoListRepository repository){
        AddTaskUseCase useCase = new AddTaskService(repository);
        AddTaskInput input = new AddTaskInput();
        input.toDoListId = ToDoListApp.DEFAULT_TO_DO_LIST_ID;
        input.projectName = projectName;
        input.description = "Study DDD";
        input.done = false;
        return useCase.execute(input).getId();
    }
}
