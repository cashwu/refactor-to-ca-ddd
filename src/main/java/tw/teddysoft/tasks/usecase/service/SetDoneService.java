package tw.teddysoft.tasks.usecase.service;

import tw.teddysoft.ezddd.core.usecase.ExitCode;
import tw.teddysoft.ezddd.cqrs.usecase.CqrsOutput;
import tw.teddysoft.tasks.entity.TaskId;
import tw.teddysoft.tasks.entity.ToDoList;
import tw.teddysoft.tasks.entity.ToDoListId;
import tw.teddysoft.tasks.usecase.port.in.task.setdone.SetDoneInput;
import tw.teddysoft.tasks.usecase.port.in.task.setdone.SetDoneUseCase;
import tw.teddysoft.tasks.usecase.port.out.ToDoListRepository;

import static java.lang.String.format;

public class SetDoneService implements SetDoneUseCase {

    private final ToDoListRepository repository;

    public SetDoneService(ToDoListRepository repository) {
        this.repository = repository;
    }

    @Override
    public CqrsOutput execute(SetDoneInput input) {
        TaskId taskId = TaskId.of(input.taskId);
        ToDoList toDoList = repository.findById(ToDoListId.of(input.toDoListId)).get();

        if (!toDoList.containTask(taskId)){
            StringBuilder out = new StringBuilder();
            out.append(format("Could not find a task with an ID of %s.", taskId.value()));
            out.append("\n");
            return CqrsOutput.create().setExitCode(ExitCode.FAILURE).setMessage(out.toString());
        }
        toDoList.setDone(taskId, input.done);
        repository.save(toDoList);
        return CqrsOutput.create().setExitCode(ExitCode.SUCCESS).setMessage("");
    }
}
