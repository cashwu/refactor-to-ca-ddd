package tw.teddysoft.tasks.usecase.service;

import tw.teddysoft.ezddd.cqrs.usecase.CqrsOutput;
import tw.teddysoft.tasks.entity.TaskId;
import tw.teddysoft.tasks.entity.ToDoList;
import tw.teddysoft.tasks.entity.ToDoListId;
import tw.teddysoft.tasks.usecase.port.in.task.delete.DeleteTaskInput;
import tw.teddysoft.tasks.usecase.port.in.task.delete.DeleteTaskUseCase;
import tw.teddysoft.tasks.usecase.port.out.ToDoListRepository;

import static java.lang.String.format;


public class DeleteTaskService implements DeleteTaskUseCase {

    private final ToDoListRepository repository;

    public DeleteTaskService(ToDoListRepository repository) {
        this.repository = repository;
    }

    @Override
    public CqrsOutput execute(DeleteTaskInput input) {

        ToDoList toDoList = repository.findById(ToDoListId.of(input.toDoListId)).get();
        var taskId = TaskId.of(input.taskId);
        if (!toDoList.containTask(taskId)){
            return CqrsOutput.create().fail().setMessage(format("Task '%s' does not exist", input.taskId)).setId(input.taskId);
        }

        toDoList.deleteTask(taskId);
        repository.save(toDoList);

        return CqrsOutput.create().succeed().setId(input.taskId);
    }
}
