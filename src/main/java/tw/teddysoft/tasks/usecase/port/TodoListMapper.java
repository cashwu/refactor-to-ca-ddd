package tw.teddysoft.tasks.usecase.port;

import tw.teddysoft.tasks.entity.TodoList;

public class TodoListMapper {


    public static TodoListDto toDto(TodoList todoList) {

        TodoListDto dto = new TodoListDto();

        dto.id = todoList.getId().value();
        dto.projects = ProjectMapper.toDto(todoList.getProject());

        return dto;
    }
}
