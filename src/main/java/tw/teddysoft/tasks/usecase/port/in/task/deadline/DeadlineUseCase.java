package tw.teddysoft.tasks.usecase.port.in.task.deadline;

import tw.teddysoft.ezddd.core.usecase.UseCase;
import tw.teddysoft.ezddd.cqrs.usecase.CqrsOutput;

public interface DeadlineUseCase extends UseCase<DeadlineInput, CqrsOutput> {
}
