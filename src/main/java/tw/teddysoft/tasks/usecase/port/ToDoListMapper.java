package tw.teddysoft.tasks.usecase.port;

import tw.teddysoft.tasks.entity.ToDoList;

public class ToDoListMapper {

    public static ToDoListDto toDto(ToDoList toDoList) {
        ToDoListDto toDoListDto = new ToDoListDto();
        toDoListDto.id = toDoList.getId().value();
        toDoListDto.projectDots = ProjectMapper.toDto(toDoList.getProjects());
        return toDoListDto;
    }

}
