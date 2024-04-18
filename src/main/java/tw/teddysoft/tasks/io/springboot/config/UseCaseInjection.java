package tw.teddysoft.tasks.io.springboot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tw.teddysoft.tasks.adapter.in.controller.web.HelpWebPresenter;
import tw.teddysoft.tasks.adapter.out.presenter.ShowConsolePresenter;
import tw.teddysoft.tasks.usecase.port.in.task.deadline.DeadlineUseCase;
import tw.teddysoft.tasks.usecase.port.in.task.today.TodayUseCase;
import tw.teddysoft.tasks.usecase.port.out.ToDoListRepository;
import tw.teddysoft.tasks.usecase.port.in.todolist.help.HelpUseCase;
import tw.teddysoft.tasks.usecase.port.in.todolist.show.ShowUseCase;
import tw.teddysoft.tasks.usecase.port.out.todolist.show.ShowPresenter;
import tw.teddysoft.tasks.usecase.port.in.project.add.AddProjectUseCase;
import tw.teddysoft.tasks.usecase.port.in.task.add.AddTaskUseCase;
import tw.teddysoft.tasks.usecase.port.in.task.setdone.SetDoneUseCase;
import tw.teddysoft.tasks.usecase.port.in.task.delete.DeleteTaskUseCase;
import tw.teddysoft.tasks.usecase.port.in.todolist.error.ErrorUseCase;
import tw.teddysoft.tasks.usecase.service.*;
import tw.teddysoft.tasks.adapter.out.presenter.HelpConsolePresenter;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

@Configuration("UseCaseInjection")
@AutoConfigureAfter({RepositoryInjection.class})
public class UseCaseInjection {

    private final ToDoListRepository toDoListRepository;

    @Autowired
    public UseCaseInjection(ToDoListRepository toDoListRepository) {
        this.toDoListRepository = toDoListRepository;
    }
    @Bean
    public BufferedReader getIn(){
        return new BufferedReader(new InputStreamReader(System.in));
    }

    @Bean
    public PrintWriter getOut(){
        return new PrintWriter(System.out);
    }

    @Bean
    public AddProjectUseCase addProjectUseCase() {
        return new AddProjectService(toDoListRepository);
    }

    @Bean
    public AddTaskUseCase addTaskUseCase() {
        return new AddTaskService(toDoListRepository);
    }

    @Bean
    public SetDoneUseCase setDoneUseCase() { return new SetDoneService(toDoListRepository); }

    @Bean
    public ErrorUseCase errorUseCase() {
        return new ErrorService();
    }

    @Bean
    public ShowUseCase showUseCase() {
        return new ShowService(toDoListRepository);
    }

    @Bean(name = "consoleHelp")
    public HelpUseCase consoleHelpUseCase() { return new HelpService(new HelpConsolePresenter(getOut())); }

    @Bean(name = "webHelp")
    public HelpUseCase webHelpUseCase() { return new HelpService(new HelpWebPresenter()); }


    @Bean
    public ShowPresenter showPresenter() {
        return new ShowConsolePresenter(getOut());
    }

    @Bean
    public DeadlineUseCase deadlineUseCase() {
        return new DeadlineService(toDoListRepository);
    }

    @Bean
    public TodayUseCase todayUseCase() {
        return new TodayService(toDoListRepository);
    }

    @Bean
    public DeleteTaskUseCase deleteTaskUseCase() {
        return new DeleteTaskService(toDoListRepository);
    }
}
