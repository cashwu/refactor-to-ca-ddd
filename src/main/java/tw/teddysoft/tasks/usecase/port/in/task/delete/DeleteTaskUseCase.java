package tw.teddysoft.tasks.usecase.port.in.task.delete;

import tw.teddysoft.ezddd.cqrs.usecase.CqrsOutput;
import tw.teddysoft.ezddd.cqrs.usecase.command.Command;

public interface DeleteTaskUseCase extends Command<DeleteTaskInput, CqrsOutput> {
}
