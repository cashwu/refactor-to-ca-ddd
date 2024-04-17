package tw.teddysoft.tasks.usecase.port;

import org.junit.jupiter.api.Test;
import tw.teddysoft.tasks.entity.ProjectName;
import tw.teddysoft.tasks.entity.ToDoList;
import tw.teddysoft.tasks.entity.ToDoListId;
import tw.teddysoft.tasks.usecase.port.out.ProjectPo;
import tw.teddysoft.tasks.usecase.port.out.TaskPo;
import tw.teddysoft.tasks.usecase.port.out.ToDoListPo;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ToDoListMapperTest {

    @Test
    public void toDto() {
        ToDoListId toDoListId = new ToDoListId("123456");
        ToDoList toDoList = new ToDoList(toDoListId, 5);
        ProjectName projectName = new ProjectName("Test");
        toDoList.addProject(projectName);
        toDoList.addProject(ProjectName.of("p2"));
        toDoList.addTask(projectName, "Read DDD", false);
        toDoList.addTask(projectName, "Read CA", false);
        toDoList.addTask(projectName, "Read Pattern", true);

        ToDoListDto toDoListDto = ToDoListMapper.toDto(toDoList);

        assertEquals(toDoList.getId().value(), toDoListDto.id);
        assertEquals(toDoList.getProjects().size(), toDoListDto.projectDots.size());
        for(int j = 0; j < toDoList.getProjects().size(); j ++){
            for(int i = 0; i< toDoList.getProjects().get(j).getTasks().size(); i++){
                assertEquals(toDoList.getProjects().get(j).getTasks().get(i).getId().value(), toDoListDto.projectDots.get(j).taskDtos.get(i).id);
                assertEquals(toDoList.getProjects().get(j).getTasks().get(i).getDescription(), toDoListDto.projectDots.get(j).taskDtos.get(i).description);
                assertEquals(toDoList.getProjects().get(j).getTasks().get(i).isDone(), toDoListDto.projectDots.get(j).taskDtos.get(i).done);
            }
        }
    }


    @Test
    public void toDomain() {
        ToDoListPo toDoListPo = new ToDoListPo();
        toDoListPo.setId("testId");
        toDoListPo.setLastId(1L);

        ProjectPo projectPo = new ProjectPo();
        String name = "Test Project";
        int order = 0;
        projectPo.setName(name);
        projectPo.setOrder(order);
        List<TaskPo> taskPos = new ArrayList<>();
        taskPos.add(new TaskPo("1", "Study Refactoring", false));
        projectPo.setTaskPos(taskPos);
        List<ProjectPo> projectPos = new ArrayList<>();
        projectPos.add(projectPo);
        toDoListPo.setProjectPos(projectPos);


        ToDoList toDoList = ToDoListMapper.toDomain(toDoListPo);

        assertEquals("testId", toDoList.getId().value());
        assertEquals(1L, toDoList.getTaskLastId());
        assertEquals(toDoList.getProjects().size(), toDoListPo.getProjectPos().size());

        for(int j = 0; j < toDoList.getProjects().size(); j ++){
            for(int i = 0; i< toDoList.getProjects().get(j).getTasks().size(); i++){
                assertEquals(toDoList.getProjects().get(j).getTasks().get(i).getId().value(), toDoListPo.getProjectPos().get(j).getTaskPos().get(i).getId());
                assertEquals(toDoList.getProjects().get(j).getTasks().get(i).getDescription(), toDoListPo.getProjectPos().get(j).getTaskPos().get(i).getDescription());
                assertEquals(toDoList.getProjects().get(j).getTasks().get(i).isDone(), toDoListPo.getProjectPos().get(j).getTaskPos().get(i).getDone());
            }
        }
    }


    @Test
    public void toPo() {
        ToDoListId toDoListId = new ToDoListId("123456");
        ToDoList toDoList = new ToDoList(toDoListId, 5);
        ProjectName projectName = new ProjectName("Test");
        toDoList.addProject(projectName);
        toDoList.addProject(ProjectName.of("p2"));
        toDoList.addTask(projectName, "Read DDD", false);
        toDoList.addTask(projectName, "Read CA", false);
        toDoList.addTask(projectName, "Read Pattern", true);

        ToDoListPo toDoListPo = ToDoListMapper.toPo(toDoList);

        assertEquals(toDoList.getId().value(), toDoListPo.getId());
        assertEquals(toDoList.getProjects().size(), toDoListPo.getProjectPos().size());
        for(int j = 0; j < toDoList.getProjects().size(); j++){
            for(int i = 0; i< toDoList.getProjects().get(j).getTasks().size(); i++){
                assertEquals(toDoList.getProjects().get(j).getTasks().get(i).getId().value(), toDoListPo.getProjectPos().get(j).getTaskPos().get(i).getId());
                assertEquals(toDoList.getProjects().get(j).getTasks().get(i).getDescription(), toDoListPo.getProjectPos().get(j).getTaskPos().get(i).getDescription());
                assertEquals(toDoList.getProjects().get(j).getTasks().get(i).isDone(), toDoListPo.getProjectPos().get(j).getTaskPos().get(i).getDone());
            }
        }
    }
}