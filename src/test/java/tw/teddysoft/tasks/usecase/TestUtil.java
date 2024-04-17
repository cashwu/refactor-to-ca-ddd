package tw.teddysoft.tasks.usecase;

import tw.teddysoft.ezddd.cqrs.usecase.CqrsOutput;
import tw.teddysoft.tasks.usecase.port.in.project.add.AddProjectInput;
import tw.teddysoft.tasks.usecase.port.in.project.add.AddProjectUseCase;
import tw.teddysoft.tasks.usecase.port.out.ToDoListRepository;
import tw.teddysoft.tasks.usecase.service.AddProjectService;
import tw.teddysoft.tasks.usecase.service.AddTaskService;
import tw.teddysoft.tasks.usecase.service.DeadlineService;
import tw.teddysoft.tasks.io.standard.ToDoListApp;
import tw.teddysoft.tasks.usecase.port.in.task.add.AddTaskUseCase;
import tw.teddysoft.tasks.usecase.port.in.task.add.AddTaskInput;
import tw.teddysoft.tasks.usecase.port.in.task.deadline.DeadlineUseCase;
import tw.teddysoft.tasks.usecase.port.in.task.deadline.DeadlineInput;

import java.time.LocalDateTime;


public class TestUtil {

    public static String run_add_project_use_case(String projectName, ToDoListRepository repository){
        AddProjectUseCase useCase = new AddProjectService(repository);
        AddProjectInput input = new AddProjectInput();
        input.toDoListId = ToDoListApp.DEFAULT_TO_DO_LIST_ID;
        input.projectName = projectName;
        return useCase.execute(input).getId();
    }

    public static String run_add_task_use_case(String projectName, ToDoListRepository repository){
        AddTaskUseCase useCase = new AddTaskService(repository);
        AddTaskInput input = new AddTaskInput();
        input.toDoListId = ToDoListApp.DEFAULT_TO_DO_LIST_ID;
        input.projectName = projectName;
        input.description = "Study DDD";
        input.done = false;
        return useCase.execute(input).getId();
    }

    public static CqrsOutput run_deadline_use_case(String taskId, LocalDateTime deadline, ToDoListRepository repository){
        DeadlineUseCase deadlineUseCase = new DeadlineService(repository);
        DeadlineInput input = new DeadlineInput();
        input.toDoListId = ToDoListApp.DEFAULT_TO_DO_LIST_ID;
        input.taskId = taskId;
        input.deadline =  deadline;

        return deadlineUseCase.execute(input);
    }
}
