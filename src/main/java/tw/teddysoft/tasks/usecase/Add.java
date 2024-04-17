package tw.teddysoft.tasks.usecase;

import tw.teddysoft.tasks.entity.ProjectName;
import tw.teddysoft.tasks.entity.Task;
import tw.teddysoft.tasks.entity.ToDoList;
import tw.teddysoft.tasks.usecase.port.in.project.add.AddProjectInput;
import tw.teddysoft.tasks.usecase.port.in.project.add.AddProjectUseCase;
import tw.teddysoft.tasks.TaskList;
import tw.teddysoft.tasks.usecase.port.out.ToDoListRepository;
import tw.teddysoft.tasks.usecase.service.AddProjectService;

import java.io.PrintWriter;
import java.util.List;

public class Add {

    private final ToDoList toDoList;
    private final PrintWriter out;
    private final ToDoListRepository repository;

    public Add(ToDoList toDoList, PrintWriter out, ToDoListRepository repository) {
        this.toDoList = toDoList;
        this.out = out;
        this.repository = repository;
    }

    public void add(String commandLine) {
        String[] subcommandRest = commandLine.split(" ", 2);
        String subcommand = subcommandRest[0];
        if (subcommand.equals("project")) {
            AddProjectUseCase addProjectUseCase = new AddProjectService(repository);
            AddProjectInput addProjectInput = new AddProjectInput();
            addProjectInput.toDoListId = TaskList.DEFAULT_TO_DO_LIST_ID;
            addProjectInput.projectName = subcommandRest[1];
            addProjectUseCase.execute(addProjectInput);
        } else if (subcommand.equals("task")) {
            String[] projectTask = subcommandRest[1].split(" ", 2);
            addTask(ProjectName.of(projectTask[0]), projectTask[1]);
        }
    }

    private void addProject(ProjectName projectName) {
        toDoList.addProject(projectName);
    }

    private void addTask(ProjectName projectName, String description) {
        List<Task> projectTasks = toDoList.getTasks(projectName);
        if (projectTasks == null) {
            out.printf("Could not find a project with the name \"%s\".", projectName);
            out.println();
            return;
        }
        toDoList.addTask(projectName, description, false);
    }

}
