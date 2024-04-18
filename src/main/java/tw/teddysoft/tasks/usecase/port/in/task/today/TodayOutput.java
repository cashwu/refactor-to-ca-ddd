package tw.teddysoft.tasks.usecase.port.in.task.today;

import tw.teddysoft.ezddd.cqrs.usecase.CqrsOutput;
import tw.teddysoft.tasks.usecase.port.ToDoListDto;

import java.util.List;

public class TodayOutput extends CqrsOutput<TodayOutput> {

    public List<TodayDto> todayDtos;

    public TodayOutput setTodayDtos(List<TodayDto> todayDtos) {
        this.todayDtos = todayDtos;
        return this;
    }
}

