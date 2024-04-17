package tw.teddysoft.tasks.usecase.port;

import tw.teddysoft.tasks.entity.ToDoList;
import tw.teddysoft.tasks.entity.ToDoListId;
import tw.teddysoft.tasks.usecase.port.out.ToDoListPo;

public class ToDoListMapper {

    public static ToDoListDto toDto(ToDoList toDoList) {
        ToDoListDto toDoListDto = new ToDoListDto();
        toDoListDto.id = toDoList.getId().value();
        toDoListDto.projectDots = ProjectMapper.toDto(toDoList.getProjects());
        return toDoListDto;
    }

    public static ToDoList toDomain(ToDoListPo toDoListPo) {
        return new ToDoList(
                ToDoListId.of(toDoListPo.getId()),
                toDoListPo.getLastId(),
                ProjectMapper.toDomain(toDoListPo.getProjectPos()));
    }

    public static ToDoListPo toPo(ToDoList toDoList) {
        ToDoListPo toDoListPo = new ToDoListPo(toDoList.getId().value(), toDoList.getTaskLastId());
        toDoListPo.setProjectPos(ProjectMapper.toPo(toDoList.getProjects()));
        return toDoListPo;
    }
}
