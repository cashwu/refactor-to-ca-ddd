package tw.teddysoft.tasks.usecase;

import tw.teddysoft.ezddd.cqrs.usecase.CqrsOutput;
import tw.teddysoft.ezddd.cqrs.usecase.command.Command;

public interface AddProjectUseCase extends Command<AddProjectInput, CqrsOutput> {
}

