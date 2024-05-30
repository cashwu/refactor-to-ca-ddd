package tw.teddysoft.tasks.usecase.service;

import tw.teddysoft.ezddd.core.usecase.ExitCode;
import tw.teddysoft.ezddd.cqrs.usecase.CqrsOutput;
import tw.teddysoft.tasks.entity.TaskId;
import tw.teddysoft.tasks.entity.ToDoList;
import tw.teddysoft.tasks.entity.ToDoListId;
import tw.teddysoft.tasks.usecase.port.in.task.deadline.DeadlineInput;
import tw.teddysoft.tasks.usecase.port.in.task.deadline.DeadlineUseCase;
import tw.teddysoft.tasks.usecase.port.out.ToDoListRepository;

import static java.lang.String.format;

public class DeadlineService implements DeadlineUseCase {

    private final ToDoListRepository repository;

    public DeadlineService(ToDoListRepository repository) {
        this.repository = repository;
    }

    @Override
    public CqrsOutput execute(DeadlineInput input) {
        TaskId taskId = TaskId.of(input.taskId);
        ToDoList toDoList = repository.findById(ToDoListId.of(input.toDoListId)).get();

        if (!toDoList.containTask(taskId)){
            StringBuilder out = new StringBuilder();
            out.append(format("Could not find a task with an ID of %s.%n", taskId.value()));
            return CqrsOutput.create().setExitCode(ExitCode.FAILURE).setMessage(out.toString());
        }

        toDoList.setDeadline(taskId, input.deadline);
        repository.save(toDoList);
        return CqrsOutput.create().setExitCode(ExitCode.SUCCESS).setMessage("").setId(input.taskId);
    }
}
