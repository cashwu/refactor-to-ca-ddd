package tw.teddysoft.tasks.usecase;

import org.junit.jupiter.api.Test;
import tw.teddysoft.ezddd.core.usecase.ExitCode;
import tw.teddysoft.ezddd.cqrs.usecase.CqrsOutput;
import tw.teddysoft.ezspec.extension.junit5.EzScenario;
import tw.teddysoft.ezspec.keyword.Feature;
import tw.teddysoft.tasks.adapter.repository.ToDoListInMemoryRepository;
import tw.teddysoft.tasks.adapter.repository.ToDoListInMemoryRepositoryPeer;
import tw.teddysoft.tasks.entity.ToDoList;
import tw.teddysoft.tasks.entity.ToDoListId;
import tw.teddysoft.tasks.usecase.port.in.project.add.AddProjectInput;
import tw.teddysoft.tasks.usecase.port.in.project.add.AddProjectUseCase;
import tw.teddysoft.tasks.usecase.port.out.ToDoListRepository;
import tw.teddysoft.tasks.usecase.service.AddProjectService;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddProjectUseCaseTest {
    static final Feature feature = Feature.New("AddProject");

    @Test
    public void create_project_use_case_success(){
        ToDoListRepository repository = new ToDoListInMemoryRepository(new ToDoListInMemoryRepositoryPeer());
        ToDoList toDoList = new ToDoList(ToDoListId.of("001"));
        repository.save(toDoList);
        AddProjectUseCase useCase = new AddProjectService(repository);
        AddProjectInput input = new AddProjectInput();
        input.toDoListId = toDoList.getId().value();
        input.projectName = "p1";

        var output = useCase.execute(input);

        assertEquals(ExitCode.SUCCESS, output.getExitCode());
        ToDoList readToDoList = repository.findById(ToDoListId.of(input.toDoListId)).get();
        assertEquals(1, readToDoList.getProjects().size());
        assertEquals(input.projectName, readToDoList.getProjects().get(0).getName().value());
    }

    @EzScenario
    public void create_project_use_case_success_ezspec(){
        ToDoListRepository repository = new ToDoListInMemoryRepository(new ToDoListInMemoryRepositoryPeer());
        feature.newScenario()
            .Given("a to do list", env -> {
                ToDoList toDoList = new ToDoList(ToDoListId.of("001"));
                repository.save(toDoList);
                env.put("todoListId", toDoList.getId().value());
            })
            .When("I add a project ${name=p1} to the to do list", env -> {
                AddProjectUseCase useCase = new AddProjectService(repository);
                AddProjectInput input = new AddProjectInput();
                input.toDoListId = env.gets("todoListId");
                input.projectName = env.getArg("name");
                var output = useCase.execute(input);
                env.put("projectName", input.projectName);
                env.put("output", output);
            })
            .ThenSuccess(env -> {
                assertEquals(ExitCode.SUCCESS, env.get("output", CqrsOutput.class).getExitCode());
            })
            .And("the to do list is saved to database", env -> {
                ToDoList readToDoList = repository.findById(ToDoListId.of(env.gets("todoListId"))).get();
                assertEquals(1, readToDoList.getProjects().size());
                assertEquals(env.gets("projectName"), readToDoList.getProjects().get(0).getName().value());
            })
            .Execute();
    }
}
