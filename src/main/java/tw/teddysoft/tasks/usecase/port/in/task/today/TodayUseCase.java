package tw.teddysoft.tasks.usecase.port.in.task.today;

import tw.teddysoft.ezddd.cqrs.usecase.query.Query;

public interface TodayUseCase extends Query<TodayInput, TodayOutput> {
}
