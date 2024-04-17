package tw.teddysoft.tasks.io.springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import tw.teddysoft.tasks.adapter.controller.console.ToDoListConsoleController;
import tw.teddysoft.tasks.entity.ToDoList;
import tw.teddysoft.tasks.entity.ToDoListId;
import tw.teddysoft.tasks.io.springboot.config.UseCaseInjection;
import tw.teddysoft.tasks.usecase.port.in.todolist.help.HelpUseCase;
import tw.teddysoft.tasks.usecase.port.in.todolist.show.ShowUseCase;
import tw.teddysoft.tasks.usecase.port.out.ToDoListRepository;
import tw.teddysoft.tasks.usecase.port.out.todolist.show.ShowPresenter;
import tw.teddysoft.tasks.usecase.port.in.project.add.AddProjectUseCase;
import tw.teddysoft.tasks.usecase.port.in.task.add.AddTaskUseCase;
import tw.teddysoft.tasks.usecase.port.in.task.setdone.SetDoneUseCase;
import tw.teddysoft.tasks.usecase.port.in.todolist.error.ErrorUseCase;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@ComponentScan(basePackages = {"tw.teddysoft.tasks"})
@EntityScan(basePackages={"tw.teddysoft.tasks"})
@AutoConfigureAfter({UseCaseInjection.class})
@SpringBootApplication
public class ToDoListSpringBootApp extends SpringBootServletInitializer implements CommandLineRunner {

    private static final String QUIT = "quit";

    public static final String TO_DO_LIST_ID = "001";

    private final BufferedReader in;
    private final PrintWriter out;
    private final ShowUseCase showUseCase;
    private final ShowPresenter showPresenter;
    private final AddProjectUseCase addProjectUseCase;
    private final AddTaskUseCase addTaskUseCase;
    private final SetDoneUseCase setDoneUseCase;
    private final ErrorUseCase errorUseCase;
    private final HelpUseCase helpUseCase;

    @Autowired
    public ToDoListSpringBootApp(
            BufferedReader in,
            PrintWriter out,
            ToDoListRepository repository,
            ShowUseCase showUseCase,
            ShowPresenter showPresenter,
            AddProjectUseCase addProjectUseCase,
            AddTaskUseCase addTaskUseCase,
            SetDoneUseCase setDoneUseCase,
            @Qualifier("consoleHelp") HelpUseCase helpUseCase,
            ErrorUseCase errorUseCase){

            this.in = in;
            this.out =  out;
            this.showUseCase = showUseCase;
            this.showPresenter = showPresenter;
            this.addProjectUseCase = addProjectUseCase;
            this.addTaskUseCase = addTaskUseCase;
            this.setDoneUseCase = setDoneUseCase;
            this.helpUseCase = helpUseCase;
            this.errorUseCase = errorUseCase;

            repository.save(new ToDoList(ToDoListId.of(TO_DO_LIST_ID)));

    }

    public static void main(String[] args) {
        SpringApplication.run(ToDoListSpringBootApp.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ToDoListSpringBootApp.class);
    }

    @Override
    public void run(String... args) {
        System.out.println("Running Spring Boot Application");
        while (true) {
            out.print("> ");
            out.flush();
            String command;
            try {
                command = in.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (command.equals(QUIT)) {
                System.exit(0);
            }
            new ToDoListConsoleController(
                    out,
                    showUseCase,
                    showPresenter,
                    addProjectUseCase,
                    addTaskUseCase,
                    setDoneUseCase,
                    helpUseCase,
                    errorUseCase).execute(command);
        }
    }
}

