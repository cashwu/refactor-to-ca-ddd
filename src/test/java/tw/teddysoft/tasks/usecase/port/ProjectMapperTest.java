package tw.teddysoft.tasks.usecase.port;

import org.junit.jupiter.api.Test;
import tw.teddysoft.tasks.entity.Project;
import tw.teddysoft.tasks.entity.ProjectName;
import tw.teddysoft.tasks.entity.Task;
import tw.teddysoft.tasks.entity.TaskId;
import tw.teddysoft.tasks.usecase.port.out.ProjectPo;
import tw.teddysoft.tasks.usecase.port.out.TaskPo;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ProjectMapperTest {

    @Test
    void toDto() {
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task(TaskId.of("Task1"), "Study DDD" , true));
        tasks.add(new Task(TaskId.of("Task2"), "Study CA", false));
        Project project = new Project(ProjectName.of("Project 1"), tasks);

        ProjectDto projectDto = ProjectMapper.toDto(project);

        assertEquals(project.getName().value(), projectDto.name);
        assertEquals(project.getTasks().size(), projectDto.taskDtos.size());
        for(int i = 0; i< project.getTasks().size(); i++){
            assertEquals(project.getTasks().get(i).getId().value(), projectDto.taskDtos.get(i).id);
            assertEquals(project.getTasks().get(i).getDescription(), projectDto.taskDtos.get(i).description);
            assertEquals(project.getTasks().get(i).isDone(), projectDto.taskDtos.get(i).done);
        }
    }

    @Test
    public void toDomain() {
        ProjectPo projectPo = new ProjectPo();
        String name = "Test Project";
        int order = 0;
        projectPo.setName(name);
        projectPo.setOrder(order);
        List<TaskPo> taskPos = new ArrayList<>();
        taskPos.add(new TaskPo("1", "Study Refactoring", false));
        projectPo.setTaskPos(taskPos);

        Project project = ProjectMapper.toDomain(projectPo);

        assertNotNull(project);
        assertEquals(projectPo.getName(), project.getName().value());
        assertEquals(projectPo.getTaskPos().size(), project.getTasks().size());
        for(int i = 0; i< project.getTasks().size(); i++){
            assertEquals(project.getTasks().get(i).getId().value(), projectPo.getTaskPos().get(i).getId());
            assertEquals(project.getTasks().get(i).getDescription(), projectPo.getTaskPos().get(i).getDescription());
            assertEquals(project.getTasks().get(i).isDone(), projectPo.getTaskPos().get(i).getDone());
        }
    }

    @Test
    void toPo() {
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task(TaskId.of("Task1"), "Study DDD" , true));
        tasks.add(new Task(TaskId.of("Task2"), "Study CA", false));
        Project project = new Project(ProjectName.of("My Project"), tasks);

        ProjectPo projectPo = ProjectMapper.toPo(project, 0);

        assertEquals(project.getName().value(), projectPo.getName());

        assertEquals(0, projectPo.getOrder());
        assertEquals(projectPo.getTaskPos().size(), project.getTasks().size());
        for(int i = 0; i< project.getTasks().size(); i++){
            assertEquals(project.getTasks().get(i).getId().value(), projectPo.getTaskPos().get(i).getId());
            assertEquals(project.getTasks().get(i).getDescription(), projectPo.getTaskPos().get(i).getDescription());
            assertEquals(project.getTasks().get(i).isDone(), projectPo.getTaskPos().get(i).getDone());
        }
    }
}